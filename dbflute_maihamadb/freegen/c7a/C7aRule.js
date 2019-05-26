// Based on ECMAScript5. Because Nashorn of java 8 is ECMAScript5.
// =======================================================================================
//                                                                              Definition
//                                                                              ==========

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
    schema: function(request) {
        return manager.initUncap(request.requestName.replace(/^C7a/g, ''));
    },

    schemaShort: function(request) {
        return this.schema(request);
    },

    schemaPackage: function(schema) {
        return manager.decamelize(schema).replace(/_/g, '.').toLowerCase();
    },

    targetKeyspace: function(Keyspace) {
        return true;
    },

    fieldName: function(c7a, tableMeta, columnMeta) {
        var fieldNaming = this.fieldNamingMapping()['column'];
        if (fieldNaming === this.FIELD_NAMING.CAMEL_TO_LOWER_SNAKE) {
            return manager.initUncap(manager.camelize(columnMeta.name));
        }
        return columnMeta.name;
    },

    // ===================================================================================
    //                                                                               DiXml
    //                                                                               =====
    poolDiXmlPath: function(c7a, resourceFilePath) {
        return '../resources/c7a/di/c7a_pool_' + c7a.schemaPackage.replace(/\./g, '-') + '.xml';
    },

    diXmlPath: function(c7a, resourceFilePath) {
        return '../resources/c7a/di/c7a_' + c7a.schemaPackage.replace(/\./g, '-') + '.xml';
    },

    // ===================================================================================
    //                                                                           User Type
    //                                                                           =========
    exUserTypeSubPackage: function(c7a, userTypeMeta) {
        return 'exudt';
    },

    exUserTypeClassName: function(c7a, userTypeMeta) {
        return 'C7a' + manager.initCap(c7a.schemaShort) + manager.initCap(manager.camelize(userTypeMeta.name.replace(/\./g, '_')));
    },

    bsUserTypeSubPackage: function(c7a, userTypeMeta) {
        return 'bsudt';
    },

    bsUserTypeClassName: function(c7a, userTypeMeta) {
        return 'C7a' + manager.initCap(c7a.schemaShort) + 'Bs' + manager.initCap(manager.camelize(userTypeMeta.name.replace(/\./g, '_')));
    },

    // ===================================================================================
    //                                                                              DBMeta
    //                                                                              ======
    dbMetaSubPackage: function(c7a, tableMeta) {
        return 'bsentity.dbmeta';
    },

    dbMetaClassName: function(c7a, tableMeta) {
        return 'C7a' + manager.initCap(c7a.schemaShort) + manager.initCap(manager.camelize(tableMeta.name.replace(/\./g, '_'))) + 'Dbm';
    },

    dbMetaExtendsClass: function(c7a, tableMeta) {
        return 'org.dbflute.c7a.entity.dbmeta.AbstractC7aDBMeta';
    },

    // ===================================================================================
    //                                                                              Entity
    //                                                                              ======
    exEntitySubPackage: function(c7a, tableMeta) {
        return 'exentity';
    },

    exEntityClassName: function(c7a, tableMeta) {
        return 'C7a' + manager.initCap(c7a.schemaShort) + manager.initCap(manager.camelize(tableMeta.name.replace(/\./g, '_')));
    },

    bsEntitySubPackage: function(c7a, tableMeta) {
        return 'bsentity';
    },

    bsEntityClassName: function(c7a, tableMeta) {
        return 'C7a' + manager.initCap(c7a.schemaShort) + 'Bs' + manager.initCap(manager.camelize(tableMeta.name.replace(/\./g, '_')));
    },

    bsEntityExtendsClass: function(c7a, tableMeta) {
        return 'org.dbflute.c7a.entity.AbstractC7aEntity';
    },

    entityDefinedCommonColumnClassName: function(c7a) {
        return 'C7a' + manager.initCap(c7a.schemaShort) + 'EntityDefinedCommonColumn';
    },

    bsEntityImplementsClasses: function(c7a, tableMeta) {
        var classes = [];
        classes.push(c7a.package + '.' + this.entityDefinedCommonColumnClassName(c7a, tableMeta));
        return classes.join(', ');
    },

    commonColumn: function() {
        return {
            'commonColumn' : [],
            'beforeInsert' : [],
            'beforeUpdate' : []
        };
    },

    optimisticLock: function() {
        return {
            'fieldName' : null,
            'operation' : null
        };
    },

    // ===================================================================================
    //                                                                      ConditionQuery
    //                                                                      ==============
    exConditionQuerySubPackage: function(c7a, tableMeta) {
        return 'cbean.cq';
    },

    exConditionQueryClassName: function(c7a, tableMeta) {
        return 'C7a' + manager.initCap(c7a.schemaShort) + manager.initCap(manager.camelize(tableMeta.name.replace(/\./g, '_'))) + 'CQ';
    },

    bsConditionQuerySubPackage: function(c7a, tableMeta) {
        return 'cbean.cq.bs';
    },

    bsConditionQueryClassName: function(c7a, tableMeta) {
        return 'C7a' + manager.initCap(c7a.schemaShort) + 'Bs' + manager.initCap(manager.camelize(tableMeta.name.replace(/\./g, '_'))) + 'CQ';
    },

    bsConditionQueryExtendsClass: function(c7a, tableMeta) {
        return 'org.dbflute.c7a.cbean.AbstractC7aConditionQuery';
    },

    bsConditionQueryImplementsClasses: function(c7a, tableMeta) {
        return null;
    },

    // ===================================================================================
    //                                                                       ConditionBean
    //                                                                       =============
    exConditionBeanSubPackage: function(c7a, tableMeta) {
        return 'cbean';
    },

    exConditionBeanClassName: function(c7a, tableMeta) {
        return 'C7a' + manager.initCap(c7a.schemaShort) + manager.initCap(manager.camelize(tableMeta.name.replace(/\./g, '_'))) + 'CB';
    },

    bsConditionBeanSubPackage: function(c7a, tableMeta) {
        return 'cbean.bs';
    },

    bsConditionBeanClassName: function(c7a, tableMeta) {
        return 'C7a' + manager.initCap(c7a.schemaShort) + 'Bs' + manager.initCap(manager.camelize(tableMeta.name.replace(/\./g, '_'))) + 'CB';
    },

    bsConditionBeanExtendsClass: function(c7a, tableMeta) {
        return 'org.dbflute.c7a.cbean.AbstractC7aConditionBean';
    },

    bsConditionBeanImplementsClasses: function(c7a, tableMeta) {
        return null;
    },

    enablePagingCount: function(c7a, tableMeta) {
        return false;
    },

    // ===================================================================================
    //                                                                            Behavior
    //                                                                            ========
    exBehaviorSubPackage: function(c7a, tableMeta) {
        return 'exbhv';
    },

    exBehaviorClassName: function(c7a, tableMeta) {
        return 'C7a' + manager.initCap(c7a.schemaShort) + manager.initCap(manager.camelize(tableMeta.name.replace(/\./g, '_'))) + 'Bhv';
    },

    bsBehaviorSubPackage: function(c7a, tableMeta) {
        return 'bsbhv';
    },

    bsBehaviorClassName: function(c7a, tableMeta) {
        return 'C7a' + manager.initCap(c7a.schemaShort) + 'Bs' + manager.initCap(manager.camelize(tableMeta.name.replace(/\./g, '_'))) + 'Bhv';
    },

    bsBehaviorExtendsClassName: function(c7a) {
        return 'AbstractC7a' + manager.initCap(c7a.schema) + 'Bhv';
    },

    bsBehaviorExtendsClass: function(c7a) {
        return c7a.package + '.' + this.bsBehaviorExtendsClassName(c7a);
    },

    bsBehaviorImplementsClasses: function(c7a, tableMeta) {
        return null;
    },

    // ===================================================================================
    //                                                                                 Doc
    //                                                                                 ===
    docGeneration: true,

    // ===================================================================================
    //                                                                              Option
    //                                                                              ======
    importOrderList: function() {
        return ['java', 'javax', 'junit', 'org', 'com', 'net', 'ognl', 'mockit', 'jp'];
    },

    /**
     * Return field naming mapping.
     * @return field naming mapping. (NotNull)
     */
    fieldNamingMapping: function() {
        return {
            'column': this.FIELD_NAMING.CAMEL_TO_LOWER_SNAKE
        };
    },

    typeMap: function() {
        return {
            'ascii': 'String',
            'bigint': 'Long',
            'blob': 'byte[]',
            'boolean': 'Boolean',
            'counter': 'Long',
            'date': 'java.time.LocalDate',
            'decimal': 'java.math.Decimal',
            'double': 'Double',
            'float': 'Float',
            'inet': 'java.net.InetAddress',
            'int': 'Integer',
            'list': 'java.util.List',
            'map': 'java.util.Map',
            'set': 'java.util.Set',
            'smallint': 'Short',
            'text': 'String',
            'time': 'java.time.LocalTime',
            'timestamp': 'java.time.LocalDateTime',
            'timeuuid': 'java.util.UUID',
            'tinyint': 'byte',
            'uuid': 'java.util.UUID',
            'varchar': 'String',
            'varint': 'java.math.BigInteger'
        };
    },

    /**
     * Return filter Table Name.
     * @return filter Table Name. (NullAllowed)
     */
    filterTableName: function(c7a, tableMeta) {
        return null;
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

var c7aRule = {};
for (var i in baseRule) {
    c7aRule[i] = baseRule[i];
}
