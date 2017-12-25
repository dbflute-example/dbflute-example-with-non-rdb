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
package org.dbflute.kvs.store.entity.dbmeta;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.dbflute.util.DfReflectionUtil;
import org.dbflute.util.DfCollectionUtil;

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

    protected <T extends Object> T toAnalyzedTypeValue(Class<?> targetClass, String fieldName, Object value) {
        Class<?> type = DfReflectionUtil.getAccessibleField(targetClass, fieldName).getType();
        if (org.dbflute.jdbc.Classification.class.isAssignableFrom(type)) {
            Method ofMethod = DfReflectionUtil.getPublicMethod(type, "codeOf", new Class<?>[] { Object.class });
            @SuppressWarnings("unchecked")
            T t = (T) DfReflectionUtil.invokeStatic(ofMethod, new Object[] { value });
            return t;
        }
        Map<String, Object> logInfoMap = DfCollectionUtil.newLinkedHashMap();
        logInfoMap.put("targetClass", targetClass);
        logInfoMap.put("fieldName", fieldName);
        logInfoMap.put("value", value);
        throw new IllegalArgumentException("couldn't analyze. " + logInfoMap);
    }
}
