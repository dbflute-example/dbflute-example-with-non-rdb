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
package org.docksidestage.solr.bsentity;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;
import org.dbflute.solr.entity.AbstractSolrEntity;

/**
 * Base Entity class of Solr schema "Example."
 * @author FreeGen
 */
public class SolrBsExample extends AbstractSolrEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** latest_purchase_date (tdate) */
    protected java.time.LocalDateTime latestPurchaseDate;

    /** product_category (string) */
    protected String productCategory;

    /** product_category_code (string) */
    protected String productCategoryCode;

    /** product_description (string) */
    protected String productDescription;

    /** product_handle_code (string) */
    protected String productHandleCode;

    /** product_name (string) */
    protected String productName;

    /** product_status (string) */
    protected String productStatus;

    /** product_status_code (string) */
    protected String productStatusCode;

    /** register_datetime (tdate) */
    protected java.time.LocalDateTime registerDatetime;

    /** register_user (string) */
    protected String registerUser;

    /** regular_price (long) */
    protected Long regularPrice;

    /** update_datetime (tdate) */
    protected java.time.LocalDateTime updateDatetime;

    /** update_user (string) */
    protected String updateUser;

    // ===================================================================================
    //                                                                        Solr DB Meta
    //                                                                        ============
    @Override
    public String asSchemaName() {
        return "Example";
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public java.time.LocalDateTime getLatestPurchaseDate() {
        checkSpecifiedProperty("latestPurchaseDate");
        return latestPurchaseDate;
    }

    @Field("latest_purchase_date")
    protected void _setLatestPurchaseDate(java.util.Date value) {
        registerModifiedProperty("latestPurchaseDate");
        latestPurchaseDate = new org.dbflute.helper.HandyDate(value).getLocalDateTime();
    }

    public void setLatestPurchaseDate(java.time.LocalDateTime value) {
        registerModifiedProperty("latestPurchaseDate");
        latestPurchaseDate = value;
    }

    public String getProductCategory() {
        checkSpecifiedProperty("productCategory");
        return productCategory;
    }

    @Field("product_category")
    public void setProductCategory(String value) {
        registerModifiedProperty("productCategory");
        productCategory = value;
    }

    public String getProductCategoryCode() {
        checkSpecifiedProperty("productCategoryCode");
        return productCategoryCode;
    }

    @Field("product_category_code")
    public void setProductCategoryCode(String value) {
        registerModifiedProperty("productCategoryCode");
        productCategoryCode = value;
    }

    public String getProductDescription() {
        checkSpecifiedProperty("productDescription");
        return productDescription;
    }

    @Field("product_description")
    public void setProductDescription(String value) {
        registerModifiedProperty("productDescription");
        productDescription = value;
    }

    public String getProductHandleCode() {
        checkSpecifiedProperty("productHandleCode");
        return productHandleCode;
    }

    @Field("product_handle_code")
    public void setProductHandleCode(String value) {
        registerModifiedProperty("productHandleCode");
        productHandleCode = value;
    }

    public String getProductName() {
        checkSpecifiedProperty("productName");
        return productName;
    }

    @Field("product_name")
    public void setProductName(String value) {
        registerModifiedProperty("productName");
        productName = value;
    }

    public String getProductStatus() {
        checkSpecifiedProperty("productStatus");
        return productStatus;
    }

    @Field("product_status")
    public void setProductStatus(String value) {
        registerModifiedProperty("productStatus");
        productStatus = value;
    }

    public String getProductStatusCode() {
        checkSpecifiedProperty("productStatusCode");
        return productStatusCode;
    }

    @Field("product_status_code")
    public void setProductStatusCode(String value) {
        registerModifiedProperty("productStatusCode");
        productStatusCode = value;
    }

    public java.time.LocalDateTime getRegisterDatetime() {
        checkSpecifiedProperty("registerDatetime");
        return registerDatetime;
    }

    @Field("register_datetime")
    protected void _setRegisterDatetime(java.util.Date value) {
        registerModifiedProperty("registerDatetime");
        registerDatetime = new org.dbflute.helper.HandyDate(value).getLocalDateTime();
    }

    public void setRegisterDatetime(java.time.LocalDateTime value) {
        registerModifiedProperty("registerDatetime");
        registerDatetime = value;
    }

    public String getRegisterUser() {
        checkSpecifiedProperty("registerUser");
        return registerUser;
    }

    @Field("register_user")
    public void setRegisterUser(String value) {
        registerModifiedProperty("registerUser");
        registerUser = value;
    }

    public Long getRegularPrice() {
        checkSpecifiedProperty("regularPrice");
        return regularPrice;
    }

    @Field("regular_price")
    public void setRegularPrice(Long value) {
        registerModifiedProperty("regularPrice");
        regularPrice = value;
    }

    public java.time.LocalDateTime getUpdateDatetime() {
        checkSpecifiedProperty("updateDatetime");
        return updateDatetime;
    }

    @Field("update_datetime")
    protected void _setUpdateDatetime(java.util.Date value) {
        registerModifiedProperty("updateDatetime");
        updateDatetime = new org.dbflute.helper.HandyDate(value).getLocalDateTime();
    }

    public void setUpdateDatetime(java.time.LocalDateTime value) {
        registerModifiedProperty("updateDatetime");
        updateDatetime = value;
    }

    public String getUpdateUser() {
        checkSpecifiedProperty("updateUser");
        return updateUser;
    }

    @Field("update_user")
    public void setUpdateUser(String value) {
        registerModifiedProperty("updateUser");
        updateUser = value;
    }

    // ===================================================================================
    //                                                                            toString
    //                                                                            ========
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("latestPurchaseDate").append("=").append(latestPurchaseDate).append(",");
        sb.append("productCategory").append("=").append(productCategory).append(",");
        sb.append("productCategoryCode").append("=").append(productCategoryCode).append(",");
        sb.append("productDescription").append("=").append(productDescription).append(",");
        sb.append("productHandleCode").append("=").append(productHandleCode).append(",");
        sb.append("productName").append("=").append(productName).append(",");
        sb.append("productStatus").append("=").append(productStatus).append(",");
        sb.append("productStatusCode").append("=").append(productStatusCode).append(",");
        sb.append("registerDatetime").append("=").append(registerDatetime).append(",");
        sb.append("registerUser").append("=").append(registerUser).append(",");
        sb.append("regularPrice").append("=").append(regularPrice).append(",");
        sb.append("updateDatetime").append("=").append(updateDatetime).append(",");
        sb.append("updateUser").append("=").append(updateUser).append(",");
        sb.append("]");
        return sb.toString();
    }
}
