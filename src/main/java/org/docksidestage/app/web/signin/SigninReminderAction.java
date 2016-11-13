package org.docksidestage.app.web.signin;

import org.docksidestage.app.web.base.NonrdbBaseAction;
import org.lastaflute.web.Execute;
import org.lastaflute.web.response.HtmlResponse;

/**
 * @author masaki.kamachi
 * @author jflute
 */
public class SigninReminderAction extends NonrdbBaseAction {

    // #pending now making...
    @Execute
    public HtmlResponse index() {
        return asHtml(path_Signin_SigninReminderHtml);
    }
}
