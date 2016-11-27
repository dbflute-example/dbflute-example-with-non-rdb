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
import org.elasticsearch.search.aggregations.bucket.global.GlobalBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.HistogramBuilder;
import org.elasticsearch.search.aggregations.bucket.missing.MissingBuilder;
import org.elasticsearch.search.aggregations.bucket.range.RangeBuilder;
import org.elasticsearch.search.aggregations.bucket.range.date.DateRangeBuilder;
import org.elasticsearch.search.aggregations.bucket.range.ipv4.IPv4RangeBuilder;
import org.elasticsearch.search.aggregations.bucket.sampler.SamplerAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.significant.SignificantTermsBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityBuilder;
import org.elasticsearch.search.aggregations.metrics.max.MaxBuilder;
import org.elasticsearch.search.aggregations.metrics.min.MinBuilder;
import org.elasticsearch.search.aggregations.metrics.percentiles.PercentileRanksBuilder;
import org.elasticsearch.search.aggregations.metrics.percentiles.PercentilesBuilder;
import org.elasticsearch.search.aggregations.metrics.scripted.ScriptedMetricBuilder;
import org.elasticsearch.search.aggregations.metrics.stats.StatsBuilder;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStatsBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountBuilder;

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

    public void global(String name, ConditionOptionCall<GlobalBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        GlobalBuilder builder = regGlobalA(name);
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

    public void scriptedMetric(String name, ConditionOptionCall<ScriptedMetricBuilder> opLambda) {
        ScriptedMetricBuilder builder = regScriptedMetricA(name);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void topHits(String name, ConditionOptionCall<TopHitsBuilder> opLambda) {
        TopHitsBuilder builder = regTopHitsA(name);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    // LocalDateTime latest_purchase_date


    public void setLatestPurchaseDate_DateRange() {
        setLatestPurchaseDate_DateRange(null);
    }

    public void setLatestPurchaseDate_DateRange(ConditionOptionCall<DateRangeBuilder> opLambda) {
        setLatestPurchaseDate_DateRange("latest_purchase_date", opLambda, null);
    }

    public void setLatestPurchaseDate_DateRange(ConditionOptionCall<DateRangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setLatestPurchaseDate_DateRange("latest_purchase_date", opLambda, aggsLambda);
    }

    public void setLatestPurchaseDate_DateRange(String name, ConditionOptionCall<DateRangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        DateRangeBuilder builder = regDateRangeA(name, "latest_purchase_date");
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

    public void setLatestPurchaseDate_DateHistogram(ConditionOptionCall<DateHistogramBuilder> opLambda) {
        setLatestPurchaseDate_DateHistogram("latest_purchase_date", opLambda, null);
    }

    public void setLatestPurchaseDate_DateHistogram(ConditionOptionCall<DateHistogramBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setLatestPurchaseDate_DateHistogram("latest_purchase_date", opLambda, aggsLambda);
    }

    public void setLatestPurchaseDate_DateHistogram(String name, ConditionOptionCall<DateHistogramBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        DateHistogramBuilder builder = regDateHistogramA(name, "latest_purchase_date");
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

    public void setLatestPurchaseDate_Count(ConditionOptionCall<ValueCountBuilder> opLambda) {
        setLatestPurchaseDate_Count("latest_purchase_date", opLambda);
    }

    public void setLatestPurchaseDate_Count(String name, ConditionOptionCall<ValueCountBuilder> opLambda) {
        ValueCountBuilder builder = regCountA(name, "latest_purchase_date");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setLatestPurchaseDate_Cardinality() {
        setLatestPurchaseDate_Cardinality(null);
    }

    public void setLatestPurchaseDate_Cardinality(ConditionOptionCall<CardinalityBuilder> opLambda) {
        setLatestPurchaseDate_Cardinality("latest_purchase_date", opLambda);
    }

    public void setLatestPurchaseDate_Cardinality(String name, ConditionOptionCall<CardinalityBuilder> opLambda) {
        CardinalityBuilder builder = regCardinalityA(name, "latest_purchase_date");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setLatestPurchaseDate_Missing() {
        setLatestPurchaseDate_Missing(null);
    }

    public void setLatestPurchaseDate_Missing(ConditionOptionCall<MissingBuilder> opLambda) {
        setLatestPurchaseDate_Missing("latest_purchase_date", opLambda, null);
    }

    public void setLatestPurchaseDate_Missing(ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setLatestPurchaseDate_Missing("latest_purchase_date", opLambda, aggsLambda);
    }

    public void setLatestPurchaseDate_Missing(String name, ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingBuilder builder = regMissingA(name, "latest_purchase_date");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    // String product_category

    public void setProductCategory_Terms() {
        setProductCategory_Terms(null);
    }

    public void setProductCategory_Terms(ConditionOptionCall<TermsBuilder> opLambda) {
        setProductCategory_Terms("product_category", opLambda, null);
    }

    public void setProductCategory_Terms(ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductCategory_Terms("product_category", opLambda, aggsLambda);
    }

    public void setProductCategory_Terms(String name, ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        TermsBuilder builder = regTermsA(name, "product_category");
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

    public void setProductCategory_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda) {
        setProductCategory_SignificantTerms("product_category", opLambda, null);
    }

    public void setProductCategory_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductCategory_SignificantTerms("product_category", opLambda, aggsLambda);
    }

    public void setProductCategory_SignificantTerms(String name, ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        SignificantTermsBuilder builder = regSignificantTermsA(name, "product_category");
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

    public void setProductCategory_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda) {
        setProductCategory_IpRange("product_category", opLambda, null);
    }

    public void setProductCategory_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductCategory_IpRange("product_category", opLambda, aggsLambda);
    }

    public void setProductCategory_IpRange(String name, ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        IPv4RangeBuilder builder = regIpRangeA(name, "product_category");
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

    public void setProductCategory_Count(ConditionOptionCall<ValueCountBuilder> opLambda) {
        setProductCategory_Count("product_category", opLambda);
    }

    public void setProductCategory_Count(String name, ConditionOptionCall<ValueCountBuilder> opLambda) {
        ValueCountBuilder builder = regCountA(name, "product_category");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategory_Cardinality() {
        setProductCategory_Cardinality(null);
    }

    public void setProductCategory_Cardinality(ConditionOptionCall<CardinalityBuilder> opLambda) {
        setProductCategory_Cardinality("product_category", opLambda);
    }

    public void setProductCategory_Cardinality(String name, ConditionOptionCall<CardinalityBuilder> opLambda) {
        CardinalityBuilder builder = regCardinalityA(name, "product_category");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategory_Missing() {
        setProductCategory_Missing(null);
    }

    public void setProductCategory_Missing(ConditionOptionCall<MissingBuilder> opLambda) {
        setProductCategory_Missing("product_category", opLambda, null);
    }

    public void setProductCategory_Missing(ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductCategory_Missing("product_category", opLambda, aggsLambda);
    }

    public void setProductCategory_Missing(String name, ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingBuilder builder = regMissingA(name, "product_category");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    // String product_category_code

    public void setProductCategoryCode_Terms() {
        setProductCategoryCode_Terms(null);
    }

    public void setProductCategoryCode_Terms(ConditionOptionCall<TermsBuilder> opLambda) {
        setProductCategoryCode_Terms("product_category_code", opLambda, null);
    }

    public void setProductCategoryCode_Terms(ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductCategoryCode_Terms("product_category_code", opLambda, aggsLambda);
    }

    public void setProductCategoryCode_Terms(String name, ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        TermsBuilder builder = regTermsA(name, "product_category_code");
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

    public void setProductCategoryCode_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda) {
        setProductCategoryCode_SignificantTerms("product_category_code", opLambda, null);
    }

    public void setProductCategoryCode_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductCategoryCode_SignificantTerms("product_category_code", opLambda, aggsLambda);
    }

    public void setProductCategoryCode_SignificantTerms(String name, ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        SignificantTermsBuilder builder = regSignificantTermsA(name, "product_category_code");
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

    public void setProductCategoryCode_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda) {
        setProductCategoryCode_IpRange("product_category_code", opLambda, null);
    }

    public void setProductCategoryCode_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductCategoryCode_IpRange("product_category_code", opLambda, aggsLambda);
    }

    public void setProductCategoryCode_IpRange(String name, ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        IPv4RangeBuilder builder = regIpRangeA(name, "product_category_code");
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

    public void setProductCategoryCode_Count(ConditionOptionCall<ValueCountBuilder> opLambda) {
        setProductCategoryCode_Count("product_category_code", opLambda);
    }

    public void setProductCategoryCode_Count(String name, ConditionOptionCall<ValueCountBuilder> opLambda) {
        ValueCountBuilder builder = regCountA(name, "product_category_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategoryCode_Cardinality() {
        setProductCategoryCode_Cardinality(null);
    }

    public void setProductCategoryCode_Cardinality(ConditionOptionCall<CardinalityBuilder> opLambda) {
        setProductCategoryCode_Cardinality("product_category_code", opLambda);
    }

    public void setProductCategoryCode_Cardinality(String name, ConditionOptionCall<CardinalityBuilder> opLambda) {
        CardinalityBuilder builder = regCardinalityA(name, "product_category_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductCategoryCode_Missing() {
        setProductCategoryCode_Missing(null);
    }

    public void setProductCategoryCode_Missing(ConditionOptionCall<MissingBuilder> opLambda) {
        setProductCategoryCode_Missing("product_category_code", opLambda, null);
    }

    public void setProductCategoryCode_Missing(ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductCategoryCode_Missing("product_category_code", opLambda, aggsLambda);
    }

    public void setProductCategoryCode_Missing(String name, ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingBuilder builder = regMissingA(name, "product_category_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    // String product_description

    public void setProductDescription_Terms() {
        setProductDescription_Terms(null);
    }

    public void setProductDescription_Terms(ConditionOptionCall<TermsBuilder> opLambda) {
        setProductDescription_Terms("product_description", opLambda, null);
    }

    public void setProductDescription_Terms(ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductDescription_Terms("product_description", opLambda, aggsLambda);
    }

    public void setProductDescription_Terms(String name, ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        TermsBuilder builder = regTermsA(name, "product_description");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setProductDescription_SignificantTerms() {
        setProductDescription_SignificantTerms(null);
    }

    public void setProductDescription_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda) {
        setProductDescription_SignificantTerms("product_description", opLambda, null);
    }

    public void setProductDescription_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductDescription_SignificantTerms("product_description", opLambda, aggsLambda);
    }

    public void setProductDescription_SignificantTerms(String name, ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        SignificantTermsBuilder builder = regSignificantTermsA(name, "product_description");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setProductDescription_IpRange() {
        setProductDescription_IpRange(null);
    }

    public void setProductDescription_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda) {
        setProductDescription_IpRange("product_description", opLambda, null);
    }

    public void setProductDescription_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductDescription_IpRange("product_description", opLambda, aggsLambda);
    }

    public void setProductDescription_IpRange(String name, ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        IPv4RangeBuilder builder = regIpRangeA(name, "product_description");
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

    public void setProductDescription_Count(ConditionOptionCall<ValueCountBuilder> opLambda) {
        setProductDescription_Count("product_description", opLambda);
    }

    public void setProductDescription_Count(String name, ConditionOptionCall<ValueCountBuilder> opLambda) {
        ValueCountBuilder builder = regCountA(name, "product_description");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductDescription_Cardinality() {
        setProductDescription_Cardinality(null);
    }

    public void setProductDescription_Cardinality(ConditionOptionCall<CardinalityBuilder> opLambda) {
        setProductDescription_Cardinality("product_description", opLambda);
    }

    public void setProductDescription_Cardinality(String name, ConditionOptionCall<CardinalityBuilder> opLambda) {
        CardinalityBuilder builder = regCardinalityA(name, "product_description");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductDescription_Missing() {
        setProductDescription_Missing(null);
    }

    public void setProductDescription_Missing(ConditionOptionCall<MissingBuilder> opLambda) {
        setProductDescription_Missing("product_description", opLambda, null);
    }

    public void setProductDescription_Missing(ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductDescription_Missing("product_description", opLambda, aggsLambda);
    }

    public void setProductDescription_Missing(String name, ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingBuilder builder = regMissingA(name, "product_description");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    // String product_handle_code

    public void setProductHandleCode_Terms() {
        setProductHandleCode_Terms(null);
    }

    public void setProductHandleCode_Terms(ConditionOptionCall<TermsBuilder> opLambda) {
        setProductHandleCode_Terms("product_handle_code", opLambda, null);
    }

    public void setProductHandleCode_Terms(ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductHandleCode_Terms("product_handle_code", opLambda, aggsLambda);
    }

    public void setProductHandleCode_Terms(String name, ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        TermsBuilder builder = regTermsA(name, "product_handle_code");
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

    public void setProductHandleCode_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda) {
        setProductHandleCode_SignificantTerms("product_handle_code", opLambda, null);
    }

    public void setProductHandleCode_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductHandleCode_SignificantTerms("product_handle_code", opLambda, aggsLambda);
    }

    public void setProductHandleCode_SignificantTerms(String name, ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        SignificantTermsBuilder builder = regSignificantTermsA(name, "product_handle_code");
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

    public void setProductHandleCode_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda) {
        setProductHandleCode_IpRange("product_handle_code", opLambda, null);
    }

    public void setProductHandleCode_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductHandleCode_IpRange("product_handle_code", opLambda, aggsLambda);
    }

    public void setProductHandleCode_IpRange(String name, ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        IPv4RangeBuilder builder = regIpRangeA(name, "product_handle_code");
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

    public void setProductHandleCode_Count(ConditionOptionCall<ValueCountBuilder> opLambda) {
        setProductHandleCode_Count("product_handle_code", opLambda);
    }

    public void setProductHandleCode_Count(String name, ConditionOptionCall<ValueCountBuilder> opLambda) {
        ValueCountBuilder builder = regCountA(name, "product_handle_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductHandleCode_Cardinality() {
        setProductHandleCode_Cardinality(null);
    }

    public void setProductHandleCode_Cardinality(ConditionOptionCall<CardinalityBuilder> opLambda) {
        setProductHandleCode_Cardinality("product_handle_code", opLambda);
    }

    public void setProductHandleCode_Cardinality(String name, ConditionOptionCall<CardinalityBuilder> opLambda) {
        CardinalityBuilder builder = regCardinalityA(name, "product_handle_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductHandleCode_Missing() {
        setProductHandleCode_Missing(null);
    }

    public void setProductHandleCode_Missing(ConditionOptionCall<MissingBuilder> opLambda) {
        setProductHandleCode_Missing("product_handle_code", opLambda, null);
    }

    public void setProductHandleCode_Missing(ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductHandleCode_Missing("product_handle_code", opLambda, aggsLambda);
    }

    public void setProductHandleCode_Missing(String name, ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingBuilder builder = regMissingA(name, "product_handle_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    // String product_name

    public void setProductName_Terms() {
        setProductName_Terms(null);
    }

    public void setProductName_Terms(ConditionOptionCall<TermsBuilder> opLambda) {
        setProductName_Terms("product_name", opLambda, null);
    }

    public void setProductName_Terms(ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductName_Terms("product_name", opLambda, aggsLambda);
    }

    public void setProductName_Terms(String name, ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        TermsBuilder builder = regTermsA(name, "product_name");
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

    public void setProductName_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda) {
        setProductName_SignificantTerms("product_name", opLambda, null);
    }

    public void setProductName_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductName_SignificantTerms("product_name", opLambda, aggsLambda);
    }

    public void setProductName_SignificantTerms(String name, ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        SignificantTermsBuilder builder = regSignificantTermsA(name, "product_name");
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

    public void setProductName_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda) {
        setProductName_IpRange("product_name", opLambda, null);
    }

    public void setProductName_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductName_IpRange("product_name", opLambda, aggsLambda);
    }

    public void setProductName_IpRange(String name, ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        IPv4RangeBuilder builder = regIpRangeA(name, "product_name");
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

    public void setProductName_Count(ConditionOptionCall<ValueCountBuilder> opLambda) {
        setProductName_Count("product_name", opLambda);
    }

    public void setProductName_Count(String name, ConditionOptionCall<ValueCountBuilder> opLambda) {
        ValueCountBuilder builder = regCountA(name, "product_name");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductName_Cardinality() {
        setProductName_Cardinality(null);
    }

    public void setProductName_Cardinality(ConditionOptionCall<CardinalityBuilder> opLambda) {
        setProductName_Cardinality("product_name", opLambda);
    }

    public void setProductName_Cardinality(String name, ConditionOptionCall<CardinalityBuilder> opLambda) {
        CardinalityBuilder builder = regCardinalityA(name, "product_name");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductName_Missing() {
        setProductName_Missing(null);
    }

    public void setProductName_Missing(ConditionOptionCall<MissingBuilder> opLambda) {
        setProductName_Missing("product_name", opLambda, null);
    }

    public void setProductName_Missing(ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductName_Missing("product_name", opLambda, aggsLambda);
    }

    public void setProductName_Missing(String name, ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingBuilder builder = regMissingA(name, "product_name");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    // String product_status

    public void setProductStatus_Terms() {
        setProductStatus_Terms(null);
    }

    public void setProductStatus_Terms(ConditionOptionCall<TermsBuilder> opLambda) {
        setProductStatus_Terms("product_status", opLambda, null);
    }

    public void setProductStatus_Terms(ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductStatus_Terms("product_status", opLambda, aggsLambda);
    }

    public void setProductStatus_Terms(String name, ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        TermsBuilder builder = regTermsA(name, "product_status");
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

    public void setProductStatus_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda) {
        setProductStatus_SignificantTerms("product_status", opLambda, null);
    }

    public void setProductStatus_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductStatus_SignificantTerms("product_status", opLambda, aggsLambda);
    }

    public void setProductStatus_SignificantTerms(String name, ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        SignificantTermsBuilder builder = regSignificantTermsA(name, "product_status");
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

    public void setProductStatus_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda) {
        setProductStatus_IpRange("product_status", opLambda, null);
    }

    public void setProductStatus_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductStatus_IpRange("product_status", opLambda, aggsLambda);
    }

    public void setProductStatus_IpRange(String name, ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        IPv4RangeBuilder builder = regIpRangeA(name, "product_status");
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

    public void setProductStatus_Count(ConditionOptionCall<ValueCountBuilder> opLambda) {
        setProductStatus_Count("product_status", opLambda);
    }

    public void setProductStatus_Count(String name, ConditionOptionCall<ValueCountBuilder> opLambda) {
        ValueCountBuilder builder = regCountA(name, "product_status");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatus_Cardinality() {
        setProductStatus_Cardinality(null);
    }

    public void setProductStatus_Cardinality(ConditionOptionCall<CardinalityBuilder> opLambda) {
        setProductStatus_Cardinality("product_status", opLambda);
    }

    public void setProductStatus_Cardinality(String name, ConditionOptionCall<CardinalityBuilder> opLambda) {
        CardinalityBuilder builder = regCardinalityA(name, "product_status");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatus_Missing() {
        setProductStatus_Missing(null);
    }

    public void setProductStatus_Missing(ConditionOptionCall<MissingBuilder> opLambda) {
        setProductStatus_Missing("product_status", opLambda, null);
    }

    public void setProductStatus_Missing(ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductStatus_Missing("product_status", opLambda, aggsLambda);
    }

    public void setProductStatus_Missing(String name, ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingBuilder builder = regMissingA(name, "product_status");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    // String product_status_code

    public void setProductStatusCode_Terms() {
        setProductStatusCode_Terms(null);
    }

    public void setProductStatusCode_Terms(ConditionOptionCall<TermsBuilder> opLambda) {
        setProductStatusCode_Terms("product_status_code", opLambda, null);
    }

    public void setProductStatusCode_Terms(ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductStatusCode_Terms("product_status_code", opLambda, aggsLambda);
    }

    public void setProductStatusCode_Terms(String name, ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        TermsBuilder builder = regTermsA(name, "product_status_code");
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

    public void setProductStatusCode_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda) {
        setProductStatusCode_SignificantTerms("product_status_code", opLambda, null);
    }

    public void setProductStatusCode_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductStatusCode_SignificantTerms("product_status_code", opLambda, aggsLambda);
    }

    public void setProductStatusCode_SignificantTerms(String name, ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        SignificantTermsBuilder builder = regSignificantTermsA(name, "product_status_code");
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

    public void setProductStatusCode_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda) {
        setProductStatusCode_IpRange("product_status_code", opLambda, null);
    }

    public void setProductStatusCode_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductStatusCode_IpRange("product_status_code", opLambda, aggsLambda);
    }

    public void setProductStatusCode_IpRange(String name, ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        IPv4RangeBuilder builder = regIpRangeA(name, "product_status_code");
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

    public void setProductStatusCode_Count(ConditionOptionCall<ValueCountBuilder> opLambda) {
        setProductStatusCode_Count("product_status_code", opLambda);
    }

    public void setProductStatusCode_Count(String name, ConditionOptionCall<ValueCountBuilder> opLambda) {
        ValueCountBuilder builder = regCountA(name, "product_status_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatusCode_Cardinality() {
        setProductStatusCode_Cardinality(null);
    }

    public void setProductStatusCode_Cardinality(ConditionOptionCall<CardinalityBuilder> opLambda) {
        setProductStatusCode_Cardinality("product_status_code", opLambda);
    }

    public void setProductStatusCode_Cardinality(String name, ConditionOptionCall<CardinalityBuilder> opLambda) {
        CardinalityBuilder builder = regCardinalityA(name, "product_status_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setProductStatusCode_Missing() {
        setProductStatusCode_Missing(null);
    }

    public void setProductStatusCode_Missing(ConditionOptionCall<MissingBuilder> opLambda) {
        setProductStatusCode_Missing("product_status_code", opLambda, null);
    }

    public void setProductStatusCode_Missing(ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setProductStatusCode_Missing("product_status_code", opLambda, aggsLambda);
    }

    public void setProductStatusCode_Missing(String name, ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingBuilder builder = regMissingA(name, "product_status_code");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    // LocalDateTime register_datetime


    public void setRegisterDatetime_DateRange() {
        setRegisterDatetime_DateRange(null);
    }

    public void setRegisterDatetime_DateRange(ConditionOptionCall<DateRangeBuilder> opLambda) {
        setRegisterDatetime_DateRange("register_datetime", opLambda, null);
    }

    public void setRegisterDatetime_DateRange(ConditionOptionCall<DateRangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegisterDatetime_DateRange("register_datetime", opLambda, aggsLambda);
    }

    public void setRegisterDatetime_DateRange(String name, ConditionOptionCall<DateRangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        DateRangeBuilder builder = regDateRangeA(name, "register_datetime");
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

    public void setRegisterDatetime_DateHistogram(ConditionOptionCall<DateHistogramBuilder> opLambda) {
        setRegisterDatetime_DateHistogram("register_datetime", opLambda, null);
    }

    public void setRegisterDatetime_DateHistogram(ConditionOptionCall<DateHistogramBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegisterDatetime_DateHistogram("register_datetime", opLambda, aggsLambda);
    }

    public void setRegisterDatetime_DateHistogram(String name, ConditionOptionCall<DateHistogramBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        DateHistogramBuilder builder = regDateHistogramA(name, "register_datetime");
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

    public void setRegisterDatetime_Count(ConditionOptionCall<ValueCountBuilder> opLambda) {
        setRegisterDatetime_Count("register_datetime", opLambda);
    }

    public void setRegisterDatetime_Count(String name, ConditionOptionCall<ValueCountBuilder> opLambda) {
        ValueCountBuilder builder = regCountA(name, "register_datetime");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_Cardinality() {
        setRegisterDatetime_Cardinality(null);
    }

    public void setRegisterDatetime_Cardinality(ConditionOptionCall<CardinalityBuilder> opLambda) {
        setRegisterDatetime_Cardinality("register_datetime", opLambda);
    }

    public void setRegisterDatetime_Cardinality(String name, ConditionOptionCall<CardinalityBuilder> opLambda) {
        CardinalityBuilder builder = regCardinalityA(name, "register_datetime");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterDatetime_Missing() {
        setRegisterDatetime_Missing(null);
    }

    public void setRegisterDatetime_Missing(ConditionOptionCall<MissingBuilder> opLambda) {
        setRegisterDatetime_Missing("register_datetime", opLambda, null);
    }

    public void setRegisterDatetime_Missing(ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegisterDatetime_Missing("register_datetime", opLambda, aggsLambda);
    }

    public void setRegisterDatetime_Missing(String name, ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingBuilder builder = regMissingA(name, "register_datetime");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    // String register_user

    public void setRegisterUser_Terms() {
        setRegisterUser_Terms(null);
    }

    public void setRegisterUser_Terms(ConditionOptionCall<TermsBuilder> opLambda) {
        setRegisterUser_Terms("register_user", opLambda, null);
    }

    public void setRegisterUser_Terms(ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegisterUser_Terms("register_user", opLambda, aggsLambda);
    }

    public void setRegisterUser_Terms(String name, ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        TermsBuilder builder = regTermsA(name, "register_user");
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

    public void setRegisterUser_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda) {
        setRegisterUser_SignificantTerms("register_user", opLambda, null);
    }

    public void setRegisterUser_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegisterUser_SignificantTerms("register_user", opLambda, aggsLambda);
    }

    public void setRegisterUser_SignificantTerms(String name, ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        SignificantTermsBuilder builder = regSignificantTermsA(name, "register_user");
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

    public void setRegisterUser_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda) {
        setRegisterUser_IpRange("register_user", opLambda, null);
    }

    public void setRegisterUser_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegisterUser_IpRange("register_user", opLambda, aggsLambda);
    }

    public void setRegisterUser_IpRange(String name, ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        IPv4RangeBuilder builder = regIpRangeA(name, "register_user");
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

    public void setRegisterUser_Count(ConditionOptionCall<ValueCountBuilder> opLambda) {
        setRegisterUser_Count("register_user", opLambda);
    }

    public void setRegisterUser_Count(String name, ConditionOptionCall<ValueCountBuilder> opLambda) {
        ValueCountBuilder builder = regCountA(name, "register_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_Cardinality() {
        setRegisterUser_Cardinality(null);
    }

    public void setRegisterUser_Cardinality(ConditionOptionCall<CardinalityBuilder> opLambda) {
        setRegisterUser_Cardinality("register_user", opLambda);
    }

    public void setRegisterUser_Cardinality(String name, ConditionOptionCall<CardinalityBuilder> opLambda) {
        CardinalityBuilder builder = regCardinalityA(name, "register_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegisterUser_Missing() {
        setRegisterUser_Missing(null);
    }

    public void setRegisterUser_Missing(ConditionOptionCall<MissingBuilder> opLambda) {
        setRegisterUser_Missing("register_user", opLambda, null);
    }

    public void setRegisterUser_Missing(ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegisterUser_Missing("register_user", opLambda, aggsLambda);
    }

    public void setRegisterUser_Missing(String name, ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingBuilder builder = regMissingA(name, "register_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    // Integer regular_price
    public void setRegularPrice_Avg() {
        setRegularPrice_Avg(null);
    }

    public void setRegularPrice_Avg(ConditionOptionCall<AvgBuilder> opLambda) {
        setRegularPrice_Avg("regular_price", opLambda);
    }

    public void setRegularPrice_Avg(String name, ConditionOptionCall<AvgBuilder> opLambda) {
        AvgBuilder builder = regAvgA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_Max() {
        setRegularPrice_Max(null);
    }

    public void setRegularPrice_Max(ConditionOptionCall<MaxBuilder> opLambda) {
        setRegularPrice_Max("regular_price", opLambda);
    }

    public void setRegularPrice_Max(String name, ConditionOptionCall<MaxBuilder> opLambda) {
        MaxBuilder builder = regMaxA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }
    public void setRegularPrice_Min() {
        setRegularPrice_Min(null);
    }

    public void setRegularPrice_Min(ConditionOptionCall<MinBuilder> opLambda) {
        setRegularPrice_Min("regular_price", opLambda);
    }

    public void setRegularPrice_Min(String name, ConditionOptionCall<MinBuilder> opLambda) {
        MinBuilder builder = regMinA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_Sum() {
        setRegularPrice_Sum(null);
    }

    public void setRegularPrice_Sum(ConditionOptionCall<SumBuilder> opLambda) {
        setRegularPrice_Sum("regular_price", opLambda);
    }

    public void setRegularPrice_Sum(String name, ConditionOptionCall<SumBuilder> opLambda) {
        SumBuilder builder = regSumA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_ExtendedStats() {
        setRegularPrice_ExtendedStats(null);
    }

    public void setRegularPrice_ExtendedStats(ConditionOptionCall<ExtendedStatsBuilder> opLambda) {
        setRegularPrice_ExtendedStats("regular_price", opLambda);
    }

    public void setRegularPrice_ExtendedStats(String name, ConditionOptionCall<ExtendedStatsBuilder> opLambda) {
        ExtendedStatsBuilder builder = regExtendedStatsA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_Stats() {
        setRegularPrice_Stats(null);
    }

    public void setRegularPrice_Stats(ConditionOptionCall<StatsBuilder> opLambda) {
        setRegularPrice_Stats("regular_price", opLambda);
    }

    public void setRegularPrice_Stats(String name, ConditionOptionCall<StatsBuilder> opLambda) {
        StatsBuilder builder = regStatsA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_Percentiles() {
        setRegularPrice_Percentiles(null);
    }

    public void setRegularPrice_Percentiles(ConditionOptionCall<PercentilesBuilder> opLambda) {
        setRegularPrice_Percentiles("regular_price", opLambda);
    }

    public void setRegularPrice_Percentiles(String name, ConditionOptionCall<PercentilesBuilder> opLambda) {
        PercentilesBuilder builder = regPercentilesA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_PercentileRanks() {
        setRegularPrice_PercentileRanks(null);
    }

    public void setRegularPrice_PercentileRanks(ConditionOptionCall<PercentileRanksBuilder> opLambda) {
        setRegularPrice_PercentileRanks("regular_price", opLambda);
    }

    public void setRegularPrice_PercentileRanks(String name, ConditionOptionCall<PercentileRanksBuilder> opLambda) {
        PercentileRanksBuilder builder = regPercentileRanksA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_Histogram() {
        setRegularPrice_Histogram(null);
    }

    public void setRegularPrice_Histogram(ConditionOptionCall<HistogramBuilder> opLambda) {
        setRegularPrice_Histogram("regular_price", opLambda, null);
    }

    public void setRegularPrice_Histogram(ConditionOptionCall<HistogramBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegularPrice_Histogram("regular_price", opLambda, aggsLambda);
    }

    public void setRegularPrice_Histogram(String name, ConditionOptionCall<HistogramBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        HistogramBuilder builder = regHistogramA(name, "regular_price");
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

    public void setRegularPrice_Range(ConditionOptionCall<RangeBuilder> opLambda) {
        setRegularPrice_Range("regular_price", opLambda, null);
    }

    public void setRegularPrice_Range(ConditionOptionCall<RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegularPrice_Range("regular_price", opLambda, aggsLambda);
    }

    public void setRegularPrice_Range(String name, ConditionOptionCall<RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        RangeBuilder builder = regRangeA(name, "regular_price");
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

    public void setRegularPrice_Count(ConditionOptionCall<ValueCountBuilder> opLambda) {
        setRegularPrice_Count("regular_price", opLambda);
    }

    public void setRegularPrice_Count(String name, ConditionOptionCall<ValueCountBuilder> opLambda) {
        ValueCountBuilder builder = regCountA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_Cardinality() {
        setRegularPrice_Cardinality(null);
    }

    public void setRegularPrice_Cardinality(ConditionOptionCall<CardinalityBuilder> opLambda) {
        setRegularPrice_Cardinality("regular_price", opLambda);
    }

    public void setRegularPrice_Cardinality(String name, ConditionOptionCall<CardinalityBuilder> opLambda) {
        CardinalityBuilder builder = regCardinalityA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setRegularPrice_Missing() {
        setRegularPrice_Missing(null);
    }

    public void setRegularPrice_Missing(ConditionOptionCall<MissingBuilder> opLambda) {
        setRegularPrice_Missing("regular_price", opLambda, null);
    }

    public void setRegularPrice_Missing(ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setRegularPrice_Missing("regular_price", opLambda, aggsLambda);
    }

    public void setRegularPrice_Missing(String name, ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingBuilder builder = regMissingA(name, "regular_price");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    // LocalDateTime update_datetime


    public void setUpdateDatetime_DateRange() {
        setUpdateDatetime_DateRange(null);
    }

    public void setUpdateDatetime_DateRange(ConditionOptionCall<DateRangeBuilder> opLambda) {
        setUpdateDatetime_DateRange("update_datetime", opLambda, null);
    }

    public void setUpdateDatetime_DateRange(ConditionOptionCall<DateRangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setUpdateDatetime_DateRange("update_datetime", opLambda, aggsLambda);
    }

    public void setUpdateDatetime_DateRange(String name, ConditionOptionCall<DateRangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        DateRangeBuilder builder = regDateRangeA(name, "update_datetime");
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

    public void setUpdateDatetime_DateHistogram(ConditionOptionCall<DateHistogramBuilder> opLambda) {
        setUpdateDatetime_DateHistogram("update_datetime", opLambda, null);
    }

    public void setUpdateDatetime_DateHistogram(ConditionOptionCall<DateHistogramBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setUpdateDatetime_DateHistogram("update_datetime", opLambda, aggsLambda);
    }

    public void setUpdateDatetime_DateHistogram(String name, ConditionOptionCall<DateHistogramBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        DateHistogramBuilder builder = regDateHistogramA(name, "update_datetime");
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

    public void setUpdateDatetime_Count(ConditionOptionCall<ValueCountBuilder> opLambda) {
        setUpdateDatetime_Count("update_datetime", opLambda);
    }

    public void setUpdateDatetime_Count(String name, ConditionOptionCall<ValueCountBuilder> opLambda) {
        ValueCountBuilder builder = regCountA(name, "update_datetime");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_Cardinality() {
        setUpdateDatetime_Cardinality(null);
    }

    public void setUpdateDatetime_Cardinality(ConditionOptionCall<CardinalityBuilder> opLambda) {
        setUpdateDatetime_Cardinality("update_datetime", opLambda);
    }

    public void setUpdateDatetime_Cardinality(String name, ConditionOptionCall<CardinalityBuilder> opLambda) {
        CardinalityBuilder builder = regCardinalityA(name, "update_datetime");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateDatetime_Missing() {
        setUpdateDatetime_Missing(null);
    }

    public void setUpdateDatetime_Missing(ConditionOptionCall<MissingBuilder> opLambda) {
        setUpdateDatetime_Missing("update_datetime", opLambda, null);
    }

    public void setUpdateDatetime_Missing(ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setUpdateDatetime_Missing("update_datetime", opLambda, aggsLambda);
    }

    public void setUpdateDatetime_Missing(String name, ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingBuilder builder = regMissingA(name, "update_datetime");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            ProductCA ca = new ProductCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    // String update_user

    public void setUpdateUser_Terms() {
        setUpdateUser_Terms(null);
    }

    public void setUpdateUser_Terms(ConditionOptionCall<TermsBuilder> opLambda) {
        setUpdateUser_Terms("update_user", opLambda, null);
    }

    public void setUpdateUser_Terms(ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setUpdateUser_Terms("update_user", opLambda, aggsLambda);
    }

    public void setUpdateUser_Terms(String name, ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        TermsBuilder builder = regTermsA(name, "update_user");
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

    public void setUpdateUser_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda) {
        setUpdateUser_SignificantTerms("update_user", opLambda, null);
    }

    public void setUpdateUser_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setUpdateUser_SignificantTerms("update_user", opLambda, aggsLambda);
    }

    public void setUpdateUser_SignificantTerms(String name, ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        SignificantTermsBuilder builder = regSignificantTermsA(name, "update_user");
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

    public void setUpdateUser_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda) {
        setUpdateUser_IpRange("update_user", opLambda, null);
    }

    public void setUpdateUser_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setUpdateUser_IpRange("update_user", opLambda, aggsLambda);
    }

    public void setUpdateUser_IpRange(String name, ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        IPv4RangeBuilder builder = regIpRangeA(name, "update_user");
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

    public void setUpdateUser_Count(ConditionOptionCall<ValueCountBuilder> opLambda) {
        setUpdateUser_Count("update_user", opLambda);
    }

    public void setUpdateUser_Count(String name, ConditionOptionCall<ValueCountBuilder> opLambda) {
        ValueCountBuilder builder = regCountA(name, "update_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_Cardinality() {
        setUpdateUser_Cardinality(null);
    }

    public void setUpdateUser_Cardinality(ConditionOptionCall<CardinalityBuilder> opLambda) {
        setUpdateUser_Cardinality("update_user", opLambda);
    }

    public void setUpdateUser_Cardinality(String name, ConditionOptionCall<CardinalityBuilder> opLambda) {
        CardinalityBuilder builder = regCardinalityA(name, "update_user");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setUpdateUser_Missing() {
        setUpdateUser_Missing(null);
    }

    public void setUpdateUser_Missing(ConditionOptionCall<MissingBuilder> opLambda) {
        setUpdateUser_Missing("update_user", opLambda, null);
    }

    public void setUpdateUser_Missing(ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        setUpdateUser_Missing("update_user", opLambda, aggsLambda);
    }

    public void setUpdateUser_Missing(String name, ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsProductCA> aggsLambda) {
        MissingBuilder builder = regMissingA(name, "update_user");
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
