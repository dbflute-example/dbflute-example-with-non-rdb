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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.dbflute.kvs.core.exception.KvsException;
import org.dbflute.util.DfCollectionUtil;
import org.lastaflute.core.time.TimeManager;
import org.lastaflute.core.util.ContainerUtil;

/**
 * @author FreeGen
 */
public class KvsLocalMapDelegator implements KvsDelegator {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final Map<String, Object> KEY_VALUE_MAP = new ConcurrentHashMap<String, Object>();
    private static final Map<String, LocalDateTime> KEY_AVAILABLEDATETIME_MAP = new ConcurrentHashMap<String, LocalDateTime>();

    // ===================================================================================
    //                                                                                Set
    //                                                                               =====
    @Override
    public void setKvsRedisPool(KvsRedisPool kvsRedisPool) {
        // dummy
    }

    // ===================================================================================
    //                                                                                Get
    //                                                                               =====
    // -----------------------------------------------------
    //                                                String
    //                                                ------
    @Override
    public String findString(String key) {
        Object value = getInAvailableDateTime(key);
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String) value;
        } else {
            String msg = "The type of the return value is invalid. expected=%s, actual=%s";
            throw new KvsException(String.format(msg, String.class, value.getClass()));
        }
    }

    @Override
    public List<String> findMultiString(List<String> keyList) {
        return keyList.stream().map(key -> findString(key)).collect(Collectors.toList());
    }

    // -----------------------------------------------------
    //                                                  List
    //                                                  ----
    @Override
    public List<String> findList(String key) {
        Object value = getInAvailableDateTime(key);
        if (value == null) {
            return Collections.emptyList();
        }
        if (value instanceof List<?>) {
            @SuppressWarnings("unchecked")
            List<String> list = (List<String>) value;
            return list.stream().distinct().collect(Collectors.toList());
        } else {
            String msg = "The type of the return value is invalid. expected=%s, actual=%s";
            throw new KvsException(String.format(msg, List.class, value.getClass()));
        }
    }

    @Override
    public List<List<String>> findMultiList(List<String> keyList) {
        return keyList.stream().map(key -> findList(key)).collect(Collectors.toList());
    }

    // -----------------------------------------------------
    //                                                  Hash
    //                                                  ----
    @Override
    public List<String> findHash(String key, Set<String> fieldList) {
        Object value = getInAvailableDateTime(key);
        if (value == null) {
            value = DfCollectionUtil.newLinkedHashMap();
        }
        if (value instanceof Map<?, ?>) {
            @SuppressWarnings("unchecked")
            Map<String, String> map = (Map<String, String>) value;
            return fieldList.stream().map(field -> map.get(field)).collect(Collectors.toList());
        } else {
            String msg = "The type of the return value is invalid. expected=%s, actual=%s";
            throw new KvsException(String.format(msg, Map.class, value.getClass()));
        }
    }

    @Override
    public List<List<String>> findMultiHash(List<String> keyList, Set<String> fieldList) {
        return keyList.stream().map(key -> findHash(key, fieldList)).collect(Collectors.toList());
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
    public void registerString(String key, String value, LocalDateTime availableDateTime) {
        registerMultiString(DfCollectionUtil.newLinkedHashMap(key, value), availableDateTime);
    }

    @Override
    public void registerMultiString(Map<String, String> keyValueMap) {
        registerMultiString(keyValueMap, null);
    }

    @Override
    public void registerMultiString(Map<String, String> keyValueMap, LocalDateTime availableDateTime) {
        keyValueMap.entrySet().forEach(keyValue -> {
            KEY_VALUE_MAP.put(keyValue.getKey(), keyValue.getValue());
            if (availableDateTime != null) {
                KEY_AVAILABLEDATETIME_MAP.put(keyValue.getKey(), availableDateTime);
            }
        });
    }

    // -----------------------------------------------------
    //                                                  List
    //                                                  ----
    @Override
    public void registerList(String key, List<String> value) {
        registerList(key, value, null);
    }

    @Override
    public void registerList(String key, List<String> value, LocalDateTime availableDateTime) {
        registerMultiList(DfCollectionUtil.newLinkedHashMap(key, value), availableDateTime);
    }

    @Override
    public void registerMultiList(Map<String, List<String>> keyValueMap) {
        registerMultiList(keyValueMap, null);
    }

    @Override
    public void registerMultiList(Map<String, List<String>> keyValueMap, LocalDateTime availableDateTime) {
        keyValueMap.forEach((key, value) -> {
            KEY_VALUE_MAP.put(key, value);
            if (availableDateTime != null) {
                KEY_AVAILABLEDATETIME_MAP.put(key, availableDateTime);
            }
        });
    }

    // -----------------------------------------------------
    //                                                  Hash
    //                                                  ----
    @Override
    public void registerHash(String key, Map<String, String> fieldValueMap) {
        registerHash(key, fieldValueMap, null);
    }

    @Override
    public void registerHash(String key, Map<String, String> fieldValueMap, LocalDateTime availableDateTime) {
        registerMultiHash(DfCollectionUtil.newLinkedHashMap(key, fieldValueMap), availableDateTime);
    }

    @Override
    public void registerMultiHash(Map<String, Map<String, String>> keyValueMap) {
        registerMultiHash(keyValueMap, null);
    }

    @Override
    public void registerMultiHash(Map<String, Map<String, String>> keyValueMap, LocalDateTime availableDateTime) {
        keyValueMap.forEach((key, fieldValueMap) -> {
            Object value = getInAvailableDateTime(key);
            if (value == null) {
                KEY_VALUE_MAP.put(key, fieldValueMap);
                if (availableDateTime != null) {
                    KEY_AVAILABLEDATETIME_MAP.put(key, availableDateTime);
                }
                return;
            }
            if (fieldValueMap instanceof Map<?, ?>) {
                @SuppressWarnings("all")
                Map<String, String> map = (Map<String, String>) value;
                map.putAll(fieldValueMap);
                if (availableDateTime != null) {
                    KEY_AVAILABLEDATETIME_MAP.put(key, availableDateTime);
                }
            }
        });
    }

    // ===================================================================================
    //                                                                              Delete
    //                                                                              ======
    @Override
    public void delete(String key) {
        KEY_VALUE_MAP.remove(key);
        KEY_AVAILABLEDATETIME_MAP.remove(key);
    }

    @Override
    public void delete(String... keys) {
        Arrays.stream(keys).forEach(key -> {
            KEY_VALUE_MAP.remove(key);
            KEY_AVAILABLEDATETIME_MAP.remove(key);
        });
    }

    // ===================================================================================
    //                                                                               Other
    //                                                                               =====
    @Override
    public int getNumActive() {
        return 1;
    }

    // ===================================================================================
    //                                                                        Small Helper
    //                                                                        ============
    protected Object getInAvailableDateTime(String key) {
        LocalDateTime availableDateTime = KEY_AVAILABLEDATETIME_MAP.get(key);
        LocalDateTime currentDateTime = getTimeManager().currentDateTime();
        if (availableDateTime == null || availableDateTime.compareTo(currentDateTime) >= 0) {
            return KEY_VALUE_MAP.get(key);
        }
        KEY_VALUE_MAP.remove(key);
        KEY_AVAILABLEDATETIME_MAP.remove(key);
        return null;
    }

    private TimeManager getTimeManager() {
        return ContainerUtil.getComponent(TimeManager.class);
    }
}
