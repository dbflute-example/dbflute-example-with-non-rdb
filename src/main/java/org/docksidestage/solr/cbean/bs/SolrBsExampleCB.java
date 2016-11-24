/*
 * Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.solr.cbean.bs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.dbflute.solr.cbean.AbstractSolrConditionBean;
import org.dbflute.solr.cbean.AbstractSolrSpecification;
import org.dbflute.solr.cbean.SolrFilterQueryBean;
import org.dbflute.solr.cbean.SolrQBCall;
import org.dbflute.solr.cbean.SolrQueryBean;
import org.dbflute.solr.cbean.SolrSpecification;

import org.docksidestage.solr.bsentity.meta.SolrExampleDbm;
import org.docksidestage.solr.cbean.cfq.SolrExampleCFQ;
import org.docksidestage.solr.cbean.cq.SolrExampleCQ;

/**
 * Base ConditionBean class of Solr schema "Example."
 * @author FreeGen
 */
public class SolrBsExampleCB extends AbstractSolrConditionBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected SolrExampleCQ queryBean;

    protected SolrExampleCFQ filterQueryBean;

    protected HpSpecification specification;

    protected List<SolrExampleCQ> facetQueryList;

    protected HpSpecification facetSpecification;

    public SolrExampleCQ query() {
        if (this.queryBean == null) {
            this.queryBean = new SolrExampleCQ();
        }
        return this.queryBean;
    }

    public SolrExampleCFQ filterQuery() {
        if (this.filterQueryBean == null) {
            this.filterQueryBean = new SolrExampleCFQ();
        }
        return this.filterQueryBean;
    }

    public HpSpecification specify() {
        this.setSpecified(true);
        if (this.specification == null) {
            this.specification = new HpSpecification();
        }
        return this.specification;
    }

    @Override
    public String[] getAllFields() {
        return Stream.of(SolrExampleDbm.values()).map(dbm -> dbm.fieldName()).toArray(count -> new String[count]);
    }

    @Override
    protected SolrQueryBean getQueryBean() {
        return this.query();
    }

    @Override
    protected SolrFilterQueryBean getFilterQueryBean() {
        return this.filterQuery();
    }

    @Override
    protected SolrSpecification getSpecification() {
        return this.specify();
    }

    public void addFacetQuery(SolrQBCall<SolrExampleCQ> qbLambda) {
        if (this.facetQueryList == null) {
            this.facetQueryList = new ArrayList<SolrExampleCQ>();
        }
        SolrExampleCQ queryBean = new SolrExampleCQ();
        qbLambda.callback(queryBean);
        facetQueryList.add(queryBean);
    }

    @Override
    public List<? extends SolrQueryBean> getFacetQueryList() {
        if (this.facetQueryList == null) {
            this.facetQueryList = new ArrayList<SolrExampleCQ>();
        }
        return facetQueryList;
    }

    public HpSpecification facetSpecify() {
        if (this.facetSpecification == null) {
            this.facetSpecification = new HpSpecification();
        }
        return this.facetSpecification;
    }

    @Override
    protected SolrSpecification getFacetSpecification() {
        return this.facetSpecify();
    }

    public static class HpSpecification extends AbstractSolrSpecification {
        // ===================================================================================
        //                                                                        SpricyColumn
        //                                                                        ============
        public void fieldLatestPurchaseDate() {
            this.addSpecifyField(SolrExampleDbm.LatestPurchaseDate);
        }
        public void fieldProductCategory() {
            this.addSpecifyField(SolrExampleDbm.ProductCategory);
        }
        public void fieldProductCategoryCode() {
            this.addSpecifyField(SolrExampleDbm.ProductCategoryCode);
        }
        public void fieldProductDescription() {
            this.addSpecifyField(SolrExampleDbm.ProductDescription);
        }
        public void fieldProductHandleCode() {
            this.addSpecifyField(SolrExampleDbm.ProductHandleCode);
        }
        public void fieldProductName() {
            this.addSpecifyField(SolrExampleDbm.ProductName);
        }
        public void fieldProductStatus() {
            this.addSpecifyField(SolrExampleDbm.ProductStatus);
        }
        public void fieldProductStatusCode() {
            this.addSpecifyField(SolrExampleDbm.ProductStatusCode);
        }
        public void fieldRegisterDatetime() {
            this.addSpecifyField(SolrExampleDbm.RegisterDatetime);
        }
        public void fieldRegisterUser() {
            this.addSpecifyField(SolrExampleDbm.RegisterUser);
        }
        public void fieldRegularPrice() {
            this.addSpecifyField(SolrExampleDbm.RegularPrice);
        }
        public void fieldUpdateDatetime() {
            this.addSpecifyField(SolrExampleDbm.UpdateDatetime);
        }
        public void fieldUpdateUser() {
            this.addSpecifyField(SolrExampleDbm.UpdateUser);
        }
    }
}
