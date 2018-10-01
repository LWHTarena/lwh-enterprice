package com.lwhtarena.company.aop;

import com.lwhtarena.company.annotation.Token;
import com.lwhtarena.company.log.util.LogWrite;
import com.lwhtarena.company.login.util.LoginUtil;
import com.lwhtarena.company.sys.util.HttpUtil;
import com.lwhtarena.company.sys.util.MavUtil;
import com.lwhtarena.company.sys.util.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * <p>
 * <h2>简述：</h2>
 * <ol></ol>
 * <h2>功能描述：</h2>
 * <ol></ol>
 * </p>
 *
 * @Author: liwh
 * @Date :
 * @Version: 版本
 */

@Aspect
@Component
public class TokenAspect {

    @Autowired
    private ResourceBundleMessageSource messageSource;
    @Autowired
    private HttpServletRequest request;

    public TokenAspect() {
    }

    @Around("execution(* *..handlers..*.*(..)) && @annotation(annotation)")
    public Object around(ProceedingJoinPoint pjp, Token annotation) throws Throwable {
        boolean ajax = annotation.ajax();
        boolean token = annotation.token();
        boolean admin = annotation.admin();
        boolean login = annotation.login();
        boolean admin0 = annotation.admin0();
        boolean role0 = annotation.role0();
        boolean loginOrAdmin = annotation.loginOrAdmin();
        HttpSession session = HttpUtil.currSession();
        if (this.request == null) {
            this.request = HttpUtil.currRequest();
        }

        String msgKey = annotation.msgKey();
        String failedPage = annotation.failedPage();
        String msg = null;
        if (failedPage.equals("") || failedPage.trim().equals("")) {
            failedPage = null;
        }

        if (failedPage != null) {
            failedPage = failedPage.trim();
            failedPage = MavUtil.adminUrlChk(this.messageSource, failedPage);
        }

        String tokenParameter = HttpUtil.getTokenFromParameter(this.request);
        String tokenSession = HttpUtil.getTokenFromSession();
        int result = 0;
        if (token) {
            if (tokenSession == null || tokenSession.trim().equals("")) {
                result = -8;
                if (msgKey == null || msgKey.trim().equals("")) {
                    msg = this.messageSource.getMessage("err.session.timeout", (Object[])null, "Session timeout!", (Locale)null);
                }
            }

            if (result == 0 && (tokenParameter == null || tokenParameter.trim().equals(""))) {
                result = -7;
                if (msgKey == null || msgKey.trim().equals("")) {
                    msg = this.messageSource.getMessage("err.form.illegal", (Object[])null, "Illegal sources!", (Locale)null);
                }
            }

            if (result == 0 && !tokenSession.trim().equals(tokenParameter.trim())) {
                result = -7;
                if (msgKey == null || msgKey.trim().equals("")) {
                    msg = this.messageSource.getMessage("err.form.illegal", (Object[])null, "Illegal sources!", (Locale)null);
                }
            }

            if (failedPage == null) {
                failedPage = "jsp/warn/resubmit";
            }

            HttpUtil.clearToken();
        }

        if (failedPage == null) {
            failedPage = "jsp/warn/failed";
        }

        String mask;
        if (result == 0) {
            if (loginOrAdmin) {
                mask = HttpUtil.getCookie(this.messageSource, this.request, "uid_lerx");
                long uid;
                if (mask != null && !mask.trim().equals("") && StringUtil.isNumber(mask)) {
                    uid = Long.valueOf(mask);
                } else {
                    uid = 0L;
                }

                if (!LoginUtil.adminChk(this.messageSource, session) && uid <= 0L) {
                    result = -1;
                    if (msgKey == null || msgKey.trim().equals("")) {
                        msg = this.messageSource.getMessage("fail.permission", (Object[])null, "You have no permissions for this operation!", (Locale)null);
                    }
                }
            } else {
                if (admin && !LoginUtil.adminChk(this.messageSource, session)) {
                    result = -1;
                    if (msgKey == null || msgKey.trim().equals("")) {
                        msg = this.messageSource.getMessage("fail.permission", (Object[])null, "You have no permissions for this operation!", (Locale)null);
                    }
                }

                if (login) {
                    long uid = LoginUtil.uid(this.messageSource, this.request);
                    if (uid <= 0L) {
                        result = -1;
                        if (msgKey == null || msgKey.trim().equals("")) {
                            msg = this.messageSource.getMessage("fail.nologin", (Object[])null, "You must be logged in!", (Locale)null);
                        }
                    }
                }
            }
        }

        if (result == 0 && admin0 && !LoginUtil.admin0Chk(this.messageSource, session)) {
            result = -1;
            if (msgKey == null || msgKey.trim().equals("")) {
                msg = this.messageSource.getMessage("fail.permission", (Object[])null, "You have no permissions for this operation!", (Locale)null);
            }
        }

        if (result == 0 && role0) {
            mask = HttpUtil.getCookie(this.messageSource, this.request, "role0_lerx");
            if (mask == null || !mask.trim().equals("true")) {
                result = -1;
                if (msgKey == null || msgKey.trim().equals("")) {
                    msg = this.messageSource.getMessage("fail.permission", (Object[])null, "You have no permissions for this operation!", (Locale)null);
                }
            }
        }

        if (result == 0) {
            return pjp.proceed();
        } else if (ajax) {
            return -1;
        } else {
            Signature signature = pjp.getSignature();
            Class<?> returnType = ((MethodSignature)signature).getReturnType();
            if (failedPage == null || failedPage.trim().equals("")) {
                failedPage = "jsp/warn/resubmit";
            }

            if (returnType.getSimpleName().trim().equalsIgnoreCase("String")) {
                return failedPage;
            } else {
                if (msg == null || msg.trim().equals("")) {
                    msg = this.messageSource.getMessage("fail.universal", (Object[])null, "The operation failed!", (Locale)null);
                    msg = msg + "Err code:" + result;
                }

                return MavUtil.mav(failedPage, msg);
            }
        }
    }

    @AfterReturning(
            returning = "rtv",
            value = "execution(* *..handlers..*.*(..)) && @annotation(annotation)"
    )
    public void doAfterReturning(JoinPoint joinPoint, Token annotation, Object rtv) {
        boolean log = annotation.log();
        boolean login = annotation.login();
        boolean admin = annotation.admin();
        if (log) {
            String mark = annotation.mark();
            String logtxt = "";
            long uid = 0L;
            if (mark == null || mark.trim().equals("")) {
                mark = "<null>";
            }

            String lb = System.getProperty("line.separator");
            String targetName = joinPoint.getTarget().getClass().getName();
            logtxt = logtxt + "\ttarget:" + targetName + lb;
            String methodName = joinPoint.getSignature().getName();
            logtxt = logtxt + "\tmethod:" + methodName + lb;
            logtxt = logtxt + "\tmark:" + mark;
            HttpSession session = HttpUtil.currSession();
            if (login) {
                uid = LoginUtil.userChk(this.messageSource, session);
                logtxt = logtxt + ";login:true;uid<" + uid + ">";
            }

            if (admin) {
                String adminID = LoginUtil.getAdminID(this.messageSource, session);
                if (adminID == null) {
                    adminID = "null";
                }

                logtxt = logtxt + ";admin:true;adminID<" + adminID + ">";
            }

            logtxt = logtxt + lb;
            logtxt = logtxt + "\t{Args}:" + lb;
            Object[] arguments = joinPoint.getArgs();
            logtxt = logtxt + "\t\t{" + lb;

            for(int i = 0; i < arguments.length; ++i) {
                if (arguments[i] != null) {
                    logtxt = logtxt + "\t\t" + arguments[i].toString() + lb;
                }
            }

            logtxt = logtxt + "\t\t}" + lb;
            logtxt = logtxt + "\treturn:" + rtv.toString();
            logtxt = logtxt + lb;
            LogWrite.logWrite(HttpUtil.currRequest(), "info-proce-doAfterreturing " + lb + logtxt);
        }

    }
}
