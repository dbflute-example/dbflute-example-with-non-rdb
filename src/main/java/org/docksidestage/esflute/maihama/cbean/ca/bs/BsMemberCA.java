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
import org.docksidestage.esflute.maihama.cbean.ca.MemberCA;
import org.docksidestage.esflute.maihama.cbean.cq.MemberCQ;
import org.docksidestage.esflute.maihama.cbean.cq.bs.BsMemberCQ;

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

    public void global(String name, ConditionOptionCall<GlobalAggregationBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        GlobalAggregationBuilder builder = regGlobalA(name);
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


    public void setAccount_Terms() {
        setAccount_Terms(null);
    }

    public void setAccount_Terms(ConditionOptionCall<TermsAggregationBuilder> opLambda) {
        setAccount_Terms("account", opLambda, null);
    }

    public void setAccount_Terms(ConditionOptionCall<TermsAggregationBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        setAccount_Terms("account", opLambda, aggsLambda);
    }

    public void setAccount_Terms(String name, ConditionOptionCall<TermsAggregationBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        TermsAggregationBuilder builder = regTermsA(name, "account");
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

    public void setAccount_SignificantTerms(ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda) {
        setAccount_SignificantTerms("account", opLambda, null);
    }

    public void setAccount_SignificantTerms(ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        setAccount_SignificantTerms("account", opLambda, aggsLambda);
    }

    public void setAccount_SignificantTerms(String name, ConditionOptionCall<SignificantTermsAggregationBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        SignificantTermsAggregationBuilder builder = regSignificantTermsA(name, "account");
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

    public void setAccount_IpRange(ConditionOptionCall<IpRangeAggregationBuilder> opLambda) {
        setAccount_IpRange("account", opLambda, null);
    }

    public void setAccount_IpRange(ConditionOptionCall<IpRangeAggregationBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        setAccount_IpRange("account", opLambda, aggsLambda);
    }

    public void setAccount_IpRange(String name, ConditionOptionCall<IpRangeAggregationBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        IpRangeAggregationBuilder builder = regIpRangeA(name, "account");
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

    public void setAccount_Count(ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        setAccount_Count("account", opLambda);
    }

    public void setAccount_Count(String name, ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        ValueCountAggregationBuilder builder = regCountA(name, "account");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setAccount_Cardinality() {
        setAccount_Cardinality(null);
    }

    public void setAccount_Cardinality(ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        setAccount_Cardinality("account", opLambda);
    }

    public void setAccount_Cardinality(String name, ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        CardinalityAggregationBuilder builder = regCardinalityA(name, "account");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setAccount_Missing() {
        setAccount_Missing(null);
    }

    public void setAccount_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda) {
        setAccount_Missing("account", opLambda, null);
    }

    public void setAccount_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        setAccount_Missing("account", opLambda, aggsLambda);
    }

    public void setAccount_Missing(String name, ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        MissingAggregationBuilder builder = regMissingA(name, "account");
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

    public void setName_Count(ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        setName_Count("name", opLambda);
    }

    public void setName_Count(String name, ConditionOptionCall<ValueCountAggregationBuilder> opLambda) {
        ValueCountAggregationBuilder builder = regCountA(name, "name");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setName_Cardinality() {
        setName_Cardinality(null);
    }

    public void setName_Cardinality(ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        setName_Cardinality("name", opLambda);
    }

    public void setName_Cardinality(String name, ConditionOptionCall<CardinalityAggregationBuilder> opLambda) {
        CardinalityAggregationBuilder builder = regCardinalityA(name, "name");
        if (opLambda != null) {
            opLambda.callback(builder);
        }
    }

    public void setName_Missing() {
        setName_Missing(null);
    }

    public void setName_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda) {
        setName_Missing("name", opLambda, null);
    }

    public void setName_Missing(ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        setName_Missing("name", opLambda, aggsLambda);
    }

    public void setName_Missing(String name, ConditionOptionCall<MissingAggregationBuilder> opLambda, OperatorCall<BsMemberCA> aggsLambda) {
        MissingAggregationBuilder builder = regMissingA(name, "name");
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
