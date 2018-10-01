package com.lwhtarena.company.sys.util;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Author：liwh
 * @Description:
 * @Date 22:58 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class IniUtil {

    public static String read(File iniFile, String section, String key) throws IOException {
        Ini ini = new Ini();

        try {
            FileReader fr = new FileReader(iniFile);
            ini.load(fr);
            fr.close();
            fr = null;
        } catch (InvalidFileFormatException var7) {
            var7.printStackTrace();
            return null;
        } catch (FileNotFoundException var8) {
            var8.printStackTrace();
            return null;
        } catch (IOException var9) {
            var9.printStackTrace();
            return null;
        }

        Section sec = (Section)ini.get(section);
        String r = (String)sec.get(key);
        return r;
    }
}
