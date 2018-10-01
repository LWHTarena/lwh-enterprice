package com.lwhtarena.company.sys.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author：liwh
 * @Description:
 * @Date 13:14 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class StringUtil {


    /**
     * 是否为空
     * @param v
     * @return
     */
    public static boolean isNull(Object v) {
        return v == null || "".equals(v.toString().trim());
    }

    /**
     * 是否非空
     * @param v
     * @return
     */
    public static boolean isNotNull(Object v) {
        return !isNull(v);
    }

    /**
     * 去除字符串两边的空格
     * @param v
     * @return
     */
    public static String trimNull(Object v) {
        return (v == null ? "" : v.toString().trim());
    }

    /**
     * 判断是否是空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if(str==null || "".equals(str.trim()) || "null".equals(str)){
            return true;
        }else{
            return false;
        }
    }

    public static String smartHtmlFilter(String str, String smartFile, String charset, String breakstr) {
        str = strReplace(str, "<img", "_img");
        str = htmlFilter(str);
        if (breakstr != null && breakstr.trim().equalsIgnoreCase("br")) {
            str = strReplace(str, "\n", "<br />");
        } else {
            str = strReplace(str, "\n", "</p><p>");
            str = "<p>" + str;
            str = str + "</p>";
        }

        Pattern pattern = Pattern.compile("<p>(&nbsp;+)");
        Matcher matcher = pattern.matcher(str);
        str = matcher.replaceAll("<p>");
        pattern = Pattern.compile("<p>(\t+)");
        matcher = pattern.matcher(str);
        str = matcher.replaceAll("<p>");
        pattern = Pattern.compile("<p>(\\s+)");
        matcher = pattern.matcher(str);
        str = matcher.replaceAll("<p>");
        str = strReplace(str, "<p></p>", "");
        str = strReplace(str, "_img", "<img");
        pattern = Pattern.compile("(&nbsp;+)<img");
        matcher = pattern.matcher(str);
        str = matcher.replaceAll("<img");
        return str;
    }

    public static boolean sqlInjection(String str) {
        return str.toLowerCase().indexOf(" or ") != -1 || str.toLowerCase().indexOf(" and ") != -1;
    }

    public static List<String> readLine(String txt, boolean ignoreNull, boolean trim) {
        Pattern pattern = Pattern.compile("(\r\n|\r|\n|\n\r)");
        Matcher matcher = pattern.matcher(txt);
        String tag = "__lzh_lerx_say__";
        String newTxt = matcher.replaceAll(tag);
        String[] sArray = newTxt.split(tag);
        List<String> listr = new ArrayList();

        for(int i = 0; i < sArray.length; ++i) {
            String lineTxt = sArray[i];
            if (lineTxt != null && !lineTxt.trim().equals("")) {
                if (trim) {
                    lineTxt = lineTxt.trim();
                }

                listr.add(lineTxt);
            } else {
                if (lineTxt != null && trim) {
                    lineTxt = lineTxt.trim();
                }

                if (ignoreNull) {
                    listr.add(lineTxt);
                }
            }
        }

        return listr;
    }

    public static String replaceEnter(String oldString) {
        Pattern pattern = Pattern.compile("(\r\n|\r|\n|\n\r)");
        Matcher matcher = pattern.matcher(oldString);
        String newString = matcher.replaceAll("");
        return newString;
    }

    public static String randomString(int size) {
        char[] c = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm'};
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < size; ++i) {
            sb.append(c[Math.abs(random.nextInt()) % c.length]);
        }

        return sb.toString();
    }

    public static String md5(String s) {
        MessageDigest md5Digest = null;
        StringBuffer digestBuffer = null;

        try {
            md5Digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
        }

        digestBuffer = new StringBuffer();
        digestBuffer.setLength(0);
        byte[] abyte0 = md5Digest.digest(s.getBytes());

        for(int i = 0; i < abyte0.length; ++i) {
            digestBuffer.append(toHex(abyte0[i]));
        }

        return digestBuffer.toString();
    }

    public static String toHex(byte one) {
        String HEX = "0123456789ABCDEF";
        char[] result = new char[]{HEX.charAt((one & 240) >> 4), HEX.charAt(one & 15)};
        String mm = new String(result);
        return mm;
    }

    public static String nullFilter(String var) {
        return var == null ? "" : var;
    }

    public static String nullAndHtmlFilter(String var) {
        return var == null ? "" : htmlFilter(var);
    }

    public static String nullFilter2(String var) {
        return var != null && !var.trim().equalsIgnoreCase("null") ? var.trim() : "";
    }

    public static String countStr(int n, String str) {
        if (n <= 0) {
            return "";
        } else {
            String endStr = "";

            for(int i = 1; i <= n; ++i) {
                endStr = endStr + str;
            }

            return endStr;
        }
    }

    public static String strReplace(String strSource, String strFrom, String strTo) {
        if (strSource == null) {
            return null;
        } else if (strFrom != null && !strFrom.equals("")) {
            String strDest = "";
            int intFromLen = strFrom.length();
            if (strSource != null) {
                int intPos;
                while((intPos = strSource.indexOf(strFrom)) != -1) {
                    strDest = strDest + strSource.substring(0, intPos);
                    strDest = strDest + strTo;
                    strSource = strSource.substring(intPos + intFromLen);
                }

                strDest = strDest + strSource;
            }

            return strDest;
        } else {
            return strSource;
        }
    }

    public static boolean emailTest(String email) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        boolean b = m.matches();
        return b;
    }

    public static String escapeUrl(String url, int act) {
        if (act == 1) {
            url = strReplace(url, ":", "-________-");
            url = strReplace(url, "/", "-_______-");
            url = strReplace(url, "#", "-______-");
            url = strReplace(url, "+", "-_____-");
            url = strReplace(url, "%", "-____-");
            url = strReplace(url, "&", "-___-");
            url = strReplace(url, "=", "-__-");
            url = strReplace(url, ",", "-_-");
        } else {
            url = strReplace(url, "-________-", ":");
            url = strReplace(url, "-_______-", "/");
            url = strReplace(url, "-______-", "#");
            url = strReplace(url, "-_____-", "+");
            url = strReplace(url, "-____-", "%");
            url = strReplace(url, "-___-", "&");
            url = strReplace(url, "-__-", "=");
            url = strReplace(url, "-_-", ",");
        }

        return url;
    }

    public static String uuidStr() {
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        s = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
        return s;
    }

    public static String filterByWords(String source, String words, boolean rep, String repTarget) {
        if (source != null && !source.trim().equals("")) {
            String[] sArray = words.split(",");
            if (sArray.length > 0) {
                for(int step = 0; step < sArray.length; ++step) {
                    if (source.indexOf(sArray[step]) != -1) {
                        if (!rep) {
                            source = strReplace(source, sArray[step], "");
                        } else {
                            source = strReplace(source, sArray[step], repTarget);
                        }
                    }
                }
            }

            return source;
        } else {
            return source;
        }
    }

    public static boolean findByWords(String source, String words) {
        if (source != null && !source.trim().equals("")) {
            String[] sArray = words.split(",");
            if (sArray.length > 0) {
                for(int step = 0; step < sArray.length; ++step) {
                    if (source.indexOf(sArray[step]) != -1) {
                        return true;
                    }
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static String filterByHtmlLabel(String htmlStr, String label) {
        if (htmlStr == null) {
            return htmlStr;
        } else {
            String regEx_style = "<[\\s]*?" + label + "[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?" + label + "[\\s]*?>";
            Pattern pStyle = Pattern.compile(regEx_style, 2);
            Matcher mStyle = pStyle.matcher(htmlStr);
            htmlStr = mStyle.replaceAll("");
            return htmlStr;
        }
    }

    public static String htmlFilter(String htmlStr) {
        if (htmlStr != null && !htmlStr.trim().equals("")) {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>";
            String regEx_space = "<.+?>|&nbsp;|//s";
            Pattern pScript = Pattern.compile(regEx_script, 2);
            Matcher mScript = pScript.matcher(htmlStr);
            htmlStr = mScript.replaceAll("");
            Pattern pStyle = Pattern.compile(regEx_style, 2);
            Matcher mStyle = pStyle.matcher(htmlStr);
            htmlStr = mStyle.replaceAll("");
            Pattern pHtml = Pattern.compile(regEx_html, 2);
            Matcher mHtml = pHtml.matcher(htmlStr);
            htmlStr = mHtml.replaceAll("");
            pHtml = Pattern.compile(regEx_space, 2);
            mHtml = pHtml.matcher(htmlStr);
            htmlStr = mHtml.replaceAll("");
            return htmlStr;
        } else {
            return htmlStr;
        }
    }

    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public static String timeCustomReplace(String sourStr, String pre, Timestamp t) {
        String forEndStr = "";
        if (sourStr != null && !sourStr.trim().equals("")) {
            int poss = sourStr.indexOf("{$$" + pre + "@");
            if (poss == -1) {
                return sourStr;
            } else {
                int pose = sourStr.indexOf("$$}", poss);
                forEndStr = sourStr.substring(poss, pose + 3);
                if (forEndStr.trim().equals("")) {
                    forEndStr = strReplace(sourStr, forEndStr, "");
                } else if (t != null) {
                    DateFormat f = new SimpleDateFormat(forEndStr.substring(4 + pre.length(), forEndStr.length() - 3));
                    forEndStr = strReplace(sourStr, forEndStr, f.format(t));
                } else {
                    forEndStr = strReplace(sourStr, forEndStr, "");
                }

                return forEndStr;
            }
        } else {
            return null;
        }
    }

    public static String htmlSpecialCharsForKE(String str) {
        str = nullFilter(str);
        if (!str.trim().equals("")) {
            str = str.replaceAll("&", "&amp;");
            str = str.replaceAll("<", "&lt;");
            str = str.replaceAll(">", "&gt;");
            str = str.replaceAll("\"", "&quot;");
        }

        return str;
    }

    public static String covIntToStr(int n, int l) {
        String tmp = "" + n;
        if (tmp.length() >= l) {
            return tmp;
        } else {
            int emp = l - tmp.length();
            String empStr = countStr(emp, "0");
            tmp = empStr + tmp;
            return tmp;
        }
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    public static int byteLen(String val) {
        int len = 0;

        for(int i = 0; i < val.length(); ++i) {
            if (isChinese(val.charAt(i))) {
                len += 2;
            } else {
                ++len;
            }
        }

        return len;
    }

    public static String cutByte(String val, int len) {
        if (byteLen(val) <= len) {
            return val;
        } else {
            String str = "";
            String str2 = "";
            int i = 0;
            int end = 0;

            while(end < len) {
                char s = val.charAt(i);
                str2 = str2 + s;
                if (byteLen(str2) <= len) {
                    str = str + s;
                }

                ++i;
                ++end;
                if (isChinese(s)) {
                    ++end;
                }
            }

            return str;
        }
    }

    public static String filterUnNumber(String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String escape(String str) {
        if (str == null) {
            return str;
        } else {
            str = strReplace(str, "\"", "\\&quot;");
            str = strReplace(str, "'", "\\&apos;");
            str = strReplace(str, " ", "&nbsp;");
            str = strReplace(str, "×", "&times;");
            str = strReplace(str, "÷", "&divide;");
            str = strReplace(str, "<", "&lt;");
            str = strReplace(str, ">", "&gt;");
            str = strReplace(str, "©", "&copy;");
            str = strReplace(str, "®", "&reg;");
            return str;
        }
    }

    public static int bitForStrCharsAdd(String str) {
        int n = 0;
        int len = str.length();

        for(int i = 0; i < len; ++i) {
            char c = str.charAt(i);
            n += c;
        }

        return n % 2;
    }

    public static String strAtBit(String str, int bit) {
        String s = "";
        int len = str.length();

        for(int i = 0; i < len; ++i) {
            char c = str.charAt(i);
            if (bit == 0) {
                if (i % 2 == 0) {
                    s = s + String.valueOf(c);
                }
            } else if (i % 2 == 1) {
                s = s + String.valueOf(c);
            }
        }

        return s;
    }

    public static String urlEncoder(String url, String charSet) throws UnsupportedEncodingException {
        url = strReplace(url, "/", "_____a__a_____");
        url = strReplace(url, ":", "_____b__b_____");
        url = URLEncoder.encode(url, charSet);
        url = strReplace(url, "_____a__a_____", "/");
        url = strReplace(url, "_____b__b_____", ":");
        url = url.toLowerCase();
        return url;
    }

    public static boolean compare(String s1, String s2) {
        return (s1 != null || s2 == null) && (s2 != null || s1 == null) && (s1 == null || s2 == null || s1.trim().equals(s2));
    }

    public static String propertiesValue(Properties p, String key) {
        String toString = p.toString();
        toString = strReplace(toString, "{", "");
        toString = strReplace(toString, "}", "");
        String[] sArray = toString.split(",");
        String value = null;

        for(int i = 0; i < sArray.length; ++i) {
            int j = sArray[i].indexOf(61);
            if (j > 0) {
                String name = sArray[i].substring(0, j);
                String val = sArray[i].substring(j + 1);
                if (key.trim().equals(name.trim())) {
                    value = val.trim();
                }
            }
        }

        return value;
    }

    public static String mactcherG1(String var, String pat) {
        Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(var);
        String val = null;
        if (matcher.find()) {
            val = matcher.group(1);
        }

        return val;
    }

    public static String captureName(String name, int mode) {
        if (mode == 0) {
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            return name;
        } else {
            char[] cs = name.toCharArray();
            cs[0] = (char)(cs[0] - 32);
            return String.valueOf(cs);
        }
    }

    public static String getEncoding(String str) {
        String encode = "GB2312";

        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception var6) {
            ;
        }

        encode = "ISO-8859-1";

        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception var5) {
            ;
        }

        encode = "UTF-8";

        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception var4) {
            ;
        }

        encode = "GBK";

        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception var3) {
            ;
        }

        return "";
    }

    public static String clear65279(String str) {
        int len = str.length();
        String newStr = "";

        for(int i = 0; i < len; ++i) {
            char c = str.charAt(i);
            if (c != '\ufeff') {
                newStr = newStr + String.valueOf(c);
            }
        }

        return newStr;
    }

    public static String sub(String str, int start, int len, boolean ellipsis) {
        if (str.length() <= len) {
            return str;
        } else {
            return ellipsis ? str.substring(start, len) + "..." : str.substring(start, len);
        }
    }

    public float getSimilarityRatio(String str, String target) {
        return 1.0F - (float)this.similar(str, target) / (float)Math.max(str.length(), target.length());
    }

    private int similar(String str, String target) {
        int n = str.length();
        int m = target.length();
        if (n == 0) {
            return m;
        } else if (m == 0) {
            return n;
        } else {
            int[][] d = new int[n + 1][m + 1];

            int i;
            for(i = 0; i <= n; d[i][0] = i++) {
                ;
            }

            int j;
            for(j = 0; j <= m; d[0][j] = j++) {
                ;
            }

            for(i = 1; i <= n; ++i) {
                char ch1 = str.charAt(i - 1);

                for(j = 1; j <= m; ++j) {
                    char ch2 = target.charAt(j - 1);
                    byte temp;
                    if (ch1 != ch2 && ch1 != ch2 + 32 && ch1 + 32 != ch2) {
                        temp = 1;
                    } else {
                        temp = 0;
                    }

                    d[i][j] = this.min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
                }
            }

            return d[n][m];
        }
    }

    private int min(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;
    }

    public static void main(String[] args) throws ParseException, Exception {
        System.out.println(isNumber("1aa"));
        System.out.println(isNumber("-13"));
    }
}
