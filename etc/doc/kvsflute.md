# KVSFlute
Class generator plugin for [DBFlute](http://dbflute.seasar.org/) to access to KVS.

This plugin provides three ways of accessing to KVS:
- as data store (KVS Store)
- as cache for access to RDB (KVS Cache)
- as cache for columns specified by DBFlute ConditionBean (CB-Embedded Cache)

## Setup
- Add dependency on [jedis](https://github.com/xetorthio/jedis) to `pom.xml`/`build.gradle`
   - because current version of KVSFlute only supports Redis
- Copy & paste directories under `freegen` to `freegen` in your project's dbflute-client
- Create `ControlFreeGen.vm` under `freegen` and add following:
```
#parse("./kvs/ControlFreeGenKvsJava.vm")
#parse("./dbfluteOptional/ControlFreeGenDbfluteOptionalJava.vm")
```
- Create `kvs-pool.json` and add information of KVS instance to connect to
```
// e.g.
{
    "examplekvs": {"$comment": "Example Store", "$configClass": "com.example.mylasta.direction.ExampleConfig"}
}
```
- Add properties to `config.properties` and `env.properties`, and adjust themaccording to your condition (see `properties` files in this repo for instance)
   - Add a set of properties per KVS instance
- Add pool information to `freeGenMap.dfprop` (see `dfprop/freeGenMap.dfprop` for instance)
   - Add a set of pool information per KVS instance
- Execute 'freegen' task of DBFlute
   - by `mvn` task
   - by `manage.sh`

### Setup for KVS Store
- Create `kvs-store-schema-{instanceName}.json` and define schema (see `playskvs/schema/kvs-store-schema-examplestore.json` for instance)
   - Create schema.json per schema
- Add schema information to `freeGenMap.dfprop` (see `dfprop/freeGenMap.dfprop` for instance)
   - Add a set of schema information per schema
- Execute 'freegen' task of DBFlute

### Setup for KVS Cache
- Create `kvs-cache-schema-{dbfluteClientName}.json` and define schema (see `playskvs/schema/kvs-store-schema-exampledb.json` for instance)
- Add schema information to `freeGenMap.dfprop` (see `dfprop/freeGenMap.dfprop` for instance)
- Execute 'freegen' task of DBFlute

### Setup for CB-Embedded Cache
- Add configuration to `dfprop/littladjustment.dfprop`
```
# e.g.
; columnNullObjectMap = map:{
    ; providerPackage = org.dbflute.kvs.cache
    ; isGearedToSpecify = true
    ; columnMap = map:{
        ; MEMBER_STATUS = map:{
            ; DESCRIPTION = KvsCacheColumnNullObject.getInstance().findColumn(this, "$$columnName$$", $$primaryKey$$)
        }
        ; MEMBER_SECURITY = map:{
            ; REMINDER_ANSWER = KvsCacheColumnNullObject.getInstance().findColumn(this, "$$columnName$$", $$primaryKey$$)
            ; REMINDER_QUESTION = KvsCacheColumnNullObject.getInstance().findColumn(this, "$$columnName$$", $$primaryKey$$)
            ; UPDATE_DATETIME = KvsCacheColumnNullObject.getInstance().findColumn(this, "$$columnName$$", $$primaryKey$$)
           }
       }
   }
   ```
- Execute **' (re)generate'** task of DBflute
   - CB-Embedded Cache modifies classes derived from DBFlute engine

## Usage example
### KVS Store
```java
// Insert/Update
KvsEgStoreExample insertedEntity = kvsEgStoreExampleBhv.insertOrUpdate(() -> {
    KvsEgStoreExample entity = new KvsEgStoreExample();

    entity.setEgName(name);
    entity.setEgDescription(description);
    entity.setExpireDatetime(expireDatetime);

    return entity;
});

String egkey = insertedEntity.getEgkey();

// Select (selectEntity)
OptionalEntity<KvsEgStoreExample> result = kvsEgStoreExampleBhv.selectEntity(cb -> {
    cb.acceptPK(egkey);
});

// Delete
kvsEgStoreExampleBhv.delete(() -> {
    KvsEgStoreExample entity = new KvsEgStoreExample();

    entity.setEgkey(egkey);

    return entity;
});
```

### KVS Cache
```java
// Insert/Update
kvsProductBhv.insertOrUpdateByCategoryCode(() -> {
    Product product = new Product();

    // The column(s) registerd to "kvsKeys" must be set some value.
    product.setProductCategoryCode(categoryCode);

    product.setProductName(productName);
    product.setProductHandleCode(productHandleCode);
    product.setProductStatusCode_OnSaleProduction();
    product.setRegularPrice(price);

    return product;
});

// Select (selectList)
List<Product> productList = kvsProductBhv.selectListByCategoryCode(cb -> {
    // Query condition must be set to the column(s) registerd to "kvsKeys."
    cb.query().setProductCategoryCode_Equal(categoryCode);

    cb.query().setProductStatusCode_Equal(statusCode);
    cb.query().addOrderBy_RegisterDatetime_Desc();
});

// Delete
kvsProductBhv.deleteByProductId(() -> {
    Product product = new Product();

    // The column(s) registerd to "kvsKeys" must be set some value.
    product.setProductCategoryCode(categoryCode);

    return product;
});
```

### CB-Embedded Cache
Applied column is cached when it is specified by `specify()` in DBFlute ConditionBean.

## Supported KVS product
- [Redis](https://redis.io/)

## License
Apache 2.0
