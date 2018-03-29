// Based on ECMAScript5. Because Nashorn of java 8 is ECMAScript5.
// =======================================================================================
//                                                                              Definition
//                                                                              ==========

var baseRule = {
    // ===================================================================================
    //                                                                                 Doc
    //                                                                                 ===
    docGeneration : true,
};

var kvsRule = {};
for (var i in baseRule) {
    kvsRule[i] = baseRule[i];
}
