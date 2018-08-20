package org.docksidestage.app.web.kvs;

import java.util.List;

import javax.annotation.Resource;

import org.dbflute.optional.OptionalEntity;
import org.docksidestage.dbflute.allcommon.CDef.ProductCategory;
import org.docksidestage.dbflute.allcommon.CDef.ProductStatus;
import org.docksidestage.dbflute.exentity.Product;
import org.docksidestage.kvs.cache.maihamadb.exbhv.KvsProductBhv;
import org.docksidestage.unit.UnitNonrdbTestCase;

/**
 * @author nagahori
 */
public class KvsCacheTest extends UnitNonrdbTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private KvsProductBhv kvsProductBhv;

    public void test_productId() {
        // ## Arrange ##
        String productName = "somethingCool";
        String productHandleCode = "XXXXXXXXXX";
        Integer price = 1000;

        // ## Act ##
        Product insertedProduct = kvsProductBhv.insertOrUpdateByProductId(() -> {
            Product product = new Product();
            product.setProductName(productName);
            product.setProductCategoryCode(ProductCategory.Music.code());
            product.setProductStatusCode_OnSaleProduction();
            product.setProductHandleCode(productHandleCode);
            product.setRegularPrice(price);
            return product;
        });

        Integer productId = insertedProduct.getProductId();
        OptionalEntity<Product> result = kvsProductBhv.selectEntityByProductId(cb -> {
            cb.query().setProductId_Equal(productId);
        });

        // ## Assert ##
        assertTrue(result.isPresent());

        // ## Act 2 ##
        kvsProductBhv.deleteByProductId(() -> {
            Product product = new Product();
            product.setProductId(productId);
            return product;
        });

        // ## Assert 2 ##
        OptionalEntity<Product> resultAfterDelete = kvsProductBhv.selectEntityByProductId(cb -> {
            cb.query().setProductId_Equal(productId);
        });
        assertFalse(resultAfterDelete.isPresent());
    }

    public void test_productCategoryCode() {
        // ## Arrange ##
        String categoryCode = ProductCategory.Instruments.code();
        String statusCode = ProductStatus.OnSaleProduction.code();

        String productName = "something";
        String productHandleCode = "XXXXXXXXXX";
        Integer price = 1000;

        // ## Act ##
        kvsProductBhv.insertOrUpdateByCategoryCode(() -> {
            Product product = new Product();
            product.setProductName(productName);
            product.setProductHandleCode(productHandleCode);
            product.setProductCategoryCode(categoryCode);
            product.setProductStatusCode_OnSaleProduction();
            product.setRegularPrice(price);
            return product;
        });
        List<Product> productList = kvsProductBhv.selectListByCategoryCode(cb -> {
            cb.query().setProductCategoryCode_Equal(categoryCode);
            cb.query().setProductStatusCode_Equal(statusCode);
            cb.query().addOrderBy_RegisterDatetime_Desc();
        });

        // ## Assert ##
        assertNotNull(productList);
        productList.forEach(product -> {
            assertEquals(categoryCode, product.getProductCategoryCode());
            assertEquals(statusCode, product.getProductStatusCode());
            log(product.getProductName(), product.getProductHandleCode(), product.getRegularPrice());
        });
    }
}
