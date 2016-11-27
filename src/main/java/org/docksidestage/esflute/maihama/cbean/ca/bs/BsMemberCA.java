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
import org.docksidestage.esflute.maihama.cbean.ca.MemberCA;
import org.docksidestage.esflute.maihama.cbean.cq.MemberCQ;
import org.docksidestage.esflute.maihama.cbean.cq.bs.BsMemberCQ;

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
public abstract class BsMemberCA extends EsAbstractConditionAggregation {

    // ===================================================================================
    //                                                                     Aggregation Set
    //                                                                           =========

    public void filter(String name, EsAbstractConditionQuery.OperatorCall<BsMemberCQ> queryLambda,
            ConditionOptionCall<FilterAggregationBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        MemberCQ cq = new MemberCQ();
        if (queryLambda != null) {
            queryLambda.callback(cq);
        }
        FilterAggregationBuilder builder = regFilterA(name, cq.getQuery());
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            MemberCA ca = new MemberCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void global(String name, ConditionOptionCall<GlobalBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        GlobalBuilder builder = regGlobalA(name);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            MemberCA ca = new MemberCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void sampler(String name, ConditionOptionCall<SamplerAggregationBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        SamplerAggregationBuilder builder = regSamplerA(name);
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            MemberCA ca = new MemberCA();
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

    // String account

    public void setAccount_Terms() {
        setAccount_Terms(null);
    }

    public void setAccount_Terms(ConditionOptionCall<TermsBuilder> opLambda) {
        setAccount_Terms("account", opLambda, null);
    }

    public void setAccount_Terms(ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        setAccount_Terms("account", opLambda, aggsLambda);
    }

    public void setAccount_Terms(String name, ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        TermsBuilder builder = regTermsA(name, "account");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            MemberCA ca = new MemberCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setAccount_SignificantTerms() {
        setAccount_SignificantTerms(null);
    }

    public void setAccount_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda) {
        setAccount_SignificantTerms("account", opLambda, null);
    }

    public void setAccount_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        setAccount_SignificantTerms("account", opLambda, aggsLambda);
    }

    public void setAccount_SignificantTerms(String name, ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        SignificantTermsBuilder builder = regSignificantTermsA(name, "account");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            MemberCA ca = new MemberCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setAccount_IpRange() {
        setAccount_IpRange(null);
    }

    public void setAccount_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda) {
        setAccount_IpRange("account", opLambda, null);
    }

    public void setAccount_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        setAccount_IpRange("account", opLambda, aggsLambda);
    }

    public void setAccount_IpRange(String name, ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        IPv4RangeBuilder builder = regIpRangeA(name, "account");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            MemberCA ca = new MemberCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }


    public void setAccount_Count() {
        setAccount_Count(null);
    }

    public void setAccount_Count(ConditionOptionCall<ValueCountBuilder> opLambda) {
        setAccount_Count("account", opLambda);
    }

    public void setAccount_Count(String name, ConditionOptionCall<ValueCountBuilder> opLambda) {
        ValueCountBuilder builder = regCountA(name, "account");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setAccount_Cardinality() {
        setAccount_Cardinality(null);
    }

    public void setAccount_Cardinality(ConditionOptionCall<CardinalityBuilder> opLambda) {
        setAccount_Cardinality("account", opLambda);
    }

    public void setAccount_Cardinality(String name, ConditionOptionCall<CardinalityBuilder> opLambda) {
        CardinalityBuilder builder = regCardinalityA(name, "account");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setAccount_Missing() {
        setAccount_Missing(null);
    }

    public void setAccount_Missing(ConditionOptionCall<MissingBuilder> opLambda) {
        setAccount_Missing("account", opLambda, null);
    }

    public void setAccount_Missing(ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        setAccount_Missing("account", opLambda, aggsLambda);
    }

    public void setAccount_Missing(String name, ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        MissingBuilder builder = regMissingA(name, "account");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            MemberCA ca = new MemberCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    // String name

    public void setName_Terms() {
        setName_Terms(null);
    }

    public void setName_Terms(ConditionOptionCall<TermsBuilder> opLambda) {
        setName_Terms("name", opLambda, null);
    }

    public void setName_Terms(ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        setName_Terms("name", opLambda, aggsLambda);
    }

    public void setName_Terms(String name, ConditionOptionCall<TermsBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        TermsBuilder builder = regTermsA(name, "name");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            MemberCA ca = new MemberCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setName_SignificantTerms() {
        setName_SignificantTerms(null);
    }

    public void setName_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda) {
        setName_SignificantTerms("name", opLambda, null);
    }

    public void setName_SignificantTerms(ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        setName_SignificantTerms("name", opLambda, aggsLambda);
    }

    public void setName_SignificantTerms(String name, ConditionOptionCall<SignificantTermsBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        SignificantTermsBuilder builder = regSignificantTermsA(name, "name");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            MemberCA ca = new MemberCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

    public void setName_IpRange() {
        setName_IpRange(null);
    }

    public void setName_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda) {
        setName_IpRange("name", opLambda, null);
    }

    public void setName_IpRange(ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        setName_IpRange("name", opLambda, aggsLambda);
    }

    public void setName_IpRange(String name, ConditionOptionCall<IPv4RangeBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        IPv4RangeBuilder builder = regIpRangeA(name, "name");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            MemberCA ca = new MemberCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }


    public void setName_Count() {
        setName_Count(null);
    }

    public void setName_Count(ConditionOptionCall<ValueCountBuilder> opLambda) {
        setName_Count("name", opLambda);
    }

    public void setName_Count(String name, ConditionOptionCall<ValueCountBuilder> opLambda) {
        ValueCountBuilder builder = regCountA(name, "name");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setName_Cardinality() {
        setName_Cardinality(null);
    }

    public void setName_Cardinality(ConditionOptionCall<CardinalityBuilder> opLambda) {
        setName_Cardinality("name", opLambda);
    }

    public void setName_Cardinality(String name, ConditionOptionCall<CardinalityBuilder> opLambda) {
        CardinalityBuilder builder = regCardinalityA(name, "name");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setName_Missing() {
        setName_Missing(null);
    }

    public void setName_Missing(ConditionOptionCall<MissingBuilder> opLambda) {
        setName_Missing("name", opLambda, null);
    }

    public void setName_Missing(ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        setName_Missing("name", opLambda, aggsLambda);
    }

    public void setName_Missing(String name, ConditionOptionCall<MissingBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        MissingBuilder builder = regMissingA(name, "name");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
        if (aggsLambda != null) {
            MemberCA ca = new MemberCA();
            aggsLambda.callback(ca);
            ca.getAggregationBuilderList().forEach(builder::subAggregation);
        }
    }

}
