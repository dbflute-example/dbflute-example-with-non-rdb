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
package org.docksidestage.kvs.store.examplestore.bsbhv;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.dbflute.optional.OptionalEntity;
import org.dbflute.util.DfCollectionUtil;
import org.docksidestage.kvs.store.examplestore.bsentity.dbmeta.KvsEgStoreExampleDbm;
import org.docksidestage.kvs.store.examplestore.cbean.KvsEgStoreExampleCB;
import org.docksidestage.kvs.store.examplestore.exentity.KvsEgStoreExample;
import org.docksidestage.kvs.store.examplestore.facade.ExamplestoreKvsStoreFacade;

/**
 * The behavior of (Description about this KVS store)StoreExample.
 * @author FreeGen
 */
public abstract class KvsEgBsStoreExampleBhv {

    /** ExamplestoreKvsStoreFacade. */
    @Resource
    private ExamplestoreKvsStoreFacade examplestoreKvsStoreFacade;

    /**
     * Handle the meta as DBMeta, that has all info of the table.
     * @return DBMeta instance (NotNull)
     */
    public KvsEgStoreExampleDbm asDBMeta() {
        return KvsEgStoreExampleDbm.getInstance();
    }

    /**
     * Select the entity by the condition-bean.<br>
     * It returns non-null Optional entity, so you should...
     * <ul>
     *   <li>use alwaysPresent() if the data is always present as your business rule</li>
     *   <li>use ifPresent() and orElse() if it might be empty</li>
     * </ul>
     * <pre>
     * <span style="color: #0000C0">kvsEgStoreExampleBhv</span>.<span style="color: #CC4747">selectEntity</span>(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.acceptKey(<span style="color: #553000">key</span>);
     * }).<span style="color: #CC4747">ifPresent</span>(<span style="color: #553000">storeExample</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #3F7E5E">// Called if present</span>
     *     ... = <span style="color: #553000">storeExample</span>.get...;
     * }).<span style="color: #CC4747">orElse</span>(() <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #3F7E5E">// Called if not present</span>
     *     ...
     * });
     * </pre>
     * @param cbLambda The callback for condition-bean of KvsEgStoreExample. (NotNull)
     * @return The optional entity selected by the condition. (NotNull: if no data, empty entity)
     */
    public OptionalEntity<KvsEgStoreExample> selectEntity(Consumer<KvsEgStoreExampleCB> cbLambda) {
        KvsEgStoreExampleCB cb = new KvsEgStoreExampleCB();
        cbLambda.accept(cb);

        KvsEgStoreExampleDbm dbMeta = asDBMeta();
        dbMeta.validateKeyColumn(cb);

        return examplestoreKvsStoreFacade.findEntity(dbMeta, dbMeta.extractKeyList(cb));
    }

    /**
     * Select the entity List by the condition-bean.
     * <pre>
     * List&lt;KvsEgStoreExample&gt; kvsEgStoreExampleList = <span style="color: #0000C0">kvsEgStoreExampleBhv</span>.<span style="color: #CC4747">selectList</span>(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.query().setEgkey_inScope(<span style="color: #553000">keyList</span>);
     * });
     * kvsEgStoreExampleList.forEach(<span style="color: #553000">storeExample</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     ... = <span style="color: #553000">storeExample</span>.get...;
     * });
     * </pre>
     * @param cbLambda The callback for condition-bean of KvsEgStoreExample (NotNull)
     * @return The List of entities selected by the condition (NotNull: if no data, empty list)
     */
    public List<KvsEgStoreExample> selectList(Consumer<KvsEgStoreExampleCB> cbLambda) {
        KvsEgStoreExampleCB cb = new KvsEgStoreExampleCB();
        cbLambda.accept(cb);

        KvsEgStoreExampleDbm dbMeta = asDBMeta();

        List<List<Object>> keyListList = cb.query().xdfgetEgkeyList().stream().map(value -> {
            List<Object> keyList = DfCollectionUtil.newArrayList(value);
            return keyList;
        }).collect(Collectors.toList());

        return examplestoreKvsStoreFacade.findEntityMap(dbMeta, keyListList)
                .entrySet()
                .stream()
                .map(entry -> (KvsEgStoreExample) entry.getValue())
                .collect(Collectors.toList());
    }

    /**
     * Insert or update the entity.
     * <pre>
     * <span style="color: #0000C0">kvsEgStoreExampleBhv</span>.<span style="color: #CC4747">insertOrUpdate</span>(() <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     KvsEgStoreExample entity = <span style="color: #70226C">new</span> KvsEgStoreExample();
     *     <span style="color: #3F7E5E">// Setting KVS-key(s) is required</span>
     *     entity.setXxx(<span style="color: #553000">xxx</span>);
     *     <span style="color: #3F7E5E">// Set other column value(s) for insert/update</span>
     *     entity.set...;
     *     ...
     *     <span style="color: #70226C">return</span> entity;
     * });
     * </pre>
     * @param entityLambda The handler of entity row of KvsEgStoreExample (NotNull)
     * @return The Entity used to insert/update with automatically-set column value (NotNull)
     */
    public KvsEgStoreExample insertOrUpdate(Supplier<KvsEgStoreExample> entityLambda) {
        KvsEgStoreExample storeExample = entityLambda.get();
        KvsEgStoreExampleDbm dbMeta = asDBMeta();
        dbMeta.validateAllColumn(storeExample);
        examplestoreKvsStoreFacade.insertOrUpdate(dbMeta, dbMeta.extractKeyList(storeExample), storeExample);
        return storeExample;
    }

    /**
     * Insert or update the entity with TTL.
     * <pre>
     * <span style="color: #0000C0">kvsEgStoreExampleBhv</span>.<span style="color: #CC4747">insertOrUpdate</span>(() <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     KvsEgStoreExample entity = <span style="color: #70226C">new</span> KvsEgStoreExample();
     *     <span style="color: #3F7E5E">// Setting KVS-key(s) is required</span>
     *     entity.setXxx(<span style="color: #553000">xxx</span>);
     *     <span style="color: #3F7E5E">// Set other column value(s) for insert/update</span>
     *     entity.set...;
     *     ...
     *     <span style="color: #70226C">return</span> entity;
     * }, LocalDateTime.now().plus(<span style="color: #553000">86400000</span>, ChronoUnit.MILLIS));
     * </pre>
     * @param entityLambda The handler of entity row of KvsEgStoreExample (NotNull)
     * @param expireDateTime expire date time
     * @return The Entity used to insert/update with automatically-set column value (NotNull)
     */
    public KvsEgStoreExample insertOrUpdate(Supplier<KvsEgStoreExample> entityLambda, LocalDateTime expireDateTime) {
        KvsEgStoreExample storeExample = entityLambda.get();
        KvsEgStoreExampleDbm dbMeta = asDBMeta();
        dbMeta.validateAllColumn(storeExample);
        examplestoreKvsStoreFacade.insertOrUpdate(dbMeta, dbMeta.extractKeyList(storeExample), storeExample, expireDateTime);

        return storeExample;
    }

    /**
     * Delete the entity.
     * <pre>
     * <span style="color: #0000C0">kvsEgStoreExampleBhv</span>.<span style="color: #CC4747">delete</span>(() <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     KvsEgStoreExample entity = <span style="color: #70226C">new</span> KvsEgStoreExample();
     *     <span style="color: #3F7E5E">// Set KVS-key(s)</span>
     *     entity.setXxx(<span style="color: #553000">xxx</span>);
     *     <span style="color: #70226C">return</span> entity;
     * });
     * </pre>
     * @param entityLambda The handler of entity row of KvsEgStoreExample (NotNull)
     * @return The Entity used to delete (NotNull)
     */
    public KvsEgStoreExample delete(Supplier<KvsEgStoreExample> entityLambda) {
        KvsEgStoreExample storeExample = entityLambda.get();
        KvsEgStoreExampleDbm dbMeta = asDBMeta();
        dbMeta.validateKeyColumn(storeExample);
        examplestoreKvsStoreFacade.delete(dbMeta, dbMeta.extractKeyList(storeExample));

        return storeExample;
    }
}
