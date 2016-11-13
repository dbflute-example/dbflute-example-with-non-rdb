package org.docksidestage.app.web.product;

import org.dbflute.optional.OptionalThing;
import org.dbflute.utflute.lastaflute.mock.TestingHtmlData;
import org.docksidestage.unit.UnitNonrdbTestCase;
import org.lastaflute.web.response.HtmlResponse;

/**
 * @author jflute
 */
public class ProductListActionTest extends UnitNonrdbTestCase {

    public void test_index_success_by_productName() throws Exception {
        // ## Arrange ##
        ProductListAction action = new ProductListAction();
        inject(action);
        ProductSearchForm form = new ProductSearchForm();
        form.productName = "a";

        // ## Act ##
        HtmlResponse response = action.index(OptionalThing.of(2), form);

        // ## Assert ##
        TestingHtmlData htmlData = validateHtmlData(response);
        htmlData.requiredList("beans", ProductSearchRowBean.class).forEach(bean -> {
            log(bean);
            assertContainsIgnoreCase(bean.productName, form.productName);
        });
    }
}
