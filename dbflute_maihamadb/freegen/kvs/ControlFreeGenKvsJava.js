/**
 * process.
 * @param {Request} request - request (NotNull)
 */
function process(request) {
    try {
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
 * process kvs.
 * @param {Request} request - request (NotNull)
 */
function processKvs(request) {
    var optionMap = request.optionMap;
    request.enableOutputDirectory();
    manager.makeDirectory(request.generateDirPath);

    scriptEngine.eval('load("./freegen/kvs/KvsRule.js");');
    if (optionMap.ruleJsPath && optionMap.ruleJsPath != '') {
        // load application rule settings if exists
        scriptEngine.eval('load("' + optionMap.ruleJsPath + '");');
    }
    var rule = scriptEngine.get('kvsRule');

    processKvsDoc(rule, request);
}

/**
 * Process kvs doc.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Request} request - request (NotNull)
 */
function processKvsDoc(rule, request) {
    if (!rule['docGeneration']) {
        return;
    }
    processKvsStoreDoc(rule, request);
    processKvsCacheDoc(rule, request);
}

/**
 * Process kvs store doc.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Request} request - request (NotNull)
 */
function processKvsStoreDoc(rule, request) {
    if (!request.requestName.startsWith('KvsStore')) {
        return;
    }
    var doc = new java.util.LinkedHashMap();
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

/**
 * Process kvs cache doc.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Request} request - request (NotNull)
 */
function processKvsCacheDoc(rule, request) {
    if (!request.requestName.startsWith('KvsCache')) {
        return;
    }
    var doc = new java.util.LinkedHashMap();
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
        java.nio.file.Files.write(lastaDocHtmlPath, lastaDocHtml.replace(markBody, kvsCacheDocHtml + '\n' + markBody).getBytes());
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
