/*
 * Copyright(c) u-next.
 */
package org.dbflute.kvs.cache;

import java.util.List;

import javax.annotation.Resource;

import org.dbflute.util.DfCollectionUtil;
import org.docksidestage.dbflute.bsentity.dbmeta.MemberStatusDbm;
import org.docksidestage.dbflute.exbhv.MemberSecurityBhv;
import org.docksidestage.dbflute.exbhv.MemberStatusBhv;
import org.docksidestage.dbflute.exentity.MemberSecurity;
import org.docksidestage.dbflute.exentity.MemberStatus;
import org.docksidestage.unit.UnitNonrdbTestCase;

public class KvsCacheColumnNullObjectTest extends UnitNonrdbTestCase {

    @Resource
    private MemberStatusBhv memberStatusBhv;

    @Resource
    private MemberSecurityBhv memberSecurityBhv;

    public void test_memberStatus() throws Exception {
        List<MemberStatus> list = memberStatusBhv.selectList(cb -> cb.specify().columnDescription());
        list.forEach(memberStatus -> {
            log(memberStatus.myspecifiedProperties());
            assertNotNull(memberStatus.getDescription());
        });
    }

    public void test_memberStatus_All() throws Exception {
        List<MemberStatus> list = memberStatusBhv.selectList(cb -> cb.specify().columnDescription());
        list.forEach(memberStatus -> {
            log(memberStatus.myspecifiedProperties());
            assertNotNull(memberStatus.getDescription());
        });
    }

    public void test_memberSecurity() throws Exception {
        List<MemberSecurity> list = memberSecurityBhv.selectList(cb -> {
            cb.specify().columnReminderQuestion();
            cb.query().setMemberId_InScope(DfCollectionUtil.newArrayList(1));
        });
        list.forEach(memberSecurity -> {
            log(memberSecurity.myspecifiedProperties());
            log(memberSecurity);
            assertNotNull(memberSecurity.getReminderQuestion());
            log(memberSecurity);
        });

        list = memberSecurityBhv.selectList(cb -> {
            cb.specify().columnReminderQuestion();
            cb.query().setMemberId_InScope(DfCollectionUtil.newArrayList(1));
        });
        list.forEach(memberSecurity -> {
            log(memberSecurity.myspecifiedProperties());
            log(memberSecurity);
            assertNotNull(memberSecurity.getReminderQuestion());
            log(memberSecurity);
        });

        list = memberSecurityBhv.selectList(cb -> {
            cb.specify().columnReminderAnswer();
            cb.query().setMemberId_InScope(DfCollectionUtil.newArrayList(1));
        });
        list.forEach(memberSecurity -> {
            log(memberSecurity.myspecifiedProperties());
            log(memberSecurity);
            assertNotNull(memberSecurity.getReminderAnswer());
            log(memberSecurity);
        });

        list = memberSecurityBhv.selectList(cb -> {
            cb.specify().columnUpdateDatetime();
            cb.query().setMemberId_InScope(DfCollectionUtil.newArrayList(1));
        });
        list.forEach(memberSecurity -> {
            log(memberSecurity.myspecifiedProperties());
            log(memberSecurity);
            assertNotNull(memberSecurity.getUpdateDatetime());
            log(memberSecurity);
        });

        list = memberSecurityBhv.selectList(cb -> {
            cb.specify().columnUpdateDatetime();
            cb.query().setMemberId_InScope(DfCollectionUtil.newArrayList(1));
        });
        list.forEach(memberSecurity -> {
            log(memberSecurity.myspecifiedProperties());
            log(memberSecurity);
            assertNotNull(memberSecurity.getUpdateDatetime());
            log(memberSecurity);
        });
        sleep(1000);
    }

    public void test_memberStatus_load() throws Exception {
        List<MemberStatus> list = memberStatusBhv.selectList(cb -> cb.specify().columnDescription());
        KvsCacheColumnNullObject.getInstance().loadThreadCache(MemberStatusDbm.getInstance(), list);
        list.forEach(memberStatus -> {
            log(memberStatus.myspecifiedProperties());
            log(memberStatus);
            assertNotNull(memberStatus.getDescription());
            log(memberStatus);
        });
    }
}
