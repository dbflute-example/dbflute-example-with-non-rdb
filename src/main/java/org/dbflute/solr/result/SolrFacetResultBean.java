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
package org.dbflute.solr.result;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.response.FacetField;

/**
 * The solr facet result bean of paging.
 * @author FreeGen
 * @param <ENTITY> entity
 */
public class SolrFacetResultBean<ENTITY> extends SolrPagingResultBean<ENTITY> implements SolrResultBean {

    private static final long serialVersionUID = 1L;

    /** facet result */
    private Map<String, Integer> facetResult;

    /** facet field list */
    private List<FacetField> facetFieldList;

    /**
     * ファセット結果を取得します。
     * @return ファセット結果
     */
    public Map<String, Integer> getFacetResult() {
        return facetResult;
    }

    /**
     * ファセット結果を設定します。
     * @param facetResult ファセット結果
     */
    public void setFacetResult(Map<String, Integer> facetResult) {
        this.facetResult = facetResult;
    }

    /**
     * ファセット結果を取得します。
     * @return ファセット結果
     */
    public List<FacetField> getFacetFieldList() {
        return facetFieldList;
    }

    /**
     * ファセット結果を設定します。
     * @param facetFieldList ファセット結果
     */
    public void setFacetFieldList(List<FacetField> facetFieldList) {
        this.facetFieldList = facetFieldList;
    }
}
