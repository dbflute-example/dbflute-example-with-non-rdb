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
package org.docksidestage.esflute.maihama.bsbhv;

import java.util.List;
import java.util.Map;

import org.docksidestage.esflute.maihama.allcommon.EsAbstractBehavior;
import org.docksidestage.esflute.maihama.allcommon.EsAbstractEntity;
import org.docksidestage.esflute.maihama.allcommon.EsAbstractEntity.RequestOptionCall;
import org.docksidestage.esflute.maihama.bsentity.dbmeta.MemberDbm;
import org.docksidestage.esflute.maihama.cbean.MemberCB;
import org.docksidestage.esflute.maihama.exentity.Member;
import org.dbflute.Entity;
import org.dbflute.bhv.readable.CBCall;
import org.dbflute.bhv.readable.EntityRowHandler;
import org.dbflute.cbean.ConditionBean;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.exception.IllegalBehaviorStateException;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.util.DfTypeUtil;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;

/**
 * @author ESFlute (using FreeGen)
 */
public abstract class BsMemberBhv extends EsAbstractBehavior<Member, MemberCB> {

    // ===================================================================================
    //                                                                    Control Override
    //                                                                    ================
    @Override
    public String asTableDbName() {
        return asEsIndexType();
    }

    @Override
    protected String asEsIndex() {
        return "maihama";
    }

    @Override
    public String asEsIndexType() {
        return "member";
    }

    @Override
    public String asEsSearchType() {
        return "member";
    }

    @Override
    public MemberDbm asDBMeta() {
        return MemberDbm.getInstance();
    }

    @Override
    protected <RESULT extends Member> RESULT createEntity(Map<String, Object> source, Class<? extends RESULT> entityType) {
        try {
            final RESULT result = entityType.newInstance();
            result.setAccount(DfTypeUtil.toString(source.get("account")));
            result.setName(DfTypeUtil.toString(source.get("name")));
            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            final String msg = "Cannot create a new instance: " + entityType.getName();
            throw new IllegalBehaviorStateException(msg, e);
        }
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    public int selectCount(CBCall<MemberCB> cbLambda) {
        return facadeSelectCount(createCB(cbLambda));
    }

    public OptionalEntity<Member> selectEntity(CBCall<MemberCB> cbLambda) {
        return facadeSelectEntity(createCB(cbLambda));
    }

    protected OptionalEntity<Member> facadeSelectEntity(MemberCB cb) {
        return doSelectOptionalEntity(cb, typeOfSelectedEntity());
    }

    protected <ENTITY extends Member> OptionalEntity<ENTITY> doSelectOptionalEntity(MemberCB cb,
            Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectEntity(cb, tp), cb);
    }

    @Override
    public MemberCB newConditionBean() {
        return new MemberCB();
    }

    @Override
    protected Entity doReadEntity(ConditionBean cb) {
        return facadeSelectEntity(downcast(cb)).orElse(null);
    }

    public Member selectEntityWithDeletedCheck(CBCall<MemberCB> cbLambda) {
        return facadeSelectEntityWithDeletedCheck(createCB(cbLambda));
    }

    public OptionalEntity<Member> selectByPK(String id) {
        return facadeSelectByPK(id);
    }

    protected OptionalEntity<Member> facadeSelectByPK(String id) {
        return doSelectOptionalByPK(id, typeOfSelectedEntity());
    }

    protected <ENTITY extends Member> ENTITY doSelectByPK(String id, Class<? extends ENTITY> tp) {
        return doSelectEntity(xprepareCBAsPK(id), tp);
    }

    protected MemberCB xprepareCBAsPK(String id) {
        assertObjectNotNull("id", id);
        return newConditionBean().acceptPK(id);
    }

    protected <ENTITY extends Member> OptionalEntity<ENTITY> doSelectOptionalByPK(String id, Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectByPK(id, tp), id);
    }

    @Override
    protected Class<? extends Member> typeOfSelectedEntity() {
        return Member.class;
    }

    @Override
    protected Class<Member> typeOfHandlingEntity() {
        return Member.class;
    }

    @Override
    protected Class<MemberCB> typeOfHandlingConditionBean() {
        return MemberCB.class;
    }

    public ListResultBean<Member> selectList(CBCall<MemberCB> cbLambda) {
        return facadeSelectList(createCB(cbLambda));
    }

    public PagingResultBean<Member> selectPage(CBCall<MemberCB> cbLambda) {
        // #pending same?
        return (PagingResultBean<Member>) facadeSelectList(createCB(cbLambda));
    }

    public void selectCursor(CBCall<MemberCB> cbLambda, EntityRowHandler<Member> entityLambda) {
        facadeSelectCursor(createCB(cbLambda), entityLambda);
    }

    public void selectBulk(CBCall<MemberCB> cbLambda, EntityRowHandler<List<Member>> entityLambda) {
        delegateSelectBulk(createCB(cbLambda), entityLambda,typeOfSelectedEntity());
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    public void insert(Member entity) {
        doInsert(entity, null);
    }

    public void insert(Member entity, RequestOptionCall<IndexRequestBuilder> opLambda) {
        entity.asDocMeta().indexOption(opLambda);
        doInsert(entity, null);
    }

    public void update(Member entity) {
        doUpdate(entity, null);
    }

    public void update(Member entity, RequestOptionCall<IndexRequestBuilder> opLambda) {
        entity.asDocMeta().indexOption(opLambda);
        doUpdate(entity, null);
    }

    public void insertOrUpdate(Member entity) {
        doInsertOrUpdate(entity, null, null);
    }

    public void insertOrUpdate(Member entity, RequestOptionCall<IndexRequestBuilder> opLambda) {
        entity.asDocMeta().indexOption(opLambda);
        doInsertOrUpdate(entity, null, null);
    }

    public void delete(Member entity) {
        doDelete(entity, null);
    }

    public void delete(Member entity, RequestOptionCall<DeleteRequestBuilder> opLambda) {
        entity.asDocMeta().deleteOption(opLambda);
        doDelete(entity, null);
    }

    public int queryDelete(CBCall<MemberCB> cbLambda) {
        return doQueryDelete(createCB(cbLambda), null);
    }

    public int[] batchInsert(List<Member> list) {
        return batchInsert(list, null, null);
    }

    public int[] batchInsert(List<Member> list, RequestOptionCall<BulkRequestBuilder> call) {
        return batchInsert(list, call, null);
    }

    public int[] batchInsert(List<Member> list, RequestOptionCall<BulkRequestBuilder> call,
            RequestOptionCall<IndexRequestBuilder> entityCall) {
        return doBatchInsert(new BulkList<>(list, call, entityCall), null);
    }

    public int[] batchUpdate(List<Member> list) {
        return batchUpdate(list, null, null);
    }

    public int[] batchUpdate(List<Member> list, RequestOptionCall<BulkRequestBuilder> call) {
        return batchUpdate(list, call, null);
    }

    public int[] batchUpdate(List<Member> list, RequestOptionCall<BulkRequestBuilder> call,
            RequestOptionCall<IndexRequestBuilder> entityCall) {
        return doBatchUpdate(new BulkList<>(list, call, entityCall), null);
    }

    public int[] batchDelete(List<Member> list) {
        return batchDelete(list, null, null);
    }

    public int[] batchDelete(List<Member> list, RequestOptionCall<BulkRequestBuilder> call) {
        return batchDelete(list, call, null);
    }

    public int[] batchDelete(List<Member> list, RequestOptionCall<BulkRequestBuilder> call,
            RequestOptionCall<IndexRequestBuilder> entityCall) {
        return doBatchDelete(new BulkList<>(list, call, entityCall), null);
    }

    // #pending create, modify, remove
}

