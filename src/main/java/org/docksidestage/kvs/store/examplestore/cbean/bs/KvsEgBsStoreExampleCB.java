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
package org.docksidestage.kvs.store.examplestore.cbean.bs;

import org.dbflute.kvs.store.cbean.KvsStoreConditionBean;
import org.dbflute.kvs.store.entity.dbmeta.KvsStoreDBMeta;

import org.docksidestage.kvs.store.examplestore.bsentity.dbmeta.KvsEgStoreExampleDbm;
import org.docksidestage.kvs.store.examplestore.cbean.KvsEgStoreExampleCB;
import org.docksidestage.kvs.store.examplestore.cbean.cq.KvsEgStoreExampleCQ;

/**
 * The base condition-bean of (Description about this KVS store)StoreExample.
 * @author FreeGen
 */
public abstract class KvsEgBsStoreExampleCB implements KvsStoreConditionBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected KvsEgStoreExampleCQ _conditionQuery;

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    public KvsEgStoreExampleCQ query() {
        return doGetConditionQuery();
    }

    protected KvsEgStoreExampleCQ doGetConditionQuery() {
        if (_conditionQuery == null) {
            _conditionQuery = new KvsEgStoreExampleCQ();
        }
        return _conditionQuery;
    }

    @Override
    public KvsStoreDBMeta asDBMeta() {
        return KvsEgStoreExampleDbm.getInstance();
    }

    public KvsEgStoreExampleCB acceptPK(String egkey) {
        KvsEgBsStoreExampleCB cb = this;
        cb.query().setEgkey_Equal(egkey);

        return (KvsEgStoreExampleCB) this;
    }
}
