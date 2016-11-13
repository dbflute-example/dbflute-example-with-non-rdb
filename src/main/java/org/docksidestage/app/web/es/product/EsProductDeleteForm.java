package org.docksidestage.app.web.es.product;

import org.lastaflute.web.validation.Required;

public class EsProductDeleteForm {
    @Required
    public String productId;
}
