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
package org.docksidestage.esflute.maihama.cbean.bs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.docksidestage.esflute.maihama.allcommon.EsAbstractConditionBean;
import org.docksidestage.esflute.maihama.bsentity.dbmeta.ProductDbm;
import org.docksidestage.esflute.maihama.cbean.ProductCB;
import org.docksidestage.esflute.maihama.cbean.cq.ProductCQ;
import org.docksidestage.esflute.maihama.cbean.cq.bs.BsProductCQ;
import org.dbflute.cbean.ConditionQuery;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author ESFlute (using FreeGen)
 */
public class BsProductCB extends EsAbstractConditionBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected BsProductCQ _conditionQuery;
    protected HpSpecification _specification;

    // ===================================================================================
    //                                                                             Control
    //                                                                             =======
    @Override
    public ProductDbm asDBMeta() {
        return ProductDbm.getInstance();
    }

    @Override
    public String asTableDbName() {
        return "product";
    }

    @Override
    public boolean hasSpecifiedColumn() {
        return _specification != null;
    }

    @Override
    public ConditionQuery localCQ() {
        return doGetConditionQuery();
    }

    // ===================================================================================
    //                                                                         Primary Key
    //                                                                         ===========
    public ProductCB acceptPK(String id) {
        assertObjectNotNull("id", id);
        BsProductCB cb = this;
        cb.query().docMeta().setId_Equal(id);
        return (ProductCB) this;
    }

    @Override
    public void acceptPrimaryKeyMap(Map<String, ? extends Object> primaryKeyMap) {
        acceptPK((String)primaryKeyMap.get("_id"));
    }

    // ===================================================================================
    //                                                                               Build
    //                                                                               =====

    @Override
    public SearchRequestBuilder build(SearchRequestBuilder builder) {
        if (_conditionQuery != null) {
            QueryBuilder queryBuilder = _conditionQuery.getQuery();
            if (queryBuilder != null) {
                builder.setQuery(queryBuilder);
            }
            _conditionQuery.getFieldSortBuilderList().forEach(sort -> {
                builder.addSort(sort);
            });
        }

        if (_specification != null) {
            builder.setFetchSource(_specification.columnList.toArray(new String[_specification.columnList.size()]), null);
        }

        return builder;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    public BsProductCQ query() {
        assertQueryPurpose();
        return doGetConditionQuery();
    }

    protected BsProductCQ doGetConditionQuery() {
        if (_conditionQuery == null) {
            _conditionQuery = createLocalCQ();
        }
        return _conditionQuery;
    }

    protected BsProductCQ createLocalCQ() {
        return new ProductCQ();
    }

    // ===================================================================================
    //                                                                             Specify
    //                                                                             =======
    public HpSpecification specify() {
        assertSpecifyPurpose();
        if (_specification == null) {
            _specification = new HpSpecification();
        }
        return _specification;
    }

    protected void assertQueryPurpose() {
    }

    protected void assertSpecifyPurpose() {
    }

    public static class HpSpecification {
        private List<String> columnList = new ArrayList<>();

        private void doColumn(String name) {
            columnList.add(name);
        }

        public void columnId() {
            doColumn("_id");
        }

        public void columnLatestPurchaseDate() {
            doColumn("latest_purchase_date");
        }
        public void columnProductCategory() {
            doColumn("product_category");
        }
        public void columnProductCategoryCode() {
            doColumn("product_category_code");
        }
        public void columnProductDescription() {
            doColumn("product_description");
        }
        public void columnProductHandleCode() {
            doColumn("product_handle_code");
        }
        public void columnProductName() {
            doColumn("product_name");
        }
        public void columnProductStatus() {
            doColumn("product_status");
        }
        public void columnProductStatusCode() {
            doColumn("product_status_code");
        }
        public void columnRegisterDatetime() {
            doColumn("register_datetime");
        }
        public void columnRegisterUser() {
            doColumn("register_user");
        }
        public void columnRegularPrice() {
            doColumn("regular_price");
        }
        public void columnUpdateDatetime() {
            doColumn("update_datetime");
        }
        public void columnUpdateUser() {
            doColumn("update_user");
        }
    }
}
