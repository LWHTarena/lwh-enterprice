package com.lwhtarena.company.sys.util;

import org.springframework.context.support.ResourceBundleMessageSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class ContextUtil {

    private static ResourceBundleMessageSource messageSource;
    private HttpServletRequest requst;
    private HttpServletResponse response;

    public static String getText(String key) {
        if (messageSource == null) {
            messageSource = new ResourceBundleMessageSource();
        }

        return messageSource.getMessage(key, (Object[])null, "0", (Locale)null);
    }

    public static ResourceBundleMessageSource getMessageSource() {
        return messageSource;
    }

    public static void setMessageSource(ResourceBundleMessageSource messageSource) {
        ContextUtil.messageSource = messageSource;
    }

    public HttpServletRequest getRequst() {
        return requst;
    }

    public void setRequst(HttpServletRequest requst) {
        this.requst = requst;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
