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
package org.docksidestage.esflute.maihama.allcommon;

import org.dbflute.cbean.result.PagingResultBean;
import org.elasticsearch.action.search.SearchRequestBuilder;

/**
 * @param <ENTITY> The type of entity.
 * @author ESFlute (using FreeGen)
 */
public class EsPagingResultBean<ENTITY> extends PagingResultBean<ENTITY> {

    private static final long serialVersionUID = 1L;

    protected long took;
    private int totalShards;
    private int successfulShards;
    private int failedShards;
    private SearchRequestBuilder builder;

    public EsPagingResultBean(final SearchRequestBuilder builder) {
        this.builder = builder;
    }

    public String getQueryDsl() {
        return builder.toString();
    }

    public long getTook() {
        return took;
    }

    public void setTook(long took) {
        this.took = took;
    }

    public int getTotalShards() {
        return totalShards;
    }

    public void setTotalShards(int totalShards) {
        this.totalShards = totalShards;
    }

    public int getSuccessfulShards() {
        return successfulShards;
    }

    public void setSuccessfulShards(int successfulShards) {
        this.successfulShards = successfulShards;
    }

    public int getFailedShards() {
        return failedShards;
    }

    public void setFailedShards(int failedShards) {
        this.failedShards = failedShards;
    }
}

