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
package org.dbflute.kvs.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.dbflute.Entity;
import org.dbflute.bhv.BehaviorWritable;
import org.dbflute.bhv.core.BehaviorCommandMeta;
import org.dbflute.bhv.writable.WritableOption;
import org.dbflute.cbean.ConditionBean;
import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.accessory.ColumnNullObjectable;
import org.dbflute.dbmeta.info.ColumnInfo;
import org.dbflute.dbmeta.info.PrimaryInfo;
import org.dbflute.kvs.cache.facade.KvsCacheFacade;
import org.dbflute.kvs.core.exception.KvsException;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.optional.OptionalThing;
import org.dbflute.util.DfCollectionUtil;
import org.lastaflute.core.magic.ThreadCacheContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author FreeGen
 */
public class KvsCacheColumnNullObject {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    protected static final KvsCacheColumnNullObject _instance = new KvsCacheColumnNullObject();

    private static final Logger logger = LoggerFactory.getLogger(KvsCacheColumnNullObject.class);

    private static final String KVS_CACHE_CLEAR_PREFIX = "KVS_CACHE_CLEAR_LIST_";

    public static KvsCacheColumnNullObject getInstance() {
        return _instance;
    }

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected Map<String, KvsCacheFacade> _kvsCacheFacadeMap;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    protected KvsCacheColumnNullObject() {
    }

    // ===================================================================================
    //                                                                                Init
    //                                                                                ====
    public void init(Map<String, KvsCacheFacade> kvsCacheFacadeMap) {
        _kvsCacheFacadeMap = kvsCacheFacadeMap;
    }

    // ===================================================================================
    //                                                                        Column Cache
    //                                                                        ============
    public <PROP> PROP findColumn(Entity entity, String columnName, Object primaryKey) {
        return this.findColumn(entity, columnName, primaryKey, true);
    }

    public <PROP> PROP findColumn(Entity entity, String columnName, Object primaryKey, boolean allColumnNullObject) {
        logger.debug("tableName={}, columnName={}, primaryKey={}", entity.asTableDbName(), columnName, primaryKey);
        if (primaryKey == null) { // basically no way, just in case
            return null;
        }
        final DBMeta dbMeta = entity.asDBMeta();
        final Set<ColumnInfo> specifiedColumnInfoSet;
        if (allColumnNullObject) {
            specifiedColumnInfoSet = entity.myspecifiedProperties().stream().map(property -> {
                return dbMeta.findColumnInfo(property);
            }).filter(columnInfo -> columnInfo.canBeNullObject()).collect(Collectors.toSet());
        } else {
            specifiedColumnInfoSet = DfCollectionUtil.newLinkedHashSet(dbMeta.findColumnInfo(columnName));
        }
        final OptionalEntity<Entity> optCached = getKvsCacheFacade(dbMeta).findEntityById(primaryKey, dbMeta, specifiedColumnInfoSet);

        if (!optCached.isPresent()) {
            return null;
        }

        final Entity cached = optCached.get();

        if (cached instanceof ColumnNullObjectable) {
            ((ColumnNullObjectable) cached).disableColumnNullObject();
        }

        try {
            PROP value = read(columnName, cached);
            logger.debug("tableName={}, columnName={}, primaryKey={}, value={}", entity.asTableDbName(), columnName, primaryKey, value);
            return value;
        } finally {
            if (cached instanceof ColumnNullObjectable) {
                ((ColumnNullObjectable) cached).enableColumnNullObject();
            }
        }
    }

    protected <PROP> PROP read(String columnName, Entity cached) {
        return findColumnInfo(cached.asDBMeta(), columnName).read(cached);
    }

    protected ColumnInfo findColumnInfo(DBMeta dbMeta, String columnName) {
        return dbMeta.findColumnInfo(columnName);
    }

    /**
     * Load thread cache and KVS cache including target column(s) of ColumnNullObjectable.
     * @param dbMeta DBMeta
     * @param list A list of Entity objects
     */
    public void loadThreadCache(DBMeta dbMeta, List<? extends Entity> list) {
        final List<Object> idList = DfCollectionUtil.newArrayList();
        final Set<ColumnInfo> specifiedColumnInfoSet = DfCollectionUtil.newHashSet();
        list.stream().forEach(entity -> {
            PrimaryInfo primaryInfo = entity.asDBMeta().getPrimaryInfo();
            if (primaryInfo.isCompoundKey()) {
                throw new KvsException("This method is not available with concatenated primary key: " + primaryInfo);
            }
            idList.add(primaryInfo.getFirstColumn().getPropertyGateway().read(entity));
            specifiedColumnInfoSet.addAll(dbMeta.getColumnInfoList()
                    .stream()
                    .filter(columnInfo -> entity.myspecifiedProperties().contains(columnInfo.getPropertyName()))
                    .collect(Collectors.toSet()));
        });

        if (specifiedColumnInfoSet.isEmpty()) {
            specifiedColumnInfoSet.addAll(dbMeta.getColumnInfoList());
        }

        getKvsCacheFacade(dbMeta).loadThreadCacheByIds(idList, dbMeta, specifiedColumnInfoSet);
    }

    /**
     * KVSのキャッシュをクリアするために保存する。
     * @param command The command meta of behavior for update. (NotNull)
     * @param entityResource The resource of entity for update, entity or list. (NotNull)
     * @param cbResource The optional resource of condition-bean for update. (NotNull, EmptyAllowed: except query-update)
     * @param option The optional option of update. (NotNull, EmptyAllowed: when no option)
     */
    public void saveForKvsCacheClear(BehaviorCommandMeta command, Object entityResource, OptionalThing<ConditionBean> cbResource,
            OptionalThing<? extends WritableOption<? extends ConditionBean>> option) {
        cbResource.ifPresent(cb -> {
            final DBMeta dbMeta = command.getDBMeta();
            final PrimaryInfo primaryInfo = dbMeta.getPrimaryInfo();
            try {
                primaryInfo.getPrimaryColumnList().forEach(column -> cb.invokeSpecifyColumn(column.getPropertyName()));
                final BehaviorWritable bhv = (BehaviorWritable) getKvsCacheFacade(dbMeta).mySelector().byName(dbMeta.getTableDbName());
                final List<Entity> readList = bhv.readList(cb);
                ThreadCacheContext.setObject(generateThreadCacheKey(command.getDBMeta()), readList);
            } finally {
                cb.getSqlClause().clearSpecifiedSelectColumn();
            }
        });
    }

    /**
     * KVSのキャッシュをクリアする。
     * @param command The command meta of behavior for update. (NotNull)
     * @param entityResource The resource of entity for update, entity or list. (NotNull)
     * @param cbResource The optional resource of condition-bean for update. (NotNull, EmptyAllowed: except query-update)
     * @param option The optional option of update. (NotNull, EmptyAllowed: when no option)
     * @param cause The optional cause exception from update, but not contains update count check. (NotNull, EmptyAllowed: when no failure)
     */
    public void clearKvsCache(BehaviorCommandMeta command, Object entityResource, OptionalThing<ConditionBean> cbResource,
            OptionalThing<? extends WritableOption<? extends ConditionBean>> option, OptionalThing<RuntimeException> cause) {
        if (cause.isPresent()) {
            return;
        }
        List<Entity> list = null;
        @SuppressWarnings("unchecked")
        List<Entity> saveList = (List<Entity>) ThreadCacheContext.removeObject(generateThreadCacheKey(command.getDBMeta()));
        if (saveList != null) {
            list = saveList;
        } else if (entityResource instanceof List<?>) {
            @SuppressWarnings("unchecked")
            final List<Entity> tempList = (List<Entity>) entityResource;
            list = tempList;
        } else if (entityResource instanceof Entity) {
            list = DfCollectionUtil.newArrayList((Entity) entityResource);
        }
        if (list != null) {
            list.forEach(sakuhinDetail -> getKvsCacheFacade(command.getDBMeta()).clearCache(sakuhinDetail));
        }
    }

    // ===================================================================================
    //                                                                        Small Helper
    //                                                                        ============
    protected String generateThreadCacheKey(DBMeta dbMeta) {
        return KVS_CACHE_CLEAR_PREFIX + dbMeta.getTableDbName();
    }

    protected KvsCacheFacade getKvsCacheFacade(DBMeta dbMeta) {
        return _kvsCacheFacadeMap.get(dbMeta.getProjectName());
    }
}
