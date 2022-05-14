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
package org.docksidestage.solr.bsentity.index;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

import org.dbflute.solr.entity.AbstractSolrIndexEntity;

/**
 * Base IndexEntity class of Solr schema "Example."
 * @author FreeGen
 */
public class SolrBsExampleIndex extends AbstractSolrIndexEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** product_id (long) */
    @Field("product_id")
    protected Long productId;

    /** latest_purchase_date (tdate) */
    @Field("latest_purchase_date")
    protected java.time.LocalDateTime latestPurchaseDate;

    /** product_category (string) */
    @Field("product_category")
    protected String productCategory;

    /** product_category_code (string) */
    @Field("product_category_code")
    protected String productCategoryCode;

    /** product_description (string) */
    @Field("product_description")
    protected String productDescription;

    /** product_handle_code (string) */
    @Field("product_handle_code")
    protected String productHandleCode;

    /** product_name (string) */
    @Field("product_name")
    protected String productName;

    /** product_status (string) */
    @Field("product_status")
    protected String productStatus;

    /** product_status_code (string) */
    @Field("product_status_code")
    protected String productStatusCode;

    /** regular_price (long) */
    @Field("regular_price")
    protected Long regularPrice;

    /** register_datetime (tdate) */
    @Field("register_datetime")
    protected java.time.LocalDateTime registerDatetime;

    /** register_user (string) */
    @Field("register_user")
    protected String registerUser;

    /** update_datetime (tdate) */
    @Field("update_datetime")
    protected java.time.LocalDateTime updateDatetime;

    /** update_user (string) */
    @Field("update_user")
    protected String updateUser;

    /** _version_ (long) */
    @Field("_version_")
    protected Long version;


    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long value) {
        this.productId = value;
    }

    public java.time.LocalDateTime getLatestPurchaseDate() {
        return this.latestPurchaseDate;
    }

    public void setLatestPurchaseDate(java.time.LocalDateTime value) {
        this.latestPurchaseDate = value;
    }

    public String getProductCategory() {
        return this.productCategory;
    }

    public void setProductCategory(String value) {
        this.productCategory = value;
    }

    public String getProductCategoryCode() {
        return this.productCategoryCode;
    }

    public void setProductCategoryCode(String value) {
        this.productCategoryCode = value;
    }

    public String getProductDescription() {
        return this.productDescription;
    }

    public void setProductDescription(String value) {
        this.productDescription = value;
    }

    public String getProductHandleCode() {
        return this.productHandleCode;
    }

    public void setProductHandleCode(String value) {
        this.productHandleCode = value;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String value) {
        this.productName = value;
    }

    public String getProductStatus() {
        return this.productStatus;
    }

    public void setProductStatus(String value) {
        this.productStatus = value;
    }

    public String getProductStatusCode() {
        return this.productStatusCode;
    }

    public void setProductStatusCode(String value) {
        this.productStatusCode = value;
    }

    public Long getRegularPrice() {
        return this.regularPrice;
    }

    public void setRegularPrice(Long value) {
        this.regularPrice = value;
    }

    public java.time.LocalDateTime getRegisterDatetime() {
        return this.registerDatetime;
    }

    public void setRegisterDatetime(java.time.LocalDateTime value) {
        this.registerDatetime = value;
    }

    public String getRegisterUser() {
        return this.registerUser;
    }

    public void setRegisterUser(String value) {
        this.registerUser = value;
    }

    public java.time.LocalDateTime getUpdateDatetime() {
        return this.updateDatetime;
    }

    public void setUpdateDatetime(java.time.LocalDateTime value) {
        this.updateDatetime = value;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String value) {
        this.updateUser = value;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long value) {
        this.version = value;
    }

}
