var genType = 'kvs';
var genCore = false;
var genCoreStore = false;
var genCoreCache = false;
var genCoreCacheColumnNullObject = false;
var srcPathList = [];

/**
 * process.
 * @param {Request[]} requestList - requestList (NotNull)
 */
function process(requestList) {
    for each (var request in requestList) {
        if (!(request.isResourceTypeJsonSchema() && request.requestName.startsWith("Kvs"))) {
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
    clean(null, null, 'org/dbflute/' + genType , srcPathList);
    clean(null, null, '../resources/' + genType + '/di', srcPathList);
}

/**
 * process core.
 * @param {Request} request - request (NotNull)
 */
function processCore(request) {
    var rule = scriptEngine.get(genType + 'Rule');
    var kvs = new java.util.LinkedHashMap();
    kvs.kvsCacheAsyncReflectionEnabled = scriptEngine.invokeMethod(rule, 'kvsCacheAsyncReflectionEnabled', null);
    var coreVmList = [];
    if (!genCore) {
        genCore = true;
        coreVmList.push('core/assertion/KvsAssertion.vm');
        coreVmList.push('core/exception/KvsException.vm');
        coreVmList.push('core/delegator/KvsDelegator.vm');
        if (rule['cluster']) {
	        coreVmList.push('core/delegator/cluster/AbstractKvsRedisDelegator.vm');
	        coreVmList.push('core/delegator/cluster/KvsRedisPool.vm');
	    } else {
	        coreVmList.push('core/delegator/AbstractKvsRedisDelegator.vm');
	        coreVmList.push('core/delegator/KvsRedisPool.vm');
	    }
        coreVmList.push('core/delegator/KvsLocalMapDelegator.vm');
    }

    if (request.requestName.startsWith("KvsCache") && !genCoreCache) {
        genCoreCache = true;
        coreVmList.push('cache/delegator/KvsCacheRedisDelegator.vm');
        coreVmList.push('cache/facade/KvsCacheFacade.vm');
        coreVmList.push('cache/facade/AbstractKvsCacheFacade.vm');
        coreVmList.push('cache/KvsCacheBusinessAssist.vm');
        coreVmList.push('cache/KvsCacheConverterHandler.vm');
        coreVmList.push('cache/KvsCacheManager.vm');
        coreVmList.push('cache/KvsCacheManagerImpl.vm');
        coreVmList.push('cache/bhv/AbstractKvsCacheBehaviorWritable.vm');
        coreVmList.push('cache/bhv/writable/DeleteOption.vm');
        coreVmList.push('cache/bhv/writable/InsertOrUpdateOption.vm');
        coreVmList.push('cache/bhv/writable/WritableOption.vm');
        coreVmList.push('cache/cbean/KvsCacheConditionBean.vm');
    }

    if (request.requestName.startsWith("KvsStore") && !genCoreStore) {
        genCoreStore = true;
        coreVmList.push('store/delegator/KvsStoreRedisDelegator.vm');
        coreVmList.push('store/cbean/KvsStoreConditionBean.vm');
        coreVmList.push('store/entity/dbmeta/KvsStoreDBMeta.vm');
        coreVmList.push('store/entity/dbmeta/AbstractKvsStoreDBMeta.vm');
        coreVmList.push('store/entity/KvsStoreEntity.vm');
        coreVmList.push('store/facade/KvsStoreFacade.vm');
        coreVmList.push('store/facade/AbstractKvsStoreFacade.vm');
        coreVmList.push('store/facade/AbstractKvsStoreHashFacade.vm');
        coreVmList.push('store/KvsStoreConverterHandler.vm');
        coreVmList.push('store/KvsStoreManager.vm');
        coreVmList.push('store/KvsStoreManagerImpl.vm');
    }
    for each (var coreVm in coreVmList) {
        generate('./' + genType + '/allcommon/' + coreVm, 'org/dbflute/' + genType + '/' + coreVm.replaceAll('/cluster/', '/').replaceAll('\.vm$', '.java'), kvs, true);
    }
}

/**
 * process hull.
 * @param {Request} request - request (NotNull)
 */
function processHull(request) {
    var rule = scriptEngine.get(genType + 'Rule');
    processPool(rule, request);
    processCache(rule, request);
    processStore(rule, request);
    var package = request.package + '.' + scriptEngine.invokeMethod(rule, 'schema', request);
    clean(rule, request, package.replace(/\./g, '/'), srcPathList);
}

/**
 * Process pool.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Request} request - request (NotNull)
 */
function processPool(rule, request) {
    if (!request.requestName.startsWith("KvsPool")) {
        return;
    }
    var tableMap = request.tableMap;
    generate('./kvs/KvsRedisPoolFactory.vm', request.generateDirPath + 'KvsRedisPoolFactory.java', request, true);
    for each (var table in request.tableList) {
        var di = new java.util.LinkedHashMap();
        di.name = table.uncapCamelName;
        di.package = request.package;
        if (manager.isTargetContainerSeasar()) {
            generate('./kvs/allcommon/container/seasar/KvsPoolDicon.vm', '../resources/kvs/di/kvs-pool-' + di.name + '.dicon', di, true);
        }
        if (manager.isTargetContainerLastaDi()) {
            generate('./kvs/allcommon/container/lastadi/KvsPoolDiXml.vm', '../resources/kvs/di/kvs-pool-' + di.name + '.xml', di, true);
        }
    }
}

/**
 * Process cache.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Request} request - request (NotNull)
 */
function processCache(rule, request) {
    if (!request.requestName.startsWith('KvsCache')) {
        return;
    }
    var tableMap = request.tableMap;
    var kvsCacheFacadeImpl = new java.util.LinkedHashMap();
    kvsCacheFacadeImpl.package = request.package + '.' + tableMap.schema + '.facade';
    kvsCacheFacadeImpl.className = manager.initCap(tableMap.schema) + 'KvsCacheFacade';
    generate('./kvs/cache/KvsCacheFacadeImpl.vm', request.generateDirPath + tableMap.schema + '/facade/' + kvsCacheFacadeImpl.className + '.java', kvsCacheFacadeImpl, true);

    var kvs = new java.util.LinkedHashMap();
    kvs.schema = scriptEngine.invokeMethod(rule, 'schema', request);
    kvs.schemaShort = scriptEngine.invokeMethod(rule, 'schemaShort', request);
    kvs.kvsCacheAsyncReflectionEnabled = scriptEngine.invokeMethod(rule, 'kvsCacheAsyncReflectionEnabled', null);
    kvs.package = request.package + '.' + kvs.schema;
    kvs.kvsCacheFacadeImpl = kvsCacheFacadeImpl;
    kvs.suppressBehaviorGen = tableMap.suppressBehaviorGen === 'true';

    var exConditionQueryList = [];
    var exConditionBeanList = [];
    var exBehaviorList = [];
    if (!kvs.suppressBehaviorGen) {
        for each (var table in request.tableList) {
            var base = new java.util.LinkedHashMap();
            base.tableName = table.capCamelName;
            base.comment = table.comment;
            base.kvs = kvs;
            base.bs = new java.util.LinkedHashMap();
            base.bs.tableName = table.capCamelName;
            base.bs.comment = table.comment;
            base.bs.kvs = kvs;
            base.bs.dbflute = new java.util.LinkedHashMap();
            base.bs.dbflute.dbMeta = new java.util.LinkedHashMap();
            base.bs.dbflute.dbMeta.package = tableMap.dbflutePackage + '.bsentity.dbmeta';
            base.bs.dbflute.dbMeta.className = kvs.schemaShort + table.camelizedName + 'Dbm';
            base.bs.dbflute.exConditionBean = new java.util.LinkedHashMap();
            base.bs.dbflute.exConditionBean.package = tableMap.dbflutePackage + '.cbean';
            base.bs.dbflute.exConditionBean.className = kvs.schemaShort + table.camelizedName + 'CB';
            base.bs.dbflute.exEntity = new java.util.LinkedHashMap();
            base.bs.dbflute.exEntity.package = tableMap.dbflutePackage + '.exentity';
            base.bs.dbflute.exEntity.className = kvs.schemaShort + table.camelizedName;
            base.bs.dbflute.instance = tableMap.databaseMap[kvs.schema].instance;

            var exConditionQuery = new java.util.LinkedHashMap(base);
            subPackage = 'cbean.cq';
            exConditionQuery.package = subPackage ? kvs.package + '.' + subPackage : kvs.package;
            exConditionQuery.className = 'Kvs' + kvs.schemaShort + table.camelizedName + 'CQ';
            exConditionQuery.bs = new java.util.LinkedHashMap(base.bs);
            subPackage = 'cbean.cq.bs';
            exConditionQuery.bs.package = subPackage ? kvs.package + '.' + subPackage : kvs.package;
            exConditionQuery.bs.className = 'Kvs' + kvs.schemaShort + 'Bs' + table.camelizedName + 'CQ';
            exConditionQuery.bs.extendsClass = '';
            exConditionQuery.bs.implementsClasses = '';
            exConditionQuery.bs.columnList = table.columnList;
            exConditionQueryList.push(exConditionQuery);

            var exConditionBean = new java.util.LinkedHashMap(base);
            subPackage = 'cbean';
            exConditionBean.package = subPackage ? kvs.package + '.' + subPackage : kvs.package;
            exConditionBean.className = 'Kvs' + kvs.schemaShort + table.camelizedName + 'CB';
            exConditionBean.bs = new java.util.LinkedHashMap(base.bs);
            subPackage = 'cbean.bs';
            exConditionBean.bs.package = subPackage ? kvs.package + '.' + subPackage : kvs.package;
            exConditionBean.bs.className = 'Kvs' + kvs.schemaShort + 'Bs' + table.camelizedName + 'CB';
            exConditionBean.bs.extendsClass = '';
            exConditionBean.bs.implementsClasses = '';
            exConditionBean.bs.exConditionQuery = exConditionQuery;
            exConditionBeanList.push(exConditionBean);

            var exBehavior = new java.util.LinkedHashMap(base);
            subPackage = 'exbhv';
            exBehavior.package = subPackage ? kvs.package + '.' + subPackage : kvs.package;
            exBehavior.className = 'Kvs' + kvs.schemaShort + table.camelizedName + 'Bhv';
            exBehavior.bs = new java.util.LinkedHashMap(base.bs);
            subPackage = 'bsbhv';
            exBehavior.bs.package = subPackage ? kvs.package + '.' + subPackage : kvs.package;
            exBehavior.bs.className = 'Kvs' + kvs.schemaShort + 'Bs' + table.camelizedName + 'Bhv';
            exBehavior.bs.extendsClassName = 'AbstractKvsCacheBehaviorWritable<' + base.bs.dbflute.exEntity.className + ', ' + base.bs.dbflute.exConditionBean.className + ', ' + exConditionBean.className + '>';
            exBehavior.bs.extendsClass = 'org.dbflute.kvs.cache.bhv.AbstractKvsCacheBehaviorWritable';
            exBehavior.bs.implementsClasses = '';
            exBehavior.bs.subClassName = exBehavior.className;
            exBehavior.bs.exConditionBean = exConditionBean;
            exBehavior.bs.suppressBehaviorBasicMethodGen = tableMap.suppressBehaviorBasicMethodGen === 'true';
            exBehavior.bs.columnList = table.columnList;
            exBehaviorList.push(exBehavior);
        }
    }
    processVm(exConditionQueryList, './kvs/cache/KvsCacheBsCQ.vm', './kvs/cache/KvsCacheExCQ.vm');
    processVm(exConditionBeanList, './kvs/cache/KvsCacheBsCB.vm', './kvs/cache/KvsCacheExCB.vm');
    processVm(exBehaviorList, './kvs/cache/KvsCacheBsBehavior.vm', './kvs/cache/KvsCacheExBehavior.vm');

    var di = new java.util.LinkedHashMap(base);
    di.kvs = kvs;
    di.kvsPoolDiFile = tableMap.kvsPoolDiFile;
    di.dbfluteDiFile = tableMap.dbfluteDiFile;
    di.exBehaviorList = exBehaviorList;
    if (manager.isTargetContainerSeasar()) {
        generate('./kvs/allcommon/container/seasar/KvsCacheDicon.vm', '../resources/kvs/di/kvs-cache-' + tableMap.schema + '.dicon', di, true);
    }
    if (manager.isTargetContainerLastaDi()) {
        generate('./kvs/allcommon/container/lastadi/KvsCacheDiXml.vm', '../resources/kvs/di/kvs-cache-' + tableMap.schema + '.xml', di, true);
    }

    if (!genCoreCacheColumnNullObject) {
        genCoreCacheColumnNullObject = true;
        generate('./kvs/allcommon/cache/KvsCacheColumnNullObject.vm', 'org/dbflute/kvs/cache/KvsCacheColumnNullObject.java', null, true);
    }

    var doc = new java.util.LinkedHashMap();
    doc.kvs = kvs;
    doc.exBehaviorList = exBehaviorList;
    processCacheDoc(rule, doc);
}

/**
 * Process store.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Request} request - request (NotNull)
 */
function processStore(rule, request) {
    if (!request.requestName.startsWith('KvsStore')) {
        return;
    }
    var tableMap = request.tableMap;
    var kvsStoreFacadeImpl = new java.util.LinkedHashMap();
    kvsStoreFacadeImpl.package = request.package + '.' + tableMap.schema + '.facade';
    kvsStoreFacadeImpl.className = manager.initCap(tableMap.schema) + 'KvsStoreFacade';
    kvsStoreFacadeImpl.extendsClass = tableMap.type === 'hash' ? 'org.dbflute.kvs.store.facade.AbstractKvsStoreHashFacade' : 'org.dbflute.kvs.store.facade.AbstractKvsStoreFacade';
    generate('./kvs/store/KvsStoreFacadeImpl.vm', request.generateDirPath + tableMap.schema + '/facade/' + kvsStoreFacadeImpl.className + '.java', kvsStoreFacadeImpl, true);

    var kvs = new java.util.LinkedHashMap();
    kvs.schema = scriptEngine.invokeMethod(rule, 'schema', request);
    kvs.schemaShort = scriptEngine.invokeMethod(rule, 'schemaShort', request);
    kvs.package = request.package + '.' + kvs.schema;
    kvs.kvsStoreFacadeImpl = kvsStoreFacadeImpl;
    kvs.suppressBehaviorGen = tableMap.suppressBehaviorGen === 'true';

    var dbMetaList = [];
    var exEntityList = [];
    var exConditionQueryList = [];
    var exConditionBeanList = [];
    var exBehaviorList = [];
    for each (var table in request.tableList) {
        var variableKvs = new java.util.LinkedHashMap(kvs);
        if (table.suppressBehaviorGen === true) {
            variableKvs.suppressBehaviorGen = true;
        }

        var base = new java.util.LinkedHashMap();
        base.tableName = table.capCamelName;
        base.comment = table.comment;
        base.kvs = variableKvs;
        base.bs = new java.util.LinkedHashMap();
        base.bs.tableName = table.capCamelName;
        base.bs.comment = table.comment;
        base.bs.kvs = variableKvs;

        var dbMeta = new java.util.LinkedHashMap(base);
        subPackage = 'bsentity.dbmeta';
        dbMeta.package = subPackage ? kvs.package + '.' + subPackage : kvs.package;
        dbMeta.className = 'Kvs' + kvs.schemaShort + table.camelizedName + 'Dbm';
        dbMeta.columnList = table.columnList;
        dbMetaList.push(dbMeta);

        var exEntity = new java.util.LinkedHashMap(base);
        subPackage = 'exentity';
        exEntity.package = subPackage ? kvs.package + '.' + subPackage : kvs.package;
        exEntity.className = 'Kvs' + kvs.schemaShort + table.camelizedName;
        exEntity.bs = new java.util.LinkedHashMap(base.bs);
        subPackage = 'bsentity';
        exEntity.bs.package = subPackage ? kvs.package + '.' + subPackage : kvs.package;
        exEntity.bs.className = 'Kvs' + kvs.schemaShort + 'Bs' + table.camelizedName;
        exEntity.bs.extendsClass = '';
        exEntity.bs.implementsClasses = '';
        exEntity.bs.dbMeta = dbMeta;
        exEntity.bs.exEntity = exEntity;
        exEntity.bs.columnList = table.columnList;
        exEntityList.push(exEntity);
        dbMeta.exEntity = exEntity;

        if (!variableKvs.suppressBehaviorGen) {
            var exConditionQuery = new java.util.LinkedHashMap(base);
            subPackage = 'cbean.cq';
            exConditionQuery.package = subPackage ? kvs.package + '.' + subPackage : kvs.package;
            exConditionQuery.className = 'Kvs' + kvs.schemaShort + table.camelizedName + 'CQ';
            exConditionQuery.bs = new java.util.LinkedHashMap(base.bs);
            subPackage = 'cbean.cq.bs';
            exConditionQuery.bs.package = subPackage ? kvs.package + '.' + subPackage : kvs.package;
            exConditionQuery.bs.className = 'Kvs' + kvs.schemaShort + 'Bs' + table.camelizedName + 'CQ';
            exConditionQuery.bs.extendsClass = '';
            exConditionQuery.bs.implementsClasses = '';
            exConditionQuery.bs.columnList = table.columnList;
            exConditionQuery.bs.compoundKey = new java.util.LinkedHashMap();
            exConditionQuery.bs.compoundKey.className = 'Kvs' + kvs.schemaShort + table.camelizedName + 'CompoundKey';
            exConditionQueryList.push(exConditionQuery);

            dbMeta.exConditionQuery = exConditionQuery;

            var exConditionBean = new java.util.LinkedHashMap(base);
            subPackage = 'cbean';
            exConditionBean.package = subPackage ? kvs.package + '.' + subPackage : kvs.package;
            exConditionBean.className = 'Kvs' + kvs.schemaShort + table.camelizedName + 'CB';
            exConditionBean.bs = new java.util.LinkedHashMap(base.bs);
            subPackage = 'cbean.bs';
            exConditionBean.bs.package = subPackage ? kvs.package + '.' + subPackage : kvs.package;
            exConditionBean.bs.className = 'Kvs' + kvs.schemaShort + 'Bs' + table.camelizedName + 'CB';
            exConditionBean.bs.extendsClass = '';
            exConditionBean.bs.implementsClasses = '';
            exConditionBean.bs.dbMeta = dbMeta;
            exConditionBean.bs.exEntity = exEntity;
            exConditionBean.bs.exConditionQuery = exConditionQuery;
            exConditionBean.bs.exConditionBean = exConditionBean;
            exConditionBean.bs.columnList = table.columnList;
            exConditionBeanList.push(exConditionBean);
            dbMeta.exConditionBean = exConditionBean;

            var exBehavior = new java.util.LinkedHashMap(base);
            subPackage = 'exbhv';
            exBehavior.package = subPackage ? kvs.package + '.' + subPackage : kvs.package;
            exBehavior.className = 'Kvs' + kvs.schemaShort + table.camelizedName + 'Bhv';
            exBehavior.bs = new java.util.LinkedHashMap(base.bs);
            subPackage = 'bsbhv';
            exBehavior.bs.package = subPackage ? kvs.package + '.' + subPackage : kvs.package;
            exBehavior.bs.className = 'Kvs' + kvs.schemaShort + 'Bs' + table.camelizedName + 'Bhv';
            exBehavior.bs.extendsClassName = 'AbstractKvsStoreBehaviorWritable';
            exBehavior.bs.extendsClass = 'org.dbflute.kvs.store.bhv.AbstractKvsStoreBehaviorWritable';
            exBehavior.bs.implementsClasses = '';
            exBehavior.bs.subClassName = exBehavior.className;
            exBehavior.bs.dbMeta = dbMeta;
            exBehavior.bs.exEntity = exEntity;
            exBehavior.bs.exConditionBean = exConditionBean;
            exBehavior.bs.suppressBehaviorBasicMethodGen = tableMap.suppressBehaviorBasicMethodGen === 'true';
            exBehavior.bs.columnList = table.columnList;
            exBehavior.bs.many = table.many;
            exBehavior.bs.ttl = table.ttl;
            exBehaviorList.push(exBehavior);
        }
    }
    for each (var table in request.tableList) {
        for each (var column in table.columnList) {
            column.isEntity = column.type && column.type.contains('@');
            if (column.isEntity) {
                column.type = column.type.replace(/@.*?@/g, function(str) {
                    var type = 'Kvs' + kvs.schemaShort + manager.camelize(str.substring(1, str.length - 1));
                    for each (var dbMeta in dbMetaList) {
                        if (type + 'Dbm' == dbMeta.className) {
                            column.dbMeta = dbMeta;
                        }
                    }
                    return exEntity.package + '.' + type;
                });
            }
        }
    }
    processVm(dbMetaList, null, './kvs/store/KvsStoreDBMeta.vm');
    processVm(exEntityList, './kvs/store/KvsStoreBsEntity.vm', './kvs/store/KvsStoreExEntity.vm');
    if (!kvs.suppressBehaviorGen) {
        processVm(exConditionQueryList, './kvs/store/KvsStoreBsCQ.vm', './kvs/store/KvsStoreExCQ.vm');
        processVm(exConditionBeanList, './kvs/store/KvsStoreBsCB.vm', './kvs/store/KvsStoreExCB.vm');
        processVm(exBehaviorList, './kvs/store/KvsStoreBsBehavior.vm', './kvs/store/KvsStoreExBehavior.vm');
        var doc = new java.util.LinkedHashMap();
        doc.kvs = kvs;
        doc.exBehaviorList = exBehaviorList;
        processStoreDoc(rule, doc);
    }

    var di = new java.util.LinkedHashMap(base);
    di.kvs = kvs;
    di.kvsPoolDiFile = tableMap.kvsPoolDiFile;
    di.exBehaviorList = exBehaviorList;
    if (manager.isTargetContainerSeasar()) {
        generate('./kvs/allcommon/container/seasar/KvsStoreDicon.vm', '../resources/kvs/di/kvs-store-' + kvs.schema + '.dicon', di, true);
    }
    if (manager.isTargetContainerLastaDi()) {
        generate('./kvs/allcommon/container/lastadi/KvsStoreDiXml.vm', '../resources/kvs/di/kvs-store-' + kvs.schema + '.xml', di, true);
    }
}

/**
 * Process cache doc.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Doc} doc - doc (NotNull)
 */
function processCacheDoc(rule, doc) {
    if (!rule['docGeneration']) {
        return;
    }
    var docHtml = generate('./kvs/doc/KvsCacheDocHtml.vm', null, doc, true);
    var lastaDocHtmlPathList = manager.getLastaDocHtmlPathList();
    var markNaviLink = manager.getLastaDocHtmlMarkFreeGenDocNaviLink();
    var markBody = manager.getLastaDocHtmlMarkFreeGenDocBody();
    var naviLinkHtml = '    | <a href="#kvscache">to kvscache</a>';
    var naviLinkDestinationHtml = '<span id="kvscache"></span>';
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

/**
 * Process store doc.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Doc} doc - doc (NotNull)
 */
function processStoreDoc(rule, doc) {
    if (!rule['docGeneration']) {
        return;
    }
    var docHtml = generate('./kvs/doc/KvsStoreDocHtml.vm', null, doc, true);
    var lastaDocHtmlPathList = manager.getLastaDocHtmlPathList();
    var markNaviLink = manager.getLastaDocHtmlMarkFreeGenDocNaviLink();
    var markBody = manager.getLastaDocHtmlMarkFreeGenDocBody();
    var naviLinkHtml = '    | <a href="#kvsstore">to kvsstore</a>';
    var naviLinkDestinationHtml = '<span id="kvsstore"></span>';
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
