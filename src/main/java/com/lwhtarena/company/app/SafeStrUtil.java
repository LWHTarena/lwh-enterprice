package com.lwhtarena.company.app;

import com.lwhtarena.company.ip.util.IPUtil;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @Author：liwh
 * @Description:
 * @Date 22:45 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class SafeStrUtil {
    public static String getstr(ResourceBundleMessageSource messageSource, HttpServletRequest request, String key) {
        String safeSessionStr = messageSource.getMessage("session.safe.str", (Object[])null, "_lerx_ip_server_key_", (Locale)null).toLowerCase();
        String curIP = IPUtil.getRealRemotIP(request);
        safeSessionStr = safeSessionStr.replace("ip", curIP);
        safeSessionStr = safeSessionStr.replace("server", request.getServerName());
        if (key != null) {
            if (key.trim().equals("") || key.trim().equalsIgnoreCase("null")) {
                key = "default";
            }
        } else {
            key = "default";
        }

        safeSessionStr = safeSessionStr.replace("key", key);
        return safeSessionStr;
    }
}
