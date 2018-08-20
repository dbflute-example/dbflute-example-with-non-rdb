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
package org.dbflute.kvs.store.facade;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.dbflute.bhv.exception.BehaviorExceptionThrower;
import org.dbflute.kvs.store.KvsStoreConverterHandler;
import org.dbflute.kvs.store.KvsStoreManager;
import org.dbflute.kvs.store.entity.KvsStoreEntity;
import org.dbflute.kvs.store.entity.dbmeta.KvsStoreDBMeta;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.util.DfCollectionUtil;
import org.dbflute.util.DfTypeUtil;

/**
 * @author FreeGen
 */
public abstract class AbstractKvsStoreHashFacade implements KvsStoreFacade {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    protected static final String KEY_DELIMITER = "|";

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private KvsStoreManager kvsStoreManager;

    protected KvsStoreConverterHandler kvsStoreConverterHandler = new KvsStoreConverterHandler();

    // ===================================================================================
    //                                                                                Find
    //                                                                                ====
    @Override
    public <ENTITY extends KvsStoreEntity> OptionalEntity<ENTITY> findEntity(KvsStoreDBMeta kvsStoreDBMeta, List<Object> serchKeyList) {
        Map<String, Object> allColumnMap = kvsStoreDBMeta.extractAllColumnMap(kvsStoreDBMeta.newKvsStoreEntity());
        List<String> value = kvsStoreManager
                .findHash(generateKey(kvsStoreDBMeta.getProjectName(), kvsStoreDBMeta.getTableName(), serchKeyList), allColumnMap.keySet());
        return OptionalEntity.ofNullable(kvsStoreConverterHandler.toEntity(value, kvsStoreDBMeta), () -> {
            createBhvExThrower().throwSelectEntityAlreadyDeletedException(serchKeyList);
        });
    }

    @Override
    public <ENTITY extends KvsStoreEntity> Map<List<Object>, ENTITY> findEntityMap(KvsStoreDBMeta kvsStoreDBMeta,
            List<List<Object>> searchKeyListList) {
        List<String> keyList = searchKeyListList.stream().map(serchKeyList -> {
            return generateKey(kvsStoreDBMeta.getProjectName(), kvsStoreDBMeta.getTableName(), serchKeyList);
        }).collect(Collectors.toList());

        Map<String, Object> allColumnMap = kvsStoreDBMeta.extractAllColumnMap(kvsStoreDBMeta.newKvsStoreEntity());
        List<List<String>> list = kvsStoreManager.findMultiHash(keyList, allColumnMap.keySet());

        Map<List<Object>, ENTITY> valueMap = DfCollectionUtil.newLinkedHashMap();

        list.forEach(value -> {
            if (value == null) {
                return;
            }
            ENTITY entity = kvsStoreConverterHandler.toEntity(value, kvsStoreDBMeta);
            valueMap.put(kvsStoreDBMeta.extractKeyList(entity), entity);
        });
        return valueMap;
    }

    @Override
    public <ENTITY extends KvsStoreEntity> List<ENTITY> findList(KvsStoreDBMeta kvsStoreDBMeta, List<Object> searchKeyList) {
        throw new UnsupportedOperationException();
    }

    // ===================================================================================
    //                                                                            Register
    //                                                                            ========
    @Override
    public <ENTITY extends KvsStoreEntity> void insertOrUpdate(KvsStoreDBMeta kvsStoreDBMeta, List<Object> searchKeyList, ENTITY entity) {
        insertOrUpdate(kvsStoreDBMeta, searchKeyList, entity, null);
    }

    @Override
    public <ENTITY extends KvsStoreEntity> void insertOrUpdate(KvsStoreDBMeta kvsStoreDBMeta, List<Object> searchKeyList, ENTITY entity,
            LocalDateTime expireDateTime) {
        String key = generateKey(kvsStoreDBMeta.getProjectName(), kvsStoreDBMeta.getTableName(), searchKeyList);
        Map<String, String> columnMap = kvsStoreDBMeta.extractAllColumnMap(entity)
                .entrySet()
                .stream()
                .collect(HashMap::new, (map, column) -> map.put(column.getKey(), DfTypeUtil.toString(column.getValue())), Map::putAll);
        kvsStoreManager.registerHash(key, columnMap, expireDateTime);
    }

    @Override
    public <ENTITY extends KvsStoreEntity> void insertOrUpdate(KvsStoreDBMeta kvsStoreDBMeta, List<Object> searchKeyList,
            List<ENTITY> entityList) {
        insertOrUpdate(kvsStoreDBMeta, searchKeyList, entityList, null);
    }

    @Override
    public <ENTITY extends KvsStoreEntity> void insertOrUpdate(KvsStoreDBMeta kvsStoreDBMeta, List<Object> searchKeyList,
            List<ENTITY> entityList, LocalDateTime expireDateTime) {
        throw new UnsupportedOperationException();
    }

    // ===================================================================================
    //                                                                              Delete
    //                                                                              ======
    @Override
    public void delete(KvsStoreDBMeta kvsStoreDBMeta, List<Object> searchKeyList) {
        kvsStoreManager.delete(generateKey(kvsStoreDBMeta.getProjectName(), kvsStoreDBMeta.getTableName(), searchKeyList));
    }

    // ===================================================================================
    //                                                                               Other
    //                                                                               =====
    @Override
    public int getNumActive() {
        return kvsStoreManager.getNumActive();
    }

    // ===================================================================================
    //                                                                        Generate Key
    //                                                                        ============
    /**
     * Generate a key-string for KVS to store a value by concatenating information of the value.
     * e.g.) "DB name|table name|sercheKeyList(0)|searchKeyList(1)|..."
     * @param dbName The name of DB to connect to
     * @param tableName The name of table in RDB that contains the rows stored into KVS (NotNull)
     * @param searchkeyList A list of keys for search (NotNull)
     * @return A key-string used to store data into KVS (NotNull)
     */
    public String generateKey(String dbName, String tableName, List<Object> searchkeyList) {
        final StringBuilder sb = new StringBuilder();
        final String keyDelimiter = KEY_DELIMITER;
        sb.append(dbName);
        sb.append(keyDelimiter).append(tableName);
        searchkeyList.forEach(searchKey -> {
            sb.append(keyDelimiter);
            sb.append(toString(searchKey));
        });
        return sb.toString();
    }

    // ===================================================================================
    //                                                                        Small Helper
    //                                                                        ============
    protected String toString(final Object obj) {
        return obj == null ? "" : obj.toString();
    }

    protected boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    protected BehaviorExceptionThrower createBhvExThrower() {
        return new BehaviorExceptionThrower();
    }
}
