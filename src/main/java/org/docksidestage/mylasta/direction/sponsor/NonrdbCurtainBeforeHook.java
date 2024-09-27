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
package org.docksidestage.mylasta.direction.sponsor;

import java.util.Map;
import java.util.TimeZone;

import org.dbflute.kvs.cache.KvsCacheColumnNullObject;
import org.dbflute.kvs.cache.facade.KvsCacheFacade;
import org.dbflute.system.DBFluteSystem;
import org.dbflute.system.provider.DfFinalTimeZoneProvider;
import org.dbflute.util.DfCollectionUtil;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dbflute.allcommon.DBCurrent;
import org.docksidestage.kvs.cache.maihamadb.facade.MaihamadbKvsCacheFacade;
import org.lastaflute.core.direction.CurtainBeforeHook;
import org.lastaflute.core.direction.FwAssistantDirector;
import org.lastaflute.core.util.ContainerUtil;

/**
 * @author jflute
 */
public class NonrdbCurtainBeforeHook implements CurtainBeforeHook {

    public void hook(FwAssistantDirector assistantDirector) {
        processDBFluteSystem();
        processDBFluteCacheObject();
    }

    protected void processDBFluteSystem() {
        DBFluteSystem.unlock();
        DBFluteSystem.setFinalTimeZoneProvider(createFinalTimeZoneProvider());
        DBFluteSystem.lock();
    }

    protected DfFinalTimeZoneProvider createFinalTimeZoneProvider() {
        return new DfFinalTimeZoneProvider() {
            protected final TimeZone provided = NonrdbUserTimeZoneProcessProvider.centralTimeZone;

            public TimeZone provide() {
                return provided;
            }

            @Override
            public String toString() {
                return DfTypeUtil.toClassTitle(this) + ":{" + provided.getID() + "}";
            }
        };
    }

    protected void processDBFluteCacheObject() {
        initializeColumnCache();
    }

    // #KvsCacheColumnNullObject initialize
    protected void initializeColumnCache() {
        Map<String, KvsCacheFacade> kvsCacheFacadeMap = DfCollectionUtil.newHashMap();
        kvsCacheFacadeMap.put(DBCurrent.getInstance().projectName(), ContainerUtil.getComponent(MaihamadbKvsCacheFacade.class));
        KvsCacheColumnNullObject.getInstance().init(kvsCacheFacadeMap);

        ContainerUtil.searchComponents(KvsCacheFacade.class);
    }
}
