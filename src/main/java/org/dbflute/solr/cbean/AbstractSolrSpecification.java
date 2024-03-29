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
import java.util.List;
import java.util.stream.Collectors;

import org.dbflute.solr.entity.dbmeta.SolrDBMeta;

/**
 * @author FreeGen
 */
public abstract class AbstractSolrSpecification implements SolrSpecification {

    private final List<SolrDBMeta> spcifyFieldList = new ArrayList<SolrDBMeta>();

    protected boolean _scoreEnabled;

    protected void addSpecifyField(SolrDBMeta solrMeta) {
        this.spcifyFieldList.add(solrMeta);
    }

    protected void clearSpecify() {
        this.spcifyFieldList.clear();
    }

    @Override
    public boolean isSpecify() {
        return !spcifyFieldList.isEmpty();
    }

    @Override
    public String[] getSpecifyPropertys() {
        return spcifyFieldList.stream().map(spcifyField -> spcifyField.propertyName()).toArray(String[]::new);
    }

    @Override
    public String[] getSpecifyFields() {
        return spcifyFieldList.stream().map(spcifyField -> spcifyField.fieldName()).toArray(String[]::new);
    }

    public void enableScore() {
        _scoreEnabled = true;
    }

    public void disableScore() {
        _scoreEnabled = false;
    }

    @Override
    public boolean isScoreEnable() {
        return _scoreEnabled;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" { ");
        sb.append(spcifyFieldList.stream().map(solrDBMeta -> solrDBMeta.toString()).collect(Collectors.joining(",")));
        if (this.isScoreEnable()) {
            if (!spcifyFieldList.isEmpty()) {
                sb.append(",");
            }
            sb.append("score");
        }
        sb.append(" } ");

        return sb.toString();
    }
}
