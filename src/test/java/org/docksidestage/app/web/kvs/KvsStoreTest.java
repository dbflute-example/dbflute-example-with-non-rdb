package org.docksidestage.app.web.kvs;

import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.dbflute.optional.OptionalEntity;
import org.docksidestage.kvs.store.examplestore.exbhv.KvsEgStoreExampleBhv;
import org.docksidestage.kvs.store.examplestore.exentity.KvsEgStoreExample;
import org.docksidestage.unit.UnitNonrdbTestCase;

/**
 * @author nagahori
 */
public class KvsStoreTest extends UnitNonrdbTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private KvsEgStoreExampleBhv kvsEgStoreExampleBhv;

    public void test_name() {
        // ## Arrange ##
        String key = "key";
        Integer id = 1;
        String name = "foo";
        LocalDateTime expireDatetime = currentLocalDateTime();

        // ## Act ##
        KvsEgStoreExample insertedEntity = kvsEgStoreExampleBhv.insertOrUpdate(() -> {
            KvsEgStoreExample entity = new KvsEgStoreExample();
            entity.setEgkey(key);
            entity.setEgId(id);
            entity.setEgName(name);
            entity.setExpireDatetime(expireDatetime);
            return entity;
        });
        String egkey = insertedEntity.getEgkey();
        OptionalEntity<KvsEgStoreExample> result = kvsEgStoreExampleBhv.selectEntity(cb -> {
            cb.acceptPK(egkey);
        });

        // ## Assert ##
        assertTrue(result.isPresent());

        // ## Act 2 ##
        kvsEgStoreExampleBhv.delete(() -> {
            KvsEgStoreExample entity = new KvsEgStoreExample();
            entity.setEgkey(egkey);
            return entity;
        });

        // ## Assert 2 ##
        assertFalse(kvsEgStoreExampleBhv.selectEntity(cb -> cb.acceptPK(egkey)).isPresent());
    }
}
