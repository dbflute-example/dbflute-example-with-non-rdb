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
package org.docksidestage.remote.petstore.user;

import java.util.function.Consumer;

import org.dbflute.remoteapi.FlutyRemoteApiRule;
import org.docksidestage.remote.petstore.AbstractRemotePetstoreBhv;
import org.docksidestage.remote.petstore.user.createwitharray.RemoteUserCreatewitharrayParam;
import org.docksidestage.remote.petstore.user.createwithlist.RemoteUserCreatewithlistParam;
import org.docksidestage.remote.petstore.user.index.RemoteUserGetReturn;
import org.docksidestage.remote.petstore.user.index.RemoteUserParam;
import org.docksidestage.remote.petstore.user.index.RemoteUserPutParam;
import org.docksidestage.remote.petstore.user.login.RemoteUserLoginParam;
import org.lastaflute.web.servlet.request.RequestManager;

/**
 * The base class as generation gap for remote API of user.
 * @author FreeGen
 */
public abstract class BsRemotePetstoreUserBhv extends AbstractRemotePetstoreBhv {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    /**
     * @param requestManager The manager of request, LastaFlute component. (NotNull)
     */
    public BsRemotePetstoreUserBhv(RequestManager requestManager) {
        super(requestManager);
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    /**
     * Request remote call to /user. (auto-generated method)<br>
     * <pre>
     * url: /user
     * httpMethod: POST
     * </pre>
     * @param paramLambda The callback for RemoteUserParam. (NotNull)
     */
    public void request(Consumer<RemoteUserParam> paramLambda) {
        doRequest(paramLambda, rule -> {});
    }

    /**
     * Request remote call to /user. (auto-generated method)<br>
     * <pre>
     * url: /user
     * httpMethod: POST
     * </pre>
     * @param paramLambda The callback for RemoteUserParam. (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     */
    protected void doRequest(Consumer<RemoteUserParam> paramLambda, Consumer<FlutyRemoteApiRule> ruleLambda) {
        RemoteUserParam param = new RemoteUserParam();
        paramLambda.accept(param);
         doRequestPost(void.class, "/user", noMoreUrl(), param, rule -> {
            ruleOf(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /user.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOf(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /user/createWithArray. (auto-generated method)<br>
     * <pre>
     * url: /user/createWithArray
     * httpMethod: POST
     * </pre>
     * @param paramLambda The callback for java.util.List<RemoteUserCreatewitharrayParam>. (NotNull)
     */
    public void requestCreatewitharray(Consumer<java.util.List<RemoteUserCreatewitharrayParam>> paramLambda) {
        doRequestCreatewitharray(paramLambda, rule -> {});
    }

    /**
     * Request remote call to /user/createWithArray. (auto-generated method)<br>
     * <pre>
     * url: /user/createWithArray
     * httpMethod: POST
     * </pre>
     * @param paramLambda The callback for java.util.List<RemoteUserCreatewitharrayParam>. (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     */
    protected void doRequestCreatewitharray(Consumer<java.util.List<RemoteUserCreatewitharrayParam>> paramLambda, Consumer<FlutyRemoteApiRule> ruleLambda) {
        java.util.List<RemoteUserCreatewitharrayParam> param = new java.util.ArrayList<RemoteUserCreatewitharrayParam>();
        paramLambda.accept(param);
         doRequestPost(void.class, "/user/createWithArray", noMoreUrl(), param, rule -> {
            ruleOfCreatewitharray(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /user/createWithArray.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfCreatewitharray(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /user/createWithList. (auto-generated method)<br>
     * <pre>
     * url: /user/createWithList
     * httpMethod: POST
     * </pre>
     * @param paramLambda The callback for java.util.List<RemoteUserCreatewithlistParam>. (NotNull)
     */
    public void requestCreatewithlist(Consumer<java.util.List<RemoteUserCreatewithlistParam>> paramLambda) {
        doRequestCreatewithlist(paramLambda, rule -> {});
    }

    /**
     * Request remote call to /user/createWithList. (auto-generated method)<br>
     * <pre>
     * url: /user/createWithList
     * httpMethod: POST
     * </pre>
     * @param paramLambda The callback for java.util.List<RemoteUserCreatewithlistParam>. (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     */
    protected void doRequestCreatewithlist(Consumer<java.util.List<RemoteUserCreatewithlistParam>> paramLambda, Consumer<FlutyRemoteApiRule> ruleLambda) {
        java.util.List<RemoteUserCreatewithlistParam> param = new java.util.ArrayList<RemoteUserCreatewithlistParam>();
        paramLambda.accept(param);
         doRequestPost(void.class, "/user/createWithList", noMoreUrl(), param, rule -> {
            ruleOfCreatewithlist(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /user/createWithList.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfCreatewithlist(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /user/login. (auto-generated method)<br>
     * <pre>
     * url: /user/login
     * httpMethod: GET
     * </pre>
     * @param paramLambda The callback for RemoteUserLoginParam. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    public String requestLogin(Consumer<RemoteUserLoginParam> paramLambda) {
        return doRequestLogin(paramLambda, rule -> {});
    }

    /**
     * Request remote call to /user/login. (auto-generated method)<br>
     * <pre>
     * url: /user/login
     * httpMethod: GET
     * </pre>
     * @param paramLambda The callback for RemoteUserLoginParam. (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    protected String doRequestLogin(Consumer<RemoteUserLoginParam> paramLambda, Consumer<FlutyRemoteApiRule> ruleLambda) {
        RemoteUserLoginParam param = new RemoteUserLoginParam();
        paramLambda.accept(param);
         return doRequestGet(String.class, "/user/login", noMoreUrl(), query(param), rule -> {
            ruleOfLogin(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /user/login.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfLogin(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /user/logout. (auto-generated method)<br>
     * <pre>
     * url: /user/logout
     * httpMethod: GET
     * </pre>
     */
    public void requestLogout() {
        doRequestLogout(rule -> {});
    }

    /**
     * Request remote call to /user/logout. (auto-generated method)<br>
     * <pre>
     * url: /user/logout
     * httpMethod: GET
     * </pre>
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     */
    protected void doRequestLogout(Consumer<FlutyRemoteApiRule> ruleLambda) {
         doRequestGet(void.class, "/user/logout", noMoreUrl(), noQuery(), rule -> {
            ruleOfLogout(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /user/logout.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfLogout(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /user/{username}. (auto-generated method)<br>
     * <pre>
     * url: /user/{username}
     * httpMethod: GET
     * </pre>
     * @param username The value of path variable for username. (The name that needs to be fetched. Use user1 for testing. ) (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    public RemoteUserGetReturn requestGet(String username) {
        return doRequestGet(username, rule -> {});
    }

    /**
     * Request remote call to /user/{username}. (auto-generated method)<br>
     * <pre>
     * url: /user/{username}
     * httpMethod: GET
     * </pre>
     * @param username The value of path variable for username. (The name that needs to be fetched. Use user1 for testing. ) (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    protected RemoteUserGetReturn doRequestGet(String username, Consumer<FlutyRemoteApiRule> ruleLambda) {
         return doRequestGet(RemoteUserGetReturn.class, "/user/{username}", moreUrl(username), noQuery(), rule -> {
            ruleOfGetUsername(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /user/{username}.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfGetUsername(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /user/{username}. (auto-generated method)<br>
     * <pre>
     * url: /user/{username}
     * httpMethod: PUT
     * </pre>
     * @param username The value of path variable for username. (name that need to be updated) (NotNull)
     * @param paramLambda The callback for RemoteUserPutParam. (NotNull)
     */
    public void requestPut(String username, Consumer<RemoteUserPutParam> paramLambda) {
        doRequestPut(username, paramLambda, rule -> {});
    }

    /**
     * Request remote call to /user/{username}. (auto-generated method)<br>
     * <pre>
     * url: /user/{username}
     * httpMethod: PUT
     * </pre>
     * @param username The value of path variable for username. (name that need to be updated) (NotNull)
     * @param paramLambda The callback for RemoteUserPutParam. (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     */
    protected void doRequestPut(String username, Consumer<RemoteUserPutParam> paramLambda, Consumer<FlutyRemoteApiRule> ruleLambda) {
        RemoteUserPutParam param = new RemoteUserPutParam();
        paramLambda.accept(param);
         doRequestPut(void.class, "/user/{username}", moreUrl(username), param, rule -> {
            ruleOfPutUsername(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /user/{username}.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfPutUsername(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /user/{username}. (auto-generated method)<br>
     * <pre>
     * url: /user/{username}
     * httpMethod: DELETE
     * </pre>
     * @param username The value of path variable for username. (The name that needs to be deleted) (NotNull)
     */
    public void requestDelete(String username) {
        doRequestDelete(username, rule -> {});
    }

    /**
     * Request remote call to /user/{username}. (auto-generated method)<br>
     * <pre>
     * url: /user/{username}
     * httpMethod: DELETE
     * </pre>
     * @param username The value of path variable for username. (The name that needs to be deleted) (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     */
    protected void doRequestDelete(String username, Consumer<FlutyRemoteApiRule> ruleLambda) {
         doRequestDelete(void.class, "/user/{username}", moreUrl(username), noQuery(), rule -> {
            ruleOfDeleteUsername(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /user/{username}.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfDeleteUsername(FlutyRemoteApiRule rule) {
    }
}
