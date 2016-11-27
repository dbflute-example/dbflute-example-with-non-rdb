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
package org.dbflute.kvs.core.delegator;

import java.io.Closeable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author FreeGen
 */
public class KvsRedisPool implements Closeable {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** Connection pool for Jedis */
    protected JedisPool jedisPool;

    private JedisPoolConfig jedisPoolConfig;

    private String host;

    private Integer port;

    private Integer timeout;

    // ===================================================================================
    //                                                                     Pool Management
    //                                                                     ===============
    @PostConstruct
    public void init() {
        jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);
    }

    public Jedis getResource() {
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }

    public int getNumActive() {
        return jedisPool.getNumActive();
    }

    @Override
    public void close() {
        jedisPool.close();
    }

    @PreDestroy
    public void destroy() {
        jedisPool.destroy();
    }

    // ===================================================================================
    //                                                                     Connection Info
    //                                                                     ===============
    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
