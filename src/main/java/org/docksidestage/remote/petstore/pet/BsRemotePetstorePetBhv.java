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
package org.docksidestage.remote.petstore.pet;

import java.util.function.Consumer;

import org.dbflute.remoteapi.FlutyRemoteApiRule;
import org.docksidestage.remote.petstore.AbstractRemotePetstoreBhv;
import org.docksidestage.remote.petstore.pet.findbystatus.RemotePetFindbystatusParam;
import org.docksidestage.remote.petstore.pet.findbystatus.RemotePetFindbystatusReturn;
import org.docksidestage.remote.petstore.pet.findbytags.RemotePetFindbytagsParam;
import org.docksidestage.remote.petstore.pet.findbytags.RemotePetFindbytagsReturn;
import org.docksidestage.remote.petstore.pet.index.RemotePetGetReturn;
import org.docksidestage.remote.petstore.pet.index.RemotePetPetidPostParam;
import org.docksidestage.remote.petstore.pet.index.RemotePetPostParam;
import org.docksidestage.remote.petstore.pet.index.RemotePetPutParam;
import org.docksidestage.remote.petstore.pet.uploadimage.RemotePetUploadimageParam;
import org.docksidestage.remote.petstore.pet.uploadimage.RemotePetUploadimageReturn;
import org.lastaflute.web.servlet.request.RequestManager;

/**
 * The base class as generation gap for remote API of pet.
 * @author FreeGen
 */
public abstract class BsRemotePetstorePetBhv extends AbstractRemotePetstoreBhv {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    /**
     * @param requestManager The manager of request, LastaFlute component. (NotNull)
     */
    public BsRemotePetstorePetBhv(RequestManager requestManager) {
        super(requestManager);
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    /**
     * Request remote call to /pet. (auto-generated method)<br>
     * <pre>
     * url: /pet
     * httpMethod: POST
     * </pre>
     * @param paramLambda The callback for RemotePetPostParam. (NotNull)
     */
    public void requestPost(Consumer<RemotePetPostParam> paramLambda) {
        requestPost(paramLambda, rule -> {});
    }

    /**
     * Request remote call to /pet. (auto-generated method)<br>
     * <pre>
     * url: /pet
     * httpMethod: POST
     * </pre>
     * @param paramLambda The callback for RemotePetPostParam. (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     */
    protected void requestPost(Consumer<RemotePetPostParam> paramLambda, Consumer<FlutyRemoteApiRule> ruleLambda) {
        RemotePetPostParam param = new RemotePetPostParam();
        paramLambda.accept(param);
        doRequestPost(void.class, "/pet", noMoreUrl(), param, rule -> {
            ruleOfPost(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /pet.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfPost(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /pet. (auto-generated method)<br>
     * <pre>
     * url: /pet
     * httpMethod: PUT
     * </pre>
     * @param paramLambda The callback for RemotePetPutParam. (NotNull)
     */
    public void requestPut(Consumer<RemotePetPutParam> paramLambda) {
        requestPut(paramLambda, rule -> {});
    }

    /**
     * Request remote call to /pet. (auto-generated method)<br>
     * <pre>
     * url: /pet
     * httpMethod: PUT
     * </pre>
     * @param paramLambda The callback for RemotePetPutParam. (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     */
    protected void requestPut(Consumer<RemotePetPutParam> paramLambda, Consumer<FlutyRemoteApiRule> ruleLambda) {
        RemotePetPutParam param = new RemotePetPutParam();
        paramLambda.accept(param);
        doRequestPut(void.class, "/pet", noMoreUrl(), param, rule -> {
            ruleOfPut(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /pet.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfPut(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /pet/findByStatus. (auto-generated method)<br>
     * <pre>
     * url: /pet/findByStatus
     * httpMethod: GET
     * </pre>
     * @param paramLambda The callback for RemotePetFindbystatusParam. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    public java.util.List<RemotePetFindbystatusReturn> requestFindbystatus(Consumer<RemotePetFindbystatusParam> paramLambda) {
        return requestFindbystatus(paramLambda, rule -> {});
    }

    /**
     * Request remote call to /pet/findByStatus. (auto-generated method)<br>
     * <pre>
     * url: /pet/findByStatus
     * httpMethod: GET
     * </pre>
     * @param paramLambda The callback for RemotePetFindbystatusParam. (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    protected java.util.List<RemotePetFindbystatusReturn> requestFindbystatus(Consumer<RemotePetFindbystatusParam> paramLambda, Consumer<FlutyRemoteApiRule> ruleLambda) {
        RemotePetFindbystatusParam param = new RemotePetFindbystatusParam();
        paramLambda.accept(param);
        return doRequestGet(new org.lastaflute.di.helper.misc.ParameterizedRef<java.util.List<RemotePetFindbystatusReturn>>() {
        }.getType(), "/pet/findByStatus", noMoreUrl(), query(param), rule -> {
            ruleOfFindbystatus(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /pet/findByStatus.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfFindbystatus(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /pet/findByTags. (auto-generated method)<br>
     * <pre>
     * url: /pet/findByTags
     * httpMethod: GET
     * </pre>
     * @param paramLambda The callback for RemotePetFindbytagsParam. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    public java.util.List<RemotePetFindbytagsReturn> requestFindbytags(Consumer<RemotePetFindbytagsParam> paramLambda) {
        return requestFindbytags(paramLambda, rule -> {});
    }

    /**
     * Request remote call to /pet/findByTags. (auto-generated method)<br>
     * <pre>
     * url: /pet/findByTags
     * httpMethod: GET
     * </pre>
     * @param paramLambda The callback for RemotePetFindbytagsParam. (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    protected java.util.List<RemotePetFindbytagsReturn> requestFindbytags(Consumer<RemotePetFindbytagsParam> paramLambda, Consumer<FlutyRemoteApiRule> ruleLambda) {
        RemotePetFindbytagsParam param = new RemotePetFindbytagsParam();
        paramLambda.accept(param);
        return doRequestGet(new org.lastaflute.di.helper.misc.ParameterizedRef<java.util.List<RemotePetFindbytagsReturn>>() {
        }.getType(), "/pet/findByTags", noMoreUrl(), query(param), rule -> {
            ruleOfFindbytags(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /pet/findByTags.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfFindbytags(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /pet/{petId}. (auto-generated method)<br>
     * <pre>
     * url: /pet/{petId}
     * httpMethod: GET
     * </pre>
     * @param petId The value of path variable for petId. (ID of pet to return) (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    public RemotePetGetReturn requestGet(Long petId) {
        return requestGet(petId, rule -> {});
    }

    /**
     * Request remote call to /pet/{petId}. (auto-generated method)<br>
     * <pre>
     * url: /pet/{petId}
     * httpMethod: GET
     * </pre>
     * @param petId The value of path variable for petId. (ID of pet to return) (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    protected RemotePetGetReturn requestGet(Long petId, Consumer<FlutyRemoteApiRule> ruleLambda) {
        return doRequestGet(RemotePetGetReturn.class, "/pet/{petId}", moreUrl(petId), noQuery(), rule -> {
            ruleOfGetPetId(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /pet/{petId}.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfGetPetId(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /pet/{petId}. (auto-generated method)<br>
     * <pre>
     * url: /pet/{petId}
     * httpMethod: POST
     * </pre>
     * @param petId The value of path variable for petId. (ID of pet that needs to be updated) (NotNull)
     * @param paramLambda The callback for RemotePetPetidPostParam. (NotNull)
     */
    public void requestPost(Long petId, Consumer<RemotePetPetidPostParam> paramLambda) {
        requestPost(petId, paramLambda, rule -> {});
    }

    /**
     * Request remote call to /pet/{petId}. (auto-generated method)<br>
     * <pre>
     * url: /pet/{petId}
     * httpMethod: POST
     * </pre>
     * @param petId The value of path variable for petId. (ID of pet that needs to be updated) (NotNull)
     * @param paramLambda The callback for RemotePetPetidPostParam. (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     */
    protected void requestPost(Long petId, Consumer<RemotePetPetidPostParam> paramLambda, Consumer<FlutyRemoteApiRule> ruleLambda) {
        RemotePetPetidPostParam param = new RemotePetPetidPostParam();
        paramLambda.accept(param);
        doRequestPost(void.class, "/pet/{petId}", moreUrl(petId), param, rule -> {
            rule.sendBodyBy(
                    new org.lastaflute.remoteapi.sender.body.LaFormSender(new org.dbflute.remoteapi.mapping.FlVacantMappingPolicy()));
            ruleOfPostPetId(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /pet/{petId}.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfPostPetId(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /pet/{petId}. (auto-generated method)<br>
     * <pre>
     * url: /pet/{petId}
     * httpMethod: DELETE
     * </pre>
     * @param petId The value of path variable for petId. (Pet id to delete) (NotNull)
     */
    public void requestDelete(Long petId) {
        requestDelete(petId, rule -> {});
    }

    /**
     * Request remote call to /pet/{petId}. (auto-generated method)<br>
     * <pre>
     * url: /pet/{petId}
     * httpMethod: DELETE
     * </pre>
     * @param petId The value of path variable for petId. (Pet id to delete) (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     */
    protected void requestDelete(Long petId, Consumer<FlutyRemoteApiRule> ruleLambda) {
        doRequestDelete(void.class, "/pet/{petId}", moreUrl(petId), noQuery(), rule -> {
            ruleOfDeletePetId(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /pet/{petId}.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfDeletePetId(FlutyRemoteApiRule rule) {
    }

    /**
     * Request remote call to /pet/{petId}/uploadImage. (auto-generated method)<br>
     * <pre>
     * url: /pet/{petId}/uploadImage
     * httpMethod: POST
     * </pre>
     * @param petId The value of path variable for petId. (ID of pet to update) (NotNull)
     * @param paramLambda The callback for RemotePetUploadimageParam. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    public RemotePetUploadimageReturn requestUploadimage(Long petId, Consumer<RemotePetUploadimageParam> paramLambda) {
        return requestUploadimage(petId, paramLambda, rule -> {});
    }

    /**
     * Request remote call to /pet/{petId}/uploadImage. (auto-generated method)<br>
     * <pre>
     * url: /pet/{petId}/uploadImage
     * httpMethod: POST
     * </pre>
     * @param petId The value of path variable for petId. (ID of pet to update) (NotNull)
     * @param paramLambda The callback for RemotePetUploadimageParam. (NotNull)
     * @param ruleLambda The callback for setting rule as dynamic requirement. (NotNull)
     * @return The bean object as return type, receiving response body. (NotNull)
     */
    protected RemotePetUploadimageReturn requestUploadimage(Long petId, Consumer<RemotePetUploadimageParam> paramLambda, Consumer<FlutyRemoteApiRule> ruleLambda) {
        RemotePetUploadimageParam param = new RemotePetUploadimageParam();
        paramLambda.accept(param);
        return doRequestPost(RemotePetUploadimageReturn.class, "/pet/{petId}/uploadImage", moreUrl(petId), param, rule -> {
            rule.sendBodyBy(
                    new org.lastaflute.remoteapi.sender.body.LaFormSender(new org.dbflute.remoteapi.mapping.FlVacantMappingPolicy()));
            ruleOfUploadimagePetId(rule);
            ruleLambda.accept(rule);
        });
    }

    /**
     * Set up method-level rule of /pet/{petId}/uploadImage.<br>
     * @param rule The rule that class default rule is already set. (NotNull)
     */
    protected void ruleOfUploadimagePetId(FlutyRemoteApiRule rule) {
    }
}
