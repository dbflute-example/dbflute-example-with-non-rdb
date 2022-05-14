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
package org.docksidestage.mylasta.direction;

import org.lastaflute.core.direction.ObjectiveConfig;
import org.lastaflute.core.direction.exception.ConfigPropertyNotFoundException;

/**
 * @author FreeGen
 */
public interface NonrdbEnv {

    /** The key of the configuration. e.g. hot */
    String lasta_di_SMART_DEPLOY_MODE = "lasta_di.smart.deploy.mode";

    /** The key of the configuration. e.g. true */
    String DEVELOPMENT_HERE = "development.here";

    /** The key of the configuration. e.g. Local Development */
    String ENVIRONMENT_TITLE = "environment.title";

    /** The key of the configuration. e.g. false */
    String FRAMEWORK_DEBUG = "framework.debug";

    /** The key of the configuration. e.g. 0 */
    String TIME_ADJUST_TIME_MILLIS = "time.adjust.time.millis";

    /** The key of the configuration. e.g. debug */
    String LOG_LEVEL = "log.level";

    /** The key of the configuration. e.g. debug */
    String LOG_CONSOLE_LEVEL = "log.console.level";

    /** The key of the configuration. e.g. /tmp/lastaflute/nonrdb */
    String LOG_FILE_BASEDIR = "log.file.basedir";

    /** The key of the configuration. e.g. true */
    String MAIL_SEND_MOCK = "mail.send.mock";

    /** The key of the configuration. e.g. localhost:25 */
    String MAIL_SMTP_SERVER_MAIN_HOST_AND_PORT = "mail.smtp.server.main.host.and.port";

    /** The key of the configuration. e.g. [Test] */
    String MAIL_SUBJECT_TEST_PREFIX = "mail.subject.test.prefix";

    /** The key of the configuration. e.g. returnpath@docksidestage.org */
    String MAIL_RETURN_PATH = "mail.return.path";

    /** The key of the configuration. e.g. nonrdb-support@annie.example.com */
    String MAIL_ADDRESS_SUPPORT = "mail.address.support";

    /** The key of the configuration. e.g. org.h2.Driver */
    String JDBC_DRIVER = "jdbc.driver";

    /** The key of the configuration. e.g. jdbc:h2:file:$classes(org.docksidestage.dbflute.allcommon.DBCurrent.class)/../../etc/testdb/maihamadb */
    String JDBC_URL = "jdbc.url";

    /** The key of the configuration. e.g. maihamadb */
    String JDBC_USER = "jdbc.user";

    /** The key of the configuration. e.g. maihamadb */
    String JDBC_PASSWORD = "jdbc.password";

    /** The key of the configuration. e.g. 10 */
    String JDBC_CONNECTION_POOLING_SIZE = "jdbc.connection.pooling.size";

    /** The key of the configuration. e.g. http://localhost:8983/solr/example */
    String SOLR_EXAMPLE_URL = "solr.example.url";

    /** The key of the configuration. e.g. false */
    String KVS_MOCK = "kvs.mock";

    /** The key of the configuration. e.g. localhost */
    String KVS_EXAMPLEKVS_HOST = "kvs.examplekvs.host";

    /** The key of the configuration. e.g. 6379 */
    String KVS_EXAMPLEKVS_PORT = "kvs.examplekvs.port";

    /** The key of the configuration. e.g. 2000 */
    String KVS_EXAMPLEKVS_TIMEOUT = "kvs.examplekvs.timeout";

    /** The key of the configuration. e.g. 15 */
    String KVS_EXAMPLEKVS_MAX_TOTAL = "kvs.examplekvs.maxTotal";

    /** The key of the configuration. e.g. 15 */
    String KVS_EXAMPLEKVS_MAX_IDLE = "kvs.examplekvs.maxIdle";

    /** The key of the configuration. e.g. 15 */
    String KVS_EXAMPLEKVS_MIN_IDLE = "kvs.examplekvs.minIdle";

    /** The key of the configuration. e.g. localhost:8090/nonrdb */
    String SERVER_DOMAIN = "server.domain";

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
     * Get the value for the key 'lasta_di.smart.deploy.mode'. <br>
     * The value is, e.g. hot <br>
     * comment: The mode of Lasta Di's smart-deploy, should be cool in production (e.g. hot, cool, warm)
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getLastaDiSmartDeployMode();

    /**
     * Get the value for the key 'development.here'. <br>
     * The value is, e.g. true <br>
     * comment: Is development environment here? (used for various purpose, you should set false if unknown)
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getDevelopmentHere();

    /**
     * Is the property for the key 'development.here' true? <br>
     * The value is, e.g. true <br>
     * comment: Is development environment here? (used for various purpose, you should set false if unknown)
     * @return The determination, true or false. (if not found, exception but basically no way)
     */
    boolean isDevelopmentHere();

    /**
     * Get the value for the key 'environment.title'. <br>
     * The value is, e.g. Local Development <br>
     * comment: The title of environment (e.g. local or integartion or production)
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getEnvironmentTitle();

    /**
     * Get the value for the key 'framework.debug'. <br>
     * The value is, e.g. false <br>
     * comment: Does it enable the Framework internal debug? (true only when emergency)
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getFrameworkDebug();

    /**
     * Is the property for the key 'framework.debug' true? <br>
     * The value is, e.g. false <br>
     * comment: Does it enable the Framework internal debug? (true only when emergency)
     * @return The determination, true or false. (if not found, exception but basically no way)
     */
    boolean isFrameworkDebug();

    /**
     * Get the value for the key 'time.adjust.time.millis'. <br>
     * The value is, e.g. 0 <br>
     * comment: <br>
     * one day: 86400000, three days: 259200000, five days: 432000000, one week: 604800000, one year: 31556926000<br>
     * special script :: absolute mode: $(2014/07/10), relative mode: addDay(3).addMonth(4)<br>
     * The milliseconds for (relative or absolute) adjust time (set only when test) @LongType *dynamic in development
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getTimeAdjustTimeMillis();

    /**
     * Get the value for the key 'time.adjust.time.millis' as {@link Long}. <br>
     * The value is, e.g. 0 <br>
     * comment: <br>
     * one day: 86400000, three days: 259200000, five days: 432000000, one week: 604800000, one year: 31556926000<br>
     * special script :: absolute mode: $(2014/07/10), relative mode: addDay(3).addMonth(4)<br>
     * The milliseconds for (relative or absolute) adjust time (set only when test) @LongType *dynamic in development
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not long.
     */
    Long getTimeAdjustTimeMillisAsLong();

    /**
     * Get the value for the key 'log.level'. <br>
     * The value is, e.g. debug <br>
     * comment: The log level for logback
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getLogLevel();

    /**
     * Get the value for the key 'log.console.level'. <br>
     * The value is, e.g. debug <br>
     * comment: The log console level for logback
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getLogConsoleLevel();

    /**
     * Get the value for the key 'log.file.basedir'. <br>
     * The value is, e.g. /tmp/lastaflute/nonrdb <br>
     * comment: The log file basedir
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getLogFileBasedir();

    /**
     * Get the value for the key 'mail.send.mock'. <br>
     * The value is, e.g. true <br>
     * comment: Does it send mock mail? (true: no send actually, logging only)
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getMailSendMock();

    /**
     * Is the property for the key 'mail.send.mock' true? <br>
     * The value is, e.g. true <br>
     * comment: Does it send mock mail? (true: no send actually, logging only)
     * @return The determination, true or false. (if not found, exception but basically no way)
     */
    boolean isMailSendMock();

    /**
     * Get the value for the key 'mail.smtp.server.main.host.and.port'. <br>
     * The value is, e.g. localhost:25 <br>
     * comment: SMTP server settings for main: host:port
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getMailSmtpServerMainHostAndPort();

    /**
     * Get the value for the key 'mail.subject.test.prefix'. <br>
     * The value is, e.g. [Test] <br>
     * comment: The prefix of subject to show test environment or not
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getMailSubjectTestPrefix();

    /**
     * Get the value for the key 'mail.return.path'. <br>
     * The value is, e.g. returnpath@docksidestage.org <br>
     * comment: The common return path of all mail
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getMailReturnPath();

    /**
     * Get the value for the key 'mail.address.support'. <br>
     * The value is, e.g. nonrdb-support@annie.example.com <br>
     * comment: Mail Address for Nonrdb Support
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getMailAddressSupport();

    /**
     * Get the value for the key 'jdbc.driver'. <br>
     * The value is, e.g. org.h2.Driver <br>
     * comment: The driver FQCN to connect database for JDBC
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getJdbcDriver();

    /**
     * Get the value for the key 'jdbc.url'. <br>
     * The value is, e.g. jdbc:h2:file:$classes(org.docksidestage.dbflute.allcommon.DBCurrent.class)/../../etc/testdb/maihamadb <br>
     * comment: The URL of database connection for JDBC
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getJdbcUrl();

    /**
     * Get the value for the key 'jdbc.user'. <br>
     * The value is, e.g. maihamadb <br>
     * comment: The user of database connection for JDBC
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getJdbcUser();

    /**
     * Get the value for the key 'jdbc.password'. <br>
     * The value is, e.g. maihamadb <br>
     * comment: @Secure The password of database connection for JDBC
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getJdbcPassword();

    /**
     * Get the value for the key 'jdbc.connection.pooling.size'. <br>
     * The value is, e.g. 10 <br>
     * comment: The (max) pooling size of connection pool
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getJdbcConnectionPoolingSize();

    /**
     * Get the value for the key 'jdbc.connection.pooling.size' as {@link Integer}. <br>
     * The value is, e.g. 10 <br>
     * comment: The (max) pooling size of connection pool
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getJdbcConnectionPoolingSizeAsInteger();

    /**
     * Get the value for the key 'solr.example.url'. <br>
     * The value is, e.g. http://localhost:8983/solr/example <br>
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getSolrExampleUrl();

    /**
     * Get the value for the key 'kvs.mock'. <br>
     * The value is, e.g. false <br>
     * comment: <br>
     * KVS<br>
     * -=-=-<br>
     * Whether to use mock for KVS
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getKvsMock();

    /**
     * Is the property for the key 'kvs.mock' true? <br>
     * The value is, e.g. false <br>
     * comment: <br>
     * KVS<br>
     * -=-=-<br>
     * Whether to use mock for KVS
     * @return The determination, true or false. (if not found, exception but basically no way)
     */
    boolean isKvsMock();

    /**
     * Get the value for the key 'kvs.examplekvs.host'. <br>
     * The value is, e.g. localhost <br>
     * comment: Hostname
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getKvsExamplekvsHost();

    /**
     * Get the value for the key 'kvs.examplekvs.port'. <br>
     * The value is, e.g. 6379 <br>
     * comment: Port
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getKvsExamplekvsPort();

    /**
     * Get the value for the key 'kvs.examplekvs.port' as {@link Integer}. <br>
     * The value is, e.g. 6379 <br>
     * comment: Port
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getKvsExamplekvsPortAsInteger();

    /**
     * Get the value for the key 'kvs.examplekvs.timeout'. <br>
     * The value is, e.g. 2000 <br>
     * comment: Timeout for connection
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getKvsExamplekvsTimeout();

    /**
     * Get the value for the key 'kvs.examplekvs.timeout' as {@link Integer}. <br>
     * The value is, e.g. 2000 <br>
     * comment: Timeout for connection
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getKvsExamplekvsTimeoutAsInteger();

    /**
     * Get the value for the key 'kvs.examplekvs.maxTotal'. <br>
     * The value is, e.g. 15 <br>
     * comment: Max number of pooled connections
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getKvsExamplekvsMaxTotal();

    /**
     * Get the value for the key 'kvs.examplekvs.maxTotal' as {@link Integer}. <br>
     * The value is, e.g. 15 <br>
     * comment: Max number of pooled connections
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getKvsExamplekvsMaxTotalAsInteger();

    /**
     * Get the value for the key 'kvs.examplekvs.maxIdle'. <br>
     * The value is, e.g. 15 <br>
     * comment: Max number of idle connections
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getKvsExamplekvsMaxIdle();

    /**
     * Get the value for the key 'kvs.examplekvs.maxIdle' as {@link Integer}. <br>
     * The value is, e.g. 15 <br>
     * comment: Max number of idle connections
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getKvsExamplekvsMaxIdleAsInteger();

    /**
     * Get the value for the key 'kvs.examplekvs.minIdle'. <br>
     * The value is, e.g. 15 <br>
     * comment: Min number of idle connections
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getKvsExamplekvsMinIdle();

    /**
     * Get the value for the key 'kvs.examplekvs.minIdle' as {@link Integer}. <br>
     * The value is, e.g. 15 <br>
     * comment: Min number of idle connections
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     * @throws NumberFormatException When the property is not integer.
     */
    Integer getKvsExamplekvsMinIdleAsInteger();

    /**
     * Get the value for the key 'server.domain'. <br>
     * The value is, e.g. localhost:8090/nonrdb <br>
     * comment: domain to access this application from internet, e.g. for registration mail
     * @return The value of found property. (NotNull: if not found, exception but basically no way)
     */
    String getServerDomain();

    /**
     * The simple implementation for configuration.
     * @author FreeGen
     */
    public static class SimpleImpl extends ObjectiveConfig implements NonrdbEnv {

        /** The serial version UID for object serialization. (Default) */
        private static final long serialVersionUID = 1L;

        public String getLastaDiSmartDeployMode() {
            return get(NonrdbEnv.lasta_di_SMART_DEPLOY_MODE);
        }

        public String getDevelopmentHere() {
            return get(NonrdbEnv.DEVELOPMENT_HERE);
        }

        public boolean isDevelopmentHere() {
            return is(NonrdbEnv.DEVELOPMENT_HERE);
        }

        public String getEnvironmentTitle() {
            return get(NonrdbEnv.ENVIRONMENT_TITLE);
        }

        public String getFrameworkDebug() {
            return get(NonrdbEnv.FRAMEWORK_DEBUG);
        }

        public boolean isFrameworkDebug() {
            return is(NonrdbEnv.FRAMEWORK_DEBUG);
        }

        public String getTimeAdjustTimeMillis() {
            return get(NonrdbEnv.TIME_ADJUST_TIME_MILLIS);
        }

        public Long getTimeAdjustTimeMillisAsLong() {
            return getAsLong(NonrdbEnv.TIME_ADJUST_TIME_MILLIS);
        }

        public String getLogLevel() {
            return get(NonrdbEnv.LOG_LEVEL);
        }

        public String getLogConsoleLevel() {
            return get(NonrdbEnv.LOG_CONSOLE_LEVEL);
        }

        public String getLogFileBasedir() {
            return get(NonrdbEnv.LOG_FILE_BASEDIR);
        }

        public String getMailSendMock() {
            return get(NonrdbEnv.MAIL_SEND_MOCK);
        }

        public boolean isMailSendMock() {
            return is(NonrdbEnv.MAIL_SEND_MOCK);
        }

        public String getMailSmtpServerMainHostAndPort() {
            return get(NonrdbEnv.MAIL_SMTP_SERVER_MAIN_HOST_AND_PORT);
        }

        public String getMailSubjectTestPrefix() {
            return get(NonrdbEnv.MAIL_SUBJECT_TEST_PREFIX);
        }

        public String getMailReturnPath() {
            return get(NonrdbEnv.MAIL_RETURN_PATH);
        }

        public String getMailAddressSupport() {
            return get(NonrdbEnv.MAIL_ADDRESS_SUPPORT);
        }

        public String getJdbcDriver() {
            return get(NonrdbEnv.JDBC_DRIVER);
        }

        public String getJdbcUrl() {
            return get(NonrdbEnv.JDBC_URL);
        }

        public String getJdbcUser() {
            return get(NonrdbEnv.JDBC_USER);
        }

        public String getJdbcPassword() {
            return get(NonrdbEnv.JDBC_PASSWORD);
        }

        public String getJdbcConnectionPoolingSize() {
            return get(NonrdbEnv.JDBC_CONNECTION_POOLING_SIZE);
        }

        public Integer getJdbcConnectionPoolingSizeAsInteger() {
            return getAsInteger(NonrdbEnv.JDBC_CONNECTION_POOLING_SIZE);
        }

        public String getSolrExampleUrl() {
            return get(NonrdbEnv.SOLR_EXAMPLE_URL);
        }

        public String getKvsMock() {
            return get(NonrdbEnv.KVS_MOCK);
        }

        public boolean isKvsMock() {
            return is(NonrdbEnv.KVS_MOCK);
        }

        public String getKvsExamplekvsHost() {
            return get(NonrdbEnv.KVS_EXAMPLEKVS_HOST);
        }

        public String getKvsExamplekvsPort() {
            return get(NonrdbEnv.KVS_EXAMPLEKVS_PORT);
        }

        public Integer getKvsExamplekvsPortAsInteger() {
            return getAsInteger(NonrdbEnv.KVS_EXAMPLEKVS_PORT);
        }

        public String getKvsExamplekvsTimeout() {
            return get(NonrdbEnv.KVS_EXAMPLEKVS_TIMEOUT);
        }

        public Integer getKvsExamplekvsTimeoutAsInteger() {
            return getAsInteger(NonrdbEnv.KVS_EXAMPLEKVS_TIMEOUT);
        }

        public String getKvsExamplekvsMaxTotal() {
            return get(NonrdbEnv.KVS_EXAMPLEKVS_MAX_TOTAL);
        }

        public Integer getKvsExamplekvsMaxTotalAsInteger() {
            return getAsInteger(NonrdbEnv.KVS_EXAMPLEKVS_MAX_TOTAL);
        }

        public String getKvsExamplekvsMaxIdle() {
            return get(NonrdbEnv.KVS_EXAMPLEKVS_MAX_IDLE);
        }

        public Integer getKvsExamplekvsMaxIdleAsInteger() {
            return getAsInteger(NonrdbEnv.KVS_EXAMPLEKVS_MAX_IDLE);
        }

        public String getKvsExamplekvsMinIdle() {
            return get(NonrdbEnv.KVS_EXAMPLEKVS_MIN_IDLE);
        }

        public Integer getKvsExamplekvsMinIdleAsInteger() {
            return getAsInteger(NonrdbEnv.KVS_EXAMPLEKVS_MIN_IDLE);
        }

        public String getServerDomain() {
            return get(NonrdbEnv.SERVER_DOMAIN);
        }
    }
}
