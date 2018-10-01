package com.lwhtarena.company.sys.util;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

/**
 * @Author：liwh
 * @Description:
 * @Date 12:58 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class MavUtil {

    public static ModelAndView mav(String viewPage, String msg) {
        ModelAndView mav = new ModelAndView(viewPage);
        mav.addObject("msg", msg);
        return mav;
    }

    public static ModelAndView mav(String viewPage, String msg, boolean back) {
        ModelAndView mav = new ModelAndView(viewPage);
        mav.addObject("msg", msg);
        if (back) {
            mav.addObject("back", true);
        }

        return mav;
    }

    public static String adminUrlChk(ResourceBundleMessageSource messageSource, String url) {
        if (url != null && !url.trim().equals("")) {
            if (url.startsWith("_") && url.endsWith("_")) {
                url = url.substring(1, url.length() - 1);
                url = messageSource.getMessage(url, (Object[]) null, "null_key_" + url, (Locale) null);
            }

            String adminFolder = messageSource.getMessage("folder.admin", (Object[]) null, "admin", (Locale) null);
            url = StringUtil.strReplace(url, "{folder.admin}", adminFolder);
            return url;
        } else {
            return url;
        }
    }
}
