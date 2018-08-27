var genKvsCore = false;
var genKvsStore = false;
var genKvsCache = false;
var genKvsCacheColumnNullObject = false;

/**
 * process.
 * @param {Request} request - request (NotNull)
 */
function process(request) {
    if (!(request.isResourceTypeJsonSchema() && request.requestName.startsWith("Kvs"))) {
		return;
    }
    try {
        request.enableOutputDirectory();
        manager.makeDirectory(request.generateDirPath);
        processKvsCore(request)
        processKvs(request);
    } catch (e) {
        var message = '/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n';
        message += 'Error in kvs generation.\n';
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
 * Process kvs core.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Request} request - request (NotNull)
 */
function processKvsCore(request) {
    if (!genKvsCore) {
	    genKvsCore = true;
	    generate('./kvs/allcommon/core/delegator/AbstractKvsRedisDelegator.vm', 'org/dbflute/kvs/core/delegator/AbstractKvsRedisDelegator.java', null, true);
	    generate('./kvs/allcommon/core/delegator/KvsDelegator.vm', 'org/dbflute/kvs/core/delegator/KvsDelegator.java', null, true);
	    generate('./kvs/allcommon/core/delegator/KvsLocalMapDelegator.vm', 'org/dbflute/kvs/core/delegator/KvsLocalMapDelegator.java', null, true);
	    generate('./kvs/allcommon/core/delegator/KvsRedisPool.vm', 'org/dbflute/kvs/core/delegator/KvsRedisPool.java', null, true);
	    generate('./kvs/allcommon/core/exception/KvsException.vm', 'org/dbflute/kvs/core/exception/KvsException.java', null, true);
	    generate('./kvs/allcommon/core/assertion/KvsAssertion.vm', 'org/dbflute/kvs/core/assertion/KvsAssertion.java', null, true);
    }

    if (request.requestName.startsWith("KvsCache") && !genKvsCache) {
        genKvsCache = true;
        generate('./kvs/allcommon/cache/delegator/KvsCacheRedisDelegator.vm', 'org/dbflute/kvs/cache/delegator/KvsCacheRedisDelegator.java', null, true);
        generate('./kvs/allcommon/cache/facade/KvsCacheFacade.vm', 'org/dbflute/kvs/cache/facade/KvsCacheFacade.java', null, true);
        generate('./kvs/allcommon/cache/facade/AbstractKvsCacheFacade.vm', 'org/dbflute/kvs/cache/facade/AbstractKvsCacheFacade.java', null, true);
        generate('./kvs/allcommon/cache/KvsCacheBusinessAssist.vm', 'org/dbflute/kvs/cache/KvsCacheBusinessAssist.java', null, true);
        generate('./kvs/allcommon/cache/KvsCacheConverterHandler.vm', 'org/dbflute/kvs/cache/KvsCacheConverterHandler.java', null, true);
        generate('./kvs/allcommon/cache/KvsCacheManager.vm', 'org/dbflute/kvs/cache/KvsCacheManager.java', null, true);
        generate('./kvs/allcommon/cache/KvsCacheManagerImpl.vm', 'org/dbflute/kvs/cache/KvsCacheManagerImpl.java', null, true);
        generate('./kvs/allcommon/cache/bhv/AbstractKvsCacheBehaviorWritable.vm', 'org/dbflute/kvs/cache/bhv/AbstractKvsCacheBehaviorWritable.java', null, true);
        generate('./kvs/allcommon/cache/cbean/KvsCacheConditionBean.vm', 'org/dbflute/kvs/cache/cbean/KvsCacheConditionBean.java', null, true);
    }

    if (request.requestName.startsWith("KvsStore") && !genKvsStore) {
        genKvsStore = true;
        generate('./kvs/allcommon/store/delegator/KvsStoreRedisDelegator.vm', 'org/dbflute/kvs/store/delegator/KvsStoreRedisDelegator.java', null, true);
        generate('./kvs/allcommon/store/cbean/KvsStoreConditionBean.vm', 'org/dbflute/kvs/store/cbean/KvsStoreConditionBean.java', null, true);
        generate('./kvs/allcommon/store/entity/dbmeta/KvsStoreDBMeta.vm', 'org/dbflute/kvs/store/entity/dbmeta/KvsStoreDBMeta.java', null, true);
        generate('./kvs/allcommon/store/entity/dbmeta/AbstractKvsStoreDBMeta.vm', 'org/dbflute/kvs/store/entity/dbmeta/AbstractKvsStoreDBMeta.java', null, true);
        generate('./kvs/allcommon/store/entity/KvsStoreEntity.vm', 'org/dbflute/kvs/store/entity/KvsStoreEntity.java', null, true);
        generate('./kvs/allcommon/store/facade/KvsStoreFacade.vm', 'org/dbflute/kvs/store/facade/KvsStoreFacade.java', null, true);
        generate('./kvs/allcommon/store/facade/AbstractKvsStoreFacade.vm', 'org/dbflute/kvs/store/facade/AbstractKvsStoreFacade.java', null, true);
        generate('./kvs/allcommon/store/facade/AbstractKvsStoreHashFacade.vm', 'org/dbflute/kvs/store/facade/AbstractKvsStoreHashFacade.java', null, true);
        generate('./kvs/allcommon/store/KvsStoreConverterHandler.vm', 'org/dbflute/kvs/store/KvsStoreConverterHandler.java', null, true);
        generate('./kvs/allcommon/store/KvsStoreManager.vm', 'org/dbflute/kvs/store/KvsStoreManager.java', null, true);
        generate('./kvs/allcommon/store/KvsStoreManagerImpl.vm', 'org/dbflute/kvs/store/KvsStoreManagerImpl.java', null, true);
    }
}

/**
 * process kvs.
 * @param {Request} request - request (NotNull)
 */
function processKvs(request) {
    var optionMap = request.optionMap;
    scriptEngine.eval('load("./freegen/kvs/KvsRule.js");');
    if (optionMap.ruleJsPath && optionMap.ruleJsPath != '') {
        // load application rule settings if exists
        scriptEngine.eval('load("' + optionMap.ruleJsPath + '");');
    }
    var rule = scriptEngine.get('kvsRule');
    processKvsPool(rule, request);
    processKvsCache(rule, request);
    processKvsStore(rule, request);
}

/**
 * Process kvs pool.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Request} request - request (NotNull)
 */
function processKvsPool(rule, request) {
    if (!request.requestName.startsWith("KvsPool")) {
        return;
    }
    var tableMap = request.tableMap;
    generate('./kvs/KvsRedisPoolFactory.vm', request.generateDirPath + 'KvsRedisPoolFactory.java', null, true);
    for each (table in request.tableList) {
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
 * Process kvs cache.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Request} request - request (NotNull)
 */
function processKvsCache(rule, request) {
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
    kvs.package = request.package + '.' + kvs.schema;
    kvs.kvsCacheFacadeImpl = kvsCacheFacadeImpl;
    kvs.suppressBehaviorGen = tableMap.suppressBehaviorGen === 'true';

    var exConditionQueryList = [];
    var exConditionBeanList = [];
    var exBehaviorList = [];
    if (!kvs.suppressBehaviorGen) {
        for each (table in request.tableList) {
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
    processVm(rule, exConditionQueryList, './kvs/cache/KvsCacheBsCQ.vm', './kvs/cache/KvsCacheExCQ.vm');
    processVm(rule, exConditionBeanList, './kvs/cache/KvsCacheBsCB.vm', './kvs/cache/KvsCacheExCB.vm');
    processVm(rule, exBehaviorList, './kvs/cache/KvsCacheBsBehavior.vm', './kvs/cache/KvsCacheExBehavior.vm');

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

    if (!genKvsCacheColumnNullObject) {
    	genKvsCacheColumnNullObject = true;
        generate('./kvs/allcommon/cache/KvsCacheColumnNullObject.vm', 'org/dbflute/kvs/cache/KvsCacheColumnNullObject.java', null, true);
	}

	var doc = new java.util.LinkedHashMap();
	doc.kvs = kvs;
	doc.exBehaviorList = exBehaviorList;
    processKvsCacheDoc(rule, doc);
}

/**
 * Process kvs store.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Request} request - request (NotNull)
 */
function processKvsStore(rule, request) {
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
	for each (table in request.tableList) {
        var base = new java.util.LinkedHashMap();
        base.tableName = table.capCamelName;
        base.comment = table.comment;
        base.kvs = kvs;
        base.bs = new java.util.LinkedHashMap();
        base.bs.tableName = table.capCamelName;
        base.bs.comment = table.comment;
        base.bs.kvs = kvs;

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
        exEntity.bs.columnList = table.columnList;
        exEntityList.push(exEntity);

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
        exConditionBean.bs.dbMeta = dbMeta;
        exConditionBean.bs.exEntity = exEntity;
        exConditionBean.bs.exConditionQuery = exConditionQuery;
        exConditionBean.bs.exConditionBean = exConditionBean;
        exConditionBean.bs.columnList = table.columnList;
        exConditionBeanList.push(exConditionBean);

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
        
        dbMeta.exEntity = exEntity;
        dbMeta.exConditionBean = exConditionBean;
	}
    processVm(rule, dbMetaList, null, './kvs/store/KvsStoreDBMeta.vm');
    processVm(rule, exEntityList, './kvs/store/KvsStoreBsEntity.vm', './kvs/store/KvsStoreExEntity.vm');
    if (!kvs.suppressBehaviorGen) {
		processVm(rule, exConditionQueryList, './kvs/store/KvsStoreBsCQ.vm', './kvs/store/KvsStoreExCQ.vm');
		processVm(rule, exConditionBeanList, './kvs/store/KvsStoreBsCB.vm', './kvs/store/KvsStoreExCB.vm');
		processVm(rule, exBehaviorList, './kvs/store/KvsStoreBsBehavior.vm', './kvs/store/KvsStoreExBehavior.vm');
		var doc = new java.util.LinkedHashMap();
		doc.kvs = kvs;
		doc.exBehaviorList = exBehaviorList;
	    processKvsStoreDoc(rule, doc);
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
 * Process kvs cache doc.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Doc} doc - doc (NotNull)
 */
function processKvsCacheDoc(rule, doc) {
    if (!rule['docGeneration']) {
        return;
    }
    var kvsCacheDocHtml = generate('./kvs/doc/KvsCacheDocHtml.vm', null, doc, true);
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
            kvsCacheDocHtml = naviLinkDestinationHtml + kvsCacheDocHtml;
        }
        java.nio.file.Files.write(lastaDocHtmlPath, lastaDocHtml.replace(markBody, kvsCacheDocHtml + '\n' + markBody).getBytes('UTF-8'));
    }
}

/**
 * Process kvs store doc.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Doc} doc - doc (NotNull)
 */
function processKvsStoreDoc(rule, doc) {
    if (!rule['docGeneration']) {
        return;
    }
    var kvsStoreDocHtml = generate('./kvs/doc/KvsStoreDocHtml.vm', null, doc, true);
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
            kvsStoreDocHtml = naviLinkDestinationHtml + kvsStoreDocHtml;
        }
        java.nio.file.Files.write(lastaDocHtmlPath, lastaDocHtml.replace(markBody, kvsStoreDocHtml + '\n' + markBody).getBytes());
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
