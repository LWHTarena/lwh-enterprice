package com.lwhtarena.company.sys.util;


import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.convert.LegacyListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;

import java.io.File;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:22 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class ConfigReader {

    private static FileHandler load(PropertiesConfiguration pc, String fileName) throws ConfigurationException {
        FileHandler handler = new FileHandler(pc);
        handler.setFileName(fileName);
        handler.load();
        return handler;
    }

    public static String getKey(File file, String key) {
        key = StringUtil.clear65279(key);
        PropertiesConfiguration conf = new PropertiesConfiguration();
        conf.setListDelimiterHandler(new LegacyListDelimiterHandler(','));
        String ap = file.getAbsolutePath();
        try {
            load(conf, ap);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        String val = conf.getString(key);
        return val;
    }
}
