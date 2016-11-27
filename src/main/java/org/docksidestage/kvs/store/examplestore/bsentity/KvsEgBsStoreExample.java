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
package org.docksidestage.kvs.store.examplestore.bsentity;

import java.io.Serializable;

import org.dbflute.kvs.store.entity.KvsStoreEntity;
import org.dbflute.kvs.store.entity.dbmeta.KvsStoreDBMeta;

import org.docksidestage.kvs.store.examplestore.bsentity.dbmeta.KvsEgStoreExampleDbm;

/**
 * The entity of (Description about this KVS store)StoreExample.
 * @author FreeGen
 */
public abstract class KvsEgBsStoreExample implements KvsStoreEntity, Serializable {

    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** egkey: this column will be used as (a part of) KVS key */
    protected String _egkey;

    /** egId: id */
    protected Integer _egId;

    /** egName: name */
    protected String _egName;

    /** expireDatetime: time to live */
    protected java.time.LocalDateTime _expireDatetime;

    // [Referrers] *comment only

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * this column will be used as (a part of) KVS keyを返す。
     * @return this column will be used as (a part of) KVS key
     */
    public String getEgkey() {
        return _egkey;
    }

    /**
     * this column will be used as (a part of) KVS keyを設定する。
     * @param egkey this column will be used as (a part of) KVS key
     */
    public void setEgkey(String egkey) {
        _egkey = egkey;
    }

    /**
     * idを返す。
     * @return id
     */
    public Integer getEgId() {
        return _egId;
    }

    /**
     * idを設定する。
     * @param egId id
     */
    public void setEgId(Integer egId) {
        _egId = egId;
    }

    /**
     * nameを返す。
     * @return name
     */
    public String getEgName() {
        return _egName;
    }

    /**
     * nameを設定する。
     * @param egName name
     */
    public void setEgName(String egName) {
        _egName = egName;
    }

    /**
     * time to liveを返す。
     * @return time to live
     */
    public java.time.LocalDateTime getExpireDatetime() {
        return _expireDatetime;
    }

    /**
     * time to liveを設定する。
     * @param expireDatetime time to live
     */
    public void setExpireDatetime(java.time.LocalDateTime expireDatetime) {
        _expireDatetime = expireDatetime;
    }

    @Override
    public KvsStoreDBMeta asDBMeta() {
        return KvsEgStoreExampleDbm.getInstance();
    }

    @Override
    public String toString() {
        String delimiter = ", ";
        StringBuilder sb = new StringBuilder();
        sb.append(delimiter).append(getEgkey());
        sb.append(delimiter).append(getEgId());
        sb.append(delimiter).append(getEgName());
        sb.append(delimiter).append(getExpireDatetime());
        if (sb.length() > delimiter.length()) {
            sb.delete(0, delimiter.length());
        }
        sb.insert(0, super.toString() + "{").append("}");
        return sb.toString();
    }
}
