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
package org.dbflute.kvs.cache.bhv;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.dbflute.Entity;
import org.dbflute.cbean.ConditionBean;
import org.dbflute.kvs.cache.cbean.KvsCacheConditionBean;
import org.dbflute.kvs.cache.facade.KvsCacheFacade;
import org.dbflute.util.DfStringUtil;

/**
 * @author FreeGen
 */
public abstract class AbstractKvsCacheBehaviorWritable<ENTITY extends Entity, CB extends ConditionBean, KVS_CB extends KvsCacheConditionBean> {

    protected KVS_CB createCB(Consumer<KVS_CB> cbCall) { // CB from callback
        assertCBCallNotNull(cbCall);
        final KVS_CB cb = newConditionBean();
        cbCall.accept(cb);
        return cb;
    }

    public abstract KVS_CB newConditionBean();

    // -----------------------------------------------------
    //                                        expireDateTime
    //                                        --------------
    protected Function<ENTITY, LocalDateTime> expireDateTimeLambda(KvsCacheFacade kvsCacheFacade, String expireDateTimeColumnFlexibleName) {
        return entity -> {
            if (entity == null || DfStringUtil.is_Null_or_Empty(expireDateTimeColumnFlexibleName)) {
                return kvsCacheFacade.calcExpireDateTime(kvsCacheFacade.cacheTtl());
            }
            return entity.asDBMeta().findColumnInfo(expireDateTimeColumnFlexibleName).read(entity);
        };
    }

    protected Function<List<ENTITY>, LocalDateTime> expireDateTimeOfMaxInListLambda(KvsCacheFacade kvsCacheFacade,
            String expireDateTimeColumnFlexibleName) {
        return entityList -> {
            if (DfStringUtil.is_Null_or_Empty(expireDateTimeColumnFlexibleName)) {
                return kvsCacheFacade.calcExpireDateTime(kvsCacheFacade.cacheTtl());
            }
            return entityList.stream()
                    .map(entity -> (LocalDateTime) entity.asDBMeta().findColumnInfo(expireDateTimeColumnFlexibleName).read(entity))
                    .max(Comparator.comparing(dateTime -> dateTime))
                    .orElseGet(() -> kvsCacheFacade.calcExpireDateTime(kvsCacheFacade.cacheTtl()));
        };
    }

    protected Function<List<ENTITY>, LocalDateTime> expireDateTimeOfMinInListLambda(KvsCacheFacade kvsCacheFacade,
            String expireDateTimeColumnFlexibleName) {
        return entityList -> {
            if (DfStringUtil.is_Null_or_Empty(expireDateTimeColumnFlexibleName)) {
                return kvsCacheFacade.calcExpireDateTime(kvsCacheFacade.cacheTtl());
            }
            return entityList.stream()
                    .map(entity -> (LocalDateTime) entity.asDBMeta().findColumnInfo(expireDateTimeColumnFlexibleName).read(entity))
                    .min(Comparator.comparing(dateTime -> dateTime))
                    .orElseGet(() -> kvsCacheFacade.calcExpireDateTime(kvsCacheFacade.cacheTtl()));
        };
    }

    // -----------------------------------------------------
    //                                                Assert
    //                                                ------
    /**
     * Assert that the callback of condition-bean is not null.
     * @param cbCall The interface of kvs-condition-bean to be checked. (NotNull)
     */
    protected void assertCBCallNotNull(Consumer<KVS_CB> cbCall) {
        assertObjectNotNull("cbLambda", cbCall);
    }

    /**
     * Assert that the object is not null.
     * @param variableName The variable name for message. (NotNull)
     * @param value The value the checked variable. (NotNull)
     * @throws IllegalArgumentException When the variable name or the variable is null.
     */
    protected void assertObjectNotNull(String variableName, Object value) {
        if (variableName == null) {
            String msg = "The value should not be null: variableName=null value=" + value;
            throw new IllegalArgumentException(msg);
        }
        if (value == null) {
            String msg = "The value should not be null: variableName=" + variableName;
            throw new IllegalArgumentException(msg);
        }
    }
}
