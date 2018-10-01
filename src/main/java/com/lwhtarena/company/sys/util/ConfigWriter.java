package com.lwhtarena.company.sys.util;

import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.*;
import java.util.Properties;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:28 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class ConfigWriter {
    public static void writeKey(File file, String key, String value)
            throws ConfigurationException, IOException {
        if (StringUtil.isNull(file)) {
            return;
        }
        Properties prop = new Properties();

        FileInputStream fs = new FileInputStream(file);
        InputStream in = new BufferedInputStream(fs);
        prop.load(in);

        FileOutputStream fos = new FileOutputStream(file, false);
        prop.setProperty(key, value);
        prop.store(fos, file.getAbsolutePath());
        fs.close();
        in.close();
        fos.flush();
        fos.close();
    }
}
