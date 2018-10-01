package com.lwhtarena.company.log.util;

import com.lwhtarena.company.ip.util.IPUtil;
import com.lwhtarena.company.sys.obj.LogWriteRequestArgs;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author：liwh
 * @Description:
 * @Date 21:10 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class LogWrite {
    private static Logger logger = Logger.getLogger(LogWrite.class);

    public LogWrite() {
    }

    public static void logWrite(HttpServletRequest request, String msg) {
        if (request != null) {
            String lb = System.getProperty("line.separator");
            String info = "\thost:" + request.getRemoteHost() + lb + "\tpathinfo:" + request.getPathInfo() + lb + "\trequesturl:" + request.getRequestURI() + lb + "\tReferer:" + request.getHeader("Referer") + lb + "\tip:" + IPUtil.getRealRemotIP(request);
            CustomLogger clog = new CustomLogger();
            clog.custom(info + lb + "\tinfo:" + msg);
        }

    }

    public static void logWrite(LogWriteRequestArgs lwra, String msg) {
        if (lwra != null) {
            String lb = System.getProperty("line.separator");
            String info = "\thost:" + lwra.getRemoteHost() + lb + "\tpathinfo:" + lwra.getPathInfo() + lb + "\trequesturl:" + lwra.getRequestURI() + lb + "\tReferer:" + lwra.getReferer() + lb + "\tip:" + lwra.getIp();
            CustomLogger clog = new CustomLogger();
            clog.custom(info + lb + "\tinfo:" + msg);
        }

    }
}
