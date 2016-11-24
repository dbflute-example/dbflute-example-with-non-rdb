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
package org.docksidestage.solr.bsbhv;

import javax.annotation.PostConstruct;
import org.dbflute.solr.bhv.AbstractSolrBehavior;
import org.lastaflute.core.util.ContainerUtil;

import org.docksidestage.solr.cbean.SolrExampleCB;
import org.docksidestage.solr.exentity.SolrExample;
import org.docksidestage.solr.exentity.index.SolrExampleIndex;

/**
 * Base Behavior class of Solr schema "Example."
 * @author FreeGen
 */
public abstract class SolrBsExampleBhv extends AbstractSolrBehavior<SolrExample, SolrExampleIndex, SolrExampleCB> {

    @PostConstruct
    public void init() {
        org.docksidestage.mylasta.direction.NonrdbConfig config = getConfig();
        masterSolrClient = createHttpSolrClient(config.getSolrExampleUrl());
        slaveSolrClient = createHttpSolrClient(config.getSolrExampleUrl());
    }

    @Override
    public SolrExampleCB newConditionBean() {
        return new SolrExampleCB();
    }

    @Override
    public Class<SolrExample> getEntityClass() {
        return SolrExample.class;
    }

    @Override
    public int getConnectionTimeout() {
        return getConfig().getSolrConnectionTimeoutAsInteger();
    }

    @Override
    public int getSocketTimeout() {
        return getConfig().getSolrSocketTimeoutAsInteger();
    }

    @Override
    public int getDefaultMaxConnectionsPerHost() {
        return getConfig().getSolrDefaultMaxConnectionsPerHostAsInteger();
    }

    @Override
    public int getMaxTotalConnections() {
        return getConfig().getSolrMaxTotalConnectionsAsInteger();
    }

    @Override
    public int getAliveCheckInterval() {
        return getConfig().getSolrAliveCheckIntervalAsInteger();
    }

    protected org.docksidestage.mylasta.direction.NonrdbConfig getConfig() {
        return ContainerUtil.getComponent(org.docksidestage.mylasta.direction.NonrdbConfig.class);
    }
}
