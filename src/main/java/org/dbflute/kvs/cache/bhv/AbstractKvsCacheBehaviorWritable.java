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
package org.dbflute.kvs.cache.bhv;

import java.util.function.Consumer;

import org.dbflute.Entity;
import org.dbflute.cbean.ConditionBean;
import org.dbflute.kvs.cache.cbean.KvsCacheConditionBean;

public abstract class AbstractKvsCacheBehaviorWritable<ENTITY extends Entity, CB extends ConditionBean, KVS_CB extends KvsCacheConditionBean> {

    protected KVS_CB createCB(Consumer<KVS_CB> cbCall) { // CB from callback
        assertCBCallNotNull(cbCall);
        final KVS_CB cb = newConditionBean();
        cbCall.accept(cb);
        return cb;
    }

    public abstract KVS_CB newConditionBean();

    // -----------------------------------------------------
    //                                                Assert
    //                                                ------
    /**
     * Assert that the callback of condition-bean is not null.
     * @param cbCall The interface of kvs-condition-bean to be checked. (NotNull)
     */
    protected void assertCBCallNotNull(Consumer<KVS_CB> cbCall) {
        assertObjectNotNull("cbLambda", cbCall);
    }

    /**
     * Assert that the object is not null.
     * @param variableName The variable name for message. (NotNull)
     * @param value The value the checked variable. (NotNull)
     * @throws IllegalArgumentException When the variable name or the variable is null.
     */
    protected void assertObjectNotNull(String variableName, Object value) {
        if (variableName == null) {
            String msg = "The value should not be null: variableName=null value=" + value;
            throw new IllegalArgumentException(msg);
        }
        if (value == null) {
            String msg = "The value should not be null: variableName=" + variableName;
            throw new IllegalArgumentException(msg);
        }
    }
}
