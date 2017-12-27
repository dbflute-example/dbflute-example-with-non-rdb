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

import javax.annotation.Resource;

import org.dbflute.remoteapi.mock.MockHttpClient;
import org.dbflute.util.DfCollectionUtil;
import org.docksidestage.remote.petstore.pet.index.RemotePetGetReturn;
import org.docksidestage.unit.UnitNonrdbTestCase;
import org.lastaflute.web.servlet.request.RequestManager;

public class RemotePetstorePetBhvTest extends UnitNonrdbTestCase {

    @Resource
    private RequestManager requestManager;

    public void test_requestProductList_basic() {
        // ## Arrange ##
        Long petId = 1L;
        String resopnseJsonPath = derivedResopnsePath(RemotePetGetReturn.class, "basic.json");
        MockHttpClient client = MockHttpClient.create(response -> {
            response.peekRequest(request -> {
                assertContains(request.getUrl(), "/" + petId);
            });
            response.asJson(resopnseJsonPath, request -> true);
        });
        registerMock(client);
        RemotePetstorePetBhv bhv = new RemotePetstorePetBhv(requestManager);
        inject(bhv);

        // ## Act ##
        RemotePetGetReturn ret = bhv.requestGet(petId);

        // ## Assert ##
        assertEquals(petId, ret.id);
        assertEquals(Long.valueOf(1), ret.category.id);
        assertEquals("Cachorro", ret.category.name);
        assertEquals("doggie", ret.name);
        assertEquals(DfCollectionUtil.newArrayList("string"), ret.photoUrls);
        assertHasOnlyOneElement(ret.tags);
        assertEquals(Long.valueOf(1), ret.tags.get(0).id);
        assertEquals("nome", ret.tags.get(0).name);
        assertEquals("available", ret.status);
    }
}
