package org.docksidestage.app.web.signup;

import java.util.Random;

import javax.annotation.Resource;

import org.docksidestage.app.web.base.NonrdbBaseAction;
import org.docksidestage.app.web.base.login.NonrdbLoginAssist;
import org.docksidestage.app.web.mypage.MypageAction;
import org.docksidestage.app.web.signin.SigninAction;
import org.docksidestage.dbflute.exbhv.MemberBhv;
import org.docksidestage.dbflute.exbhv.MemberSecurityBhv;
import org.docksidestage.dbflute.exbhv.MemberServiceBhv;
import org.docksidestage.dbflute.exentity.Member;
import org.docksidestage.dbflute.exentity.MemberSecurity;
import org.docksidestage.dbflute.exentity.MemberService;
import org.docksidestage.mylasta.action.NonrdbMessages;
import org.docksidestage.mylasta.direction.NonrdbConfig;
import org.docksidestage.mylasta.mail.member.WelcomeMemberPostcard;
import org.lastaflute.core.mail.Postbox;
import org.lastaflute.core.security.PrimaryCipher;
import org.lastaflute.core.util.LaStringUtil;
import org.lastaflute.web.Execute;
import org.lastaflute.web.response.HtmlResponse;
import org.lastaflute.web.servlet.request.ResponseManager;
import org.lastaflute.web.servlet.session.SessionManager;

/**
 * @author annie_pocket
 * @author jflute
 */
public class SignupAction extends NonrdbBaseAction {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final String SIGNUP_TOKEN_KEY = "signupToken";

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private PrimaryCipher primaryCipher;
    @Resource
    private Postbox postbox;
    @Resource
    private SessionManager sessionManager;
    @Resource
    private ResponseManager responseManager;
    @Resource
    private NonrdbConfig config;
    @Resource
    private MemberBhv memberBhv;
    @Resource
    private MemberSecurityBhv memberSecurityBhv;
    @Resource
    private MemberServiceBhv memberServiceBhv;
    @Resource
    private NonrdbLoginAssist loginAssist;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @Execute
    public HtmlResponse index() {
        return asHtml(path_Signup_SignupHtml).useForm(SignupForm.class);
    }

    @Execute
    public HtmlResponse signup(SignupForm form) {
        validate(form, messages -> moreValidate(form, messages), () -> {
            return asHtml(path_Signup_SignupHtml);
        });
        Integer memberId = insertProvisionalMember(form);
        loginAssist.identityLogin(memberId, op -> {}); // no remember-me here

        String signupToken = saveSignupToken();
        sendSignupMail(form, signupToken);
        return redirect(MypageAction.class);
    }

    private void moreValidate(SignupForm form, NonrdbMessages messages) {
        if (LaStringUtil.isNotEmpty(form.memberAccount)) {
            int count = memberBhv.selectCount(cb -> {
                cb.query().setMemberAccount_Equal(form.memberAccount);
            });
            if (count > 0) {
                messages.addErrorsSignupAccountAlreadyExists("memberAccount");
            }
        }
    }

    private String saveSignupToken() {
        String token = primaryCipher.encrypt(String.valueOf(new Random().nextInt())); // #simple_for_example
        sessionManager.setAttribute(SIGNUP_TOKEN_KEY, token);
        return token;
    }

    private void sendSignupMail(SignupForm form, String signupToken) {
        WelcomeMemberPostcard.droppedInto(postbox, postcard -> {
            postcard.setFrom(config.getMailAddressSupport(), "Nonrdb Support"); // #simple_for_example
            postcard.addTo(form.memberAccount + "@docksidestage.org"); // #simple_for_example
            postcard.setDomain(config.getServerDomain());
            postcard.setMemberName(form.memberName);
            postcard.setAccount(form.memberAccount);
            postcard.setToken(signupToken);
        });
    }

    @Execute
    public HtmlResponse formalize(String account, String token) { // from mail link
        verifySignupTokenMatched(account, token);
        updateMemberAsFormalized(account);
        return redirect(SigninAction.class);
    }

    private void verifySignupTokenMatched(String account, String token) {
        String saved = sessionManager.getAttribute(SIGNUP_TOKEN_KEY, String.class).orElseTranslatingThrow(cause -> {
            return responseManager.new404("Not found the signupToken in session: " + account, op -> op.cause(cause));
        });
        if (!saved.equals(token)) {
            throw responseManager.new404("Unmatched signupToken in session: saved=" + saved + ", requested=" + token);
        }
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    private Integer insertProvisionalMember(SignupForm form) {
        Member member = new Member();
        member.setMemberName(form.memberName);
        member.setMemberAccount(form.memberAccount);
        member.setMemberStatusCode_Provisional();
        memberBhv.insert(member); // #simple_for_example same-name concurrent access as application exception

        MemberSecurity security = new MemberSecurity();
        security.setMemberId(member.getMemberId());
        security.setLoginPassword(loginAssist.encryptPassword(form.password));
        security.setReminderQuestion(form.reminderQuestion);
        security.setReminderAnswer(form.reminderAnswer);
        security.setReminderUseCount(0);
        memberSecurityBhv.insert(security);

        MemberService service = new MemberService();
        service.setMemberId(member.getMemberId());
        service.setServicePointCount(0);
        service.setServiceRankCode_Plastic();
        memberServiceBhv.insert(service);
        return member.getMemberId();
    }

    private void updateMemberAsFormalized(String account) {
        Member member = new Member();
        member.uniqueBy(account);
        member.setMemberStatusCode_Formalized();
        memberBhv.updateNonstrict(member);
    }
}
