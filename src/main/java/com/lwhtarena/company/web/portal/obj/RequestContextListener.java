package com.lwhtarena.company.web.portal.obj;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

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
public class RequestContextListener implements ServletRequestListener {

    private static final String REQUEST_ATTRIBUTES_ATTRIBUTE = RequestContextListener.class.getName() + ".REQUEST_ATTRIBUTES";

    @Override
    public void requestDestroyed(ServletRequestEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void requestInitialized(ServletRequestEvent requestEvent) {
        if (!(requestEvent.getServletRequest() instanceof HttpServletRequest)) {

            throw new IllegalArgumentException(

                    "Request is not an HttpServletRequest: " + requestEvent.getServletRequest());

        }

        HttpServletRequest request = (HttpServletRequest) requestEvent.getServletRequest();

        ServletRequestAttributes attributes = new ServletRequestAttributes(request);

        request.setAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE, attributes);

        LocaleContextHolder.setLocale(request.getLocale());

        RequestContextHolder.setRequestAttributes(attributes);

    }

}
