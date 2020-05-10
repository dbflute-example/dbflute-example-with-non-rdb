package org.docksidestage.app.web.solr;

import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.dbflute.helper.HandyDate;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.solr.result.SolrFacetResultBean;
import org.dbflute.solr.result.SolrPagingResultBean;
import org.docksidestage.solr.exbhv.SolrExampleBhv;
import org.docksidestage.solr.exentity.SolrExample;
import org.docksidestage.unit.UnitNonrdbTestCase;

/**
 * @author nagahori
 */
public class SolrExampleTest extends UnitNonrdbTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private SolrExampleBhv solrExampleBhv;

    public void test_selectFirst() {
        // ## Arrange ##
        // ## Act ##
        OptionalEntity<SolrExample> result = solrExampleBhv.selectFirst(cb -> {
            cb.query().setProductName_RangeSearchFrom("P");
        });

        // ## Assert ##
        assertTrue(result.isPresent());
    }

    public void test_selectPage() {
        // ## Arrange ##
        LocalDateTime from = new HandyDate("2016/11/1").getLocalDateTime();

        // ## Act ##
        SolrPagingResultBean<SolrExample> result = solrExampleBhv.selectPage(cb -> {
            cb.query().setLatestPurchaseDate_RangeSearchFrom(from);
            cb.paging(10, 1);
        });

        // ## Assert ##
        result.forEach(example -> {
            assertTrue(example.getLatestPurchaseDate().isAfter(from) || example.getLatestPurchaseDate().isEqual(from));
        });
    }

    public void test_selectFacetQuery() {
        // ## Arrange ##
        // ## Act ##
        SolrFacetResultBean<SolrExample> result = solrExampleBhv.selectFacetQuery(cb -> {
            cb.query().setProductName_Equal("foo");
            cb.addFacetQuery(qb -> qb.setLatestPurchaseDate_RangeSearchTo(toLocalDateTime("2015/12/31")));
            cb.addFacetQuery(qb -> qb.setLatestPurchaseDate_RangeSearch(toLocalDateTime("2016/1/1"), toLocalDateTime("2016/3/31")));
            cb.addFacetQuery(qb -> qb.setLatestPurchaseDate_RangeSearch(toLocalDateTime("2016/4/1"), toLocalDateTime("2016/6/30")));
            cb.addFacetQuery(qb -> qb.setLatestPurchaseDate_RangeSearch(toLocalDateTime("2016/7/1"), toLocalDateTime("2016/9/30")));
            cb.addFacetQuery(qb -> qb.setLatestPurchaseDate_RangeSearch(toLocalDateTime("2016/10/1"), toLocalDateTime("2016/12/31")));
            cb.addFacetQuery(qb -> qb.setLatestPurchaseDate_RangeSearchFrom(toLocalDateTime("2017/1/1")));
        });

        // ## Assert ##
        result.getFacetFieldList().forEach(field -> log(field));
        result.getFacetResult().forEach((key, value) -> log(key, value));
    }
}
