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
package org.docksidestage.remote.petstore;

import javax.annotation.Resource;

import org.dbflute.remoteapi.FlutyRemoteApiRule;
import org.docksidestage.mylasta.direction.NonrdbConfig;
import org.lastaflute.core.json.JsonMappingOption;
import org.lastaflute.remoteapi.receiver.LaJsonReceiver;
import org.lastaflute.remoteapi.sender.body.LaJsonSender;
import org.lastaflute.web.servlet.request.RequestManager;

/**
 * The base class of all remote API behaviors as RemoteApiPetstore.
 * <p>
 * You can implement your original methods here.
 * This class remains when re-generating.
 * </p>
 * @author FreeGen
 */
public abstract class AbstractRemotePetstoreBhv extends org.lastaflute.remoteapi.LastaRemoteBehavior {

    @Resource
    private NonrdbConfig nonrdbConfig;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    /**
     * @param requestManager The manager of request, LastaFlute component. (NotNull)
     */
    public AbstractRemotePetstoreBhv(RequestManager requestManager) {
        super(requestManager);
    }

    // ===================================================================================
    //                                                                          Initialize
    //                                                                          ==========
    @Override
    protected void yourDefaultRule(FlutyRemoteApiRule rule) {
        JsonMappingOption mappingOption = new JsonMappingOption();
        rule.sendBodyBy(new LaJsonSender(requestManager, mappingOption));
        rule.receiveBodyBy(new LaJsonReceiver(requestManager, mappingOption));
    }

    @Override
    protected String getUrlBase() {
        return nonrdbConfig.getRemoteApiPetstoreUrlBase();
    }
}
