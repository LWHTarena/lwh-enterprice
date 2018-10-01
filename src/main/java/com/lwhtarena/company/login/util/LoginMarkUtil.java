package com.lwhtarena.company.login.util;

import com.lwhtarena.company.sys.obj.LoginMark;
import org.springframework.context.support.ResourceBundleMessageSource;

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
public class LoginMarkUtil {
    public static void clean(ResourceBundleMessageSource messageSource, HttpSession session) {
        String key = messageSource.getMessage("login.failed.session.recstr", (Object[])null, "loginlsr", (Locale)null);
        session.removeAttribute(key);
    }

    public static LoginMark obtain(ResourceBundleMessageSource messageSource, HttpSession session) {
        LoginMark lm = null;
        String key = messageSource.getMessage("login.failed.session.recstr", (Object[])null, "loginlsr", (Locale)null);
        String lsrStr = (String)session.getAttribute(key);
        if (lsrStr != null && !lsrStr.trim().equals("")) {
            lm = covStrToLsr(lsrStr);
        }

        return lm;
    }

    public static LoginMark addErr(ResourceBundleMessageSource messageSource, HttpSession session, LoginMark lm) {
        lm.setFailn(lm.getFailn() + 1);
        String key = messageSource.getMessage("login.failed.session.recstr", (Object[])null, "loginlsr", (Locale)null);
        session.setAttribute(key, covLsrToStr(lm));
        return lm;
    }

    public static LoginMark chk(ResourceBundleMessageSource messageSource, LoginMark lm) {
        long now = System.currentTimeMillis();
        if (lm != null) {
            int upper = intValFromConfig(messageSource, "login.failed.times.upper", 5);
            int waitMinutes = intValFromConfig(messageSource, "login.failed.waiting.minutes", 10);
            long span = now - lm.getTimepos();
            if (span < (long)('\uea60' * waitMinutes)) {
                if (lm.getTimepos() == 0L) {
                    lm.setTimepos(now);
                }

                if (lm.getFailn() >= upper) {
                    lm.setCon(false);
                    lm.setTimepos(now);
                } else {
                    lm.setCon(true);
                }
            } else {
                lm = null;
            }
        }

        if (lm == null) {
            lm = new LoginMark();
            lm.setFailn(0);
            lm.setTimepos(now);
            lm.setCon(true);
        }

        return lm;
    }

    private static LoginMark covStrToLsr(String str) {
        LoginMark lm = new LoginMark();
        String[] sArray = str.split("_");
        lm.setFailn(Integer.valueOf(sArray[0]));
        lm.setTimepos(Long.valueOf(sArray[1]));
        if (Integer.valueOf(sArray[2]) == 1) {
            lm.setCon(true);
        } else {
            lm.setCon(false);
        }

        return lm;
    }

    private static String covLsrToStr(LoginMark lm) {
        byte con;
        if (lm.isCon()) {
            con = 1;
        } else {
            con = 0;
        }

        return lm.getFailn() + "_" + lm.getTimepos() + "_" + con;
    }

    private static int intValFromConfig(ResourceBundleMessageSource messageSource, String key, int def) {
        String valTmp = messageSource.getMessage(key, (Object[])null, "" + def, (Locale)null);
        int value;
        if (valTmp != null && !valTmp.trim().equals("")) {
            value = Integer.valueOf(valTmp);
        } else {
            value = 0;
        }

        return value;
    }
}
