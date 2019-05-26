// Based on ECMAScript5. Because Nashorn of java 8 is ECMAScript5.
// =======================================================================================
//                                                                              Definition
//                                                                              ==========

var baseRule = {

    // ===================================================================================
    //                                                                               Base
    //                                                                              ======
    schema: function(request) {
        if (request.tableMap.schema !== null) {
            return request.tableMap.schema;
        }
        return manager.initUncap(request.requestName.replace(/^Kvs(Cache|Store)/g, ''));
    },

    schemaShort: function(request) {
        if (request.tableMap.containsKey('schemaPrefix')) {
            return request.tableMap.schemaPrefix ? request.tableMap.schemaPrefix : '';
        }
        return manager.initCap(this.schema(request));
    },

    kvsPoolDiFile: function(request) {
        if (request.tableMap.kvsPoolDiFile) {
            return request.tableMap.kvsPoolDiFile;
        }
        throw new Error('override kvsPoolDiFile(request)');
    },

    // ===================================================================================
    //                                                                               Cache
    //                                                                               =====

    // ===================================================================================
    //                                                                                 Doc
    //                                                                                 ===
    docGeneration : true,

    // ===================================================================================
    //                                                                              Option
    //                                                                              ======
    /**
     * Return java import order list.
     * @return java import order list. (NotNull)
     */
    importOrderList: function() {
        return ['java', 'javax', 'junit', 'org', 'com', 'net', 'ognl', 'mockit', 'jp'];
    },

    /**
     * Return converter handler.
     * @return converter handler. (NotNull)
     */
    converterHandler: function() {
        // MapListString Instantiate everytime because of the statefulness of...
        return {
            'toMapString': 'new org.dbflute.helper.dfmap.DfMapStyle().toMapString(map)',
            'fromMapString': 'new org.dbflute.helper.dfmap.DfMapStyle().fromMapString(mapString)'
        };
    },

    /** true for cluster. */
    cluster : false,

    /**
     * Return kvs cache async reflection enabled.
     * @return kvs cache async reflection enabled (NotNull)
     */
    kvsCacheAsyncReflectionEnabled: function () {
        return true;
    },

    /**
     * Return delete target.
     * @param {Request} request - rquest. (NullAllowed)
     * @return {File} file. (NotNull)
     * @return {boolean} delete target. (NotNull)
     */
    deleteTarget: function(request, file) {
        return true;
    }
};

var kvsRule = {};
for (var i in baseRule) {
    kvsRule[i] = baseRule[i];
}
