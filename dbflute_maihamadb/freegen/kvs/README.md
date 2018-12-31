# KVSFlute

Class generator plugin for [DBFlute](http://dbflute.seasar.org/) to access to KVS(Redis).

This plugin generates classes for accessing to KVS(Redis).

[about KVSFlute](http://dbflute.seasar.org/ja/manual/function/helper/kvsflute/index.html)

## Setup for use KVS

- Add dependency on [jedis](https://github.com/xetorthio/jedis).

  e.g. pom.xml (In the case of maven)

  ```
  <dependencies>
      <dependency>
          <groupId>redis.clients</groupId>
          <artifactId>jedis</artifactId>
          <version>${redisVersion}</version>
      </dependency>
  </dependencies>
  ```

  e.g. build.gradle (In the case of gradle)

  ```
  dependencies {
      compile "redis.clients:jedis:${redisVersion}"
  }
  ```

- Copy & paste directories under `freegen/kvs` to `freegen/kvs` in your project's dbflute-client.

- Create `ControlFreeGen.vm` under `freegen` and add following.

  ```
  #parse("./kvs/ControlFreeGenKvsJava.vm")
  ```

- Create `kvs-pool.json` and add information of KVS instance to connect to.

  ```
  {
      "examplekvs": {"$comment": "Example Store", "$configClass": "com.example.mylasta.direction.ExampleConfig"}
  }
  ```

- Add properties to `config.properties` and `env.properties`, and adjust themaccording to your condition.

  Add a set of properties per KVS instance.

  e.g. [service]_config.properties

  ```
  # ========================================================================================
  #                                                                                      DB
  #                                                                                     ====
  # =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
  #                                                                           KVS
  #                                                                          -=-=-
  # ----------------------------------------------------------
  #                                                    example
  #                                                    -------
  # Following configs will not be used if kvs.mock is true.
  #
  # Max latency to get connection to kvs (milliseconds)
  kvs.examplekvs.maxWaitMillis = -1
  # TTL for idle connection (milliseconds)
  kvs.examplekvs.minEvictableIdleTimeMillis = 60000
  # Soft TTL for idle connection (milliseconds)
  kvs.examplekvs.softMinEvictableIdleTimeMillis = 30000
  # Max number of idle connections per monitoring process
  kvs.examplekvs.numTestsPerEvictionRun = 10
  # Should check if connection is available before getting connection
  kvs.examplekvs.testOnBorrow = true
  # Should check if connection is available before returning connection
  kvs.examplekvs.testOnReturn = false
  # Should check if idle connections are available
  kvs.examplekvs.testWhileIdle = true
  # Monitoring interval for idle connections in connection pool (milliseconds)
  kvs.examplekvs.timeBetweenEvictionRunsMillis = 30000
  ```

  e.g. [service]_env.properties

  ```
  # ========================================================================================
  #                                                                                      DB
  #                                                                                     ====
  # =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
  #                                                                           KVS
  #                                                                          -=-=-
  # Whether to use mock for KVS
  kvs.mock = true

  # ----------------------------------------------------------
  #                                                    example
  #                                                    -------
  # Hostname
  kvs.examplekvs.host = localhost
  # Port
  kvs.examplekvs.port = 6379
  # Timeout for connection
  kvs.examplekvs.timeout = 2000
  # Max number of pooled connections
  kvs.examplekvs.maxTotal = 15
  # Max number of idle connections
  kvs.examplekvs.maxIdle = 15
  # Min number of idle connections
  kvs.examplekvs.minIdle = 15
  ```

- Add pool information to `dfprop/freeGenMap.dfprop`.

  ```
  map:{
      # ==========================================================================
      #                                                                   Kvs Pool
      #                                                                   ========
      ; KvsPool = map:{
          ; resourceMap = map:{
              ; baseDir = ../src/main
              ; resourceType = JSON_SCHEMA
              ; resourceFile = $$baseDir$$/resources/kvs/schema/kvs_pool.json
          }
          ; outputMap = map:{
              ; outputDirectory = $$baseDir$$/java
              ; package = org.docksidestage.kvs.pool
          }
          ; tableMap = map:{
              ; tablePath = map
          }
      }
  }
  ```

- Execute 'freegen' task of DBFlute.

  ```
  sh manage.sh 12
  ```

### Setup for KVS Store

- Create `kvs-store-schema-{instanceName}.json` and define schema.

  Create schema.json per schema.

  e.g. kvs_store_schema_examplestore.json

  ```
  {
      "store_example": {
          "$comment": "Description about this KVS store",
          "$type": "table",
          "egkey": { "type": "String", "comment": "this column will be used as (a part of) KVS key", "kvsKey": true, "notNull": true},
          "egId": { "type": "Integer", "comment": "id", "notNull": true},
          "egName": { "type": "String", "comment": "name", "notNull": true},
          "expireDatetime": { "type": "java.time.LocalDateTime", "comment": "time to live", "notNull": true}
      }
  }
  ```

- Add schema information to `freeGenMap.dfprop`.

  Add a set of schema information per schema.

  ```
  map:{
      # ==========================================================================
      #                                                    Kvs Store(examplestore)
      #                                                    =======================
      ; KvsStoreExampleStore = map:{
          ; resourceMap = map:{
              ; baseDir = ../src/main
              ; resourceType = JSON_SCHEMA
              ; resourceFile =  $$baseDir$$/resources/kvs/schema/kvs_store_schema_examplestore.json
          }
          ; outputMap = map:{
              ; outputDirectory = $$baseDir$$/java
              ; package = org.docksidestage.kvs.store
          }
          ; tableMap = map:{
              ; tablePath = map
              ; schema = examplestore
              ; schemaPrefix = Eg
              ; kvsPoolDiFile = kvs/di/kvs-pool-examplekvs.xml
          }
      }
  }
  ```

- Execute 'freegen' task of DBFlute.

  ```
  sh manage.sh 12
  ```

### Setup for KVS Cache
- Create `kvs-cache-schema-{dbfluteClientName}.json` and define schema.

  Create schema.json per schema.

  e.g. kvs_cache_schema_maihamadb.json

  ```
  {
      "product": {
          "$comment": "Product",
          "productId": {
              "$comment": "ID",
              "many": false,
              "kvsKeys": ["PRODUCT_ID"]
          },
          "categoryCode": {
              "$comment": "Category code of each product",
              "many": true,
              "kvsKeys": ["PRODUCT_CATEGORY_CODE"],
              "orderBy": ["REGULAR_PRICE", "PRODUCT_ID"] 
          }
      }
  }
  ```

- Add schema information to `freeGenMap.dfprop`.

  Add a set of schema information per schema.

  ```
  map:{
      # ==========================================================================
      #                                                       Kvs Cache(maihamadb)
      #                                                       ====================
      ; KvsCacheExampledb = map:{
          ; resourceMap = map:{
              ; baseDir = ../src/main
              ; resourceType = JSON_SCHEMA
              ; resourceFile = $$baseDir$$/resources/kvs/schema/kvs_cache_schema_maihamadb.json
          }
          ; outputMap = map:{
              ; outputDirectory = $$baseDir$$/java
              ; package = org.docksidestage.kvs.cache
          }
          ; tableMap = map:{
              ; tablePath = map
              ; schema = maihamadb
              ; schemaPrefix =
              ; kvsPoolDiFile = kvs/di/kvs-pool-examplekvs.xml
              ; dbfluteDiFile = dbflute.xml
              ; dbflutePackage = org.docksidestage.dbflute
              ; databaseMap = map:{
                  ; maihamadb = map:{
                      ; schemaDir = ./schema
                  }
              }
          }
      }
  }
  ```

- Execute 'freegen' task of DBFlute.

  ```
  sh manage.sh 12
  ```

### Setup for CB-Embedded Cache

- Add configuration to `dfprop/littladjustment.dfprop`.

  ```
  ; columnNullObjectMap = map:{
      ; providerPackage = org.dbflute.kvs.cache
      ; isGearedToSpecify = true
      ; columnMap = map:{
          ; MEMBER_STATUS = map:{
              ; DESCRIPTION = KvsCacheColumnNullObject.getInstance().findColumn(this,  "$$columnName$$", $$primaryKey$$)
          }
          ; MEMBER_SECURITY = map:{
              ; REMINDER_ANSWER = KvsCacheColumnNullObject.getInstance().findColumn(this, "$$columnName$$", $$primaryKey$$)
              ; REMINDER_QUESTION = KvsCacheColumnNullObject.getInstance().findColumn(this, "$$columnName$$", $$primaryKey$$)
              ; UPDATE_DATETIME = KvsCacheColumnNullObject.getInstance().findColumn(this, "$$columnName$$", $$primaryKey$$)
          }
      }
  }
  ```

- Execute ' (re)generate' task of DBflute.  
  B-Embedded Cache modifies classes derived from DBFlute engine.

  ```
  sh manage.sh 1
  ```

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
