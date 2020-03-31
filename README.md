DBFlute Example with non-RDB
=======================
example project for DBFlute with non-RDB

LastaFlute:  
https://github.com/dbflute-example/dbflute-example-with-non-rdb

# Quick Trial
*ReplaceSchema
```java
// call manage.sh at dbflute-example-with-non-rdb/dbflute_maihamadb
// and select replace-schema in displayed menu
...:dbflute_maihamadb ...$ sh manage.sh
```

*main() method
```java
public class NonrdbBoot {

    public static void main(String[] args) {
        new JettyBoot(8175, "/nonrdb").asDevelopment(isNoneEnv()).bootAwait();
    }
}
```

# Generators for Non RDBs
## C7aFlute for Cassandra
```java
OptionalEntity<Product> result = c7aProductBhv.selectEntity(cb -> {
    cb.query().acceptPK(productId);
});
```
[How to use C7aFlute](dbflute_maihamadb/freegen/c7a/README.md)  
contributed by U-NEXT: http://video.unext.jp/

## ESFlute for Elasticsearch
```java
PagingResultBean<Product> productPage = productBhv.selectPage(cb -> {
    cb.query().setProductName_MatchPhrase(form.productName);
    cb.query().setProductStatusCode_Equal(form.productStatus);
    cb.query().addOrderBy_ProductName_Asc();
    cb.query().addOrderBy_Id_Asc();
    cb.paging(4, pageNumber);
});
```
contributed by CodeLibs Project: https://github.com/codelibs

## KVSFlute for KVS (Redis)
```java
OptionalEntity<Product> result = kvsProductBhv.selectEntityByProductId(cb -> {
    cb.query().setProductId_Equal(productId);
});
```
[How to use KvsFlute](dbflute_maihamadb/freegen/kvs/README.md)  
contributed by U-NEXT: http://video.unext.jp/

## RemoteApiGen (with LastaFlute)

to this URL.

https://github.com/dbflute-example/dbflute-example-with-remoteapi-gen


## SolrFlute for Solr
```java
SolrPagingResultBean<SolrExample> result = solrExampleBhv.selectPage(cb -> {
    cb.query().setLatestPurchaseDate_RangeSearchFrom(from);
    cb.paging(10, 1);
});
```
[How to use SolrFlute](dbflute_maihamadb/freegen/solr/README.md)  
contributed by U-NEXT: http://video.unext.jp/

# Information
## License
Apache License 2.0

## Official site
comming soon...
