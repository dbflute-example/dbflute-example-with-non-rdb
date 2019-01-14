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
package org.dbflute.solr.cbean;

import java.util.Arrays;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.dbflute.util.DfStringUtil;

/**
 * Abstract class to generate query condition for Solr.
 * @author FreeGen
 */
public abstract class AbstractSolrConditionBean implements SolrConditionBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private static final Integer DEFAULT_PAGE_SIZE = 1000;

    protected Integer pageSize;

    protected Integer pageNumber;

    private static final String SOLR_QUERY_OPERATOR_KEY = "q.op";

    protected QueryOperator queryOperator;

    public enum QueryOperator {
        AND, OR
    }

    /** Flag indicating whether to use fq (filter query) or not. */
    protected boolean useFilterQuery;

    private static final String SOLR_ROUTER_KEY = "_route_";

    protected String route;

    // ===================================================================================
    //                                                                              Paging
    //                                                                              ======
    public void paging(Integer pageSize, Integer pageNumber) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    @Override
    public Integer getPageNumer() {
        if (pageNumber != null) {
            return pageNumber;
        }
        return 1;
    }

    public Integer getPageStart() {
        return getPageSize() * (getPageNumer() - 1);
    }

    @Override
    public Integer getPageSize() {
        if (pageSize != null) {
            return pageSize;
        }
        return DEFAULT_PAGE_SIZE;
    }

    // ===================================================================================
    //                                                                       QueryOperator
    //                                                                       =============
    public void setQueryOperator(QueryOperator queryOperator) {
        this.queryOperator = queryOperator;
    }

    // ===================================================================================
    //                                                                             Routing
    //                                                                             =======
    public void setRoute(String route) {
        this.route = route;
    }

    public String getRoute() {
        return this.route;
    }

    // ===================================================================================
    //                                                                            Abstract
    //                                                                            ========
    protected abstract SolrQueryBean getQueryBean();

    protected abstract SolrFilterQueryBean getFilterQueryBean();

    protected abstract SolrSpecification getSpecification();

    protected abstract List<? extends SolrQueryBean> getFacetQueryList();

    protected abstract SolrSpecification getFacetSpecification();

    // ===================================================================================
    //                                                                          Query Info
    //                                                                          ==========
    public boolean isUseSort() {
        return this.getQueryBean().isUseSort();
    }

    public List<SortClause> getSolrSortClauseList() {
        return this.getQueryBean().getSolrSortClauseList();
    }

    public String getMinimumQuery() {
        return getQueryBean().getMinimumQueryString();
    }

    public String[] getQueryArray() {
        List<String> list = getQueryBean().getQueryList();
        return list.size() <= 1 ? new String[] {} : list.subList(1, list.size()).toArray(new String[] {});
    }

    public String getQueryString() {
        return getQueryBean().getQueryString();
    }

    public String[] getFilterQueryArray() {
        List<String> list = getFilterQueryBean().getQueryList();
        return list.toArray(new String[] {});
    }

    // ===================================================================================
    //                                                                        Specify Info
    //                                                                        ============
    @Override
    public String[] getSpecifyPropertys() {
        return this.getSpecification().getSpecifyPropertys();
    }

    public String[] getSpecifyFields() {
        return this.getSpecification().getSpecifyFields();
    }

    // ===================================================================================
    //                                                                                Info
    //                                                                                ====
    @Override
    public SolrQuery buildSolrQuery() {
        SolrQuery query = new SolrQuery();
        if (queryOperator != null) {
            query.add(SOLR_QUERY_OPERATOR_KEY, queryOperator.name());
        }
        if (this.specify().isSpecify()) {
            query.setFields(this.getSpecifyFields());
        }
        query.setIncludeScore(this.specify().isScoreEnable());
        if (this.isUseFilterQuery()) {
            query.setQuery(this.getMinimumQuery());
            query.setFilterQueries(this.getQueryArray());
        } else {
            query.setQuery(this.getQueryString());
            String[] filterQueryArray = this.getFilterQueryArray();
            if (filterQueryArray != null && filterQueryArray.length != 0) {
                query.setFilterQueries(this.getFilterQueryArray());
            }
        }
        if (this.isUseSort()) {
            for (SortClause sortClause : this.getSolrSortClauseList()) {
                query.addSort(sortClause);
            }
        }

        for (SolrQueryBean queryBean : this.getFacetQueryList()) {
            query.addFacetQuery(queryBean.getQueryString());
        }

        SolrSpecification facetSpecifyBean = getFacetSpecification();
        if (facetSpecifyBean.getSpecifyFields().length > 0) {
            query.addFacetField(facetSpecifyBean.getSpecifyFields());
        }

        if (DfStringUtil.is_NotNull_and_NotEmpty(route)) {
            query.add(SOLR_ROUTER_KEY, route);
        }

        query.setStart(this.getPageStart());
        query.setRows(this.getPageSize());

        return query;
    }

    @Override
    public String toString() {
        return toDisplayCondition();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public boolean isUseFilterQuery() {
        return useFilterQuery;
    }

    /**
     * Set whether to use filter query or not.
     * @param useFilterQuery Boolean value indicating whether to use filter query (NotNull)
     * @deprecated Use XxxCB#filterQuery()
     */
    @Deprecated
    public void setUseFilterQuery(boolean useFilterQuery) {
        this.useFilterQuery = useFilterQuery;
    }

    public boolean isFacetQuery() {
        return this.getFacetQueryList() != null && this.getFacetQueryList().size() > 0;
    }

    /**
     * Return query condition String.
     * @return String of query condition
     */
    public String toDisplayCondition() {
        StringBuilder sb = new StringBuilder();
        buildSolrQuery().getMap().forEach((key, value) -> {
            sb.append("").append(key).append(" = ").append(Arrays.asList(value)).append(" \n");
        });

        return sb.toString();
    }
}
