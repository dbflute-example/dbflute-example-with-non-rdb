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
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author FreeGen
 */
public interface KvsDelegator {

    // ===================================================================================
    //                                                                                Set
    //                                                                               =====
    void setKvsRedisPool(KvsRedisPool kvsRedisPool);

    // ===================================================================================
    //                                                                                Get
    //                                                                               =====
    // -----------------------------------------------------
    //                                                String
    //                                                ------
    /**
     * 指定されたキーに対応する文字列を返す。
     * @param key キー (NotNull)
     * @return 指定されたキーに対応する文字列 (NullAllowed)
     */
    String findString(String key);

    /**
     * 指定されたすべてのキーに対応する文字列を返す。
     * @param keyList キーリスト (NotNull)
     * @return 指定されたすべてのキーに対応する文字列 (NotNull)
     */
    List<String> findMultiString(List<String> keyList);

    // -----------------------------------------------------
    //                                                  List
    //                                                  ----
    /**
     * 指定されたキーに対応する文字列リストを返す。
     * <p>
     * このメソッドは、同一キーの処理においてスレッドセーフではありません。<br>
     * これは同一キーでの並列処理が業務上発生しない & 影響は軽微ですので、実装の簡易さのため整合性の担保は行わない方針だからです。
     * (不整合の例:このメソッドはKVSからデータを取得しますが、キャッシュヒットしなかった場合RDBの値でキャッシュをリフレッシュしする実装である場合、
     * 同じタイミングで別スレッドから読み込みが行われた場合不整合なデータが返却されます)
     * </p>
     * @param key キー (NotNull)
     * @return * 指定されたキーに対応する文字列リスト (NullAllowd)
     */
    List<String> findList(String key);

    /**
     * 指定されたすべてのキーに対応する文字列リストを返す。
     * @param keyList キーリスト (NotNull)
     * @return 指定されたすべてのキーに対応する文字列リスト (NotNull)
     */
    List<List<String>> findMultiList(List<String> keyList);

    // -----------------------------------------------------
    //                                                  Hash
    //                                                  ----
    /**
     * 指定されたキーに対応するハッシュを返す。
     * @param key キー (NotNull)
     * @return 指定されたキーに対応するハッシュ (NullAllowed)
     */
    Map<String, String> findHash(String key);

    /**
     * 指定されたキーに対応するハッシュを返す。
     * @param key キー (NotNull)
     * @param fieldList フィールドリスト (NotNull)
     * @return 指定されたキーに対応するハッシュ (NullAllowed)
     */
    List<String> findHash(String key, Set<String> fieldList);

    /**
     * 指定されたすべてのキーに対応するハッシュを返す。
     * @param keyList キーリスト (NotNull)
     * @param fieldList フィールドリスト (NotNull)
     * @return 指定されたすべてのキーに対応するハッシュ (NotNull)
     */
    List<List<String>> findMultiHash(List<String> keyList, Set<String> fieldList);

    // ===================================================================================
    //                                                                            Register
    //                                                                            ========
    // -----------------------------------------------------
    //                                                String
    //                                                ------
    /**
     * 指定されたキーに対して、文字列を登録する。
     * @param key キー (NotNull)
     * @param value 文字列 (NotNull)
     */
    void registerString(String key, String value);

    /**
     * 指定されたキーに対して、有効期限を明示して文字列を登録する。
     * @param key キー (NotNull)
     * @param value 文字列 (NotNull)
     * @param expireDateTime 有効期限 (NullAllowed: nullの場合はKVSサーバーの設定に依存)
     */
    void registerString(String key, String value, LocalDateTime expireDateTime);

    /**
     * 指定されたすべてのキーに対して、文字列を登録する。
     * @param keyValueMap キー、文字列MAP (NotNull)
     */
    void registerMultiString(Map<String, String> keyValueMap);

    /**
     * 指定されたすべてのキーに対して、有効期限を明示して文字列を登録する。
     * @param keyValueMap キー、文字列MAP (NotNull)
     * @param expireDateTime 有効期限 (NullAllowed: nullの場合はKVSサーバーの設定に依存)
     */
    void registerMultiString(Map<String, String> keyValueMap, LocalDateTime expireDateTime);

    // -----------------------------------------------------
    //                                                  List
    //                                                  ----
    /**
     * 指定されたキーに対して、文字列リストを登録する。
     * @param key キー (NotNull)
     * @param value 文字列リスト (NotNull)
     */
    void registerList(String key, List<String> value);

    /**
     * 指定されたキーに対して、有効期限を明示して文字列リストを登録する。
     * @param key キー (NotNull)
     * @param value 文字列リスト (NotNull)
     * @param expireDateTime 有効期限 (NullAllowed: nullの場合はKVSサーバーの設定に依存)
     */
    void registerList(String key, List<String> value, LocalDateTime expireDateTime);

    /**
     * 指定されたすべてのキーに対して、文字列リストを登録する。
     * @param keyValueMap キー、文字列リストMAP (NotNull)
     */
    void registerMultiList(Map<String, List<String>> keyValueMap);

    /**
     * 指定されたすべてのキーに対して、有効期限を明示して文字列リストを登録する。
     * @param keyValueMap キー、文字列リストMAP (NotNull)
     * @param expireDateTime 有効期限 (NullAllowed: nullの場合はKVSサーバーの設定に依存)
     */
    void registerMultiList(Map<String, List<String>> keyValueMap, LocalDateTime expireDateTime);

    // -----------------------------------------------------
    //                                                  Hash
    //                                                  ----
    /**
     * 指定されたキーに対して、ハッシュを登録する。
     * @param key キー (NotNull)
     * @param fieldValueMap フィールド、文字列Map (NotNull)
     */
    void registerHash(String key, Map<String, String> fieldValueMap);

    /**
     * 指定されたキーに対して、有効期限を明示してハッシュを登録する。
     * @param key キー (NotNull)
     * @param fieldValueMap フィールド、文字列Map (NotNull)
     * @param expireDateTime 有効期限 (NullAllowed: nullの場合はKVSサーバーの設定に依存)
     */
    void registerHash(String key, Map<String, String> fieldValueMap, LocalDateTime expireDateTime);

    /**
     * 指定されたすべてのキーに対して、ハッシュを登録する。
     * @param keyValueMap キー、フィールド、文字列Map (NotNull)
     */
    void registerMultiHash(Map<String, Map<String, String>> keyValueMap);

    /**
     * 指定されたすべてのキーに対して、有効期限を明示してハッシュを登録する。
     * @param keyValueMap キー、フィールド、文字列Map (NotNull)
     * @param expireDateTime 有効期限 (NullAllowed: nullの場合はKVSサーバーの設定に依存)
     */
    void registerMultiHash(Map<String, Map<String, String>> keyValueMap, LocalDateTime expireDateTime);

    // ===================================================================================
    //                                                                              Delete
    //                                                                              ======
    /**
     * Remove the specified key.
     * @param key Key to delete (NotNull)
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
     * @param key Key to delete (NotNull)
     * @param fieldList field list to delete (NotNull)
     */
    void deleteHash(String key, Set<String> fieldList);

    // ===================================================================================
    //                                                                              Exists
    //                                                                              ======
    /**
     * Test if the specified key exists.
     * @param key Key (NotNull)
     */
    boolean exists(String key);

    // ===================================================================================
    //                                                                                 TTL
    //                                                                                 ===
    /**
     * Return the ttl.
     * @param key Key (NotNull)
     * @return The ttl
     */
    Long ttl(String key);

    /**
     * set expireAt.
     * @param key Key (NotNull)
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
