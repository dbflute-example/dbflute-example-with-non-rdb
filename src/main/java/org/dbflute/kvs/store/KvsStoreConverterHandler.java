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
package org.dbflute.kvs.store;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.dbflute.kvs.store.entity.KvsStoreEntity;
import org.dbflute.kvs.store.entity.dbmeta.KvsStoreDBMeta;
import org.dbflute.util.DfCollectionUtil;

/**
 * @author FreeGen
 */
public class KvsStoreConverterHandler {

    // ===================================================================================
    //                                                                        to MapString
    //                                                                        ============
    /**
     * Convert an Entity object to a MapString in KVS represented by String.
     * @param entity Entity of DBFlute (NotNull)
     * @param <ENTITY> Entity class of DBFlute
     * @return A String value representing MapString in KVS (NotNull)
     */
    public <ENTITY extends KvsStoreEntity> String toMapString(ENTITY entity) {
        return toMapString(entity.asDBMeta().extractAllColumnMap(entity));
    }

    /**
     * Convert Entity objects in the assigned list to MapString values in KVS represented by String.
     * @param entityList A list of Entity objects (NotNull)
     * @param <ENTITY> Entity class of DBFlute
     * @return A list of String values representing MapString in KVS (NotNull)
     */
    public <ENTITY extends KvsStoreEntity> List<String> toMapStringList(List<ENTITY> entityList) {
        List<String> list = entityList.stream().map(entity -> {
            return toMapString(entity.asDBMeta().extractAllColumnMap(entity));
        }).collect(Collectors.toList());
        return list;
    }

    // ===================================================================================
    //                                                                           to Entity
    //                                                                           =========
    /**
     * Convert A MapString in KVS represented by Java String to an Entity object.
     * @param mapString A String value representing MapString (NotNull)
     * @param dbmeta DBMeta (NotNull)
     * @param <ENTITY> Entity class of DBFlute
     * @return An Entity object of DBFlute (NotNull)
     */
    public <ENTITY extends KvsStoreEntity> ENTITY toEntity(String mapString, KvsStoreDBMeta dbmeta) {
        @SuppressWarnings("unchecked")
        ENTITY entity = (ENTITY) dbmeta.newKvsStoreEntity();
        dbmeta.acceptAllColumnMap(entity, fromMapString(mapString));
        return entity;
    }

    /**
     * Convert Mapstrings in KVS represented by Java String to Entity objects.
     * @param mapStringList A list of String values representing MapString (NotNull)
     * @param dbmeta DBMeta (NotNull)
     * @param <ENTITY> Entity class of DBFlute
     * @return A list of Entity objects of DBFlute (NotNull)
     */
    public <ENTITY extends KvsStoreEntity> List<ENTITY> toEntityList(List<String> mapStringList, KvsStoreDBMeta dbmeta) {
        List<ENTITY> entityList = mapStringList.stream().map(mapString -> {
            @SuppressWarnings("unchecked")
            ENTITY entity = (ENTITY) dbmeta.newKvsStoreEntity();
            dbmeta.acceptAllColumnMap(entity, fromMapString(mapString));
            return entity;
        }).collect(Collectors.toList());
        return entityList;
    }

    /**
     * Convert a list of String value to an Entity object.
     * @param value A list of String values (NotNull)
     * @param dbmeta DBMeta (NotNull)
     * @param <ENTITY> Entity class of DBFlute
     * @return An Entity object of DBFlute (NotNull)
     */
    public <ENTITY extends KvsStoreEntity> ENTITY toEntity(List<String> value, KvsStoreDBMeta dbmeta) {
        @SuppressWarnings("unchecked")
        ENTITY entity = (ENTITY) dbmeta.newKvsStoreEntity();
        List<String> list = DfCollectionUtil.newArrayList(dbmeta.extractAllColumnMap(entity).keySet());
        Map<String, Object> columnValueMap = IntStream.range(0, Math.min(list.size(), value.size()))
                .filter(index -> value.get(index) != null)
                .boxed()
                .collect(Collectors.toMap(index -> list.get(index), index -> value.get(index)));

        if (columnValueMap.isEmpty()) {
            return null;
        }

        dbmeta.acceptAllColumnMap(entity, columnValueMap);
        return entity;
    }

    // ===================================================================================
    //                                                                           Converter
    //                                                                           =========
    protected String toMapString(Map<String, ? extends Object> map) {
        return new org.dbflute.helper.dfmap.DfMapStyle().toMapString(map);
    }

    protected Map<String, Object> fromMapString(String mapString) {
        return new org.dbflute.helper.dfmap.DfMapStyle().fromMapString(mapString);
    }
}
