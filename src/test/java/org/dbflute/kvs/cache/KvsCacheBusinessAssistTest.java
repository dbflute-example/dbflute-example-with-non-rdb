package org.dbflute.kvs.cache;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.dbflute.cbean.ckey.ConditionKey;
import org.dbflute.dbmeta.info.ColumnInfo;
import org.dbflute.dbmeta.info.PrimaryInfo;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.util.DfCollectionUtil;
import org.docksidestage.dbflute.allcommon.ImplementedBehaviorSelector;
import org.docksidestage.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dbflute.bsentity.dbmeta.MemberSecurityDbm;
import org.docksidestage.dbflute.cbean.MemberCB;
import org.docksidestage.dbflute.exentity.Member;
import org.docksidestage.unit.UnitNonrdbTestCase;

public class KvsCacheBusinessAssistTest extends UnitNonrdbTestCase {

    @Resource
    private KvsCacheBusinessAssist kvsCacheBusinessAssist;

    @Resource
    private ImplementedBehaviorSelector selector;

    public void test_findEntity() throws Exception {
        MemberCB cb = new MemberCB();
        cb.specify().columnMemberId();
        cb.query().setMemberId_Equal(1);
        OptionalEntity<Member> memberOptionalEntity = kvsCacheBusinessAssist.findEntity(selector, "", DfCollectionUtil.newArrayList(1), cb,
                entity -> null, DfCollectionUtil.newHashSet(MemberDbm.getInstance().columnMemberId()), true);
        memberOptionalEntity.alwaysPresent(member -> {
            assertEquals(1, member.getMemberId());
        });
    }

    public void test_loadThreadCacheByIds() throws Exception {
        List<Object> idList = DfCollectionUtil.newArrayList((1));
        MemberSecurityDbm dbmeta = MemberSecurityDbm.getInstance();
        final PrimaryInfo primaryInfo = dbmeta.getPrimaryInfo();
        final String pkName = primaryInfo.getFirstColumn().getColumnDbName();
        HashSet<ColumnInfo> specifiedColumnInfoSet = DfCollectionUtil.newHashSet(MemberSecurityDbm.getInstance().columnMemberId(),
                MemberSecurityDbm.getInstance().columnReminderAnswer());
        kvsCacheBusinessAssist.loadThreadCacheByIds(selector, dbmeta.getProjectName(),
                idList.stream().map(id -> DfCollectionUtil.newArrayList((Object) id)).collect(Collectors.toList()), dbmeta, (cb, list) -> {
                    cb.localCQ().invokeQuery(pkName, ConditionKey.CK_IN_SCOPE.getConditionKey(), list.stream().flatMap(ids -> {
                        return ids.stream();
                    }).collect(Collectors.toList()));
                }, Long.valueOf(1), specifiedColumnInfoSet);
    }
}
