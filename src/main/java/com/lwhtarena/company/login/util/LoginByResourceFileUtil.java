package com.lwhtarena.company.login.util;

import com.lwhtarena.company.ip.util.IPUtil;
import com.lwhtarena.company.login.val.LoginSignUnitValues;
import com.lwhtarena.company.sys.util.ConfigReader;
import com.lwhtarena.company.sys.util.StringUtil;
import com.lwhtarena.company.sys.util.SysUtil;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
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
public class LoginByResourceFileUtil {

    public static String login(ResourceBundleMessageSource messageSource,
                               HttpServletRequest request,
                               HttpSession session,
                               String username,
                               String password) {
        File configFile = SysUtil.getConfigFile(messageSource, "app");
        String curIP = IPUtil.getRealRemotIP(request);
        String ipScope = ConfigReader.getKey(configFile, "login.configer.ip.allow");
        ipScope = StringUtil.strReplace(ipScope, "|", ",");
        String builtinUsername = ConfigReader.getKey(configFile, "\ufeff\ufeffdefault.admin.username");
        String builtinPassword = ConfigReader.getKey(configFile, "default.admin.password");
        if (builtinUsername == null) {
            builtinUsername = "error";
        }

        if (builtinPassword == null) {
            builtinPassword = "error";
        }

        boolean con = true;
        String errStr = null;
        LoginSignUnitValues lsuv = LoginUtil.read(session);
        if (!IPUtil.isInRange(curIP, ipScope)) {
            con = false;
            errStr = messageSource.getMessage("fail.ip.range.outside", (Object[])null, "The IP address that is currently used is not within the permitted range!", (Locale)null);
            return errStr;
        } else if (!con || builtinUsername.trim().equalsIgnoreCase(username) && builtinPassword.trim().equalsIgnoreCase(password)) {
            if (con) {
                lsuv = new LoginSignUnitValues();
                lsuv.setAdminID("0");
                LoginUtil.save(session, lsuv);
                return "success";
            } else {
                if (errStr == null || errStr.trim().equals("")) {
                    errStr = "fail";
                }

                return errStr;
            }
        } else {
            con = false;
            errStr = messageSource.getMessage("fail.login", (Object[])null, "Login failed, please reenter the correct username and password!", (Locale)null);
            return errStr;
        }
    }
}
