DBFlute Example with non-RDB
=======================
example project for DBFlute with non-RDB

LastaFlute:  
https://github.com/dbflute-example/dbflute-example-with-non-rdb

# Generators for Non RDBs
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

# Information
## License
Apache License 2.0

## Official site
comming soon...
