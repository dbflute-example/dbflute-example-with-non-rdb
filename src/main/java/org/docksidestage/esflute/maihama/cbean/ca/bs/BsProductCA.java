/*
 * Copyright 2015-2022 the original author or authors.
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
package org.docksidestage.esflute.maihama.cbean.ca.bs;

import java.util.List;

import org.docksidestage.esflute.maihama.allcommon.EsAbstractConditionAggregation;
import org.docksidestage.esflute.maihama.allcommon.EsAbstractConditionQuery;
import org.docksidestage.esflute.maihama.cbean.ca.ProductCA;
import org.docksidestage.esflute.maihama.cbean.cq.ProductCQ;
import org.docksidestage.esflute.maihama.cbean.cq.bs.BsProductCQ;

import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.global.GlobalAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.HistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.missing.MissingAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.DateRangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.IpRangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.sampler.SamplerAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.significant.SignificantTermsAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.percentiles.PercentileRanksAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.percentiles.PercentilesAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.scripted.ScriptedMetricAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.stats.StatsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStatsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;

/**
 * @author ESFlute (using FreeGen)
 */
public abstract class BsProductCA extends EsAbstractConditionAggregation {

    // ===================================================================================
    //                                                                     Aggregation Set
    //                                                                           =========

    public void filter(String name, EsAbstractConditionQuery.OperatorCall<BsProductCQ> queryLambda,
            ConditionOptionCall<FilterAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        ProductCQ cq = new ProductCQ();
        if (queryLambda != null) {
            queryLambda.callback(cq);
        }
        FilterAggregationBuilder builder = regFilterA(name, cq.getQuery());
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void global(String name, ConditionOptionCall<GlobalAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        GlobalAggregationBuilder builder = regGlobalA(name);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void sampler(String name, ConditionOptionCall<SamplerAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        SamplerAggregationBuilder builder = regSamplerA(name);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void scriptedMetric(String name, ConditionOptionCall<ScriptedMetricAggregationBuilder> opLambda) {
        ScriptedMetricAggregationBuilder builder = regScriptedMetricA(name);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void topHits(String name, ConditionOptionCall<TopHitsAggregationBuilder> opLambda) {
        TopHitsAggregationBuilder builder = regTopHitsA(name);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }



    public void setLatestPurchaseDate_DateRange() {
        setLatestPurchaseDate_DateRange(null);
    }

    public void setLatestPurchaseDate_DateRange(ConditionOptionCall<DateRangeAggregationBuilder> opLambda) {
        setLatestPurchaseDate_DateRange("latest_purchase_date", opLambda, null);
    }

    public void setLatestPurchaseDate_DateRange(ConditionOptionCall<DateRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setLatestPurchaseDate_DateRange("latest_purchase_date", opLambda, aggsLambda);
    }

    public void setLatestPurchaseDate_DateRange(String name, ConditionOptionCall<DateRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        DateRangeAggregationBuilder builder = regDateRangeA(name, "latest_purchase_date");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setLatestPurchaseDate_DateHistogram() {
        setLatestPurchaseDate_DateHistogram(null);
    }

    public void setLatestPurchaseDate_DateHistogram(ConditionOptionCall<DateHistogramAggregationBuilder> opLambda) {
        setLatestPurchaseDate_DateHistogram("latest_purchase_date", opLambda, null);
    }

    public void setLatestPurchaseDate_DateHistogram(ConditionOptionCall<DateHistogramAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setLatestPurchaseDate_DateHistogram("latest_purchase_date", opLambda, aggsLambda);
    }

    public void setLatestPurchaseDate_DateHistogram(String name, ConditionOptionCall<DateHistogramAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        DateHistogramAggregationBuilder builder = regDateHistogramA(name, "latest_purchase_date");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setLatestPurchaseDate_Count() {
        setLatestPurchaseDate_Count(null);
    }

    public void setLatestPurchaseDate_Count(ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        setLatestPurchaseDate_Count("latest_purchase_date", opLambda);
    }

    public void setLatestPurchaseDate_Count(String name, ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        ValueCountAggregationBuilder builder = regCountA(name, "latest_purchase_date");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setLatestPurchaseDate_Cardinality() {
        setLatestPurchaseDate_Cardinality(null);
    }

    public void setLatestPurchaseDate_Cardinality(ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        setLatestPurchaseDate_Cardinality("latest_purchase_date", opLambda);
    }

    public void setLatestPurchaseDate_Cardinality(String name, ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        CardinalityAggregationBuilder builder = regCardinalityA(name, "latest_purchase_date");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setLatestPurchaseDate_Missing() {
        setLatestPurchaseDate_Missing(null);
    }

    public void setLatestPurchaseDate_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda) {
        setLatestPurchaseDate_Missing("latest_purchase_date", opLambda, null);
    }

    public void setLatestPurchaseDate_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setLatestPurchaseDate_Missing("latest_purchase_date", opLambda, aggsLambda);
    }

    public void setLatestPurchaseDate_Missing(String name, ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingAggregationBuilder builder = regMissingA(name, "latest_purchase_date");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }


    public void setProductCategory_Terms() {
        setProductCategory_Terms(null);
    }

    public void setProductCategory_Terms(ConditionOptionCall<TermsAggregationBuilder> opLambda) {
        setProductCategory_Terms("product_category", opLambda, null);
    }

    public void setProductCategory_Terms(ConditionOptionCall<TermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductCategory_Terms("product_category", opLambda, aggsLambda);
    }

    public void setProductCategory_Terms(String name, ConditionOptionCall<TermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        TermsAggregationBuilder builder = regTermsA(name, "product_category");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setProductCategory_SignificantTerms() {
        setProductCategory_SignificantTerms(null);
    }

    public void setProductCategory_SignificantTerms(ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda) {
        setProductCategory_SignificantTerms("product_category", opLambda, null);
    }

    public void setProductCategory_SignificantTerms(ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductCategory_SignificantTerms("product_category", opLambda, aggsLambda);
    }

    public void setProductCategory_SignificantTerms(String name, ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        SignificantTermsAggregationBuilder builder = regSignificantTermsA(name, "product_category");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setProductCategory_IpRange() {
        setProductCategory_IpRange(null);
    }

    public void setProductCategory_IpRange(ConditionOptionCall<IpRangeAggregationBuilder> opLambda) {
        setProductCategory_IpRange("product_category", opLambda, null);
    }

    public void setProductCategory_IpRange(ConditionOptionCall<IpRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductCategory_IpRange("product_category", opLambda, aggsLambda);
    }

    public void setProductCategory_IpRange(String name, ConditionOptionCall<IpRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        IpRangeAggregationBuilder builder = regIpRangeA(name, "product_category");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }


    public void setProductCategory_Count() {
        setProductCategory_Count(null);
    }

    public void setProductCategory_Count(ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        setProductCategory_Count("product_category", opLambda);
    }

    public void setProductCategory_Count(String name, ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        ValueCountAggregationBuilder builder = regCountA(name, "product_category");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategory_Cardinality() {
        setProductCategory_Cardinality(null);
    }

    public void setProductCategory_Cardinality(ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        setProductCategory_Cardinality("product_category", opLambda);
    }

    public void setProductCategory_Cardinality(String name, ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        CardinalityAggregationBuilder builder = regCardinalityA(name, "product_category");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategory_Missing() {
        setProductCategory_Missing(null);
    }

    public void setProductCategory_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda) {
        setProductCategory_Missing("product_category", opLambda, null);
    }

    public void setProductCategory_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductCategory_Missing("product_category", opLambda, aggsLambda);
    }

    public void setProductCategory_Missing(String name, ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingAggregationBuilder builder = regMissingA(name, "product_category");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }


    public void setProductCategoryCode_Terms() {
        setProductCategoryCode_Terms(null);
    }

    public void setProductCategoryCode_Terms(ConditionOptionCall<TermsAggregationBuilder> opLambda) {
        setProductCategoryCode_Terms("product_category_code", opLambda, null);
    }

    public void setProductCategoryCode_Terms(ConditionOptionCall<TermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductCategoryCode_Terms("product_category_code", opLambda, aggsLambda);
    }

    public void setProductCategoryCode_Terms(String name, ConditionOptionCall<TermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        TermsAggregationBuilder builder = regTermsA(name, "product_category_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setProductCategoryCode_SignificantTerms() {
        setProductCategoryCode_SignificantTerms(null);
    }

    public void setProductCategoryCode_SignificantTerms(ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda) {
        setProductCategoryCode_SignificantTerms("product_category_code", opLambda, null);
    }

    public void setProductCategoryCode_SignificantTerms(ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductCategoryCode_SignificantTerms("product_category_code", opLambda, aggsLambda);
    }

    public void setProductCategoryCode_SignificantTerms(String name, ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        SignificantTermsAggregationBuilder builder = regSignificantTermsA(name, "product_category_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setProductCategoryCode_IpRange() {
        setProductCategoryCode_IpRange(null);
    }

    public void setProductCategoryCode_IpRange(ConditionOptionCall<IpRangeAggregationBuilder> opLambda) {
        setProductCategoryCode_IpRange("product_category_code", opLambda, null);
    }

    public void setProductCategoryCode_IpRange(ConditionOptionCall<IpRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductCategoryCode_IpRange("product_category_code", opLambda, aggsLambda);
    }

    public void setProductCategoryCode_IpRange(String name, ConditionOptionCall<IpRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        IpRangeAggregationBuilder builder = regIpRangeA(name, "product_category_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }


    public void setProductCategoryCode_Count() {
        setProductCategoryCode_Count(null);
    }

    public void setProductCategoryCode_Count(ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        setProductCategoryCode_Count("product_category_code", opLambda);
    }

    public void setProductCategoryCode_Count(String name, ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        ValueCountAggregationBuilder builder = regCountA(name, "product_category_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategoryCode_Cardinality() {
        setProductCategoryCode_Cardinality(null);
    }

    public void setProductCategoryCode_Cardinality(ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        setProductCategoryCode_Cardinality("product_category_code", opLambda);
    }

    public void setProductCategoryCode_Cardinality(String name, ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        CardinalityAggregationBuilder builder = regCardinalityA(name, "product_category_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategoryCode_Missing() {
        setProductCategoryCode_Missing(null);
    }

    public void setProductCategoryCode_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda) {
        setProductCategoryCode_Missing("product_category_code", opLambda, null);
    }

    public void setProductCategoryCode_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductCategoryCode_Missing("product_category_code", opLambda, aggsLambda);
    }

    public void setProductCategoryCode_Missing(String name, ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingAggregationBuilder builder = regMissingA(name, "product_category_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }




    public void setProductDescription_Count() {
        setProductDescription_Count(null);
    }

    public void setProductDescription_Count(ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        setProductDescription_Count("product_description", opLambda);
    }

    public void setProductDescription_Count(String name, ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        ValueCountAggregationBuilder builder = regCountA(name, "product_description");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductDescription_Cardinality() {
        setProductDescription_Cardinality(null);
    }

    public void setProductDescription_Cardinality(ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        setProductDescription_Cardinality("product_description", opLambda);
    }

    public void setProductDescription_Cardinality(String name, ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        CardinalityAggregationBuilder builder = regCardinalityA(name, "product_description");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductDescription_Missing() {
        setProductDescription_Missing(null);
    }

    public void setProductDescription_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda) {
        setProductDescription_Missing("product_description", opLambda, null);
    }

    public void setProductDescription_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductDescription_Missing("product_description", opLambda, aggsLambda);
    }

    public void setProductDescription_Missing(String name, ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingAggregationBuilder builder = regMissingA(name, "product_description");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }


    public void setProductHandleCode_Terms() {
        setProductHandleCode_Terms(null);
    }

    public void setProductHandleCode_Terms(ConditionOptionCall<TermsAggregationBuilder> opLambda) {
        setProductHandleCode_Terms("product_handle_code", opLambda, null);
    }

    public void setProductHandleCode_Terms(ConditionOptionCall<TermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductHandleCode_Terms("product_handle_code", opLambda, aggsLambda);
    }

    public void setProductHandleCode_Terms(String name, ConditionOptionCall<TermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        TermsAggregationBuilder builder = regTermsA(name, "product_handle_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setProductHandleCode_SignificantTerms() {
        setProductHandleCode_SignificantTerms(null);
    }

    public void setProductHandleCode_SignificantTerms(ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda) {
        setProductHandleCode_SignificantTerms("product_handle_code", opLambda, null);
    }

    public void setProductHandleCode_SignificantTerms(ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductHandleCode_SignificantTerms("product_handle_code", opLambda, aggsLambda);
    }

    public void setProductHandleCode_SignificantTerms(String name, ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        SignificantTermsAggregationBuilder builder = regSignificantTermsA(name, "product_handle_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setProductHandleCode_IpRange() {
        setProductHandleCode_IpRange(null);
    }

    public void setProductHandleCode_IpRange(ConditionOptionCall<IpRangeAggregationBuilder> opLambda) {
        setProductHandleCode_IpRange("product_handle_code", opLambda, null);
    }

    public void setProductHandleCode_IpRange(ConditionOptionCall<IpRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductHandleCode_IpRange("product_handle_code", opLambda, aggsLambda);
    }

    public void setProductHandleCode_IpRange(String name, ConditionOptionCall<IpRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        IpRangeAggregationBuilder builder = regIpRangeA(name, "product_handle_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }


    public void setProductHandleCode_Count() {
        setProductHandleCode_Count(null);
    }

    public void setProductHandleCode_Count(ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        setProductHandleCode_Count("product_handle_code", opLambda);
    }

    public void setProductHandleCode_Count(String name, ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        ValueCountAggregationBuilder builder = regCountA(name, "product_handle_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductHandleCode_Cardinality() {
        setProductHandleCode_Cardinality(null);
    }

    public void setProductHandleCode_Cardinality(ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        setProductHandleCode_Cardinality("product_handle_code", opLambda);
    }

    public void setProductHandleCode_Cardinality(String name, ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        CardinalityAggregationBuilder builder = regCardinalityA(name, "product_handle_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductHandleCode_Missing() {
        setProductHandleCode_Missing(null);
    }

    public void setProductHandleCode_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda) {
        setProductHandleCode_Missing("product_handle_code", opLambda, null);
    }

    public void setProductHandleCode_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductHandleCode_Missing("product_handle_code", opLambda, aggsLambda);
    }

    public void setProductHandleCode_Missing(String name, ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingAggregationBuilder builder = regMissingA(name, "product_handle_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }


    public void setProductName_Terms() {
        setProductName_Terms(null);
    }

    public void setProductName_Terms(ConditionOptionCall<TermsAggregationBuilder> opLambda) {
        setProductName_Terms("product_name", opLambda, null);
    }

    public void setProductName_Terms(ConditionOptionCall<TermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductName_Terms("product_name", opLambda, aggsLambda);
    }

    public void setProductName_Terms(String name, ConditionOptionCall<TermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        TermsAggregationBuilder builder = regTermsA(name, "product_name");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setProductName_SignificantTerms() {
        setProductName_SignificantTerms(null);
    }

    public void setProductName_SignificantTerms(ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda) {
        setProductName_SignificantTerms("product_name", opLambda, null);
    }

    public void setProductName_SignificantTerms(ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductName_SignificantTerms("product_name", opLambda, aggsLambda);
    }

    public void setProductName_SignificantTerms(String name, ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        SignificantTermsAggregationBuilder builder = regSignificantTermsA(name, "product_name");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setProductName_IpRange() {
        setProductName_IpRange(null);
    }

    public void setProductName_IpRange(ConditionOptionCall<IpRangeAggregationBuilder> opLambda) {
        setProductName_IpRange("product_name", opLambda, null);
    }

    public void setProductName_IpRange(ConditionOptionCall<IpRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductName_IpRange("product_name", opLambda, aggsLambda);
    }

    public void setProductName_IpRange(String name, ConditionOptionCall<IpRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        IpRangeAggregationBuilder builder = regIpRangeA(name, "product_name");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }


    public void setProductName_Count() {
        setProductName_Count(null);
    }

    public void setProductName_Count(ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        setProductName_Count("product_name", opLambda);
    }

    public void setProductName_Count(String name, ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        ValueCountAggregationBuilder builder = regCountA(name, "product_name");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductName_Cardinality() {
        setProductName_Cardinality(null);
    }

    public void setProductName_Cardinality(ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        setProductName_Cardinality("product_name", opLambda);
    }

    public void setProductName_Cardinality(String name, ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        CardinalityAggregationBuilder builder = regCardinalityA(name, "product_name");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductName_Missing() {
        setProductName_Missing(null);
    }

    public void setProductName_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda) {
        setProductName_Missing("product_name", opLambda, null);
    }

    public void setProductName_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductName_Missing("product_name", opLambda, aggsLambda);
    }

    public void setProductName_Missing(String name, ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingAggregationBuilder builder = regMissingA(name, "product_name");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }


    public void setProductStatus_Terms() {
        setProductStatus_Terms(null);
    }

    public void setProductStatus_Terms(ConditionOptionCall<TermsAggregationBuilder> opLambda) {
        setProductStatus_Terms("product_status", opLambda, null);
    }

    public void setProductStatus_Terms(ConditionOptionCall<TermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductStatus_Terms("product_status", opLambda, aggsLambda);
    }

    public void setProductStatus_Terms(String name, ConditionOptionCall<TermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        TermsAggregationBuilder builder = regTermsA(name, "product_status");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setProductStatus_SignificantTerms() {
        setProductStatus_SignificantTerms(null);
    }

    public void setProductStatus_SignificantTerms(ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda) {
        setProductStatus_SignificantTerms("product_status", opLambda, null);
    }

    public void setProductStatus_SignificantTerms(ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductStatus_SignificantTerms("product_status", opLambda, aggsLambda);
    }

    public void setProductStatus_SignificantTerms(String name, ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        SignificantTermsAggregationBuilder builder = regSignificantTermsA(name, "product_status");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setProductStatus_IpRange() {
        setProductStatus_IpRange(null);
    }

    public void setProductStatus_IpRange(ConditionOptionCall<IpRangeAggregationBuilder> opLambda) {
        setProductStatus_IpRange("product_status", opLambda, null);
    }

    public void setProductStatus_IpRange(ConditionOptionCall<IpRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductStatus_IpRange("product_status", opLambda, aggsLambda);
    }

    public void setProductStatus_IpRange(String name, ConditionOptionCall<IpRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        IpRangeAggregationBuilder builder = regIpRangeA(name, "product_status");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }


    public void setProductStatus_Count() {
        setProductStatus_Count(null);
    }

    public void setProductStatus_Count(ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        setProductStatus_Count("product_status", opLambda);
    }

    public void setProductStatus_Count(String name, ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        ValueCountAggregationBuilder builder = regCountA(name, "product_status");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatus_Cardinality() {
        setProductStatus_Cardinality(null);
    }

    public void setProductStatus_Cardinality(ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        setProductStatus_Cardinality("product_status", opLambda);
    }

    public void setProductStatus_Cardinality(String name, ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        CardinalityAggregationBuilder builder = regCardinalityA(name, "product_status");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatus_Missing() {
        setProductStatus_Missing(null);
    }

    public void setProductStatus_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda) {
        setProductStatus_Missing("product_status", opLambda, null);
    }

    public void setProductStatus_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductStatus_Missing("product_status", opLambda, aggsLambda);
    }

    public void setProductStatus_Missing(String name, ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingAggregationBuilder builder = regMissingA(name, "product_status");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }


    public void setProductStatusCode_Terms() {
        setProductStatusCode_Terms(null);
    }

    public void setProductStatusCode_Terms(ConditionOptionCall<TermsAggregationBuilder> opLambda) {
        setProductStatusCode_Terms("product_status_code", opLambda, null);
    }

    public void setProductStatusCode_Terms(ConditionOptionCall<TermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductStatusCode_Terms("product_status_code", opLambda, aggsLambda);
    }

    public void setProductStatusCode_Terms(String name, ConditionOptionCall<TermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        TermsAggregationBuilder builder = regTermsA(name, "product_status_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setProductStatusCode_SignificantTerms() {
        setProductStatusCode_SignificantTerms(null);
    }

    public void setProductStatusCode_SignificantTerms(ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda) {
        setProductStatusCode_SignificantTerms("product_status_code", opLambda, null);
    }

    public void setProductStatusCode_SignificantTerms(ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductStatusCode_SignificantTerms("product_status_code", opLambda, aggsLambda);
    }

    public void setProductStatusCode_SignificantTerms(String name, ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        SignificantTermsAggregationBuilder builder = regSignificantTermsA(name, "product_status_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setProductStatusCode_IpRange() {
        setProductStatusCode_IpRange(null);
    }

    public void setProductStatusCode_IpRange(ConditionOptionCall<IpRangeAggregationBuilder> opLambda) {
        setProductStatusCode_IpRange("product_status_code", opLambda, null);
    }

    public void setProductStatusCode_IpRange(ConditionOptionCall<IpRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductStatusCode_IpRange("product_status_code", opLambda, aggsLambda);
    }

    public void setProductStatusCode_IpRange(String name, ConditionOptionCall<IpRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        IpRangeAggregationBuilder builder = regIpRangeA(name, "product_status_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }


    public void setProductStatusCode_Count() {
        setProductStatusCode_Count(null);
    }

    public void setProductStatusCode_Count(ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        setProductStatusCode_Count("product_status_code", opLambda);
    }

    public void setProductStatusCode_Count(String name, ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        ValueCountAggregationBuilder builder = regCountA(name, "product_status_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatusCode_Cardinality() {
        setProductStatusCode_Cardinality(null);
    }

    public void setProductStatusCode_Cardinality(ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        setProductStatusCode_Cardinality("product_status_code", opLambda);
    }

    public void setProductStatusCode_Cardinality(String name, ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        CardinalityAggregationBuilder builder = regCardinalityA(name, "product_status_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatusCode_Missing() {
        setProductStatusCode_Missing(null);
    }

    public void setProductStatusCode_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda) {
        setProductStatusCode_Missing("product_status_code", opLambda, null);
    }

    public void setProductStatusCode_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductStatusCode_Missing("product_status_code", opLambda, aggsLambda);
    }

    public void setProductStatusCode_Missing(String name, ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingAggregationBuilder builder = regMissingA(name, "product_status_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }



    public void setRegisterDatetime_DateRange() {
        setRegisterDatetime_DateRange(null);
    }

    public void setRegisterDatetime_DateRange(ConditionOptionCall<DateRangeAggregationBuilder> opLambda) {
        setRegisterDatetime_DateRange("register_datetime", opLambda, null);
    }

    public void setRegisterDatetime_DateRange(ConditionOptionCall<DateRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegisterDatetime_DateRange("register_datetime", opLambda, aggsLambda);
    }

    public void setRegisterDatetime_DateRange(String name, ConditionOptionCall<DateRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        DateRangeAggregationBuilder builder = regDateRangeA(name, "register_datetime");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setRegisterDatetime_DateHistogram() {
        setRegisterDatetime_DateHistogram(null);
    }

    public void setRegisterDatetime_DateHistogram(ConditionOptionCall<DateHistogramAggregationBuilder> opLambda) {
        setRegisterDatetime_DateHistogram("register_datetime", opLambda, null);
    }

    public void setRegisterDatetime_DateHistogram(ConditionOptionCall<DateHistogramAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegisterDatetime_DateHistogram("register_datetime", opLambda, aggsLambda);
    }

    public void setRegisterDatetime_DateHistogram(String name, ConditionOptionCall<DateHistogramAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        DateHistogramAggregationBuilder builder = regDateHistogramA(name, "register_datetime");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setRegisterDatetime_Count() {
        setRegisterDatetime_Count(null);
    }

    public void setRegisterDatetime_Count(ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        setRegisterDatetime_Count("register_datetime", opLambda);
    }

    public void setRegisterDatetime_Count(String name, ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        ValueCountAggregationBuilder builder = regCountA(name, "register_datetime");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_Cardinality() {
        setRegisterDatetime_Cardinality(null);
    }

    public void setRegisterDatetime_Cardinality(ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        setRegisterDatetime_Cardinality("register_datetime", opLambda);
    }

    public void setRegisterDatetime_Cardinality(String name, ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        CardinalityAggregationBuilder builder = regCardinalityA(name, "register_datetime");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_Missing() {
        setRegisterDatetime_Missing(null);
    }

    public void setRegisterDatetime_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda) {
        setRegisterDatetime_Missing("register_datetime", opLambda, null);
    }

    public void setRegisterDatetime_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegisterDatetime_Missing("register_datetime", opLambda, aggsLambda);
    }

    public void setRegisterDatetime_Missing(String name, ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingAggregationBuilder builder = regMissingA(name, "register_datetime");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }


    public void setRegisterUser_Terms() {
        setRegisterUser_Terms(null);
    }

    public void setRegisterUser_Terms(ConditionOptionCall<TermsAggregationBuilder> opLambda) {
        setRegisterUser_Terms("register_user", opLambda, null);
    }

    public void setRegisterUser_Terms(ConditionOptionCall<TermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegisterUser_Terms("register_user", opLambda, aggsLambda);
    }

    public void setRegisterUser_Terms(String name, ConditionOptionCall<TermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        TermsAggregationBuilder builder = regTermsA(name, "register_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setRegisterUser_SignificantTerms() {
        setRegisterUser_SignificantTerms(null);
    }

    public void setRegisterUser_SignificantTerms(ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda) {
        setRegisterUser_SignificantTerms("register_user", opLambda, null);
    }

    public void setRegisterUser_SignificantTerms(ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegisterUser_SignificantTerms("register_user", opLambda, aggsLambda);
    }

    public void setRegisterUser_SignificantTerms(String name, ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        SignificantTermsAggregationBuilder builder = regSignificantTermsA(name, "register_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setRegisterUser_IpRange() {
        setRegisterUser_IpRange(null);
    }

    public void setRegisterUser_IpRange(ConditionOptionCall<IpRangeAggregationBuilder> opLambda) {
        setRegisterUser_IpRange("register_user", opLambda, null);
    }

    public void setRegisterUser_IpRange(ConditionOptionCall<IpRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegisterUser_IpRange("register_user", opLambda, aggsLambda);
    }

    public void setRegisterUser_IpRange(String name, ConditionOptionCall<IpRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        IpRangeAggregationBuilder builder = regIpRangeA(name, "register_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }


    public void setRegisterUser_Count() {
        setRegisterUser_Count(null);
    }

    public void setRegisterUser_Count(ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        setRegisterUser_Count("register_user", opLambda);
    }

    public void setRegisterUser_Count(String name, ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        ValueCountAggregationBuilder builder = regCountA(name, "register_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_Cardinality() {
        setRegisterUser_Cardinality(null);
    }

    public void setRegisterUser_Cardinality(ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        setRegisterUser_Cardinality("register_user", opLambda);
    }

    public void setRegisterUser_Cardinality(String name, ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        CardinalityAggregationBuilder builder = regCardinalityA(name, "register_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_Missing() {
        setRegisterUser_Missing(null);
    }

    public void setRegisterUser_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda) {
        setRegisterUser_Missing("register_user", opLambda, null);
    }

    public void setRegisterUser_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegisterUser_Missing("register_user", opLambda, aggsLambda);
    }

    public void setRegisterUser_Missing(String name, ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingAggregationBuilder builder = regMissingA(name, "register_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setRegularPrice_Avg() {
        setRegularPrice_Avg(null);
    }

    public void setRegularPrice_Avg(ConditionOptionCall<AvgAggregationBuilder> opLambda) {
        setRegularPrice_Avg("regular_price", opLambda);
    }

    public void setRegularPrice_Avg(String name, ConditionOptionCall<AvgAggregationBuilder> opLambda) {
        AvgAggregationBuilder builder = regAvgA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_Max() {
        setRegularPrice_Max(null);
    }

    public void setRegularPrice_Max(ConditionOptionCall<MaxAggregationBuilder> opLambda) {
        setRegularPrice_Max("regular_price", opLambda);
    }

    public void setRegularPrice_Max(String name, ConditionOptionCall<MaxAggregationBuilder> opLambda) {
        MaxAggregationBuilder builder = regMaxA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }
    public void setRegularPrice_Min() {
        setRegularPrice_Min(null);
    }

    public void setRegularPrice_Min(ConditionOptionCall<MinAggregationBuilder> opLambda) {
        setRegularPrice_Min("regular_price", opLambda);
    }

    public void setRegularPrice_Min(String name, ConditionOptionCall<MinAggregationBuilder> opLambda) {
        MinAggregationBuilder builder = regMinA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_Sum() {
        setRegularPrice_Sum(null);
    }

    public void setRegularPrice_Sum(ConditionOptionCall<SumAggregationBuilder> opLambda) {
        setRegularPrice_Sum("regular_price", opLambda);
    }

    public void setRegularPrice_Sum(String name, ConditionOptionCall<SumAggregationBuilder> opLambda) {
        SumAggregationBuilder builder = regSumA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_ExtendedStats() {
        setRegularPrice_ExtendedStats(null);
    }

    public void setRegularPrice_ExtendedStats(ConditionOptionCall<ExtendedStatsAggregationBuilder> opLambda) {
        setRegularPrice_ExtendedStats("regular_price", opLambda);
    }

    public void setRegularPrice_ExtendedStats(String name, ConditionOptionCall<ExtendedStatsAggregationBuilder> opLambda) {
        ExtendedStatsAggregationBuilder builder = regExtendedStatsA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_Stats() {
        setRegularPrice_Stats(null);
    }

    public void setRegularPrice_Stats(ConditionOptionCall<StatsAggregationBuilder> opLambda) {
        setRegularPrice_Stats("regular_price", opLambda);
    }

    public void setRegularPrice_Stats(String name, ConditionOptionCall<StatsAggregationBuilder> opLambda) {
        StatsAggregationBuilder builder = regStatsA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_Percentiles() {
        setRegularPrice_Percentiles(null);
    }

    public void setRegularPrice_Percentiles(ConditionOptionCall<PercentilesAggregationBuilder> opLambda) {
        setRegularPrice_Percentiles("regular_price", opLambda);
    }

    public void setRegularPrice_Percentiles(String name, ConditionOptionCall<PercentilesAggregationBuilder> opLambda) {
        PercentilesAggregationBuilder builder = regPercentilesA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_PercentileRanks(double[] values) {
        setRegularPrice_PercentileRanks(values, null);
    }

    public void setRegularPrice_PercentileRanks(double[] values, ConditionOptionCall<PercentileRanksAggregationBuilder> opLambda) {
        setRegularPrice_PercentileRanks("regular_price", values, opLambda);
    }

    public void setRegularPrice_PercentileRanks(String name, double[] values, ConditionOptionCall<PercentileRanksAggregationBuilder> opLambda) {
        PercentileRanksAggregationBuilder builder = regPercentileRanksA(name, "regular_price", values);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_Histogram() {
        setRegularPrice_Histogram(null);
    }

    public void setRegularPrice_Histogram(ConditionOptionCall<HistogramAggregationBuilder> opLambda) {
        setRegularPrice_Histogram("regular_price", opLambda, null);
    }

    public void setRegularPrice_Histogram(ConditionOptionCall<HistogramAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegularPrice_Histogram("regular_price", opLambda, aggsLambda);
    }

    public void setRegularPrice_Histogram(String name, ConditionOptionCall<HistogramAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        HistogramAggregationBuilder builder = regHistogramA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setRegularPrice_Range() {
        setRegularPrice_Range(null);
    }

    public void setRegularPrice_Range(ConditionOptionCall<RangeAggregationBuilder> opLambda) {
        setRegularPrice_Range("regular_price", opLambda, null);
    }

    public void setRegularPrice_Range(ConditionOptionCall<RangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegularPrice_Range("regular_price", opLambda, aggsLambda);
    }

    public void setRegularPrice_Range(String name, ConditionOptionCall<RangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        RangeAggregationBuilder builder = regRangeA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }



    public void setRegularPrice_Count() {
        setRegularPrice_Count(null);
    }

    public void setRegularPrice_Count(ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        setRegularPrice_Count("regular_price", opLambda);
    }

    public void setRegularPrice_Count(String name, ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        ValueCountAggregationBuilder builder = regCountA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_Cardinality() {
        setRegularPrice_Cardinality(null);
    }

    public void setRegularPrice_Cardinality(ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        setRegularPrice_Cardinality("regular_price", opLambda);
    }

    public void setRegularPrice_Cardinality(String name, ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        CardinalityAggregationBuilder builder = regCardinalityA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_Missing() {
        setRegularPrice_Missing(null);
    }

    public void setRegularPrice_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda) {
        setRegularPrice_Missing("regular_price", opLambda, null);
    }

    public void setRegularPrice_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegularPrice_Missing("regular_price", opLambda, aggsLambda);
    }

    public void setRegularPrice_Missing(String name, ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingAggregationBuilder builder = regMissingA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }



    public void setUpdateDatetime_DateRange() {
        setUpdateDatetime_DateRange(null);
    }

    public void setUpdateDatetime_DateRange(ConditionOptionCall<DateRangeAggregationBuilder> opLambda) {
        setUpdateDatetime_DateRange("update_datetime", opLambda, null);
    }

    public void setUpdateDatetime_DateRange(ConditionOptionCall<DateRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setUpdateDatetime_DateRange("update_datetime", opLambda, aggsLambda);
    }

    public void setUpdateDatetime_DateRange(String name, ConditionOptionCall<DateRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        DateRangeAggregationBuilder builder = regDateRangeA(name, "update_datetime");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setUpdateDatetime_DateHistogram() {
        setUpdateDatetime_DateHistogram(null);
    }

    public void setUpdateDatetime_DateHistogram(ConditionOptionCall<DateHistogramAggregationBuilder> opLambda) {
        setUpdateDatetime_DateHistogram("update_datetime", opLambda, null);
    }

    public void setUpdateDatetime_DateHistogram(ConditionOptionCall<DateHistogramAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setUpdateDatetime_DateHistogram("update_datetime", opLambda, aggsLambda);
    }

    public void setUpdateDatetime_DateHistogram(String name, ConditionOptionCall<DateHistogramAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        DateHistogramAggregationBuilder builder = regDateHistogramA(name, "update_datetime");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setUpdateDatetime_Count() {
        setUpdateDatetime_Count(null);
    }

    public void setUpdateDatetime_Count(ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        setUpdateDatetime_Count("update_datetime", opLambda);
    }

    public void setUpdateDatetime_Count(String name, ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        ValueCountAggregationBuilder builder = regCountA(name, "update_datetime");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_Cardinality() {
        setUpdateDatetime_Cardinality(null);
    }

    public void setUpdateDatetime_Cardinality(ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        setUpdateDatetime_Cardinality("update_datetime", opLambda);
    }

    public void setUpdateDatetime_Cardinality(String name, ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        CardinalityAggregationBuilder builder = regCardinalityA(name, "update_datetime");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_Missing() {
        setUpdateDatetime_Missing(null);
    }

    public void setUpdateDatetime_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda) {
        setUpdateDatetime_Missing("update_datetime", opLambda, null);
    }

    public void setUpdateDatetime_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setUpdateDatetime_Missing("update_datetime", opLambda, aggsLambda);
    }

    public void setUpdateDatetime_Missing(String name, ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingAggregationBuilder builder = regMissingA(name, "update_datetime");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }


    public void setUpdateUser_Terms() {
        setUpdateUser_Terms(null);
    }

    public void setUpdateUser_Terms(ConditionOptionCall<TermsAggregationBuilder> opLambda) {
        setUpdateUser_Terms("update_user", opLambda, null);
    }

    public void setUpdateUser_Terms(ConditionOptionCall<TermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setUpdateUser_Terms("update_user", opLambda, aggsLambda);
    }

    public void setUpdateUser_Terms(String name, ConditionOptionCall<TermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        TermsAggregationBuilder builder = regTermsA(name, "update_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setUpdateUser_SignificantTerms() {
        setUpdateUser_SignificantTerms(null);
    }

    public void setUpdateUser_SignificantTerms(ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda) {
        setUpdateUser_SignificantTerms("update_user", opLambda, null);
    }

    public void setUpdateUser_SignificantTerms(ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setUpdateUser_SignificantTerms("update_user", opLambda, aggsLambda);
    }

    public void setUpdateUser_SignificantTerms(String name, ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        SignificantTermsAggregationBuilder builder = regSignificantTermsA(name, "update_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setUpdateUser_IpRange() {
        setUpdateUser_IpRange(null);
    }

    public void setUpdateUser_IpRange(ConditionOptionCall<IpRangeAggregationBuilder> opLambda) {
        setUpdateUser_IpRange("update_user", opLambda, null);
    }

    public void setUpdateUser_IpRange(ConditionOptionCall<IpRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setUpdateUser_IpRange("update_user", opLambda, aggsLambda);
    }

    public void setUpdateUser_IpRange(String name, ConditionOptionCall<IpRangeAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        IpRangeAggregationBuilder builder = regIpRangeA(name, "update_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }


    public void setUpdateUser_Count() {
        setUpdateUser_Count(null);
    }

    public void setUpdateUser_Count(ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        setUpdateUser_Count("update_user", opLambda);
    }

    public void setUpdateUser_Count(String name, ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        ValueCountAggregationBuilder builder = regCountA(name, "update_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_Cardinality() {
        setUpdateUser_Cardinality(null);
    }

    public void setUpdateUser_Cardinality(ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        setUpdateUser_Cardinality("update_user", opLambda);
    }

    public void setUpdateUser_Cardinality(String name, ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        CardinalityAggregationBuilder builder = regCardinalityA(name, "update_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_Missing() {
        setUpdateUser_Missing(null);
    }

    public void setUpdateUser_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda) {
        setUpdateUser_Missing("update_user", opLambda, null);
    }

    public void setUpdateUser_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setUpdateUser_Missing("update_user", opLambda, aggsLambda);
    }

    public void setUpdateUser_Missing(String name, ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingAggregationBuilder builder = regMissingA(name, "update_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

}
