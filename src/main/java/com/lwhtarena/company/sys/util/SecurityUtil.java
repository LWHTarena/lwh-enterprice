package com.lwhtarena.company.sys.util;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Locale;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:47 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class SecurityUtil {

    public static String readWords(ResourceBundleMessageSource messageSource) {
        String secFileName = messageSource.getMessage("security.filename", (Object[])null, "security", (Locale)null);
        String realPath = FileUtil.webinfPath();
        String separator;
        if (File.separator.equals("/")) {
            separator = File.separator;
        } else {
            separator = "\\";
        }

        String fe = realPath + separator + secFileName;
        File f = new File(fe);
        String readString = null;
        BufferedReader o;
        if (f.exists()) {
            try {
                FileReader fr = new FileReader(f);
                o = new BufferedReader(fr);
                readString = o.readLine();
                fr.close();
                o.close();
                fr = null;
                o = null;
            } catch (Exception var11) {
                var11.printStackTrace();
                readString = null;
            }
        }

        if (readString != null && !readString.trim().equals("")) {
            return readString;
        } else {
            if (f.exists()) {
                f.delete();
            }

            String secStr = StringUtil.uuidStr();

            try {
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(secStr.getBytes());
                fos.flush();
                fos.close();
                fos = null;
                return secStr;
            } catch (Exception var10) {
                var10.printStackTrace();
                return null;
            }
        }
    }
}
