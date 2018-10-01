package com.lwhtarena.company.Interface;

import com.lwhtarena.company.log.obj.CustomLevel;
import org.apache.log4j.Level;

/**
 * @Author：liwh
 * @Description:
 * @Date 21:07 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface ICustomLogLevel {
    Level CUSTOM_LEVEL = new CustomLevel(49950, "CUSTOM", 128);
}
