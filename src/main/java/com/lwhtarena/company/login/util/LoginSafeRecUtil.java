package com.lwhtarena.company.login.util;

import com.lwhtarena.company.sys.obj.LoginMark;
import com.lwhtarena.company.sys.obj.LoginSessionTest;
import com.lwhtarena.company.sys.util.HttpUtil;
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
public class LoginSafeRecUtil {

    public static LoginMark covStrToLsr(String str) {
        LoginMark lsr = new LoginMark();
        String[] sArray = str.split("_");
        lsr.setFailn(Integer.valueOf(sArray[0]));
        lsr.setTimepos(Long.valueOf(sArray[1]));
        if (Integer.valueOf(sArray[2]) == 1) {
            lsr.setCon(true);
        } else {
            lsr.setCon(false);
        }

        return lsr;
    }

    public static String covLsrToStr(LoginMark lsr) {
        byte con;
        if (lsr != null && !lsr.isCon()) {
            con = 0;
        } else {
            con = 1;
        }

        if (lsr == null) {
            lsr = new LoginMark();
            lsr.setFailn(1);
            lsr.setTimepos(System.currentTimeMillis());
            lsr.setCon(true);
        }

        return lsr.getFailn() + "_" + lsr.getTimepos() + "_" + con;
    }

    public static LoginMark chk(LoginMark lsr, ResourceBundleMessageSource messageSource) {
        return chk(lsr, messageSource.getMessage("login.upper.failed", (Object[])null, "5", (Locale)null), messageSource.getMessage("login.minutes.wait.afterFailed", (Object[])null, "10", (Locale)null));
    }

    public static LoginMark chk(LoginMark lsr, String upperStr, String minutesStr) {
        long now = System.currentTimeMillis();
        if (lsr != null) {
            int upper;
            if (upperStr != null && !upperStr.trim().equals("")) {
                upper = Integer.valueOf(upperStr);
            } else {
                upper = 0;
            }

            int waitMinutes;
            if (minutesStr != null && !minutesStr.trim().equals("")) {
                waitMinutes = Integer.valueOf(minutesStr);
            } else {
                waitMinutes = 0;
            }

            long span = now - lsr.getTimepos();
            if (span < (long)('\uea60' * waitMinutes)) {
                if (lsr.getTimepos() == 0L) {
                    lsr.setTimepos(now);
                }

                lsr.setFailn(lsr.getFailn() + 1);
                if (lsr.getFailn() >= upper) {
                    if (lsr.isCon()) {
                        lsr.setCon(false);
                        lsr.setTimepos(now);
                    }
                } else {
                    lsr.setCon(true);
                }
            }
        } else {
            lsr = new LoginMark();
            lsr.setFailn(1);
            lsr.setTimepos(now);
            lsr.setCon(true);
        }

        return lsr;
    }

    public static LoginSessionTest interruptTest(String upperStr, String minutesStr, String location) {
        LoginSessionTest lst = new LoginSessionTest();
        LoginMark lsr = null;
        HttpSession session = HttpUtil.currSession();
        String lsrStr = (String)session.getAttribute("lsr_" + location);
        if (lsrStr != null && !lsrStr.trim().equals("")) {
            lsr = covStrToLsr(lsrStr);
        }

        lsr = chk(lsr, upperStr, minutesStr);
        lsrStr = covLsrToStr(lsr);
        session.setAttribute("lsr_" + location, lsrStr);
        lst.setLsr(lsr);
        lst.setLocation(location);
        lst.setLsrStr(lsrStr);
        if (lsr != null && !lsr.isCon()) {
            lst.setInterrup(true);
        } else {
            lst.setInterrup(false);
        }

        return lst;
    }
}
