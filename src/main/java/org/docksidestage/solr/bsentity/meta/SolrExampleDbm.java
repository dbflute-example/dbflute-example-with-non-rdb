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
package org.docksidestage.solr.bsentity.meta;

import org.dbflute.solr.entity.dbmeta.SolrDBMeta;

/**
 * Meta class of Solr schema "Example."
 * @author FreeGen
 */
public enum SolrExampleDbm implements SolrDBMeta {

    // ===================================================================================
    //                                                                       SolrFieldName
    //                                                                       =============
    /** latest_purchase_date */
    LatestPurchaseDate("latestPurchaseDate", "latest_purchase_date"),
    /** product_category */
    ProductCategory("productCategory", "product_category"),
    /** product_category_code */
    ProductCategoryCode("productCategoryCode", "product_category_code"),
    /** product_description */
    ProductDescription("productDescription", "product_description"),
    /** product_handle_code */
    ProductHandleCode("productHandleCode", "product_handle_code"),
    /** product_name */
    ProductName("productName", "product_name"),
    /** product_status */
    ProductStatus("productStatus", "product_status"),
    /** product_status_code */
    ProductStatusCode("productStatusCode", "product_status_code"),
    /** register_datetime */
    RegisterDatetime("registerDatetime", "register_datetime"),
    /** register_user */
    RegisterUser("registerUser", "register_user"),
    /** regular_price */
    RegularPrice("regularPrice", "regular_price"),
    /** update_datetime */
    UpdateDatetime("updateDatetime", "update_datetime"),
    /** update_user */
    UpdateUser("updateUser", "update_user"),
;

    private final String _propertyName;
    private final String _fieldName;
    private SolrExampleDbm(String propertyName, String fieldName) {
        _propertyName = propertyName;
        _fieldName = fieldName;
    }

    @Override
    public String propertyName() {
        return _propertyName;
    }

    @Override
    public String fieldName() {
        return _fieldName;
    }
}
