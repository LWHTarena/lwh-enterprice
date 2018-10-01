package com.lwhtarena.company.sys.util;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.io.IOException;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:30 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class DesUtils {

    public DesUtils() {
    }

    public static String encrypt(ResourceBundleMessageSource messageSource, String source, boolean escape) {
        String securityStr = SecurityUtil.readWords(messageSource);

        try {
            return encryptStr(source, byteNum(securityStr), escape);
        } catch (IOException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static String decrypt(ResourceBundleMessageSource messageSource, String source, boolean escape) {
        String securityStr = SecurityUtil.readWords(messageSource);

        try {
            return decryptStr(source, byteNum(securityStr), escape);
        } catch (IOException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    private static String encryptStr(String source, Integer[] in, boolean escape) {
        int l = source.length();
        int[] ic = new int[l];

        for(int i = 0; i < l; ++i) {
            int c = source.charAt(i) + in[i % 10];
            ic[i] = c;
        }

        String r = "";

        for(int m = 0; m < ic.length; ++m) {
            r = r + (char)ic[m];
        }

        if (escape) {
            r = StringUtil.strReplace(r, "\\", "__lerxc00__");
            r = StringUtil.strReplace(r, " ", "__lerxc01__");
            r = StringUtil.strReplace(r, "&", "__lerxc02__");
            r = StringUtil.strReplace(r, "?", "__lerxc03__");
            r = StringUtil.strReplace(r, "@", "__lerxc04__");
            r = StringUtil.strReplace(r, "#", "__lerxc05__");
            r = StringUtil.strReplace(r, "%", "__lerxc06__");
            r = StringUtil.strReplace(r, "$", "__lerxc07__");
            r = StringUtil.strReplace(r, "!", "__lerxc08__");
            r = StringUtil.strReplace(r, ":", "__lerxc09__");
            r = StringUtil.strReplace(r, ",", "__lerxc10__");
            r = StringUtil.strReplace(r, ";", "__lerxc11__");
            r = StringUtil.strReplace(r, "|", "__lerxc12__");
            r = StringUtil.strReplace(r, "~", "__lerxc13__");
            r = StringUtil.strReplace(r, "^", "__lerxc14__");
            r = StringUtil.strReplace(r, "*", "__lerxc15__");
            r = StringUtil.strReplace(r, "`", "__lerxc16__");
            r = StringUtil.strReplace(r, "\t", "__lerxc17__");
            r = StringUtil.strReplace(r, "{", "__lerxc18__");
            r = StringUtil.strReplace(r, "}", "__lerxc19__");
            r = StringUtil.strReplace(r, "<", "__lerxc20__");
            r = StringUtil.strReplace(r, ">", "__lerxc21__");
            r = StringUtil.strReplace(r, "[", "__lerxc22__");
            r = StringUtil.strReplace(r, "]", "__lerxc23__");
            r = StringUtil.strReplace(r, "(", "__lerxc24__");
            r = StringUtil.strReplace(r, ")", "__lerxc25__");
        }

        return r;
    }

    private static String decryptStr(String source, Integer[] in, boolean escape) {
        if (escape) {
            source = StringUtil.strReplace(source, "__lerxc00__", "\\");
            source = StringUtil.strReplace(source, "__lerxc01__", " ");
            source = StringUtil.strReplace(source, "__lerxc02__", "&");
            source = StringUtil.strReplace(source, "__lerxc03__", "?");
            source = StringUtil.strReplace(source, "__lerxc04__", "@");
            source = StringUtil.strReplace(source, "__lerxc05__", "#");
            source = StringUtil.strReplace(source, "__lerxc06__", "%");
            source = StringUtil.strReplace(source, "__lerxc07__", "$");
            source = StringUtil.strReplace(source, "__lerxc08__", "!");
            source = StringUtil.strReplace(source, "__lerxc09__", ":");
            source = StringUtil.strReplace(source, "__lerxc10__", ",");
            source = StringUtil.strReplace(source, "__lerxc11__", ";");
            source = StringUtil.strReplace(source, "__lerxc12__", "|");
            source = StringUtil.strReplace(source, "__lerxc13__", "~");
            source = StringUtil.strReplace(source, "__lerxc14__", "^");
            source = StringUtil.strReplace(source, "__lerxc15__", "*");
            source = StringUtil.strReplace(source, "__lerxc16__", "`");
            source = StringUtil.strReplace(source, "__lerxc17__", "\t");
            source = StringUtil.strReplace(source, "__lerxc18__", "{");
            source = StringUtil.strReplace(source, "__lerxc19__", "}");
            source = StringUtil.strReplace(source, "__lerxc20__", "<");
            source = StringUtil.strReplace(source, "__lerxc21__", ">");
            source = StringUtil.strReplace(source, "__lerxc22__", "[");
            source = StringUtil.strReplace(source, "__lerxc23__", "]");
            source = StringUtil.strReplace(source, "__lerxc24__", "(");
            source = StringUtil.strReplace(source, "__lerxc25__", ")");
        }

        int l = source.length();
        int[] ic = new int[l];

        for(int i = 0; i < l; ++i) {
            int c = source.charAt(i) - in[i % 10];
            ic[i] = c;
        }

        String r = "";

        for(int m = 0; m < ic.length; ++m) {
            r = r + (char)ic[m];
        }

        return r;
    }

    private static Integer[] byteNum(String securityWords) throws IOException {
        if (securityWords == null) {
            securityWords = "";
        }

        securityWords = securityWords.trim();
        if (securityWords.length() < 32) {
            return null;
        } else {
            Integer[] in = new Integer[10];

            for(int i = 0; i <= 9; ++i) {
                int a = securityWords.charAt(i);
                int b = securityWords.charAt(i + 10);
                int c = securityWords.charAt(i + 20);
                int d = 0;
                if (i == 0) {
                    d = securityWords.charAt(30);
                }

                if (i == 9) {
                    d = securityWords.charAt(31);
                }

                int e = a + b + c + d;
                e %= 10;
                in[i] = e;
            }

            return in;
        }
    }
}
