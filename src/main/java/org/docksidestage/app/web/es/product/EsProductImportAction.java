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
package org.docksidestage.app.web.es.product;

import javax.annotation.Resource;

import org.docksidestage.app.web.base.NonrdbBaseAction;
import org.docksidestage.app.web.base.view.DisplayAssist;
import org.docksidestage.esflute.maihama.exbhv.ProductBhv;
import org.docksidestage.esflute.maihama.exentity.Product;
import org.lastaflute.di.core.SingletonLaContainer;
import org.lastaflute.web.Execute;
import org.lastaflute.web.login.AllowAnyoneAccess;
import org.lastaflute.web.response.HtmlResponse;

/**
 * @author jflute
 */
@AllowAnyoneAccess
public class EsProductImportAction extends NonrdbBaseAction {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private ProductBhv productBhv;
    @Resource
    private DisplayAssist displayAssist;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @Execute
    public HtmlResponse index() {
        // copy data from db to elasticsearch
        SingletonLaContainer.getComponent(org.docksidestage.dbflute.exbhv.ProductBhv.class).selectCursor(cb -> {
            cb.configure(config -> {
                config.fetchSize(10);
                cb.setupSelect_ProductStatus();
                cb.setupSelect_ProductCategory();
                cb.specify().derivedPurchase().max(purchaseCB -> {
                    purchaseCB.specify().columnPurchaseDatetime();
                }, org.docksidestage.dbflute.exentity.Product.ALIAS_latestPurchaseDate);
            });
        }, entity -> {
            Product product = new Product();
            product.asDocMeta().id(entity.getProductId().toString());
            product.setLatestPurchaseDate(displayAssist.toDateTime(entity.getLatestPurchaseDate()).orElse(null));
            // needs DB change
            //product.setProductDescription(entity.getProductDescription());
            product.setProductCategoryCode(entity.getProductCategoryCode());
            product.setProductCategory(entity.getProductCategory().get().getProductCategoryName());
            product.setProductHandleCode(entity.getProductHandleCode());
            product.setProductName(entity.getProductName());
            product.setRegisterDatetime(entity.getRegisterDatetime());
            product.setRegisterUser(entity.getRegisterUser());
            product.setRegularPrice(entity.getRegularPrice());
            product.setProductStatusCode(entity.getProductStatusCode());
            product.setProductStatus(entity.getProductStatus().get().getProductStatusName());
            product.setUpdateDatetime(entity.getUpdateDatetime());
            product.setUpdateUser(entity.getUpdateUser());
            productBhv.insert(product, op -> {
                op.setRefreshPolicy("true");
            });
        });
        return redirect(EsProductListAction.class);
    }

}
