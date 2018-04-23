// Based on ECMAScript5. Because Nashorn of java 8 is ECMAScript5.
// =======================================================================================
//                                                                              Definition
//                                                                              ==========

var baseRule = {

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

    // ===================================================================================
    //                                                                                 Doc
    //                                                                                 ===
    docGeneration : true
};

var kvsRule = {};
for (var i in baseRule) {
    kvsRule[i] = baseRule[i];
}
