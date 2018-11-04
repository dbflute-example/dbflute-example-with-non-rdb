var genC7aCore = false;

/**
 * process.
 * @param {Request} request - request (NotNull)
 */
function process(request) {
    try {
        request.enableOutputDirectory();
        manager.makeDirectory(request.generateDirPath);
        processC7aCore(request);
        processC7a(request);
    } catch (e) {
        var message = '/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n';
        message += 'Error in C8a generation.\n';
        message += '\n';
        message += '[Advice]\n';
        message += '1. Upgrade version of dbflute engine to 1.1.6 or later.\n';
        message += '2. If you can not upgrade the version, you may be able to avoid errors by chang setting.\n';
        message += '3. Still if you do not improve it, there is a possibility of a bug.\n';
        message += '_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/\n';
        print(message);
        throw e;
    }
}

/**
 * process C8a Core.
 * @param {Request} request - request (NotNull)
 */
function processC7aCore(request) {
    if (genC7aCore) {
        return;
    }
    genC7aCore = true;
    generate('./c7a/allcommon/c7a/C7aPool.vm', 'org/dbflute/c7a/C7aPool.java', null, true);
    generate('./c7a/allcommon/c7a/bhv/AbstractC7aBehaviorWritable.vm', 'org/dbflute/c7a/bhv/AbstractC7aBehaviorWritable.java', null, true);
    generate('./c7a/allcommon/c7a/entity/C7aEntity.vm', 'org/dbflute/c7a/entity/C7aEntity.java', null, true);
    generate('./c7a/allcommon/c7a/entity/AbstractC7aEntity.vm', 'org/dbflute/c7a/entity/AbstractC7aEntity.java', null, true);
    generate('./c7a/allcommon/c7a/entity/C7aPagingResultBean.vm', 'org/dbflute/c7a/entity/C7aPagingResultBean.java', null, true);
    generate('./c7a/allcommon/c7a/entity/dbmeta/C7aDBMeta.vm', 'org/dbflute/c7a/entity/dbmeta/C7aDBMeta.java', null, true);
    generate('./c7a/allcommon/c7a/entity/dbmeta/AbstractC7aDBMeta.vm', 'org/dbflute/c7a/entity/dbmeta/AbstractC7aDBMeta.java', null, true);
    generate('./c7a/allcommon/c7a/cbean/C7aConditionQuery.vm', 'org/dbflute/c7a/cbean/C7aConditionQuery.java', null, true);
    generate('./c7a/allcommon/c7a/cbean/AbstractC7aConditionQuery.vm', 'org/dbflute/c7a/cbean/AbstractC7aConditionQuery.java', null, true);
    generate('./c7a/allcommon/c7a/cbean/C7aConditionBean.vm', 'org/dbflute/c7a/cbean/C7aConditionBean.java', null, true);
    generate('./c7a/allcommon/c7a/cbean/AbstractC7aConditionBean.vm', 'org/dbflute/c7a/cbean/AbstractC7aConditionBean.java', null, true);
}

/**
 * process C8a.
 * @param {Request} request - request (NotNull)
 */
function processC7a(request) {
    var optionMap = request.optionMap;

    scriptEngine.eval('load("./freegen/c7a/C7aRule.js");');
    if (optionMap.ruleJsPath && optionMap.ruleJsPath != '') {
        // load application rule settings if exists
        scriptEngine.eval('load("' + optionMap.ruleJsPath + '");');
    }
    var rule = scriptEngine.get('c7aRule');

    // only remote api requests here
    manager.info('...Generating c7a: ' + request.requestName);

    var c7a = new java.util.LinkedHashMap();
    c7a.schema = scriptEngine.invokeMethod(rule, 'schema', request);
    c7a.schemaShort = scriptEngine.invokeMethod(rule, 'schemaShort', request);
    c7a.schemaPackage = scriptEngine.invokeMethod(rule, 'schemaPackage', c7a.schema);
    c7a.package = request.package + '.' + c7a.schemaPackage;
    c7a.optimisticLock = scriptEngine.invokeMethod(rule, 'optimisticLock');
    var typeMap = scriptEngine.invokeMethod(rule, 'typeMap');

    var exUserTypeMap = new java.util.LinkedHashMap();
    var exDBMetaList = [];
    var exEntityList = [];
    var exConditionBeanList = [];
    var exConditionQueryList = [];
    var exBehaviorList = [];
    var keyspaceMetaList = optionMap.jsonMap['keyspaceMetaList'];
    for (var keyspaceMetaKey in keyspaceMetaList) {
        var keyspaceMeta = keyspaceMetaList[keyspaceMetaKey];
        for (var userTypeMetaKey in keyspaceMeta.userTypeMetaList) {
            var userTypeMeta = keyspaceMeta.userTypeMetaList[userTypeMetaKey];

            var exUserType = new java.util.LinkedHashMap();
            exUserType.c7a = c7a;
            exUserType.userTypeName = userTypeMeta.name;
            exUserType.bs = new java.util.LinkedHashMap();
            exUserType.bs.c7a = c7a;
            exUserType.bs.exUserTypeMap = exUserTypeMap;
            exUserType.bs.keyspaceName = keyspaceMeta.name;
            exUserType.bs.userTypeName = userTypeMeta.name;
            exUserType.bs.properties = userTypeMeta.columnMetaList;
            analyzeProperties(rule, exUserType.bs);

            var subPackage = scriptEngine.invokeMethod(rule, 'exUserTypeSubPackage', c7a, userTypeMeta);
            exUserType.package = subPackage ? c7a.package + '.' + subPackage : c7a.package;
            exUserType.className = scriptEngine.invokeMethod(rule, 'exUserTypeClassName', c7a, userTypeMeta);
            subPackage = scriptEngine.invokeMethod(rule, 'bsUserTypeSubPackage', c7a, userTypeMeta);
            exUserType.bs.package = subPackage ? c7a.package + '.' + subPackage : c7a.package;
            exUserType.bs.className = scriptEngine.invokeMethod(rule, 'bsUserTypeClassName', c7a, userTypeMeta);
            exUserTypeMap.put(exUserType.userTypeName, exUserType);
        }

        for (var tableMetaKey in keyspaceMeta.tableMetaList) {
            var tableMeta = keyspaceMeta.tableMetaList[tableMetaKey];

            var base = new java.util.LinkedHashMap();
            base.c7a = c7a;
            base.tableName = tableMeta.name;
            base.bs = new java.util.LinkedHashMap();
            base.bs.c7a = c7a;
            base.bs.exUserTypeMap = exUserTypeMap;
            base.bs.keyspaceName = keyspaceMeta.name;
            base.bs.tableName = tableMeta.name;
            base.bs.alias = tableMeta.alias;
            base.bs.comment = tableMeta.comment;
            base.bs.properties = tableMeta.columnMetaList;
            base.bs.partitionKeyList = tableMeta.partitionKeyList;
            base.bs.clusteringColumnList = tableMeta.clusteringColumnList;
            base.bs.clusteringOrderList = tableMeta.clusteringOrderList;
            base.bs.primaryKeyList = tableMeta.primaryKeyList;
            base.bs.materializedViewMetaList = tableMeta.materializedViewMetaList;
            base.bs.filterTableName = scriptEngine.invokeMethod(rule, 'filterTableName', c7a, tableMeta);
            analyzeProperties(rule, base.bs);

            var exDBMeta = new java.util.LinkedHashMap(base.bs);
            var subPackage = scriptEngine.invokeMethod(rule, 'dbMetaSubPackage', c7a, tableMeta);
            exDBMeta.package = subPackage ? c7a.package + '.' + subPackage : c7a.package;
            exDBMeta.className = scriptEngine.invokeMethod(rule, 'dbMetaClassName', c7a, tableMeta);
            exDBMeta.extendsClass = scriptEngine.invokeMethod(rule, 'dbMetaExtendsClass', c7a, tableMeta);
            exDBMeta.properties = base.bs.properties;
            exDBMetaList.push(exDBMeta);

            var exEntity = new java.util.LinkedHashMap(base);
            var subPackage = scriptEngine.invokeMethod(rule, 'exEntitySubPackage', c7a, tableMeta);
            exEntity.package = subPackage ? c7a.package + '.' + subPackage : c7a.package;
            exEntity.className = scriptEngine.invokeMethod(rule, 'exEntityClassName', c7a, tableMeta);
            exEntity.bs = new java.util.LinkedHashMap(base.bs);
            subPackage = scriptEngine.invokeMethod(rule, 'bsEntitySubPackage', c7a, tableMeta);
            exEntity.bs.package = subPackage ? c7a.package + '.' + subPackage : c7a.package;
            exEntity.bs.className = scriptEngine.invokeMethod(rule, 'bsEntityClassName', c7a, tableMeta);
            exEntity.bs.extendsClass = scriptEngine.invokeMethod(rule, 'bsEntityExtendsClass', c7a, tableMeta);
            exEntity.bs.implementsClasses = scriptEngine.invokeMethod(rule, 'bsEntityImplementsClasses', c7a, tableMeta);
            exEntity.bs.exDBMeta = exDBMeta;
            exEntityList.push(exEntity);

            var exConditionQuery = new java.util.LinkedHashMap(base);
            subPackage = scriptEngine.invokeMethod(rule, 'exConditionQuerySubPackage', c7a, tableMeta);
            exConditionQuery.c7a = c7a;
            exConditionQuery.package = subPackage ? c7a.package + '.' + subPackage : c7a.package;
            exConditionQuery.className = scriptEngine.invokeMethod(rule, 'exConditionQueryClassName', c7a, tableMeta);
            exConditionQuery.bs = new java.util.LinkedHashMap(base.bs);
            subPackage = scriptEngine.invokeMethod(rule, 'bsConditionQuerySubPackage', c7a, tableMeta);
            exConditionQuery.bs.package = subPackage ? c7a.package + '.' + subPackage : c7a.package;
            exConditionQuery.bs.className = scriptEngine.invokeMethod(rule, 'bsConditionQueryClassName', c7a, tableMeta);
            exConditionQuery.bs.extendsClass = scriptEngine.invokeMethod(rule, 'bsConditionQueryExtendsClass', c7a, tableMeta);
            exConditionQuery.bs.implementsClasses = scriptEngine.invokeMethod(rule, 'bsConditionQueryImplementsClasses', c7a, tableMeta);
            exConditionQueryList.push(exConditionQuery);

            var exConditionBean = new java.util.LinkedHashMap(base);
            subPackage = scriptEngine.invokeMethod(rule, 'exConditionBeanSubPackage', c7a, tableMeta);
            exConditionBean.package = subPackage ? c7a.package + '.' + subPackage : c7a.package;
            exConditionBean.className = scriptEngine.invokeMethod(rule, 'exConditionBeanClassName', c7a, tableMeta);
            exConditionBean.bs = new java.util.LinkedHashMap(base.bs);
            subPackage = scriptEngine.invokeMethod(rule, 'bsConditionBeanSubPackage', c7a, tableMeta);
            exConditionBean.bs.package = subPackage ? c7a.package + '.' + subPackage : c7a.package;
            exConditionBean.bs.className = scriptEngine.invokeMethod(rule, 'bsConditionBeanClassName', c7a, tableMeta);
            exConditionBean.bs.extendsClass = scriptEngine.invokeMethod(rule, 'bsConditionBeanExtendsClass', c7a, tableMeta);
            exConditionBean.bs.implementsClasses = scriptEngine.invokeMethod(rule, 'bsConditionBeanImplementsClasses', c7a, tableMeta);
            exConditionBean.bs.enablePagingCount = scriptEngine.invokeMethod(rule, 'enablePagingCount', c7a, tableMeta);
            exConditionBean.bs.exDBMeta = exDBMeta;
            exConditionBean.bs.exConditionQuery = exConditionQuery;
            exConditionBeanList.push(exConditionBean);

            var exBehavior = new java.util.LinkedHashMap(base);
            subPackage = scriptEngine.invokeMethod(rule, 'exBehaviorSubPackage', c7a, tableMeta);
            exBehavior.c7a = c7a;
            exBehavior.package = subPackage ? c7a.package + '.' + subPackage : c7a.package;
            exBehavior.className = scriptEngine.invokeMethod(rule, 'exBehaviorClassName', c7a, tableMeta);
            exBehavior.bs = new java.util.LinkedHashMap(base.bs);
            subPackage = scriptEngine.invokeMethod(rule, 'bsBehaviorSubPackage', c7a, tableMeta);
            exBehavior.bs.package = subPackage ? c7a.package + '.' + subPackage : c7a.package;
            exBehavior.bs.className = scriptEngine.invokeMethod(rule, 'bsBehaviorClassName', c7a, tableMeta);
            exBehavior.bs.extendsClassName = scriptEngine.invokeMethod(rule, 'bsBehaviorExtendsClassName', c7a);
            exBehavior.bs.extendsClass = scriptEngine.invokeMethod(rule, 'bsBehaviorExtendsClass', c7a);
            exBehavior.bs.implementsClasses = scriptEngine.invokeMethod(rule, 'bsBehaviorImplementsClasses', c7a, tableMeta);
            exBehavior.bs.exEntity = exEntity;
            exBehavior.bs.exConditionBean = exConditionBean;
            exBehaviorList.push(exBehavior);
        }
    }

    var commonColumn = new java.util.LinkedHashMap();
    commonColumn.c7a = c7a;
    commonColumn.package = c7a.package;
    commonColumn.className = scriptEngine.invokeMethod(rule, 'entityDefinedCommonColumnClassName', c7a);
    commonColumn.putAll(scriptEngine.invokeMethod(rule, 'commonColumn'));
    commonColumn.properties = commonColumn.commonColumn;
    analyzeProperties(rule, commonColumn);

    var path = commonColumn.package.replace(/\./g, '/') + '/' + commonColumn.className.replace(/\./g, '/') + '.java';
    generate('./c7a/C7aEntityDefinedCommonColumn.vm', path, commonColumn, true);

    var abstractBehavior = new java.util.LinkedHashMap();
    abstractBehavior.c7a = c7a;
    abstractBehavior.package = c7a.package;
    abstractBehavior.className = scriptEngine.invokeMethod(rule, 'bsBehaviorExtendsClassName', c7a);
    abstractBehavior.commonColumn = commonColumn;

    var path = abstractBehavior.package.replace(/\./g, '/') + '/' + abstractBehavior.className.replace(/\./g, '/') + '.java';
    generate('./c7a/C7aAbstractBhv.vm', path, abstractBehavior, true);

    var di = new java.util.LinkedHashMap();
    di.c7a = c7a;
    di.exBehaviorList = exBehaviorList;
    if (manager.isTargetContainerLastaDi()) {
        path = scriptEngine.invokeMethod(rule, 'poolDiXmlPath', c7a, request.resourceFile);
        generate('./c7a/allcommon/container/lastadi/C7aPoolDiXml.vm', path, di, true);
        path = scriptEngine.invokeMethod(rule, 'diXmlPath', c7a, request.resourceFile);
        generate('./c7a/allcommon/container/lastadi/C7aDiXml.vm', path, di, true);
    }

    processVm(rule, exUserTypeMap.values(), './c7a/C7aBsUserType.vm', './c7a/C7aExUserType.vm');
    processVm(rule, exDBMetaList, null, './c7a/C7aDBMeta.vm');
    processVm(rule, exEntityList, './c7a/C7aBsEntity.vm', './c7a/C7aExEntity.vm');
    processVm(rule, exConditionQueryList, './c7a/C7aBsConditionQuery.vm', './c7a/C7aExConditionQuery.vm');
    processVm(rule, exConditionBeanList, './c7a/C7aBsConditionBean.vm', './c7a/C7aExConditionBean.vm');
    processVm(rule, exBehaviorList, './c7a/C7aBsBehavior.vm', './c7a/C7aExBehavior.vm');
    processDoc(rule, c7a, exEntityList);
}

function analyzeProperties(rule, base) {
    var typeMap = scriptEngine.invokeMethod(rule, 'typeMap');
    base.userTypeList = [];
    for (var propertyKey in base.properties) {
        var property = base.properties[propertyKey];
        property.fieldName = scriptEngine.invokeMethod(rule, 'fieldName', base.c7a, base, property);
        if (property.typeName !== 'udt') {
            property.fieldClass = typeMap[property.typeName];
        } else {
            var userTypeMeta = new java.util.LinkedHashMap();
            userTypeMeta.name = property.userTypeName;
            property.fieldClass = scriptEngine.invokeMethod(rule, 'exUserTypeClassName', base.c7a, userTypeMeta);
            base.userTypeList.push(userTypeMeta.name);
        }
        for (var typeArgumentIndex in property.typeArgumentList) {
            if (typeArgumentIndex === 0) {
                property.fieldClass += '<';
            }
            if (property.typeArgumentList[typeArgumentIndex].typeName !== 'udt') {
                var typeName = typeMap[property.typeArgumentList[typeArgumentIndex].typeName];
            } else {
                var userTypeMeta = new java.util.LinkedHashMap();
                userTypeMeta.name = property.typeArgumentList[typeArgumentIndex].userTypeName;
                var typeName = scriptEngine.invokeMethod(rule, 'exUserTypeClassName', base.c7a, userTypeMeta);
                base.userTypeList.push(userTypeMeta.name);
            }
            property.fieldClass += typeName;
            if (typeArgumentIndex + 1 === property.typeArgumentList.size()) {
                property.fieldClass += '>';
            } else {
                property.fieldClass += ', ';
            }
        }
        property.properties = property.columnMetaList;
    }
}

/**
 * Process doc.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Request} request - request (NotNull)
 */
function processDoc(rule, c7a, exEntityList) {
    if (!rule['docGeneration']) {
        return;
    }
    var doc = new java.util.LinkedHashMap();
    doc.put('c7a', c7a);
    doc.put('exEntityList', exEntityList);
    var c7aDocHtml = generate('./c7a/doc/C7aDocHtml.vm', null, doc, true);
    var lastaDocHtmlPathList = manager.getLastaDocHtmlPathList();
    var markNaviLink = manager.getLastaDocHtmlMarkFreeGenDocNaviLink();
    var markBody = manager.getLastaDocHtmlMarkFreeGenDocBody();
    var naviLinkHtml = '    | <a href="#c7a">to c7a</a>';
    var naviLinkDestinationHtml = '<span id="c7a"></span>';
    for (var lastaDocHtmlPathIndex in lastaDocHtmlPathList) {
        var lastaDocHtmlPath = java.nio.file.Paths.get(lastaDocHtmlPathList[lastaDocHtmlPathIndex]);
        var lastaDocHtml = Java.type('java.lang.String').join('\n', java.nio.file.Files.readAllLines(lastaDocHtmlPath));
        if (!lastaDocHtml.contains(naviLinkHtml)) {
            lastaDocHtml = lastaDocHtml.replace(markNaviLink, naviLinkHtml + '\n' + markNaviLink);
        }
        if (!lastaDocHtml.contains(naviLinkDestinationHtml)) {
            c7aDocHtml = naviLinkDestinationHtml + c7aDocHtml;
        }
        java.nio.file.Files.write(lastaDocHtmlPath, lastaDocHtml.replace(markBody, c7aDocHtml + '\n' + markBody).getBytes('UTF-8'));
    }
}

// ===================================================================================
//                                                                              Common
//                                                                              ======
/**
 * generate file.
 * @param {string} src - src (NotNull)
 * @param {string} dest - dest (NotNull)
 * @param {map} data - data (NotNull)
 * @param {boolean} overwite - overwite (NotNull)
 */
function generate(src, dest, data, overwite) {
    if (dest === null) {
        return generator.parse(src, dest, 'data', data);
    }
    if (!java.nio.file.Files.exists(java.nio.file.Paths.get(generator.outputPath, dest)) || overwite) {
        manager.makeDirectory(dest);
        print('generate("' + dest + '")');
        return generator.parse(src, dest, 'data', data);
    }
    return '';
}

function processVm(rule, exList, bsVm, exVm) {
    for each (var ex in exList) {
        var bs = ex.bs;
        if (bsVm != null) {
            var path = bs.package.replace(/\./g, '/') + '/' + bs.className + '.java';
            generate(bsVm, path, bs, true);
        }
        if (exVm != null) {
            var path = ex.package.replace(/\./g, '/') + '/' + ex.className + '.java';
            generate(exVm, path, ex, bsVm == null);
        }
    }
}
