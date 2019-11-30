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

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.common.params.FacetParams;
import org.dbflute.util.DfStringUtil;

/**
 * Abstract class to generate query condition for Solr.
 * @author FreeGen
 */
public abstract class AbstractSolrConditionBean implements SolrConditionBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private static final String SOLR_QUERY_OPERATOR_KEY = "q.op";

    private static final String SOLR_ROUTER_KEY = "_route_";

    private static final Integer DEFAULT_FETCH_SIZE = 1000;

    private static final Integer DEFAULT_FACET_FETCH_SIZE = null;

    protected Integer fetchSize;

    protected Integer pageNumber;

    protected Integer facetFetchSize;

    protected Integer facetPageNumber;

    protected QueryOperator queryOperator;

    /** Flag indicating whether to use fq (filter query) or not. */
    protected boolean useFilterQuery;

    protected String route;

    protected SolrNativeQueryCall nativeQueryLambda;

    public enum QueryOperator {
        AND, OR
    }

    // ===================================================================================
    //                                                                              Paging
    //                                                                              ======
    @Override
    public void fetchFirst(Integer fetchSize) {
        this.fetchSize = fetchSize;
    }

    public void paging(Integer pageSize, Integer pageNumber) {
        this.fetchSize = pageSize;
        this.pageNumber = pageNumber;
    }

    @Override
    public Integer getPageNumer() {
        return pageNumber != null ? pageNumber : 1;
    }

    @Override
    public Integer getFetchSize() {
        return fetchSize != null ? fetchSize : DEFAULT_FETCH_SIZE;
    }

    public Integer getOffset() {
        if (getFetchSize() == null) {
            return null;
        }
        return getFetchSize() * (getPageNumer() - 1);
    }

    // ===================================================================================
    //                                                                        Fetch Paging
    //                                                                        ============
    public void facetFetchFirst(Integer fetchSize) {
        this.facetFetchSize = fetchSize;
    }

    public void facetPaging(Integer pageSize, Integer pageNumber) {
        this.facetFetchSize = pageSize;
        this.facetPageNumber = pageNumber;
    }

    @Override
    public Integer getFacetPageNumer() {
        return facetPageNumber != null ? facetPageNumber : 1;
    }

    @Override
    public Integer getFacetFetchSize() {
        return facetFetchSize != null ? facetFetchSize : DEFAULT_FACET_FETCH_SIZE;
    }

    public Integer getFacetOffset() {
        if (getFacetFetchSize() == null) {
            return null;
        }
        return getFacetFetchSize() * (getFacetPageNumer() - 1);
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

    protected List<SortClause> getSolrSortClauseList() {
        return this.getQueryBean().getSolrSortClauseList();
    }

    protected String getMinimumQuery() {
        return getQueryBean().getMinimumQueryString();
    }

    protected String[] getQueryArray() {
        List<String> list = getQueryBean().getQueryList();
        return list.size() <= 1 ? new String[] {} : list.subList(1, list.size()).toArray(new String[] {});
    }

    protected String getQueryString() {
        return getQueryBean().getQueryString();
    }

    protected String[] getFilterQueryArray() {
        List<String> list = getFilterQueryBean().getQueryList();
        return list.toArray(new String[] {});
    }

    public void nativeQuery(SolrNativeQueryCall nativeQueryLambda) {
        this.nativeQueryLambda = nativeQueryLambda;
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

        if (this.getOffset() != null && !this.getOffset().equals(0)) {
            query.setStart(this.getOffset());
        }
        query.setRows(this.getFetchSize());

        if (Boolean.parseBoolean(query.get(FacetParams.FACET))) {
            if (this.getFacetOffset() != null && !this.getFacetOffset().equals(0)) {
                query.set(FacetParams.FACET_OFFSET, this.getFacetOffset());
            }
            if (this.getFacetFetchSize() != null) {
                query.setFacetLimit(this.getFacetFetchSize());
            }
        }

        if (nativeQueryLambda != null) {
            nativeQueryLambda.callback(query);
        }

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
        return buildSolrQuery().toString();
    }
}
