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
package org.docksidestage.solr.cbean.cfq.bs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.dbflute.util.DfStringUtil;

import org.dbflute.solr.cbean.AbstractSolrFilterQueryBean;
import org.dbflute.solr.cbean.SolrFQBCall;
import org.dbflute.solr.cbean.SolrQueryBuilder;
import org.dbflute.solr.cbean.SolrQueryLogicalOperator;
import org.dbflute.solr.cbean.SolrSetRangeSearchBean;

import org.docksidestage.solr.cbean.cfq.SolrExampleCFQ;

/**
 * Base FilterQuery class of Solr schema "Example."
 * @author FreeGen
 */
public class SolrBsExampleCFQ extends AbstractSolrFilterQueryBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** Query Attribute for latest_purchase_date (tdate) */
    protected String latestPurchaseDateQuery;

    /** Query Attribute for product_category (string) */
    protected String productCategoryQuery;

    /** Query Attribute for product_category_code (string) */
    protected String productCategoryCodeQuery;

    /** Query Attribute for product_handle_code (string) */
    protected String productHandleCodeQuery;

    /** Query Attribute for product_name (string) */
    protected String productNameQuery;

    /** Query Attribute for product_status (string) */
    protected String productStatusQuery;

    /** Query Attribute for product_status_code (string) */
    protected String productStatusCodeQuery;

    /** Query Attribute for register_datetime (tdate) */
    protected String registerDatetimeQuery;

    /** Query Attribute for register_user (string) */
    protected String registerUserQuery;

    /** Query Attribute for regular_price (long) */
    protected String regularPriceQuery;

    /** Query Attribute for update_datetime (tdate) */
    protected String updateDatetimeQuery;

    /** Query Attribute for update_user (string) */
    protected String updateUserQuery;

    // ===================================================================================
    //                                                                          Conditions
    //                                                                          ==========
    // ===========================================================
    // Query Setter for latest_purchase_date (tdate)
    //                                                  ==========
    /**
     * ExistsQuery( q=latest_purchase_date:* )
     */
    public void setLatestPurchaseDate_Exists() {
        SolrQueryBuilder.assertNullQuery("latest_purchase_date", this.latestPurchaseDateQuery);
        this.latestPurchaseDateQuery = SolrQueryBuilder.queryBuilderForExists("latest_purchase_date");
    }

    /**
     * ExistsQuery( q=NOT latest_purchase_date:* )
     */
    public void setLatestPurchaseDate_NotExists() {
        SolrQueryBuilder.assertNullQuery("latest_purchase_date", this.latestPurchaseDateQuery);
        this.latestPurchaseDateQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("latest_purchase_date"), false);
    }

    /**
     * ExistsQuery( q=(query OR (NOT latest_purchase_date:*)) )
     */
    public void addLatestPurchaseDate_NotExists() {
        SolrQueryBuilder.assertNotNullQuery("latest_purchase_date", this.latestPurchaseDateQuery);
        this.latestPurchaseDateQuery = SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(this.latestPurchaseDateQuery, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("latest_purchase_date"), true)), SolrQueryLogicalOperator.OR));
    }

    public void setLatestPurchaseDate_Equal(java.time.LocalDateTime query) {
        SolrQueryBuilder.assertNullQuery("latest_purchase_date", this.latestPurchaseDateQuery);
        if (query != null) {
            this.latestPurchaseDateQuery = SolrQueryBuilder.queryBuilderForEqual("latest_purchase_date", query.toString());
        }
    }

    public void setLatestPurchaseDate_NotEqual(java.time.LocalDateTime query) {
        SolrQueryBuilder.assertNullQuery("latest_purchase_date", this.latestPurchaseDateQuery);
        if (query != null) {
            this.latestPurchaseDateQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("latest_purchase_date", query.toString()), false);
        }
    }

    public void setLatestPurchaseDate_RangeSearchFrom(java.time.LocalDateTime from) {
        if (from != null) {
            setLatestPurchaseDate_RangeSearch(from, null);
        }
    }

    public void setLatestPurchaseDate_RangeSearchTo(java.time.LocalDateTime to) {
        if (to != null) {
            setLatestPurchaseDate_RangeSearch(null, to);
        }
    }

    public void setLatestPurchaseDate_RangeSearch(java.time.LocalDateTime from, java.time.LocalDateTime to) {
        SolrQueryBuilder.assertNullQuery("latest_purchase_date", this.latestPurchaseDateQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("latest_purchase_date", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.latestPurchaseDateQuery = convertedQuery;
        }
    }

    // ===========================================================
    // Query Setter for product_category (string)
    //                                                  ==========
    /**
     * ExistsQuery( q=product_category:* )
     */
    public void setProductCategory_Exists() {
        SolrQueryBuilder.assertNullQuery("product_category", this.productCategoryQuery);
        this.productCategoryQuery = SolrQueryBuilder.queryBuilderForExists("product_category");
    }

    /**
     * ExistsQuery( q=NOT product_category:* )
     */
    public void setProductCategory_NotExists() {
        SolrQueryBuilder.assertNullQuery("product_category", this.productCategoryQuery);
        this.productCategoryQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_category"), false);
    }

    /**
     * ExistsQuery( q=(query OR (NOT product_category:*)) )
     */
    public void addProductCategory_NotExists() {
        SolrQueryBuilder.assertNotNullQuery("product_category", this.productCategoryQuery);
        this.productCategoryQuery = SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(this.productCategoryQuery, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_category"), true)), SolrQueryLogicalOperator.OR));
    }

    public void setProductCategory_Equal(String query) {
        SolrQueryBuilder.assertNullQuery("product_category", this.productCategoryQuery);
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productCategoryQuery = SolrQueryBuilder.queryBuilderForEqual("product_category", query.toString());
        }
    }

    public void setProductCategory_NotEqual(String query) {
        SolrQueryBuilder.assertNullQuery("product_category", this.productCategoryQuery);
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productCategoryQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("product_category", query.toString()), false);
        }
    }

    public void setProductCategory_InScope(Collection<String> queryList) {
        this.setProductCategory_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setProductCategory_InScope(Collection<String> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("product_category", this.productCategoryQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_category", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryQuery = convertedQuery;
        }
    }

    public void setProductCategory_NotInScope(Collection<String> queryList) {
        SolrQueryBuilder.assertNullQuery("product_category", this.productCategoryQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_category", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryQuery = convertedQuery;
        }
    }

    public void setProductCategory_PrefixSearch(String query) {
        SolrQueryBuilder.assertNullQuery("product_category", this.productCategoryQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForPrefixSearch("product_category", query);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryQuery = convertedQuery;
        }
    }

    public void setProductCategory_RangeSearchFrom(String from) {
        if (DfStringUtil.is_NotNull_and_NotEmpty(from)) {
            setProductCategory_RangeSearch(from, null);
        }
    }

    public void setProductCategory_RangeSearchTo(String to) {
        if (DfStringUtil.is_NotNull_and_NotEmpty(to)) {
            setProductCategory_RangeSearch(null, to);
        }
    }

    public void setProductCategory_RangeSearch(String from, String to) {
        SolrQueryBuilder.assertNullQuery("product_category", this.productCategoryQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("product_category", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryQuery = convertedQuery;
        }
    }

    public void setProductCategory_SetRangeSearch(String cd, String from, String to) {
        SolrQueryBuilder.assertNullQuery("product_category", this.productCategoryQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearch("product_category", cd, from, to, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryQuery = convertedQuery;
        }
    }

    public void setProductCategory_SetRangeSearchInScope(Collection<SolrSetRangeSearchBean> beanList) {
        SolrQueryBuilder.assertNullQuery("product_category", this.productCategoryQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearchInScope("product_category", beanList, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryQuery = convertedQuery;
        }
    }

    // ===========================================================
    // Query Setter for product_category_code (string)
    //                                                  ==========
    /**
     * ExistsQuery( q=product_category_code:* )
     */
    public void setProductCategoryCode_Exists() {
        SolrQueryBuilder.assertNullQuery("product_category_code", this.productCategoryCodeQuery);
        this.productCategoryCodeQuery = SolrQueryBuilder.queryBuilderForExists("product_category_code");
    }

    /**
     * ExistsQuery( q=NOT product_category_code:* )
     */
    public void setProductCategoryCode_NotExists() {
        SolrQueryBuilder.assertNullQuery("product_category_code", this.productCategoryCodeQuery);
        this.productCategoryCodeQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_category_code"), false);
    }

    /**
     * ExistsQuery( q=(query OR (NOT product_category_code:*)) )
     */
    public void addProductCategoryCode_NotExists() {
        SolrQueryBuilder.assertNotNullQuery("product_category_code", this.productCategoryCodeQuery);
        this.productCategoryCodeQuery = SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(this.productCategoryCodeQuery, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_category_code"), true)), SolrQueryLogicalOperator.OR));
    }

    public void setProductCategoryCode_Equal(String query) {
        SolrQueryBuilder.assertNullQuery("product_category_code", this.productCategoryCodeQuery);
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productCategoryCodeQuery = SolrQueryBuilder.queryBuilderForEqual("product_category_code", query.toString());
        }
    }

    public void setProductCategoryCode_NotEqual(String query) {
        SolrQueryBuilder.assertNullQuery("product_category_code", this.productCategoryCodeQuery);
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productCategoryCodeQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("product_category_code", query.toString()), false);
        }
    }

    public void setProductCategoryCode_InScope(Collection<String> queryList) {
        this.setProductCategoryCode_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setProductCategoryCode_InScope(Collection<String> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("product_category_code", this.productCategoryCodeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_category_code", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryCodeQuery = convertedQuery;
        }
    }

    public void setProductCategoryCode_NotInScope(Collection<String> queryList) {
        SolrQueryBuilder.assertNullQuery("product_category_code", this.productCategoryCodeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_category_code", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryCodeQuery = convertedQuery;
        }
    }

    public void setProductCategoryCode_PrefixSearch(String query) {
        SolrQueryBuilder.assertNullQuery("product_category_code", this.productCategoryCodeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForPrefixSearch("product_category_code", query);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryCodeQuery = convertedQuery;
        }
    }

    public void setProductCategoryCode_RangeSearchFrom(String from) {
        if (DfStringUtil.is_NotNull_and_NotEmpty(from)) {
            setProductCategoryCode_RangeSearch(from, null);
        }
    }

    public void setProductCategoryCode_RangeSearchTo(String to) {
        if (DfStringUtil.is_NotNull_and_NotEmpty(to)) {
            setProductCategoryCode_RangeSearch(null, to);
        }
    }

    public void setProductCategoryCode_RangeSearch(String from, String to) {
        SolrQueryBuilder.assertNullQuery("product_category_code", this.productCategoryCodeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("product_category_code", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryCodeQuery = convertedQuery;
        }
    }

    public void setProductCategoryCode_SetRangeSearch(String cd, String from, String to) {
        SolrQueryBuilder.assertNullQuery("product_category_code", this.productCategoryCodeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearch("product_category_code", cd, from, to, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryCodeQuery = convertedQuery;
        }
    }

    public void setProductCategoryCode_SetRangeSearchInScope(Collection<SolrSetRangeSearchBean> beanList) {
        SolrQueryBuilder.assertNullQuery("product_category_code", this.productCategoryCodeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearchInScope("product_category_code", beanList, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryCodeQuery = convertedQuery;
        }
    }

    // ===========================================================
    // Query Setter for product_handle_code (string)
    //                                                  ==========
    /**
     * ExistsQuery( q=product_handle_code:* )
     */
    public void setProductHandleCode_Exists() {
        SolrQueryBuilder.assertNullQuery("product_handle_code", this.productHandleCodeQuery);
        this.productHandleCodeQuery = SolrQueryBuilder.queryBuilderForExists("product_handle_code");
    }

    /**
     * ExistsQuery( q=NOT product_handle_code:* )
     */
    public void setProductHandleCode_NotExists() {
        SolrQueryBuilder.assertNullQuery("product_handle_code", this.productHandleCodeQuery);
        this.productHandleCodeQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_handle_code"), false);
    }

    /**
     * ExistsQuery( q=(query OR (NOT product_handle_code:*)) )
     */
    public void addProductHandleCode_NotExists() {
        SolrQueryBuilder.assertNotNullQuery("product_handle_code", this.productHandleCodeQuery);
        this.productHandleCodeQuery = SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(this.productHandleCodeQuery, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_handle_code"), true)), SolrQueryLogicalOperator.OR));
    }

    public void setProductHandleCode_Equal(String query) {
        SolrQueryBuilder.assertNullQuery("product_handle_code", this.productHandleCodeQuery);
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productHandleCodeQuery = SolrQueryBuilder.queryBuilderForEqual("product_handle_code", query.toString());
        }
    }

    public void setProductHandleCode_NotEqual(String query) {
        SolrQueryBuilder.assertNullQuery("product_handle_code", this.productHandleCodeQuery);
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productHandleCodeQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("product_handle_code", query.toString()), false);
        }
    }

    public void setProductHandleCode_InScope(Collection<String> queryList) {
        this.setProductHandleCode_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setProductHandleCode_InScope(Collection<String> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("product_handle_code", this.productHandleCodeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_handle_code", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productHandleCodeQuery = convertedQuery;
        }
    }

    public void setProductHandleCode_NotInScope(Collection<String> queryList) {
        SolrQueryBuilder.assertNullQuery("product_handle_code", this.productHandleCodeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_handle_code", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productHandleCodeQuery = convertedQuery;
        }
    }

    public void setProductHandleCode_PrefixSearch(String query) {
        SolrQueryBuilder.assertNullQuery("product_handle_code", this.productHandleCodeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForPrefixSearch("product_handle_code", query);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productHandleCodeQuery = convertedQuery;
        }
    }

    public void setProductHandleCode_RangeSearchFrom(String from) {
        if (DfStringUtil.is_NotNull_and_NotEmpty(from)) {
            setProductHandleCode_RangeSearch(from, null);
        }
    }

    public void setProductHandleCode_RangeSearchTo(String to) {
        if (DfStringUtil.is_NotNull_and_NotEmpty(to)) {
            setProductHandleCode_RangeSearch(null, to);
        }
    }

    public void setProductHandleCode_RangeSearch(String from, String to) {
        SolrQueryBuilder.assertNullQuery("product_handle_code", this.productHandleCodeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("product_handle_code", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productHandleCodeQuery = convertedQuery;
        }
    }

    public void setProductHandleCode_SetRangeSearch(String cd, String from, String to) {
        SolrQueryBuilder.assertNullQuery("product_handle_code", this.productHandleCodeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearch("product_handle_code", cd, from, to, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productHandleCodeQuery = convertedQuery;
        }
    }

    public void setProductHandleCode_SetRangeSearchInScope(Collection<SolrSetRangeSearchBean> beanList) {
        SolrQueryBuilder.assertNullQuery("product_handle_code", this.productHandleCodeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearchInScope("product_handle_code", beanList, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productHandleCodeQuery = convertedQuery;
        }
    }

    // ===========================================================
    // Query Setter for product_name (string)
    //                                                  ==========
    /**
     * ExistsQuery( q=product_name:* )
     */
    public void setProductName_Exists() {
        SolrQueryBuilder.assertNullQuery("product_name", this.productNameQuery);
        this.productNameQuery = SolrQueryBuilder.queryBuilderForExists("product_name");
    }

    /**
     * ExistsQuery( q=NOT product_name:* )
     */
    public void setProductName_NotExists() {
        SolrQueryBuilder.assertNullQuery("product_name", this.productNameQuery);
        this.productNameQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_name"), false);
    }

    /**
     * ExistsQuery( q=(query OR (NOT product_name:*)) )
     */
    public void addProductName_NotExists() {
        SolrQueryBuilder.assertNotNullQuery("product_name", this.productNameQuery);
        this.productNameQuery = SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(this.productNameQuery, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_name"), true)), SolrQueryLogicalOperator.OR));
    }

    public void setProductName_Equal(String query) {
        SolrQueryBuilder.assertNullQuery("product_name", this.productNameQuery);
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productNameQuery = SolrQueryBuilder.queryBuilderForEqual("product_name", query.toString());
        }
    }

    public void setProductName_NotEqual(String query) {
        SolrQueryBuilder.assertNullQuery("product_name", this.productNameQuery);
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productNameQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("product_name", query.toString()), false);
        }
    }

    public void setProductName_InScope(Collection<String> queryList) {
        this.setProductName_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setProductName_InScope(Collection<String> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("product_name", this.productNameQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_name", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productNameQuery = convertedQuery;
        }
    }

    public void setProductName_NotInScope(Collection<String> queryList) {
        SolrQueryBuilder.assertNullQuery("product_name", this.productNameQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_name", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productNameQuery = convertedQuery;
        }
    }

    public void setProductName_PrefixSearch(String query) {
        SolrQueryBuilder.assertNullQuery("product_name", this.productNameQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForPrefixSearch("product_name", query);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productNameQuery = convertedQuery;
        }
    }

    public void setProductName_RangeSearchFrom(String from) {
        if (DfStringUtil.is_NotNull_and_NotEmpty(from)) {
            setProductName_RangeSearch(from, null);
        }
    }

    public void setProductName_RangeSearchTo(String to) {
        if (DfStringUtil.is_NotNull_and_NotEmpty(to)) {
            setProductName_RangeSearch(null, to);
        }
    }

    public void setProductName_RangeSearch(String from, String to) {
        SolrQueryBuilder.assertNullQuery("product_name", this.productNameQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("product_name", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productNameQuery = convertedQuery;
        }
    }

    public void setProductName_SetRangeSearch(String cd, String from, String to) {
        SolrQueryBuilder.assertNullQuery("product_name", this.productNameQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearch("product_name", cd, from, to, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productNameQuery = convertedQuery;
        }
    }

    public void setProductName_SetRangeSearchInScope(Collection<SolrSetRangeSearchBean> beanList) {
        SolrQueryBuilder.assertNullQuery("product_name", this.productNameQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearchInScope("product_name", beanList, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productNameQuery = convertedQuery;
        }
    }

    // ===========================================================
    // Query Setter for product_status (string)
    //                                                  ==========
    /**
     * ExistsQuery( q=product_status:* )
     */
    public void setProductStatus_Exists() {
        SolrQueryBuilder.assertNullQuery("product_status", this.productStatusQuery);
        this.productStatusQuery = SolrQueryBuilder.queryBuilderForExists("product_status");
    }

    /**
     * ExistsQuery( q=NOT product_status:* )
     */
    public void setProductStatus_NotExists() {
        SolrQueryBuilder.assertNullQuery("product_status", this.productStatusQuery);
        this.productStatusQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_status"), false);
    }

    /**
     * ExistsQuery( q=(query OR (NOT product_status:*)) )
     */
    public void addProductStatus_NotExists() {
        SolrQueryBuilder.assertNotNullQuery("product_status", this.productStatusQuery);
        this.productStatusQuery = SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(this.productStatusQuery, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_status"), true)), SolrQueryLogicalOperator.OR));
    }

    public void setProductStatus_Equal(String query) {
        SolrQueryBuilder.assertNullQuery("product_status", this.productStatusQuery);
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productStatusQuery = SolrQueryBuilder.queryBuilderForEqual("product_status", query.toString());
        }
    }

    public void setProductStatus_NotEqual(String query) {
        SolrQueryBuilder.assertNullQuery("product_status", this.productStatusQuery);
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productStatusQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("product_status", query.toString()), false);
        }
    }

    public void setProductStatus_InScope(Collection<String> queryList) {
        this.setProductStatus_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setProductStatus_InScope(Collection<String> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("product_status", this.productStatusQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_status", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusQuery = convertedQuery;
        }
    }

    public void setProductStatus_NotInScope(Collection<String> queryList) {
        SolrQueryBuilder.assertNullQuery("product_status", this.productStatusQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_status", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusQuery = convertedQuery;
        }
    }

    public void setProductStatus_PrefixSearch(String query) {
        SolrQueryBuilder.assertNullQuery("product_status", this.productStatusQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForPrefixSearch("product_status", query);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusQuery = convertedQuery;
        }
    }

    public void setProductStatus_RangeSearchFrom(String from) {
        if (DfStringUtil.is_NotNull_and_NotEmpty(from)) {
            setProductStatus_RangeSearch(from, null);
        }
    }

    public void setProductStatus_RangeSearchTo(String to) {
        if (DfStringUtil.is_NotNull_and_NotEmpty(to)) {
            setProductStatus_RangeSearch(null, to);
        }
    }

    public void setProductStatus_RangeSearch(String from, String to) {
        SolrQueryBuilder.assertNullQuery("product_status", this.productStatusQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("product_status", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusQuery = convertedQuery;
        }
    }

    public void setProductStatus_SetRangeSearch(String cd, String from, String to) {
        SolrQueryBuilder.assertNullQuery("product_status", this.productStatusQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearch("product_status", cd, from, to, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusQuery = convertedQuery;
        }
    }

    public void setProductStatus_SetRangeSearchInScope(Collection<SolrSetRangeSearchBean> beanList) {
        SolrQueryBuilder.assertNullQuery("product_status", this.productStatusQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearchInScope("product_status", beanList, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusQuery = convertedQuery;
        }
    }

    // ===========================================================
    // Query Setter for product_status_code (string)
    //                                                  ==========
    /**
     * ExistsQuery( q=product_status_code:* )
     */
    public void setProductStatusCode_Exists() {
        SolrQueryBuilder.assertNullQuery("product_status_code", this.productStatusCodeQuery);
        this.productStatusCodeQuery = SolrQueryBuilder.queryBuilderForExists("product_status_code");
    }

    /**
     * ExistsQuery( q=NOT product_status_code:* )
     */
    public void setProductStatusCode_NotExists() {
        SolrQueryBuilder.assertNullQuery("product_status_code", this.productStatusCodeQuery);
        this.productStatusCodeQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_status_code"), false);
    }

    /**
     * ExistsQuery( q=(query OR (NOT product_status_code:*)) )
     */
    public void addProductStatusCode_NotExists() {
        SolrQueryBuilder.assertNotNullQuery("product_status_code", this.productStatusCodeQuery);
        this.productStatusCodeQuery = SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(this.productStatusCodeQuery, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_status_code"), true)), SolrQueryLogicalOperator.OR));
    }

    public void setProductStatusCode_Equal(String query) {
        SolrQueryBuilder.assertNullQuery("product_status_code", this.productStatusCodeQuery);
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productStatusCodeQuery = SolrQueryBuilder.queryBuilderForEqual("product_status_code", query.toString());
        }
    }

    public void setProductStatusCode_NotEqual(String query) {
        SolrQueryBuilder.assertNullQuery("product_status_code", this.productStatusCodeQuery);
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productStatusCodeQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("product_status_code", query.toString()), false);
        }
    }

    public void setProductStatusCode_InScope(Collection<String> queryList) {
        this.setProductStatusCode_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setProductStatusCode_InScope(Collection<String> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("product_status_code", this.productStatusCodeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_status_code", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusCodeQuery = convertedQuery;
        }
    }

    public void setProductStatusCode_NotInScope(Collection<String> queryList) {
        SolrQueryBuilder.assertNullQuery("product_status_code", this.productStatusCodeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_status_code", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusCodeQuery = convertedQuery;
        }
    }

    public void setProductStatusCode_PrefixSearch(String query) {
        SolrQueryBuilder.assertNullQuery("product_status_code", this.productStatusCodeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForPrefixSearch("product_status_code", query);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusCodeQuery = convertedQuery;
        }
    }

    public void setProductStatusCode_RangeSearchFrom(String from) {
        if (DfStringUtil.is_NotNull_and_NotEmpty(from)) {
            setProductStatusCode_RangeSearch(from, null);
        }
    }

    public void setProductStatusCode_RangeSearchTo(String to) {
        if (DfStringUtil.is_NotNull_and_NotEmpty(to)) {
            setProductStatusCode_RangeSearch(null, to);
        }
    }

    public void setProductStatusCode_RangeSearch(String from, String to) {
        SolrQueryBuilder.assertNullQuery("product_status_code", this.productStatusCodeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("product_status_code", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusCodeQuery = convertedQuery;
        }
    }

    public void setProductStatusCode_SetRangeSearch(String cd, String from, String to) {
        SolrQueryBuilder.assertNullQuery("product_status_code", this.productStatusCodeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearch("product_status_code", cd, from, to, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusCodeQuery = convertedQuery;
        }
    }

    public void setProductStatusCode_SetRangeSearchInScope(Collection<SolrSetRangeSearchBean> beanList) {
        SolrQueryBuilder.assertNullQuery("product_status_code", this.productStatusCodeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearchInScope("product_status_code", beanList, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusCodeQuery = convertedQuery;
        }
    }

    // ===========================================================
    // Query Setter for register_datetime (tdate)
    //                                                  ==========
    /**
     * ExistsQuery( q=register_datetime:* )
     */
    public void setRegisterDatetime_Exists() {
        SolrQueryBuilder.assertNullQuery("register_datetime", this.registerDatetimeQuery);
        this.registerDatetimeQuery = SolrQueryBuilder.queryBuilderForExists("register_datetime");
    }

    /**
     * ExistsQuery( q=NOT register_datetime:* )
     */
    public void setRegisterDatetime_NotExists() {
        SolrQueryBuilder.assertNullQuery("register_datetime", this.registerDatetimeQuery);
        this.registerDatetimeQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("register_datetime"), false);
    }

    /**
     * ExistsQuery( q=(query OR (NOT register_datetime:*)) )
     */
    public void addRegisterDatetime_NotExists() {
        SolrQueryBuilder.assertNotNullQuery("register_datetime", this.registerDatetimeQuery);
        this.registerDatetimeQuery = SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(this.registerDatetimeQuery, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("register_datetime"), true)), SolrQueryLogicalOperator.OR));
    }

    public void setRegisterDatetime_Equal(java.time.LocalDateTime query) {
        SolrQueryBuilder.assertNullQuery("register_datetime", this.registerDatetimeQuery);
        if (query != null) {
            this.registerDatetimeQuery = SolrQueryBuilder.queryBuilderForEqual("register_datetime", query.toString());
        }
    }

    public void setRegisterDatetime_NotEqual(java.time.LocalDateTime query) {
        SolrQueryBuilder.assertNullQuery("register_datetime", this.registerDatetimeQuery);
        if (query != null) {
            this.registerDatetimeQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("register_datetime", query.toString()), false);
        }
    }

    public void setRegisterDatetime_RangeSearchFrom(java.time.LocalDateTime from) {
        if (from != null) {
            setRegisterDatetime_RangeSearch(from, null);
        }
    }

    public void setRegisterDatetime_RangeSearchTo(java.time.LocalDateTime to) {
        if (to != null) {
            setRegisterDatetime_RangeSearch(null, to);
        }
    }

    public void setRegisterDatetime_RangeSearch(java.time.LocalDateTime from, java.time.LocalDateTime to) {
        SolrQueryBuilder.assertNullQuery("register_datetime", this.registerDatetimeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("register_datetime", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.registerDatetimeQuery = convertedQuery;
        }
    }

    // ===========================================================
    // Query Setter for register_user (string)
    //                                                  ==========
    /**
     * ExistsQuery( q=register_user:* )
     */
    public void setRegisterUser_Exists() {
        SolrQueryBuilder.assertNullQuery("register_user", this.registerUserQuery);
        this.registerUserQuery = SolrQueryBuilder.queryBuilderForExists("register_user");
    }

    /**
     * ExistsQuery( q=NOT register_user:* )
     */
    public void setRegisterUser_NotExists() {
        SolrQueryBuilder.assertNullQuery("register_user", this.registerUserQuery);
        this.registerUserQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("register_user"), false);
    }

    /**
     * ExistsQuery( q=(query OR (NOT register_user:*)) )
     */
    public void addRegisterUser_NotExists() {
        SolrQueryBuilder.assertNotNullQuery("register_user", this.registerUserQuery);
        this.registerUserQuery = SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(this.registerUserQuery, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("register_user"), true)), SolrQueryLogicalOperator.OR));
    }

    public void setRegisterUser_Equal(String query) {
        SolrQueryBuilder.assertNullQuery("register_user", this.registerUserQuery);
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.registerUserQuery = SolrQueryBuilder.queryBuilderForEqual("register_user", query.toString());
        }
    }

    public void setRegisterUser_NotEqual(String query) {
        SolrQueryBuilder.assertNullQuery("register_user", this.registerUserQuery);
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.registerUserQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("register_user", query.toString()), false);
        }
    }

    public void setRegisterUser_InScope(Collection<String> queryList) {
        this.setRegisterUser_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setRegisterUser_InScope(Collection<String> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("register_user", this.registerUserQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("register_user", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.registerUserQuery = convertedQuery;
        }
    }

    public void setRegisterUser_NotInScope(Collection<String> queryList) {
        SolrQueryBuilder.assertNullQuery("register_user", this.registerUserQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("register_user", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.registerUserQuery = convertedQuery;
        }
    }

    public void setRegisterUser_PrefixSearch(String query) {
        SolrQueryBuilder.assertNullQuery("register_user", this.registerUserQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForPrefixSearch("register_user", query);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.registerUserQuery = convertedQuery;
        }
    }

    public void setRegisterUser_RangeSearchFrom(String from) {
        if (DfStringUtil.is_NotNull_and_NotEmpty(from)) {
            setRegisterUser_RangeSearch(from, null);
        }
    }

    public void setRegisterUser_RangeSearchTo(String to) {
        if (DfStringUtil.is_NotNull_and_NotEmpty(to)) {
            setRegisterUser_RangeSearch(null, to);
        }
    }

    public void setRegisterUser_RangeSearch(String from, String to) {
        SolrQueryBuilder.assertNullQuery("register_user", this.registerUserQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("register_user", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.registerUserQuery = convertedQuery;
        }
    }

    public void setRegisterUser_SetRangeSearch(String cd, String from, String to) {
        SolrQueryBuilder.assertNullQuery("register_user", this.registerUserQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearch("register_user", cd, from, to, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.registerUserQuery = convertedQuery;
        }
    }

    public void setRegisterUser_SetRangeSearchInScope(Collection<SolrSetRangeSearchBean> beanList) {
        SolrQueryBuilder.assertNullQuery("register_user", this.registerUserQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearchInScope("register_user", beanList, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.registerUserQuery = convertedQuery;
        }
    }

    // ===========================================================
    // Query Setter for regular_price (long)
    //                                                  ==========
    /**
     * ExistsQuery( q=regular_price:* )
     */
    public void setRegularPrice_Exists() {
        SolrQueryBuilder.assertNullQuery("regular_price", this.regularPriceQuery);
        this.regularPriceQuery = SolrQueryBuilder.queryBuilderForExists("regular_price");
    }

    /**
     * ExistsQuery( q=NOT regular_price:* )
     */
    public void setRegularPrice_NotExists() {
        SolrQueryBuilder.assertNullQuery("regular_price", this.regularPriceQuery);
        this.regularPriceQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("regular_price"), false);
    }

    /**
     * ExistsQuery( q=(query OR (NOT regular_price:*)) )
     */
    public void addRegularPrice_NotExists() {
        SolrQueryBuilder.assertNotNullQuery("regular_price", this.regularPriceQuery);
        this.regularPriceQuery = SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(this.regularPriceQuery, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("regular_price"), true)), SolrQueryLogicalOperator.OR));
    }

    public void setRegularPrice_Equal(Long query) {
        SolrQueryBuilder.assertNullQuery("regular_price", this.regularPriceQuery);
        if (query != null) {
            this.regularPriceQuery = SolrQueryBuilder.queryBuilderForEqual("regular_price", query.toString());
        }
    }

    public void setRegularPrice_NotEqual(Long query) {
        SolrQueryBuilder.assertNullQuery("regular_price", this.regularPriceQuery);
        if (query != null) {
            this.regularPriceQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("regular_price", query.toString()), false);
        }
    }

    public void setRegularPrice_InScope(Collection<Long> queryList) {
        this.setRegularPrice_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setRegularPrice_InScope(Collection<Long> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("regular_price", this.regularPriceQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchLongList("regular_price", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.regularPriceQuery = convertedQuery;
        }
    }

    public void setRegularPrice_NotInScope(Collection<Long> queryList) {
        SolrQueryBuilder.assertNullQuery("regular_price", this.regularPriceQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchLongList("regular_price", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.regularPriceQuery = convertedQuery;
        }
    }

    public void setRegularPrice_RangeSearchFrom(Long from) {
        if (from != null) {
            setRegularPrice_RangeSearch(from, null);
        }
    }

    public void setRegularPrice_RangeSearchTo(Long to) {
        if (to != null) {
            setRegularPrice_RangeSearch(null, to);
        }
    }

    public void setRegularPrice_RangeSearch(Long from, Long to) {
        SolrQueryBuilder.assertNullQuery("regular_price", this.regularPriceQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("regular_price", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.regularPriceQuery = convertedQuery;
        }
    }

    // ===========================================================
    // Query Setter for update_datetime (tdate)
    //                                                  ==========
    /**
     * ExistsQuery( q=update_datetime:* )
     */
    public void setUpdateDatetime_Exists() {
        SolrQueryBuilder.assertNullQuery("update_datetime", this.updateDatetimeQuery);
        this.updateDatetimeQuery = SolrQueryBuilder.queryBuilderForExists("update_datetime");
    }

    /**
     * ExistsQuery( q=NOT update_datetime:* )
     */
    public void setUpdateDatetime_NotExists() {
        SolrQueryBuilder.assertNullQuery("update_datetime", this.updateDatetimeQuery);
        this.updateDatetimeQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("update_datetime"), false);
    }

    /**
     * ExistsQuery( q=(query OR (NOT update_datetime:*)) )
     */
    public void addUpdateDatetime_NotExists() {
        SolrQueryBuilder.assertNotNullQuery("update_datetime", this.updateDatetimeQuery);
        this.updateDatetimeQuery = SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(this.updateDatetimeQuery, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("update_datetime"), true)), SolrQueryLogicalOperator.OR));
    }

    public void setUpdateDatetime_Equal(java.time.LocalDateTime query) {
        SolrQueryBuilder.assertNullQuery("update_datetime", this.updateDatetimeQuery);
        if (query != null) {
            this.updateDatetimeQuery = SolrQueryBuilder.queryBuilderForEqual("update_datetime", query.toString());
        }
    }

    public void setUpdateDatetime_NotEqual(java.time.LocalDateTime query) {
        SolrQueryBuilder.assertNullQuery("update_datetime", this.updateDatetimeQuery);
        if (query != null) {
            this.updateDatetimeQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("update_datetime", query.toString()), false);
        }
    }

    public void setUpdateDatetime_RangeSearchFrom(java.time.LocalDateTime from) {
        if (from != null) {
            setUpdateDatetime_RangeSearch(from, null);
        }
    }

    public void setUpdateDatetime_RangeSearchTo(java.time.LocalDateTime to) {
        if (to != null) {
            setUpdateDatetime_RangeSearch(null, to);
        }
    }

    public void setUpdateDatetime_RangeSearch(java.time.LocalDateTime from, java.time.LocalDateTime to) {
        SolrQueryBuilder.assertNullQuery("update_datetime", this.updateDatetimeQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("update_datetime", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.updateDatetimeQuery = convertedQuery;
        }
    }

    // ===========================================================
    // Query Setter for update_user (string)
    //                                                  ==========
    /**
     * ExistsQuery( q=update_user:* )
     */
    public void setUpdateUser_Exists() {
        SolrQueryBuilder.assertNullQuery("update_user", this.updateUserQuery);
        this.updateUserQuery = SolrQueryBuilder.queryBuilderForExists("update_user");
    }

    /**
     * ExistsQuery( q=NOT update_user:* )
     */
    public void setUpdateUser_NotExists() {
        SolrQueryBuilder.assertNullQuery("update_user", this.updateUserQuery);
        this.updateUserQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("update_user"), false);
    }

    /**
     * ExistsQuery( q=(query OR (NOT update_user:*)) )
     */
    public void addUpdateUser_NotExists() {
        SolrQueryBuilder.assertNotNullQuery("update_user", this.updateUserQuery);
        this.updateUserQuery = SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(this.updateUserQuery, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("update_user"), true)), SolrQueryLogicalOperator.OR));
    }

    public void setUpdateUser_Equal(String query) {
        SolrQueryBuilder.assertNullQuery("update_user", this.updateUserQuery);
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.updateUserQuery = SolrQueryBuilder.queryBuilderForEqual("update_user", query.toString());
        }
    }

    public void setUpdateUser_NotEqual(String query) {
        SolrQueryBuilder.assertNullQuery("update_user", this.updateUserQuery);
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.updateUserQuery = SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("update_user", query.toString()), false);
        }
    }

    public void setUpdateUser_InScope(Collection<String> queryList) {
        this.setUpdateUser_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setUpdateUser_InScope(Collection<String> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("update_user", this.updateUserQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("update_user", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.updateUserQuery = convertedQuery;
        }
    }

    public void setUpdateUser_NotInScope(Collection<String> queryList) {
        SolrQueryBuilder.assertNullQuery("update_user", this.updateUserQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("update_user", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.updateUserQuery = convertedQuery;
        }
    }

    public void setUpdateUser_PrefixSearch(String query) {
        SolrQueryBuilder.assertNullQuery("update_user", this.updateUserQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForPrefixSearch("update_user", query);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.updateUserQuery = convertedQuery;
        }
    }

    public void setUpdateUser_RangeSearchFrom(String from) {
        if (DfStringUtil.is_NotNull_and_NotEmpty(from)) {
            setUpdateUser_RangeSearch(from, null);
        }
    }

    public void setUpdateUser_RangeSearchTo(String to) {
        if (DfStringUtil.is_NotNull_and_NotEmpty(to)) {
            setUpdateUser_RangeSearch(null, to);
        }
    }

    public void setUpdateUser_RangeSearch(String from, String to) {
        SolrQueryBuilder.assertNullQuery("update_user", this.updateUserQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("update_user", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.updateUserQuery = convertedQuery;
        }
    }

    public void setUpdateUser_SetRangeSearch(String cd, String from, String to) {
        SolrQueryBuilder.assertNullQuery("update_user", this.updateUserQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearch("update_user", cd, from, to, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.updateUserQuery = convertedQuery;
        }
    }

    public void setUpdateUser_SetRangeSearchInScope(Collection<SolrSetRangeSearchBean> beanList) {
        SolrQueryBuilder.assertNullQuery("update_user", this.updateUserQuery);
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearchInScope("update_user", beanList, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.updateUserQuery = convertedQuery;
        }
    }

   /**
     * OR condition
     * @param orQueryLambda ConditionBean function for Solr for generating OR-combined condition (NotNull)
     */
    public void orScopeQuery(SolrFQBCall<SolrExampleCFQ> orQueryLambda) {
        SolrExampleCFQ queryBean = new SolrExampleCFQ();
        orQueryLambda.callback(queryBean);
        addNestQuery(queryBean, SolrQueryLogicalOperator.OR);
    }

   /**
     * AND condition(s) nested in OR condition
     * @param orQueryAndPartLambda ConditionBean function for Solr for generating OR-combined condition (NotNull)
     */
    public void orScopeQueryAndPart(SolrFQBCall<SolrExampleCFQ> orQueryAndPartLambda) {
        SolrExampleCFQ queryBean = new SolrExampleCFQ();
        orQueryAndPartLambda.callback(queryBean);
        addNestQuery(queryBean, SolrQueryLogicalOperator.AND);
    }

    // ===================================================================================
    //                                                                        Query Getter
    //                                                                        ============
    @Override
    public List<String> getQueryList() {
        List<String> queryList = new ArrayList<String>();
        if (DfStringUtil.is_NotNull_and_NotEmpty(dismaxQuery)) {
            queryList.add(dismaxQuery);
        }
        if (DfStringUtil.is_NotNull_and_NotEmpty(latestPurchaseDateQuery)) {
            queryList.add(latestPurchaseDateQuery);
        }

        if (DfStringUtil.is_NotNull_and_NotEmpty(productCategoryQuery)) {
            queryList.add(productCategoryQuery);
        }

        if (DfStringUtil.is_NotNull_and_NotEmpty(productCategoryCodeQuery)) {
            queryList.add(productCategoryCodeQuery);
        }

        if (DfStringUtil.is_NotNull_and_NotEmpty(productHandleCodeQuery)) {
            queryList.add(productHandleCodeQuery);
        }

        if (DfStringUtil.is_NotNull_and_NotEmpty(productNameQuery)) {
            queryList.add(productNameQuery);
        }

        if (DfStringUtil.is_NotNull_and_NotEmpty(productStatusQuery)) {
            queryList.add(productStatusQuery);
        }

        if (DfStringUtil.is_NotNull_and_NotEmpty(productStatusCodeQuery)) {
            queryList.add(productStatusCodeQuery);
        }

        if (DfStringUtil.is_NotNull_and_NotEmpty(registerDatetimeQuery)) {
            queryList.add(registerDatetimeQuery);
        }

        if (DfStringUtil.is_NotNull_and_NotEmpty(registerUserQuery)) {
            queryList.add(registerUserQuery);
        }

        if (DfStringUtil.is_NotNull_and_NotEmpty(regularPriceQuery)) {
            queryList.add(regularPriceQuery);
        }

        if (DfStringUtil.is_NotNull_and_NotEmpty(updateDatetimeQuery)) {
            queryList.add(updateDatetimeQuery);
        }

        if (DfStringUtil.is_NotNull_and_NotEmpty(updateUserQuery)) {
            queryList.add(updateUserQuery);
        }

        List<String> nestQueryList = getNestQueryList();
        if (!nestQueryList.isEmpty()) {
            queryList.addAll(nestQueryList);
        }
        return queryList;
    }
}
