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
package org.dbflute.solr.bhv;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.LBHttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.dbflute.bhv.exception.BehaviorExceptionThrower;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.solr.cbean.SolrCBCall;
import org.dbflute.solr.cbean.SolrConditionBean;
import org.dbflute.solr.entity.AbstractSolrIndexEntity;
import org.dbflute.solr.entity.SolrEntity;
import org.dbflute.solr.exception.SolrException;
import org.dbflute.solr.result.SolrFacetResultBean;
import org.dbflute.solr.result.SolrPagingResultBean;
import org.dbflute.util.DfCollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The abstract class of behavior.
 * @param <ENTITY> The type of entity handled by this behavior.
 * @param <INDEX> The type of index entity handled by this behavior.
 * @param <CB> The type of condition-bean handled by this behavior.
 * @author FreeGen
 */
public abstract class AbstractSolrBehavior<ENTITY extends SolrEntity, INDEX extends AbstractSolrIndexEntity, CB extends SolrConditionBean> {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final Logger logger = LoggerFactory.getLogger(AbstractSolrBehavior.class);

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** Master client */
    protected SolrClient masterSolrClient;

    /** Slave client */
    protected SolrClient slaveSolrClient;

    public abstract CB newConditionBean();

    public abstract Class<ENTITY> getEntityClass();

    public SolrClient getMasterSolrClient() {
        return masterSolrClient;
    }

    public SolrClient getSlaveSolrClient() {
        return slaveSolrClient;
    }

    /**
     * Retrieve count of the number of entities matched to the specified query condition.
     * @param cbLambda ConditionBean callback function (NotNull)
     * @return Total number of entities matched to query condition
     */
    public int selectCount(SolrCBCall<CB> cbLambda) {
        CB cb = createCB(cbLambda);
        SolrQuery query = cb.buildSolrQuery();
        query.setStart(0);
        query.setRows(0);

        if (logger.isDebugEnabled()) {
            logger.debug("query={}", query.toQueryString());
        }

        try {
            QueryResponse rsp = getSlaveSolrClient().query(query, METHOD.POST);
            return (int) rsp.getResults().getNumFound();
        } catch (SolrServerException | IOException e) {
            throw new SolrException("Failed to Solr of access.", e);
        }
    }

    /**
     * Retrieve entities by paging-search.
     * @param cbLambda ConditionBean callback function (NotNull)
     * @return List of the result entities (NotNull)
     */
    public SolrPagingResultBean<ENTITY> selectPage(SolrCBCall<CB> cbLambda) {
        return selectPage(cbLambda, getEntityClass());
    }

    /**
     * Retrieve entities by paging-search.
     * @param cbLambda ConditionBean callback function (NotNull)
     * @param clazz BEAN class for the result search (NotNull)
     * @param <RESULT_BEAN> BEAN for the result of search
     * @return List of the result entities (NotNull)
     */
    public <RESULT_BEAN> SolrPagingResultBean<RESULT_BEAN> selectPage(SolrCBCall<CB> cbLambda, Class<RESULT_BEAN> clazz) {
        CB cb = createCB(cbLambda);
        SolrQuery query = cb.buildSolrQuery();

        if (logger.isDebugEnabled()) {
            logger.debug("query={}", query.toQueryString());
        }

        try {
            QueryResponse rsp = getSlaveSolrClient().query(query, METHOD.POST);

            SolrPagingResultBean<RESULT_BEAN> pagingResult = new SolrPagingResultBean<RESULT_BEAN>();
            List<RESULT_BEAN> beans = rsp.getBeans(clazz);
            if (SolrEntity.class.isAssignableFrom(clazz)) {
                beans.forEach(bean -> {
                    SolrEntity entity = (SolrEntity) bean;
                    entity.markAsSelect();
                    if (cb.specify().isSpecify()) {
                        entity.modifiedToSpecified(cb.getSpecifyPropertys());
                    }
                });
            }

            pagingResult.setSelectedList(beans);
            int numFound = (int) rsp.getResults().getNumFound();
            pagingResult.setAllRecordCount(numFound);
            pagingResult.setPageSize(cb.getFetchSize() == null ? numFound : cb.getFetchSize());
            pagingResult.setCurrentPageNumber(cb.getPageNumer());
            pagingResult.setQueryTime(rsp.getQTime());
            pagingResult.setQueryString(query.toString());
            return pagingResult;
        } catch (SolrServerException | IOException e) {
            throw new SolrException("Failed to Solr of access.", e);
        }
    }

    /**
     * Retrieve an entity matched to the specified query condition.
     * @param cbLambda ConditionBean callback function (NotNull)
     * @return Result entity (NotNull)
     */
    public OptionalEntity<ENTITY> selectFirst(SolrCBCall<CB> cbLambda) {
        return selectFirst(cbLambda, getEntityClass());
    }

    /**
     * Retrieve an entity matched to the specified query condition.
     * @param cbLambda ConditionBean callback function (NotNull)
     * @param clazz BEAN class for the result search (NotNull)
     * @param <RESULT_BEAN> BEAN for the result of search
     * @return Result entity (NotNull)
     */
    public <RESULT_BEAN> OptionalEntity<RESULT_BEAN> selectFirst(SolrCBCall<CB> cbLambda, Class<RESULT_BEAN> clazz) {
        return createOptionalEntity(selectPage(cb -> {
            cbLambda.callback(cb);
            cb.fetchFirst(1);
        }, clazz).stream().findFirst().orElse(null), createCB(cb -> {
            cbLambda.callback(cb);
            cb.fetchFirst(1);
        }));
    }

    /**
     * Retrieve entities by faceted search.
     * @param cbLambda ConditionBean callback function
     * @return List of faceted search result (NotNull)
     */
    public SolrFacetResultBean<ENTITY> selectFacetQuery(SolrCBCall<CB> cbLambda) {
        CB cb = createCB(cbLambda);
        SolrQuery query = cb.buildSolrQuery();

        if (logger.isDebugEnabled()) {
            logger.debug("query={}", query.toQueryString());
        }

        try {
            QueryResponse rsp = getSlaveSolrClient().query(query, METHOD.POST);

            List<ENTITY> beans = rsp.getBeans(getEntityClass());
            beans.forEach(bean -> {
                bean.markAsSelect();
                if (cb.specify().isSpecify()) {
                    bean.modifiedToSpecified(cb.getSpecifyPropertys());
                }
            });
            SolrFacetResultBean<ENTITY> resultBean = new SolrFacetResultBean<ENTITY>();
            resultBean.setSelectedList(beans);
            int numFound = (int) rsp.getResults().getNumFound();
            resultBean.setAllRecordCount(numFound);
            resultBean.setPageSize(cb.getFetchSize() == null ? numFound : cb.getFetchSize());
            resultBean.setCurrentPageNumber(cb.getPageNumer());
            resultBean.setQueryString(query.toString());
            resultBean.setQueryTime(rsp.getQTime());

            resultBean.setFacetResult(rsp.getFacetQuery());
            resultBean.setFacetFieldList(rsp.getFacetFields());

            return resultBean;
        } catch (SolrServerException | IOException e) {
            throw new SolrException("Failed to Solr of access.", e);
        }
    }

    /**
     * Delete all index.
     */
    public void deleteAllIndex() {
        try {
            getMasterSolrClient().deleteByQuery("*:*");
        } catch (SolrServerException | IOException e) {
            throw new SolrException("Failed to Solr of access.", e);
        }
    }

    /**
     * Delete index associated with the assigned ID.
     * @param id ID for an index to be deleted
     */
    public void deleteById(Object id) {
        try {
            getMasterSolrClient().deleteByQuery("id:" + id);
        } catch (SolrServerException | IOException e) {
            throw new SolrException("Failed to Solr of access.", e);
        }
    }

    /**
     * Add index entity list.
     * @param indexEntityList Index entity list (NullAllowed: do nothing if null)
     */
    public void addSolrIndexEntity(List<INDEX> indexEntityList) {
        if (indexEntityList == null || indexEntityList.isEmpty()) {
            return;
        }
        try {
            getMasterSolrClient().addBeans(indexEntityList);
        } catch (SolrServerException | IOException e) {
            throw new SolrException("Failed to Solr of access.", e);
        }
    }

    /**
     * Commit.
     */
    public void commit() {
        try {
            getMasterSolrClient().commit();
        } catch (SolrServerException | IOException e) {
            throw new SolrException("Failed to Solr of access.", e);
        }
    }

    /**
     * Optimize Solr.
     */
    public void optimize() {
        try {
            // Do not wait until the optimization prosess has finished.
            getMasterSolrClient().optimize(false, false);
        } catch (SolrServerException | IOException e) {
            throw new SolrException("failed Solr Optimize.", e);
        }
    }

    public abstract int getConnectionTimeout();

    public abstract int getSocketTimeout();

    public abstract int getDefaultMaxConnectionsPerHost();

    public abstract int getMaxTotalConnections();

    public abstract int getAliveCheckInterval();

    /**
     * Create CondetionBean from the assigned callback function.
     * @param cbLambda ConditionBean callback function (NotNull)
     * @return ConditionBean
     */
    protected CB createCB(SolrCBCall<CB> cbLambda) {
        if (cbLambda == null) {
            throw new SolrException("cbLambda is null.");
        }
        CB cb = newConditionBean();
        cbLambda.callback(cb);
        return cb;
    }

    /**
     * Create HttpSolrClient associated with the assigned URL.
     * @param url URL (NotNull)
     * @return HttpSolrClient (NotNull)
     */
    protected HttpSolrClient createHttpSolrClient(String url) {
        HttpSolrClient.Builder builder = new HttpSolrClient.Builder(url);
        builder.withHttpClient(createHttpClient());
        builder.withConnectionTimeout(getConnectionTimeout());
        builder.withSocketTimeout(getSocketTimeout());
        return builder.build();
    }

    /**
     * Create CloudSolrClient associated with the assigned host.
     * @param host Host (NotNull)
     * @return CloudSolrClient (NotNull)
     */
    protected CloudSolrClient createCloudSolrClient(String host) {
        CloudSolrClient.Builder builder = new CloudSolrClient.Builder(DfCollectionUtil.newArrayList(host), Optional.empty());
        builder.withHttpClient(createHttpClient());
        builder.withConnectionTimeout(getConnectionTimeout());
        builder.withSocketTimeout(getSocketTimeout());
        CloudSolrClient client = builder.build();
        client.setZkConnectTimeout(getConnectionTimeout());
        return client;
    }

    /**
     * Create LBHttpSolrClient associated with the assigned list of URLs.
     * @param urlList List of URL (NotNull)
     * @return LBHttpSolrClient (NotNull)
     */
    protected LBHttpSolrClient createLBHttpSolrClient(List<String> urlList) {
        LBHttpSolrClient.Builder builder = new LBHttpSolrClient.Builder();
        builder.withBaseSolrUrls(urlList.toArray(new String[] {}));
        builder.withHttpClient(createHttpClient());
        builder.withConnectionTimeout(getConnectionTimeout());
        builder.withSocketTimeout(getSocketTimeout());
        LBHttpSolrClient client = builder.build();
        client.setAliveCheckInterval(getAliveCheckInterval());
        return client;
    }

    /**
     * Create HTTP client.
     * @return HTTP client
     */
    protected HttpClient createHttpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(getMaxTotalConnections());
        connectionManager.setDefaultMaxPerRoute(getDefaultMaxConnectionsPerHost());
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setConnectionManager(connectionManager);
        CloseableHttpClient httpClient = builder.build();
        return httpClient;
    }

    protected <RESULT> OptionalEntity<RESULT> createOptionalEntity(RESULT entity, Object... searchKey) {
        if (entity != null) {
            return OptionalEntity.of(entity);
        } else { // not serializable here because of select entity (while relation optional is able)
            return OptionalEntity.ofNullable(entity, () -> throwSelectEntityAlreadyDeletedException(searchKey));
        }
    }

    protected void throwSelectEntityAlreadyDeletedException(Object searchKey) {
        createBhvExThrower().throwSelectEntityAlreadyDeletedException(searchKey);
    }

    protected BehaviorExceptionThrower createBhvExThrower() {
        return new BehaviorExceptionThrower();
    }
}
