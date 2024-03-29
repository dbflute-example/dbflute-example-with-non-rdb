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
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author FreeGen
 */
public interface KvsDelegator {

    // ===================================================================================
    //                                                                                 Get
    //                                                                                 ===
    // -----------------------------------------------------
    //                                                String
    //                                                ------
    /**
     * Return value for the specified key.
     * @param key key (NotNull)
     * @return value for the specified key (NullAllowed)
     */
    String findString(String key);

    /**
     * Return value list for the specified key list.
     * @param keyList KeyList (NotNull)
     * @return value list for the specified key list (NotNull)
     */
    List<String> findMultiString(List<String> keyList);

    // -----------------------------------------------------
    //                                                  List
    //                                                  ----
    /**
     * Return list for the specified key.
     * <p>
     * このメソッドは、同一キーの処理においてスレッドセーフではありません。<br>
     * これは同一キーでの並列処理が業務上発生しない & 影響は軽微ですので、実装の簡易さのため整合性の担保は行わない方針だからです。
     * (不整合の例:このメソッドはKVSからデータを取得しますが、キャッシュヒットしなかった場合RDBの値でキャッシュをリフレッシュしする実装である場合、
     * 同じタイミングで別スレッドから読み込みが行われた場合不整合なデータが返却されます)
     * </p>
     * @param key key (NotNull)
     * @return list for the specified key (NotNull)
     */
    List<String> findList(String key);

    /**
     * Return list for the specified key list.
     * @param keyList key list (NotNull)
     * @return list for the specified key list (NotNull)
     */
    List<List<String>> findMultiList(List<String> keyList);

    // -----------------------------------------------------
    //                                                  Hash
    //                                                  ----
    /**
     * Return hash for the specified key.
     * @param key key (NotNull)
     * @return hash for the specified key (NotNull)
     */
    Map<String, String> findHash(String key);

    /**
     * Return hash field value list for the specified key.
     * @param key key (NotNull)
     * @param fieldList field list (NotNull)
     * @return hash field value list for the specified key (NotNull)
     */
    List<String> findHash(String key, Set<String> fieldList);

    /**
     * Return hash field value list for the specified key.
     * @param keyList key list (NotNull)
     * @param fieldList field list (NotNull)
     * @return hash field value list for the specified key list (NotNull)
     */
    List<List<String>> findMultiHash(List<String> keyList, Set<String> fieldList);

    // ===================================================================================
    //                                                                            Register
    //                                                                            ========
    // -----------------------------------------------------
    //                                                String
    //                                                ------
    /**
     * Register value for the specified key.
     * @param key key (NotNull)
     * @param value value (NotNull)
     */
    void registerString(String key, String value);

    /**
     * Register value for the specified key. (Specify expire date time.)
     * @param key key (NotNull)
     * @param value value (NotNull)
     * @param expireDateTime expire date time (NullAllowed: If it is null, it depends on the KVS server setting)
     */
    void registerString(String key, String value, LocalDateTime expireDateTime);

    /**
     * Register the specified combination of key and value.
     * @param combinationKeyValueMap combination of key and value (NotNull)
     */
    void registerMultiString(Map<String, String> combinationKeyValueMap);

    /**
     * Register the specified combination of key and value. (Specify expire date time.)
     * @param combinationKeyValueMap combination of key and value (NotNull)
     * @param expireDateTime expire date time (NullAllowed: If it is null, it depends on the KVS server setting)
     */
    void registerMultiString(Map<String, String> combinationKeyValueMap, LocalDateTime expireDateTime);

    // -----------------------------------------------------
    //                                                  List
    //                                                  ----
    /**
     * Register list for the specified key.
     * @param key key (NotNull)
     * @param list list (NotNull)
     */
    void registerList(String key, List<String> list);

    /**
     * Register list for the specified key. (Specify expire date time.)
     * @param key key (NotNull)
     * @param list list (NotNull)
     * @param expireDateTime expire date time (NullAllowed: If it is null, it depends on the KVS server setting)
     */
    void registerList(String key, List<String> list, LocalDateTime expireDateTime);

    /**
     * Register a combination of the specified key and list.
     * @param combinationKeyListMap combination of key list map (NotNull)
     */
    void registerMultiList(Map<String, List<String>> combinationKeyListMap);

    /**
     * Register a combination of the specified key and list. (Specify expire date time.)
     * @param combinationKeyListMap combination of key list map (NotNull)
     * @param expireDateTime expire date time (NullAllowed: If it is null, it depends on the KVS server setting)
     */
    void registerMultiList(Map<String, List<String>> combinationKeyListMap, LocalDateTime expireDateTime);

    // -----------------------------------------------------
    //                                                  Hash
    //                                                  ----
    /**
     * Register hash for the specified key.
     * @param key key (NotNull)
     * @param hash hash (NotNull)
     */
    void registerHash(String key, Map<String, String> hash);

    /**
     * Register hash for the specified key. (Specify expire date time.)
     * @param key key (NotNull)
     * @param hash hash (NotNull)
     * @param expireDateTime expire date time (NullAllowed: If it is null, it depends on the KVS server setting)
     */
    void registerHash(String key, Map<String, String> hash, LocalDateTime expireDateTime);

    /**
     * If the specified field does not exist in the hash of the specified key, register the hash of the specified key.
     * @param key key (NotNull)
     * @param field field (NotNull)
     * @param value value (NotNull)
     * @return If the field already exists, false is returned, otherwise if a new field is created true is returned.
     */
    boolean registerHashNx(String key, String field, String value);

    /**
     * If the specified field does not exist in the hash of the specified key, register the hash of the specified key. (Specify expire date time.)
     * @param key key (NotNull)
     * @param field field (NotNull)
     * @param value value (NotNull)
     * @param expireDateTime expire date time (NullAllowed: If it is null, it depends on the KVS server setting)
     * @return If the field already exists, false is returned, otherwise if a new field is created true is returned.
     */
    boolean registerHashNx(String key, String field, String value, LocalDateTime expireDateTime);

    /**
     * Register a combination of the specified key and hash.
     * @param combinationKeyHashMap combination of key hash map (NotNull)
     */
    void registerMultiHash(Map<String, Map<String, String>> combinationKeyHashMap);

    /**
     * Register a combination of the specified key and hash. (Specify expire date time.)
     * @param combinationKeyHashMap combination of key hash map (NotNull)
     * @param expireDateTime expire date time (NullAllowed: If it is null, it depends on the KVS server setting)
     */
    void registerMultiHash(Map<String, Map<String, String>> combinationKeyHashMap, LocalDateTime expireDateTime);

    // ===================================================================================
    //                                                                              Delete
    //                                                                              ======
    /**
     * Remove the specified key.
     * @param key key to delete (NotNull)
     */
    void delete(String key);

    /**
     * Remove the specified keys.
     * @param keys Keys to delete (NotNull)
     */
    void delete(String... keys);

    // -----------------------------------------------------
    //                                                  Hash
    //                                                  ----
    /**
     * Remove the specified field from an hash stored at key.
     * @param key key to delete (NotNull)
     * @param fieldList field list to delete (NotNull)
     */
    void deleteHash(String key, Set<String> fieldList);

    // ===================================================================================
    //                                                                              Exists
    //                                                                              ======
    /**
     * Test if the specified key exists.
     * @param key key (NotNull)
     */
    boolean exists(String key);

    // ===================================================================================
    //                                                                                 TTL
    //                                                                                 ===
    /**
     * Return the ttl.
     * @param key key (NotNull)
     * @return The ttl
     */
    Long ttl(String key);

    /**
     * set expireAt.
     * @param key key (NotNull)
     * @param expireDateTime expire date time (NotNull)
     */
    void expireAt(String key, LocalDateTime expireDateTime);

    // ===================================================================================
    //                                                                               Other
    //                                                                               =====
    /**
     * Get the number of active connections in connection pool.
     * @return The number of active connections in connection pool
     */
    int getNumActive();
}
