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
package org.dbflute.kvs.cache.facade;

import java.util.List;
import java.util.Set;

import org.dbflute.Entity;
import org.dbflute.cbean.ConditionBean;
import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.info.ColumnInfo;
import org.dbflute.optional.OptionalEntity;

/**
 * @author FreeGen
 */
public interface KvsCacheFacade {

    // ===================================================================================
    //                                                                                Find
    //                                                                                ====
    /**
     * Retrieve an entity corresponding to the assigned search key-list,
     * or one stored in RDB retrieved with the search condition that CB specifies
     * if KVS does not have the value associated with the key-list.
     * @param searchKeyList A list containing keys for search (NotNull)
     * @param cb CB that specifies query condition (NotNull)
     * @param <ENTITY> Entity of DBFlute
     * @return The entity associated with the assigned search key-list (NotNull)
     */
    <ENTITY extends Entity> OptionalEntity<ENTITY> findEntity(List<Object> searchKeyList, ConditionBean cb);

    /**
     * Retrieve an entity associated with the specified ID from DBmeta of the argument.
     * @param id An ID as the primary key (NotNull)
     * @param dbmeta A DBmeta of the corresponding table (NotNull)
     * @param specifiedColumnInfoSet A set of column information for column(s) to be selected (NotNull)
     * @return The entity retrieved by the specified ID (NotNull)
     */
    <ENTITY extends Entity> OptionalEntity<ENTITY> findEntityById(Object id, DBMeta dbmeta, Set<ColumnInfo> specifiedColumnInfoSet);

    /**
     * Retrieve a list of entities corresponding to the assigned search key-list,
     * or one stored in RDB retrieved with the search condition that CB specifies
     * if KVS does not have the value associated with the key.
     * @param searchKeyList A list containing keys for search (NotNull)
     * @param cb CB that specifies query condition (NotNull)
     * @param <ENTITY> Entity of DBFlute
     * @return The list of entities associated with the search key-list (NotNull)
     */
    <ENTITY extends Entity> List<ENTITY> findList(List<Object> searchKeyList, ConditionBean cb);

    /**
     * Retrieve a list of entities associated with the List of specified IDs from DBmeta of the argument,
     * and load it to thread cache and KVS.
     * @param idList A list of IDs as the primary keys (NotNull)
     * @param dbmeta A DBmeta of the corresponding table (NotNull)
     * @param specifiedColumnInfoSet A set of column information for column(s) to be selected (NotNull)
     */
    void loadThreadCacheByIds(List<Object> idList, DBMeta dbmeta, Set<ColumnInfo> specifiedColumnInfoSet);

    // ===================================================================================
    //                                                                    Insert OR Update
    //                                                                   =================
    /**
     * Insert/update a specified entity in(into) RDB, 
     * and flush caches associated with specified search key-list. 
     * @param searchKeyList A list of keys for search (NotNull)
     * @param entity The entity to be inserted or updated (NotNull)
     * @param <ENTITY> Entity of DBFlute
     */
    <ENTITY extends Entity> void insertOrUpdate(List<Object> searchKeyList, ENTITY entity);

    // ===================================================================================
    //                                                                              Delete
    //                                                                              ======
    /**
     * Delete a specified entity from RDB, 
     * and flush caches associated with specified search key-list.
     * @param searchKeyList A list of keys for search (NotNull)
     * @param entity The entity to be deleted (NotNull)
     * @param <ENTITY> Entity of DBFlute
     * @return The number of entity that has been deleted
     */
    <ENTITY extends Entity> int delete(List<Object> searchKeyList, ENTITY entity);

    /**
     * Delete row(s) in RDB corresponding to the query condition specified by CB, 
     * and flush caches associated with specified search key-list together.
     * @param searchKeyList A list of keys for search (NotNull)
     * @param cb CB that specifies query condition（NotNull）
     * @return The number of entity that has been deleted
     */
    int queryDelete(List<Object> searchKeyList, ConditionBean cb);

    // ===================================================================================
    //                                                                          ClearCache
    //                                                                          ==========
    /**
     * Flush caches in KVS associated with specified search key-list, but do nothing to RDB.
     * @param searchKeyList A list of keys for search (NotNull)
     * @param entity The entity that identifies the caches to be flushed (NotNull)
     * @param <ENTITY> Entity of DBFlute
     */
    <ENTITY extends Entity> void clearCache(List<Object> searchKeyList, ENTITY entity);

    /**
     * Delete the cache of specified entity.
     * @param entity The entity to be deleted (NotNull)
     */
    <ENTITY extends Entity> void clearCache(ENTITY entity);

    // ===================================================================================
    //                                                                               Other
    //                                                                               =====
    /**
     * Get the number of active connections in connection pool.
     * @return The number of active connections in connection pool
     */
    int getNumActive();
}
