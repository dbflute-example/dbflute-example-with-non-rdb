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
package org.dbflute.kvs.cache.delegator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dbflute.kvs.core.delegator.AbstractKvsRedisDelegator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Because this class is cache setting and retrieval processing,
 * we should continue processing even if an error occurs, so ignore the error.
 * @author FreeGen
 */
public class KvsCacheRedisDelegator extends AbstractKvsRedisDelegator {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** Slf4jのロガー。 */
    private static final Logger logger = LoggerFactory.getLogger(KvsCacheRedisDelegator.class);

    // ===================================================================================
    //                                                                                Get
    //                                                                               =====
    // -----------------------------------------------------
    //                                                String
    //                                                ------
    @Override
    public String findString(String key) {
        try {
            return super.findString(key);
        } catch (Exception e) {
            logger.warn("kvs access error. key={}", key, e);
            return null;
        }
    }

    @Override
    public List<String> findMultiString(List<String> keyList) {
        try {
            return super.findMultiString(keyList);
        } catch (Exception e) {
            logger.warn("kvs access error. keyList={}", keyList, e);
            return new ArrayList<String>();
        }
    }

    // -----------------------------------------------------
    //                                                  List
    //                                                  ----
    @Override
    public List<String> findList(String key) {
        try {
            return super.findList(key);
        } catch (Exception e) {
            logger.warn("kvs access error. key={}", key, e);
            return new ArrayList<String>();
        }
    }

    @Override
    public List<List<String>> findMultiList(List<String> keyList) {
        try {
            return super.findMultiList(keyList);
        } catch (Exception e) {
            logger.warn("kvs access error. keyList={}", keyList, e);
            return new ArrayList<List<String>>();
        }
    }

    // -----------------------------------------------------
    //                                                  Hash
    //                                                  ----
    @Override
    public Map<String, String> findHash(String key) {
        try {
            return super.findHash(key);
        } catch (Exception e) {
            logger.warn("kvs access error. key={}", key, e);
            return Collections.emptyMap();
        }
    }

    @Override
    public List<String> findHash(String key, Set<String> fieldList) {
        try {
            return super.findHash(key, fieldList);
        } catch (Exception e) {
            logger.warn("kvs access error. key={}, fieldList={}", key, fieldList, e);
            return new ArrayList<String>();
        }
    }

    @Override
    public List<List<String>> findMultiHash(List<String> keyList, Set<String> fieldList) {
        try {
            return super.findMultiHash(keyList, fieldList);
        } catch (Exception e) {
            logger.warn("kvs access error. keyList={}, fieldList={}", keyList, fieldList, e);
            return new ArrayList<List<String>>();
        }
    }

    // ===================================================================================
    //                                                                            Register
    //                                                                            ========
    // -----------------------------------------------------
    //                                                String
    //                                                ------
    @Override
    public void registerString(String key, String value) {
        try {
            super.registerString(key, value);
        } catch (Exception e) {
            logger.warn("kvs access error. key={}, value={}", key, value, e);
            return;
        }
    }

    @Override
    public void registerString(String key, String value, LocalDateTime expireDateTime) {
        try {
            super.registerString(key, value, expireDateTime);
        } catch (Exception e) {
            logger.warn("kvs access error. key={}, value={}, expireDateTime={}", key, value, expireDateTime, e);
            return;
        }
    }

    @Override
    public void registerMultiString(Map<String, String> keyValueMap) {
        try {
            super.registerMultiString(keyValueMap);
        } catch (Exception e) {
            logger.warn("kvs access error. keyValueMap={}", keyValueMap, e);
            return;
        }
    }

    @Override
    public void registerMultiString(Map<String, String> keyValueMap, LocalDateTime expireDateTime) {
        try {
            super.registerMultiString(keyValueMap, expireDateTime);
        } catch (Exception e) {
            logger.warn("kvs access error. keyValueMap={}, expireDateTime={}", keyValueMap, expireDateTime, e);
            return;
        }
    }

    // -----------------------------------------------------
    //                                                  List
    //                                                  ----
    @Override
    public void registerList(String key, List<String> list) {
        try {
            super.registerList(key, list);
        } catch (Exception e) {
            logger.warn("kvs access error. key={}, list={}", key, list, e);
            return;
        }
    }

    @Override
    public void registerList(String key, List<String> list, LocalDateTime expireDateTime) {
        try {
            super.registerList(key, list, expireDateTime);
        } catch (Exception e) {
            logger.warn("kvs access error. key={}, list={}, expireDateTime={}", key, list, expireDateTime, e);
            return;
        }
    }

    @Override
    public void registerMultiList(Map<String, List<String>> keyListMap) {
        try {
            super.registerMultiList(keyListMap);
        } catch (Exception e) {
            logger.warn("kvs access error. keyListMap={}", keyListMap, e);
            return;
        }
    }

    @Override
    public void registerMultiList(Map<String, List<String>> keyListMap, LocalDateTime expireDateTime) {
        try {
            super.registerMultiList(keyListMap, expireDateTime);
        } catch (Exception e) {
            logger.warn("kvs access error. keyListMap={}, expireDateTime={}", keyListMap, expireDateTime, e);
            return;
        }
    }

    // -----------------------------------------------------
    //                                                  Hash
    //                                                  ----
    @Override
    public void registerHash(String key, Map<String, String> hash) {
        try {
            super.registerHash(key, hash);
        } catch (Exception e) {
            logger.warn("kvs access error. key={}, hash={}", key, hash, e);
            return;
        }
    }

    @Override
    public void registerHash(String key, Map<String, String> hash, LocalDateTime expireDateTime) {
        try {
            super.registerHash(key, hash, expireDateTime);
        } catch (Exception e) {
            logger.warn("kvs access error. key={}, hash={}, expireDateTime={}", key, hash, expireDateTime, e);
            return;
        }
    }

    @Override
    public boolean registerHashNx(String key, String field, String value) {
        try {
            return registerHashNx(key, field, value);
        } catch (Exception e) {
            logger.warn("kvs access error. key={}, field={}, value={}", key, field, value, e);
            return false;
        }
    }

    @Override
    public boolean registerHashNx(String key, String field, String value, LocalDateTime expireDateTime) {
        try {
            return registerHashNx(key, field, value, expireDateTime);
        } catch (Exception e) {
            logger.warn("kvs access error. key={}, field={}, value={}, expireDateTime={}", key, field, value, expireDateTime, e);
            return false;
        }
    }

    @Override
    public void registerMultiHash(Map<String, Map<String, String>> keyValueMap) {
        try {
            super.registerMultiHash(keyValueMap);
        } catch (Exception e) {
            logger.warn("kvs access error. keyValueMap={}", keyValueMap, e);
            return;
        }
    }

    @Override
    public void registerMultiHash(Map<String, Map<String, String>> keyValueMap, LocalDateTime expireDateTime) {
        try {
            super.registerMultiHash(keyValueMap, expireDateTime);
        } catch (Exception e) {
            logger.warn("kvs access error. keyValueMap={}, expireDateTime={}", keyValueMap, expireDateTime, e);
            return;
        }
    }

    // ===================================================================================
    //                                                                              Delete
    //                                                                              ======
    @Override
    public void delete(String key) {
        try {
            super.delete(key);
        } catch (Exception e) {
            logger.warn("kvs access error. key={}", key, e);
            return;
        }
    }

    @Override
    public void delete(String... keys) {
        try {
            super.delete(keys);
        } catch (Exception e) {
            logger.warn("kvs access error. keys={}", Arrays.asList(keys), e);
            return;
        }
    }

    @Override
    public void deleteHash(String key, Set<String> fieldList) {
        try {
            super.deleteHash(key, fieldList);
        } catch (Exception e) {
            logger.warn("kvs access error. key={}, fieldList={}", key, fieldList, e);
            return;
        }
    }

    // ===================================================================================
    //                                                                              Exists
    //                                                                              ======
    @Override
    public boolean exists(String key) {
        try {
            return super.exists(key);
        } catch (Exception e) {
            logger.warn("kvs access error. key={}", key, e);
            return false;
        }
    }

    // ===================================================================================
    //                                                                                 TTL
    //                                                                                 ===
    @Override
    public Long ttl(String key) {
        try {
            return super.ttl(key);
        } catch (Exception e) {
            logger.warn("kvs access error. key={}", key, e);
            return -1L;
        }
    }

    @Override
    public void expireAt(String key, LocalDateTime expireDateTime) {
        try {
            super.expireAt(key, expireDateTime);
        } catch (Exception e) {
            logger.warn("kvs access error. key={}, expireDateTime={}", key, expireDateTime, e);
            return;
        }
    }
}
