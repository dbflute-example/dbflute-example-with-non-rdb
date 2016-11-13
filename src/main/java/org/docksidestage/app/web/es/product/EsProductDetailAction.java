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

import javax.annotation.Resource;

import org.docksidestage.app.web.base.NonrdbBaseAction;
import org.docksidestage.esflute.maihama.exbhv.ProductBhv;
import org.docksidestage.esflute.maihama.exentity.Product;
import org.lastaflute.web.Execute;
import org.lastaflute.web.login.AllowAnyoneAccess;
import org.lastaflute.web.response.HtmlResponse;

/**
 * @author jflute
 * @author shinsuke
 */
@AllowAnyoneAccess
public class EsProductDetailAction extends NonrdbBaseAction {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // -----------------------------------------------------
    //                                          DI Component
    //                                          ------------
    @Resource
    private ProductBhv productBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @Execute
    public HtmlResponse index(String productId) {
        validate(productId, messages -> {}, () -> {
            return asHtml(path_EsProduct_EsProductListJsp);
        });
        Product product = selectProduct(productId);
        return asHtml(path_EsProduct_EsProductDetailJsp).renderWith(data -> {
            data.register("product", mappingToBean(product));
        }).useForm(EsProductDeleteForm.class, op -> {
            op.setup(form -> {
                form.productId = productId;
            });
        });
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private Product selectProduct(String productId) {
        return productBhv.selectEntity(cb -> {
            cb.query().setId_Equal(productId);
        }).get();
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private EsProductDetailBean mappingToBean(Product product) {
        EsProductDetailBean bean = new EsProductDetailBean();
        bean.productId = product.asDocMeta().id();
        bean.productName = product.getProductName();
        bean.regularPrice = product.getRegularPrice();
        bean.productHandleCode = product.getProductHandleCode();
        bean.categoryName = product.getProductCategory();
        return bean;
    }
}
