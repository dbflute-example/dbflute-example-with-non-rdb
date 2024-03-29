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
package org.dbflute.kvs.cache;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.dbflute.kvs.core.delegator.KvsDelegator;

/**
 * @author FreeGen
 */
public class KvsCacheManagerImpl implements KvsCacheManager {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private KvsDelegator kvsCacheDelegator;

    // ===================================================================================
    //                                                                                Get
    //                                                                               =====
    // -----------------------------------------------------
    //                                                String
    //                                                ------
    @Override
    public String findString(String key) {
        return kvsCacheDelegator.findString(key);
    }

    @Override
    public List<String> findMultiString(List<String> keyList) {
        return kvsCacheDelegator.findMultiString(keyList);
    }

    // -----------------------------------------------------
    //                                                  List
    //                                                  ----
    @Override
    public List<String> findList(String key) {
        return kvsCacheDelegator.findList(key);
    }

    @Override
    public List<List<String>> findMultiList(List<String> keyList) {
        return kvsCacheDelegator.findMultiList(keyList);
    }

    // -----------------------------------------------------
    //                                                  Hash
    //                                                  ----
    @Override
    public List<String> findHash(String key, Set<String> fieldList) {
        return kvsCacheDelegator.findHash(key, fieldList);
    }

    @Override
    public List<List<String>> findMultiHash(List<String> keyList, Set<String> fieldList) {
        return kvsCacheDelegator.findMultiHash(keyList, fieldList);
    }

    // ===================================================================================
    //                                                                            Register
    //                                                                            ========
    // -----------------------------------------------------
    //                                                String
    //                                                ------
    @Override
    public void registerString(String key, String value) {
        kvsCacheDelegator.registerString(key, value);
    }

    @Override
    public void registerString(String key, String value, LocalDateTime expireDateTime) {
        kvsCacheDelegator.registerString(key, value, expireDateTime);
    }

    @Override
    public void registerMultiString(Map<String, String> combinationKeyValueMap) {
        kvsCacheDelegator.registerMultiString(combinationKeyValueMap);
    }

    @Override
    public void registerMultiString(Map<String, String> combinationKeyValueMap, LocalDateTime expireDateTime) {
        kvsCacheDelegator.registerMultiString(combinationKeyValueMap, expireDateTime);
    }

    // -----------------------------------------------------
    //                                                  List
    //                                                  ----
    @Override
    public void registerList(String key, List<String> list) {
        kvsCacheDelegator.registerList(key, list);
    }

    @Override
    public void registerList(String key, List<String> list, LocalDateTime expireDateTime) {
        kvsCacheDelegator.registerList(key, list, expireDateTime);
    }

    @Override
    public void registerMultiList(Map<String, List<String>> combinationKeyListMap) {
        kvsCacheDelegator.registerMultiList(combinationKeyListMap);

    }

    @Override
    public void registerMultiList(Map<String, List<String>> combinationKeyListMap, LocalDateTime expireDateTime) {
        kvsCacheDelegator.registerMultiList(combinationKeyListMap, expireDateTime);
    }

    // -----------------------------------------------------
    //                                                  Hash
    //                                                  ----
    @Override
    public void registerHash(String key, Map<String, String> hash) {
        kvsCacheDelegator.registerHash(key, hash);
    }

    @Override
    public void registerHash(String key, Map<String, String> hash, LocalDateTime expireDateTime) {
        kvsCacheDelegator.registerHash(key, hash, expireDateTime);
    }

    @Override
    public void registerMultiHash(Map<String, Map<String, String>> combinationKeyHashMap) {
        kvsCacheDelegator.registerMultiHash(combinationKeyHashMap);
    }

    @Override
    public void registerMultiHash(Map<String, Map<String, String>> combinationKeyHashMap, LocalDateTime expireDateTime) {
        kvsCacheDelegator.registerMultiHash(combinationKeyHashMap, expireDateTime);
    }

    // ===================================================================================
    //                                                                              Delete
    //                                                                              ======
    @Override
    public void delete(String key) {
        kvsCacheDelegator.delete(key);
    }

    @Override
    public void delete(String... keys) {
        kvsCacheDelegator.delete(keys);
    }

    // ===================================================================================
    //                                                                                 TTL
    //                                                                                 ===
    @Override
    public Long ttl(String key) {
        return kvsCacheDelegator.ttl(key);
    }

    // ===================================================================================
    //                                                                               Other
    //                                                                               =====
    @Override
    public int getNumActive() {
        return kvsCacheDelegator.getNumActive();
    }
}
