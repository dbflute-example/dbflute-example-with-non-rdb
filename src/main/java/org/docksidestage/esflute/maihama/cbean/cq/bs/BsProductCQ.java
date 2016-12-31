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
package org.docksidestage.esflute.maihama.cbean.cq.bs;

import java.time.*;
import java.util.ArrayList;

import java.util.Collection;

import org.docksidestage.esflute.maihama.allcommon.EsAbstractConditionQuery;
import org.docksidestage.esflute.maihama.cbean.cq.ProductCQ;
import org.dbflute.cbean.ckey.ConditionKey;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder.FilterFunctionBuilder;

/**
 * @author ESFlute (using FreeGen)
 */
public abstract class BsProductCQ extends EsAbstractConditionQuery {

    protected static final Class<?> suppressUnusedImportLocalDateTime = LocalDateTime.class;

    // ===================================================================================
    //                                                                       Name Override
    //                                                                       =============
    @Override
    public String asTableDbName() {
        return "product";
    }

    @Override
    public String xgetAliasName() {
        return "product";
    }

    // ===================================================================================
    //                                                                       Query Control
    //                                                                       =============
    public void functionScore(OperatorCall<ProductCQ> queryLambda, ScoreFunctionCall<ScoreFunctionCreator<ProductCQ>> functionsLambda,
            final ConditionOptionCall<FunctionScoreQueryBuilder> opLambda) {
        ProductCQ cq = new ProductCQ();
        queryLambda.callback(cq);
        final Collection<FilterFunctionBuilder> list = new ArrayList<>();
        if (functionsLambda != null) {
            functionsLambda.callback((cqLambda, scoreFunctionBuilder) -> {
                ProductCQ cf = new ProductCQ();
                cqLambda.callback(cf);
                list.add(new FilterFunctionBuilder(cf.getQuery(), scoreFunctionBuilder));
            });
        }
        final FunctionScoreQueryBuilder builder = regFunctionScoreQ(cq.getQuery(), list);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void filtered(FilteredCall<ProductCQ, ProductCQ> filteredLambda) {
        filtered(filteredLambda, null);
    }

    public void filtered(FilteredCall<ProductCQ, ProductCQ> filteredLambda,
            ConditionOptionCall<BoolQueryBuilder> opLambda) {
        bool((must, should, mustNot, filter)->{
            filteredLambda.callback(must, filter);
        }, opLambda);
    }

    public void not(OperatorCall<ProductCQ> notLambda) {
        not(notLambda, null);
    }

    public void not(final OperatorCall<ProductCQ> notLambda, final ConditionOptionCall<BoolQueryBuilder> opLambda) {
        bool((must, should, mustNot, filter) -> notLambda.callback(mustNot), opLambda);
    }

    public void bool(BoolCall<ProductCQ> boolLambda) {
        bool(boolLambda, null);
    }

    public void bool(BoolCall<ProductCQ> boolLambda, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        ProductCQ mustQuery = new ProductCQ();
        ProductCQ shouldQuery = new ProductCQ();
        ProductCQ mustNotQuery = new ProductCQ();
        ProductCQ filterQuery = new ProductCQ();
        boolLambda.callback(mustQuery, shouldQuery, mustNotQuery, filterQuery);
        if (mustQuery.hasQueries() || shouldQuery.hasQueries() || mustNotQuery.hasQueries() || filterQuery.hasQueries()) {
            BoolQueryBuilder builder = regBoolCQ(mustQuery.getQueryBuilderList(), shouldQuery.getQueryBuilderList(), mustNotQuery.getQueryBuilderList(), filterQuery.getQueryBuilderList());
            if (opLambda != null) {
                opLambda.callback(builder);
            }
        }
    }

    // ===================================================================================
    //                                                                           Query Set
    //                                                                           =========
    public void setId_Equal(String id) {
        setId_Term(id, null);
    }

    public void setId_Equal(String id, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setId_Term(id, opLambda);
    }

    public void setId_Term(String id) {
        setId_Term(id, null);
    }

    public void setId_Term(String id, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("_id", id);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setId_NotEqual(String id) {
        setId_NotTerm(id, null);
    }

    public void setId_NotTerm(String id) {
        setId_NotTerm(id, null);
    }

    public void setId_NotEqual(String id, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        setId_NotTerm(id, opLambda);
    }

    public void setId_NotTerm(String id, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        not(not -> not.setId_Term(id), opLambda);
    }

    public void setId_Terms(Collection<String> idList) {
        setId_Terms(idList, null);
    }

    public void setId_Terms(Collection<String> idList, ConditionOptionCall<IdsQueryBuilder> opLambda) {
        IdsQueryBuilder builder = regIdsQ(idList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setId_InScope(Collection<String> idList) {
        setId_Terms(idList, null);
    }

    public void setId_InScope(Collection<String> idList, ConditionOptionCall<IdsQueryBuilder> opLambda) {
        setId_Terms(idList, opLambda);
    }

    public BsProductCQ addOrderBy_Id_Asc() {
        regOBA("_uid");
        return this;
    }

    public BsProductCQ addOrderBy_Id_Desc() {
        regOBD("_uid");
        return this;
    }

    public void setLatestPurchaseDate_Equal(LocalDateTime latestPurchaseDate) {
        setLatestPurchaseDate_Term(latestPurchaseDate, null);
    }

    public void setLatestPurchaseDate_Equal(LocalDateTime latestPurchaseDate, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setLatestPurchaseDate_Term(latestPurchaseDate, opLambda);
    }

    public void setLatestPurchaseDate_Term(LocalDateTime latestPurchaseDate) {
        setLatestPurchaseDate_Term(latestPurchaseDate, null);
    }

    public void setLatestPurchaseDate_Term(LocalDateTime latestPurchaseDate, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("latest_purchase_date", latestPurchaseDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setLatestPurchaseDate_NotEqual(LocalDateTime latestPurchaseDate) {
        setLatestPurchaseDate_NotTerm(latestPurchaseDate, null);
    }

    public void setLatestPurchaseDate_NotTerm(LocalDateTime latestPurchaseDate) {
        setLatestPurchaseDate_NotTerm(latestPurchaseDate, null);
    }

    public void setLatestPurchaseDate_NotEqual(LocalDateTime latestPurchaseDate, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        setLatestPurchaseDate_NotTerm(latestPurchaseDate, opLambda);
    }

    public void setLatestPurchaseDate_NotTerm(LocalDateTime latestPurchaseDate, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        not(not -> not.setLatestPurchaseDate_Term(latestPurchaseDate), opLambda);
    }

    public void setLatestPurchaseDate_Terms(Collection<LocalDateTime> latestPurchaseDateList) {
        setLatestPurchaseDate_Terms(latestPurchaseDateList, null);
    }

    public void setLatestPurchaseDate_Terms(Collection<LocalDateTime> latestPurchaseDateList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("latest_purchase_date", latestPurchaseDateList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setLatestPurchaseDate_InScope(Collection<LocalDateTime> latestPurchaseDateList) {
        setLatestPurchaseDate_Terms(latestPurchaseDateList, null);
    }

    public void setLatestPurchaseDate_InScope(Collection<LocalDateTime> latestPurchaseDateList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setLatestPurchaseDate_Terms(latestPurchaseDateList, opLambda);
    }

    public void setLatestPurchaseDate_Match(LocalDateTime latestPurchaseDate) {
        setLatestPurchaseDate_Match(latestPurchaseDate, null);
    }

    public void setLatestPurchaseDate_Match(LocalDateTime latestPurchaseDate, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("latest_purchase_date", latestPurchaseDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setLatestPurchaseDate_MatchPhrase(LocalDateTime latestPurchaseDate) {
        setLatestPurchaseDate_MatchPhrase(latestPurchaseDate, null);
    }

    public void setLatestPurchaseDate_MatchPhrase(LocalDateTime latestPurchaseDate, ConditionOptionCall<MatchPhraseQueryBuilder> opLambda) {
        MatchPhraseQueryBuilder builder = regMatchPhraseQ("latest_purchase_date", latestPurchaseDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setLatestPurchaseDate_MatchPhrasePrefix(LocalDateTime latestPurchaseDate) {
        setLatestPurchaseDate_MatchPhrasePrefix(latestPurchaseDate, null);
    }

    public void setLatestPurchaseDate_MatchPhrasePrefix(LocalDateTime latestPurchaseDate, ConditionOptionCall<MatchPhrasePrefixQueryBuilder> opLambda) {
        MatchPhrasePrefixQueryBuilder builder = regMatchPhrasePrefixQ("latest_purchase_date", latestPurchaseDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setLatestPurchaseDate_Fuzzy(LocalDateTime latestPurchaseDate) {
        setLatestPurchaseDate_Fuzzy(latestPurchaseDate, null);
    }

    public void setLatestPurchaseDate_Fuzzy(LocalDateTime latestPurchaseDate, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regFuzzyQ("latest_purchase_date", latestPurchaseDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setLatestPurchaseDate_GreaterThan(LocalDateTime latestPurchaseDate) {
        setLatestPurchaseDate_GreaterThan(latestPurchaseDate, null);
    }

    public void setLatestPurchaseDate_GreaterThan(LocalDateTime latestPurchaseDate, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = toRangeLocalDateTimeString(latestPurchaseDate, "date_optional_time");
        RangeQueryBuilder builder = regRangeQ("latest_purchase_date", ConditionKey.CK_GREATER_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setLatestPurchaseDate_LessThan(LocalDateTime latestPurchaseDate) {
        setLatestPurchaseDate_LessThan(latestPurchaseDate, null);
    }

    public void setLatestPurchaseDate_LessThan(LocalDateTime latestPurchaseDate, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = toRangeLocalDateTimeString(latestPurchaseDate, "date_optional_time");
        RangeQueryBuilder builder = regRangeQ("latest_purchase_date", ConditionKey.CK_LESS_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setLatestPurchaseDate_GreaterEqual(LocalDateTime latestPurchaseDate) {
        setLatestPurchaseDate_GreaterEqual(latestPurchaseDate, null);
    }

    public void setLatestPurchaseDate_GreaterEqual(LocalDateTime latestPurchaseDate, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = toRangeLocalDateTimeString(latestPurchaseDate, "date_optional_time");
        RangeQueryBuilder builder = regRangeQ("latest_purchase_date", ConditionKey.CK_GREATER_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setLatestPurchaseDate_LessEqual(LocalDateTime latestPurchaseDate) {
        setLatestPurchaseDate_LessEqual(latestPurchaseDate, null);
    }

    public void setLatestPurchaseDate_LessEqual(LocalDateTime latestPurchaseDate, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = toRangeLocalDateTimeString(latestPurchaseDate, "date_optional_time");
        RangeQueryBuilder builder = regRangeQ("latest_purchase_date", ConditionKey.CK_LESS_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setLatestPurchaseDate_Exists() {
        setLatestPurchaseDate_Exists(null);
    }

    public void setLatestPurchaseDate_Exists(ConditionOptionCall<ExistsQueryBuilder> opLambda) {
        ExistsQueryBuilder builder = regExistsQ("latest_purchase_date");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setLatestPurchaseDate_CommonTerms(LocalDateTime latestPurchaseDate) {
        setLatestPurchaseDate_CommonTerms(latestPurchaseDate, null);
    }

    public void setLatestPurchaseDate_CommonTerms(LocalDateTime latestPurchaseDate, ConditionOptionCall<CommonTermsQueryBuilder> opLambda) {
        CommonTermsQueryBuilder builder = regCommonTermsQ("latest_purchase_date", latestPurchaseDate);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_LatestPurchaseDate_Asc() {
        regOBA("latest_purchase_date");
        return this;
    }

    public BsProductCQ addOrderBy_LatestPurchaseDate_Desc() {
        regOBD("latest_purchase_date");
        return this;
    }


    public void setProductCategory_Equal(String productCategory) {
        setProductCategory_Term(productCategory, null);
    }

    public void setProductCategory_Equal(String productCategory, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setProductCategory_Term(productCategory, opLambda);
    }

    public void setProductCategory_Term(String productCategory) {
        setProductCategory_Term(productCategory, null);
    }

    public void setProductCategory_Term(String productCategory, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("product_category", productCategory);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategory_NotEqual(String productCategory) {
        setProductCategory_NotTerm(productCategory, null);
    }

    public void setProductCategory_NotTerm(String productCategory) {
        setProductCategory_NotTerm(productCategory, null);
    }

    public void setProductCategory_NotEqual(String productCategory, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        setProductCategory_NotTerm(productCategory, opLambda);
    }

    public void setProductCategory_NotTerm(String productCategory, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        not(not -> not.setProductCategory_Term(productCategory), opLambda);
    }

    public void setProductCategory_Terms(Collection<String> productCategoryList) {
        setProductCategory_Terms(productCategoryList, null);
    }

    public void setProductCategory_Terms(Collection<String> productCategoryList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("product_category", productCategoryList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategory_InScope(Collection<String> productCategoryList) {
        setProductCategory_Terms(productCategoryList, null);
    }

    public void setProductCategory_InScope(Collection<String> productCategoryList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setProductCategory_Terms(productCategoryList, opLambda);
    }

    public void setProductCategory_Match(String productCategory) {
        setProductCategory_Match(productCategory, null);
    }

    public void setProductCategory_Match(String productCategory, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("product_category", productCategory);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategory_MatchPhrase(String productCategory) {
        setProductCategory_MatchPhrase(productCategory, null);
    }

    public void setProductCategory_MatchPhrase(String productCategory, ConditionOptionCall<MatchPhraseQueryBuilder> opLambda) {
        MatchPhraseQueryBuilder builder = regMatchPhraseQ("product_category", productCategory);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategory_MatchPhrasePrefix(String productCategory) {
        setProductCategory_MatchPhrasePrefix(productCategory, null);
    }

    public void setProductCategory_MatchPhrasePrefix(String productCategory, ConditionOptionCall<MatchPhrasePrefixQueryBuilder> opLambda) {
        MatchPhrasePrefixQueryBuilder builder = regMatchPhrasePrefixQ("product_category", productCategory);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategory_Fuzzy(String productCategory) {
        setProductCategory_Fuzzy(productCategory, null);
    }

    public void setProductCategory_Fuzzy(String productCategory, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regFuzzyQ("product_category", productCategory);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategory_Prefix(String productCategory) {
        setProductCategory_Prefix(productCategory, null);
    }

    public void setProductCategory_Prefix(String productCategory, ConditionOptionCall<PrefixQueryBuilder> opLambda) {
        PrefixQueryBuilder builder = regPrefixQ("product_category", productCategory);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategory_Wildcard(String productCategory) {
        setProductCategory_Wildcard(productCategory, null);
    }

    public void setProductCategory_Wildcard(String productCategory, ConditionOptionCall<WildcardQueryBuilder> opLambda) {
        WildcardQueryBuilder builder = regWildcardQ("product_category", productCategory);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategory_Regexp(String productCategory) {
        setProductCategory_Regexp(productCategory, null);
    }

    public void setProductCategory_Regexp(String productCategory, ConditionOptionCall<RegexpQueryBuilder> opLambda) {
        RegexpQueryBuilder builder = regRegexpQ("product_category", productCategory);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategory_SpanTerm(String productCategory) {
        setProductCategory_SpanTerm("product_category", null);
    }

    public void setProductCategory_SpanTerm(String productCategory, ConditionOptionCall<SpanTermQueryBuilder> opLambda) {
        SpanTermQueryBuilder builder = regSpanTermQ("product_category", productCategory);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }
    public void setProductCategory_GreaterThan(String productCategory) {
        setProductCategory_GreaterThan(productCategory, null);
    }

    public void setProductCategory_GreaterThan(String productCategory, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productCategory;
        RangeQueryBuilder builder = regRangeQ("product_category", ConditionKey.CK_GREATER_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategory_LessThan(String productCategory) {
        setProductCategory_LessThan(productCategory, null);
    }

    public void setProductCategory_LessThan(String productCategory, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productCategory;
        RangeQueryBuilder builder = regRangeQ("product_category", ConditionKey.CK_LESS_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategory_GreaterEqual(String productCategory) {
        setProductCategory_GreaterEqual(productCategory, null);
    }

    public void setProductCategory_GreaterEqual(String productCategory, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productCategory;
        RangeQueryBuilder builder = regRangeQ("product_category", ConditionKey.CK_GREATER_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategory_LessEqual(String productCategory) {
        setProductCategory_LessEqual(productCategory, null);
    }

    public void setProductCategory_LessEqual(String productCategory, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productCategory;
        RangeQueryBuilder builder = regRangeQ("product_category", ConditionKey.CK_LESS_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategory_Exists() {
        setProductCategory_Exists(null);
    }

    public void setProductCategory_Exists(ConditionOptionCall<ExistsQueryBuilder> opLambda) {
        ExistsQueryBuilder builder = regExistsQ("product_category");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategory_CommonTerms(String productCategory) {
        setProductCategory_CommonTerms(productCategory, null);
    }

    public void setProductCategory_CommonTerms(String productCategory, ConditionOptionCall<CommonTermsQueryBuilder> opLambda) {
        CommonTermsQueryBuilder builder = regCommonTermsQ("product_category", productCategory);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_ProductCategory_Asc() {
        regOBA("product_category");
        return this;
    }

    public BsProductCQ addOrderBy_ProductCategory_Desc() {
        regOBD("product_category");
        return this;
    }


    public void setProductCategoryCode_Equal(String productCategoryCode) {
        setProductCategoryCode_Term(productCategoryCode, null);
    }

    public void setProductCategoryCode_Equal(String productCategoryCode, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setProductCategoryCode_Term(productCategoryCode, opLambda);
    }

    public void setProductCategoryCode_Term(String productCategoryCode) {
        setProductCategoryCode_Term(productCategoryCode, null);
    }

    public void setProductCategoryCode_Term(String productCategoryCode, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("product_category_code", productCategoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategoryCode_NotEqual(String productCategoryCode) {
        setProductCategoryCode_NotTerm(productCategoryCode, null);
    }

    public void setProductCategoryCode_NotTerm(String productCategoryCode) {
        setProductCategoryCode_NotTerm(productCategoryCode, null);
    }

    public void setProductCategoryCode_NotEqual(String productCategoryCode, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        setProductCategoryCode_NotTerm(productCategoryCode, opLambda);
    }

    public void setProductCategoryCode_NotTerm(String productCategoryCode, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        not(not -> not.setProductCategoryCode_Term(productCategoryCode), opLambda);
    }

    public void setProductCategoryCode_Terms(Collection<String> productCategoryCodeList) {
        setProductCategoryCode_Terms(productCategoryCodeList, null);
    }

    public void setProductCategoryCode_Terms(Collection<String> productCategoryCodeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("product_category_code", productCategoryCodeList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategoryCode_InScope(Collection<String> productCategoryCodeList) {
        setProductCategoryCode_Terms(productCategoryCodeList, null);
    }

    public void setProductCategoryCode_InScope(Collection<String> productCategoryCodeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setProductCategoryCode_Terms(productCategoryCodeList, opLambda);
    }

    public void setProductCategoryCode_Match(String productCategoryCode) {
        setProductCategoryCode_Match(productCategoryCode, null);
    }

    public void setProductCategoryCode_Match(String productCategoryCode, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("product_category_code", productCategoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategoryCode_MatchPhrase(String productCategoryCode) {
        setProductCategoryCode_MatchPhrase(productCategoryCode, null);
    }

    public void setProductCategoryCode_MatchPhrase(String productCategoryCode, ConditionOptionCall<MatchPhraseQueryBuilder> opLambda) {
        MatchPhraseQueryBuilder builder = regMatchPhraseQ("product_category_code", productCategoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategoryCode_MatchPhrasePrefix(String productCategoryCode) {
        setProductCategoryCode_MatchPhrasePrefix(productCategoryCode, null);
    }

    public void setProductCategoryCode_MatchPhrasePrefix(String productCategoryCode, ConditionOptionCall<MatchPhrasePrefixQueryBuilder> opLambda) {
        MatchPhrasePrefixQueryBuilder builder = regMatchPhrasePrefixQ("product_category_code", productCategoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategoryCode_Fuzzy(String productCategoryCode) {
        setProductCategoryCode_Fuzzy(productCategoryCode, null);
    }

    public void setProductCategoryCode_Fuzzy(String productCategoryCode, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regFuzzyQ("product_category_code", productCategoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategoryCode_Prefix(String productCategoryCode) {
        setProductCategoryCode_Prefix(productCategoryCode, null);
    }

    public void setProductCategoryCode_Prefix(String productCategoryCode, ConditionOptionCall<PrefixQueryBuilder> opLambda) {
        PrefixQueryBuilder builder = regPrefixQ("product_category_code", productCategoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategoryCode_Wildcard(String productCategoryCode) {
        setProductCategoryCode_Wildcard(productCategoryCode, null);
    }

    public void setProductCategoryCode_Wildcard(String productCategoryCode, ConditionOptionCall<WildcardQueryBuilder> opLambda) {
        WildcardQueryBuilder builder = regWildcardQ("product_category_code", productCategoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategoryCode_Regexp(String productCategoryCode) {
        setProductCategoryCode_Regexp(productCategoryCode, null);
    }

    public void setProductCategoryCode_Regexp(String productCategoryCode, ConditionOptionCall<RegexpQueryBuilder> opLambda) {
        RegexpQueryBuilder builder = regRegexpQ("product_category_code", productCategoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategoryCode_SpanTerm(String productCategoryCode) {
        setProductCategoryCode_SpanTerm("product_category_code", null);
    }

    public void setProductCategoryCode_SpanTerm(String productCategoryCode, ConditionOptionCall<SpanTermQueryBuilder> opLambda) {
        SpanTermQueryBuilder builder = regSpanTermQ("product_category_code", productCategoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }
    public void setProductCategoryCode_GreaterThan(String productCategoryCode) {
        setProductCategoryCode_GreaterThan(productCategoryCode, null);
    }

    public void setProductCategoryCode_GreaterThan(String productCategoryCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productCategoryCode;
        RangeQueryBuilder builder = regRangeQ("product_category_code", ConditionKey.CK_GREATER_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategoryCode_LessThan(String productCategoryCode) {
        setProductCategoryCode_LessThan(productCategoryCode, null);
    }

    public void setProductCategoryCode_LessThan(String productCategoryCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productCategoryCode;
        RangeQueryBuilder builder = regRangeQ("product_category_code", ConditionKey.CK_LESS_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategoryCode_GreaterEqual(String productCategoryCode) {
        setProductCategoryCode_GreaterEqual(productCategoryCode, null);
    }

    public void setProductCategoryCode_GreaterEqual(String productCategoryCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productCategoryCode;
        RangeQueryBuilder builder = regRangeQ("product_category_code", ConditionKey.CK_GREATER_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategoryCode_LessEqual(String productCategoryCode) {
        setProductCategoryCode_LessEqual(productCategoryCode, null);
    }

    public void setProductCategoryCode_LessEqual(String productCategoryCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productCategoryCode;
        RangeQueryBuilder builder = regRangeQ("product_category_code", ConditionKey.CK_LESS_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategoryCode_Exists() {
        setProductCategoryCode_Exists(null);
    }

    public void setProductCategoryCode_Exists(ConditionOptionCall<ExistsQueryBuilder> opLambda) {
        ExistsQueryBuilder builder = regExistsQ("product_category_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategoryCode_CommonTerms(String productCategoryCode) {
        setProductCategoryCode_CommonTerms(productCategoryCode, null);
    }

    public void setProductCategoryCode_CommonTerms(String productCategoryCode, ConditionOptionCall<CommonTermsQueryBuilder> opLambda) {
        CommonTermsQueryBuilder builder = regCommonTermsQ("product_category_code", productCategoryCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_ProductCategoryCode_Asc() {
        regOBA("product_category_code");
        return this;
    }

    public BsProductCQ addOrderBy_ProductCategoryCode_Desc() {
        regOBD("product_category_code");
        return this;
    }


    public void setProductDescription_Equal(String productDescription) {
        setProductDescription_Term(productDescription, null);
    }

    public void setProductDescription_Equal(String productDescription, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setProductDescription_Term(productDescription, opLambda);
    }

    public void setProductDescription_Term(String productDescription) {
        setProductDescription_Term(productDescription, null);
    }

    public void setProductDescription_Term(String productDescription, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("product_description", productDescription);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductDescription_NotEqual(String productDescription) {
        setProductDescription_NotTerm(productDescription, null);
    }

    public void setProductDescription_NotTerm(String productDescription) {
        setProductDescription_NotTerm(productDescription, null);
    }

    public void setProductDescription_NotEqual(String productDescription, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        setProductDescription_NotTerm(productDescription, opLambda);
    }

    public void setProductDescription_NotTerm(String productDescription, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        not(not -> not.setProductDescription_Term(productDescription), opLambda);
    }

    public void setProductDescription_Terms(Collection<String> productDescriptionList) {
        setProductDescription_Terms(productDescriptionList, null);
    }

    public void setProductDescription_Terms(Collection<String> productDescriptionList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("product_description", productDescriptionList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductDescription_InScope(Collection<String> productDescriptionList) {
        setProductDescription_Terms(productDescriptionList, null);
    }

    public void setProductDescription_InScope(Collection<String> productDescriptionList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setProductDescription_Terms(productDescriptionList, opLambda);
    }

    public void setProductDescription_Match(String productDescription) {
        setProductDescription_Match(productDescription, null);
    }

    public void setProductDescription_Match(String productDescription, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("product_description", productDescription);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductDescription_MatchPhrase(String productDescription) {
        setProductDescription_MatchPhrase(productDescription, null);
    }

    public void setProductDescription_MatchPhrase(String productDescription, ConditionOptionCall<MatchPhraseQueryBuilder> opLambda) {
        MatchPhraseQueryBuilder builder = regMatchPhraseQ("product_description", productDescription);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductDescription_MatchPhrasePrefix(String productDescription) {
        setProductDescription_MatchPhrasePrefix(productDescription, null);
    }

    public void setProductDescription_MatchPhrasePrefix(String productDescription, ConditionOptionCall<MatchPhrasePrefixQueryBuilder> opLambda) {
        MatchPhrasePrefixQueryBuilder builder = regMatchPhrasePrefixQ("product_description", productDescription);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductDescription_Fuzzy(String productDescription) {
        setProductDescription_Fuzzy(productDescription, null);
    }

    public void setProductDescription_Fuzzy(String productDescription, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regFuzzyQ("product_description", productDescription);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductDescription_Prefix(String productDescription) {
        setProductDescription_Prefix(productDescription, null);
    }

    public void setProductDescription_Prefix(String productDescription, ConditionOptionCall<PrefixQueryBuilder> opLambda) {
        PrefixQueryBuilder builder = regPrefixQ("product_description", productDescription);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductDescription_Wildcard(String productDescription) {
        setProductDescription_Wildcard(productDescription, null);
    }

    public void setProductDescription_Wildcard(String productDescription, ConditionOptionCall<WildcardQueryBuilder> opLambda) {
        WildcardQueryBuilder builder = regWildcardQ("product_description", productDescription);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductDescription_Regexp(String productDescription) {
        setProductDescription_Regexp(productDescription, null);
    }

    public void setProductDescription_Regexp(String productDescription, ConditionOptionCall<RegexpQueryBuilder> opLambda) {
        RegexpQueryBuilder builder = regRegexpQ("product_description", productDescription);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductDescription_SpanTerm(String productDescription) {
        setProductDescription_SpanTerm("product_description", null);
    }

    public void setProductDescription_SpanTerm(String productDescription, ConditionOptionCall<SpanTermQueryBuilder> opLambda) {
        SpanTermQueryBuilder builder = regSpanTermQ("product_description", productDescription);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }
    public void setProductDescription_GreaterThan(String productDescription) {
        setProductDescription_GreaterThan(productDescription, null);
    }

    public void setProductDescription_GreaterThan(String productDescription, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productDescription;
        RangeQueryBuilder builder = regRangeQ("product_description", ConditionKey.CK_GREATER_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductDescription_LessThan(String productDescription) {
        setProductDescription_LessThan(productDescription, null);
    }

    public void setProductDescription_LessThan(String productDescription, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productDescription;
        RangeQueryBuilder builder = regRangeQ("product_description", ConditionKey.CK_LESS_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductDescription_GreaterEqual(String productDescription) {
        setProductDescription_GreaterEqual(productDescription, null);
    }

    public void setProductDescription_GreaterEqual(String productDescription, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productDescription;
        RangeQueryBuilder builder = regRangeQ("product_description", ConditionKey.CK_GREATER_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductDescription_LessEqual(String productDescription) {
        setProductDescription_LessEqual(productDescription, null);
    }

    public void setProductDescription_LessEqual(String productDescription, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productDescription;
        RangeQueryBuilder builder = regRangeQ("product_description", ConditionKey.CK_LESS_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductDescription_Exists() {
        setProductDescription_Exists(null);
    }

    public void setProductDescription_Exists(ConditionOptionCall<ExistsQueryBuilder> opLambda) {
        ExistsQueryBuilder builder = regExistsQ("product_description");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductDescription_CommonTerms(String productDescription) {
        setProductDescription_CommonTerms(productDescription, null);
    }

    public void setProductDescription_CommonTerms(String productDescription, ConditionOptionCall<CommonTermsQueryBuilder> opLambda) {
        CommonTermsQueryBuilder builder = regCommonTermsQ("product_description", productDescription);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }



    public void setProductHandleCode_Equal(String productHandleCode) {
        setProductHandleCode_Term(productHandleCode, null);
    }

    public void setProductHandleCode_Equal(String productHandleCode, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setProductHandleCode_Term(productHandleCode, opLambda);
    }

    public void setProductHandleCode_Term(String productHandleCode) {
        setProductHandleCode_Term(productHandleCode, null);
    }

    public void setProductHandleCode_Term(String productHandleCode, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("product_handle_code", productHandleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductHandleCode_NotEqual(String productHandleCode) {
        setProductHandleCode_NotTerm(productHandleCode, null);
    }

    public void setProductHandleCode_NotTerm(String productHandleCode) {
        setProductHandleCode_NotTerm(productHandleCode, null);
    }

    public void setProductHandleCode_NotEqual(String productHandleCode, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        setProductHandleCode_NotTerm(productHandleCode, opLambda);
    }

    public void setProductHandleCode_NotTerm(String productHandleCode, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        not(not -> not.setProductHandleCode_Term(productHandleCode), opLambda);
    }

    public void setProductHandleCode_Terms(Collection<String> productHandleCodeList) {
        setProductHandleCode_Terms(productHandleCodeList, null);
    }

    public void setProductHandleCode_Terms(Collection<String> productHandleCodeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("product_handle_code", productHandleCodeList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductHandleCode_InScope(Collection<String> productHandleCodeList) {
        setProductHandleCode_Terms(productHandleCodeList, null);
    }

    public void setProductHandleCode_InScope(Collection<String> productHandleCodeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setProductHandleCode_Terms(productHandleCodeList, opLambda);
    }

    public void setProductHandleCode_Match(String productHandleCode) {
        setProductHandleCode_Match(productHandleCode, null);
    }

    public void setProductHandleCode_Match(String productHandleCode, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("product_handle_code", productHandleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductHandleCode_MatchPhrase(String productHandleCode) {
        setProductHandleCode_MatchPhrase(productHandleCode, null);
    }

    public void setProductHandleCode_MatchPhrase(String productHandleCode, ConditionOptionCall<MatchPhraseQueryBuilder> opLambda) {
        MatchPhraseQueryBuilder builder = regMatchPhraseQ("product_handle_code", productHandleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductHandleCode_MatchPhrasePrefix(String productHandleCode) {
        setProductHandleCode_MatchPhrasePrefix(productHandleCode, null);
    }

    public void setProductHandleCode_MatchPhrasePrefix(String productHandleCode, ConditionOptionCall<MatchPhrasePrefixQueryBuilder> opLambda) {
        MatchPhrasePrefixQueryBuilder builder = regMatchPhrasePrefixQ("product_handle_code", productHandleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductHandleCode_Fuzzy(String productHandleCode) {
        setProductHandleCode_Fuzzy(productHandleCode, null);
    }

    public void setProductHandleCode_Fuzzy(String productHandleCode, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regFuzzyQ("product_handle_code", productHandleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductHandleCode_Prefix(String productHandleCode) {
        setProductHandleCode_Prefix(productHandleCode, null);
    }

    public void setProductHandleCode_Prefix(String productHandleCode, ConditionOptionCall<PrefixQueryBuilder> opLambda) {
        PrefixQueryBuilder builder = regPrefixQ("product_handle_code", productHandleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductHandleCode_Wildcard(String productHandleCode) {
        setProductHandleCode_Wildcard(productHandleCode, null);
    }

    public void setProductHandleCode_Wildcard(String productHandleCode, ConditionOptionCall<WildcardQueryBuilder> opLambda) {
        WildcardQueryBuilder builder = regWildcardQ("product_handle_code", productHandleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductHandleCode_Regexp(String productHandleCode) {
        setProductHandleCode_Regexp(productHandleCode, null);
    }

    public void setProductHandleCode_Regexp(String productHandleCode, ConditionOptionCall<RegexpQueryBuilder> opLambda) {
        RegexpQueryBuilder builder = regRegexpQ("product_handle_code", productHandleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductHandleCode_SpanTerm(String productHandleCode) {
        setProductHandleCode_SpanTerm("product_handle_code", null);
    }

    public void setProductHandleCode_SpanTerm(String productHandleCode, ConditionOptionCall<SpanTermQueryBuilder> opLambda) {
        SpanTermQueryBuilder builder = regSpanTermQ("product_handle_code", productHandleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }
    public void setProductHandleCode_GreaterThan(String productHandleCode) {
        setProductHandleCode_GreaterThan(productHandleCode, null);
    }

    public void setProductHandleCode_GreaterThan(String productHandleCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productHandleCode;
        RangeQueryBuilder builder = regRangeQ("product_handle_code", ConditionKey.CK_GREATER_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductHandleCode_LessThan(String productHandleCode) {
        setProductHandleCode_LessThan(productHandleCode, null);
    }

    public void setProductHandleCode_LessThan(String productHandleCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productHandleCode;
        RangeQueryBuilder builder = regRangeQ("product_handle_code", ConditionKey.CK_LESS_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductHandleCode_GreaterEqual(String productHandleCode) {
        setProductHandleCode_GreaterEqual(productHandleCode, null);
    }

    public void setProductHandleCode_GreaterEqual(String productHandleCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productHandleCode;
        RangeQueryBuilder builder = regRangeQ("product_handle_code", ConditionKey.CK_GREATER_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductHandleCode_LessEqual(String productHandleCode) {
        setProductHandleCode_LessEqual(productHandleCode, null);
    }

    public void setProductHandleCode_LessEqual(String productHandleCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productHandleCode;
        RangeQueryBuilder builder = regRangeQ("product_handle_code", ConditionKey.CK_LESS_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductHandleCode_Exists() {
        setProductHandleCode_Exists(null);
    }

    public void setProductHandleCode_Exists(ConditionOptionCall<ExistsQueryBuilder> opLambda) {
        ExistsQueryBuilder builder = regExistsQ("product_handle_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductHandleCode_CommonTerms(String productHandleCode) {
        setProductHandleCode_CommonTerms(productHandleCode, null);
    }

    public void setProductHandleCode_CommonTerms(String productHandleCode, ConditionOptionCall<CommonTermsQueryBuilder> opLambda) {
        CommonTermsQueryBuilder builder = regCommonTermsQ("product_handle_code", productHandleCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_ProductHandleCode_Asc() {
        regOBA("product_handle_code");
        return this;
    }

    public BsProductCQ addOrderBy_ProductHandleCode_Desc() {
        regOBD("product_handle_code");
        return this;
    }


    public void setProductName_Equal(String productName) {
        setProductName_Term(productName, null);
    }

    public void setProductName_Equal(String productName, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setProductName_Term(productName, opLambda);
    }

    public void setProductName_Term(String productName) {
        setProductName_Term(productName, null);
    }

    public void setProductName_Term(String productName, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("product_name", productName);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductName_NotEqual(String productName) {
        setProductName_NotTerm(productName, null);
    }

    public void setProductName_NotTerm(String productName) {
        setProductName_NotTerm(productName, null);
    }

    public void setProductName_NotEqual(String productName, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        setProductName_NotTerm(productName, opLambda);
    }

    public void setProductName_NotTerm(String productName, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        not(not -> not.setProductName_Term(productName), opLambda);
    }

    public void setProductName_Terms(Collection<String> productNameList) {
        setProductName_Terms(productNameList, null);
    }

    public void setProductName_Terms(Collection<String> productNameList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("product_name", productNameList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductName_InScope(Collection<String> productNameList) {
        setProductName_Terms(productNameList, null);
    }

    public void setProductName_InScope(Collection<String> productNameList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setProductName_Terms(productNameList, opLambda);
    }

    public void setProductName_Match(String productName) {
        setProductName_Match(productName, null);
    }

    public void setProductName_Match(String productName, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("product_name", productName);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductName_MatchPhrase(String productName) {
        setProductName_MatchPhrase(productName, null);
    }

    public void setProductName_MatchPhrase(String productName, ConditionOptionCall<MatchPhraseQueryBuilder> opLambda) {
        MatchPhraseQueryBuilder builder = regMatchPhraseQ("product_name", productName);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductName_MatchPhrasePrefix(String productName) {
        setProductName_MatchPhrasePrefix(productName, null);
    }

    public void setProductName_MatchPhrasePrefix(String productName, ConditionOptionCall<MatchPhrasePrefixQueryBuilder> opLambda) {
        MatchPhrasePrefixQueryBuilder builder = regMatchPhrasePrefixQ("product_name", productName);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductName_Fuzzy(String productName) {
        setProductName_Fuzzy(productName, null);
    }

    public void setProductName_Fuzzy(String productName, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regFuzzyQ("product_name", productName);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductName_Prefix(String productName) {
        setProductName_Prefix(productName, null);
    }

    public void setProductName_Prefix(String productName, ConditionOptionCall<PrefixQueryBuilder> opLambda) {
        PrefixQueryBuilder builder = regPrefixQ("product_name", productName);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductName_Wildcard(String productName) {
        setProductName_Wildcard(productName, null);
    }

    public void setProductName_Wildcard(String productName, ConditionOptionCall<WildcardQueryBuilder> opLambda) {
        WildcardQueryBuilder builder = regWildcardQ("product_name", productName);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductName_Regexp(String productName) {
        setProductName_Regexp(productName, null);
    }

    public void setProductName_Regexp(String productName, ConditionOptionCall<RegexpQueryBuilder> opLambda) {
        RegexpQueryBuilder builder = regRegexpQ("product_name", productName);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductName_SpanTerm(String productName) {
        setProductName_SpanTerm("product_name", null);
    }

    public void setProductName_SpanTerm(String productName, ConditionOptionCall<SpanTermQueryBuilder> opLambda) {
        SpanTermQueryBuilder builder = regSpanTermQ("product_name", productName);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }
    public void setProductName_GreaterThan(String productName) {
        setProductName_GreaterThan(productName, null);
    }

    public void setProductName_GreaterThan(String productName, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productName;
        RangeQueryBuilder builder = regRangeQ("product_name", ConditionKey.CK_GREATER_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductName_LessThan(String productName) {
        setProductName_LessThan(productName, null);
    }

    public void setProductName_LessThan(String productName, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productName;
        RangeQueryBuilder builder = regRangeQ("product_name", ConditionKey.CK_LESS_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductName_GreaterEqual(String productName) {
        setProductName_GreaterEqual(productName, null);
    }

    public void setProductName_GreaterEqual(String productName, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productName;
        RangeQueryBuilder builder = regRangeQ("product_name", ConditionKey.CK_GREATER_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductName_LessEqual(String productName) {
        setProductName_LessEqual(productName, null);
    }

    public void setProductName_LessEqual(String productName, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productName;
        RangeQueryBuilder builder = regRangeQ("product_name", ConditionKey.CK_LESS_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductName_Exists() {
        setProductName_Exists(null);
    }

    public void setProductName_Exists(ConditionOptionCall<ExistsQueryBuilder> opLambda) {
        ExistsQueryBuilder builder = regExistsQ("product_name");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductName_CommonTerms(String productName) {
        setProductName_CommonTerms(productName, null);
    }

    public void setProductName_CommonTerms(String productName, ConditionOptionCall<CommonTermsQueryBuilder> opLambda) {
        CommonTermsQueryBuilder builder = regCommonTermsQ("product_name", productName);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }


    public BsProductCQ addOrderBy_ProductName_raw_Asc() {
        regOBA("product_name.raw");
        return this;
    }

    public BsProductCQ addOrderBy_ProductName_raw_Desc() {
        regOBD("product_name.raw");
        return this;
    }

    public void setProductStatus_Equal(String productStatus) {
        setProductStatus_Term(productStatus, null);
    }

    public void setProductStatus_Equal(String productStatus, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setProductStatus_Term(productStatus, opLambda);
    }

    public void setProductStatus_Term(String productStatus) {
        setProductStatus_Term(productStatus, null);
    }

    public void setProductStatus_Term(String productStatus, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("product_status", productStatus);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatus_NotEqual(String productStatus) {
        setProductStatus_NotTerm(productStatus, null);
    }

    public void setProductStatus_NotTerm(String productStatus) {
        setProductStatus_NotTerm(productStatus, null);
    }

    public void setProductStatus_NotEqual(String productStatus, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        setProductStatus_NotTerm(productStatus, opLambda);
    }

    public void setProductStatus_NotTerm(String productStatus, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        not(not -> not.setProductStatus_Term(productStatus), opLambda);
    }

    public void setProductStatus_Terms(Collection<String> productStatusList) {
        setProductStatus_Terms(productStatusList, null);
    }

    public void setProductStatus_Terms(Collection<String> productStatusList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("product_status", productStatusList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatus_InScope(Collection<String> productStatusList) {
        setProductStatus_Terms(productStatusList, null);
    }

    public void setProductStatus_InScope(Collection<String> productStatusList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setProductStatus_Terms(productStatusList, opLambda);
    }

    public void setProductStatus_Match(String productStatus) {
        setProductStatus_Match(productStatus, null);
    }

    public void setProductStatus_Match(String productStatus, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("product_status", productStatus);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatus_MatchPhrase(String productStatus) {
        setProductStatus_MatchPhrase(productStatus, null);
    }

    public void setProductStatus_MatchPhrase(String productStatus, ConditionOptionCall<MatchPhraseQueryBuilder> opLambda) {
        MatchPhraseQueryBuilder builder = regMatchPhraseQ("product_status", productStatus);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatus_MatchPhrasePrefix(String productStatus) {
        setProductStatus_MatchPhrasePrefix(productStatus, null);
    }

    public void setProductStatus_MatchPhrasePrefix(String productStatus, ConditionOptionCall<MatchPhrasePrefixQueryBuilder> opLambda) {
        MatchPhrasePrefixQueryBuilder builder = regMatchPhrasePrefixQ("product_status", productStatus);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatus_Fuzzy(String productStatus) {
        setProductStatus_Fuzzy(productStatus, null);
    }

    public void setProductStatus_Fuzzy(String productStatus, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regFuzzyQ("product_status", productStatus);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatus_Prefix(String productStatus) {
        setProductStatus_Prefix(productStatus, null);
    }

    public void setProductStatus_Prefix(String productStatus, ConditionOptionCall<PrefixQueryBuilder> opLambda) {
        PrefixQueryBuilder builder = regPrefixQ("product_status", productStatus);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatus_Wildcard(String productStatus) {
        setProductStatus_Wildcard(productStatus, null);
    }

    public void setProductStatus_Wildcard(String productStatus, ConditionOptionCall<WildcardQueryBuilder> opLambda) {
        WildcardQueryBuilder builder = regWildcardQ("product_status", productStatus);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatus_Regexp(String productStatus) {
        setProductStatus_Regexp(productStatus, null);
    }

    public void setProductStatus_Regexp(String productStatus, ConditionOptionCall<RegexpQueryBuilder> opLambda) {
        RegexpQueryBuilder builder = regRegexpQ("product_status", productStatus);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatus_SpanTerm(String productStatus) {
        setProductStatus_SpanTerm("product_status", null);
    }

    public void setProductStatus_SpanTerm(String productStatus, ConditionOptionCall<SpanTermQueryBuilder> opLambda) {
        SpanTermQueryBuilder builder = regSpanTermQ("product_status", productStatus);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }
    public void setProductStatus_GreaterThan(String productStatus) {
        setProductStatus_GreaterThan(productStatus, null);
    }

    public void setProductStatus_GreaterThan(String productStatus, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productStatus;
        RangeQueryBuilder builder = regRangeQ("product_status", ConditionKey.CK_GREATER_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatus_LessThan(String productStatus) {
        setProductStatus_LessThan(productStatus, null);
    }

    public void setProductStatus_LessThan(String productStatus, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productStatus;
        RangeQueryBuilder builder = regRangeQ("product_status", ConditionKey.CK_LESS_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatus_GreaterEqual(String productStatus) {
        setProductStatus_GreaterEqual(productStatus, null);
    }

    public void setProductStatus_GreaterEqual(String productStatus, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productStatus;
        RangeQueryBuilder builder = regRangeQ("product_status", ConditionKey.CK_GREATER_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatus_LessEqual(String productStatus) {
        setProductStatus_LessEqual(productStatus, null);
    }

    public void setProductStatus_LessEqual(String productStatus, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productStatus;
        RangeQueryBuilder builder = regRangeQ("product_status", ConditionKey.CK_LESS_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatus_Exists() {
        setProductStatus_Exists(null);
    }

    public void setProductStatus_Exists(ConditionOptionCall<ExistsQueryBuilder> opLambda) {
        ExistsQueryBuilder builder = regExistsQ("product_status");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatus_CommonTerms(String productStatus) {
        setProductStatus_CommonTerms(productStatus, null);
    }

    public void setProductStatus_CommonTerms(String productStatus, ConditionOptionCall<CommonTermsQueryBuilder> opLambda) {
        CommonTermsQueryBuilder builder = regCommonTermsQ("product_status", productStatus);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_ProductStatus_Asc() {
        regOBA("product_status");
        return this;
    }

    public BsProductCQ addOrderBy_ProductStatus_Desc() {
        regOBD("product_status");
        return this;
    }


    public void setProductStatusCode_Equal(String productStatusCode) {
        setProductStatusCode_Term(productStatusCode, null);
    }

    public void setProductStatusCode_Equal(String productStatusCode, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setProductStatusCode_Term(productStatusCode, opLambda);
    }

    public void setProductStatusCode_Term(String productStatusCode) {
        setProductStatusCode_Term(productStatusCode, null);
    }

    public void setProductStatusCode_Term(String productStatusCode, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("product_status_code", productStatusCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatusCode_NotEqual(String productStatusCode) {
        setProductStatusCode_NotTerm(productStatusCode, null);
    }

    public void setProductStatusCode_NotTerm(String productStatusCode) {
        setProductStatusCode_NotTerm(productStatusCode, null);
    }

    public void setProductStatusCode_NotEqual(String productStatusCode, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        setProductStatusCode_NotTerm(productStatusCode, opLambda);
    }

    public void setProductStatusCode_NotTerm(String productStatusCode, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        not(not -> not.setProductStatusCode_Term(productStatusCode), opLambda);
    }

    public void setProductStatusCode_Terms(Collection<String> productStatusCodeList) {
        setProductStatusCode_Terms(productStatusCodeList, null);
    }

    public void setProductStatusCode_Terms(Collection<String> productStatusCodeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("product_status_code", productStatusCodeList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatusCode_InScope(Collection<String> productStatusCodeList) {
        setProductStatusCode_Terms(productStatusCodeList, null);
    }

    public void setProductStatusCode_InScope(Collection<String> productStatusCodeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setProductStatusCode_Terms(productStatusCodeList, opLambda);
    }

    public void setProductStatusCode_Match(String productStatusCode) {
        setProductStatusCode_Match(productStatusCode, null);
    }

    public void setProductStatusCode_Match(String productStatusCode, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("product_status_code", productStatusCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatusCode_MatchPhrase(String productStatusCode) {
        setProductStatusCode_MatchPhrase(productStatusCode, null);
    }

    public void setProductStatusCode_MatchPhrase(String productStatusCode, ConditionOptionCall<MatchPhraseQueryBuilder> opLambda) {
        MatchPhraseQueryBuilder builder = regMatchPhraseQ("product_status_code", productStatusCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatusCode_MatchPhrasePrefix(String productStatusCode) {
        setProductStatusCode_MatchPhrasePrefix(productStatusCode, null);
    }

    public void setProductStatusCode_MatchPhrasePrefix(String productStatusCode, ConditionOptionCall<MatchPhrasePrefixQueryBuilder> opLambda) {
        MatchPhrasePrefixQueryBuilder builder = regMatchPhrasePrefixQ("product_status_code", productStatusCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatusCode_Fuzzy(String productStatusCode) {
        setProductStatusCode_Fuzzy(productStatusCode, null);
    }

    public void setProductStatusCode_Fuzzy(String productStatusCode, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regFuzzyQ("product_status_code", productStatusCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatusCode_Prefix(String productStatusCode) {
        setProductStatusCode_Prefix(productStatusCode, null);
    }

    public void setProductStatusCode_Prefix(String productStatusCode, ConditionOptionCall<PrefixQueryBuilder> opLambda) {
        PrefixQueryBuilder builder = regPrefixQ("product_status_code", productStatusCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatusCode_Wildcard(String productStatusCode) {
        setProductStatusCode_Wildcard(productStatusCode, null);
    }

    public void setProductStatusCode_Wildcard(String productStatusCode, ConditionOptionCall<WildcardQueryBuilder> opLambda) {
        WildcardQueryBuilder builder = regWildcardQ("product_status_code", productStatusCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatusCode_Regexp(String productStatusCode) {
        setProductStatusCode_Regexp(productStatusCode, null);
    }

    public void setProductStatusCode_Regexp(String productStatusCode, ConditionOptionCall<RegexpQueryBuilder> opLambda) {
        RegexpQueryBuilder builder = regRegexpQ("product_status_code", productStatusCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatusCode_SpanTerm(String productStatusCode) {
        setProductStatusCode_SpanTerm("product_status_code", null);
    }

    public void setProductStatusCode_SpanTerm(String productStatusCode, ConditionOptionCall<SpanTermQueryBuilder> opLambda) {
        SpanTermQueryBuilder builder = regSpanTermQ("product_status_code", productStatusCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }
    public void setProductStatusCode_GreaterThan(String productStatusCode) {
        setProductStatusCode_GreaterThan(productStatusCode, null);
    }

    public void setProductStatusCode_GreaterThan(String productStatusCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productStatusCode;
        RangeQueryBuilder builder = regRangeQ("product_status_code", ConditionKey.CK_GREATER_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatusCode_LessThan(String productStatusCode) {
        setProductStatusCode_LessThan(productStatusCode, null);
    }

    public void setProductStatusCode_LessThan(String productStatusCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productStatusCode;
        RangeQueryBuilder builder = regRangeQ("product_status_code", ConditionKey.CK_LESS_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatusCode_GreaterEqual(String productStatusCode) {
        setProductStatusCode_GreaterEqual(productStatusCode, null);
    }

    public void setProductStatusCode_GreaterEqual(String productStatusCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productStatusCode;
        RangeQueryBuilder builder = regRangeQ("product_status_code", ConditionKey.CK_GREATER_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatusCode_LessEqual(String productStatusCode) {
        setProductStatusCode_LessEqual(productStatusCode, null);
    }

    public void setProductStatusCode_LessEqual(String productStatusCode, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = productStatusCode;
        RangeQueryBuilder builder = regRangeQ("product_status_code", ConditionKey.CK_LESS_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatusCode_Exists() {
        setProductStatusCode_Exists(null);
    }

    public void setProductStatusCode_Exists(ConditionOptionCall<ExistsQueryBuilder> opLambda) {
        ExistsQueryBuilder builder = regExistsQ("product_status_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatusCode_CommonTerms(String productStatusCode) {
        setProductStatusCode_CommonTerms(productStatusCode, null);
    }

    public void setProductStatusCode_CommonTerms(String productStatusCode, ConditionOptionCall<CommonTermsQueryBuilder> opLambda) {
        CommonTermsQueryBuilder builder = regCommonTermsQ("product_status_code", productStatusCode);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_ProductStatusCode_Asc() {
        regOBA("product_status_code");
        return this;
    }

    public BsProductCQ addOrderBy_ProductStatusCode_Desc() {
        regOBD("product_status_code");
        return this;
    }


    public void setRegisterDatetime_Equal(LocalDateTime registerDatetime) {
        setRegisterDatetime_Term(registerDatetime, null);
    }

    public void setRegisterDatetime_Equal(LocalDateTime registerDatetime, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setRegisterDatetime_Term(registerDatetime, opLambda);
    }

    public void setRegisterDatetime_Term(LocalDateTime registerDatetime) {
        setRegisterDatetime_Term(registerDatetime, null);
    }

    public void setRegisterDatetime_Term(LocalDateTime registerDatetime, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("register_datetime", registerDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_NotEqual(LocalDateTime registerDatetime) {
        setRegisterDatetime_NotTerm(registerDatetime, null);
    }

    public void setRegisterDatetime_NotTerm(LocalDateTime registerDatetime) {
        setRegisterDatetime_NotTerm(registerDatetime, null);
    }

    public void setRegisterDatetime_NotEqual(LocalDateTime registerDatetime, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        setRegisterDatetime_NotTerm(registerDatetime, opLambda);
    }

    public void setRegisterDatetime_NotTerm(LocalDateTime registerDatetime, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        not(not -> not.setRegisterDatetime_Term(registerDatetime), opLambda);
    }

    public void setRegisterDatetime_Terms(Collection<LocalDateTime> registerDatetimeList) {
        setRegisterDatetime_Terms(registerDatetimeList, null);
    }

    public void setRegisterDatetime_Terms(Collection<LocalDateTime> registerDatetimeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("register_datetime", registerDatetimeList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_InScope(Collection<LocalDateTime> registerDatetimeList) {
        setRegisterDatetime_Terms(registerDatetimeList, null);
    }

    public void setRegisterDatetime_InScope(Collection<LocalDateTime> registerDatetimeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setRegisterDatetime_Terms(registerDatetimeList, opLambda);
    }

    public void setRegisterDatetime_Match(LocalDateTime registerDatetime) {
        setRegisterDatetime_Match(registerDatetime, null);
    }

    public void setRegisterDatetime_Match(LocalDateTime registerDatetime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("register_datetime", registerDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_MatchPhrase(LocalDateTime registerDatetime) {
        setRegisterDatetime_MatchPhrase(registerDatetime, null);
    }

    public void setRegisterDatetime_MatchPhrase(LocalDateTime registerDatetime, ConditionOptionCall<MatchPhraseQueryBuilder> opLambda) {
        MatchPhraseQueryBuilder builder = regMatchPhraseQ("register_datetime", registerDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_MatchPhrasePrefix(LocalDateTime registerDatetime) {
        setRegisterDatetime_MatchPhrasePrefix(registerDatetime, null);
    }

    public void setRegisterDatetime_MatchPhrasePrefix(LocalDateTime registerDatetime, ConditionOptionCall<MatchPhrasePrefixQueryBuilder> opLambda) {
        MatchPhrasePrefixQueryBuilder builder = regMatchPhrasePrefixQ("register_datetime", registerDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_Fuzzy(LocalDateTime registerDatetime) {
        setRegisterDatetime_Fuzzy(registerDatetime, null);
    }

    public void setRegisterDatetime_Fuzzy(LocalDateTime registerDatetime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regFuzzyQ("register_datetime", registerDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_GreaterThan(LocalDateTime registerDatetime) {
        setRegisterDatetime_GreaterThan(registerDatetime, null);
    }

    public void setRegisterDatetime_GreaterThan(LocalDateTime registerDatetime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = toRangeLocalDateTimeString(registerDatetime, "date_optional_time");
        RangeQueryBuilder builder = regRangeQ("register_datetime", ConditionKey.CK_GREATER_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_LessThan(LocalDateTime registerDatetime) {
        setRegisterDatetime_LessThan(registerDatetime, null);
    }

    public void setRegisterDatetime_LessThan(LocalDateTime registerDatetime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = toRangeLocalDateTimeString(registerDatetime, "date_optional_time");
        RangeQueryBuilder builder = regRangeQ("register_datetime", ConditionKey.CK_LESS_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_GreaterEqual(LocalDateTime registerDatetime) {
        setRegisterDatetime_GreaterEqual(registerDatetime, null);
    }

    public void setRegisterDatetime_GreaterEqual(LocalDateTime registerDatetime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = toRangeLocalDateTimeString(registerDatetime, "date_optional_time");
        RangeQueryBuilder builder = regRangeQ("register_datetime", ConditionKey.CK_GREATER_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_LessEqual(LocalDateTime registerDatetime) {
        setRegisterDatetime_LessEqual(registerDatetime, null);
    }

    public void setRegisterDatetime_LessEqual(LocalDateTime registerDatetime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = toRangeLocalDateTimeString(registerDatetime, "date_optional_time");
        RangeQueryBuilder builder = regRangeQ("register_datetime", ConditionKey.CK_LESS_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_Exists() {
        setRegisterDatetime_Exists(null);
    }

    public void setRegisterDatetime_Exists(ConditionOptionCall<ExistsQueryBuilder> opLambda) {
        ExistsQueryBuilder builder = regExistsQ("register_datetime");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_CommonTerms(LocalDateTime registerDatetime) {
        setRegisterDatetime_CommonTerms(registerDatetime, null);
    }

    public void setRegisterDatetime_CommonTerms(LocalDateTime registerDatetime, ConditionOptionCall<CommonTermsQueryBuilder> opLambda) {
        CommonTermsQueryBuilder builder = regCommonTermsQ("register_datetime", registerDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_RegisterDatetime_Asc() {
        regOBA("register_datetime");
        return this;
    }

    public BsProductCQ addOrderBy_RegisterDatetime_Desc() {
        regOBD("register_datetime");
        return this;
    }


    public void setRegisterUser_Equal(String registerUser) {
        setRegisterUser_Term(registerUser, null);
    }

    public void setRegisterUser_Equal(String registerUser, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setRegisterUser_Term(registerUser, opLambda);
    }

    public void setRegisterUser_Term(String registerUser) {
        setRegisterUser_Term(registerUser, null);
    }

    public void setRegisterUser_Term(String registerUser, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("register_user", registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_NotEqual(String registerUser) {
        setRegisterUser_NotTerm(registerUser, null);
    }

    public void setRegisterUser_NotTerm(String registerUser) {
        setRegisterUser_NotTerm(registerUser, null);
    }

    public void setRegisterUser_NotEqual(String registerUser, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        setRegisterUser_NotTerm(registerUser, opLambda);
    }

    public void setRegisterUser_NotTerm(String registerUser, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        not(not -> not.setRegisterUser_Term(registerUser), opLambda);
    }

    public void setRegisterUser_Terms(Collection<String> registerUserList) {
        setRegisterUser_Terms(registerUserList, null);
    }

    public void setRegisterUser_Terms(Collection<String> registerUserList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("register_user", registerUserList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_InScope(Collection<String> registerUserList) {
        setRegisterUser_Terms(registerUserList, null);
    }

    public void setRegisterUser_InScope(Collection<String> registerUserList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setRegisterUser_Terms(registerUserList, opLambda);
    }

    public void setRegisterUser_Match(String registerUser) {
        setRegisterUser_Match(registerUser, null);
    }

    public void setRegisterUser_Match(String registerUser, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("register_user", registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_MatchPhrase(String registerUser) {
        setRegisterUser_MatchPhrase(registerUser, null);
    }

    public void setRegisterUser_MatchPhrase(String registerUser, ConditionOptionCall<MatchPhraseQueryBuilder> opLambda) {
        MatchPhraseQueryBuilder builder = regMatchPhraseQ("register_user", registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_MatchPhrasePrefix(String registerUser) {
        setRegisterUser_MatchPhrasePrefix(registerUser, null);
    }

    public void setRegisterUser_MatchPhrasePrefix(String registerUser, ConditionOptionCall<MatchPhrasePrefixQueryBuilder> opLambda) {
        MatchPhrasePrefixQueryBuilder builder = regMatchPhrasePrefixQ("register_user", registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_Fuzzy(String registerUser) {
        setRegisterUser_Fuzzy(registerUser, null);
    }

    public void setRegisterUser_Fuzzy(String registerUser, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regFuzzyQ("register_user", registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_Prefix(String registerUser) {
        setRegisterUser_Prefix(registerUser, null);
    }

    public void setRegisterUser_Prefix(String registerUser, ConditionOptionCall<PrefixQueryBuilder> opLambda) {
        PrefixQueryBuilder builder = regPrefixQ("register_user", registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_Wildcard(String registerUser) {
        setRegisterUser_Wildcard(registerUser, null);
    }

    public void setRegisterUser_Wildcard(String registerUser, ConditionOptionCall<WildcardQueryBuilder> opLambda) {
        WildcardQueryBuilder builder = regWildcardQ("register_user", registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_Regexp(String registerUser) {
        setRegisterUser_Regexp(registerUser, null);
    }

    public void setRegisterUser_Regexp(String registerUser, ConditionOptionCall<RegexpQueryBuilder> opLambda) {
        RegexpQueryBuilder builder = regRegexpQ("register_user", registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_SpanTerm(String registerUser) {
        setRegisterUser_SpanTerm("register_user", null);
    }

    public void setRegisterUser_SpanTerm(String registerUser, ConditionOptionCall<SpanTermQueryBuilder> opLambda) {
        SpanTermQueryBuilder builder = regSpanTermQ("register_user", registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }
    public void setRegisterUser_GreaterThan(String registerUser) {
        setRegisterUser_GreaterThan(registerUser, null);
    }

    public void setRegisterUser_GreaterThan(String registerUser, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = registerUser;
        RangeQueryBuilder builder = regRangeQ("register_user", ConditionKey.CK_GREATER_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_LessThan(String registerUser) {
        setRegisterUser_LessThan(registerUser, null);
    }

    public void setRegisterUser_LessThan(String registerUser, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = registerUser;
        RangeQueryBuilder builder = regRangeQ("register_user", ConditionKey.CK_LESS_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_GreaterEqual(String registerUser) {
        setRegisterUser_GreaterEqual(registerUser, null);
    }

    public void setRegisterUser_GreaterEqual(String registerUser, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = registerUser;
        RangeQueryBuilder builder = regRangeQ("register_user", ConditionKey.CK_GREATER_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_LessEqual(String registerUser) {
        setRegisterUser_LessEqual(registerUser, null);
    }

    public void setRegisterUser_LessEqual(String registerUser, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = registerUser;
        RangeQueryBuilder builder = regRangeQ("register_user", ConditionKey.CK_LESS_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_Exists() {
        setRegisterUser_Exists(null);
    }

    public void setRegisterUser_Exists(ConditionOptionCall<ExistsQueryBuilder> opLambda) {
        ExistsQueryBuilder builder = regExistsQ("register_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_CommonTerms(String registerUser) {
        setRegisterUser_CommonTerms(registerUser, null);
    }

    public void setRegisterUser_CommonTerms(String registerUser, ConditionOptionCall<CommonTermsQueryBuilder> opLambda) {
        CommonTermsQueryBuilder builder = regCommonTermsQ("register_user", registerUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_RegisterUser_Asc() {
        regOBA("register_user");
        return this;
    }

    public BsProductCQ addOrderBy_RegisterUser_Desc() {
        regOBD("register_user");
        return this;
    }


    public void setRegularPrice_Equal(Integer regularPrice) {
        setRegularPrice_Term(regularPrice, null);
    }

    public void setRegularPrice_Equal(Integer regularPrice, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setRegularPrice_Term(regularPrice, opLambda);
    }

    public void setRegularPrice_Term(Integer regularPrice) {
        setRegularPrice_Term(regularPrice, null);
    }

    public void setRegularPrice_Term(Integer regularPrice, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("regular_price", regularPrice);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_NotEqual(Integer regularPrice) {
        setRegularPrice_NotTerm(regularPrice, null);
    }

    public void setRegularPrice_NotTerm(Integer regularPrice) {
        setRegularPrice_NotTerm(regularPrice, null);
    }

    public void setRegularPrice_NotEqual(Integer regularPrice, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        setRegularPrice_NotTerm(regularPrice, opLambda);
    }

    public void setRegularPrice_NotTerm(Integer regularPrice, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        not(not -> not.setRegularPrice_Term(regularPrice), opLambda);
    }

    public void setRegularPrice_Terms(Collection<Integer> regularPriceList) {
        setRegularPrice_Terms(regularPriceList, null);
    }

    public void setRegularPrice_Terms(Collection<Integer> regularPriceList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("regular_price", regularPriceList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_InScope(Collection<Integer> regularPriceList) {
        setRegularPrice_Terms(regularPriceList, null);
    }

    public void setRegularPrice_InScope(Collection<Integer> regularPriceList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setRegularPrice_Terms(regularPriceList, opLambda);
    }

    public void setRegularPrice_Match(Integer regularPrice) {
        setRegularPrice_Match(regularPrice, null);
    }

    public void setRegularPrice_Match(Integer regularPrice, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("regular_price", regularPrice);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_MatchPhrase(Integer regularPrice) {
        setRegularPrice_MatchPhrase(regularPrice, null);
    }

    public void setRegularPrice_MatchPhrase(Integer regularPrice, ConditionOptionCall<MatchPhraseQueryBuilder> opLambda) {
        MatchPhraseQueryBuilder builder = regMatchPhraseQ("regular_price", regularPrice);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_MatchPhrasePrefix(Integer regularPrice) {
        setRegularPrice_MatchPhrasePrefix(regularPrice, null);
    }

    public void setRegularPrice_MatchPhrasePrefix(Integer regularPrice, ConditionOptionCall<MatchPhrasePrefixQueryBuilder> opLambda) {
        MatchPhrasePrefixQueryBuilder builder = regMatchPhrasePrefixQ("regular_price", regularPrice);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_Fuzzy(Integer regularPrice) {
        setRegularPrice_Fuzzy(regularPrice, null);
    }

    public void setRegularPrice_Fuzzy(Integer regularPrice, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regFuzzyQ("regular_price", regularPrice);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_GreaterThan(Integer regularPrice) {
        setRegularPrice_GreaterThan(regularPrice, null);
    }

    public void setRegularPrice_GreaterThan(Integer regularPrice, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = regularPrice;
        RangeQueryBuilder builder = regRangeQ("regular_price", ConditionKey.CK_GREATER_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_LessThan(Integer regularPrice) {
        setRegularPrice_LessThan(regularPrice, null);
    }

    public void setRegularPrice_LessThan(Integer regularPrice, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = regularPrice;
        RangeQueryBuilder builder = regRangeQ("regular_price", ConditionKey.CK_LESS_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_GreaterEqual(Integer regularPrice) {
        setRegularPrice_GreaterEqual(regularPrice, null);
    }

    public void setRegularPrice_GreaterEqual(Integer regularPrice, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = regularPrice;
        RangeQueryBuilder builder = regRangeQ("regular_price", ConditionKey.CK_GREATER_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_LessEqual(Integer regularPrice) {
        setRegularPrice_LessEqual(regularPrice, null);
    }

    public void setRegularPrice_LessEqual(Integer regularPrice, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = regularPrice;
        RangeQueryBuilder builder = regRangeQ("regular_price", ConditionKey.CK_LESS_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_Exists() {
        setRegularPrice_Exists(null);
    }

    public void setRegularPrice_Exists(ConditionOptionCall<ExistsQueryBuilder> opLambda) {
        ExistsQueryBuilder builder = regExistsQ("regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_CommonTerms(Integer regularPrice) {
        setRegularPrice_CommonTerms(regularPrice, null);
    }

    public void setRegularPrice_CommonTerms(Integer regularPrice, ConditionOptionCall<CommonTermsQueryBuilder> opLambda) {
        CommonTermsQueryBuilder builder = regCommonTermsQ("regular_price", regularPrice);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_RegularPrice_Asc() {
        regOBA("regular_price");
        return this;
    }

    public BsProductCQ addOrderBy_RegularPrice_Desc() {
        regOBD("regular_price");
        return this;
    }


    public void setUpdateDatetime_Equal(LocalDateTime updateDatetime) {
        setUpdateDatetime_Term(updateDatetime, null);
    }

    public void setUpdateDatetime_Equal(LocalDateTime updateDatetime, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setUpdateDatetime_Term(updateDatetime, opLambda);
    }

    public void setUpdateDatetime_Term(LocalDateTime updateDatetime) {
        setUpdateDatetime_Term(updateDatetime, null);
    }

    public void setUpdateDatetime_Term(LocalDateTime updateDatetime, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("update_datetime", updateDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_NotEqual(LocalDateTime updateDatetime) {
        setUpdateDatetime_NotTerm(updateDatetime, null);
    }

    public void setUpdateDatetime_NotTerm(LocalDateTime updateDatetime) {
        setUpdateDatetime_NotTerm(updateDatetime, null);
    }

    public void setUpdateDatetime_NotEqual(LocalDateTime updateDatetime, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        setUpdateDatetime_NotTerm(updateDatetime, opLambda);
    }

    public void setUpdateDatetime_NotTerm(LocalDateTime updateDatetime, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        not(not -> not.setUpdateDatetime_Term(updateDatetime), opLambda);
    }

    public void setUpdateDatetime_Terms(Collection<LocalDateTime> updateDatetimeList) {
        setUpdateDatetime_Terms(updateDatetimeList, null);
    }

    public void setUpdateDatetime_Terms(Collection<LocalDateTime> updateDatetimeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("update_datetime", updateDatetimeList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_InScope(Collection<LocalDateTime> updateDatetimeList) {
        setUpdateDatetime_Terms(updateDatetimeList, null);
    }

    public void setUpdateDatetime_InScope(Collection<LocalDateTime> updateDatetimeList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setUpdateDatetime_Terms(updateDatetimeList, opLambda);
    }

    public void setUpdateDatetime_Match(LocalDateTime updateDatetime) {
        setUpdateDatetime_Match(updateDatetime, null);
    }

    public void setUpdateDatetime_Match(LocalDateTime updateDatetime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("update_datetime", updateDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_MatchPhrase(LocalDateTime updateDatetime) {
        setUpdateDatetime_MatchPhrase(updateDatetime, null);
    }

    public void setUpdateDatetime_MatchPhrase(LocalDateTime updateDatetime, ConditionOptionCall<MatchPhraseQueryBuilder> opLambda) {
        MatchPhraseQueryBuilder builder = regMatchPhraseQ("update_datetime", updateDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_MatchPhrasePrefix(LocalDateTime updateDatetime) {
        setUpdateDatetime_MatchPhrasePrefix(updateDatetime, null);
    }

    public void setUpdateDatetime_MatchPhrasePrefix(LocalDateTime updateDatetime, ConditionOptionCall<MatchPhrasePrefixQueryBuilder> opLambda) {
        MatchPhrasePrefixQueryBuilder builder = regMatchPhrasePrefixQ("update_datetime", updateDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_Fuzzy(LocalDateTime updateDatetime) {
        setUpdateDatetime_Fuzzy(updateDatetime, null);
    }

    public void setUpdateDatetime_Fuzzy(LocalDateTime updateDatetime, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regFuzzyQ("update_datetime", updateDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_GreaterThan(LocalDateTime updateDatetime) {
        setUpdateDatetime_GreaterThan(updateDatetime, null);
    }

    public void setUpdateDatetime_GreaterThan(LocalDateTime updateDatetime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = toRangeLocalDateTimeString(updateDatetime, "date_optional_time");
        RangeQueryBuilder builder = regRangeQ("update_datetime", ConditionKey.CK_GREATER_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_LessThan(LocalDateTime updateDatetime) {
        setUpdateDatetime_LessThan(updateDatetime, null);
    }

    public void setUpdateDatetime_LessThan(LocalDateTime updateDatetime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = toRangeLocalDateTimeString(updateDatetime, "date_optional_time");
        RangeQueryBuilder builder = regRangeQ("update_datetime", ConditionKey.CK_LESS_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_GreaterEqual(LocalDateTime updateDatetime) {
        setUpdateDatetime_GreaterEqual(updateDatetime, null);
    }

    public void setUpdateDatetime_GreaterEqual(LocalDateTime updateDatetime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = toRangeLocalDateTimeString(updateDatetime, "date_optional_time");
        RangeQueryBuilder builder = regRangeQ("update_datetime", ConditionKey.CK_GREATER_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_LessEqual(LocalDateTime updateDatetime) {
        setUpdateDatetime_LessEqual(updateDatetime, null);
    }

    public void setUpdateDatetime_LessEqual(LocalDateTime updateDatetime, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = toRangeLocalDateTimeString(updateDatetime, "date_optional_time");
        RangeQueryBuilder builder = regRangeQ("update_datetime", ConditionKey.CK_LESS_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_Exists() {
        setUpdateDatetime_Exists(null);
    }

    public void setUpdateDatetime_Exists(ConditionOptionCall<ExistsQueryBuilder> opLambda) {
        ExistsQueryBuilder builder = regExistsQ("update_datetime");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_CommonTerms(LocalDateTime updateDatetime) {
        setUpdateDatetime_CommonTerms(updateDatetime, null);
    }

    public void setUpdateDatetime_CommonTerms(LocalDateTime updateDatetime, ConditionOptionCall<CommonTermsQueryBuilder> opLambda) {
        CommonTermsQueryBuilder builder = regCommonTermsQ("update_datetime", updateDatetime);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_UpdateDatetime_Asc() {
        regOBA("update_datetime");
        return this;
    }

    public BsProductCQ addOrderBy_UpdateDatetime_Desc() {
        regOBD("update_datetime");
        return this;
    }


    public void setUpdateUser_Equal(String updateUser) {
        setUpdateUser_Term(updateUser, null);
    }

    public void setUpdateUser_Equal(String updateUser, ConditionOptionCall<TermQueryBuilder> opLambda) {
        setUpdateUser_Term(updateUser, opLambda);
    }

    public void setUpdateUser_Term(String updateUser) {
        setUpdateUser_Term(updateUser, null);
    }

    public void setUpdateUser_Term(String updateUser, ConditionOptionCall<TermQueryBuilder> opLambda) {
        TermQueryBuilder builder = regTermQ("update_user", updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_NotEqual(String updateUser) {
        setUpdateUser_NotTerm(updateUser, null);
    }

    public void setUpdateUser_NotTerm(String updateUser) {
        setUpdateUser_NotTerm(updateUser, null);
    }

    public void setUpdateUser_NotEqual(String updateUser, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        setUpdateUser_NotTerm(updateUser, opLambda);
    }

    public void setUpdateUser_NotTerm(String updateUser, ConditionOptionCall<BoolQueryBuilder> opLambda) {
        not(not -> not.setUpdateUser_Term(updateUser), opLambda);
    }

    public void setUpdateUser_Terms(Collection<String> updateUserList) {
        setUpdateUser_Terms(updateUserList, null);
    }

    public void setUpdateUser_Terms(Collection<String> updateUserList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        TermsQueryBuilder builder = regTermsQ("update_user", updateUserList);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_InScope(Collection<String> updateUserList) {
        setUpdateUser_Terms(updateUserList, null);
    }

    public void setUpdateUser_InScope(Collection<String> updateUserList, ConditionOptionCall<TermsQueryBuilder> opLambda) {
        setUpdateUser_Terms(updateUserList, opLambda);
    }

    public void setUpdateUser_Match(String updateUser) {
        setUpdateUser_Match(updateUser, null);
    }

    public void setUpdateUser_Match(String updateUser, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regMatchQ("update_user", updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_MatchPhrase(String updateUser) {
        setUpdateUser_MatchPhrase(updateUser, null);
    }

    public void setUpdateUser_MatchPhrase(String updateUser, ConditionOptionCall<MatchPhraseQueryBuilder> opLambda) {
        MatchPhraseQueryBuilder builder = regMatchPhraseQ("update_user", updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_MatchPhrasePrefix(String updateUser) {
        setUpdateUser_MatchPhrasePrefix(updateUser, null);
    }

    public void setUpdateUser_MatchPhrasePrefix(String updateUser, ConditionOptionCall<MatchPhrasePrefixQueryBuilder> opLambda) {
        MatchPhrasePrefixQueryBuilder builder = regMatchPhrasePrefixQ("update_user", updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_Fuzzy(String updateUser) {
        setUpdateUser_Fuzzy(updateUser, null);
    }

    public void setUpdateUser_Fuzzy(String updateUser, ConditionOptionCall<MatchQueryBuilder> opLambda) {
        MatchQueryBuilder builder = regFuzzyQ("update_user", updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_Prefix(String updateUser) {
        setUpdateUser_Prefix(updateUser, null);
    }

    public void setUpdateUser_Prefix(String updateUser, ConditionOptionCall<PrefixQueryBuilder> opLambda) {
        PrefixQueryBuilder builder = regPrefixQ("update_user", updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_Wildcard(String updateUser) {
        setUpdateUser_Wildcard(updateUser, null);
    }

    public void setUpdateUser_Wildcard(String updateUser, ConditionOptionCall<WildcardQueryBuilder> opLambda) {
        WildcardQueryBuilder builder = regWildcardQ("update_user", updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_Regexp(String updateUser) {
        setUpdateUser_Regexp(updateUser, null);
    }

    public void setUpdateUser_Regexp(String updateUser, ConditionOptionCall<RegexpQueryBuilder> opLambda) {
        RegexpQueryBuilder builder = regRegexpQ("update_user", updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_SpanTerm(String updateUser) {
        setUpdateUser_SpanTerm("update_user", null);
    }

    public void setUpdateUser_SpanTerm(String updateUser, ConditionOptionCall<SpanTermQueryBuilder> opLambda) {
        SpanTermQueryBuilder builder = regSpanTermQ("update_user", updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }
    public void setUpdateUser_GreaterThan(String updateUser) {
        setUpdateUser_GreaterThan(updateUser, null);
    }

    public void setUpdateUser_GreaterThan(String updateUser, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = updateUser;
        RangeQueryBuilder builder = regRangeQ("update_user", ConditionKey.CK_GREATER_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_LessThan(String updateUser) {
        setUpdateUser_LessThan(updateUser, null);
    }

    public void setUpdateUser_LessThan(String updateUser, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = updateUser;
        RangeQueryBuilder builder = regRangeQ("update_user", ConditionKey.CK_LESS_THAN, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_GreaterEqual(String updateUser) {
        setUpdateUser_GreaterEqual(updateUser, null);
    }

    public void setUpdateUser_GreaterEqual(String updateUser, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = updateUser;
        RangeQueryBuilder builder = regRangeQ("update_user", ConditionKey.CK_GREATER_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_LessEqual(String updateUser) {
        setUpdateUser_LessEqual(updateUser, null);
    }

    public void setUpdateUser_LessEqual(String updateUser, ConditionOptionCall<RangeQueryBuilder> opLambda) {
        final Object _value = updateUser;
        RangeQueryBuilder builder = regRangeQ("update_user", ConditionKey.CK_LESS_EQUAL, _value);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_Exists() {
        setUpdateUser_Exists(null);
    }

    public void setUpdateUser_Exists(ConditionOptionCall<ExistsQueryBuilder> opLambda) {
        ExistsQueryBuilder builder = regExistsQ("update_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_CommonTerms(String updateUser) {
        setUpdateUser_CommonTerms(updateUser, null);
    }

    public void setUpdateUser_CommonTerms(String updateUser, ConditionOptionCall<CommonTermsQueryBuilder> opLambda) {
        CommonTermsQueryBuilder builder = regCommonTermsQ("update_user", updateUser);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public BsProductCQ addOrderBy_UpdateUser_Asc() {
        regOBA("update_user");
        return this;
    }

    public BsProductCQ addOrderBy_UpdateUser_Desc() {
        regOBD("update_user");
        return this;
    }


}
