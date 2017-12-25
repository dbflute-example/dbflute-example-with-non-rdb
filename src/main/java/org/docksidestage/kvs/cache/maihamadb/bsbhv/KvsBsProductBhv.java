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
package org.docksidestage.kvs.cache.maihamadb.bsbhv;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.dbflute.dbmeta.info.ColumnInfo;
import org.dbflute.kvs.cache.bhv.AbstractKvsCacheBehaviorWritable;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.util.DfCollectionUtil;

import org.docksidestage.dbflute.bsentity.dbmeta.ProductDbm;
import org.docksidestage.dbflute.cbean.ProductCB;
import org.docksidestage.dbflute.exentity.Product;
import org.docksidestage.kvs.cache.maihamadb.cbean.KvsProductCB;
import org.docksidestage.kvs.cache.maihamadb.facade.MaihamadbKvsCacheFacade;

/**
 * The behavior of (Product)Product.
 * @author FreeGen
 */
public abstract class KvsBsProductBhv extends AbstractKvsCacheBehaviorWritable<Product, ProductCB, KvsProductCB> {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** MaihamadbKvsCacheFacade. */
    @Resource
    protected MaihamadbKvsCacheFacade maihamadbKvsCacheFacade;

    /**
     * Handle the meta as DBMeta, that has all info of the table.
     * @return DBMeta instance (NotNull)
     */
    public ProductDbm asDBMeta() {
        return ProductDbm.getInstance();
    }

    // ===================================================================================
    //                                                              ProductId
    //                                                              ======================
    /**
     * Select the entity by the condition-bean.<br>
     * It returns non-null Optional entity, so you should...
     * <ul>
     *   <li>use alwaysPresent() if the data is always present as your business rule</li>
     *   <li>use ifPresent() and orElse() if it might be empty</li>
     * </ul>
     * <pre>
     * <span style="color: #0000C0">kvsProductBhv</span>.<span style="color: #CC4747">selectEntityByProductId</span>(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #3F7E5E">// Setting query condition to the key column is required</span>
     *     <span style="color: #553000">cb</span>.query().setProductId_Equal(1);
     *     <span style="color: #3F7E5E">// Condition for other column(s) can be set if necessary</span>
     *     <span style="color: #553000">cb</span>.query().set...
     * }).<span style="color: #CC4747">alwaysPresent</span>(<span style="color: #553000">product</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     ...
     * });
     * </pre>
     * @param cbLambda The callback for condition-bean of KvsProduct (NotNull)
     * @return The optional entity selected by the condition (NotNull: if no data, empty entity)
     */
    public OptionalEntity<Product> selectEntityByProductId(Consumer<KvsProductCB> cbLambda) {
        KvsProductCB kvsCB = createCB(cbLambda);
        ProductCB cb = adjustKvsConditionBeanOfProductId(kvsCB);

        OptionalEntity<Product> optEntity =
                maihamadbKvsCacheFacade.findEntity(createKvsKeyListOfProductId(kvsCB), cb, expireDateTimeLambdaOfProductId());

        return Stream.of(optEntity)
                .filter(OptionalEntity::isPresent)
                .map(OptionalEntity::get)
                .filter(kvsCB.query().getWherePredicate())
                .findFirst()
                .map(OptionalEntity::of)
                .orElseGet(() -> OptionalEntity.empty());
    }

    // KVS CB から ProductId 用の ProductCB を作成して返します。
    protected ProductCB adjustKvsConditionBeanOfProductId(KvsProductCB kvsCB) {
        ProductCB cb = new ProductCB();
        addKvsConditionOfProductId(kvsCB, cb);

        return cb;
    }

    // ProductId で DB に使う条件のみを kvsCB から取り出し cb に設定します。
    protected void addKvsConditionOfProductId(KvsProductCB kvsCB, ProductCB cb) {
        addDefaultSpecifyColumnOfProductId(kvsCB, cb);
        addDefaultWhereOfProductId(kvsCB, cb);
    }

    // ProductId 用の select 句の列挙されるカラムを CB に設定します。
    protected void addDefaultSpecifyColumnOfProductId(KvsProductCB kvsCB, ProductCB cb) {
        cb.specify().everyColumn();
    }

    // ProductId 用の キー条件を CB に設定します。
    protected void addDefaultWhereOfProductId(KvsProductCB kvsCB, ProductCB cb) {
        Map<ColumnInfo, Object> columnEqualValue = kvsCB.query().xdfgetColumnEqualValue();

        Object productId = columnEqualValue.get(asDBMeta().columnProductId());
        if (productId == null) {
            throw new IllegalStateException("'setProductId_Equal()' has not been called.");
        }
        cb.query().invokeQueryEqual("productId", productId);
    }

    protected Function<Product, LocalDateTime> expireDateTimeLambdaOfProductId() {
        String expireDateTimeColumnFlexibleName = null;
        return expireDateTimeLambda(maihamadbKvsCacheFacade, expireDateTimeColumnFlexibleName);
    }

    /**
     * Select the ttl by the condition-bean.<br>
     * It returns non-null Optional entity, so you should...
     * <ul>
     *   <li>use alwaysPresent() if the data is always present as your business rule</li>
     *   <li>use ifPresent() and orElse() if it might be empty</li>
     * </ul>
     * <pre>
     * <span style="color: #0000C0">kvsProductBhv</span>.<span style="color: #CC4747">ttlByProductId</span>(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #3F7E5E">// Setting query condition to the key column is required</span>
     *     <span style="color: #553000">cb</span>.query().setProductId_Equal(1);
     *     <span style="color: #3F7E5E">// Condition for other column(s) can be set if necessary</span>
     *     <span style="color: #553000">cb</span>.query().set...
     * }).<span style="color: #CC4747">alwaysPresent</span>(<span style="color: #553000">ttl</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     ...
     * });
     * </pre>
     * @param cbLambda The callback for condition-bean of KvsProduct (NotNull)
     * @return The optional ttl selected by the condition (NotNull: if no data, empty ttl)
     */
    public OptionalEntity<Long> selectTtlByProductId(Consumer<KvsProductCB> cbLambda) {
        KvsProductCB kvsCB = createCB(cbLambda);
        ProductCB cb = adjustKvsConditionBeanOfProductId(kvsCB);
        return maihamadbKvsCacheFacade.ttl(createKvsKeyListOfProductId(kvsCB), cb);
    }

    /**
     * Insert or update the entity.
     * <pre>
     * <span style="color: #0000C0">kvsSakuhinBhv</span>.<span style="color: #CC4747">insertOrUpdateBySakuhinId</span>(() <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     Sakuhin sakuhin = <span style="color: #70226C">new</span> Sakuhin();
     *     <span style="color: #3F7E5E">// If you are going to update or the KVS-key which is not primary key in RDB is specified, set key-column(s)</span>
     *     <span style="color: #3F7E5E">// e.g.) sakuhin.setProductionYear(year);</span>
     *     <span style="color: #3F7E5E">// Set value for insert/update</span>
     *     sakuhin.set...;
     *     ...
     *     <span style="color: #70226C">return</span> sakuhin;
     * });
     * </pre>
     * @param entityLambda The handler of entity row of Product (NotNull)
     * @return The Entity used to insert/update with automatically-set column value (NotNull)
     */
    public Product insertOrUpdateByProductId(Supplier<Product> entityLambda) {
        Product product = entityLambda.get();
        maihamadbKvsCacheFacade.insertOrUpdate(createKvsKeyListOfProductId(product), product);

        return product;
    }

    /**
     * Delete the entity.
     * <pre>
     * <span style="color: #0000C0">kvsSakuhinBhv</span>.<span style="color: #CC4747">deleteBySakuhinId</span>(() <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     Sakuhin sakuhin = <span style="color: #70226C">new</span> Sakuhin();
     *     <span style="color: #3F7E5E">// Set KVS-key(s)</span>
     *     sakuhin.setSakuhinId(<span style="color: #553000">sakuhinId</span>);
     *     <span style="color: #70226C">return</span> sakuhin;
     * });
     * </pre>
     * @param entityLambda The handler of entity row of Product (NotNull)
     * @return The Entity used to delete (NotNull)
     */
    public Product deleteByProductId(Supplier<Product> entityLambda) {
        Product product = entityLambda.get();
        maihamadbKvsCacheFacade.delete(createKvsKeyListOfProductId(product), product);

        return product;
    }

    protected List<Object> createKvsKeyListOfProductId(KvsProductCB kvsCB) {
        List<Object> kvsKeyList = DfCollectionUtil.newArrayList();
        kvsKeyList.add("productId");

        Map<ColumnInfo, Object> columnEqualValue = kvsCB.query().xdfgetColumnEqualValue();

        // productId
        Object productId = columnEqualValue.get(asDBMeta().columnProductId());
        if (productId == null) {
            throw new IllegalStateException("'setProductId_Equal()' has not been called.");
        }
        kvsKeyList.add(productId);

        return kvsKeyList;
    }

    protected List<Object> createKvsKeyListOfProductId(Product product) {
        List<Object> kvsKeyList = DfCollectionUtil.newArrayList();
        kvsKeyList.add("productId");
        kvsKeyList.add(product.getProductId());
        return kvsKeyList;
    }

    // ===================================================================================
    //                                                              CategoryCode
    //                                                              ======================
    /**
     * Select the entity List by the condition-bean.
     * <pre>
     * ListResultBean&lt;SakuhinDetail&gt; sakuhinDetailList = <span style="color: #0000C0">kvsSakuhinDetailBhv</span>.<span style="color: #CC4747">selectListByProductionCountrySakuhin</span>(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #3F7E5E">// Setting query condition to the key column(s) is required</span>
     *     <span style="color: #553000">cb</span>.query().setDisplayProductionCountry_Equal(countryName);
     *     <span style="color: #553000">cb</span>.query().setSakuhinKana_LikeSearch(...);
     * });
     * sakuhinDetailList.forEach(<span style="color: #553000">detail</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     ... = <span style="color: #553000">detail</span>.get...;
     * });
     * </pre>
     * @param cbLambda The callback for condition-bean of KvsProduct (NotNull)
     * @return The List of entities selected by the condition (NotNull: if no data, empty list)
     */
    public List<Product> selectListByCategoryCode(Consumer<KvsProductCB> cbLambda) {
        KvsProductCB kvsCB = createCB(cbLambda);
        ProductCB cb = adjustKvsConditionBeanOfCategoryCode(kvsCB);

        List<Product> list =
                maihamadbKvsCacheFacade.findList(createKvsKeyListOfCategoryCode(kvsCB), cb, expireDateTimeLambdaOfCategoryCode());

        Predicate<Product> filter = kvsCB.query().getWherePredicate();
        Comparator<Product> sorted = kvsCB.query().getOrderByComparator();

        Stream<Product> stream = list.stream().filter(filter);

        if (sorted != null) {
            stream = stream.sorted(sorted);
        }

        if (kvsCB.xgetFetchFirst().isPresent()) {
            stream = stream.limit(kvsCB.xgetFetchFirst().get());
        }

        return stream.collect(Collectors.toList());
    }

    // KVS CB から CategoryCode 用の ProductCB を作成して返します。
    protected ProductCB adjustKvsConditionBeanOfCategoryCode(KvsProductCB kvsCB) {
        ProductCB cb = new ProductCB();
        addKvsConditionOfCategoryCode(kvsCB, cb);

        return cb;
    }

    // CategoryCode で DB に使う条件のみを kvsCB から取り出し cb に設定します。
    protected void addKvsConditionOfCategoryCode(KvsProductCB kvsCB, ProductCB cb) {
        addDefaultSpecifyColumnOfCategoryCode(kvsCB, cb);
        addDefaultWhereOfCategoryCode(kvsCB, cb);
        addDefaultOrderByOfCategoryCode(cb);
        kvsCB.xgetFetchFirst().ifPresent(fetchSize -> cb.fetchFirst(fetchSize));
    }

    // CategoryCode 用の select 句の列挙されるカラムを CB に設定します。
    protected void addDefaultSpecifyColumnOfCategoryCode(KvsProductCB kvsCB, ProductCB cb) {
        cb.specify().everyColumn();
    }

    // CategoryCode 用のキー条件を CB に設定します。
    protected void addDefaultWhereOfCategoryCode(KvsProductCB kvsCB, ProductCB cb) {
        Map<ColumnInfo, Object> columnEqualValue = kvsCB.query().xdfgetColumnEqualValue();

        Object productCategoryCode = columnEqualValue.get(asDBMeta().columnProductCategoryCode());
        if (productCategoryCode == null) {
            throw new IllegalStateException("'setCategoryCode_Equal()' has not been called.");
        }
        cb.query().invokeQueryEqual("productCategoryCode", productCategoryCode);
    }

    // CategoryCode 用のデフォルトソート順を CB に設定します。
    protected void addDefaultOrderByOfCategoryCode(ProductCB cb) {
        cb.query().addOrderBy_RegularPrice_Asc();
        cb.query().addOrderBy_ProductId_Asc();
    }

    protected Function<List<Product>, LocalDateTime> expireDateTimeLambdaOfCategoryCode() {
        String expireDateTimeColumnFlexibleName = null;
        return expireDateTimeOfMinInListLambda(maihamadbKvsCacheFacade, expireDateTimeColumnFlexibleName);
    }

    /**
     * CategoryCode 用のデフォルトソート順を Comparator で取得します。
     */
    protected Comparator<Product> createDefaultOrderByOfCategoryCode() {
        Comparator<Product> comparator = Comparator.comparing(Product::getRegularPrice, Comparator.naturalOrder());
        comparator.thenComparing(Product::getProductId, Comparator.naturalOrder());
        return comparator;
    }

    /**
     * Select the ttl by the condition-bean.<br>
     * It returns non-null Optional entity, so you should...
     * <ul>
     *   <li>use alwaysPresent() if the data is always present as your business rule</li>
     *   <li>use ifPresent() and orElse() if it might be empty</li>
     * </ul>
     * <pre>
     * <span style="color: #0000C0">kvsProductBhv</span>.<span style="color: #CC4747">ttlByCategoryCode</span>(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #3F7E5E">// Setting query condition to the key column is required</span>
     *     <span style="color: #553000">cb</span>.query().setCategoryCode_Equal(1);
     *     <span style="color: #3F7E5E">// Condition for other column(s) can be set if necessary</span>
     *     <span style="color: #553000">cb</span>.query().set...
     * }).<span style="color: #CC4747">alwaysPresent</span>(<span style="color: #553000">ttl</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     ...
     * });
     * </pre>
     * @param cbLambda The callback for condition-bean of KvsProduct (NotNull)
     * @return The optional ttl selected by the condition (NotNull: if no data, empty ttl)
     */
    public OptionalEntity<Long> selectTtlByCategoryCode(Consumer<KvsProductCB> cbLambda) {
        KvsProductCB kvsCB = createCB(cbLambda);
        ProductCB cb = adjustKvsConditionBeanOfCategoryCode(kvsCB);
        return maihamadbKvsCacheFacade.ttl(createKvsKeyListOfCategoryCode(kvsCB), cb);
    }

    /**
     * Insert or update the entity.
     * <pre>
     * <span style="color: #0000C0">kvsSakuhinBhv</span>.<span style="color: #CC4747">insertOrUpdateBySakuhinId</span>(() <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     Sakuhin sakuhin = <span style="color: #70226C">new</span> Sakuhin();
     *     <span style="color: #3F7E5E">// If you are going to update or the KVS-key which is not primary key in RDB is specified, set key-column(s)</span>
     *     <span style="color: #3F7E5E">// e.g.) sakuhin.setProductionYear(year);</span>
     *     <span style="color: #3F7E5E">// Set value for insert/update</span>
     *     sakuhin.set...;
     *     ...
     *     <span style="color: #70226C">return</span> sakuhin;
     * });
     * </pre>
     * @param entityLambda The handler of entity row of Product (NotNull)
     * @return The Entity used to insert/update with automatically-set column value (NotNull)
     */
    public Product insertOrUpdateByCategoryCode(Supplier<Product> entityLambda) {
        Product product = entityLambda.get();
        maihamadbKvsCacheFacade.insertOrUpdate(createKvsKeyListOfCategoryCode(product), product);

        return product;
    }

    /**
     * Delete the entity.
     * <pre>
     * <span style="color: #0000C0">kvsSakuhinBhv</span>.<span style="color: #CC4747">deleteBySakuhinId</span>(() <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     Sakuhin sakuhin = <span style="color: #70226C">new</span> Sakuhin();
     *     <span style="color: #3F7E5E">// Set KVS-key(s)</span>
     *     sakuhin.setSakuhinId(<span style="color: #553000">sakuhinId</span>);
     *     <span style="color: #70226C">return</span> sakuhin;
     * });
     * </pre>
     * @param entityLambda The handler of entity row of Product (NotNull)
     * @return The Entity used to delete (NotNull)
     */
    public Product deleteByCategoryCode(Supplier<Product> entityLambda) {
        Product product = entityLambda.get();
        maihamadbKvsCacheFacade.delete(createKvsKeyListOfCategoryCode(product), product);

        return product;
    }

    protected List<Object> createKvsKeyListOfCategoryCode(KvsProductCB kvsCB) {
        List<Object> kvsKeyList = DfCollectionUtil.newArrayList();
        kvsKeyList.add("categoryCode");

        Map<ColumnInfo, Object> columnEqualValue = kvsCB.query().xdfgetColumnEqualValue();

        // productCategoryCode
        Object productCategoryCode = columnEqualValue.get(asDBMeta().columnProductCategoryCode());
        if (productCategoryCode == null) {
            throw new IllegalStateException("'setProductCategoryCode_Equal()' has not been called.");
        }
        kvsKeyList.add(productCategoryCode);

        return kvsKeyList;
    }

    protected List<Object> createKvsKeyListOfCategoryCode(Product product) {
        List<Object> kvsKeyList = DfCollectionUtil.newArrayList();
        kvsKeyList.add("categoryCode");
        kvsKeyList.add(product.getProductCategoryCode());
        return kvsKeyList;
    }

    @Override
    public KvsProductCB newConditionBean() {
        return new KvsProductCB();
    }
}
