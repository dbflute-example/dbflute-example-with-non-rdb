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
package org.dbflute.kvs.cache.facade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.dbflute.Entity;
import org.dbflute.bhv.BehaviorReadable;
import org.dbflute.bhv.BehaviorSelector;
import org.dbflute.cbean.ConditionBean;
import org.dbflute.cbean.ckey.ConditionKey;
import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.info.ColumnInfo;
import org.dbflute.dbmeta.info.PrimaryInfo;
import org.dbflute.kvs.cache.KvsCacheBusinessAssist;
import org.dbflute.kvs.cache.KvsCacheManager;
import org.dbflute.kvs.cache.bhv.writable.DeleteOption;
import org.dbflute.kvs.cache.bhv.writable.InsertOrUpdateOption;
import org.dbflute.kvs.core.exception.KvsException;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.util.DfCollectionUtil;
import org.lastaflute.core.magic.ThreadCacheContext;

/**
 * @author FreeGen
 */
public abstract class AbstractKvsCacheFacade implements KvsCacheFacade {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private KvsCacheManager kvsCacheManager;
    @Resource
    private KvsCacheBusinessAssist kvsCacheBusinessAssist;
    @Resource
    private BehaviorSelector behaviorSelector;
    @Resource
    private Integer cacheTtl;

    // ===================================================================================
    //                                                                Find (For KVS Cache)
    //                                                                ====================
    @Override
    public <ENTITY extends Entity> OptionalEntity<ENTITY> findEntity(List<Object> searchKeyList, ConditionBean cb) {
        return findEntity(searchKeyList, cb, entity -> calcExpireDateTime(cacheTtl()), false);
    }

    @Override
    public <ENTITY extends Entity> OptionalEntity<ENTITY> findEntity(List<Object> searchKeyList, ConditionBean cb,
            Function<ENTITY, LocalDateTime> expireDateTimeLambda, boolean kvsCacheAsyncReflectionEnabled) {
        return kvsCacheBusinessAssist.findEntity(mySelector(), cb.asDBMeta().getProjectName(), searchKeyList, cb, expireDateTimeLambda,
                kvsCacheAsyncReflectionEnabled);
    }

    @Override
    public <ENTITY extends Entity> List<ENTITY> findList(List<Object> searchKeyList, ConditionBean cb) {
        return findList(searchKeyList, cb, entity -> calcExpireDateTime(cacheTtl()), false);
    }

    @Override
    public <ENTITY extends Entity> List<ENTITY> findList(List<Object> searchKeyList, ConditionBean cb,
            Function<List<ENTITY>, LocalDateTime> expireDateTimeLambda, boolean kvsCacheAsyncReflectionEnabled) {
        return kvsCacheBusinessAssist.findList(mySelector(), cb.asDBMeta().getProjectName(), searchKeyList, cb, expireDateTimeLambda,
                kvsCacheAsyncReflectionEnabled);
    }

    // ===================================================================================
    //                                                    Insert OR Update (For KVS Cache)
    //                                                    ================================
    @Override
    public <ENTITY extends Entity> void insertOrUpdate(List<Object> searchKeyList, ENTITY entity, InsertOrUpdateOption op) {
        kvsCacheBusinessAssist.insertOrUpdate(mySelector(), entity.asDBMeta().getProjectName(), searchKeyList, entity, op);
    }

    // ===================================================================================
    //                                                              Delete (For KVS Cache)
    //                                                              ======================
    @Override
    public <ENTITY extends Entity> int delete(List<Object> searchKeyList, ENTITY entity, DeleteOption op) {
        return kvsCacheBusinessAssist.delete(mySelector(), entity.asDBMeta().getProjectName(), searchKeyList, entity, op);
    }

    @Override
    public int queryDelete(List<Object> searchKeyList, ConditionBean cb) {
        return kvsCacheBusinessAssist.queryDelete(mySelector(), cb.asDBMeta().getProjectName(), searchKeyList, cb);
    }

    // ===================================================================================
    //                                                        Find (For CB-Embedded Cache)
    //                                                        ============================
    @Override
    public <ENTITY extends Entity> OptionalEntity<ENTITY> findEntityById(Object id, DBMeta dbmeta, Set<ColumnInfo> specifiedColumnInfoSet) {
        return findEntityById(id, dbmeta, specifiedColumnInfoSet, entity -> calcExpireDateTime(cacheTtl()), false);
    }

    @Override
    public <ENTITY extends Entity> OptionalEntity<ENTITY> findEntityById(Object id, DBMeta dbmeta, Set<ColumnInfo> specifiedColumnInfoSet,
            Function<ENTITY, LocalDateTime> expireDateTimeLambda, boolean kvsCacheAsyncReflectionEnabled) {
        final List<Object> searchKeyList = new ArrayList<Object>(1);
        searchKeyList.add(id);
        final BehaviorReadable readable = behaviorSelector.byName(dbmeta.getTableDbName());
        final PrimaryInfo primaryInfo = dbmeta.getPrimaryInfo();
        assertOnlyOnePrimaryKey(primaryInfo);
        final ConditionBean cb = readable.newConditionBean();
        final Map<String, Object> primaryKeyMap = new HashMap<String, Object>(1);
        primaryKeyMap.put(primaryInfo.getFirstColumn().getColumnDbName(), id);
        cb.acceptPrimaryKeyMap(primaryKeyMap);
        return kvsCacheBusinessAssist.findEntity(mySelector(), cb.asDBMeta().getProjectName(), searchKeyList, cb,
                entity -> calcExpireDateTime(cacheTtl()), specifiedColumnInfoSet, kvsCacheAsyncReflectionEnabled);
    }

    @Override
    public void loadThreadCacheByIds(List<Object> idList, DBMeta dbmeta, Set<ColumnInfo> specifiedColumnInfoSet) {
        final PrimaryInfo primaryInfo = dbmeta.getPrimaryInfo();
        assertOnlyOnePrimaryKey(primaryInfo);
        final String pkName = primaryInfo.getFirstColumn().getColumnDbName();
        kvsCacheBusinessAssist.loadThreadCacheByIds(behaviorSelector, dbmeta.getProjectName(),
                idList.stream().map(id -> DfCollectionUtil.newArrayList((Object) id)).collect(Collectors.toList()), dbmeta, (cb, list) -> {
                    cb.localCQ().invokeQuery(pkName, ConditionKey.CK_IN_SCOPE.getConditionKey(), list.stream().flatMap(ids -> {
                        return ids.stream();
                    }).collect(Collectors.toList()));
                }, cacheTtl(), specifiedColumnInfoSet);
    }

    // ===================================================================================
    //                                                  ClearCache (For CB-Embedded Cache)
    //                                                  ==================================
    // unused
    @Override
    public <ENTITY extends Entity> void clearCache(List<Object> searchKeyList, ENTITY entity) {
        kvsCacheBusinessAssist.clearCache(mySelector(), entity.asDBMeta().getProjectName(), searchKeyList, entity);
    }

    @Override
    public <ENTITY extends Entity> void clearCache(ENTITY entity) {
        final DBMeta dbMeta = entity.asDBMeta();
        final List<Object> keyStringList = new ArrayList<Object>();
        dbMeta.extractPrimaryKeyMap(entity).forEach((key, value) -> {
            keyStringList.add(value);
        });
        String key = generateKeyForColumnNullObject(dbMeta.getProjectName(), dbMeta.getTableDbName(), keyStringList);
        ThreadCacheContext.removeObject(key);
        kvsCacheManager.delete(key);
    }

    // ===================================================================================
    //                                                                                 TTL
    //                                                                                 ===
    @Override
    public OptionalEntity<Long> ttl(List<Object> searchKeyList, ConditionBean cb) {
        String key = kvsCacheBusinessAssist.generateKey(cb.asDBMeta().getProjectName(), cb.asTableDbName(), searchKeyList);
        Long ttl = kvsCacheManager.ttl(key);
        return OptionalEntity.ofNullable(ttl, () -> {
            throw new IllegalArgumentException("Not found the entity by the condition. (might be deleted?)");
        });
    }

    @Override
    public LocalDateTime calcExpireDateTime(Long ttl) {
        return kvsCacheBusinessAssist.calcExpireDateTime(ttl);
    }

    @Override
    public Long cacheTtl() {
        return this.cacheTtl == null ? null : this.cacheTtl.longValue();
    }

    // ===================================================================================
    //                                                                               Other
    //                                                                               =====
    @Override
    public BehaviorSelector mySelector() {
        return this.behaviorSelector;
    }

    @Override
    public int getNumActive() {
        return kvsCacheManager.getNumActive();
    }

    // ===================================================================================
    //                                                Generate Key (For CB-Embedded Cache)
    //                                                ====================================
    protected String generateKeyForColumnNullObject(final String projectName, final String tableName, final List<Object> keyStringList) {
        return kvsCacheBusinessAssist.generateKeyForColumnNullObject(projectName, tableName, keyStringList);
    }

    // ===================================================================================
    //                                                                        Small Helper
    //                                                                        ============
    protected PrimaryInfo assertOnlyOnePrimaryKey(PrimaryInfo primaryInfo) {
        if (primaryInfo.isCompoundKey()) {
            throw new KvsException("This method is not available with concatenated primary key: " + primaryInfo);
        }
        return primaryInfo;
    }
}
