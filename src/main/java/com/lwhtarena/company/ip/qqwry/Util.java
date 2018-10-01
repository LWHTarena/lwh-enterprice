package com.lwhtarena.company.ip.qqwry;

import com.lwhtarena.company.sys.obj.LogFactory;
import org.apache.log4j.Level;

import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:18 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class Util {
    private static StringBuilder sb = new StringBuilder();

    public Util() {
    }

    public static byte[] getIpByteArrayFromString(String ip) {
        byte[] ret = new byte[4];
        StringTokenizer st = new StringTokenizer(ip, ".");

        try {
            ret[0] = (byte)(Integer.parseInt(st.nextToken()) & 255);
            ret[1] = (byte)(Integer.parseInt(st.nextToken()) & 255);
            ret[2] = (byte)(Integer.parseInt(st.nextToken()) & 255);
            ret[3] = (byte)(Integer.parseInt(st.nextToken()) & 255);
        } catch (Exception var4) {
            LogFactory.log("从ip的字符串形式得到字节数组形式报错", Level.ERROR, var4);
        }

        return ret;
    }

    public static String getIpStringFromBytes(byte[] ip) {
        sb.delete(0, sb.length());
        sb.append(ip[0] & 255);
        sb.append('.');
        sb.append(ip[1] & 255);
        sb.append('.');
        sb.append(ip[2] & 255);
        sb.append('.');
        sb.append(ip[3] & 255);
        return sb.toString();
    }

    public static String getString(byte[] b, int offset, int len, String encoding) {
        try {
            return new String(b, offset, len, encoding);
        } catch (UnsupportedEncodingException var5) {
            return new String(b, offset, len);
        }
    }
}
