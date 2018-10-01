package com.lwhtarena.company.sys.util;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:35 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class HttpUtil {

    public HttpUtil() {
    }

    public static HttpServletRequest currRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }

    public static HttpServletResponse currResponse() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletResponse response = sra.getResponse();
        return response;
    }

    public static HttpSession currSession() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();
        HttpSession session = request.getSession();
        return session;
    }

    public static String buildRequest(HttpServletRequest request) {
        Map<String, String> parmMap = new HashMap();
        Map<String, String[]> map = request.getParameterMap();
        Set<String> key = map.keySet();
        Iterator iterator = key.iterator();

        while(iterator.hasNext()) {
            String k = (String)iterator.next();
            parmMap.put(k, ((String[])map.get(k))[0]);
            System.out.println("1" + k + " ：" + ((String[])map.get(k))[0]);
        }

        System.out.println("1parmMap=====" + parmMap.toString());
        Enumeration<String> a = request.getParameterNames();
        String parm = null;
        String val = "";

        while(a.hasMoreElements()) {
            parm = (String)a.nextElement();
            val = request.getParameter(parm);
            parmMap.put(parm, val);
            System.out.println("2" + parm + " ：" + val);
        }

        System.out.println("2parmMap==========" + parmMap);
        return "";
    }

    public static String buildToken() {
        HttpServletRequest request = currRequest();
        String token = StringUtil.uuidStr();
        request.getSession().setAttribute("token", token);
        request.setAttribute("token", token);
        return token;
    }

    public static String getTokenFromParameter(HttpServletRequest request) {
        if (request == null) {
            request = currRequest();
        }

        String token = request.getParameter("token");
        return token;
    }

    public static String getTokenFromSession() {
        HttpServletRequest request = currRequest();
        String token = (String)request.getSession().getAttribute("token");
        return token;
    }

    public static void clearToken() {
        HttpServletRequest request = currRequest();
        request.getSession().removeAttribute("token");
        request.removeAttribute("token");
    }

    public static String getCookie(ResourceBundleMessageSource messageSource, HttpServletRequest request, String key) {
        if (request == null) {
            request = currRequest();
        }

        String v = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            String cookieDomain = messageSource.getMessage("cookie.domain", (Object[])null, "", (Locale)null);

            for(int i = 0; i < cookies.length; ++i) {
                Cookie c = cookies[i];
                if (cookieDomain != null && !cookieDomain.trim().equals("") && !cookieDomain.trim().equalsIgnoreCase("null")) {
                    c.setPath("/");
                    c.setDomain("." + cookieDomain);
                }

                String n = c.getName();
                if (n.trim().equals(key)) {
                    v = c.getValue();
                    if (v != null && !v.trim().equals("")) {
                        return v;
                    }
                }
            }

            if (v != null && v.trim().equals("")) {
                v = null;
            }

            return v;
        } else {
            return null;
        }
    }

    public static void saveCookie(ResourceBundleMessageSource messageSource, HttpServletResponse response, String k, String v) {
        if (response == null) {
            response = currResponse();
        }

        String cookieDomain = messageSource.getMessage("cookie.domain", (Object[])null, "", (Locale)null);
        int maxage = Integer.valueOf(messageSource.getMessage("cookies.times", (Object[])null, "-1", (Locale)null));
        Cookie c = new Cookie(k, v);
        if (cookieDomain != null && !cookieDomain.trim().equals("") && !cookieDomain.trim().equalsIgnoreCase("null")) {
            c.setDomain("." + cookieDomain);
        }

        c.setPath("/");
        c.setMaxAge(maxage);
        response.addCookie(c);
    }

    public static void clearCookie(HttpServletRequest request, HttpServletResponse response, String key) {
        if (request == null) {
            request = currRequest();
        }

        if (response == null) {
            response = currResponse();
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for(int i = 0; i < cookies.length; ++i) {
                Cookie c = cookies[i];
                String n = c.getName();
                if (n.trim().equals(key)) {
                    c.setMaxAge(0);
                    c.setPath("/");
                    response.addCookie(c);
                }
            }
        }

    }

    public static String getSrvUrl(HttpServletRequest request) {
        String url = "";
        url = url + request.getScheme();
        if ((!url.trim().equals("http") || request.getServerPort() != 80) && (!url.trim().equals("https") || request.getServerPort() != 443)) {
            url = url + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        } else {
            url = url + "://" + request.getServerName() + request.getContextPath();
        }

        return url;
    }
}
