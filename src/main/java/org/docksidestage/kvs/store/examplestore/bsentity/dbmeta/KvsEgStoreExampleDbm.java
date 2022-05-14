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
package org.docksidestage.kvs.store.examplestore.bsentity.dbmeta;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dbflute.kvs.store.cbean.KvsStoreConditionBean;
import org.dbflute.kvs.store.entity.KvsStoreEntity;
import org.dbflute.kvs.store.entity.dbmeta.AbstractKvsStoreDBMeta;
import org.dbflute.util.DfAssertUtil;
import org.docksidestage.kvs.store.examplestore.bsentity.KvsEgBsStoreExample;
import org.docksidestage.kvs.store.examplestore.cbean.KvsEgStoreExampleCB;
import org.docksidestage.kvs.store.examplestore.exentity.KvsEgStoreExample;

/**
 * The DB meta of (Description about this KVS store)StoreExample. (Singleton)
 * @author FreeGen
 */
public class KvsEgStoreExampleDbm extends AbstractKvsStoreDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    /** Description about this KVS storeメタ。 */
    private static final KvsEgStoreExampleDbm INSTANCE = new KvsEgStoreExampleDbm();

    /**
     * Description about this KVS storeメタを作成する。
     */
    private KvsEgStoreExampleDbm() {
    }

    /**
     * Description about this KVS storeメタを返す。
     * @return Description about this KVS storeメタ
     */
    public static KvsEgStoreExampleDbm getInstance() {
        return INSTANCE;
    }

    // ===================================================================================
    //                                                                       Current DBDef
    //                                                                       =============
    @Override
    public String getProjectName() {
        return "examplestore";
    }

    @Override
    public String getTableName() {
        return "StoreExample";
    }

    @Override
    public KvsStoreEntity newKvsStoreEntity() {
        return new KvsEgStoreExample();
    }

    @Override
    public void acceptAllColumnMap(KvsStoreEntity entity, Map<String, ? extends Object> map) {
        if (map == null) {
            return;
        }

        KvsEgBsStoreExample storeExample = (KvsEgBsStoreExample) entity;
        storeExample.setEgkey(toAnalyzedTypeValue(String.class, map.get("egkey")));
        storeExample.setEgId(toAnalyzedTypeValue(Integer.class, map.get("egId")));
        storeExample.setEgName(toAnalyzedTypeValue(String.class, map.get("egName")));
        storeExample.setExpireDatetime(toAnalyzedTypeValue(java.time.LocalDateTime.class, map.get("expireDatetime")));
    }

    @Override
    public List<Object> extractKeyList(KvsStoreConditionBean cb) {
        List<Object> keyList = new ArrayList<Object>();
        KvsEgStoreExampleCB storeExampleCB = (KvsEgStoreExampleCB) cb;

        keyList.add(storeExampleCB.query().getEgkey_Equal());
        return keyList;
    }

    @Override
    public List<Object> extractKeyList(KvsStoreEntity entity) {
        List<Object> keyList = new ArrayList<Object>();
        KvsEgStoreExample storeExample = (KvsEgStoreExample) entity;

        keyList.add(storeExample.getEgkey());
        return keyList;
    }

    @Override
    public Map<String, Object> extractAllColumnMap(KvsStoreEntity entity) {
        if (entity == null) {
            return null;
        }

        KvsEgStoreExample storeExample = (KvsEgStoreExample) entity;

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("egkey", storeExample.getEgkey());
        map.put("egId", storeExample.getEgId());
        map.put("egName", storeExample.getEgName());
        map.put("expireDatetime", formatLocalDateTime(storeExample.getExpireDatetime()));
        return map;
    }

    @Override
    public void validateKeyColumn(KvsStoreConditionBean cb) {
        KvsEgStoreExampleCB storeExampleCB = (KvsEgStoreExampleCB) cb;

        DfAssertUtil.assertStringNotNullAndNotTrimmedEmpty("egkey", storeExampleCB.query().getEgkey_Equal());
    }

    @Override
    public void validateKeyColumn(KvsStoreEntity entity) {
        KvsEgStoreExample storeExample = (KvsEgStoreExample) entity;

        DfAssertUtil.assertStringNotNullAndNotTrimmedEmpty("egkey", storeExample.getEgkey());
    }

    @Override
    public void validateAllColumn(KvsStoreEntity entity) {
        if (entity == null) {
             return;
        }

        KvsEgStoreExample storeExample = (KvsEgStoreExample) entity;

        DfAssertUtil.assertStringNotNullAndNotTrimmedEmpty("egkey", storeExample.getEgkey());
        DfAssertUtil.assertObjectNotNull("egId", storeExample.getEgId());
        DfAssertUtil.assertStringNotNullAndNotTrimmedEmpty("egName", storeExample.getEgName());
        DfAssertUtil.assertObjectNotNull("expireDatetime", storeExample.getExpireDatetime());
    }
}
