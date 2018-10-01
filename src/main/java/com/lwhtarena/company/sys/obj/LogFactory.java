package com.lwhtarena.company.sys.obj;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:21 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class LogFactory {
    private static final Logger logger =Logger.getLogger("stdout");

    static{
        logger.setLevel(Level.DEBUG);
    }

    public static void log(String info,Level level,Throwable ex){
        logger.log(level,info,ex);
    }

    public static Level getLogLevel(){
        return logger.getLevel();
    }
}
