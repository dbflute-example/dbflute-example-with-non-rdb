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
package org.docksidestage.remote.petstore.store;

import java.util.function.Consumer;

import org.dbflute.remoteapi.FlutyRemoteApiRule;
import org.docksidestage.remote.petstore.AbstractRemotePetstoreBhv;
import org.docksidestage.remote.petstore.store.order.RemoteStoreOrderGetReturn;
import org.docksidestage.remote.petstore.store.order.RemoteStoreOrderParam;
import org.docksidestage.remote.petstore.store.order.RemoteStoreOrderReturn;
import org.lastaflute.web.servlet.request.RequestManager;

/**
 * The base class as generation gap for remote API of store.
 * @author FreeGen
 */
public abstract class BsRemotePetstoreStoreBhv extends AbstractRemotePetstoreBhv {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    /**
     * @param requestManager The manager of request, LastaFlute component. (NotNull)
     */
    public BsRemotePetstoreStoreBhv(RequestManager requestManager) {
        super(requestManager);
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    /**
     * Request remote call to /store/inventory. (auto-generated method)<br>
     * <pre>
     * url: /store/inventory
     * httpMethod: GET
     * </pre>
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    public Object requestInventory() {
        return doRequestInventory(rule -> {});
    }

    /**
     * Request remote call to /store/inventory. (auto-generated method)<br>
     * <pre>
     * url: /store/inventory
     * httpMethod: GET
     * </pre>
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    protected Object doRequestInventory(Consumer<FlutyRemoteApiRule> ruleLambda) {
         return doRequestGet(Object.class, "/store/inventory", noMoreUrl(), noQuery(), rule -> {
            ruleOfInventory(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /store/inventory.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfInventory(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /store/order. (auto-generated method)<br>
     * <pre>
     * url: /store/order
     * httpMethod: POST
     * </pre>
     * @param paramLambda The callback for RemoteStoreOrderParam. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    public RemoteStoreOrderReturn requestOrder(Consumer<RemoteStoreOrderParam> paramLambda) {
        return doRequestOrder(paramLambda, rule -> {});
    }

    /**
     * Request remote call to /store/order. (auto-generated method)<br>
     * <pre>
     * url: /store/order
     * httpMethod: POST
     * </pre>
     * @param paramLambda The callback for RemoteStoreOrderParam. (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    protected RemoteStoreOrderReturn doRequestOrder(Consumer<RemoteStoreOrderParam> paramLambda, Consumer<FlutyRemoteApiRule> ruleLambda) {
        RemoteStoreOrderParam param = new RemoteStoreOrderParam();
        paramLambda.accept(param);
         return doRequestPost(RemoteStoreOrderReturn.class, "/store/order", noMoreUrl(), param, rule -> {
            ruleOfOrder(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /store/order.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfOrder(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /store/order/{orderId}. (auto-generated method)<br>
     * <pre>
     * url: /store/order/{orderId}
     * httpMethod: GET
     * </pre>
     * @param orderId The value of path variable for orderId. (ID of pet that needs to be fetched) (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    public RemoteStoreOrderGetReturn requestOrderGet(Long orderId) {
        return doRequestOrderGet(orderId, rule -> {});
    }

    /**
     * Request remote call to /store/order/{orderId}. (auto-generated method)<br>
     * <pre>
     * url: /store/order/{orderId}
     * httpMethod: GET
     * </pre>
     * @param orderId The value of path variable for orderId. (ID of pet that needs to be fetched) (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    protected RemoteStoreOrderGetReturn doRequestOrderGet(Long orderId, Consumer<FlutyRemoteApiRule> ruleLambda) {
         return doRequestGet(RemoteStoreOrderGetReturn.class, "/store/order/{orderId}", moreUrl(orderId), noQuery(), rule -> {
            ruleOfOrderGetOrderId(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /store/order/{orderId}.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfOrderGetOrderId(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /store/order/{orderId}. (auto-generated method)<br>
     * <pre>
     * url: /store/order/{orderId}
     * httpMethod: DELETE
     * </pre>
     * @param orderId The value of path variable for orderId. (ID of the order that needs to be deleted) (NotNull)
     */
    public void requestOrderDelete(Long orderId) {
        doRequestOrderDelete(orderId, rule -> {});
    }

    /**
     * Request remote call to /store/order/{orderId}. (auto-generated method)<br>
     * <pre>
     * url: /store/order/{orderId}
     * httpMethod: DELETE
     * </pre>
     * @param orderId The value of path variable for orderId. (ID of the order that needs to be deleted) (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     */
    protected void doRequestOrderDelete(Long orderId, Consumer<FlutyRemoteApiRule> ruleLambda) {
         doRequestDelete(void.class, "/store/order/{orderId}", moreUrl(orderId), noQuery(), rule -> {
            ruleOfOrderDeleteOrderId(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /store/order/{orderId}.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfOrderDeleteOrderId(FlutyRemoteApiRule rule) {
    }
}
