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
package org.dbflute.kvs.store.facade;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.dbflute.kvs.store.entity.KvsStoreEntity;
import org.dbflute.kvs.store.entity.dbmeta.KvsStoreDBMeta;
import org.dbflute.optional.OptionalEntity;

/**
 * @author FreeGen
 */
public interface KvsStoreFacade {

    // ===================================================================================
    //                                                                                Get
    //                                                                               =====
    /**
     * Retrieve an entity corresponding to the assigned search key-list.
     * @param kvsStoreDBMeta Metadata of KVS store (NotNull)
     * @param searchKeyList A list containing keys for search (NotNull)
     * @param <ENTITY> Entity of DBFlute
     * @return The entity associated with the assigned search key-list (NullAllowed)
     */
    <ENTITY extends KvsStoreEntity> OptionalEntity<ENTITY> findEntity(KvsStoreDBMeta kvsStoreDBMeta, List<Object> searchKeyList);

    /**
     * Retrieve entites corresponding to the assigned search key-list and return them as a Map.
     * @param kvsStoreDBMeta Metadata of KVS store (NotNull)
     * @param searchKeyListList A list containing keys for search (NotNull)
     * @param <ENTITY> Entity of DBFlute
     * @return The map of entities associated with the search key-list (NotNull)
     */
    <ENTITY extends KvsStoreEntity> Map<List<Object>, ENTITY> findEntityMap(KvsStoreDBMeta kvsStoreDBMeta,
            List<List<Object>> searchKeyListList);

    /**
     * Retrieve entities corresponding to the assigned search key-list and return them as a List.
     * @param kvsStoreDBMeta Metadata of KVS store (NotNull)
     * @param searchKeyList A list containing keys for search (NotNull)
     * @param <ENTITY> Entity of DBFlute
     * @return The list of entities associated with the search key-list (NotNull)
     */
    <ENTITY extends KvsStoreEntity> List<ENTITY> findList(KvsStoreDBMeta kvsStoreDBMeta, List<Object> searchKeyList);

    // ===================================================================================
    //                                                                    Insert OR Update
    //                                                                   =================
    /**
     * Insert/Update the assigned entity into(in) KVS.
     * @param kvsStoreDBMeta Metadata of KVS store (NotNull)
     * @param searchKeyList A list containing keys for search (NotNull)
     * @param entity Entity to insert/update (NotNull)
     * @param <ENTITY> Entity of DBFlute
     */
    <ENTITY extends KvsStoreEntity> void insertOrUpdate(KvsStoreDBMeta kvsStoreDBMeta, List<Object> searchKeyList, ENTITY entity);

    /**
     * Insert/Update the assingned entity into(in) KVS with ttl.
     * @param kvsStoreDBMeta Metadata of KVS store (NotNull)
     * @param searchKeyList A list containing keys for search (NotNull)
     * @param entity Entity to insert/update (NotNull)
     * @param expireDateTime Time To Live (NullAllowed: use server-config if null is set)
     * @param <ENTITY> Entity of DBFlute
     */
    <ENTITY extends KvsStoreEntity> void insertOrUpdate(KvsStoreDBMeta kvsStoreDBMeta, List<Object> searchKeyList, ENTITY entity,
            LocalDateTime expireDateTime);

    /**
     * Insert/Update the assigned entities into(in) KVS.
     * @param kvsStoreDBMeta Metadata of KVS store (NotNull)
     * @param searchKeyList A list containing keys for search (NotNull)
     * @param entityList Entity list to insert/update (NotNull)
     * @param <ENTITY> Entity of DBFlute
     */
    <ENTITY extends KvsStoreEntity> void insertOrUpdate(KvsStoreDBMeta kvsStoreDBMeta, List<Object> searchKeyList, List<ENTITY> entityList);

    /**
     * Insert/Update the assingned entities into(in) KVS with ttl.
     * @param kvsStoreDBMeta Metadata of KVS store (NotNull)
     * @param searchKeyList A list containing keys for search (NotNull)
     * @param entityList Entity list to insert/update (NotNull)
     * @param expireDateTime Time To Live (NullAllowed: use server-config if null is set)
     * @param <ENTITY> Entity of DBFlute
     */
    <ENTITY extends KvsStoreEntity> void insertOrUpdate(KvsStoreDBMeta kvsStoreDBMeta, List<Object> searchKeyList, List<ENTITY> entityList,
            LocalDateTime expireDateTime);

    // ===================================================================================
    //                                                                              Delete
    //                                                                              ======
    /**
     * Delete selected entities.
     * @param kvsStoreDBMeta Meta data for KVS store (NotNull)
     * @param searchKeyList A list of search-key (NotNull)
     */
    void delete(KvsStoreDBMeta kvsStoreDBMeta, List<Object> searchKeyList);

    // ===================================================================================
    //                                                                               Other
    //                                                                               =====
    /**
     * Get the number of active connections in connection pool.
     * @return The number of active connections in connection pool
     */
    int getNumActive();
}
