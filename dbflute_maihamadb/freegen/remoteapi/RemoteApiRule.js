// Based on ECMAScript5. Because Nashorn of java 8 is ECMAScript5.
// ===================================================================================
//                                                                          Definition
//                                                                          ==========
/**
 * Request Type.
 * @typedef {Object} Request
 */

/**
 * API Type.
 * @typedef {Object} Api
 * @property {string} api.scheme - Scheme.
 * @property {string} api.url - URL.
 * @property {string} api.httpMethod - HttpMethod.
 * @property {string[]} api.consumes - Consumes.
 * @property {string[]} api.produces - Produces.
 */

/**
 * PathVariable Type.
 * @typedef {Object} PathVariable
 * @property {string} pathVariable.name - Name.
 * @property {string} pathVariable.in - In.
 * @property {string} pathVariable.required - Required.(optional)
 * @property {string} pathVariable.description - Description.(optional)
 * @property {string} pathVariable.type - Type.(optional)
 * @property {string} pathVariable.format - Format.(optional)
 * @property {string} pathVariable.default - Default.(optional)
 * @property {Items} pathVariable.items - Items.(optional)
 * @property {Schema} pathVariable.schema - Schema.(optional)
 * @property {string} pathVariable.enum - Enum.(optional)
 */

/**
 * Property Type.
 * @typedef {Object} Property
 * @property {string} property.name - Name.
 * @property {string} property.required - Required.(optional)
 * @property {string} property.description - Description.(optional)
 * @property {string} property.type - Type.(optional)
 * @property {string} property.format - Format.(optional)
 * @property {string} property.default - Default.(optional)
 * @property {Items} property.items - Items.(optional)
 * @property {Schema} property.schema - Schema.(optional)
 * @property {string} property.enum - Enum.(optional)
 */

var baseRule = {

    // ===================================================================================
    //                                                                               Base
    //                                                                              ======
    /**
     * Return scheme.
     * @param {Request} request - Request.
     * @return {string} scheme.
     */
    scheme : function(request) {
        return request.requestName.replace(/^RemoteApi/g, '');
    },

    /**
     * Return scheme package.
     * @param {Api} api - API.
     * @return {string} scheme package.
     */
    schemePackage : function(scheme) {
        return manager.decamelize(scheme).replace(/_/g, '.').toLowerCase();
    },

    /**
     * Return true if target.
     * @param {Api} api - API.
     * @return {boolean} true if target.
     */
    target : function(api) {
        var contentTypes = [];
        Array.prototype.push.apply(contentTypes, api.consumes ? api.consumes : []);
        Array.prototype.push.apply(contentTypes, api.produces ? api.produces : []);
        return (contentTypes.indexOf('application/json') != -1) && api.url.indexOf('/swagger/json') != 0;
    },

    /**
     * Return filtered URL.
     * @param {Api} api - API.
     * @return {boolean} filtered URL.
     */
    url : function(api) { return api.url; },

    /**
     * Return sub package.
     * @param {Api} api - API.
     * @return {string} sub package.
     */
    subPackage : function(api) {
        return api.url.replace(/(_|^\/|\/$)/g, '').replace(/\/\{.*?\}/g, '').replace(/\//g, '.').toLowerCase();
    },

    // ===================================================================================
    //                                                                               DiXml
    //                                                                               =====
    diXmlPath : function(scheme, resourceFilePath) {
        return '../resources/remoteapi/di/remoteapi_' + this.schemePackage(scheme).replace(/\./g, '-') + '.xml';
    },

    diconPath : function(scheme, resourceFilePath) {
        return '../resources/remoteapi/di/remoteapi_' + this.schemePackage(scheme).replace(/\./g, '-') + '.dicon';
    },

    // ===================================================================================
    //                                                                            Behavior
    //                                                                            ========
    behaviorClassGeneration : true,
    behaviorMethodGeneration : true,
    behaviorMethodAccessModifier : 'public',
    frameworkBehaviorClass : 'org.lastaflute.remoteapi.LastaRemoteBehavior',

    /**
     * Return abstractBehaviorClassName.
     * @param {string} scheme - scheme.
     * @return {string} abstractBehaviorClassName.
     */
    abstractBehaviorClassName : function(scheme) {
        return 'AbstractRemote' + scheme + 'Bhv';
    },

    /**
     * Return filtered Behavior SubPackage.
     * @param {Api} api - API.
     * @return {string} filtered Behavior SubPackage.
     */
    behaviorSubPackage : function(api) {
        return this.subPackage(api).replace(/^([^.]*)\.(.+)/, '$1');
    },

    /**
     * Return bsBehaviorClassName.
     * @param {Api} api - API.
     * @return {string} bsBehaviorClassName.
     */
    bsBehaviorClassName : function(api) {
        return 'BsRemote' + api.scheme + manager.initCap(manager.camelize(this.behaviorSubPackage(api).replace(/\./g, '_'))) + 'Bhv';
    },

    /**
     * Return exBehaviorClassName.
     * @param {Api} api - API.
     * @return {string} exBehaviorClassName.
     */
    exBehaviorClassName : function(api) {
        return 'Remote' + api.scheme + manager.initCap(manager.camelize(this.behaviorSubPackage(api).replace(/\./g, '_'))) + 'Bhv';
    },

    /**
     * Return behaviorRequestMethodName.
     * @param {Api} api - API.
     * @return {string} behaviorRequestMethodName.
     */
    behaviorRequestMethodName : function(api) {
        var methodPart = manager.camelize(this.subPackage(api).replace(this.behaviorSubPackage(api), '').replace(/\./g, '_'));
        return 'request' + manager.initCap(methodPart) + (api.multipleHttpMethod ? manager.initCap(api.httpMethod) : '');
    },

    /**
     * Return behaviorRuleMethodName.
     * @param {Api} api - API.
     * @return {string} behaviorRuleMethodName.
     */
    behaviorRuleMethodName : function(api) {
        var methodPart = manager.camelize(this.subPackage(api).replace(this.behaviorSubPackage(api), '').replace(/\./g, '_'));
        return 'ruleOf' + manager.initCap(methodPart) + (api.multipleHttpMethod ? manager.initCap(api.httpMethod) : '');
    },

    // ===================================================================================
    //                                                                        Param/Return
    //                                                                        ============
    /**
     * Return filtered Bean SubPackage.
     * @param {Api} api - API.
     * @return {string} filtered Bean SubPackage.
     */
    beanSubPackage : function(api) {
        var package = this.subPackage(api);
        if (package === this.behaviorSubPackage(api)) {
            package += '.index';
        }
        return package;
    },
    definitionKey : function(definitionKey) { return definitionKey; },
    unDefinitionKey : function(definitionKey) { return definitionKey; },

    /**
     * Return beanClassName.
     * @param {Api} api - API.
     * @param {boolean} detail - detail.
     * @return {string} beanClassName.
     */
    beanClassName : function(api, detail) {
        var namePart = detail ? api.url.replace(/(_|^\/|\/$|\{|\})/g, '').replace(/\//g, '_').toLowerCase() : this.subPackage(api);
        return 'Remote' + manager.initCap(manager.camelize(namePart.replace(/\./g, '_'))) + (api.multipleHttpMethod ? manager.initCap(api.httpMethod) : '');
    },

    /**
     * Return paramExtendsClass.
     * @param {Api} api - API.
     * @param {Object} properties - properties.
     * @return {string} paramExtendsClass.
     */
    paramExtendsClass : function(api, properties) {
        return null;
    },

    /**
     * Return paramImplementsClasses.
     * @param {Api} api - API.
     * @param {Object} properties - properties.
     * @return {string} paramImplementsClasses.
     */
    paramImplementsClasses : function(api, properties) {
        return null;
    },

    /**
     * Return paramClassName.
     * @param {Api} api - API.
     * @param {boolean} detail - detail.
     * @return {string} paramClassName.
     */
    paramClassName : function(api, detail) {
        return this.beanClassName(api, detail) + 'Param';
    },

    /**
     * Return returnExtendsClass.
     * @param {Api} api - API.
     * @param {Object} properties - properties.
     * @return {string} returnExtendsClass.
     */
    returnExtendsClass : function(api, properties) {
        return null;
    },

    /**
     * Return returnImplementsClasses.
     * @param {Api} api - API.
     * @param {Object} properties - properties.
     * @return {string} returnImplementsClasses.
     */
    returnImplementsClasses : function(api, properties) {
        return null;
    },

    /**
     * Return returnClassName.
     * @param {Api} api - API.
     * @param {boolean} detail - detail.
     * @return {string} returnClassName.
     */
    returnClassName : function(api, detail) {
        return this.beanClassName(api, detail) + 'Return';
    },

    /**
     * Return nestClassName.
     * @param {Api} api - API.
     * @param {string} className - className.
     * @return {string} nestClassName.
     */
    nestClassName : function(api, className) {
        return className.replace(/(Part|Result|Model|Bean)$/, '') + 'Part';
    },

    /**
     * Return fieldName.
     * @param {Api} api - API.
     * @param {string} fieldName - fieldName.
     * @return {string} fieldName.
     */
    fieldName : function(api, fieldName) {
        return manager.initUncap(manager.camelize(fieldName));
    },

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
     * Return java property type mapping.
     * e.g. java.util.List -> org.eclipse.collections.api.list.ImmutableList, java.time.LocalDate -> String etc.
     * @return typeMap The map of type conversion, swagger type to java type. (NotNull)
     */
    typeMap: function() {
        return {
            'object': 'Object',
            'int32': 'Integer',
            'int64': 'Long',
            'float': 'Float',
            'double': 'Double',
            'string': 'String',
            'byte': 'byte[]',
            'binary': 'org.lastaflute.web.ruts.multipart.MultipartFormFile',
            'file': 'org.lastaflute.web.ruts.multipart.MultipartFormFile',
            'boolean': 'Boolean',
            'date': 'java.time.LocalDate',
            'date-time': 'java.time.LocalDateTime',
            'array': 'java.util.List'
        };
    },

    /**
     * Return pathVariableManualMappingClass.
     * @param {Api} api - API.
     * @param {PathVariable} pathVariable - pathVariable.
     * @return {string} pathVariableManualMappingClass.
     */
    pathVariableManualMappingClass : function(api, pathVariable) {
        return null;
    },

    /**
     * Return pathVariableManualMappingClass.
     * @param {Api} api - API.
     * @param {string} beanClassName - beanClassName.
     * @param {Property} property - property.
     * @return {string} pathVariableManualMappingClass.
     */
    beanPropertyManualMappingClass : function(api, beanClassName, property) {
        return null;
    },

    /**
     * Return pathVariableManualMappingDescription.
     * @param {Api} api - API.
     * @param {PathVariable} pathVariable - pathVariable.
     * @return {string} pathVariableManualMappingClass.
     */
    pathVariableManualMappingDescription : function(api, pathVariable) {
        return null;
    },

    /**
     * Return beanPropertyManualMappingDescription.
     * @param {Api} api - API.
     * @param {string} beanClassName - beanClassName.
     * @param {Property} property - property.
     * @return {string} beanPropertyManualMappingDescription.
     */
    beanPropertyManualMappingDescription : function(api, beanClassName, property) {
        return null;
    }
};

var remoteApiRule = {};
for (var i in baseRule) {
    remoteApiRule[i] = baseRule[i];
}
