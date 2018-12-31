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
package org.docksidestage.kvs.store.examplestore.cbean.cq.bs;

import java.util.Collection;

import org.dbflute.kvs.core.assertion.KvsAssertion;

/**
 * The base condition-query of (Description about this KVS store)StoreExample.
 * @author FreeGen
 */
public abstract class KvsEgBsStoreExampleCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** egkey: this column will be used as (a part of) KVS key */
    protected String _egkey;

    protected Collection<String> _egkeyList;

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * this column will be used as (a part of) KVS keyを返す。
     * @return this column will be used as (a part of) KVS key
     */
    public String getEgkey_Equal() {
        return _egkey;
    }

    /**
     * this column will be used as (a part of) KVS keyを設定する。
     * @param egkey this column will be used as (a part of) KVS key
     */
    public void setEgkey_Equal(String egkey) {
        KvsAssertion.assertNullQuery("egkey", _egkey);
        KvsAssertion.assertNullQuery("egkeyList", _egkeyList);
        _egkey = egkey;
    }

    public Collection<String> xdfgetEgkeyList() {
        return _egkeyList;
    }

    public void setEgkey_InScope(Collection<String> egkeyList) {
        KvsAssertion.assertNullQuery("egkey", _egkey);
        KvsAssertion.assertNullQuery("egkeyList", _egkeyList);
        _egkeyList = egkeyList;
    }

    @Override
    public String toString() {
        String delimiter = ", ";
        StringBuilder sb = new StringBuilder();
        sb.append(delimiter).append(_egkey);
        if (sb.length() > delimiter.length()) {
            sb.delete(0, delimiter.length());
        }
        sb.insert(0, super.toString() + "{").append("}");
        return sb.toString();
    }
}
