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
package org.dbflute.solr.result;

import org.dbflute.cbean.result.PagingResultBean;

/**
 * The solr result bean of paging.
 * @author FreeGen
 * @param <ENTITY> entity
 */
public class SolrPagingResultBean<ENTITY> extends PagingResultBean<ENTITY> implements SolrResultBean {

    private static final long serialVersionUID = 1L;

    /** クエリー実行時間 */
    private long queryTime;

    /** 実際に発行されたクエリー文字列 */
    private String query;

    /**
     * クエリー実行時間を取得します。
     * @return クエリー実行時間
     */
    public long getQueryTime() {
        return queryTime;
    }

    /**
     * クエリー実行時間を設定します。
     * @param queryTime クエリー実行時間
     */
    public void setQueryTime(long queryTime) {
        this.queryTime = queryTime;
    }

    /**
     * 実際に発行されたクエリー文字列を取得します。
     * @return 実際に発行されたクエリー文字列
     */
    @Override
    public String getQueryString() {
        return query;
    }

    /**
     * 実際に発行されたクエリー文字列を設定します。
     * @param query 実際に発行されたクエリー文字列
     */
    @Override
    public void setQueryString(String query) {
        this.query = query;
    }
}
