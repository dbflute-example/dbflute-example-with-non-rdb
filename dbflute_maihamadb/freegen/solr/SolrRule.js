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
            return manager.initUncap(request.tableMap.schema);
        }
        return manager.initUncap(request.requestName.replace(/^Solr/g, ''));
    },

    schemaShort: function(request) {
        if (request.tableMap.containsKey('schemaPrefix')) {
            return request.tableMap.schemaPrefix ? request.tableMap.schemaPrefix : '';
        }
        return manager.initCap(this.schema(request));
    },

    // ===================================================================================
    //                                                                                 Doc
    //                                                                                 ===
    docGeneration : true,

    // ===================================================================================
    //                                                                              Option
    //                                                                              ======
    defaultValueMap: function() {
        return {
            'fetchSize': 1000,
            'facetFetchSize': null
        };
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

var solrRule = {};
for (var i in baseRule) {
    solrRule[i] = baseRule[i];
}
