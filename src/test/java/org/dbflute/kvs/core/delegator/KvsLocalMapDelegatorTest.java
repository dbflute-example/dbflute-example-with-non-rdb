/*
 * Copyright(c) u-next.
 */
package org.dbflute.kvs.core.delegator;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.dbflute.util.DfCollectionUtil;
import org.docksidestage.unit.UnitNonrdbTestCase;
import org.lastaflute.core.time.TimeManager;

public class KvsLocalMapDelegatorTest extends UnitNonrdbTestCase {

    @Resource
    private TimeManager timeManager;

    public void testExpireDateTime() {
        // ## Arrange ##
        LocalDateTime expireDateTime = timeManager.currentDateTime().plusMinutes(1);
        KvsLocalMapDelegator kvsLocalMapDelegator = new KvsLocalMapDelegator();

        // ## Act ##
        kvsLocalMapDelegator.registerString("key_string", "value_string", expireDateTime);
        kvsLocalMapDelegator.registerMultiString(DfCollectionUtil.newLinkedHashMap("key_multi_string", "value_multi_string"),
                expireDateTime);
        kvsLocalMapDelegator.registerList("key_list", DfCollectionUtil.newArrayList("value_list"), expireDateTime);
        kvsLocalMapDelegator.registerMultiList(
                DfCollectionUtil.newLinkedHashMap("key_multi_list", DfCollectionUtil.newArrayList("value_multi_list")), expireDateTime);
        kvsLocalMapDelegator.registerHash("key_hash", DfCollectionUtil.newLinkedHashMap("field", "value_hash"), expireDateTime);
        kvsLocalMapDelegator.registerMultiHash(
                DfCollectionUtil.newLinkedHashMap("key_multi_hash", DfCollectionUtil.newLinkedHashMap("field", "value_multi_hash")),
                expireDateTime);

        String valueString = kvsLocalMapDelegator.findString("key_string");
        List<String> valueMultiString = kvsLocalMapDelegator.findMultiString(DfCollectionUtil.newArrayList("key_multi_string"));
        List<String> valueList = kvsLocalMapDelegator.findList("key_list");
        List<List<String>> valueMultiList = kvsLocalMapDelegator.findMultiList(DfCollectionUtil.newArrayList("key_multi_list"));
        List<String> valueHash = kvsLocalMapDelegator.findHash("key_hash", DfCollectionUtil.newLinkedHashSet("field"));
        List<List<String>> valueMultiHash = kvsLocalMapDelegator.findMultiHash(DfCollectionUtil.newArrayList("key_multi_hash"),
                DfCollectionUtil.newLinkedHashSet("field"));

        // ## Assert ##
        assertEquals("value_string", valueString);
        assertEquals(DfCollectionUtil.newArrayList("value_multi_string"), valueMultiString);
        assertEquals(DfCollectionUtil.newArrayList("value_list"), valueList);
        assertEquals(DfCollectionUtil.newArrayList((Object) DfCollectionUtil.newArrayList("value_multi_list")), valueMultiList);
        assertEquals(DfCollectionUtil.newArrayList("value_hash"), valueHash);
        assertEquals(DfCollectionUtil.newArrayList((Object) DfCollectionUtil.newArrayList("value_multi_hash")), valueMultiHash);
    }

    public void testNotExpireDateTime() {
        // ## Arrange ##
        LocalDateTime expireDateTime = timeManager.currentDateTime().plusMinutes(-1);
        KvsLocalMapDelegator kvsLocalMapDelegator = new KvsLocalMapDelegator();

        // ## Act ##
        kvsLocalMapDelegator.registerString("key_string", "value_string", expireDateTime);
        kvsLocalMapDelegator.registerMultiString(DfCollectionUtil.newLinkedHashMap("key_multi_string", "value_multi_string"),
                expireDateTime);
        kvsLocalMapDelegator.registerList("key_list", DfCollectionUtil.newArrayList("value_list"), expireDateTime);
        kvsLocalMapDelegator.registerMultiList(
                DfCollectionUtil.newLinkedHashMap("key_multi_list", DfCollectionUtil.newArrayList("value_multi_list")), expireDateTime);
        kvsLocalMapDelegator.registerHash("key_hash", DfCollectionUtil.newLinkedHashMap("field", "value_hash"), expireDateTime);
        kvsLocalMapDelegator.registerMultiHash(
                DfCollectionUtil.newLinkedHashMap("key_multi_hash", DfCollectionUtil.newLinkedHashMap("field", "value_multi_hash")),
                expireDateTime);

        String valueString = kvsLocalMapDelegator.findString("key_string");
        List<String> valueMultiString = kvsLocalMapDelegator.findMultiString(DfCollectionUtil.newArrayList("key_multi_string"));
        List<String> valueList = kvsLocalMapDelegator.findList("key_list");
        List<List<String>> valueMultiList = kvsLocalMapDelegator.findMultiList(DfCollectionUtil.newArrayList("key_multi_list"));
        List<String> valueHash = kvsLocalMapDelegator.findHash("key_hash", DfCollectionUtil.newLinkedHashSet("field"));
        List<List<String>> valueMultiHash = kvsLocalMapDelegator.findMultiHash(DfCollectionUtil.newArrayList("key_multi_hash"),
                DfCollectionUtil.newLinkedHashSet("field"));

        // ## Assert ##
        assertNull(valueString);
        assertEquals(DfCollectionUtil.newArrayList((Object) null), valueMultiString);
        assertEquals(0, valueList.size());
        assertEquals(1, valueMultiList.size());
        assertEquals(0, valueMultiList.get(0).size());
        assertEquals(DfCollectionUtil.newArrayList((Object) null), valueHash);
        assertEquals(DfCollectionUtil.newArrayList((Object) DfCollectionUtil.newArrayList((Object) null)), valueMultiHash);
    }
}
