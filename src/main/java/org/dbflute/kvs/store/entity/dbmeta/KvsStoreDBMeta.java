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
package org.dbflute.kvs.store.entity.dbmeta;

import java.util.List;
import java.util.Map;

import org.dbflute.kvs.store.cbean.KvsStoreConditionBean;
import org.dbflute.kvs.store.entity.KvsStoreEntity;

/**
 * @author FreeGen
 */
public interface KvsStoreDBMeta {

    /**
     * プロジェクト名を返す。
     * @return プロジェクト名
     */
    String getProjectName();

    /**
     * テーブル名を返す。
     * @return テーブル名
     */
    String getTableName();

    /**
     * KVSストアEntityを作成する。
     * @return KVSストアEntity
     */
    KvsStoreEntity newKvsStoreEntity();

    /**
     * すべてのカラムを受け入れる。
     * @param entity KVSストアEntity
     * @param map バリューMap
     */
    void acceptAllColumnMap(KvsStoreEntity entity, Map<String, ? extends Object> map);

    /**
     * キーリストを抽出する。
     * @param cb KVSストアConditionBean
     * @return キーリスト
     */
    List<Object> extractKeyList(KvsStoreConditionBean cb);

    /**
     * キーリストを抽出する。
     * @param entity KVSストアEntity
     * @return キーリスト
     */
    List<Object> extractKeyList(KvsStoreEntity entity);

    /**
     * すべてのカラムを抽出する。
     * @param entity KVSストアEntity
     * @return バリューMap
     */
    Map<String, Object> extractAllColumnMap(KvsStoreEntity entity);

    /**
     * キーカラムを検証する。
     * @param cb KVSストアConditionBean
     */
    void validateKeyColumn(KvsStoreConditionBean cb);

    /**
     * キーカラムを検証する。
     * @param entity KVSストアEntity
     */
    void validateKeyColumn(KvsStoreEntity entity);

    /**
     * すべてのカラムを検証する。
     * @param entity KVSストアEntity
     */
    void validateAllColumn(KvsStoreEntity entity);
}
