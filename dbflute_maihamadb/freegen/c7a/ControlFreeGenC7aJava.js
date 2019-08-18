var genType = 'c7a';
var genCore = false;
var srcPathList = [];

/**
 * process.
 * @param {Request[]} requestList - requestList (NotNull)
 */
function process(requestList) {
    for each (var request in requestList) {
        if (!(request.isResourceTypeJsonGeneral() && request.requestName.startsWith("C7a"))) {
            continue;
        }
        manager.info('...Generating ' + genType + ': ' + request.requestName);
        try {
            request.enableOutputDirectory();
            manager.makeDirectory(request.generateDirPath);
            var optionMap = request.optionMap;
            scriptEngine.eval('load("./freegen/' + genType + '/' + manager.initCap(genType) + 'Rule.js");');
            if (optionMap.ruleJsPath && optionMap.ruleJsPath != '') {
                // load application rule settings if exists
                scriptEngine.eval('load("' + optionMap.ruleJsPath + '");');
            }
            processCore(request);
            processHull(request);
        } catch (e) {
            var message = '/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n';
            message += 'Error in ' + genType + ' generation.\n';
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
    clean(null, null, 'org/dbflute/' + genType, srcPathList);
    clean(null, null, '../resources/' + genType + '/di', srcPathList);
}

/**
 * process core.
 * @param {Request} request - request (NotNull)
 */
function processCore(request) {
    var coreVmList = [];
    if (!genCore) {
        genCore = true;
        coreVmList.push('C7aPool.vm');
        coreVmList.push('bhv/AbstractC7aBehaviorWritable.vm');
        coreVmList.push('entity/C7aEntity.vm');
        coreVmList.push('entity/AbstractC7aEntity.vm');
        coreVmList.push('entity/C7aPagingResultBean.vm');
        coreVmList.push('entity/dbmeta/C7aDBMeta.vm');
        coreVmList.push('entity/dbmeta/AbstractC7aDBMeta.vm');
        coreVmList.push('cbean/C7aSpecification.vm');
        coreVmList.push('cbean/AbstractC7aSpecification.vm');
        coreVmList.push('cbean/C7aConditionQuery.vm');
        coreVmList.push('cbean/AbstractC7aConditionQuery.vm');
        coreVmList.push('cbean/C7aConditionBean.vm');
        coreVmList.push('cbean/AbstractC7aConditionBean.vm');
    }
    for each (var coreVm in coreVmList) {
        generate('./' + genType + '/allcommon/' + genType + '/' + coreVm, 'org/dbflute/' + genType + '/' + coreVm.replaceAll('\.vm$', '.java'), '', true);
    }
}

/**
 * process hull.
 * @param {Request} request - request (NotNull)
 */
function processHull(request) {
    var rule = scriptEngine.get(genType + 'Rule');

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

    var optionMap = request.optionMap;
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

    var poolFactory = new java.util.LinkedHashMap();
    poolFactory.c7a = c7a;
    poolFactory.package = c7a.package;
    poolFactory.className = 'C7a' + manager.initCap(poolFactory.c7a.schema) + 'PoolFactory';

    var path = poolFactory.package.replace(/\./g, '/') + '/' + poolFactory.className.replace(/\./g, '/') + '.java';
    generate('./c7a/C7aPoolFactory.vm', path, poolFactory, false);

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

    processVm(exUserTypeMap.values(), './c7a/C7aBsUserType.vm', './c7a/C7aExUserType.vm');
    processVm(exDBMetaList, null, './c7a/C7aDBMeta.vm');
    processVm(exEntityList, './c7a/C7aBsEntity.vm', './c7a/C7aExEntity.vm');
    processVm(exConditionQueryList, './c7a/C7aBsConditionQuery.vm', './c7a/C7aExConditionQuery.vm');
    processVm(exConditionBeanList, './c7a/C7aBsConditionBean.vm', './c7a/C7aExConditionBean.vm');
    processVm(exBehaviorList, './c7a/C7aBsBehavior.vm', './c7a/C7aExBehavior.vm');
    processDoc(rule, c7a, exEntityList);
    clean(rule, request, c7a.package.replace(/\./g, '/'), srcPathList);
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
    srcPathList.push(dest);
    if (!java.nio.file.Files.exists(java.nio.file.Paths.get(generator.outputPath, dest)) || overwite) {
        manager.makeDirectory(dest);
        print('generate("' + dest + '")');
        return generator.parse(src, dest, 'data', data);
    }
    return '';
}

function processVm(exList, bsVm, exVm) {
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

function clean(rule, request, genDir, srcPathList) {
    var generateAbsolutePathList = [];
    for (var srcPathIndex in srcPathList) {
        generateAbsolutePathList.push(new java.io.File(generator.outputPath, srcPathList[srcPathIndex]).getAbsolutePath());
    }
    var list = listFiles(new java.io.File(generator.outputPath, genDir));
    for (var index in list) {
        var file = list[index];
        if (generateAbsolutePathList.indexOf(file.getAbsolutePath()) === -1
                && (rule == null || scriptEngine.invokeMethod(rule, 'deleteTarget', request, file))) {
            print('delete(' + file + ')');
            file.delete();
        }
    }
}

function listFiles(dir) {
    var list = [];
    var fileList = dir.listFiles();
    for (var fileIndex in fileList) {
        var file = fileList[fileIndex];
        if (file.isDirectory()) {
            list = list.concat(this.listFiles(file));
        } else if (file.isFile()) {
            list.push(file);
        }
    }
    return list;
}
