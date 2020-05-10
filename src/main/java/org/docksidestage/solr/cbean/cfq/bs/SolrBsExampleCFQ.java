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
import java.util.LinkedHashMap;
import java.util.Map;

import org.dbflute.util.DfStringUtil;

import org.dbflute.solr.cbean.AbstractSolrFilterQueryBean;
import org.dbflute.solr.cbean.SolrFQBCall;
import org.dbflute.solr.cbean.SolrQueryBuilder;
import org.dbflute.solr.cbean.SolrQueryLogicalOperator;
import org.dbflute.solr.cbean.SolrSetRangeSearchBean;
import org.dbflute.util.DfCollectionUtil;

import org.docksidestage.solr.cbean.cfq.SolrExampleCFQ;

/**
 * Base FilterQuery class of Solr schema "Example."
 * @author FreeGen
 */
public class SolrBsExampleCFQ extends AbstractSolrFilterQueryBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** Query Attribute for product_id (long) */
    protected Map<String, String> productIdQueryMap = new LinkedHashMap<String, String>();

    /** Query Attribute for latest_purchase_date (tdate) */
    protected Map<String, String> latestPurchaseDateQueryMap = new LinkedHashMap<String, String>();

    /** Query Attribute for product_category (string) */
    protected Map<String, String> productCategoryQueryMap = new LinkedHashMap<String, String>();

    /** Query Attribute for product_category_code (string) */
    protected Map<String, String> productCategoryCodeQueryMap = new LinkedHashMap<String, String>();

    /** Query Attribute for product_handle_code (string) */
    protected Map<String, String> productHandleCodeQueryMap = new LinkedHashMap<String, String>();

    /** Query Attribute for product_name (string) */
    protected Map<String, String> productNameQueryMap = new LinkedHashMap<String, String>();

    /** Query Attribute for product_status (string) */
    protected Map<String, String> productStatusQueryMap = new LinkedHashMap<String, String>();

    /** Query Attribute for product_status_code (string) */
    protected Map<String, String> productStatusCodeQueryMap = new LinkedHashMap<String, String>();

    /** Query Attribute for regular_price (long) */
    protected Map<String, String> regularPriceQueryMap = new LinkedHashMap<String, String>();

    /** Query Attribute for register_datetime (tdate) */
    protected Map<String, String> registerDatetimeQueryMap = new LinkedHashMap<String, String>();

    /** Query Attribute for register_user (string) */
    protected Map<String, String> registerUserQueryMap = new LinkedHashMap<String, String>();

    /** Query Attribute for update_datetime (tdate) */
    protected Map<String, String> updateDatetimeQueryMap = new LinkedHashMap<String, String>();

    /** Query Attribute for update_user (string) */
    protected Map<String, String> updateUserQueryMap = new LinkedHashMap<String, String>();

    /** Query Attribute for _version_ (long) */
    protected Map<String, String> versionQueryMap = new LinkedHashMap<String, String>();

    // ===================================================================================
    //                                                                          Conditions
    //                                                                          ==========
    // ===========================================================
    // Query Setter for product_id (long)
    //                                                  ==========
    /**
     * ExistsQuery( q=product_id:* )
     */
    public void setProductId_Exists() {
        SolrQueryBuilder.assertNullQuery("product_id_Exists", this.productIdQueryMap.get("product_id_Exists"));
        this.productIdQueryMap.put("product_id_Exists", SolrQueryBuilder.queryBuilderForExists("product_id"));
    }

    /**
     * ExistsQuery( q=NOT product_id:* )
     */
    public void setProductId_NotExists() {
        SolrQueryBuilder.assertNullQuery("product_id_NotExists", this.productIdQueryMap.get("product_id_NotExists"));
        this.productIdQueryMap.put("product_id_NotExists", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_id"), false));
    }

    /**
     * ExistsQuery( q=(query OR (NOT product_id:*)) )
     */
    public void addProductId_NotExists() {
        String value = null;
        if (!this.productIdQueryMap.isEmpty()) {
            List<String> list = DfCollectionUtil.newArrayList(this.productIdQueryMap.values());
            value = list.get(list.size() - 1);
        }
        SolrQueryBuilder.assertNotNullQuery("product_id", value);
        this.productIdQueryMap.put("product_id", SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(value, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_id"), true)), SolrQueryLogicalOperator.OR)));
    }

    public void setProductId_Equal(Long query) {
        SolrQueryBuilder.assertNullQuery("product_id_Equal", this.productIdQueryMap.get("product_id_Equal"));
        if (query != null) {
            this.productIdQueryMap.put("product_id_Equal", SolrQueryBuilder.queryBuilderForEqual("product_id", query.toString()));
        }
    }

    public void setProductId_NotEqual(Long query) {
        SolrQueryBuilder.assertNullQuery("product_id_NotEqual", this.productIdQueryMap.get("product_id_NotEqual"));
        if (query != null) {
            this.productIdQueryMap.put("product_id_NotEqual", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("product_id", query.toString()), false));
        }
    }

    public void setProductId_InScope(Collection<Long> queryList) {
        this.setProductId_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setProductId_InScope(Collection<Long> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("product_id_InScope", this.productIdQueryMap.get("product_id_InScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchList("product_id", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productIdQueryMap.put("product_id_InScope", convertedQuery);
        }
    }

    public void setProductId_NotInScope(Collection<Long> queryList) {
        SolrQueryBuilder.assertNullQuery("product_id_NotInScope", this.productIdQueryMap.get("product_id_NotInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchList("product_id", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productIdQueryMap.put("product_id_NotInScope", convertedQuery);
        }
    }

    public void setProductId_RangeSearchFrom(Long from) {
        if (from != null) {
            setProductId_RangeSearch(from, null);
        }
    }

    public void setProductId_RangeSearchTo(Long to) {
        if (to != null) {
            setProductId_RangeSearch(null, to);
        }
    }

    public void setProductId_RangeSearch(Long from, Long to) {
        SolrQueryBuilder.assertNullQuery("product_id_RangeSearch", this.productIdQueryMap.get("product_id_RangeSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("product_id", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productIdQueryMap.put("product_id_RangeSearch", convertedQuery);
        }
    }

    // ===========================================================
    // Query Setter for latest_purchase_date (tdate)
    //                                                  ==========
    /**
     * ExistsQuery( q=latest_purchase_date:* )
     */
    public void setLatestPurchaseDate_Exists() {
        SolrQueryBuilder.assertNullQuery("latest_purchase_date_Exists", this.latestPurchaseDateQueryMap.get("latest_purchase_date_Exists"));
        this.latestPurchaseDateQueryMap.put("latest_purchase_date_Exists", SolrQueryBuilder.queryBuilderForExists("latest_purchase_date"));
    }

    /**
     * ExistsQuery( q=NOT latest_purchase_date:* )
     */
    public void setLatestPurchaseDate_NotExists() {
        SolrQueryBuilder.assertNullQuery("latest_purchase_date_NotExists", this.latestPurchaseDateQueryMap.get("latest_purchase_date_NotExists"));
        this.latestPurchaseDateQueryMap.put("latest_purchase_date_NotExists", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("latest_purchase_date"), false));
    }

    /**
     * ExistsQuery( q=(query OR (NOT latest_purchase_date:*)) )
     */
    public void addLatestPurchaseDate_NotExists() {
        String value = null;
        if (!this.latestPurchaseDateQueryMap.isEmpty()) {
            List<String> list = DfCollectionUtil.newArrayList(this.latestPurchaseDateQueryMap.values());
            value = list.get(list.size() - 1);
        }
        SolrQueryBuilder.assertNotNullQuery("latest_purchase_date", value);
        this.latestPurchaseDateQueryMap.put("latest_purchase_date", SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(value, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("latest_purchase_date"), true)), SolrQueryLogicalOperator.OR)));
    }

    public void setLatestPurchaseDate_Equal(java.time.LocalDateTime query) {
        SolrQueryBuilder.assertNullQuery("latest_purchase_date_Equal", this.latestPurchaseDateQueryMap.get("latest_purchase_date_Equal"));
        if (query != null) {
            this.latestPurchaseDateQueryMap.put("latest_purchase_date_Equal", SolrQueryBuilder.queryBuilderForEqual("latest_purchase_date", query.toString()));
        }
    }

    public void setLatestPurchaseDate_NotEqual(java.time.LocalDateTime query) {
        SolrQueryBuilder.assertNullQuery("latest_purchase_date_NotEqual", this.latestPurchaseDateQueryMap.get("latest_purchase_date_NotEqual"));
        if (query != null) {
            this.latestPurchaseDateQueryMap.put("latest_purchase_date_NotEqual", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("latest_purchase_date", query.toString()), false));
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
        SolrQueryBuilder.assertNullQuery("latest_purchase_date_RangeSearch", this.latestPurchaseDateQueryMap.get("latest_purchase_date_RangeSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("latest_purchase_date", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.latestPurchaseDateQueryMap.put("latest_purchase_date_RangeSearch", convertedQuery);
        }
    }
    // ===========================================================
    // Query Setter for product_category (string)
    //                                                  ==========
    /**
     * ExistsQuery( q=product_category:* )
     */
    public void setProductCategory_Exists() {
        SolrQueryBuilder.assertNullQuery("product_category_Exists", this.productCategoryQueryMap.get("product_category_Exists"));
        this.productCategoryQueryMap.put("product_category_Exists", SolrQueryBuilder.queryBuilderForExists("product_category"));
    }

    /**
     * ExistsQuery( q=NOT product_category:* )
     */
    public void setProductCategory_NotExists() {
        SolrQueryBuilder.assertNullQuery("product_category_NotExists", this.productCategoryQueryMap.get("product_category_NotExists"));
        this.productCategoryQueryMap.put("product_category_NotExists", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_category"), false));
    }

    /**
     * ExistsQuery( q=(query OR (NOT product_category:*)) )
     */
    public void addProductCategory_NotExists() {
        String value = null;
        if (!this.productCategoryQueryMap.isEmpty()) {
            List<String> list = DfCollectionUtil.newArrayList(this.productCategoryQueryMap.values());
            value = list.get(list.size() - 1);
        }
        SolrQueryBuilder.assertNotNullQuery("product_category", value);
        this.productCategoryQueryMap.put("product_category", SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(value, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_category"), true)), SolrQueryLogicalOperator.OR)));
    }

    public void setProductCategory_Equal(String query) {
        SolrQueryBuilder.assertNullQuery("product_category_Equal", this.productCategoryQueryMap.get("product_category_Equal"));
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productCategoryQueryMap.put("product_category_Equal", SolrQueryBuilder.queryBuilderForEqual("product_category", query.toString()));
        }
    }

    public void setProductCategory_NotEqual(String query) {
        SolrQueryBuilder.assertNullQuery("product_category_NotEqual", this.productCategoryQueryMap.get("product_category_NotEqual"));
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productCategoryQueryMap.put("product_category_NotEqual", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("product_category", query.toString()), false));
        }
    }

    public void setProductCategory_InScope(Collection<String> queryList) {
        this.setProductCategory_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setProductCategory_InScope(Collection<String> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("product_category_InScope", this.productCategoryQueryMap.get("product_category_InScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_category", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryQueryMap.put("product_category_InScope", convertedQuery);
        }
    }

    public void setProductCategory_NotInScope(Collection<String> queryList) {
        SolrQueryBuilder.assertNullQuery("product_category_NotInScope", this.productCategoryQueryMap.get("product_category_NotInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_category", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryQueryMap.put("product_category_NotInScope", convertedQuery);
        }
    }

    public void setProductCategory_PrefixSearch(String query) {
        SolrQueryBuilder.assertNullQuery("product_category_PrefixSearch", this.productCategoryQueryMap.get("product_category_PrefixSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForPrefixSearch("product_category", query);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryQueryMap.put("product_category_PrefixSearch", convertedQuery);
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
        SolrQueryBuilder.assertNullQuery("product_category_RangeSearc", this.productCategoryQueryMap.get("product_category_RangeSearc"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("product_category", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryQueryMap.put("product_category_RangeSearc", convertedQuery);
        }
    }

    public void setProductCategory_SetRangeSearch(String cd, String from, String to) {
        SolrQueryBuilder.assertNullQuery("product_category_SetRangeSearch", this.productCategoryQueryMap.get("product_category_SetRangeSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearch("product_category", cd, from, to, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryQueryMap.put("product_category_SetRangeSearch", convertedQuery);
        }
    }

    public void setProductCategory_SetRangeSearchInScope(Collection<SolrSetRangeSearchBean> beanList) {
        SolrQueryBuilder.assertNullQuery("product_category_SetRangeSearchInScope", this.productCategoryQueryMap.get("product_category_SetRangeSearchInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearchInScope("product_category", beanList, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryQueryMap.put("product_category_SetRangeSearchInScope", convertedQuery);
        }
    }
    // ===========================================================
    // Query Setter for product_category_code (string)
    //                                                  ==========
    /**
     * ExistsQuery( q=product_category_code:* )
     */
    public void setProductCategoryCode_Exists() {
        SolrQueryBuilder.assertNullQuery("product_category_code_Exists", this.productCategoryCodeQueryMap.get("product_category_code_Exists"));
        this.productCategoryCodeQueryMap.put("product_category_code_Exists", SolrQueryBuilder.queryBuilderForExists("product_category_code"));
    }

    /**
     * ExistsQuery( q=NOT product_category_code:* )
     */
    public void setProductCategoryCode_NotExists() {
        SolrQueryBuilder.assertNullQuery("product_category_code_NotExists", this.productCategoryCodeQueryMap.get("product_category_code_NotExists"));
        this.productCategoryCodeQueryMap.put("product_category_code_NotExists", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_category_code"), false));
    }

    /**
     * ExistsQuery( q=(query OR (NOT product_category_code:*)) )
     */
    public void addProductCategoryCode_NotExists() {
        String value = null;
        if (!this.productCategoryCodeQueryMap.isEmpty()) {
            List<String> list = DfCollectionUtil.newArrayList(this.productCategoryCodeQueryMap.values());
            value = list.get(list.size() - 1);
        }
        SolrQueryBuilder.assertNotNullQuery("product_category_code", value);
        this.productCategoryCodeQueryMap.put("product_category_code", SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(value, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_category_code"), true)), SolrQueryLogicalOperator.OR)));
    }

    public void setProductCategoryCode_Equal(String query) {
        SolrQueryBuilder.assertNullQuery("product_category_code_Equal", this.productCategoryCodeQueryMap.get("product_category_code_Equal"));
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productCategoryCodeQueryMap.put("product_category_code_Equal", SolrQueryBuilder.queryBuilderForEqual("product_category_code", query.toString()));
        }
    }

    public void setProductCategoryCode_NotEqual(String query) {
        SolrQueryBuilder.assertNullQuery("product_category_code_NotEqual", this.productCategoryCodeQueryMap.get("product_category_code_NotEqual"));
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productCategoryCodeQueryMap.put("product_category_code_NotEqual", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("product_category_code", query.toString()), false));
        }
    }

    public void setProductCategoryCode_InScope(Collection<String> queryList) {
        this.setProductCategoryCode_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setProductCategoryCode_InScope(Collection<String> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("product_category_code_InScope", this.productCategoryCodeQueryMap.get("product_category_code_InScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_category_code", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryCodeQueryMap.put("product_category_code_InScope", convertedQuery);
        }
    }

    public void setProductCategoryCode_NotInScope(Collection<String> queryList) {
        SolrQueryBuilder.assertNullQuery("product_category_code_NotInScope", this.productCategoryCodeQueryMap.get("product_category_code_NotInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_category_code", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryCodeQueryMap.put("product_category_code_NotInScope", convertedQuery);
        }
    }

    public void setProductCategoryCode_PrefixSearch(String query) {
        SolrQueryBuilder.assertNullQuery("product_category_code_PrefixSearch", this.productCategoryCodeQueryMap.get("product_category_code_PrefixSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForPrefixSearch("product_category_code", query);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryCodeQueryMap.put("product_category_code_PrefixSearch", convertedQuery);
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
        SolrQueryBuilder.assertNullQuery("product_category_code_RangeSearc", this.productCategoryCodeQueryMap.get("product_category_code_RangeSearc"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("product_category_code", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryCodeQueryMap.put("product_category_code_RangeSearc", convertedQuery);
        }
    }

    public void setProductCategoryCode_SetRangeSearch(String cd, String from, String to) {
        SolrQueryBuilder.assertNullQuery("product_category_code_SetRangeSearch", this.productCategoryCodeQueryMap.get("product_category_code_SetRangeSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearch("product_category_code", cd, from, to, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryCodeQueryMap.put("product_category_code_SetRangeSearch", convertedQuery);
        }
    }

    public void setProductCategoryCode_SetRangeSearchInScope(Collection<SolrSetRangeSearchBean> beanList) {
        SolrQueryBuilder.assertNullQuery("product_category_code_SetRangeSearchInScope", this.productCategoryCodeQueryMap.get("product_category_code_SetRangeSearchInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearchInScope("product_category_code", beanList, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productCategoryCodeQueryMap.put("product_category_code_SetRangeSearchInScope", convertedQuery);
        }
    }
    // ===========================================================
    // Query Setter for product_handle_code (string)
    //                                                  ==========
    /**
     * ExistsQuery( q=product_handle_code:* )
     */
    public void setProductHandleCode_Exists() {
        SolrQueryBuilder.assertNullQuery("product_handle_code_Exists", this.productHandleCodeQueryMap.get("product_handle_code_Exists"));
        this.productHandleCodeQueryMap.put("product_handle_code_Exists", SolrQueryBuilder.queryBuilderForExists("product_handle_code"));
    }

    /**
     * ExistsQuery( q=NOT product_handle_code:* )
     */
    public void setProductHandleCode_NotExists() {
        SolrQueryBuilder.assertNullQuery("product_handle_code_NotExists", this.productHandleCodeQueryMap.get("product_handle_code_NotExists"));
        this.productHandleCodeQueryMap.put("product_handle_code_NotExists", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_handle_code"), false));
    }

    /**
     * ExistsQuery( q=(query OR (NOT product_handle_code:*)) )
     */
    public void addProductHandleCode_NotExists() {
        String value = null;
        if (!this.productHandleCodeQueryMap.isEmpty()) {
            List<String> list = DfCollectionUtil.newArrayList(this.productHandleCodeQueryMap.values());
            value = list.get(list.size() - 1);
        }
        SolrQueryBuilder.assertNotNullQuery("product_handle_code", value);
        this.productHandleCodeQueryMap.put("product_handle_code", SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(value, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_handle_code"), true)), SolrQueryLogicalOperator.OR)));
    }

    public void setProductHandleCode_Equal(String query) {
        SolrQueryBuilder.assertNullQuery("product_handle_code_Equal", this.productHandleCodeQueryMap.get("product_handle_code_Equal"));
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productHandleCodeQueryMap.put("product_handle_code_Equal", SolrQueryBuilder.queryBuilderForEqual("product_handle_code", query.toString()));
        }
    }

    public void setProductHandleCode_NotEqual(String query) {
        SolrQueryBuilder.assertNullQuery("product_handle_code_NotEqual", this.productHandleCodeQueryMap.get("product_handle_code_NotEqual"));
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productHandleCodeQueryMap.put("product_handle_code_NotEqual", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("product_handle_code", query.toString()), false));
        }
    }

    public void setProductHandleCode_InScope(Collection<String> queryList) {
        this.setProductHandleCode_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setProductHandleCode_InScope(Collection<String> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("product_handle_code_InScope", this.productHandleCodeQueryMap.get("product_handle_code_InScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_handle_code", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productHandleCodeQueryMap.put("product_handle_code_InScope", convertedQuery);
        }
    }

    public void setProductHandleCode_NotInScope(Collection<String> queryList) {
        SolrQueryBuilder.assertNullQuery("product_handle_code_NotInScope", this.productHandleCodeQueryMap.get("product_handle_code_NotInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_handle_code", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productHandleCodeQueryMap.put("product_handle_code_NotInScope", convertedQuery);
        }
    }

    public void setProductHandleCode_PrefixSearch(String query) {
        SolrQueryBuilder.assertNullQuery("product_handle_code_PrefixSearch", this.productHandleCodeQueryMap.get("product_handle_code_PrefixSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForPrefixSearch("product_handle_code", query);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productHandleCodeQueryMap.put("product_handle_code_PrefixSearch", convertedQuery);
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
        SolrQueryBuilder.assertNullQuery("product_handle_code_RangeSearc", this.productHandleCodeQueryMap.get("product_handle_code_RangeSearc"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("product_handle_code", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productHandleCodeQueryMap.put("product_handle_code_RangeSearc", convertedQuery);
        }
    }

    public void setProductHandleCode_SetRangeSearch(String cd, String from, String to) {
        SolrQueryBuilder.assertNullQuery("product_handle_code_SetRangeSearch", this.productHandleCodeQueryMap.get("product_handle_code_SetRangeSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearch("product_handle_code", cd, from, to, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productHandleCodeQueryMap.put("product_handle_code_SetRangeSearch", convertedQuery);
        }
    }

    public void setProductHandleCode_SetRangeSearchInScope(Collection<SolrSetRangeSearchBean> beanList) {
        SolrQueryBuilder.assertNullQuery("product_handle_code_SetRangeSearchInScope", this.productHandleCodeQueryMap.get("product_handle_code_SetRangeSearchInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearchInScope("product_handle_code", beanList, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productHandleCodeQueryMap.put("product_handle_code_SetRangeSearchInScope", convertedQuery);
        }
    }
    // ===========================================================
    // Query Setter for product_name (string)
    //                                                  ==========
    /**
     * ExistsQuery( q=product_name:* )
     */
    public void setProductName_Exists() {
        SolrQueryBuilder.assertNullQuery("product_name_Exists", this.productNameQueryMap.get("product_name_Exists"));
        this.productNameQueryMap.put("product_name_Exists", SolrQueryBuilder.queryBuilderForExists("product_name"));
    }

    /**
     * ExistsQuery( q=NOT product_name:* )
     */
    public void setProductName_NotExists() {
        SolrQueryBuilder.assertNullQuery("product_name_NotExists", this.productNameQueryMap.get("product_name_NotExists"));
        this.productNameQueryMap.put("product_name_NotExists", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_name"), false));
    }

    /**
     * ExistsQuery( q=(query OR (NOT product_name:*)) )
     */
    public void addProductName_NotExists() {
        String value = null;
        if (!this.productNameQueryMap.isEmpty()) {
            List<String> list = DfCollectionUtil.newArrayList(this.productNameQueryMap.values());
            value = list.get(list.size() - 1);
        }
        SolrQueryBuilder.assertNotNullQuery("product_name", value);
        this.productNameQueryMap.put("product_name", SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(value, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_name"), true)), SolrQueryLogicalOperator.OR)));
    }

    public void setProductName_Equal(String query) {
        SolrQueryBuilder.assertNullQuery("product_name_Equal", this.productNameQueryMap.get("product_name_Equal"));
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productNameQueryMap.put("product_name_Equal", SolrQueryBuilder.queryBuilderForEqual("product_name", query.toString()));
        }
    }

    public void setProductName_NotEqual(String query) {
        SolrQueryBuilder.assertNullQuery("product_name_NotEqual", this.productNameQueryMap.get("product_name_NotEqual"));
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productNameQueryMap.put("product_name_NotEqual", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("product_name", query.toString()), false));
        }
    }

    public void setProductName_InScope(Collection<String> queryList) {
        this.setProductName_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setProductName_InScope(Collection<String> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("product_name_InScope", this.productNameQueryMap.get("product_name_InScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_name", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productNameQueryMap.put("product_name_InScope", convertedQuery);
        }
    }

    public void setProductName_NotInScope(Collection<String> queryList) {
        SolrQueryBuilder.assertNullQuery("product_name_NotInScope", this.productNameQueryMap.get("product_name_NotInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_name", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productNameQueryMap.put("product_name_NotInScope", convertedQuery);
        }
    }

    public void setProductName_PrefixSearch(String query) {
        SolrQueryBuilder.assertNullQuery("product_name_PrefixSearch", this.productNameQueryMap.get("product_name_PrefixSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForPrefixSearch("product_name", query);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productNameQueryMap.put("product_name_PrefixSearch", convertedQuery);
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
        SolrQueryBuilder.assertNullQuery("product_name_RangeSearc", this.productNameQueryMap.get("product_name_RangeSearc"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("product_name", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productNameQueryMap.put("product_name_RangeSearc", convertedQuery);
        }
    }

    public void setProductName_SetRangeSearch(String cd, String from, String to) {
        SolrQueryBuilder.assertNullQuery("product_name_SetRangeSearch", this.productNameQueryMap.get("product_name_SetRangeSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearch("product_name", cd, from, to, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productNameQueryMap.put("product_name_SetRangeSearch", convertedQuery);
        }
    }

    public void setProductName_SetRangeSearchInScope(Collection<SolrSetRangeSearchBean> beanList) {
        SolrQueryBuilder.assertNullQuery("product_name_SetRangeSearchInScope", this.productNameQueryMap.get("product_name_SetRangeSearchInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearchInScope("product_name", beanList, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productNameQueryMap.put("product_name_SetRangeSearchInScope", convertedQuery);
        }
    }
    // ===========================================================
    // Query Setter for product_status (string)
    //                                                  ==========
    /**
     * ExistsQuery( q=product_status:* )
     */
    public void setProductStatus_Exists() {
        SolrQueryBuilder.assertNullQuery("product_status_Exists", this.productStatusQueryMap.get("product_status_Exists"));
        this.productStatusQueryMap.put("product_status_Exists", SolrQueryBuilder.queryBuilderForExists("product_status"));
    }

    /**
     * ExistsQuery( q=NOT product_status:* )
     */
    public void setProductStatus_NotExists() {
        SolrQueryBuilder.assertNullQuery("product_status_NotExists", this.productStatusQueryMap.get("product_status_NotExists"));
        this.productStatusQueryMap.put("product_status_NotExists", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_status"), false));
    }

    /**
     * ExistsQuery( q=(query OR (NOT product_status:*)) )
     */
    public void addProductStatus_NotExists() {
        String value = null;
        if (!this.productStatusQueryMap.isEmpty()) {
            List<String> list = DfCollectionUtil.newArrayList(this.productStatusQueryMap.values());
            value = list.get(list.size() - 1);
        }
        SolrQueryBuilder.assertNotNullQuery("product_status", value);
        this.productStatusQueryMap.put("product_status", SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(value, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_status"), true)), SolrQueryLogicalOperator.OR)));
    }

    public void setProductStatus_Equal(String query) {
        SolrQueryBuilder.assertNullQuery("product_status_Equal", this.productStatusQueryMap.get("product_status_Equal"));
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productStatusQueryMap.put("product_status_Equal", SolrQueryBuilder.queryBuilderForEqual("product_status", query.toString()));
        }
    }

    public void setProductStatus_NotEqual(String query) {
        SolrQueryBuilder.assertNullQuery("product_status_NotEqual", this.productStatusQueryMap.get("product_status_NotEqual"));
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productStatusQueryMap.put("product_status_NotEqual", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("product_status", query.toString()), false));
        }
    }

    public void setProductStatus_InScope(Collection<String> queryList) {
        this.setProductStatus_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setProductStatus_InScope(Collection<String> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("product_status_InScope", this.productStatusQueryMap.get("product_status_InScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_status", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusQueryMap.put("product_status_InScope", convertedQuery);
        }
    }

    public void setProductStatus_NotInScope(Collection<String> queryList) {
        SolrQueryBuilder.assertNullQuery("product_status_NotInScope", this.productStatusQueryMap.get("product_status_NotInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_status", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusQueryMap.put("product_status_NotInScope", convertedQuery);
        }
    }

    public void setProductStatus_PrefixSearch(String query) {
        SolrQueryBuilder.assertNullQuery("product_status_PrefixSearch", this.productStatusQueryMap.get("product_status_PrefixSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForPrefixSearch("product_status", query);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusQueryMap.put("product_status_PrefixSearch", convertedQuery);
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
        SolrQueryBuilder.assertNullQuery("product_status_RangeSearc", this.productStatusQueryMap.get("product_status_RangeSearc"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("product_status", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusQueryMap.put("product_status_RangeSearc", convertedQuery);
        }
    }

    public void setProductStatus_SetRangeSearch(String cd, String from, String to) {
        SolrQueryBuilder.assertNullQuery("product_status_SetRangeSearch", this.productStatusQueryMap.get("product_status_SetRangeSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearch("product_status", cd, from, to, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusQueryMap.put("product_status_SetRangeSearch", convertedQuery);
        }
    }

    public void setProductStatus_SetRangeSearchInScope(Collection<SolrSetRangeSearchBean> beanList) {
        SolrQueryBuilder.assertNullQuery("product_status_SetRangeSearchInScope", this.productStatusQueryMap.get("product_status_SetRangeSearchInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearchInScope("product_status", beanList, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusQueryMap.put("product_status_SetRangeSearchInScope", convertedQuery);
        }
    }
    // ===========================================================
    // Query Setter for product_status_code (string)
    //                                                  ==========
    /**
     * ExistsQuery( q=product_status_code:* )
     */
    public void setProductStatusCode_Exists() {
        SolrQueryBuilder.assertNullQuery("product_status_code_Exists", this.productStatusCodeQueryMap.get("product_status_code_Exists"));
        this.productStatusCodeQueryMap.put("product_status_code_Exists", SolrQueryBuilder.queryBuilderForExists("product_status_code"));
    }

    /**
     * ExistsQuery( q=NOT product_status_code:* )
     */
    public void setProductStatusCode_NotExists() {
        SolrQueryBuilder.assertNullQuery("product_status_code_NotExists", this.productStatusCodeQueryMap.get("product_status_code_NotExists"));
        this.productStatusCodeQueryMap.put("product_status_code_NotExists", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_status_code"), false));
    }

    /**
     * ExistsQuery( q=(query OR (NOT product_status_code:*)) )
     */
    public void addProductStatusCode_NotExists() {
        String value = null;
        if (!this.productStatusCodeQueryMap.isEmpty()) {
            List<String> list = DfCollectionUtil.newArrayList(this.productStatusCodeQueryMap.values());
            value = list.get(list.size() - 1);
        }
        SolrQueryBuilder.assertNotNullQuery("product_status_code", value);
        this.productStatusCodeQueryMap.put("product_status_code", SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(value, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("product_status_code"), true)), SolrQueryLogicalOperator.OR)));
    }

    public void setProductStatusCode_Equal(String query) {
        SolrQueryBuilder.assertNullQuery("product_status_code_Equal", this.productStatusCodeQueryMap.get("product_status_code_Equal"));
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productStatusCodeQueryMap.put("product_status_code_Equal", SolrQueryBuilder.queryBuilderForEqual("product_status_code", query.toString()));
        }
    }

    public void setProductStatusCode_NotEqual(String query) {
        SolrQueryBuilder.assertNullQuery("product_status_code_NotEqual", this.productStatusCodeQueryMap.get("product_status_code_NotEqual"));
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.productStatusCodeQueryMap.put("product_status_code_NotEqual", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("product_status_code", query.toString()), false));
        }
    }

    public void setProductStatusCode_InScope(Collection<String> queryList) {
        this.setProductStatusCode_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setProductStatusCode_InScope(Collection<String> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("product_status_code_InScope", this.productStatusCodeQueryMap.get("product_status_code_InScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_status_code", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusCodeQueryMap.put("product_status_code_InScope", convertedQuery);
        }
    }

    public void setProductStatusCode_NotInScope(Collection<String> queryList) {
        SolrQueryBuilder.assertNullQuery("product_status_code_NotInScope", this.productStatusCodeQueryMap.get("product_status_code_NotInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("product_status_code", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusCodeQueryMap.put("product_status_code_NotInScope", convertedQuery);
        }
    }

    public void setProductStatusCode_PrefixSearch(String query) {
        SolrQueryBuilder.assertNullQuery("product_status_code_PrefixSearch", this.productStatusCodeQueryMap.get("product_status_code_PrefixSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForPrefixSearch("product_status_code", query);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusCodeQueryMap.put("product_status_code_PrefixSearch", convertedQuery);
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
        SolrQueryBuilder.assertNullQuery("product_status_code_RangeSearc", this.productStatusCodeQueryMap.get("product_status_code_RangeSearc"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("product_status_code", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusCodeQueryMap.put("product_status_code_RangeSearc", convertedQuery);
        }
    }

    public void setProductStatusCode_SetRangeSearch(String cd, String from, String to) {
        SolrQueryBuilder.assertNullQuery("product_status_code_SetRangeSearch", this.productStatusCodeQueryMap.get("product_status_code_SetRangeSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearch("product_status_code", cd, from, to, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusCodeQueryMap.put("product_status_code_SetRangeSearch", convertedQuery);
        }
    }

    public void setProductStatusCode_SetRangeSearchInScope(Collection<SolrSetRangeSearchBean> beanList) {
        SolrQueryBuilder.assertNullQuery("product_status_code_SetRangeSearchInScope", this.productStatusCodeQueryMap.get("product_status_code_SetRangeSearchInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearchInScope("product_status_code", beanList, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.productStatusCodeQueryMap.put("product_status_code_SetRangeSearchInScope", convertedQuery);
        }
    }
    // ===========================================================
    // Query Setter for regular_price (long)
    //                                                  ==========
    /**
     * ExistsQuery( q=regular_price:* )
     */
    public void setRegularPrice_Exists() {
        SolrQueryBuilder.assertNullQuery("regular_price_Exists", this.regularPriceQueryMap.get("regular_price_Exists"));
        this.regularPriceQueryMap.put("regular_price_Exists", SolrQueryBuilder.queryBuilderForExists("regular_price"));
    }

    /**
     * ExistsQuery( q=NOT regular_price:* )
     */
    public void setRegularPrice_NotExists() {
        SolrQueryBuilder.assertNullQuery("regular_price_NotExists", this.regularPriceQueryMap.get("regular_price_NotExists"));
        this.regularPriceQueryMap.put("regular_price_NotExists", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("regular_price"), false));
    }

    /**
     * ExistsQuery( q=(query OR (NOT regular_price:*)) )
     */
    public void addRegularPrice_NotExists() {
        String value = null;
        if (!this.regularPriceQueryMap.isEmpty()) {
            List<String> list = DfCollectionUtil.newArrayList(this.regularPriceQueryMap.values());
            value = list.get(list.size() - 1);
        }
        SolrQueryBuilder.assertNotNullQuery("regular_price", value);
        this.regularPriceQueryMap.put("regular_price", SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(value, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("regular_price"), true)), SolrQueryLogicalOperator.OR)));
    }

    public void setRegularPrice_Equal(Long query) {
        SolrQueryBuilder.assertNullQuery("regular_price_Equal", this.regularPriceQueryMap.get("regular_price_Equal"));
        if (query != null) {
            this.regularPriceQueryMap.put("regular_price_Equal", SolrQueryBuilder.queryBuilderForEqual("regular_price", query.toString()));
        }
    }

    public void setRegularPrice_NotEqual(Long query) {
        SolrQueryBuilder.assertNullQuery("regular_price_NotEqual", this.regularPriceQueryMap.get("regular_price_NotEqual"));
        if (query != null) {
            this.regularPriceQueryMap.put("regular_price_NotEqual", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("regular_price", query.toString()), false));
        }
    }

    public void setRegularPrice_InScope(Collection<Long> queryList) {
        this.setRegularPrice_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setRegularPrice_InScope(Collection<Long> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("regular_price_InScope", this.regularPriceQueryMap.get("regular_price_InScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchList("regular_price", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.regularPriceQueryMap.put("regular_price_InScope", convertedQuery);
        }
    }

    public void setRegularPrice_NotInScope(Collection<Long> queryList) {
        SolrQueryBuilder.assertNullQuery("regular_price_NotInScope", this.regularPriceQueryMap.get("regular_price_NotInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchList("regular_price", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.regularPriceQueryMap.put("regular_price_NotInScope", convertedQuery);
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
        SolrQueryBuilder.assertNullQuery("regular_price_RangeSearch", this.regularPriceQueryMap.get("regular_price_RangeSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("regular_price", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.regularPriceQueryMap.put("regular_price_RangeSearch", convertedQuery);
        }
    }

    // ===========================================================
    // Query Setter for register_datetime (tdate)
    //                                                  ==========
    /**
     * ExistsQuery( q=register_datetime:* )
     */
    public void setRegisterDatetime_Exists() {
        SolrQueryBuilder.assertNullQuery("register_datetime_Exists", this.registerDatetimeQueryMap.get("register_datetime_Exists"));
        this.registerDatetimeQueryMap.put("register_datetime_Exists", SolrQueryBuilder.queryBuilderForExists("register_datetime"));
    }

    /**
     * ExistsQuery( q=NOT register_datetime:* )
     */
    public void setRegisterDatetime_NotExists() {
        SolrQueryBuilder.assertNullQuery("register_datetime_NotExists", this.registerDatetimeQueryMap.get("register_datetime_NotExists"));
        this.registerDatetimeQueryMap.put("register_datetime_NotExists", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("register_datetime"), false));
    }

    /**
     * ExistsQuery( q=(query OR (NOT register_datetime:*)) )
     */
    public void addRegisterDatetime_NotExists() {
        String value = null;
        if (!this.registerDatetimeQueryMap.isEmpty()) {
            List<String> list = DfCollectionUtil.newArrayList(this.registerDatetimeQueryMap.values());
            value = list.get(list.size() - 1);
        }
        SolrQueryBuilder.assertNotNullQuery("register_datetime", value);
        this.registerDatetimeQueryMap.put("register_datetime", SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(value, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("register_datetime"), true)), SolrQueryLogicalOperator.OR)));
    }

    public void setRegisterDatetime_Equal(java.time.LocalDateTime query) {
        SolrQueryBuilder.assertNullQuery("register_datetime_Equal", this.registerDatetimeQueryMap.get("register_datetime_Equal"));
        if (query != null) {
            this.registerDatetimeQueryMap.put("register_datetime_Equal", SolrQueryBuilder.queryBuilderForEqual("register_datetime", query.toString()));
        }
    }

    public void setRegisterDatetime_NotEqual(java.time.LocalDateTime query) {
        SolrQueryBuilder.assertNullQuery("register_datetime_NotEqual", this.registerDatetimeQueryMap.get("register_datetime_NotEqual"));
        if (query != null) {
            this.registerDatetimeQueryMap.put("register_datetime_NotEqual", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("register_datetime", query.toString()), false));
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
        SolrQueryBuilder.assertNullQuery("register_datetime_RangeSearch", this.registerDatetimeQueryMap.get("register_datetime_RangeSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("register_datetime", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.registerDatetimeQueryMap.put("register_datetime_RangeSearch", convertedQuery);
        }
    }
    // ===========================================================
    // Query Setter for register_user (string)
    //                                                  ==========
    /**
     * ExistsQuery( q=register_user:* )
     */
    public void setRegisterUser_Exists() {
        SolrQueryBuilder.assertNullQuery("register_user_Exists", this.registerUserQueryMap.get("register_user_Exists"));
        this.registerUserQueryMap.put("register_user_Exists", SolrQueryBuilder.queryBuilderForExists("register_user"));
    }

    /**
     * ExistsQuery( q=NOT register_user:* )
     */
    public void setRegisterUser_NotExists() {
        SolrQueryBuilder.assertNullQuery("register_user_NotExists", this.registerUserQueryMap.get("register_user_NotExists"));
        this.registerUserQueryMap.put("register_user_NotExists", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("register_user"), false));
    }

    /**
     * ExistsQuery( q=(query OR (NOT register_user:*)) )
     */
    public void addRegisterUser_NotExists() {
        String value = null;
        if (!this.registerUserQueryMap.isEmpty()) {
            List<String> list = DfCollectionUtil.newArrayList(this.registerUserQueryMap.values());
            value = list.get(list.size() - 1);
        }
        SolrQueryBuilder.assertNotNullQuery("register_user", value);
        this.registerUserQueryMap.put("register_user", SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(value, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("register_user"), true)), SolrQueryLogicalOperator.OR)));
    }

    public void setRegisterUser_Equal(String query) {
        SolrQueryBuilder.assertNullQuery("register_user_Equal", this.registerUserQueryMap.get("register_user_Equal"));
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.registerUserQueryMap.put("register_user_Equal", SolrQueryBuilder.queryBuilderForEqual("register_user", query.toString()));
        }
    }

    public void setRegisterUser_NotEqual(String query) {
        SolrQueryBuilder.assertNullQuery("register_user_NotEqual", this.registerUserQueryMap.get("register_user_NotEqual"));
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.registerUserQueryMap.put("register_user_NotEqual", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("register_user", query.toString()), false));
        }
    }

    public void setRegisterUser_InScope(Collection<String> queryList) {
        this.setRegisterUser_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setRegisterUser_InScope(Collection<String> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("register_user_InScope", this.registerUserQueryMap.get("register_user_InScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("register_user", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.registerUserQueryMap.put("register_user_InScope", convertedQuery);
        }
    }

    public void setRegisterUser_NotInScope(Collection<String> queryList) {
        SolrQueryBuilder.assertNullQuery("register_user_NotInScope", this.registerUserQueryMap.get("register_user_NotInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("register_user", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.registerUserQueryMap.put("register_user_NotInScope", convertedQuery);
        }
    }

    public void setRegisterUser_PrefixSearch(String query) {
        SolrQueryBuilder.assertNullQuery("register_user_PrefixSearch", this.registerUserQueryMap.get("register_user_PrefixSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForPrefixSearch("register_user", query);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.registerUserQueryMap.put("register_user_PrefixSearch", convertedQuery);
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
        SolrQueryBuilder.assertNullQuery("register_user_RangeSearc", this.registerUserQueryMap.get("register_user_RangeSearc"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("register_user", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.registerUserQueryMap.put("register_user_RangeSearc", convertedQuery);
        }
    }

    public void setRegisterUser_SetRangeSearch(String cd, String from, String to) {
        SolrQueryBuilder.assertNullQuery("register_user_SetRangeSearch", this.registerUserQueryMap.get("register_user_SetRangeSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearch("register_user", cd, from, to, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.registerUserQueryMap.put("register_user_SetRangeSearch", convertedQuery);
        }
    }

    public void setRegisterUser_SetRangeSearchInScope(Collection<SolrSetRangeSearchBean> beanList) {
        SolrQueryBuilder.assertNullQuery("register_user_SetRangeSearchInScope", this.registerUserQueryMap.get("register_user_SetRangeSearchInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearchInScope("register_user", beanList, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.registerUserQueryMap.put("register_user_SetRangeSearchInScope", convertedQuery);
        }
    }
    // ===========================================================
    // Query Setter for update_datetime (tdate)
    //                                                  ==========
    /**
     * ExistsQuery( q=update_datetime:* )
     */
    public void setUpdateDatetime_Exists() {
        SolrQueryBuilder.assertNullQuery("update_datetime_Exists", this.updateDatetimeQueryMap.get("update_datetime_Exists"));
        this.updateDatetimeQueryMap.put("update_datetime_Exists", SolrQueryBuilder.queryBuilderForExists("update_datetime"));
    }

    /**
     * ExistsQuery( q=NOT update_datetime:* )
     */
    public void setUpdateDatetime_NotExists() {
        SolrQueryBuilder.assertNullQuery("update_datetime_NotExists", this.updateDatetimeQueryMap.get("update_datetime_NotExists"));
        this.updateDatetimeQueryMap.put("update_datetime_NotExists", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("update_datetime"), false));
    }

    /**
     * ExistsQuery( q=(query OR (NOT update_datetime:*)) )
     */
    public void addUpdateDatetime_NotExists() {
        String value = null;
        if (!this.updateDatetimeQueryMap.isEmpty()) {
            List<String> list = DfCollectionUtil.newArrayList(this.updateDatetimeQueryMap.values());
            value = list.get(list.size() - 1);
        }
        SolrQueryBuilder.assertNotNullQuery("update_datetime", value);
        this.updateDatetimeQueryMap.put("update_datetime", SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(value, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("update_datetime"), true)), SolrQueryLogicalOperator.OR)));
    }

    public void setUpdateDatetime_Equal(java.time.LocalDateTime query) {
        SolrQueryBuilder.assertNullQuery("update_datetime_Equal", this.updateDatetimeQueryMap.get("update_datetime_Equal"));
        if (query != null) {
            this.updateDatetimeQueryMap.put("update_datetime_Equal", SolrQueryBuilder.queryBuilderForEqual("update_datetime", query.toString()));
        }
    }

    public void setUpdateDatetime_NotEqual(java.time.LocalDateTime query) {
        SolrQueryBuilder.assertNullQuery("update_datetime_NotEqual", this.updateDatetimeQueryMap.get("update_datetime_NotEqual"));
        if (query != null) {
            this.updateDatetimeQueryMap.put("update_datetime_NotEqual", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("update_datetime", query.toString()), false));
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
        SolrQueryBuilder.assertNullQuery("update_datetime_RangeSearch", this.updateDatetimeQueryMap.get("update_datetime_RangeSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("update_datetime", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.updateDatetimeQueryMap.put("update_datetime_RangeSearch", convertedQuery);
        }
    }
    // ===========================================================
    // Query Setter for update_user (string)
    //                                                  ==========
    /**
     * ExistsQuery( q=update_user:* )
     */
    public void setUpdateUser_Exists() {
        SolrQueryBuilder.assertNullQuery("update_user_Exists", this.updateUserQueryMap.get("update_user_Exists"));
        this.updateUserQueryMap.put("update_user_Exists", SolrQueryBuilder.queryBuilderForExists("update_user"));
    }

    /**
     * ExistsQuery( q=NOT update_user:* )
     */
    public void setUpdateUser_NotExists() {
        SolrQueryBuilder.assertNullQuery("update_user_NotExists", this.updateUserQueryMap.get("update_user_NotExists"));
        this.updateUserQueryMap.put("update_user_NotExists", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("update_user"), false));
    }

    /**
     * ExistsQuery( q=(query OR (NOT update_user:*)) )
     */
    public void addUpdateUser_NotExists() {
        String value = null;
        if (!this.updateUserQueryMap.isEmpty()) {
            List<String> list = DfCollectionUtil.newArrayList(this.updateUserQueryMap.values());
            value = list.get(list.size() - 1);
        }
        SolrQueryBuilder.assertNotNullQuery("update_user", value);
        this.updateUserQueryMap.put("update_user", SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(value, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("update_user"), true)), SolrQueryLogicalOperator.OR)));
    }

    public void setUpdateUser_Equal(String query) {
        SolrQueryBuilder.assertNullQuery("update_user_Equal", this.updateUserQueryMap.get("update_user_Equal"));
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.updateUserQueryMap.put("update_user_Equal", SolrQueryBuilder.queryBuilderForEqual("update_user", query.toString()));
        }
    }

    public void setUpdateUser_NotEqual(String query) {
        SolrQueryBuilder.assertNullQuery("update_user_NotEqual", this.updateUserQueryMap.get("update_user_NotEqual"));
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            this.updateUserQueryMap.put("update_user_NotEqual", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("update_user", query.toString()), false));
        }
    }

    public void setUpdateUser_InScope(Collection<String> queryList) {
        this.setUpdateUser_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setUpdateUser_InScope(Collection<String> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("update_user_InScope", this.updateUserQueryMap.get("update_user_InScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("update_user", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.updateUserQueryMap.put("update_user_InScope", convertedQuery);
        }
    }

    public void setUpdateUser_NotInScope(Collection<String> queryList) {
        SolrQueryBuilder.assertNullQuery("update_user_NotInScope", this.updateUserQueryMap.get("update_user_NotInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchWordList("update_user", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.updateUserQueryMap.put("update_user_NotInScope", convertedQuery);
        }
    }

    public void setUpdateUser_PrefixSearch(String query) {
        SolrQueryBuilder.assertNullQuery("update_user_PrefixSearch", this.updateUserQueryMap.get("update_user_PrefixSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForPrefixSearch("update_user", query);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.updateUserQueryMap.put("update_user_PrefixSearch", convertedQuery);
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
        SolrQueryBuilder.assertNullQuery("update_user_RangeSearc", this.updateUserQueryMap.get("update_user_RangeSearc"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("update_user", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.updateUserQueryMap.put("update_user_RangeSearc", convertedQuery);
        }
    }

    public void setUpdateUser_SetRangeSearch(String cd, String from, String to) {
        SolrQueryBuilder.assertNullQuery("update_user_SetRangeSearch", this.updateUserQueryMap.get("update_user_SetRangeSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearch("update_user", cd, from, to, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.updateUserQueryMap.put("update_user_SetRangeSearch", convertedQuery);
        }
    }

    public void setUpdateUser_SetRangeSearchInScope(Collection<SolrSetRangeSearchBean> beanList) {
        SolrQueryBuilder.assertNullQuery("update_user_SetRangeSearchInScope", this.updateUserQueryMap.get("update_user_SetRangeSearchInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSetRangeSearchInScope("update_user", beanList, SET_RANGE_SEARCH_PADDING_LENGTH, PADDING_STR);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.updateUserQueryMap.put("update_user_SetRangeSearchInScope", convertedQuery);
        }
    }
    // ===========================================================
    // Query Setter for _version_ (long)
    //                                                  ==========
    /**
     * ExistsQuery( q=_version_:* )
     */
    public void setVersion_Exists() {
        SolrQueryBuilder.assertNullQuery("_version__Exists", this.versionQueryMap.get("_version__Exists"));
        this.versionQueryMap.put("_version__Exists", SolrQueryBuilder.queryBuilderForExists("_version_"));
    }

    /**
     * ExistsQuery( q=NOT _version_:* )
     */
    public void setVersion_NotExists() {
        SolrQueryBuilder.assertNullQuery("_version__NotExists", this.versionQueryMap.get("_version__NotExists"));
        this.versionQueryMap.put("_version__NotExists", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("_version_"), false));
    }

    /**
     * ExistsQuery( q=(query OR (NOT _version_:*)) )
     */
    public void addVersion_NotExists() {
        String value = null;
        if (!this.versionQueryMap.isEmpty()) {
            List<String> list = DfCollectionUtil.newArrayList(this.versionQueryMap.values());
            value = list.get(list.size() - 1);
        }
        SolrQueryBuilder.assertNotNullQuery("_version_", value);
        this.versionQueryMap.put("_version_", SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(Arrays.asList(value, SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForExists("_version_"), true)), SolrQueryLogicalOperator.OR)));
    }

    public void setVersion_Equal(Long query) {
        SolrQueryBuilder.assertNullQuery("_version__Equal", this.versionQueryMap.get("_version__Equal"));
        if (query != null) {
            this.versionQueryMap.put("_version__Equal", SolrQueryBuilder.queryBuilderForEqual("_version_", query.toString()));
        }
    }

    public void setVersion_NotEqual(Long query) {
        SolrQueryBuilder.assertNullQuery("_version__NotEqual", this.versionQueryMap.get("_version__NotEqual"));
        if (query != null) {
            this.versionQueryMap.put("_version__NotEqual", SolrQueryBuilder.wrapNotGroupQuery(SolrQueryBuilder.queryBuilderForEqual("_version_", query.toString()), false));
        }
    }

    public void setVersion_InScope(Collection<Long> queryList) {
        this.setVersion_InScope(queryList, SolrQueryLogicalOperator.OR);
    }

    public void setVersion_InScope(Collection<Long> queryList, SolrQueryLogicalOperator operator) {
        SolrQueryBuilder.assertNullQuery("_version__InScope", this.versionQueryMap.get("_version__InScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchList("_version_", queryList, operator);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.versionQueryMap.put("_version__InScope", convertedQuery);
        }
    }

    public void setVersion_NotInScope(Collection<Long> queryList) {
        SolrQueryBuilder.assertNullQuery("_version__NotInScope", this.versionQueryMap.get("_version__NotInScope"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForSearchList("_version_", queryList, SolrQueryLogicalOperator.NOT);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.versionQueryMap.put("_version__NotInScope", convertedQuery);
        }
    }

    public void setVersion_RangeSearchFrom(Long from) {
        if (from != null) {
            setVersion_RangeSearch(from, null);
        }
    }

    public void setVersion_RangeSearchTo(Long to) {
        if (to != null) {
            setVersion_RangeSearch(null, to);
        }
    }

    public void setVersion_RangeSearch(Long from, Long to) {
        SolrQueryBuilder.assertNullQuery("_version__RangeSearch", this.versionQueryMap.get("_version__RangeSearch"));
        String convertedQuery = SolrQueryBuilder.queryBuilderForRangeSearch("_version_", from, to);
        if (DfStringUtil.is_NotNull_and_NotEmpty(convertedQuery)) {
            this.versionQueryMap.put("_version__RangeSearch", convertedQuery);
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
        queryList.addAll(productIdQueryMap.values());
        queryList.addAll(latestPurchaseDateQueryMap.values());
        queryList.addAll(productCategoryQueryMap.values());
        queryList.addAll(productCategoryCodeQueryMap.values());
        queryList.addAll(productHandleCodeQueryMap.values());
        queryList.addAll(productNameQueryMap.values());
        queryList.addAll(productStatusQueryMap.values());
        queryList.addAll(productStatusCodeQueryMap.values());
        queryList.addAll(regularPriceQueryMap.values());
        queryList.addAll(registerDatetimeQueryMap.values());
        queryList.addAll(registerUserQueryMap.values());
        queryList.addAll(updateDatetimeQueryMap.values());
        queryList.addAll(updateUserQueryMap.values());
        queryList.addAll(versionQueryMap.values());
        List<String> nestQueryList = getNestQueryList();
        if (!nestQueryList.isEmpty()) {
            queryList.addAll(nestQueryList);
        }
        return queryList;
    }
}
