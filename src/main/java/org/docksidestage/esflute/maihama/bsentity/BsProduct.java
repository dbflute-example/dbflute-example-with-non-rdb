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
package org.docksidestage.esflute.maihama.bsentity;

import java.time.*;
import java.util.HashMap;
import java.util.Map;

import org.docksidestage.esflute.maihama.allcommon.EsAbstractEntity;
import org.docksidestage.esflute.maihama.bsentity.dbmeta.ProductDbm;

/**
 * ${table.comment}
 * @author ESFlute (using FreeGen)
 */
public class BsProduct extends EsAbstractEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final long serialVersionUID = 1L;
    protected static final Class<?> suppressUnusedImportLocalDateTime = LocalDateTime.class;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** latest_purchase_date */
    protected LocalDateTime latestPurchaseDate;

    /** product_category */
    protected String productCategory;

    /** product_category_code */
    protected String productCategoryCode;

    /** product_description */
    protected String productDescription;

    /** product_handle_code */
    protected String productHandleCode;

    /** product_name */
    protected String productName;

    /** product_status */
    protected String productStatus;

    /** product_status_code */
    protected String productStatusCode;

    /** register_datetime */
    protected LocalDateTime registerDatetime;

    /** register_user */
    protected String registerUser;

    /** regular_price */
    protected Integer regularPrice;

    /** update_datetime */
    protected LocalDateTime updateDatetime;

    /** update_user */
    protected String updateUser;

    // [Referrers] *comment only

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    @Override
    public ProductDbm asDBMeta() {
        return ProductDbm.getInstance();
    }

    @Override
    public String asTableDbName() {
        return "product";
    }

    // ===================================================================================
    //                                                                              Source
    //                                                                              ======
    @Override
    public Map<String, Object> toSource() {
        Map<String, Object> sourceMap = new HashMap<>();
        if (latestPurchaseDate != null) {
            sourceMap.put("latest_purchase_date", latestPurchaseDate);
        }
        if (productCategory != null) {
            sourceMap.put("product_category", productCategory);
        }
        if (productCategoryCode != null) {
            sourceMap.put("product_category_code", productCategoryCode);
        }
        if (productDescription != null) {
            sourceMap.put("product_description", productDescription);
        }
        if (productHandleCode != null) {
            sourceMap.put("product_handle_code", productHandleCode);
        }
        if (productName != null) {
            sourceMap.put("product_name", productName);
        }
        if (productStatus != null) {
            sourceMap.put("product_status", productStatus);
        }
        if (productStatusCode != null) {
            sourceMap.put("product_status_code", productStatusCode);
        }
        if (registerDatetime != null) {
            sourceMap.put("register_datetime", registerDatetime);
        }
        if (registerUser != null) {
            sourceMap.put("register_user", registerUser);
        }
        if (regularPrice != null) {
            sourceMap.put("regular_price", regularPrice);
        }
        if (updateDatetime != null) {
            sourceMap.put("update_datetime", updateDatetime);
        }
        if (updateUser != null) {
            sourceMap.put("update_user", updateUser);
        }
        return sourceMap;
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(latestPurchaseDate);
        sb.append(dm).append(productCategory);
        sb.append(dm).append(productCategoryCode);
        sb.append(dm).append(productDescription);
        sb.append(dm).append(productHandleCode);
        sb.append(dm).append(productName);
        sb.append(dm).append(productStatus);
        sb.append(dm).append(productStatusCode);
        sb.append(dm).append(registerDatetime);
        sb.append(dm).append(registerUser);
        sb.append(dm).append(regularPrice);
        sb.append(dm).append(updateDatetime);
        sb.append(dm).append(updateUser);
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public LocalDateTime getLatestPurchaseDate() {
        checkSpecifiedProperty("latestPurchaseDate");
        return latestPurchaseDate;
    }

    public void setLatestPurchaseDate(LocalDateTime value) {
        registerModifiedProperty("latestPurchaseDate");
        this.latestPurchaseDate = value;
    }

    public String getProductCategory() {
        checkSpecifiedProperty("productCategory");
        return convertEmptyToNull(productCategory);
    }

    public void setProductCategory(String value) {
        registerModifiedProperty("productCategory");
        this.productCategory = value;
    }

    public String getProductCategoryCode() {
        checkSpecifiedProperty("productCategoryCode");
        return convertEmptyToNull(productCategoryCode);
    }

    public void setProductCategoryCode(String value) {
        registerModifiedProperty("productCategoryCode");
        this.productCategoryCode = value;
    }

    public String getProductDescription() {
        checkSpecifiedProperty("productDescription");
        return convertEmptyToNull(productDescription);
    }

    public void setProductDescription(String value) {
        registerModifiedProperty("productDescription");
        this.productDescription = value;
    }

    public String getProductHandleCode() {
        checkSpecifiedProperty("productHandleCode");
        return convertEmptyToNull(productHandleCode);
    }

    public void setProductHandleCode(String value) {
        registerModifiedProperty("productHandleCode");
        this.productHandleCode = value;
    }

    public String getProductName() {
        checkSpecifiedProperty("productName");
        return convertEmptyToNull(productName);
    }

    public void setProductName(String value) {
        registerModifiedProperty("productName");
        this.productName = value;
    }

    public String getProductStatus() {
        checkSpecifiedProperty("productStatus");
        return convertEmptyToNull(productStatus);
    }

    public void setProductStatus(String value) {
        registerModifiedProperty("productStatus");
        this.productStatus = value;
    }

    public String getProductStatusCode() {
        checkSpecifiedProperty("productStatusCode");
        return convertEmptyToNull(productStatusCode);
    }

    public void setProductStatusCode(String value) {
        registerModifiedProperty("productStatusCode");
        this.productStatusCode = value;
    }

    public LocalDateTime getRegisterDatetime() {
        checkSpecifiedProperty("registerDatetime");
        return registerDatetime;
    }

    public void setRegisterDatetime(LocalDateTime value) {
        registerModifiedProperty("registerDatetime");
        this.registerDatetime = value;
    }

    public String getRegisterUser() {
        checkSpecifiedProperty("registerUser");
        return convertEmptyToNull(registerUser);
    }

    public void setRegisterUser(String value) {
        registerModifiedProperty("registerUser");
        this.registerUser = value;
    }

    public Integer getRegularPrice() {
        checkSpecifiedProperty("regularPrice");
        return regularPrice;
    }

    public void setRegularPrice(Integer value) {
        registerModifiedProperty("regularPrice");
        this.regularPrice = value;
    }

    public LocalDateTime getUpdateDatetime() {
        checkSpecifiedProperty("updateDatetime");
        return updateDatetime;
    }

    public void setUpdateDatetime(LocalDateTime value) {
        registerModifiedProperty("updateDatetime");
        this.updateDatetime = value;
    }

    public String getUpdateUser() {
        checkSpecifiedProperty("updateUser");
        return convertEmptyToNull(updateUser);
    }

    public void setUpdateUser(String value) {
        registerModifiedProperty("updateUser");
        this.updateUser = value;
    }
}
