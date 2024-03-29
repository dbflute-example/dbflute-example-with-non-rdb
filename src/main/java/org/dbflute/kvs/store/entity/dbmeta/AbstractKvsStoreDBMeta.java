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

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.dbflute.kvs.store.entity.KvsStoreEntity;
import org.dbflute.util.DfCollectionUtil;
import org.dbflute.util.DfReflectionUtil;
import org.dbflute.util.DfTypeUtil;

/**
 * @author FreeGen
 */
public abstract class AbstractKvsStoreDBMeta implements KvsStoreDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    /** 日フォーマッタ。 */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /** 日時フォーマッタ。 */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    protected LocalDate parseLocalDate(String localDate) {
        return localDate == null ? null : LocalDate.from(DATE_FORMATTER.parse(localDate));
    }

    protected String formatLocalDate(LocalDate localDate) {
        return localDate == null ? null : DATE_FORMATTER.format(localDate);
    }

    protected LocalDateTime parseLocalDateTime(String localDateTime) {
        return localDateTime == null ? null : LocalDateTime.from(DATE_TIME_FORMATTER.parse(localDateTime));
    }

    protected String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : DATE_TIME_FORMATTER.format(localDateTime);
    }

    protected <T extends Object> T toAnalyzedTypeValue(Class<?> targetClass, Object value) {
        if (value == null) {
            return null;
        }
        if (String.class.isAssignableFrom(targetClass)) {
            @SuppressWarnings("unchecked")
            T t = (T) value;
            return t;
        }
        if (targetClass.equals(Boolean.class)) {
            @SuppressWarnings("unchecked")
            T t = (T) DfTypeUtil.toBoolean(value);
            return t;
        }
        if (targetClass.equals(Integer.class)) {
            @SuppressWarnings("unchecked")
            T t = (T) DfTypeUtil.toInteger(value);
            return t;
        }
        if (targetClass.equals(Long.class)) {
            @SuppressWarnings("unchecked")
            T t = (T) DfTypeUtil.toLong(value);
            return t;
        }
        if (targetClass.equals(Float.class)) {
            @SuppressWarnings("unchecked")
            T t = (T) DfTypeUtil.toFloat(value);
            return t;
        }
        if (targetClass.equals(BigDecimal.class)) {
            @SuppressWarnings("unchecked")
            T t = (T) DfTypeUtil.toBigDecimal(value);
            return t;
        }
        if (targetClass.equals(LocalDate.class)) {
            @SuppressWarnings("unchecked")
            T t = (T) parseLocalDate((String) value);
            return t;
        }
        if (targetClass.equals(LocalDateTime.class)) {
            @SuppressWarnings("unchecked")
            T t = (T) parseLocalDateTime((String) value);
            return t;
        }
        if (KvsStoreEntity.class.isAssignableFrom(targetClass)) {
            KvsStoreEntity entity = (KvsStoreEntity) DfReflectionUtil.newInstance(targetClass);
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) value;
            entity.asDBMeta().acceptAllColumnMap(entity, map);
            @SuppressWarnings("unchecked")
            T t = (T) entity;
            return t;
        }
        if (org.dbflute.jdbc.Classification.class.isAssignableFrom(targetClass)) {
            Method ofMethod = DfReflectionUtil.getPublicMethod(targetClass, "codeOf", new Class<?>[] { Object.class });
            @SuppressWarnings("unchecked")
            T t = (T) DfReflectionUtil.invokeStatic(ofMethod, new Object[] { value });
            return t;
        }
        Map<String, Object> logInfoMap = DfCollectionUtil.newLinkedHashMap();
        logInfoMap.put("targetClass", targetClass);
        logInfoMap.put("value", value);
        throw new IllegalArgumentException("couldn't analyze. " + logInfoMap);
    }
}
