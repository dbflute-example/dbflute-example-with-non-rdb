var genType = 'solr';
var genCore = false;
var srcPathList = [];

/**
 * process.
 * @param {Request[]} requestList - requestList (NotNull)
 */
function process(requestList) {
    for each (var request in requestList) {
        if (!request.isResourceTypeSolr()) {
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
        coreVmList.push('bhv/AbstractSolrBehavior.vm');
        coreVmList.push('cbean/AbstractSolrConditionBean.vm');
        coreVmList.push('cbean/AbstractSolrFilterQueryBean.vm');
        coreVmList.push('cbean/AbstractSolrQueryBean.vm');
        coreVmList.push('cbean/AbstractSolrSpecification.vm');
        coreVmList.push('cbean/SolrCBCall.vm');
        coreVmList.push('cbean/SolrConditionBean.vm');
        coreVmList.push('cbean/SolrFilterQueryBean.vm');
        coreVmList.push('cbean/SolrFQBCall.vm');
        coreVmList.push('cbean/SolrQBCall.vm');
        coreVmList.push('cbean/SolrQFCall.vm');
        coreVmList.push('cbean/SolrQueryBean.vm');
        coreVmList.push('cbean/SolrQueryBuilder.vm');
        coreVmList.push('cbean/SolrQueryLogicalOperator.vm');
        coreVmList.push('cbean/SolrSetRangeSearchBean.vm');
        coreVmList.push('cbean/SolrSpecification.vm');
        coreVmList.push('entity/AbstractSolrEntity.vm');
        coreVmList.push('entity/AbstractSolrIndexEntity.vm');
        coreVmList.push('entity/dbmeta/SolrDBMeta.vm');
        coreVmList.push('entity/SolrEntity.vm');
        coreVmList.push('exception/SolrException.vm');
        coreVmList.push('result/SolrFacetResultBean.vm');
        coreVmList.push('result/SolrPagingResultBean.vm');
        coreVmList.push('result/SolrResultBean.vm');
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

    var tableMap = request.tableMap;
    var solr = new java.util.LinkedHashMap();
    solr.schema = scriptEngine.invokeMethod(rule, 'schema', request);
    solr.schemaShort = scriptEngine.invokeMethod(rule, 'schemaShort', request);
    solr.package = request.package;
    solr.tableMap = tableMap;
    solr.columnList = request.columnList;

    var dbMetaList = [];
    var exEntityList = [];
    var exIndexEntityList = [];
    var exConditionBeanList = [];
    var exConditionQueryList = [];
    var exConditionFilterQueryList = [];
    var exBehaviorList = [];

    var base = new java.util.LinkedHashMap();
    base.solr = solr;
    base.bs = new java.util.LinkedHashMap();
    base.bs.solr = solr;

    var dbMeta = new java.util.LinkedHashMap(base);
    var subPackage = 'bsentity.meta';
    dbMeta.package = subPackage ? solr.package + '.' + subPackage : solr.package;
    dbMeta.className = 'Solr' + solr.schemaShort + 'Dbm';
    dbMetaList.push(dbMeta);
    
    var exEntity = new java.util.LinkedHashMap(base);
    subPackage = 'exentity';
    exEntity.package = subPackage ? solr.package + '.' + subPackage : solr.package;
    exEntity.className = 'Solr' + solr.schemaShort;
    exEntity.bs = new java.util.LinkedHashMap(base.bs);
    subPackage = 'bsentity';
    exEntity.bs.package = subPackage ? solr.package + '.' + subPackage : solr.package;
    exEntity.bs.className = 'SolrBs' + solr.schemaShort;
    exEntityList.push(exEntity);

    var exIndexEntity = new java.util.LinkedHashMap(base);
    subPackage = 'exentity.index';
    exIndexEntity.package = subPackage ? solr.package + '.' + subPackage : solr.package;
    exIndexEntity.className = 'Solr' + solr.schemaShort + 'Index';
    exIndexEntity.bs = new java.util.LinkedHashMap(base.bs);
    subPackage = 'bsentity.index';
    exIndexEntity.bs.package = subPackage ? solr.package + '.' + subPackage : solr.package;
    exIndexEntity.bs.className = 'SolrBs' + solr.schemaShort + 'Index';
    exIndexEntityList.push(exIndexEntity);

    var exConditionBean = new java.util.LinkedHashMap(base);
    subPackage = 'cbean';
    exConditionBean.package = subPackage ? solr.package + '.' + subPackage : solr.package;
    exConditionBean.className = 'Solr' + solr.schemaShort + 'CB';
    exConditionBean.bs = new java.util.LinkedHashMap(base.bs);
    subPackage = 'cbean.bs';
    exConditionBean.bs.package = subPackage ? solr.package + '.' + subPackage : solr.package;
    exConditionBean.bs.className = 'SolrBs' + solr.schemaShort + 'CB';
    exConditionBeanList.push(exConditionBean);

    var exConditionQuery = new java.util.LinkedHashMap(base);
    subPackage = 'cbean.cq';
    exConditionQuery.package = subPackage ? solr.package + '.' + subPackage : solr.package;
    exConditionQuery.className = 'Solr' + solr.schemaShort + 'CQ';
    exConditionQuery.bs = new java.util.LinkedHashMap(base.bs);
    subPackage = 'cbean.cq.bs';
    exConditionQuery.bs.package = subPackage ? solr.package + '.' + subPackage : solr.package;
    exConditionQuery.bs.className = 'SolrBs' + solr.schemaShort + 'CQ';
    exConditionQueryList.push(exConditionQuery);

    var exConditionFilterQuery = new java.util.LinkedHashMap(base);
    subPackage = 'cbean.cfq';
    exConditionFilterQuery.package = subPackage ? solr.package + '.' + subPackage : solr.package;
    exConditionFilterQuery.className = 'Solr' + solr.schemaShort + 'CFQ';
    exConditionFilterQuery.bs = new java.util.LinkedHashMap(base.bs);
    subPackage = 'cbean.cfq.bs';
    exConditionFilterQuery.bs.package = subPackage ? solr.package + '.' + subPackage : solr.package;
    exConditionFilterQuery.bs.className = 'SolrBs' + solr.schemaShort + 'CFQ';
    exConditionFilterQueryList.push(exConditionFilterQuery);

    var exBehavior = new java.util.LinkedHashMap(base);
    subPackage = 'exbhv';
    exBehavior.package = subPackage ? solr.package + '.' + subPackage : solr.package;
    exBehavior.className = 'Solr' + solr.schemaShort + 'Bhv';
    exBehavior.bs = new java.util.LinkedHashMap(base.bs);
    subPackage = 'bsbhv';
    exBehavior.bs.package = subPackage ? solr.package + '.' + subPackage : solr.package;
    exBehavior.bs.className = 'SolrBs' + solr.schemaShort + 'Bhv';
    exBehaviorList.push(exBehavior);

    processVm(dbMetaList, null, './solr/SolrDBMeta.vm');
    processVm(exConditionBeanList, './solr/SolrBsConditionBean.vm', './solr/SolrExConditionBean.vm');
    processVm(exEntityList, './solr/SolrBsEntity.vm', './solr/SolrExEntity.vm');
    processVm(exIndexEntityList, './solr/SolrBsIndexEntity.vm', './solr/SolrExIndexEntity.vm');
    processVm(exConditionQueryList, './solr/SolrBsQueryBean.vm', './solr/SolrExQueryBean.vm');
    processVm(exConditionFilterQueryList, './solr/SolrBsFilterQueryBean.vm', './solr/SolrExFilterQueryBean.vm');
    processVm(exBehaviorList, './solr/SolrBsBehavior.vm', './solr/SolrExBehavior.vm');

    var di = new java.util.LinkedHashMap(base);
    if (manager.isTargetContainerSeasar()) {
        generate('./solr/allcommon/container/seasar/SolrDicon.vm', '../resources/solr/di/solr-' + solr.schema + '.dicon', di, true);
    }
    if (manager.isTargetContainerLastaDi()) {
        generate('./solr/allcommon/container/lastadi/SolrDiXml.vm', '../resources/solr/di/solr-' + solr.schema + '.xml', di, true);
    }

    if (genCore) {
        if (manager.isTargetContainerSeasar()) {
            generate('./solr/allcommon/container/seasar/SolrAllDicon.vm', '../resources/solr/di/solr-all.dicon', '', true);
        }
        if (manager.isTargetContainerLastaDi()) {
            generate('./solr/allcommon/container/lastadi/SolrAllDiXml.vm', '../resources/solr/di/solr-all.xml', '', true);
        }
    }
    
    processDoc(rule, request);
    clean(rule, request, solr.package.replace(/\./g, '/'), srcPathList);
}

/**
 * Process doc.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Request} request - request (NotNull)
 */
function processDoc(rule, request) {
    if (!rule['docGeneration']) {
        return;
    }
    var doc = new java.util.LinkedHashMap();
    doc.request = request;
    var docHtml = generate('./' + genType + '/doc/' + manager.initCap(genType) + 'DocHtml.vm', null, doc, true);
    var lastaDocHtmlPathList = manager.getLastaDocHtmlPathList();
    var markNaviLink = manager.getLastaDocHtmlMarkFreeGenDocNaviLink();
    var markBody = manager.getLastaDocHtmlMarkFreeGenDocBody();
    var naviLinkHtml = '    | <a href="#' + genType + '">to ' + genType + '</a>';
    var naviLinkDestinationHtml = '<span id="' + genType + '"></span>';
    for (var lastaDocHtmlPathIndex in lastaDocHtmlPathList) {
        var lastaDocHtmlPath = java.nio.file.Paths.get(lastaDocHtmlPathList[lastaDocHtmlPathIndex]);
        var lastaDocHtml = Java.type('java.lang.String').join('\n', java.nio.file.Files.readAllLines(lastaDocHtmlPath));
        if (!lastaDocHtml.contains(naviLinkHtml)) {
            lastaDocHtml = lastaDocHtml.replace(markNaviLink, naviLinkHtml + '\n' + markNaviLink);
        }
        if (!lastaDocHtml.contains(naviLinkDestinationHtml)) {
            docHtml = naviLinkDestinationHtml + docHtml;
        }
        java.nio.file.Files.write(lastaDocHtmlPath, lastaDocHtml.replace(markBody, docHtml + '\n' + markBody).getBytes('UTF-8'));
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
