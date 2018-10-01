package com.lwhtarena.company.log.util;

import com.lwhtarena.company.Interface.ICustomLogLevel;
import org.apache.log4j.Logger;

/**
 * @Author：liwh
 * @Description:
 * @Date 21:05 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class CustomLogger {
    private Logger log;

    public void custom(Object pm_objLogInfo) {
        this.log = Logger.getLogger(getClass());
        try {
            this.log.log(ICustomLogLevel.CUSTOM_LEVEL, pm_objLogInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
