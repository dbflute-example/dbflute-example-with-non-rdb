# SolrFlute
Class generator plugin for [DBFlute](http://dbflute.seasar.org/) to access to [Solr](http://lucene.apache.org/solr/).

## Setup
- Add dependency on [solrj](https://cwiki.apache.org/confluence/display/solr/Using+SolrJ) to `pom.xml`/`build.gradle`
- Copy & paste directories under `freegen` to `freegen` in your project's dbflute-client
- Create `ControlFreeGen.vm` under `freegen` and add following:
```
#parse("./solr/ControlFreeGenSolrJava.vm")
```
- locate `schema.xml` for Solr to connect to and add name to the beginning of its filename to identify schema (e.g. `mysolr_schema.xml`)
- Add properties to `config.properties` and `env.properties`, and adjust themaccording to your condition (see `properties` files in this repo for instance)
   - Add a set of properties per schema
- Add schema information to `freeGenMap.dfprop` (see `dfprop/freeGenMap.dfprop` for instance)
   - Add a set of the information per schema
- Execute 'freegen' task of DBFlute
   - by `mvn` task
   - by `manage.sh`

## Usage example
```java
// dismax with filter query
SolrPagingResultBean<SolrGeneral> list = solrExampleBhv.selectPage(cb -> {
    cb.query().dismax(keyword, queryField -> {
        queryField.put(SolrGeneralDbm.Name, null);
        queryField.put(SolrGeneralDbm.Kana, 10);
        queryField.put(SolrGeneralDbm.NameGeneral, 20);
        queryField.put(SolrGeneralDbm.Synonym, null);
    });

    cb.specify().fieldUid();

    cb.filterQuery().setCategory_Equal(someCategory);
    cb.filterQuery().setProductionYear_RangeSearchTo(year);

    cb.paging(10, 2)
});

// faceted search
SolrFacetResultBean itemFacetByGenre = solrExampleBhv.selectFacetQuery(cb -> {
    cb.query().setStatus_Equal("new");
    // specify facet counts
    cb.addFacetQuery(queryBean -> queryBean.setGenre_Equal("pops"));
    cb.addFacetQuery(queryBean -> queryBean.setGenre_Equal("classical"));
    cb.addFacetQuery(queryBean -> queryBean.setGenre_Equal("jazz"));
});

SolrFacetResultBean itemFacetByYear = solrExampleBhv.selectFacetQuery(cb -> {
    // Use specific field as facet
    cb.facetSpecify().fieldProductionYear();
});
```

## License
Apache 2.0
