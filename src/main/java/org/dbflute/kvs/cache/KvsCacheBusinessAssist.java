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
package org.dbflute.kvs.cache;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.dbflute.Entity;
import org.dbflute.bhv.BehaviorReadable;
import org.dbflute.bhv.BehaviorSelector;
import org.dbflute.bhv.BehaviorWritable;
import org.dbflute.bhv.writable.InsertOption;
import org.dbflute.bhv.writable.UpdateOption;
import org.dbflute.cbean.ConditionBean;
import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.accessory.ColumnNullObjectable;
import org.dbflute.dbmeta.info.ColumnInfo;
import org.dbflute.exception.EntityAlreadyDeletedException;
import org.dbflute.exception.EntityDuplicatedException;
import org.dbflute.kvs.cache.bhv.writable.DeleteOption;
import org.dbflute.kvs.cache.bhv.writable.InsertOrUpdateOption;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.util.DfCollectionUtil;
import org.dbflute.util.DfTypeUtil;
import org.lastaflute.core.magic.ThreadCacheContext;
import org.lastaflute.core.magic.async.AsyncManager;
import org.lastaflute.core.time.TimeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author FreeGen
 */
public class KvsCacheBusinessAssist {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    protected static final String KEY_DELIMITER = "|";

    protected static final String KEY_COLUMN_NULL_OBJECT = "COLUMN_NULL_OBJECT";

    protected static final boolean KVS_CACHE_ASYNC_REFLECTION_ENABLED = true;

    private static final Logger logger = LoggerFactory.getLogger(KvsCacheBusinessAssist.class);

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private KvsCacheManager kvsCacheManager;

    @Resource
    private AsyncManager asyncManager;

    @Resource
    private TimeManager timeManager;

    protected final KvsCacheConverterHandler kvsCacheConverterHandler = new KvsCacheConverterHandler();

    // ===================================================================================
    //                                                                Find (For KVS Cache)
    //                                                                ====================
    /**
     * Retrieve an entity corresponding to the specified key-list.
     * When KVS does not have the value associated with the key-list,
     * search the entity from RDB and store the result into KVS before return it.
     * The type of the value stored into KVS will be MapString.
     * @param selector Selector corresponding to the DB to connect to (NotNull）
     * @param dbName The name of DB to connect to  (NotNull）
     * @param searchKeyList Key info. specified in KVS (equal to the search condition spedified by CB) (NotNull)
     * @param cb CB that specifies search condition (NotNull)
     * @param expireDateTimeLambda The callback for Time To Live (NotNull, MayReturnNull: then use server-config)
     * @param <ENTITY> Entity of DBFlute
     * @param kvsCacheAsyncReflectionEnabled config of async or sync (NotNull)
     * @return Entity as the result of the retrieval (NotNull)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     */
    public <ENTITY extends Entity> OptionalEntity<ENTITY> findEntity(BehaviorSelector selector, String dbName, List<Object> searchKeyList,
            ConditionBean cb, Function<ENTITY, LocalDateTime> expireDateTimeLambda, boolean kvsCacheAsyncReflectionEnabled) {
        final String kvsKey = generateKey(dbName, cb.asTableDbName(), searchKeyList);
        final ENTITY threadCachedEntity = findEntityFromThreadCache(kvsKey);
        if (threadCachedEntity != null) {
            return OptionalEntity.of(threadCachedEntity);
        }

        final String foundValue = kvsCacheManager.findString(kvsKey); // Fetch cache as MapString
        final DBMeta dbmeta = cb.asDBMeta(); // Get metadata of the target table

        if (foundValue != null) {
            final ENTITY foundEntity = kvsCacheConverterHandler.toEntity(foundValue, dbmeta);
            if (foundEntity != null) {
                registerThreadCache(kvsKey, foundEntity);
                return OptionalEntity.of(foundEntity);
            }
        }

        cb.disableColumnNullObject();

        final OptionalEntity<ENTITY> optEntity = selectEntity(selector, cb);

        optEntity.ifPresent(entity -> {
            registerThreadCache(kvsKey, entity);
            reflectKvsCache(() -> {
                kvsCacheManager.registerString(kvsKey, kvsCacheConverterHandler.toMapString(entity), expireDateTimeLambda.apply(entity));
            }, kvsCacheAsyncReflectionEnabled);
        });

        return optEntity;
    }

    /**
     * Retrieve a list of entities corresponding to the specified key-list.
     * When KVS does not have the value associated with the key-list,
     * search the entity from RDB and store the result into KVS before return it.
     * The type of the value stored into KVS will be List of MapString.
     * @param selector Selector corresponding to the DB to connect to (NotNull）
     * @param dbName The name of DB to connect to (NotNull)
     * @param searchKeyList Key info. specified in KVS (equal to the search condition spedified by CB) (NotNull)
     * @param cb CB that specifies query condition (NotNull)
     * @param expireDateTimeLambda The callback for Time To Live (NotNull, MayReturnNull: then use server-config)
     * @param <ENTITY> Entity of DBFlute
     * @param kvsCacheAsyncReflectionEnabled config of async or sync (NotNull)
     * @return List of entities as the result of retrieval (NotNull)
     */
    public <ENTITY extends Entity> List<ENTITY> findList(BehaviorSelector selector, String dbName, List<Object> searchKeyList,
            ConditionBean cb, Function<List<ENTITY>, LocalDateTime> expireDateTimeLambda, boolean kvsCacheAsyncReflectionEnabled) {
        final String kvsKey = generateKey(dbName, cb.asTableDbName(), searchKeyList);

        final List<ENTITY> threadCachedList = findListFromThreadCache(kvsKey);
        if (isNotEmpty(threadCachedList)) {
            return threadCachedList;
        }

        final List<String> found = kvsCacheManager.findList(kvsKey); // String elements represent MapString in KVS

        if (isNotEmpty(found)) {
            List<ENTITY> entityList = kvsCacheConverterHandler.toEntityList(found, cb.asDBMeta());
            if (isNotEmpty(entityList)) {
                registerThreadCache(kvsKey, entityList);
                return entityList;
            }
        }

        cb.disableColumnNullObject();

        final List<ENTITY> entityList = selectList(selector, cb);

        registerThreadCache(kvsKey, entityList);
        reflectKvsCache(() -> {
            kvsCacheManager.registerList(kvsKey, kvsCacheConverterHandler.toMapStringList(entityList),
                    expireDateTimeLambda.apply(entityList));
        }, kvsCacheAsyncReflectionEnabled);

        return entityList;
    }

    // ===================================================================================
    //                                                    Insert Or Update (For KVS Cache)
    //                                                    ================================
    /**
     * Insert/Update the specified entity into(in) RDB, and delete caches in KVS which contain the entity if exists.
     * @param selector Selector corresponding to the DB to connect to (NotNull）
     * @param dbName The name of DB to connect to (NotNull)
     * @param searchKeyList Key info. specified in KVS (equal to the search condition spedified by CB) (NotNull)
     * @param entity Entity to be inserted or updated (NotNull)
     * @param <ENTITY> Entity of DBFlute
     * @param op Option (NotNull)
     */
    public <ENTITY extends Entity> void insertOrUpdate(BehaviorSelector selector, String dbName, List<Object> searchKeyList, ENTITY entity,
            InsertOrUpdateOption op) {
        insertOrUpdate(selector, entity, op); // Insert/Update to RDB
        removeCache(dbName, entity.asTableDbName(), searchKeyList, op.isKvsCacheAsyncReflectionEnabled());
    }

    // ===================================================================================
    //                                                              Delete (For KVS Cache)
    //                                                              ======================
    /**
     * Delete the specified entity from RDB and caches from KVS which contain the entity if exists.
     * @param selector Selector corresponding to the DB to connect to (NotNull）
     * @param dbName The name of DB to connect to (NotNull)
     * @param searchKeyList Key info. specified in KVS (equal to the search condition spedified by CB) (NotNull)
     * @param entity Entity to be deleted (NotNull)
     * @param <ENTITY> Entity of DBFlute
     * @param op Option (NotNull)
     * @return The number of deleted rows
     */
    public <ENTITY extends Entity> int delete(BehaviorSelector selector, String dbName, List<Object> searchKeyList, ENTITY entity,
            DeleteOption op) {
        final int deleteCount = queryDelete(selector, entity);
        removeCache(dbName, entity.asTableDbName(), searchKeyList, op.isKvsCacheAsyncReflectionEnabled());
        return deleteCount;
    }

    public int queryDelete(BehaviorSelector selector, String dbName, List<Object> searchKeyList, ConditionBean cb) {
        final int deleteCount = queryDelete(selector, cb);
        removeCache(dbName, cb.asTableDbName(), searchKeyList, KVS_CACHE_ASYNC_REFLECTION_ENABLED);
        return deleteCount;
    }

    // unused
    /**
     * Flush caches in KVS associated with specified key, but do nothing to RDB.
     * @param selector Selector corresponding to the DB to connect to (NotNull）
     * @param dbName The name of DB to connect to (NotNull)
     * @param searchKeyList Key info. specified in KVS (equal to the search condition spedified by CB) (NotNull)
     * @param entity Entity to identifies the caches to be flushed (NotNull)
     * @param <ENTITY> Entity of DBFlute
     */
    public <ENTITY extends Entity> void clearCache(BehaviorSelector selector, String dbName, List<Object> searchKeyList, ENTITY entity) {
        removeCache(dbName, entity.asTableDbName(), searchKeyList, KVS_CACHE_ASYNC_REFLECTION_ENABLED);
    }

    // ===================================================================================
    //                                                        Generate Key (For KVS Cache)
    //                                                        ============================
    /**
     * Generate a key-string for KVS to store a value by concatenating information of the value.
     * e.g.) "<DB name>|<table name>|sercheKeyList(0)|searchKeyList(1)|..."
     * @param dbName The name of DB to connect to (NotNull)
     * @param tableName The name of table in RDB that contains the rows stored into KVS (NotNull)
     * @param searchKeyList A list of keys for search (NotNull)
     * @return A key-string used to store data into KVS (NotNull)
     */
    public String generateKey(String dbName, String tableName, List<Object> searchKeyList) {
        final StringBuilder sb = new StringBuilder();
        final String keyDelimiter = KEY_DELIMITER;
        sb.append(dbName);
        sb.append(keyDelimiter).append(tableName.toUpperCase()); // Because MySQL makes table name lower-case when storig it.
        searchKeyList.forEach(searchKey -> {
            sb.append(keyDelimiter);
            sb.append(Objects.toString(searchKey, ""));
        });
        return sb.toString();
    }

    // ===================================================================================
    //                                                        Find (For CB-Embedded Cache)
    //                                                        ============================
    /**
     * Retrieve an entity corresponding to the specified key-list.
     * When KVS does not have the value associated with the key-list,
     * search the entity from RDB and store the result into KVS before return it.
     * The type of the value stored into KVS will be MapString.
     * @param selector Selector corresponding to the DB to connect to (NotNull）
     * @param dbName The name of DB to connect to (NotNull)
     * @param searchKeyList Key info. specified in KVS (equal to the search condition spedified by CB) (NotNull)
     * @param cb CB that specifies search condition (NotNull)
     * @param expireDateTimeLambda The callback for Time To Live (NotNull, MayReturnNull: then use server-config)
     * @param specifiedColumnInfoSet A set of column information for column(s) to be selected (NotNull)
     * @param <ENTITY> Entity of DBFlute
     * @param kvsCacheAsyncReflectionEnabled config of async or sync (NotNull)
     * @return Entity as the result of the retrieval (NotNull)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     */
    public <ENTITY extends Entity> OptionalEntity<ENTITY> findEntity(BehaviorSelector selector, String dbName, List<Object> searchKeyList,
            ConditionBean cb, Function<ENTITY, LocalDateTime> expireDateTimeLambda, Set<ColumnInfo> specifiedColumnInfoSet,
            boolean kvsCacheAsyncReflectionEnabled) {
        logger.debug("tableName={}, specifiedColumnInfoSet={}", cb.asTableDbName(), specifiedColumnInfoSet);
        final String kvsKey = generateKeyForColumnNullObject(dbName, cb.asTableDbName(), searchKeyList);
        final DBMeta dbmeta = cb.asDBMeta(); // Get metadata of the target table
        Set<String> specifiedPropertyNameSet =
                specifiedColumnInfoSet.stream().map(columnInfo -> columnInfo.getPropertyName()).collect(Collectors.toSet());

        // threadCached
        ENTITY cachedEntity = findEntityFromThreadCache(kvsKey);
        if (cachedEntity != null) {
            logger.debug("hit entity in threadCached. entity={}", cachedEntity);
            Set<String> properties = myspecifiedAndModifiedProperties(cachedEntity);
            if (properties.containsAll(specifiedPropertyNameSet)) {
                logger.debug("return entity in threadCached.");
                return OptionalEntity.of(cachedEntity);
            }
            logger.debug("don't enough entity in threadCached.");
            specifiedPropertyNameSet.removeAll(properties);
        } else {
            logger.debug("don't hit entity in threadCached.");
        }

        // KVS
        final List<String> value = kvsCacheManager.findHash(kvsKey,
                specifiedPropertyNameSet.stream().map(name -> dbmeta.findColumnInfo(name).getColumnDbName()).collect(Collectors.toSet()));
        if (value != null) {
            logger.debug("hit value in KVS. value={}", value);
            final ENTITY kvsEntity = kvsCacheConverterHandler.toEntity(value, dbmeta,
                    specifiedPropertyNameSet.stream()
                            .map(name -> dbmeta.findColumnInfo(name).getColumnDbName())
                            .collect(Collectors.toSet()));
            logger.debug("convert to entity. entity={}", kvsEntity);
            if (kvsEntity != null) {
                reflectEntity(kvsEntity, cachedEntity);
                Set<String> properties = myspecifiedAndModifiedProperties(kvsEntity);
                if (properties.containsAll(specifiedPropertyNameSet)) {
                    registerThreadCache(kvsKey, kvsEntity);
                    logger.debug("return entity in KVS.");
                    return OptionalEntity.of(kvsEntity);
                }
                logger.debug("don't enough entity in KVS.");
                specifiedPropertyNameSet.removeAll(properties);
                cachedEntity = kvsEntity;
            } else {
                logger.debug("don't enough entity in KVS.");
            }
        } else {
            logger.debug("don't hit entity in KVS.");
        }

        // DB
        specifiedPropertyNameSet.forEach(name -> {
            ColumnInfo columnInfo = dbmeta.findColumnInfo(name);
            if (columnInfo.canBeNullObject()) {
                cb.invokeSpecifyColumn(columnInfo.getPropertyName());
            }
        });
        cb.disableColumnNullObject();
        final OptionalEntity<ENTITY> optEntity = selectEntity(selector, cb);

        ENTITY halfwayCachedEntity = cachedEntity;
        optEntity.ifPresent(entity -> {
            logger.debug("hit entity in DB. entity={}", entity);
            reflectEntity(entity, halfwayCachedEntity);
            registerThreadCache(kvsKey, entity);
            Map<String, String> fieldValueMap = dbmeta.extractAllColumnMap(entity)
                    .entrySet()
                    .stream()
                    .filter(entry -> specifiedPropertyNameSet.contains(dbmeta.findColumnInfo(entry.getKey()).getPropertyName()))
                    .collect(HashMap::new, (map, column) -> map.put(column.getKey(), DfTypeUtil.toString(column.getValue())), Map::putAll);
            reflectKvsCache(() -> {
                kvsCacheManager.registerHash(kvsKey, fieldValueMap, expireDateTimeLambda.apply(entity));
            }, kvsCacheAsyncReflectionEnabled);
        }).orElse(() -> {
            logger.debug("don't hit entity in DB.");
        });

        return optEntity;
    }

    /**
     * Load a list of entities associated with the specified key to thread cache and KVS.
     * The search runs against in order of thread cache, KVS, and RDB,
     * then store the result(s) into previous target data source(s), thread cache and KVS, if not stored.
     * The type of the value stored into KVS will be List of MapString.
     * @param selector Selector corresponding to the DB to connect to (NotNull）
     * @param dbName The name of DB to connect to (NotNull)
     * @param searchKeyListList Key info. specified in KVS (equal to the search condition spedified by CB) (NotNull)
     * @param dbmeta Matadata for target DB (NotNull)
     * @param cbLamda Condition lambda expresion (NotNull)
     * @param ttl Time To Live (NullAllowed: use server-config if null is set)
     * @param specifiedColumnInfoSet A set of column information for column(s) to be selected (NotNull)
     * @param <ENTITY> Entity of DBFlute
     */
    public <ENTITY extends Entity> void loadThreadCacheByIds(BehaviorSelector selector, String dbName, List<List<Object>> searchKeyListList,
            DBMeta dbmeta, BiConsumer<ConditionBean, List<List<Object>>> cbLamda, Long ttl, Set<ColumnInfo> specifiedColumnInfoSet) {
        Set<String> specifiedPropertyNameSet =
                specifiedColumnInfoSet.stream().map(columnInfo -> columnInfo.getPropertyName()).collect(Collectors.toSet());

        // threadCached
        List<List<Object>> noCachedSearchKeyListList = searchKeyListList.stream().filter(searchKeyList -> {
            String kvsKey = generateKeyForColumnNullObject(dbName, dbmeta.getTableDbName(), searchKeyList);
            final ENTITY cachedEntity = findEntityFromThreadCache(kvsKey);
            return cachedEntity == null || !myspecifiedAndModifiedProperties(cachedEntity).containsAll(specifiedPropertyNameSet);
        }).collect(Collectors.toList());
        if (noCachedSearchKeyListList.isEmpty()) {
            logger.debug("hit all entity in threadCached.");
            return;
        }
        logger.debug("don't hit entity in threadCached or don't enough entity in threadCached.");

        // KVS
        List<List<String>> value = kvsCacheManager.findMultiHash(noCachedSearchKeyListList.stream().map(searchKeyList -> {
            return generateKeyForColumnNullObject(dbName, dbmeta.getTableDbName(), searchKeyList);
        }).collect(Collectors.toList()), specifiedPropertyNameSet);
        if (value != null && !value.isEmpty()) {
            logger.debug("hit value in KVS. value={}", value);
            value.forEach(f -> {
                if (f != null && !f.isEmpty()) {
                    logger.debug("don't enough entity in KVS.");
                    return;
                }
                Set<String> specifiedColumnDbNames =
                        specifiedColumnInfoSet.stream().map(columnInfo -> columnInfo.getColumnDbName()).collect(Collectors.toSet());
                final Entity kvsEntity = kvsCacheConverterHandler.toEntity(f, dbmeta, specifiedColumnDbNames);
                logger.debug("convert to entity. entity={}", kvsEntity);
                if (kvsEntity == null) {
                    logger.debug("don't enough entity in KVS.");
                    return;
                }
                final List<Object> cachedSearchKeyList = DfCollectionUtil.newArrayList(dbmeta.extractPrimaryKeyMap(kvsEntity).values());
                noCachedSearchKeyListList.remove(cachedSearchKeyList);
                String kvsKey = generateKeyForColumnNullObject(dbName, dbmeta.getTableDbName(), cachedSearchKeyList);
                registerThreadCache(kvsKey, kvsEntity);
                logger.debug("enough entity in KVS.");
            });
            if (noCachedSearchKeyListList.isEmpty()) {
                logger.debug("hit all entity in KVS.");
                return;
            }
        } else {
            logger.debug("don't hit entity in KVS.");
        }

        // DB
        BehaviorReadable readable = selector.byName(dbmeta.getTableDbName());
        ConditionBean cb = readable.newConditionBean();
        cbLamda.accept(cb, noCachedSearchKeyListList);

        specifiedColumnInfoSet.forEach(columnInfo -> {
            if (columnInfo.canBeNullObject()) {
                cb.invokeSpecifyColumn(columnInfo.getPropertyName());
            }
        });
        cb.disableColumnNullObject();

        List<Entity> entityList = selectList(selector, cb);

        Map<String, Map<String, String>> keyValueMap = DfCollectionUtil.newLinkedHashMap();
        entityList.forEach(entity -> {
            String kvsKey = generateKeyForColumnNullObject(dbName, dbmeta.getTableDbName(),
                    DfCollectionUtil.newArrayList(dbmeta.extractPrimaryKeyMap(entity).values()));
            Map<String, String> fieldValueMap = dbmeta.extractAllColumnMap(entity)
                    .entrySet()
                    .stream()
                    .filter(entry -> specifiedColumnInfoSet.contains(dbmeta.findColumnInfo(entry.getKey())))
                    .collect(HashMap::new, (map, column) -> map.put(column.getKey(), DfTypeUtil.toString(column.getValue())), Map::putAll);
            keyValueMap.put(kvsKey, fieldValueMap);
            registerThreadCache(kvsKey, entity);
        });

        // Insert/Update an entity into(in) KVS asynchronously
        asyncManager.async(() -> {
            kvsCacheManager.registerMultiHash(keyValueMap, calcExpireDateTime(ttl));
        });
    }

    // ===================================================================================
    //                                                Generate Key (For CB-Embedded Cache)
    //                                                ====================================
    /**
     * Generate a key-string for KVS to store a value by concatenating information of the value.
     * e.g.) "COLUMN_NULL_OBJECT|<DB name>|<table name>|sercheKeyList(0)|searchKeyList(1)|..."
     * @param dbName The name of DB to connect to (NotNull)
     * @param tableName The name of table in RDB that contains the rows stored into KVS (NotNull)
     * @param searchKeyList A list of keys for search (NotNull)
     * @return A key-string used to store data into KVS (NotNull)
     */
    public String generateKeyForColumnNullObject(String dbName, String tableName, List<Object> searchKeyList) {
        final StringBuilder sb = new StringBuilder();
        final String keyColumnNullObject = KEY_COLUMN_NULL_OBJECT;
        final String keyDelimiter = KEY_DELIMITER;
        sb.append(keyColumnNullObject);
        sb.append(keyDelimiter);
        sb.append(generateKey(dbName, tableName, searchKeyList));
        return sb.toString();
    }

    // ===================================================================================
    //                                                                                 TTL
    //                                                                                 ===
    /**
     * Return a LocalDateTime as the result of addition that adds ttl to current datetime.
     * @param ttl Time To Live (sec) (NullAllowed)
     * @return Current datetime + ttl (NullAllowed: return null if ttl is null)
     */
    public LocalDateTime calcExpireDateTime(Long ttl) {
        if (ttl == null) {
            return null;
        }
        return timeManager.currentDateTime().plusSeconds(ttl);
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    protected <ENTITY extends Entity> OptionalEntity<ENTITY> selectEntity(BehaviorSelector selector, ConditionBean cb) {
        final BehaviorReadable readable = selector.byName(cb.asTableDbName()); // Fetch abstract Behavior

        @SuppressWarnings("unchecked")
        ENTITY entity = (ENTITY) readable.readEntity(cb);

        return OptionalEntity.ofNullable(entity, () -> {
            throw new IllegalArgumentException("Not found the entity by the condition. (might be deleted?)");
        });
    }

    /**
     * Retrieve a list of entities by specified Selector and query condition.
     * @param selector Selector corresponding to the DB to connect to (NotNull）
     * @param cb CB that specifies query condition (NotNull)
     * @return A list of entities (NotNull)
     */
    protected <ENTITY extends Entity> List<ENTITY> selectList(BehaviorSelector selector, ConditionBean cb) {
        final BehaviorReadable readable = selector.byName(cb.asTableDbName()); // Fetch abstract Behavior
        return readable.readList(cb);
    }

    // ===================================================================================
    //                                                                    Insert Or Update
    //                                                                    ================
    /**
     * Insert/Update a row into(in) RDB corresponding to specified entity to the assigned entity.
     * @param selector Selector corresponding to the DB to connect to (NotNull）
     * @param entity An entity to be inserted or updated (NotNull)
     * @param <ENTITY> Entity of DBFlute
     * @param op Option (NotNull)
     */
    protected <ENTITY extends Entity> void insertOrUpdate(BehaviorSelector selector, ENTITY entity, InsertOrUpdateOption op) {
        final BehaviorWritable writable = (BehaviorWritable) selector.byName(entity.asTableDbName()); // Fetch abstract Behavior
        InsertOption<? extends ConditionBean> insertOption = new InsertOption<ConditionBean>();
        UpdateOption<? extends ConditionBean> updateOption = new UpdateOption<ConditionBean>();
        if (op.isCommonColumnAutoSetupDisabled()) {
            insertOption.disableCommonColumnAutoSetup();
            updateOption.disableCommonColumnAutoSetup();
        }
        writable.createOrModify(entity, insertOption, updateOption);
    }

    // ===================================================================================
    //                                                                              Delete
    //                                                                              ======
    /**
     * Delete a row from RDB corresponding to specified entity,
     * using queryDelete to return the number of deleted values.
     * @param selector Selector corresponding to the DB to connect to (NotNull）
     * @param entity Entity that specifies the key for the target value (NotNull)
     * @param <ENTITY> Entity of DBFlute
     * @return The number of deleted rows (0 or 1)
     * @throws EntityDuplicatedException (thrown when the number of deleted rows is two or more)
     */
    protected <ENTITY extends Entity> int queryDelete(BehaviorSelector selector, ENTITY entity) throws EntityDuplicatedException {
        final BehaviorWritable writable = (BehaviorWritable) selector.byName(entity.asTableDbName()); // Fetch abstract Behavior
        // rangeRemove() is used to return 0 when no value is deleted.
        // The primary key of assigned Entity is set to cb to identify
        // the row to delete in following process.
        final DBMeta dbmeta = entity.asDBMeta();
        final ConditionBean cb = writable.newConditionBean();
        cb.acceptPrimaryKeyMap(dbmeta.extractPrimaryKeyMap(entity));
        final int deleteCount = writable.rangeRemove(cb, null);
        if (deleteCount > 1) {
            throw new EntityDuplicatedException("More than one rows to delete are detected: " + deleteCount);
        }
        return deleteCount;
    }

    /**
     * Delete a row from RDB corresponding to specified entity.
     * @param selector Selector corresponding to the DB to connect to (NotNull）
     * @param cb CB that specifies query condition (NotNull)
     * @param <ENTITY> Entity of DBFlute
     * @return The number of deleted rows(0 or1)
     */
    protected <ENTITY extends Entity> int queryDelete(BehaviorSelector selector, ConditionBean cb) {
        final BehaviorWritable writable = (BehaviorWritable) selector.byName(cb.asTableDbName()); // Fetch abstract Behavior
        final int deleteCount = writable.rangeRemove(cb, null);
        return deleteCount;
    }

    protected <ENTITY extends Entity> void removeCache(String dbName, String tableDbName, List<Object> searchKeyList,
            boolean kvsCacheAsyncReflectionEnabled) {
        final String kvsKey = generateKey(dbName, tableDbName, searchKeyList);
        removeThreadCache(kvsKey);
        reflectKvsCache(() -> {
            kvsCacheManager.delete(kvsKey); // Delete value from KVS
        }, kvsCacheAsyncReflectionEnabled);
    }

    // ===================================================================================
    //                                                                        Thread Cache
    //                                                                        ============
    /**
     * Retrieve an entity whose key matches the assigned one.
     * @param key Key for search (NotNull)
     * @return Retrieved entity (NullAllowed: return null if no entity is retrieved)
     */
    @SuppressWarnings("unchecked")
    protected <ENTITY extends Entity> ENTITY findEntityFromThreadCache(String key) {
        return (ENTITY) ThreadCacheContext.getObject(key);
    }

    /**
     * Retrieve a list of entities each of whose key matches the assigned one.
     * @param key Key for search (NotNull)
     * @return A list of retrieved entities (NotNull)
     */
    @SuppressWarnings("unchecked")
    protected <ENTITY extends Entity> List<ENTITY> findListFromThreadCache(String key) {
        Object value = ThreadCacheContext.getObject(key);
        return value == null ? Collections.emptyList() : (List<ENTITY>) value;
    }

    /**
     * Register a value identified by the assigned key into thread cache.
     * @param key key for the value to be registered (NotNull)
     * @param value Value to be registered (NullAllowed)
     */
    protected void registerThreadCache(String key, Object value) {
        ThreadCacheContext.setObject(key, value);
    }

    /**
     * Delete cache whose key matches the assigned one from thread cache.
     * @param key Key for cache to be deleted (NotNull)
     */
    protected void removeThreadCache(String key) {
        ThreadCacheContext.removeObject(key);
    }

    // ===================================================================================
    //                                                                        Small Helper
    //                                                                        ============
    protected boolean isNotEmpty(Collection<?> coll) {
        return !(coll == null || coll.isEmpty());
    }

    protected <ENTITY extends Entity> void reflectEntity(ENTITY entity, final ENTITY reflectEntity) {
        if (reflectEntity == null) {
            return;
        }
        if (entity instanceof ColumnNullObjectable) {
            ((ColumnNullObjectable) entity).disableColumnNullObject();
        }
        if (reflectEntity instanceof ColumnNullObjectable) {
            ((ColumnNullObjectable) reflectEntity).disableColumnNullObject();
        }
        DBMeta dbMeta = entity.asDBMeta();
        Map<String, Object> allColumnMap = DfCollectionUtil.newHashMap();
        allColumnMap.putAll(dbMeta.extractAllColumnMap(entity).entrySet().stream().filter(column -> {
            return myspecifiedAndModifiedProperties(entity).contains(dbMeta.findColumnInfo(column.getKey()).getPropertyName());
        }).collect(HashMap::new, (map, column) -> map.put(column.getKey(), column.getValue()), Map::putAll));
        allColumnMap.putAll(dbMeta.extractAllColumnMap(reflectEntity).entrySet().stream().filter(column -> {
            return myspecifiedAndModifiedProperties(reflectEntity).contains(dbMeta.findColumnInfo(column.getKey()).getPropertyName());
        }).collect(HashMap::new, (map, column) -> map.put(column.getKey(), column.getValue()), Map::putAll));

        dbMeta.acceptAllColumnMap(entity, allColumnMap);

        if (entity instanceof ColumnNullObjectable) {
            ((ColumnNullObjectable) entity).enableColumnNullObject();
        }
        if (reflectEntity instanceof ColumnNullObjectable) {
            ((ColumnNullObjectable) reflectEntity).enableColumnNullObject();
        }
        entity.modifiedToSpecified();
    }

    protected <ENTITY extends Entity> Set<String> myspecifiedAndModifiedProperties(ENTITY entity) {
        Set<String> properties = DfCollectionUtil.newHashSet();
        if (entity != null) {
            properties.addAll(entity.myspecifiedProperties());
            properties.addAll(entity.mymodifiedProperties());
        }
        return properties;
    }

    protected void reflectKvsCache(Runnable run, boolean kvsCacheAsyncReflectionEnabled) {
        if (kvsCacheAsyncReflectionEnabled) {
            asyncManager.async(() -> run.run());
        } else {
            run.run();
        }
    }
}
