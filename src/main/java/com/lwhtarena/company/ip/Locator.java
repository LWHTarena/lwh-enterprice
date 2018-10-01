package com.lwhtarena.company.ip;

import com.lwhtarena.company.ip.entities.LocationInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
public class Locator implements ILocator{

    public static final String VERSION = "0.1";
    private static final Charset Utf8 = Charset.forName("UTF-8");
    private final byte[] ipData;
    private final int textOffset;
    private final int[] index;
    private final int[] indexData1;
    private final int[] indexData2;
    private final byte[] indexData3;

    private Locator(byte[] data) {
        this.ipData = data;
        this.textOffset = bigEndian(data, 0);
        this.index = new int[256];

        int nidx;
        for(nidx = 0; nidx < 256; ++nidx) {
            this.index[nidx] = littleEndian(data, 4 + nidx * 4);
        }

        nidx = (this.textOffset - 4 - 1024 - 1024) / 8;
        this.indexData1 = new int[nidx];
        this.indexData2 = new int[nidx];
        this.indexData3 = new byte[nidx];
        int i = 0;

        for(boolean var4 = false; i < nidx; ++i) {
            int off = 1028 + i * 8;
            this.indexData1[i] = bigEndian(this.ipData, off);
            this.indexData2[i] = (this.ipData[off + 6] & 255) << 16 | (this.ipData[off + 5] & 255) << 8 | this.ipData[off + 4] & 255;
            this.indexData3[i] = this.ipData[off + 7];
        }

    }

    public static int bigEndian(byte[] data, int offset) {
        int a = data[offset] & 255;
        int b = data[offset + 1] & 255;
        int c = data[offset + 2] & 255;
        int d = data[offset + 3] & 255;
        return a << 24 | b << 16 | c << 8 | d;
    }

    public static int littleEndian(byte[] data, int offset) {
        int a = data[offset] & 255;
        int b = data[offset + 1] & 255;
        int c = data[offset + 2] & 255;
        int d = data[offset + 3] & 255;
        return d << 24 | c << 16 | b << 8 | a;
    }

    public static byte parseOctet(String ipPart) {
        int octet = Integer.parseInt(ipPart);
        if (octet >= 0 && octet <= 255 && (!ipPart.startsWith("0") || ipPart.length() <= 1)) {
            return (byte)octet;
        } else {
            throw new NumberFormatException("invalid ip part");
        }
    }

    public static byte[] textToNumericFormatV4(String str) {
        String[] s = str.split("\\.");
        if (s.length != 4) {
            throw new NumberFormatException("the ip is not v4");
        } else {
            byte[] b = new byte[]{parseOctet(s[0]), parseOctet(s[1]), parseOctet(s[2]), parseOctet(s[3])};
            return b;
        }
    }

    public static LocationInfo buildInfo(byte[] bytes, int offset, int len) {
        String str = new String(bytes, offset, len, Utf8);
        String[] ss = str.split("\t", -1);
        if (ss.length == 4) {
            return new LocationInfo(ss[0], ss[1], ss[2], "");
        } else if (ss.length == 5) {
            return new LocationInfo(ss[0], ss[1], ss[2], ss[4]);
        } else if (ss.length == 3) {
            return new LocationInfo(ss[0], ss[1], ss[2], "");
        } else {
            return ss.length == 2 ? new LocationInfo(ss[0], ss[1], "", "") : null;
        }
    }

    public static Locator loadFromNet(String netPath) throws IOException {
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
                int downloaded = 0;

                int read;
                for(boolean var8 = false; downloaded < length; downloaded += read) {
                    try {
                        read = is.read(data, downloaded, length - downloaded);
                    } catch (IOException var10) {
                        is.close();
                        throw new IOException("read error");
                    }

                    if (read < 0) {
                        is.close();
                        throw new IOException("read error");
                    }
                }

                is.close();
                return loadBinary(data);
            } else {
                throw new InputMismatchException("invalid ip data");
            }
        }
    }

    public static Locator loadFromLocal(String filePath) throws IOException {
        File f = new File(filePath);
        FileInputStream fi = new FileInputStream(f);
        byte[] b = new byte[(int)f.length()];

        try {
            fi.read(b);
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
        return loadBinary(b);
    }

    public static Locator loadBinary(byte[] ipdb) {
        return new Locator(ipdb);
    }

    public static void main(String[] args) {
        if (args != null && args.length >= 2) {
            try {
                Locator l = loadFromLocal(args[0]);
                System.out.println(l.find(args[1]));
            } catch (IOException var2) {
                var2.printStackTrace();
            }

        } else {
            System.out.println("locator ipfile ip");
        }
    }

    private int findIndexOffset(long ip, int start, int end) {
        boolean var5 = false;

        long l;
        while(start < end) {
            int mid = (start + end) / 2;
            l = 4294967295L & (long)this.indexData1[mid];
            if (ip > l) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }

        l = (long)this.indexData1[end] & 4294967295L;
        if (l >= ip) {
            return end;
        } else {
            return start;
        }
    }

    @Override
    public LocationInfo find(String ip) {
        byte[] b;
        try {
            b = textToNumericFormatV4(ip);
        } catch (Exception var4) {
            return null;
        }

        return this.find(b);
    }

    @Override
    public LocationInfo find(byte[] ipBin) {
        int end = this.indexData1.length - 1;
        int a = 255 & ipBin[0];
        if (a != 255) {
            end = this.index[a + 1];
        }

        long ip = (long)bigEndian(ipBin, 0) & 4294967295L;
        int idx = this.findIndexOffset(ip, this.index[a], end);
        int off = this.indexData2[idx];
        return buildInfo(this.ipData, this.textOffset - 1024 + off, 255 & this.indexData3[idx]);
    }

    @Override
    public LocationInfo find(int address) {
        byte[] addr = new byte[]{(byte)(address >> 24 & 255), (byte)(address >> 16 & 255), (byte)(address >> 8 & 255), (byte)(address & 255)};
        return this.find(addr);
    }

    public void checkDb() throws IOException {
        byte[] addr = new byte[4];

        try {
            for(long x = 0L; x < 4294967295L; ++x) {
                addr[0] = (byte)((int)(x >> 24 & 255L));
                addr[1] = (byte)((int)(x >> 16 & 255L));
                addr[2] = (byte)((int)(x >> 8 & 255L));
                addr[3] = (byte)((int)(x & 255L));
                this.find(addr);
            }

        } catch (Exception var4) {
            throw new IOException(var4);
        }
    }
}
