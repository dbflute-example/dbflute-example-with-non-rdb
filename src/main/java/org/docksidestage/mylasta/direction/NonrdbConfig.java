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
package org.docksidestage.mylasta.direction;

import org.lastaflute.core.direction.exception.ConfigPropertyNotFoundException;

/**
 * @author FreeGen
 */
public interface NonrdbConfig extends NonrdbEnv {

    /** The key of the configuration. e.g. nonrdb */
    String DOMAIN_NAME = "domain.name";

    /** The key of the configuration. e.g. Nonrdb */
    String DOMAIN_TITLE = "domain.title";

    /** The key of the configuration. e.g. 10000 */
    String SOLR_CONNECTION_TIMEOUT = "solr.connectionTimeout";

    /** The key of the configuration. e.g. 10000 */
    String SOLR_SOCKET_TIMEOUT = "solr.socketTimeout";

    /** The key of the configuration. e.g. 32 */
    String SOLR_DEFAULT_MAX_CONNECTIONS_PER_HOST = "solr.defaultMaxConnectionsPerHost";

    /** The key of the configuration. e.g. 128 */
    String SOLR_MAX_TOTAL_CONNECTIONS = "solr.maxTotalConnections";

    /** The key of the configuration. e.g. 60000 */
    String SOLR_ALIVE_CHECK_INTERVAL = "solr.aliveCheckInterval";

    /** The key of the configuration. e.g. -1 */
    String KVS_EXAMPLEKVS_MAX_WAIT_MILLIS = "kvs.examplekvs.maxWaitMillis";

    /** The key of the configuration. e.g. 60000 */
    String KVS_EXAMPLEKVS_MIN_EVICTABLE_IDLE_TIME_MILLIS = "kvs.examplekvs.minEvictableIdleTimeMillis";

    /** The key of the configuration. e.g. 30000 */
    String KVS_EXAMPLEKVS_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS = "kvs.examplekvs.softMinEvictableIdleTimeMillis";

    /** The key of the configuration. e.g. 10 */
    String KVS_EXAMPLEKVS_NUM_TESTS_PER_EVICTION_RUN = "kvs.examplekvs.numTestsPerEvictionRun";

    /** The key of the configuration. e.g. true */
    String KVS_EXAMPLEKVS_TEST_ON_BORROW = "kvs.examplekvs.testOnBorrow";

    /** The key of the configuration. e.g. false */
    String KVS_EXAMPLEKVS_TEST_ON_RETURN = "kvs.examplekvs.testOnReturn";

    /** The key of the configuration. e.g. true */
    String KVS_EXAMPLEKVS_TEST_WHILE_IDLE = "kvs.examplekvs.testWhileIdle";

    /** The key of the configuration. e.g. 30000 */
    String KVS_EXAMPLEKVS_TIME_BETWEEN_EVICTION_RUNS_MILLIS = "kvs.examplekvs.timeBetweenEvictionRunsMillis";

    /** The key of the configuration. e.g. 3600000 */
    String KVS_CACHE_MAIHAMADB_TTL = "kvs.cache.maihamadb.ttl";

    /** The key of the configuration. e.g. / */
    String COOKIE_DEFAULT_PATH = "cookie.default.path";

    /** The key of the configuration. e.g. 31556926 */
    String COOKIE_DEFAULT_EXPIRE = "cookie.default.expire";

    /** The key of the configuration. e.g. 315360000 */
    String COOKIE_ETERNAL_EXPIRE = "cookie.eternal.expire";

    /** The key of the configuration. e.g. NRB */
    String COOKIE_REMEMBER_ME_NONRDB_KEY = "cookie.remember.me.nonrdb.key";

    /**
     * Get the value of property as {@link String}.
     * @param propertyKey The key of the property. (NotNull)
     * @return The value of found property. (NotNull: if not found, exception)
     * @throws ConfigPropertyNotFoundException When the property is not found.
     */
    String get(String propertyKey);

    /**
     * Is the property true?
     * @param propertyKey The key of the property which is boolean type. (NotNull)
     * @return The determination, true or false. (if not found, exception)
     * @throws ConfigPropertyNotFoundException When the property is not found.
     */
    boolean is(String propertyKey);

    /**
     * Get the value for the key 'domain.name'. <br>
     * The value is, e.g. nonrdb <br>
     * comment: The name of domain (means this application) as identity
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getDomainName();

    /**
     * Get the value for the key 'domain.title'. <br>
     * The value is, e.g. Nonrdb <br>
     * comment: The title of domain (means this application) for logging
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getDomainTitle();

    /**
     * Get the value for the key 'solr.connectionTimeout'. <br>
     * The value is, e.g. 10000 <br>
     * comment: Solr connection timeout
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getSolrConnectionTimeout();

    /**
     * Get the value for the key 'solr.connectionTimeout' as {@link Integer}. <br>
     * The value is, e.g. 10000 <br>
     * comment: Solr connection timeout
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getSolrConnectionTimeoutAsInteger();

    /**
     * Get the value for the key 'solr.socketTimeout'. <br>
     * The value is, e.g. 10000 <br>
     * comment: Solr socket timeout
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getSolrSocketTimeout();

    /**
     * Get the value for the key 'solr.socketTimeout' as {@link Integer}. <br>
     * The value is, e.g. 10000 <br>
     * comment: Solr socket timeout
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getSolrSocketTimeoutAsInteger();

    /**
     * Get the value for the key 'solr.defaultMaxConnectionsPerHost'. <br>
     * The value is, e.g. 32 <br>
     * comment: Solr default max connections per host
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getSolrDefaultMaxConnectionsPerHost();

    /**
     * Get the value for the key 'solr.defaultMaxConnectionsPerHost' as {@link Integer}. <br>
     * The value is, e.g. 32 <br>
     * comment: Solr default max connections per host
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getSolrDefaultMaxConnectionsPerHostAsInteger();

    /**
     * Get the value for the key 'solr.maxTotalConnections'. <br>
     * The value is, e.g. 128 <br>
     * comment: Solr max total connections
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getSolrMaxTotalConnections();

    /**
     * Get the value for the key 'solr.maxTotalConnections' as {@link Integer}. <br>
     * The value is, e.g. 128 <br>
     * comment: Solr max total connections
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getSolrMaxTotalConnectionsAsInteger();

    /**
     * Get the value for the key 'solr.aliveCheckInterval'. <br>
     * The value is, e.g. 60000 <br>
     * comment: Solr interval for checking aliveness
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getSolrAliveCheckInterval();

    /**
     * Get the value for the key 'solr.aliveCheckInterval' as {@link Integer}. <br>
     * The value is, e.g. 60000 <br>
     * comment: Solr interval for checking aliveness
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getSolrAliveCheckIntervalAsInteger();

    /**
     * Get the value for the key 'kvs.examplekvs.maxWaitMillis'. <br>
     * The value is, e.g. -1 <br>
     * comment: <br>
     * Following configs will not be used if kvs.mock is true.<br>
     * Max latency to get connection to kvs (milliseconds)
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getKvsExamplekvsMaxWaitMillis();

    /**
     * Get the value for the key 'kvs.examplekvs.maxWaitMillis' as {@link Integer}. <br>
     * The value is, e.g. -1 <br>
     * comment: <br>
     * Following configs will not be used if kvs.mock is true.<br>
     * Max latency to get connection to kvs (milliseconds)
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getKvsExamplekvsMaxWaitMillisAsInteger();

    /**
     * Get the value for the key 'kvs.examplekvs.minEvictableIdleTimeMillis'. <br>
     * The value is, e.g. 60000 <br>
     * comment: TTL for idle connection (milliseconds)
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getKvsExamplekvsMinEvictableIdleTimeMillis();

    /**
     * Get the value for the key 'kvs.examplekvs.minEvictableIdleTimeMillis' as {@link Integer}. <br>
     * The value is, e.g. 60000 <br>
     * comment: TTL for idle connection (milliseconds)
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getKvsExamplekvsMinEvictableIdleTimeMillisAsInteger();

    /**
     * Get the value for the key 'kvs.examplekvs.softMinEvictableIdleTimeMillis'. <br>
     * The value is, e.g. 30000 <br>
     * comment: Soft TTL for idle connection (milliseconds)
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getKvsExamplekvsSoftMinEvictableIdleTimeMillis();

    /**
     * Get the value for the key 'kvs.examplekvs.softMinEvictableIdleTimeMillis' as {@link Integer}. <br>
     * The value is, e.g. 30000 <br>
     * comment: Soft TTL for idle connection (milliseconds)
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getKvsExamplekvsSoftMinEvictableIdleTimeMillisAsInteger();

    /**
     * Get the value for the key 'kvs.examplekvs.numTestsPerEvictionRun'. <br>
     * The value is, e.g. 10 <br>
     * comment: Max number of idle connections per monitoring process
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getKvsExamplekvsNumTestsPerEvictionRun();

    /**
     * Get the value for the key 'kvs.examplekvs.numTestsPerEvictionRun' as {@link Integer}. <br>
     * The value is, e.g. 10 <br>
     * comment: Max number of idle connections per monitoring process
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getKvsExamplekvsNumTestsPerEvictionRunAsInteger();

    /**
     * Get the value for the key 'kvs.examplekvs.testOnBorrow'. <br>
     * The value is, e.g. true <br>
     * comment: Should check if connection is available before getting connection
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getKvsExamplekvsTestOnBorrow();

    /**
     * Is the property for the key 'kvs.examplekvs.testOnBorrow' true? <br>
     * The value is, e.g. true <br>
     * comment: Should check if connection is available before getting connection
     * @return The determination, true or false. (if not found, exception but basically no way)
     */
    boolean isKvsExamplekvsTestOnBorrow();

    /**
     * Get the value for the key 'kvs.examplekvs.testOnReturn'. <br>
     * The value is, e.g. false <br>
     * comment: Should check if connection is available before returning connection
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getKvsExamplekvsTestOnReturn();

    /**
     * Is the property for the key 'kvs.examplekvs.testOnReturn' true? <br>
     * The value is, e.g. false <br>
     * comment: Should check if connection is available before returning connection
     * @return The determination, true or false. (if not found, exception but basically no way)
     */
    boolean isKvsExamplekvsTestOnReturn();

    /**
     * Get the value for the key 'kvs.examplekvs.testWhileIdle'. <br>
     * The value is, e.g. true <br>
     * comment: Should check if idle connections are available
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getKvsExamplekvsTestWhileIdle();

    /**
     * Is the property for the key 'kvs.examplekvs.testWhileIdle' true? <br>
     * The value is, e.g. true <br>
     * comment: Should check if idle connections are available
     * @return The determination, true or false. (if not found, exception but basically no way)
     */
    boolean isKvsExamplekvsTestWhileIdle();

    /**
     * Get the value for the key 'kvs.examplekvs.timeBetweenEvictionRunsMillis'. <br>
     * The value is, e.g. 30000 <br>
     * comment: Monitoring interval for idle connections in connection pool (milliseconds)
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getKvsExamplekvsTimeBetweenEvictionRunsMillis();

    /**
     * Get the value for the key 'kvs.examplekvs.timeBetweenEvictionRunsMillis' as {@link Integer}. <br>
     * The value is, e.g. 30000 <br>
     * comment: Monitoring interval for idle connections in connection pool (milliseconds)
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getKvsExamplekvsTimeBetweenEvictionRunsMillisAsInteger();

    /**
     * Get the value for the key 'kvs.cache.maihamadb.ttl'. <br>
     * The value is, e.g. 3600000 <br>
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getKvsCacheMaihamadbTtl();

    /**
     * Get the value for the key 'kvs.cache.maihamadb.ttl' as {@link Integer}. <br>
     * The value is, e.g. 3600000 <br>
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getKvsCacheMaihamadbTtlAsInteger();

    /**
     * Get the value for the key 'cookie.default.path'. <br>
     * The value is, e.g. / <br>
     * comment: The default path of cookie (basically '/' if no context path)
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getCookieDefaultPath();

    /**
     * Get the value for the key 'cookie.default.expire'. <br>
     * The value is, e.g. 31556926 <br>
     * comment: The default expire of cookie in seconds e.g. 31556926: one year, 86400: one day
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getCookieDefaultExpire();

    /**
     * Get the value for the key 'cookie.default.expire' as {@link Integer}. <br>
     * The value is, e.g. 31556926 <br>
     * comment: The default expire of cookie in seconds e.g. 31556926: one year, 86400: one day
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getCookieDefaultExpireAsInteger();

    /**
     * Get the value for the key 'cookie.eternal.expire'. <br>
     * The value is, e.g. 315360000 <br>
     * comment: The eternal expire of cookie in seconds e.g. 315360000: ten year, 86400: one day
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getCookieEternalExpire();

    /**
     * Get the value for the key 'cookie.eternal.expire' as {@link Integer}. <br>
     * The value is, e.g. 315360000 <br>
     * comment: The eternal expire of cookie in seconds e.g. 315360000: ten year, 86400: one day
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getCookieEternalExpireAsInteger();

    /**
     * Get the value for the key 'cookie.remember.me.nonrdb.key'. <br>
     * The value is, e.g. NRB <br>
     * comment: The cookie key of remember-me for Nonrdb #change_it_first
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getCookieRememberMeNonrdbKey();

    /**
     * The simple implementation for configuration.
     * @author FreeGen
     */
    public static class SimpleImpl extends NonrdbEnv.SimpleImpl implements NonrdbConfig {

        /** The serial version UID for object serialization. (Default) */
        private static final long serialVersionUID = 1L;

        public String getDomainName() {
            return get(NonrdbConfig.DOMAIN_NAME);
        }

        public String getDomainTitle() {
            return get(NonrdbConfig.DOMAIN_TITLE);
        }

        public String getSolrConnectionTimeout() {
            return get(NonrdbConfig.SOLR_CONNECTION_TIMEOUT);
        }

        public Integer getSolrConnectionTimeoutAsInteger() {
            return getAsInteger(NonrdbConfig.SOLR_CONNECTION_TIMEOUT);
        }

        public String getSolrSocketTimeout() {
            return get(NonrdbConfig.SOLR_SOCKET_TIMEOUT);
        }

        public Integer getSolrSocketTimeoutAsInteger() {
            return getAsInteger(NonrdbConfig.SOLR_SOCKET_TIMEOUT);
        }

        public String getSolrDefaultMaxConnectionsPerHost() {
            return get(NonrdbConfig.SOLR_DEFAULT_MAX_CONNECTIONS_PER_HOST);
        }

        public Integer getSolrDefaultMaxConnectionsPerHostAsInteger() {
            return getAsInteger(NonrdbConfig.SOLR_DEFAULT_MAX_CONNECTIONS_PER_HOST);
        }

        public String getSolrMaxTotalConnections() {
            return get(NonrdbConfig.SOLR_MAX_TOTAL_CONNECTIONS);
        }

        public Integer getSolrMaxTotalConnectionsAsInteger() {
            return getAsInteger(NonrdbConfig.SOLR_MAX_TOTAL_CONNECTIONS);
        }

        public String getSolrAliveCheckInterval() {
            return get(NonrdbConfig.SOLR_ALIVE_CHECK_INTERVAL);
        }

        public Integer getSolrAliveCheckIntervalAsInteger() {
            return getAsInteger(NonrdbConfig.SOLR_ALIVE_CHECK_INTERVAL);
        }

        public String getKvsExamplekvsMaxWaitMillis() {
            return get(NonrdbConfig.KVS_EXAMPLEKVS_MAX_WAIT_MILLIS);
        }

        public Integer getKvsExamplekvsMaxWaitMillisAsInteger() {
            return getAsInteger(NonrdbConfig.KVS_EXAMPLEKVS_MAX_WAIT_MILLIS);
        }

        public String getKvsExamplekvsMinEvictableIdleTimeMillis() {
            return get(NonrdbConfig.KVS_EXAMPLEKVS_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        }

        public Integer getKvsExamplekvsMinEvictableIdleTimeMillisAsInteger() {
            return getAsInteger(NonrdbConfig.KVS_EXAMPLEKVS_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        }

        public String getKvsExamplekvsSoftMinEvictableIdleTimeMillis() {
            return get(NonrdbConfig.KVS_EXAMPLEKVS_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        }

        public Integer getKvsExamplekvsSoftMinEvictableIdleTimeMillisAsInteger() {
            return getAsInteger(NonrdbConfig.KVS_EXAMPLEKVS_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        }

        public String getKvsExamplekvsNumTestsPerEvictionRun() {
            return get(NonrdbConfig.KVS_EXAMPLEKVS_NUM_TESTS_PER_EVICTION_RUN);
        }

        public Integer getKvsExamplekvsNumTestsPerEvictionRunAsInteger() {
            return getAsInteger(NonrdbConfig.KVS_EXAMPLEKVS_NUM_TESTS_PER_EVICTION_RUN);
        }

        public String getKvsExamplekvsTestOnBorrow() {
            return get(NonrdbConfig.KVS_EXAMPLEKVS_TEST_ON_BORROW);
        }

        public boolean isKvsExamplekvsTestOnBorrow() {
            return is(NonrdbConfig.KVS_EXAMPLEKVS_TEST_ON_BORROW);
        }

        public String getKvsExamplekvsTestOnReturn() {
            return get(NonrdbConfig.KVS_EXAMPLEKVS_TEST_ON_RETURN);
        }

        public boolean isKvsExamplekvsTestOnReturn() {
            return is(NonrdbConfig.KVS_EXAMPLEKVS_TEST_ON_RETURN);
        }

        public String getKvsExamplekvsTestWhileIdle() {
            return get(NonrdbConfig.KVS_EXAMPLEKVS_TEST_WHILE_IDLE);
        }

        public boolean isKvsExamplekvsTestWhileIdle() {
            return is(NonrdbConfig.KVS_EXAMPLEKVS_TEST_WHILE_IDLE);
        }

        public String getKvsExamplekvsTimeBetweenEvictionRunsMillis() {
            return get(NonrdbConfig.KVS_EXAMPLEKVS_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
        }

        public Integer getKvsExamplekvsTimeBetweenEvictionRunsMillisAsInteger() {
            return getAsInteger(NonrdbConfig.KVS_EXAMPLEKVS_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
        }

        public String getKvsCacheMaihamadbTtl() {
            return get(NonrdbConfig.KVS_CACHE_MAIHAMADB_TTL);
        }

        public Integer getKvsCacheMaihamadbTtlAsInteger() {
            return getAsInteger(NonrdbConfig.KVS_CACHE_MAIHAMADB_TTL);
        }

        public String getCookieDefaultPath() {
            return get(NonrdbConfig.COOKIE_DEFAULT_PATH);
        }

        public String getCookieDefaultExpire() {
            return get(NonrdbConfig.COOKIE_DEFAULT_EXPIRE);
        }

        public Integer getCookieDefaultExpireAsInteger() {
            return getAsInteger(NonrdbConfig.COOKIE_DEFAULT_EXPIRE);
        }

        public String getCookieEternalExpire() {
            return get(NonrdbConfig.COOKIE_ETERNAL_EXPIRE);
        }

        public Integer getCookieEternalExpireAsInteger() {
            return getAsInteger(NonrdbConfig.COOKIE_ETERNAL_EXPIRE);
        }

        public String getCookieRememberMeNonrdbKey() {
            return get(NonrdbConfig.COOKIE_REMEMBER_ME_NONRDB_KEY);
        }
    }
}
