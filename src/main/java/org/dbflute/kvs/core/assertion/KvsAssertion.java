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
package org.dbflute.kvs.core.assertion;

/**
 * @author FreeGen
 */
public class KvsAssertion {

    /**
     * assert null query.
     * @param property property
     * @param query query
     */
    public static void assertNullQuery(String property, Object query) {
        if (property == null) {
            throw new IllegalArgumentException("Please set property name.");
        } else if (query != null) {
            String msg = "Query for this property is already registered: property=" + property;
            msg += ", query=" + query.toString();
            throw new IllegalArgumentException(msg);
        }
    }
}
