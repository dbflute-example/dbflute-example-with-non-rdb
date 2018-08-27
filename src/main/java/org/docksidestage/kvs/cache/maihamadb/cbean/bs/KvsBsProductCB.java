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
package org.docksidestage.kvs.cache.maihamadb.cbean.bs;

import org.dbflute.kvs.cache.cbean.KvsCacheConditionBean;
import org.dbflute.optional.OptionalThing;

import org.docksidestage.dbflute.cbean.ProductCB;
import org.docksidestage.kvs.cache.maihamadb.cbean.cq.KvsProductCQ;

/**
 * The base condition-bean of (Product)Product.
 * @author FreeGen
 */
public abstract class KvsBsProductCB implements KvsCacheConditionBean {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    protected Integer fetchSize;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected KvsProductCQ _conditionQuery;

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    public KvsProductCQ query() {
        return doGetConditionQuery();
    }

    protected KvsProductCQ doGetConditionQuery() {
        if (_conditionQuery == null) {
            _conditionQuery = new KvsProductCQ();
        }
        return _conditionQuery;
    }

    public void fetchFirst(int fetchSize) {
        this.fetchSize = fetchSize;
    }

    // ===================================================================================
    //                                                                             xget...
    //                                                                             =======
    public OptionalThing<Integer> xgetFetchFirst() {
        return OptionalThing.ofNullable(fetchSize, () -> {
            throw new IllegalStateException("'fetchFirst(int)' has not been called.");
        });
    }

    // ===================================================================================
    //                                                                               to...
    //                                                                               =====
    public ProductCB toProductCB() {
        ProductCB cb = new ProductCB();

        // where
        query().xdfgetColumnEqualValue().entrySet().stream().forEach(entry -> {
            cb.query().invokeQueryEqual(entry.getKey().getColumnDbName(), entry.getValue());
        });

        // orderBy
        query().xdfgetColumnOrderBy().entrySet().stream().forEach(entry -> {
            cb.query().invokeOrderBy(entry.getKey().getColumnDbName(), entry.getValue());
        });

        // fetchFirst
        xgetFetchFirst().ifPresent(fetchSize -> cb.fetchFirst(fetchSize));

        return cb;
    }
}
