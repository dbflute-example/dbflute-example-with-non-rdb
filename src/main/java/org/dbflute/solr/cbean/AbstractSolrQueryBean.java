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
package org.dbflute.solr.cbean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.dbflute.solr.entity.dbmeta.SolrDBMeta;

/**
 * @author FreeGen
 */
public abstract class AbstractSolrQueryBean implements SolrQueryBean {

    protected List<String> nestQueryList;

    protected String dismaxQuery;

    /**
     * セット範囲検索のレンジは5桁でパディングされています
     *
     * 言語CD →  EN_XXXXA
     */
    protected static final int SET_RANGE_SEARCH_PADDING_LENGTH = 5;

    /**
     * セット範囲検索のレンジパディング文字列
     */
    protected static final String PADDING_STR = "0";

    /**
     * ソート条件保存用BeanList
     */
    protected List<SortClause> solrSortClauseList = new ArrayList<SortClause>();

    @Override
    public String getQueryString() {
        return getQueryStr();
    }

    /**
     * クエリ文字列取得
     * @return クエリ文字列
     */
    public String getQueryStr() {
        return SolrQueryBuilder.concatEachCondition(getQueryList());
    }

    @Override
    public String getMinimumQueryString() {
        if (getQueryList() != null && getQueryList().size() > 0) {
            return getQueryList().get(0);
        }
        return getQueryStr();
    }

    public void addNestQuery(SolrQueryBean nestQuery, SolrQueryLogicalOperator operator) {
        getNestQueryList().add(SolrQueryBuilder.wrapGroupQuery(SolrQueryBuilder.concatEachCondition(nestQuery.getQueryList(), operator)));
    }

    @Override
    public List<String> getNestQueryList() {
        if (nestQueryList == null) {
            nestQueryList = new ArrayList<String>();
        }
        return nestQueryList;
    }

    @Override
    public boolean isUseSort() {
        return !this.solrSortClauseList.isEmpty();
    }

    @Override
    public List<SortClause> getSolrSortClauseList() {
        return solrSortClauseList;
    }

    @Override
    public void dismax(String query, SolrQFCall qfLambda) {
        Map<SolrDBMeta, Integer> qf = new LinkedHashMap<SolrDBMeta, Integer>();
        qfLambda.callback(qf);
        dismaxQuery = SolrQueryBuilder.dismax(query, qf);
    }
}
