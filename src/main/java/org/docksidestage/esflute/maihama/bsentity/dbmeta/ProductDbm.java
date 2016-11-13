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
package org.docksidestage.esflute.maihama.bsentity.dbmeta;

import java.time.*;
import java.util.List;
import java.util.Map;

import org.docksidestage.esflute.maihama.exentity.Product;

import org.dbflute.Entity;
import org.dbflute.dbmeta.AbstractDBMeta;
import org.dbflute.dbmeta.info.ColumnInfo;
import org.dbflute.dbmeta.info.UniqueInfo;
import org.dbflute.dbmeta.name.TableSqlName;
import org.dbflute.dbmeta.property.PropertyGateway;
import org.dbflute.dbway.DBDef;
import org.dbflute.util.DfTypeUtil;

/**
 * @author ESFlute (using FreeGen)
 */
public class ProductDbm extends AbstractDBMeta {

    protected static final Class<?> suppressUnusedImportLocalDateTime = LocalDateTime.class;

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final ProductDbm _instance = new ProductDbm();

    private ProductDbm() {
    }

    public static ProductDbm getInstance() {
        return _instance;
    }

    // ===================================================================================
    //                                                                       Current DBDef
    //                                                                       =============
    @Override
    public String getProjectName() {
        return null;
    }

    @Override
    public String getProjectPrefix() {
        return null;
    }

    @Override
    public String getGenerationGapBasePrefix() {
        return null;
    }

    @Override
    public DBDef getCurrentDBDef() {
        return null;
    }

    // ===================================================================================
    //                                                                    Property Gateway
    //                                                                    ================
    // -----------------------------------------------------
    //                                       Column Property
    //                                       ---------------
    protected final Map<String, PropertyGateway> _epgMap = newHashMap();
    {
        setupEpg(_epgMap, et-> ((Product)et).getLatestPurchaseDate(),(et,vl)->((Product) et).setLatestPurchaseDate(DfTypeUtil.toLocalDateTime(vl)), "latestPurchaseDate");
        setupEpg(_epgMap, et-> ((Product)et).getProductCategory(),(et,vl)->((Product) et).setProductCategory(DfTypeUtil.toString(vl)), "productCategory");
        setupEpg(_epgMap, et-> ((Product)et).getProductCategoryCode(),(et,vl)->((Product) et).setProductCategoryCode(DfTypeUtil.toString(vl)), "productCategoryCode");
        setupEpg(_epgMap, et-> ((Product)et).getProductDescription(),(et,vl)->((Product) et).setProductDescription(DfTypeUtil.toString(vl)), "productDescription");
        setupEpg(_epgMap, et-> ((Product)et).getProductHandleCode(),(et,vl)->((Product) et).setProductHandleCode(DfTypeUtil.toString(vl)), "productHandleCode");
        setupEpg(_epgMap, et-> ((Product)et).getProductName(),(et,vl)->((Product) et).setProductName(DfTypeUtil.toString(vl)), "productName");
        setupEpg(_epgMap, et-> ((Product)et).getProductStatus(),(et,vl)->((Product) et).setProductStatus(DfTypeUtil.toString(vl)), "productStatus");
        setupEpg(_epgMap, et-> ((Product)et).getProductStatusCode(),(et,vl)->((Product) et).setProductStatusCode(DfTypeUtil.toString(vl)), "productStatusCode");
        setupEpg(_epgMap, et-> ((Product)et).getRegisterDatetime(),(et,vl)->((Product) et).setRegisterDatetime(DfTypeUtil.toLocalDateTime(vl)), "registerDatetime");
        setupEpg(_epgMap, et-> ((Product)et).getRegisterUser(),(et,vl)->((Product) et).setRegisterUser(DfTypeUtil.toString(vl)), "registerUser");
        setupEpg(_epgMap, et-> ((Product)et).getRegularPrice(),(et,vl)->((Product) et).setRegularPrice(DfTypeUtil.toInteger(vl)), "regularPrice");
        setupEpg(_epgMap, et-> ((Product)et).getUpdateDatetime(),(et,vl)->((Product) et).setUpdateDatetime(DfTypeUtil.toLocalDateTime(vl)), "updateDatetime");
        setupEpg(_epgMap, et-> ((Product)et).getUpdateUser(),(et,vl)->((Product) et).setUpdateUser(DfTypeUtil.toString(vl)), "updateUser");
    }

    @Override
    public PropertyGateway findPropertyGateway(final String prop) {
        return doFindEpg(_epgMap, prop);
    }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "product";
    protected final String _tableDispName = "product";
    protected final String _tablePropertyName = "Product";
    public String getTableDbName() { return _tableDbName; }
    @Override
    public String getTableDispName() { return _tableDispName; }
    @Override
    public String getTablePropertyName() { return _tablePropertyName; }
    @Override
    public TableSqlName getTableSqlName() { return null; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnLatestPurchaseDate = cci("latest_purchase_date", "latest_purchase_date", null, null, LocalDateTime.class, "latestPurchaseDate", null, false, false, false, "LocalDateTime", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnProductCategory = cci("product_category", "product_category", null, null, String.class, "productCategory", null, false, false, false, "String", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnProductCategoryCode = cci("product_category_code", "product_category_code", null, null, String.class, "productCategoryCode", null, false, false, false, "String", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnProductDescription = cci("product_description", "product_description", null, null, String.class, "productDescription", null, false, false, false, "String", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnProductHandleCode = cci("product_handle_code", "product_handle_code", null, null, String.class, "productHandleCode", null, false, false, false, "String", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnProductName = cci("product_name", "product_name", null, null, String.class, "productName", null, false, false, false, "String", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnProductStatus = cci("product_status", "product_status", null, null, String.class, "productStatus", null, false, false, false, "String", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnProductStatusCode = cci("product_status_code", "product_status_code", null, null, String.class, "productStatusCode", null, false, false, false, "String", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterDatetime = cci("register_datetime", "register_datetime", null, null, LocalDateTime.class, "registerDatetime", null, false, false, false, "LocalDateTime", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterUser = cci("register_user", "register_user", null, null, String.class, "registerUser", null, false, false, false, "String", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegularPrice = cci("regular_price", "regular_price", null, null, Integer.class, "regularPrice", null, false, false, false, "Integer", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateDatetime = cci("update_datetime", "update_datetime", null, null, LocalDateTime.class, "updateDatetime", null, false, false, false, "LocalDateTime", 0, 0, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateUser = cci("update_user", "update_user", null, null, String.class, "updateUser", null, false, false, false, "String", 0, 0, null, false, null, null, null, null, null, false);

    public ColumnInfo columnLatestPurchaseDate() { return _columnLatestPurchaseDate; }
    public ColumnInfo columnProductCategory() { return _columnProductCategory; }
    public ColumnInfo columnProductCategoryCode() { return _columnProductCategoryCode; }
    public ColumnInfo columnProductDescription() { return _columnProductDescription; }
    public ColumnInfo columnProductHandleCode() { return _columnProductHandleCode; }
    public ColumnInfo columnProductName() { return _columnProductName; }
    public ColumnInfo columnProductStatus() { return _columnProductStatus; }
    public ColumnInfo columnProductStatusCode() { return _columnProductStatusCode; }
    public ColumnInfo columnRegisterDatetime() { return _columnRegisterDatetime; }
    public ColumnInfo columnRegisterUser() { return _columnRegisterUser; }
    public ColumnInfo columnRegularPrice() { return _columnRegularPrice; }
    public ColumnInfo columnUpdateDatetime() { return _columnUpdateDatetime; }
    public ColumnInfo columnUpdateUser() { return _columnUpdateUser; }

    protected List<ColumnInfo> ccil() {
        List<ColumnInfo> ls = newArrayList();
        ls.add(columnLatestPurchaseDate());
        ls.add(columnProductCategory());
        ls.add(columnProductCategoryCode());
        ls.add(columnProductDescription());
        ls.add(columnProductHandleCode());
        ls.add(columnProductName());
        ls.add(columnProductStatus());
        ls.add(columnProductStatusCode());
        ls.add(columnRegisterDatetime());
        ls.add(columnRegisterUser());
        ls.add(columnRegularPrice());
        ls.add(columnUpdateDatetime());
        ls.add(columnUpdateUser());
        return ls;
    }

    // ===================================================================================
    //                                                                         Unique Info
    //                                                                         ===========
    @Override
    public boolean hasPrimaryKey() {
        return false;
    }

    @Override
    public boolean hasCompoundPrimaryKey() {
        return false;
    }

    @Override
    protected UniqueInfo cpui() {
        return null;
    }

    // ===================================================================================
    //                                                                           Type Name
    //                                                                           =========
    @Override
    public String getEntityTypeName() {
        return "org.docksidestage.esflute.maihama.exentity.Product";
    }

    @Override
    public String getConditionBeanTypeName() {
        return "org.docksidestage.esflute.maihama.cbean.ProductCB";
    }

    @Override
    public String getBehaviorTypeName() {
        return "org.docksidestage.esflute.maihama.exbhv.ProductBhv";
    }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    @Override
    public Class<? extends Entity> getEntityType() {
        return Product.class;
    }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    @Override
    public Entity newEntity() {
        return new Product();
    }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    @Override
    public void acceptPrimaryKeyMap(Entity entity, Map<String, ? extends Object> primaryKeyMap) {
    }

    @Override
    public void acceptAllColumnMap(Entity entity, Map<String, ? extends Object> allColumnMap) {
    }

    @Override
    public Map<String, Object> extractPrimaryKeyMap(Entity entity) {
        return null;
    }

    @Override
    public Map<String, Object> extractAllColumnMap(Entity entity) {
        return null;
    }
}

