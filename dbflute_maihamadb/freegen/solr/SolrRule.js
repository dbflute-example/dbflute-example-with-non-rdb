// Based on ECMAScript5. Because Nashorn of java 8 is ECMAScript5.
// =======================================================================================
//                                                                              Definition
//                                                                              ==========

var baseRule = {
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
    }
};

var solrRule = {};
for (var i in baseRule) {
    solrRule[i] = baseRule[i];
}
