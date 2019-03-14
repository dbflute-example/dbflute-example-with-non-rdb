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
package org.docksidestage.kvs.pool;

import org.dbflute.kvs.core.delegator.KvsRedisPool;
import org.lastaflute.core.util.ContainerUtil;

import redis.clients.jedis.JedisPoolConfig;

/**
 * KvsRedisPoolFactory.
 * @author FreeGen
 */
public class KvsRedisPoolFactory {

    /**
     * create examplekvs(Example Store) pool.
     * @return examplekvs(Example Store) pool
     */
    public static KvsRedisPool createExamplekvs() {
        org.docksidestage.mylasta.direction.NonrdbConfig config =
                ContainerUtil.getComponent(org.docksidestage.mylasta.direction.NonrdbConfig.class);

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWaitMillis(config.getKvsExamplekvsMaxWaitMillisAsInteger());
        jedisPoolConfig.setMinEvictableIdleTimeMillis(config.getKvsExamplekvsMinEvictableIdleTimeMillisAsInteger());
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(config.getKvsExamplekvsSoftMinEvictableIdleTimeMillisAsInteger());
        jedisPoolConfig.setNumTestsPerEvictionRun(config.getKvsExamplekvsNumTestsPerEvictionRunAsInteger());
        jedisPoolConfig.setTestOnBorrow(config.isKvsExamplekvsTestOnBorrow());
        jedisPoolConfig.setTestOnReturn(config.isKvsExamplekvsTestOnReturn());
        jedisPoolConfig.setTestWhileIdle(config.isKvsExamplekvsTestWhileIdle());
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(config.getKvsExamplekvsTimeBetweenEvictionRunsMillisAsInteger());
        jedisPoolConfig.setMaxTotal(config.getKvsExamplekvsMaxTotalAsInteger());
        jedisPoolConfig.setMaxIdle(config.getKvsExamplekvsMaxIdleAsInteger());
        jedisPoolConfig.setMinIdle(config.getKvsExamplekvsMinIdleAsInteger());

        KvsRedisPool kvsRedisPool = createKvsRedisPool(config.isKvsMock());
        kvsRedisPool.setJedisPoolConfig(jedisPoolConfig);
        kvsRedisPool.setHost(config.getKvsExamplekvsHost());
        kvsRedisPool.setPort(config.getKvsExamplekvsPortAsInteger());
        kvsRedisPool.setTimeout(config.getKvsExamplekvsTimeoutAsInteger());

        return kvsRedisPool;
    }

    protected static KvsRedisPool createKvsRedisPool(boolean mock) {
        KvsRedisPool kvsRedisPool;
        // TODO p1us2er0 Do not process in case of mock, because cluster configuration is checked in case of JedisCluster (2019/03/14)
        if (mock) {
            kvsRedisPool = new KvsRedisPool() {
                @Override
                public void init() {
                }
            };
        } else {
            kvsRedisPool = new KvsRedisPool();
        }
        return kvsRedisPool;
    }
}
