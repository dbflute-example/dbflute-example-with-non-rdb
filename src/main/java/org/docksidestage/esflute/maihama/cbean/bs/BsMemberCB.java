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
import org.docksidestage.esflute.maihama.bsentity.dbmeta.MemberDbm;
import org.docksidestage.esflute.maihama.cbean.MemberCB;
import org.docksidestage.esflute.maihama.cbean.cq.MemberCQ;
import org.docksidestage.esflute.maihama.cbean.cq.bs.BsMemberCQ;
import org.docksidestage.esflute.maihama.cbean.ca.MemberCA;
import org.docksidestage.esflute.maihama.cbean.ca.bs.BsMemberCA;
import org.dbflute.cbean.ConditionQuery;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;

/**
 * @author ESFlute (using FreeGen)
 */
public class BsMemberCB extends EsAbstractConditionBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected BsMemberCQ _conditionQuery;
    protected BsMemberCA _conditionAggregation;
    protected HpSpecification _specification;

    // ===================================================================================
    //                                                                             Control
    //                                                                             =======
    @Override
    public MemberDbm asDBMeta() {
        return MemberDbm.getInstance();
    }

    @Override
    public String asTableDbName() {
        return "member";
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
    public MemberCB acceptPK(String id) {
        assertObjectNotNull("id", id);
        BsMemberCB cb = this;
        cb.query().docMeta().setId_Equal(id);
        return (MemberCB) this;
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

        if (_conditionAggregation != null) {
            _conditionAggregation.getAggregationBuilderList().forEach(builder::addAggregation);
        }

        if (_specification != null) {
            builder.setFetchSource(_specification.columnList.toArray(new String[_specification.columnList.size()]), null);
        }

        return builder;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    public BsMemberCQ query() {
        assertQueryPurpose();
        return doGetConditionQuery();
    }

    protected BsMemberCQ doGetConditionQuery() {
        if (_conditionQuery == null) {
            _conditionQuery = createLocalCQ();
        }
        return _conditionQuery;
    }

    protected BsMemberCQ createLocalCQ() {
        return new MemberCQ();
    }

    // ===================================================================================
    //                                                                         Aggregation
    //                                                                         ===========
    public BsMemberCA aggregation() {
        assertAggregationPurpose();
        return doGetConditionAggregation();
    }

    protected BsMemberCA doGetConditionAggregation() {
        if (_conditionAggregation == null) {
            _conditionAggregation = createLocalCA();
        }
        return _conditionAggregation;
    }

    protected BsMemberCA createLocalCA() {
        return new MemberCA();
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

    protected void assertAggregationPurpose() {
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

        public void columnAccount() {
            doColumn("account");
        }
        public void columnName() {
            doColumn("name");
        }
    }
}
