package com.lwhtarena.company.ip;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.InputMismatchException;

/**
 * <p>
 * <h2>简述：</h2>
 * <ol></ol>
 * <h2>功能描述：</h2>
 * <ol></ol>
 * </p>
 *
 * @Author: liwh
 * @Date :
 * @Version: 版本
 */
public class AnotherLocator {
    private int offset;
    private int[] index = new int[256];
    private ByteBuffer dataBuffer;
    private ByteBuffer indexBuffer;

    private AnotherLocator(ByteBuffer buffer) {
        this.dataBuffer = buffer;
        this.dataBuffer.position(0);
        int indexLength = this.dataBuffer.getInt();
        byte[] indexBytes = new byte[indexLength];
        this.dataBuffer.get(indexBytes, 0, indexLength - 4);
        this.indexBuffer = ByteBuffer.wrap(indexBytes);
        this.indexBuffer.order(ByteOrder.LITTLE_ENDIAN);
        this.offset = indexLength;

        for(int loop = 0; loop++ < 256; this.index[loop - 1] = this.indexBuffer.getInt()) {
            ;
        }

        this.indexBuffer.order(ByteOrder.BIG_ENDIAN);
    }

    private static long bytesToLong(byte a, byte b, byte c, byte d) {
        return int2long((a & 255) << 24 | (b & 255) << 16 | (c & 255) << 8 | d & 255);
    }

    private static int str2Ip(String ip) {
        String[] ss = ip.split("\\.");
        int a = Integer.parseInt(ss[0]);
        int b = Integer.parseInt(ss[1]);
        int c = Integer.parseInt(ss[2]);
        int d = Integer.parseInt(ss[3]);
        return a << 24 | b << 16 | c << 8 | d;
    }

    private static long ip2long(String ip) {
        return int2long(str2Ip(ip));
    }

    private static long int2long(int i) {
        long l = (long)i & 2147483647L;
        if (i < 0) {
            l |= 2147483648L;
        }

        return l;
    }

    public static AnotherLocator loadFromNet(String netPath) throws IOException {
        URL url = new URL(netPath);
        HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
        httpConn.setConnectTimeout(3000);
        httpConn.setReadTimeout(30000);
        int responseCode = httpConn.getResponseCode();
        if (responseCode != 200) {
            return null;
        } else {
            int length = httpConn.getContentLength();
            if (length > 0 && length <= 10485760) {
                InputStream is = httpConn.getInputStream();
                byte[] data = new byte[length];

                int read;
                for(int downloaded = 0; downloaded < length; downloaded += read) {
                    read = is.read(data, downloaded, length - downloaded);
                    if (read < 0) {
                        is.close();
                        throw new IOException("read error");
                    }
                }

                is.close();
                ByteBuffer bb = ByteBuffer.wrap(data);
                return new AnotherLocator(bb);
            } else {
                throw new InputMismatchException("invalid ip data");
            }
        }
    }

    public static AnotherLocator loadFromLocal(String filePath) throws IOException {
        File f = new File(filePath);
        FileInputStream fi = new FileInputStream(f);
        ByteBuffer bb = ByteBuffer.allocate((int)f.length());

        try {
            fi.read(bb.array());
        } catch (IOException var7) {
            var7.printStackTrace();

            try {
                fi.close();
            } catch (IOException var6) {
                var6.printStackTrace();
            }

            throw var7;
        }

        fi.close();
        fi = null;
        return new AnotherLocator(bb);
    }

    public String[] find(String ip) {
        int ip_prefix_value = new Integer(ip.substring(0, ip.indexOf(".")));
        long ip2long_value = ip2long(ip);
        int start = this.index[ip_prefix_value];
        int max_comp_len = this.offset - 1028;
        long index_offset = -1L;
        int index_length = -1;
        byte b = 0;

        for(start = start * 8 + 1024; start < max_comp_len; start += 8) {
            if (int2long(this.indexBuffer.getInt(start)) >= ip2long_value) {
                index_offset = bytesToLong(b, this.indexBuffer.get(start + 6), this.indexBuffer.get(start + 5), this.indexBuffer.get(start + 4));
                index_length = 255 & this.indexBuffer.get(start + 7);
                break;
            }
        }

        this.dataBuffer.position(this.offset + (int)index_offset - 1024);
        byte[] areaBytes = new byte[index_length];
        this.dataBuffer.get(areaBytes, 0, index_length);
        return (new String(areaBytes, Charset.forName("UTF-8"))).split("\t", -1);
    }
}
