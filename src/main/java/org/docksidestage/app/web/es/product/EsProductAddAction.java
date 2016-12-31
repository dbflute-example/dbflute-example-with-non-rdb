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
package org.docksidestage.app.web.es.product;

import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.docksidestage.app.web.base.NonrdbBaseAction;
import org.docksidestage.dbflute.allcommon.CDef.ProductStatus;
import org.docksidestage.dbflute.exbhv.ProductCategoryBhv;
import org.docksidestage.dbflute.exbhv.ProductStatusBhv;
import org.docksidestage.esflute.maihama.exbhv.ProductBhv;
import org.docksidestage.esflute.maihama.exentity.Product;
import org.lastaflute.web.Execute;
import org.lastaflute.web.login.AllowAnyoneAccess;
import org.lastaflute.web.response.HtmlResponse;

/**
 * @author shinsuke
 */
@AllowAnyoneAccess
public class EsProductAddAction extends NonrdbBaseAction {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // -----------------------------------------------------
    //                                          DI Component
    //                                          ------------
    @Resource
    private ProductBhv productBhv;

    @Resource
    private ProductCategoryBhv productCategoryBhv;

    @Resource
    private ProductStatusBhv productStatusBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @Execute
    public HtmlResponse index() {
        saveToken();
        return asHtml(path_EsProduct_EsProductAddJsp).useForm(EsProductAddForm.class);
    }

    @Execute
    public HtmlResponse add(EsProductAddForm form) {
        validate(form, messages -> {}, () -> {
            return asHtml(path_EsProduct_EsProductAddJsp);
        });
        verifyToken(() -> asHtml(path_EsProduct_EsProductListJsp));
        addProduct(form);
        return redirect(EsProductListAction.class);
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private void addProduct(EsProductAddForm form) {
        productBhv.insert(mappingToEntity(form), op -> {
            op.setRefreshPolicy("true");
        });
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private Product mappingToEntity(EsProductAddForm form) {
        LocalDateTime now = LocalDateTime.now();
        String username = getUserBean().map(user -> {
            return user.getMemberName();
        }).orElse("guest");
        Product product = new Product();
        product.setProductDescription(form.productDescription);
        product.setProductCategoryCode(form.productCategoryCode);
        if (form.productCategoryCode != null) {
            product.setProductCategory(productCategoryBhv.selectEntity(cb -> {
                cb.query().setProductCategoryCode_Equal(form.productCategoryCode);
            }).map(entity -> entity.getProductCategoryName()).orElse("Unknown"));
        }
        product.setProductHandleCode(form.productHandleCode);
        product.setProductName(form.productName);
        product.setRegisterDatetime(now);
        product.setRegisterUser(username);
        product.setRegularPrice(form.regularPrice);
        product.setProductStatusCode(form.productStatusCode);
        if (form.productStatusCode != null) {
            product.setProductStatus(productStatusBhv.selectEntity(cb -> {
                cb.query().setProductStatusCode_Equal_AsProductStatus(ProductStatus.codeOf(form.productStatusCode));
            }).map(entity -> entity.getProductStatusName()).orElse("Unknown"));
        }
        product.setUpdateDatetime(now);
        product.setUpdateUser(username);
        return product;
    }
}
