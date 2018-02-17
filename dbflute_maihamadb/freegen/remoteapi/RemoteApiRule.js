// Based on ECMAScript5. Because Nashorn of java 8 is ECMAScript5.
// =======================================================================================
//                                                                              Definition
//                                                                              ==========
/**
 * Request Type.
 * @typedef {Object} Request
 */

/**
 * API Type.
 * @typedef {Object} Api
 * @property {string} api.schema - Schema.
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
    //                                                                               Const
    //                                                                               =====
    FIELD_NAMING: {
        CAMEL_TO_LOWER_SNAKE: 'CAMEL_TO_LOWER_SNAKE'
    },

    // ===================================================================================
    //                                                                               Base
    //                                                                              ======
    /**
     * Return schema.
     * @param {Request} request - Request. (NotNull)
     * @return {string} schema. (NotNull)
     */
    schema: function(request) {
        return request.requestName.replace(/^RemoteApi/g, '');
    },

    /**
     * Return schema package.
     * @param {Api} api - API. (NotNull)
     * @return {string} schema package. (NotNull)
     */
    schemaPackage: function(schema) {
        return manager.decamelize(schema).replace(/_/g, '.').toLowerCase();
    },

    /**
     * Return true if target.
     * @param {Api} api - API. (NotNull)
     * @return {boolean} true if target. (NotNull)
     */
    target: function(api) {
        var contentTypes = [];
        Array.prototype.push.apply(contentTypes, api.consumes ? api.consumes: []);
        Array.prototype.push.apply(contentTypes, api.produces ? api.produces: []);
        return (contentTypes.indexOf('application/json') !== -1) && api.url.indexOf('/swagger/json') !== 0;
    },

    /**
     * Return filtered URL.
     * @param {Api} api - API. (NotNull)
     * @return {boolean} filtered URL. (NotNull)
     */
    url: function(api) { return api.url; },

    /**
     * Return sub package.
     * @param {Api} api - API. (NotNull)
     * @return {string} sub package. (NotNull)
     */
    subPackage: function(api) {
        return api.url.replace(/(_|^\/|\/$)/g, '').replace(/\/\{.*?\}/g, '').replace(/\//g, '.').toLowerCase();
    },

    // ===================================================================================
    //                                                                               DiXml
    //                                                                               =====
    diXmlPath: function(schema, resourceFilePath) {
        return '../resources/remoteapi/di/remoteapi_' + this.schemaPackage(schema).replace(/\./g, '-') + '.xml';
    },

    diconPath: function(schema, resourceFilePath) {
        return '../resources/remoteapi/di/remoteapi_' + this.schemaPackage(schema).replace(/\./g, '-') + '.dicon';
    },

    // ===================================================================================
    //                                                                            Behavior
    //                                                                            ========
    behaviorClassGeneration: true,
    behaviorMethodGeneration: true,
    behaviorMethodAccessModifier: 'public',
    frameworkBehaviorClass: 'org.lastaflute.remoteapi.LastaRemoteBehavior',

    /**
     * Return abstractBehaviorClassName.
     * @param {string} schema - schema. (NotNull)
     * @return {string} abstractBehaviorClassName. (NotNull)
     */
    abstractBehaviorClassName: function(schema) {
        return 'AbstractRemote' + schema + 'Bhv';
    },

    /**
     * Return filtered Behavior SubPackage.
     * @param {Api} api - API. (NotNull)
     * @return {string} filtered Behavior SubPackage. (NotNull)
     */
    behaviorSubPackage: function(api) {
        return this.subPackage(api).replace(/^([^.]*)\.(.+)/, '$1');
    },

    /**
     * Return bsBehaviorClassName.
     * @param {Api} api - API. (NotNull)
     * @return {string} bsBehaviorClassName. (NotNull)
     */
    bsBehaviorClassName: function(api) {
        return 'BsRemote' + api.schema + manager.initCap(manager.camelize(this.behaviorSubPackage(api).replace(/\./g, '_'))) + 'Bhv';
    },

    /**
     * Return exBehaviorClassName.
     * @param {Api} api - API. (NotNull)
     * @return {string} exBehaviorClassName. (NotNull)
     */
    exBehaviorClassName: function(api) {
        return 'Remote' + api.schema + manager.initCap(manager.camelize(this.behaviorSubPackage(api).replace(/\./g, '_'))) + 'Bhv';
    },

    /**
     * Return behaviorRequestMethodName.
     * @param {Api} api - API. (NotNull)
     * @return {string} behaviorRequestMethodName. (NotNull)
     */
    behaviorRequestMethodName: function(api) {
        var methodPart = manager.camelize(this.subPackage(api).replace(this.behaviorSubPackage(api), '').replace(/\./g, '_'));
        return 'request' + manager.initCap(methodPart) + (api.multipleHttpMethod ? manager.initCap(api.httpMethod): '');
    },

    /**
     * Return behaviorRuleMethodName.
     * @param {Api} api - API. (NotNull)
     * @return {string} behaviorRuleMethodName. (NotNull)
     */
    behaviorRuleMethodName: function(api) {
        var methodPart = manager.camelize(this.subPackage(api).replace(this.behaviorSubPackage(api), '').replace(/\./g, '_'));
        return 'ruleOf' + manager.initCap(methodPart) + (api.multipleHttpMethod ? manager.initCap(api.httpMethod): '');
    },

    // ===================================================================================
    //                                                                        Param/Return
    //                                                                        ============
    /**
     * Return filtered Bean SubPackage.
     * @param {Api} api - API. (NotNull)
     * @return {string} filtered Bean SubPackage. (NotNull)
     */
    beanSubPackage: function(api) {
        var package = this.subPackage(api);
        if (package === this.behaviorSubPackage(api)) {
            package += '.index';
        }
        return package;
    },
    definitionKey: function(definitionKey) { return definitionKey; },
    unDefinitionKey: function(definitionKey) { return definitionKey; },

    /**
     * Return beanClassName.
     * @param {Api} api - API. (NotNull)
     * @param {boolean} detail - detail. (NotNull)
     * @return {string} beanClassName. (NotNull)
     */
    beanClassName: function(api, detail) {
        var namePart = detail ? api.url.replace(/(_|^\/|\/$|\{|\})/g, '').replace(/\//g, '_').toLowerCase(): this.subPackage(api);
        return 'Remote' + manager.initCap(manager.camelize(namePart.replace(/\./g, '_'))) + (api.multipleHttpMethod ? manager.initCap(api.httpMethod): '');
    },

    /**
     * Return paramExtendsClass.
     * @param {Api} api - API. (NotNull)
     * @param {Object} properties - properties. (NotNull)
     * @return {string} paramExtendsClass. (NullAllowed)
     */
    paramExtendsClass: function(api, properties) {
        return null;
    },

    /**
     * Return paramImplementsClasses.
     * @param {Api} api - API. (NotNull)
     * @param {Object} properties - properties. (NotNull)
     * @return {string} paramImplementsClasses. (NullAllowed)
     */
    paramImplementsClasses: function(api, properties) {
        return null;
    },

    /**
     * Return paramClassName.
     * @param {Api} api - API. (NotNull)
     * @param {boolean} detail - detail. (NotNull)
     * @return {string} paramClassName. (NotNull)
     */
    paramClassName: function(api, detail) {
        return this.beanClassName(api, detail) + 'Param';
    },

    /**
     * Return returnExtendsClass.
     * @param {Api} api - API. (NotNull)
     * @param {Object} properties - properties. (NotNull)
     * @return {string} returnExtendsClass. (NullAllowed)
     */
    returnExtendsClass: function(api, properties) {
        return null;
    },

    /**
     * Return returnImplementsClasses.
     * @param {Api} api - API. (NotNull)
     * @param {Object} properties - properties. (NotNull)
     * @return {string} returnImplementsClasses. (NullAllowed)
     */
    returnImplementsClasses: function(api, properties) {
        return null;
    },

    /**
     * Return returnClassName.
     * @param {Api} api - API. (NotNull)
     * @param {boolean} detail - detail. (NotNull)
     * @return {string} returnClassName. (NotNull)
     */
    returnClassName: function(api, detail) {
        return this.beanClassName(api, detail) + 'Return';
    },

    /**
     * Return nestClassName.
     * @param {Api} api - API. (NotNull)
     * @param {string} className - className. (NotNull)
     * @return {string} nestClassName. (NotNull)
     */
    nestClassName: function(api, className) {
        return className.replace(/(Part|Result|Model|Bean)$/, '') + 'Part';
    },

    /**
     * Return fieldName.
     * @param {Api} api - API. (NotNull)
     * @param {string} fieldName - fieldName. (NotNull)
     * @return {string} fieldName. (NotNull)
     */
    fieldName: function(api, bean, fieldName) {
        var fieldNaming = this.fieldNamingMapping()[bean.in];
        if (fieldNaming === this.FIELD_NAMING.CAMEL_TO_LOWER_SNAKE) {
            return manager.initUncap(manager.camelize(fieldName));
        }
        return fieldName;
    },

    // ===================================================================================
    //                                                                                 Doc
    //                                                                                 ===
    docGeneration: true,

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
     * Return field naming mapping.
     * @return field naming mapping. (NotNull)
     */
    fieldNamingMapping: function() {
        return {
            'path': this.FIELD_NAMING.CAMEL_TO_LOWER_SNAKE,
            'query': this.FIELD_NAMING.CAMEL_TO_LOWER_SNAKE,
            'formData': this.FIELD_NAMING.CAMEL_TO_LOWER_SNAKE,
            'body': this.FIELD_NAMING.CAMEL_TO_LOWER_SNAKE,
            'response': this.FIELD_NAMING.CAMEL_TO_LOWER_SNAKE
        };
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
     * @param {Api} api - API. (NotNull)
     * @param {PathVariable} pathVariable - pathVariable. (NotNull)
     * @return {string} pathVariableManualMappingClass. (NullAllowed)
     */
    pathVariableManualMappingClass: function(api, pathVariable) {
        return null;
    },

    /**
     * Return pathVariableManualMappingClass.
     * @param {Api} api - API. (NotNull)
     * @param {string} beanClassName - beanClassName. (NotNull)
     * @param {Property} property - property. (NotNull)
     * @return {string} pathVariableManualMappingClass. (NullAllowed)
     */
    beanPropertyManualMappingClass: function(api, beanClassName, property) {
        return null;
    },

    /**
     * Return pathVariableManualMappingDescription.
     * @param {Api} api - API. (NotNull)
     * @param {PathVariable} pathVariable - pathVariable. (NotNull)
     * @return {string} pathVariableManualMappingClass. (NullAllowed)
     */
    pathVariableManualMappingDescription: function(api, pathVariable) {
        return null;
    },

    /**
     * Return beanPropertyManualMappingDescription.
     * @param {Api} api - API. (NotNull)
     * @param {string} beanClassName - beanClassName. (NotNull)
     * @param {Property} property - property. (NotNull)
     * @return {string} beanPropertyManualMappingDescription. (NullAllowed)
     */
    beanPropertyManualMappingDescription: function(api, beanClassName, property) {
        return null;
    },

    /**
     * Return delete target.
     * @param {Request} request - Request. (NotNull)
     * @return {File} file. (NotNull)
     * @return {boolean} delete target. (NotNull)
     */
    deleteTarget: function(request, file) {
        var nameFunctionList = ['bsBehaviorClassName', 'exBehaviorClassName', 'paramClassName', 'returnClassName'];
        var dummyApi = {'schema': this.schema(request), 'url': '@@@'};
        for (var nameFunctionIndex in nameFunctionList) {
            if (file.getName().match(new RegExp(this[nameFunctionList[nameFunctionIndex]](dummyApi).replace('@@@', '.+')))) {
                return true;
            }
        }
        return false;
    }
};

var remoteApiRule = {};
for (var i in baseRule) {
    remoteApiRule[i] = baseRule[i];
}
