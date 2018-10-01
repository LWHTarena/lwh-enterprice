package com.lwhtarena.company.log.obj;

import org.apache.log4j.Level;

/**
 * @Author：liwh
 * @Description:
 * @Date 21:04 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class CustomLevel extends Level {

    public CustomLevel(int level, String name, int sysLogLevel) {
        super(level, name, sysLogLevel);
    }
}
