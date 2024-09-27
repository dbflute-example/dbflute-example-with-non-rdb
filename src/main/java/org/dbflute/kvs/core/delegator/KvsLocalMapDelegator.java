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
import java.time.temporal.ChronoUnit;
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
    private static final Map<String, LocalDateTime> KEY_EXPIRE_DATE_TIME_MAP = new ConcurrentHashMap<String, LocalDateTime>();

    // ===================================================================================
    //                                                                                Get
    //                                                                               =====
    // -----------------------------------------------------
    //                                                String
    //                                                ------
    @Override
    public String findString(String key) {
        Object value = getInExpireDateTime(key);
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
        Object value = getInExpireDateTime(key);
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
    public Map<String, String> findHash(String key) {
        Object value = getInExpireDateTime(key);
        if (value == null) {
            value = DfCollectionUtil.newLinkedHashMap();
        }
        if (value instanceof Map<?, ?>) {
            @SuppressWarnings("unchecked")
            Map<String, String> map = (Map<String, String>) value;
            return map;
        } else {
            String msg = "The type of the return value is invalid. expected=%s, actual=%s";
            throw new KvsException(String.format(msg, Map.class, value.getClass()));
        }
    }

    @Override
    public List<String> findHash(String key, Set<String> fieldList) {
        Object value = getInExpireDateTime(key);
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
    public void registerString(String key, String value, LocalDateTime expireDateTime) {
        registerMultiString(DfCollectionUtil.newLinkedHashMap(key, value), expireDateTime);
    }

    @Override
    public void registerMultiString(Map<String, String> keyValueMap) {
        registerMultiString(keyValueMap, null);
    }

    @Override
    public void registerMultiString(Map<String, String> keyValueMap, LocalDateTime expireDateTime) {
        keyValueMap.entrySet().forEach(keyValue -> {
            KEY_VALUE_MAP.put(keyValue.getKey(), keyValue.getValue());
            if (expireDateTime != null) {
                KEY_EXPIRE_DATE_TIME_MAP.put(keyValue.getKey(), expireDateTime);
            }
        });
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
        registerMultiList(DfCollectionUtil.newLinkedHashMap(key, list), expireDateTime);
    }

    @Override
    public void registerMultiList(Map<String, List<String>> keyListMap) {
        registerMultiList(keyListMap, null);
    }

    @Override
    public void registerMultiList(Map<String, List<String>> keyListMap, LocalDateTime expireDateTime) {
        keyListMap.forEach((key, list) -> {
            KEY_VALUE_MAP.put(key, list);
            if (expireDateTime != null) {
                KEY_EXPIRE_DATE_TIME_MAP.put(key, expireDateTime);
            }
        });
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
        registerMultiHash(DfCollectionUtil.newLinkedHashMap(key, hash), expireDateTime);
    }

    @Override
    public boolean registerHashNx(String key, String field, String value) {
        return registerHashNx(key, field, value, null);
    }

    @Override
    public boolean registerHashNx(String key, String field, String value, LocalDateTime expireDateTime) {
        synchronized (KEY_VALUE_MAP) {
            Object holdValue = getInExpireDateTime(key);
            if (holdValue == null) {
                KEY_VALUE_MAP.put(key, DfCollectionUtil.newLinkedHashMap(field, value));
                if (expireDateTime != null) {
                    KEY_EXPIRE_DATE_TIME_MAP.put(key, expireDateTime);
                }
                return true;
            }
            if (holdValue instanceof Map<?, ?>) {
                @SuppressWarnings("all")
                Map<String, String> map = (Map<String, String>) holdValue;
                if (map.containsKey(field)) {
                    return false;
                }
                map.put(field, value);
                if (expireDateTime != null) {
                    KEY_EXPIRE_DATE_TIME_MAP.put(key, expireDateTime);
                }
            }
            return true;
        }
    }

    @Override
    public void registerMultiHash(Map<String, Map<String, String>> keyHashMap) {
        registerMultiHash(keyHashMap, null);
    }

    @Override
    public void registerMultiHash(Map<String, Map<String, String>> keyHashMap, LocalDateTime expireDateTime) {
        keyHashMap.forEach((key, hash) -> {
            Object value = getInExpireDateTime(key);
            if (value == null) {
                KEY_VALUE_MAP.put(key, hash);
                if (expireDateTime != null) {
                    KEY_EXPIRE_DATE_TIME_MAP.put(key, expireDateTime);
                }
                return;
            }
            if (hash instanceof Map<?, ?>) {
                @SuppressWarnings("all")
                Map<String, String> map = (Map<String, String>) value;
                map.putAll(hash);
                if (expireDateTime != null) {
                    KEY_EXPIRE_DATE_TIME_MAP.put(key, expireDateTime);
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
        KEY_EXPIRE_DATE_TIME_MAP.remove(key);
    }

    @Override
    public void delete(String... keys) {
        Arrays.stream(keys).forEach(key -> {
            KEY_VALUE_MAP.remove(key);
            KEY_EXPIRE_DATE_TIME_MAP.remove(key);
        });
    }

    // -----------------------------------------------------
    //                                                  Hash
    //                                                  ----
    @Override
    public void deleteHash(String key, Set<String> fieldList) {
        Object value = getInExpireDateTime(key);
        if (value == null) {
            return;
        }

        if (value instanceof Map<?, ?>) {
            @SuppressWarnings("unchecked")
            Map<String, String> map = (Map<String, String>) value;
            fieldList.stream().forEach(field -> map.remove(field));
        } else {
            String msg = "The type of the return value is invalid. expected=%s, actual=%s";
            throw new KvsException(String.format(msg, Map.class, value.getClass()));
        }
    }

    // ===================================================================================
    //                                                                              Exists
    //                                                                              ======
    @Override
    public boolean exists(String key) {
        return (getInExpireDateTime(key) != null);
    }

    // ===================================================================================
    //                                                                                 TTL
    //                                                                                 ===
    @Override
    public Long ttl(String key) {
        if (!KEY_EXPIRE_DATE_TIME_MAP.containsKey(key)) {
            return -1L;
        }

        final LocalDateTime current = LocalDateTime.now();
        final LocalDateTime expire = KEY_EXPIRE_DATE_TIME_MAP.get(key);
        return ChronoUnit.SECONDS.between(current, expire);
    }

    @Override
    public void expireAt(String key, LocalDateTime expireDateTime) {
        if (!KEY_VALUE_MAP.containsKey(key)) {
            String msg = "Key don't exist. Key=%s";
            throw new KvsException(String.format(msg, key));
        }
        KEY_EXPIRE_DATE_TIME_MAP.put(key, expireDateTime);
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
    protected Object getInExpireDateTime(String key) {
        LocalDateTime expireDateTime = KEY_EXPIRE_DATE_TIME_MAP.get(key);
        LocalDateTime currentDateTime = getTimeManager().currentDateTime();
        if (expireDateTime == null || expireDateTime.compareTo(currentDateTime) >= 0) {
            return KEY_VALUE_MAP.get(key);
        }
        KEY_VALUE_MAP.remove(key);
        KEY_EXPIRE_DATE_TIME_MAP.remove(key);
        return null;
    }

    private TimeManager getTimeManager() {
        return ContainerUtil.getComponent(TimeManager.class);
    }
}
