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
package org.dbflute.kvs.core.delegator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.dbflute.helper.HandyDate;
import org.dbflute.util.DfCollectionUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * @author FreeGen
 */
public abstract class AbstractKvsRedisDelegator implements KvsDelegator {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private KvsRedisPool kvsRedisPool;

    // ===================================================================================
    //                                                                                Set
    //                                                                               =====
    public void setKvsRedisPool(KvsRedisPool kvsRedisPool) {
        this.kvsRedisPool = kvsRedisPool;
    }

    // ===================================================================================
    //                                                                                Get
    //                                                                               =====
    // -----------------------------------------------------
    //                                                String
    //                                                ------
    @Override
    public String findString(String key) {
        try (Jedis jedis = kvsRedisPool.getResource()) {
            return jedis.get(key);
        }
    }

    @Override
    public List<String> findMultiString(List<String> keyList) {
        if (null == keyList || keyList.isEmpty()) {
            return new ArrayList<String>();
        }
        try (Jedis jedis = kvsRedisPool.getResource()) {
            List<String> list = jedis.mget(keyList.toArray(new String[] {}));
            return list;
        }
    }

    // -----------------------------------------------------
    //                                                  List
    //                                                  ----
    @Override
    public List<String> findList(String key) {
        try (Jedis jedis = kvsRedisPool.getResource()) {
            List<String> list = jedis.lrange(key, 0, -1);
            return list.stream().distinct().collect(Collectors.toList());
        }
    }

    @Override
    public List<List<String>> findMultiList(List<String> keyList) {
        List<Response<List<String>>> list = null;
        try (Jedis jedis = kvsRedisPool.getResource(); Pipeline pipeline = jedis.pipelined()) {
            list = keyList.stream().map(key -> pipeline.lrange(key, 0, -1)).collect(Collectors.toList());
            pipeline.sync();
        }

        return list.stream().map(response -> response.get().stream().distinct().collect(Collectors.toList())).collect(Collectors.toList());
    }

    // -----------------------------------------------------
    //                                                  Hash
    //                                                  ----
    @Override
    public Map<String, String> findHash(String key) {
        try (Jedis jedis = kvsRedisPool.getResource()) {
            return jedis.hgetAll(key);
        }
    }

    @Override
    public List<String> findHash(String key, Set<String> fieldList) {
        try (Jedis jedis = kvsRedisPool.getResource()) {
            List<String> list = jedis.hmget(key, fieldList.toArray(new String[] {}));
            return list.stream().map(value -> "".equals(value) ? null : value).collect(Collectors.toList());
        }
    }

    @Override
    public List<List<String>> findMultiHash(List<String> keyList, Set<String> fieldList) {
        List<Response<List<String>>> list = null;
        try (Jedis jedis = kvsRedisPool.getResource(); Pipeline pipeline = jedis.pipelined()) {
            list = keyList.stream().map(key -> pipeline.hmget(key, fieldList.toArray(new String[] {}))).collect(Collectors.toList());
            pipeline.sync();
        }

        return list.stream().map(response -> {
            return response.get().stream().map(value -> "".equals(value) ? null : value).collect(Collectors.toList());
        }).collect(Collectors.toList());
    }

    // ===================================================================================
    //                                                                            Register
    //                                                                            ========
    // -----------------------------------------------------
    //                                                String
    //                                                ------
    @Override
    public void registerString(String key, String value) {
        registerString(key, value, null);
    }

    @Override
    public void registerString(String key, String value, LocalDateTime expireDateTime) {
        if (expireDateTime == null) {
            try (Jedis jedis = kvsRedisPool.getResource()) {
                jedis.set(key, value);
            }
        } else {
            try (Jedis jedis = kvsRedisPool.getResource(); Pipeline pipeline = jedis.pipelined()) {
                pipeline.set(key, value);
                expireAt(pipeline, key, expireDateTime);
                pipeline.sync();
            }
        }
    }

    @Override
    public void registerMultiString(Map<String, String> keyValueMap) {
        registerMultiString(keyValueMap, null);
    }

    @Override
    public void registerMultiString(Map<String, String> keyValueMap, LocalDateTime expireDateTime) {
        if (keyValueMap.isEmpty()) {
            return;
        }

        String[] keysValues = keyValueMap.entrySet().stream().flatMap(keyValue -> {
            List<String> list = DfCollectionUtil.newArrayList();
            list.add(keyValue.getKey());
            list.add(keyValue.getValue());
            return list.stream();
        }).toArray(size -> new String[size]);

        if (expireDateTime == null) {
            try (Jedis jedis = kvsRedisPool.getResource()) {
                jedis.mset(keysValues);
            }
        } else {
            try (Jedis jedis = kvsRedisPool.getResource(); Pipeline pipeline = jedis.pipelined()) {
                pipeline.mset(keysValues);
                keyValueMap.keySet().stream().forEach(key -> expireAt(pipeline, key, expireDateTime));
                pipeline.sync();
            }
        }
    }

    // -----------------------------------------------------
    //                                                  List
    //                                                  ----
    @Override
    public void registerList(String key, List<String> list) {
        registerList(key, list, null);
    }

    @Override
    public void registerList(String key, List<String> list, LocalDateTime expireDateTime) {
        if (list.isEmpty()) {
            delete(key);
            return;
        }
        try (Jedis jedis = kvsRedisPool.getResource(); Pipeline pipeline = jedis.pipelined()) {
            pipeline.del(key);
            pipeline.rpush(key, list.stream().distinct().toArray(size -> new String[size]));
            if (expireDateTime != null) {
                expireAt(pipeline, key, expireDateTime);
            }
            pipeline.sync();
        }
    }

    @Override
    public void registerMultiList(Map<String, List<String>> keyListMap) {
        registerMultiList(keyListMap, null);
    }

    @Override
    public void registerMultiList(Map<String, List<String>> keyListMap, LocalDateTime expireDateTime) {
        try (Jedis jedis = kvsRedisPool.getResource(); Pipeline pipeline = jedis.pipelined()) {
            keyListMap.forEach((key, list) -> {
                pipeline.del(key);
                if (list.isEmpty()) {
                    return;
                }
                pipeline.rpush(key, list.stream().distinct().toArray(size -> new String[size]));
                if (expireDateTime != null) {
                    expireAt(pipeline, key, expireDateTime);
                }
            });
            pipeline.sync();
        }
    }

    // -----------------------------------------------------
    //                                                  Hash
    //                                                  ----
    @Override
    public void registerHash(String key, Map<String, String> hash) {
        registerHash(key, hash, null);

    }

    @Override
    public void registerHash(String key, Map<String, String> hash, LocalDateTime expireDateTime) {
        try (Jedis jedis = kvsRedisPool.getResource(); Pipeline pipeline = jedis.pipelined()) {
            pipeline.hmset(key, hash.entrySet().stream().collect(
                    Collectors.toMap(fieldValue -> fieldValue.getKey(), fieldValue -> Objects.toString(fieldValue.getValue(), ""))));
            if (expireDateTime != null) {
                expireAt(pipeline, key, expireDateTime);
            }
            pipeline.sync();
        }
    }

    @Override
    public boolean registerHashNx(String key, String field, String value) {
        return registerHashNx(key, field, value, null);
    }

    @Override
    public boolean registerHashNx(String key, String field, String value, LocalDateTime expireDateTime) {
        try (Jedis jedis = kvsRedisPool.getResource()) {
            final Long result = jedis.hsetnx(key, field, value);
            final boolean register = result == 1L;
            if (register) {
                if (expireDateTime != null) {
                    jedis.expireAt(key, TimeUnit.MILLISECONDS.toSeconds(new HandyDate(expireDateTime).getDate().getTime()));
                }
            }
            return register;
        }
    }

    @Override
    public void registerMultiHash(Map<String, Map<String, String>> keyHashMap) {
        registerMultiHash(keyHashMap, null);

    }

    @Override
    public void registerMultiHash(Map<String, Map<String, String>> keyHashMap, LocalDateTime expireDateTime) {
        try (Jedis jedis = kvsRedisPool.getResource(); Pipeline pipeline = jedis.pipelined()) {
            keyHashMap.forEach((key, hash) -> {
                pipeline.hmset(key, hash.entrySet().stream().collect(
                        Collectors.toMap(fieldValue -> fieldValue.getKey(), fieldValue -> Objects.toString(fieldValue.getValue(), ""))));
                if (expireDateTime != null) {
                    expireAt(pipeline, key, expireDateTime);
                }
            });
            pipeline.sync();
        }
    }

    // ===================================================================================
    //                                                                              Delete
    //                                                                              ======
    @Override
    public void delete(String key) {
        try (Jedis jedis = kvsRedisPool.getResource()) {
            jedis.del(key);
        }
    }

    @Override
    public void delete(String... keys) {
        if (keys.length == 0) {
            return;
        }
        try (Jedis jedis = kvsRedisPool.getResource()) {
            jedis.del(keys);
        }
    }

    @Override
    public void deleteHash(String key, Set<String> fieldList) {
        try (Jedis jedis = kvsRedisPool.getResource()) {
            jedis.hdel(key, fieldList.toArray(new String[fieldList.size()]));
        }
    }

    // ===================================================================================
    //                                                                              Exists
    //                                                                              ======
    @Override
    public boolean exists(String key) {
        try (Jedis jedis = kvsRedisPool.getResource()) {
            return jedis.exists(key);
        }
    }

    // ===================================================================================
    //                                                                                 TTL
    //                                                                                 ===
    @Override
    public Long ttl(String key) {
        try (Jedis jedis = kvsRedisPool.getResource()) {
            return jedis.ttl(key);
        }
    }

    @Override
    public void expireAt(String key, LocalDateTime expireDateTime) {
        try (Jedis jedis = kvsRedisPool.getResource(); Pipeline pipeline = jedis.pipelined()) {
            expireAt(pipeline, key, expireDateTime);
        }
    }

    /**
     * Set Time To Live to a value in KVS that is specified by the assigned key.
     * @param pipeline Pipeline (NotNull)
     * @param key Key for a value in KVS (NotNull)
     * @param expireDateTime Time To Live (NullAllowed: do not set ttl if null)
     */
    protected void expireAt(Pipeline pipeline, String key, LocalDateTime expireDateTime) {
        if (expireDateTime != null) {
            pipeline.expireAt(key, TimeUnit.MILLISECONDS.toSeconds(new HandyDate(expireDateTime).getDate().getTime()));
        }
    }

    // ===================================================================================
    //                                                                               Other
    //                                                                               =====
    @Override
    public int getNumActive() {
        return kvsRedisPool.getNumActive();
    }
}
