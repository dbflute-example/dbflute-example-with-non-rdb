/**
 * process.
 * @param {Request} request - request (NotNull)
 */
function process(request) {
    try {
        processSolr(request);
    } catch (e) {
        var message = '/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n';
        message += 'Error in solr generation.\n';
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
 * process solr.
 * @param {Request} request - request (NotNull)
 */
function processSolr(request) {
    var optionMap = request.optionMap;
    request.enableOutputDirectory();
    manager.makeDirectory(request.generateDirPath);

    scriptEngine.eval('load("./freegen/solr/SolrRule.js");');
    if (optionMap.ruleJsPath && optionMap.ruleJsPath != '') {
        // load application rule settings if exists
        scriptEngine.eval('load("' + optionMap.ruleJsPath + '");');
    }
    var rule = scriptEngine.get('solrRule');

    processSolrDoc(rule, request);
}

/**
 * Process solr doc.
 * @param {Rule} rule - rule. (NotNull)
 * @param {Request} request - request (NotNull)
 */
function processSolrDoc(rule, request) {
    if (!rule['docGeneration']) {
        return;
    }
    var doc = new java.util.LinkedHashMap();
    var solrDocHtml = generate('./solr/doc/SolrDocHtml.vm', null, doc, true);
    var lastaDocHtmlPathList = manager.getLastaDocHtmlPathList();
    var markNaviLink = manager.getLastaDocHtmlMarkFreeGenDocNaviLink();
    var markBody = manager.getLastaDocHtmlMarkFreeGenDocBody();
    var naviLinkHtml = '    | <a href="#solr">to solr</a>';
    var naviLinkDestinationHtml = '<span id="solr"></span>';
    for (var lastaDocHtmlPathIndex in lastaDocHtmlPathList) {
        var lastaDocHtmlPath = java.nio.file.Paths.get(lastaDocHtmlPathList[lastaDocHtmlPathIndex]);
        var lastaDocHtml = Java.type('java.lang.String').join('\n', java.nio.file.Files.readAllLines(lastaDocHtmlPath));
        if (!lastaDocHtml.contains(naviLinkHtml)) {
            lastaDocHtml = lastaDocHtml.replace(markNaviLink, naviLinkHtml + '\n' + markNaviLink);
        }
        if (!lastaDocHtml.contains(naviLinkDestinationHtml)) {
            solrDocHtml = naviLinkDestinationHtml + solrDocHtml;
        }
        java.nio.file.Files.write(lastaDocHtmlPath, lastaDocHtml.replace(markBody, solrDocHtml + '\n' + markBody).getBytes());
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
