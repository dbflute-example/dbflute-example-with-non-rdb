/*
 * Copyright(c) u-next.
 */
package org.dbflute.kvs.cache;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.dbflute.utflute.core.PlainTestCase;
import org.dbflute.util.DfCollectionUtil;
import org.docksidestage.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dbflute.exentity.Member;

/**
 * @author p1us2er0
 */
public class KvsCacheConverterHandlerTest extends PlainTestCase {

    public void test_toMapString_toEntity_basic() {
        // ## Arrange ##
        Member expectedMember = new Member();
        expectedMember.setMemberId(111);
        expectedMember.setMemberName("TEST");

        // ## Act ##
        KvsCacheConverterHandler kvsCacheConverterHandler = new KvsCacheConverterHandler();
        String mapString = kvsCacheConverterHandler.toMapString(expectedMember);
        Member member = kvsCacheConverterHandler.toEntity(mapString, MemberDbm.getInstance());

        // ## Assert ##
        assertEquals(expectedMember.getMemberId(), member.getMemberId());
        assertEquals(expectedMember.getMemberName(), member.getMemberName());
    }

    public void test_toMapStringList_toEntityList_basic() {
        // ## Arrange ##
        List<Member> expectedMemberList = DfCollectionUtil.newArrayList();
        Member expectedMember = new Member();
        expectedMember.setMemberId(111);
        expectedMember.setMemberName("TEST");
        expectedMemberList.add(expectedMember);
        expectedMemberList.add(expectedMember);

        // ## Act ##
        KvsCacheConverterHandler kvsCacheConverterHandler = new KvsCacheConverterHandler();
        List<String> mapStringList = kvsCacheConverterHandler.toMapStringList(expectedMemberList);
        List<Member> memberList = kvsCacheConverterHandler.toEntityList(mapStringList, MemberDbm.getInstance());

        // ## Assert ##
        assertHasAnyElement(memberList);
        memberList.forEach(member -> {
            assertEquals(expectedMember.getMemberId(), member.getMemberId());
            assertEquals(expectedMember.getMemberName(), member.getMemberName());
        });
    }

    public void test_toEntity_list_basic() {
        // ## Arrange ##
        Member expectedMember = new Member();
        expectedMember.setMemberId(111);
        expectedMember.setMemberName("TEST");

        MemberDbm dbm = MemberDbm.getInstance();
        Map<String, Object> allColumnMap = dbm.extractAllColumnMap(expectedMember);
        KvsCacheConverterHandler kvsCacheConverterHandler = new KvsCacheConverterHandler();
        List<String> value = allColumnMap.values().stream().map(v -> Objects.toString(v, null)).collect(Collectors.toList());
        Set<String> specifiedColumnDbNames = DfCollectionUtil.newLinkedHashSet(allColumnMap.keySet());
        // ## Act ##
        Member member = kvsCacheConverterHandler.toEntity(value, dbm, specifiedColumnDbNames);

        // ## Assert ##
        assertEquals(expectedMember.getMemberId(), member.getMemberId());
        assertEquals(expectedMember.getMemberName(), member.getMemberName());
    }
}
