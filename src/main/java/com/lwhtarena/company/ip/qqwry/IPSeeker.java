package com.lwhtarena.company.ip.qqwry;

import com.lwhtarena.company.ip.qqwry.entities.IPEntry;
import com.lwhtarena.company.ip.qqwry.entities.IPLocation;
import com.lwhtarena.company.sys.obj.LogFactory;
import org.apache.log4j.Level;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:18 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class IPSeeker {
    private String IP_FILE = "QQWry.Dat";
    private String INSTALL_DIR = "f:/qqwry";
    private static final int IP_RECORD_LENGTH = 7;
    private static final byte REDIRECT_MODE_1 = 1;
    private static final byte REDIRECT_MODE_2 = 2;
    private Map<String, IPLocation> ipCache;
    private RandomAccessFile ipFile;
    private MappedByteBuffer mbb;
    private long ipBegin;
    private long ipEnd;
    private IPLocation loc;
    private byte[] buf;
    private byte[] b4;
    private byte[] b3;

    public RandomAccessFile getIpFile() {
        return this.ipFile;
    }

    public IPSeeker(String fileName, String dir) {
        this.INSTALL_DIR = dir;
        this.IP_FILE = fileName;
        this.ipCache = new HashMap();
        this.loc = new IPLocation();
        this.buf = new byte[100];
        this.b4 = new byte[4];
        this.b3 = new byte[3];

        try {
            this.ipFile = new RandomAccessFile(this.IP_FILE, "r");
        } catch (FileNotFoundException var10) {
            String filename = (new File(this.IP_FILE)).getName().toLowerCase();
            File[] files = (new File(this.INSTALL_DIR)).listFiles();

            for(int i = 0; i < files.length; ++i) {
                if (files[i].isFile() && files[i].getName().toLowerCase().equals(filename)) {
                    try {
                        this.ipFile = new RandomAccessFile(files[i], "r");
                    } catch (FileNotFoundException var8) {
                        LogFactory.log("IP地址信息文件没有找到，IP显示功能将无法使用", Level.ERROR, var8);
                        this.ipFile = null;
                    }
                    break;
                }
            }
        }

        if (this.ipFile != null) {
            try {
                this.ipBegin = this.readLong4(0L);
                this.ipEnd = this.readLong4(4L);
                if (this.ipBegin == -1L || this.ipEnd == -1L) {
                    this.ipFile.close();
                    this.ipFile = null;
                }
            } catch (IOException var9) {
                LogFactory.log("IP地址信息文件格式有错误，IP显示功能将无法使用", Level.ERROR, var9);
                this.ipFile = null;
            }
        }

    }

    public List<IPEntry> getIPEntriesDebug(String s) {
        List<IPEntry> ret = new ArrayList();
        long endOffset = this.ipEnd + 4L;

        for(long offset = this.ipBegin + 4L; offset <= endOffset; offset += 7L) {
            long temp = this.readLong3(offset);
            if (temp != -1L) {
                IPLocation ipLoc = this.getIPLocation(temp);
                if (ipLoc.getCountry().indexOf(s) != -1 || ipLoc.getArea().indexOf(s) != -1) {
                    IPEntry entry = new IPEntry();
                    entry.country = ipLoc.getCountry();
                    entry.area = ipLoc.getArea();
                    this.readIP(offset - 4L, this.b4);
                    entry.beginIp = Util.getIpStringFromBytes(this.b4);
                    this.readIP(temp, this.b4);
                    entry.endIp = Util.getIpStringFromBytes(this.b4);
                    ret.add(entry);
                }
            }
        }

        return ret;
    }

    public IPLocation getIPLocation(String ip) {
        IPLocation location = new IPLocation();
        location.setArea(this.getArea(ip));
        location.setCountry(this.getCountry(ip));
        return location;
    }

    public List<IPEntry> getIPEntries(String s) {
        ArrayList ret = new ArrayList();

        try {
            if (this.mbb == null) {
                FileChannel fc = this.ipFile.getChannel();
                this.mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0L, this.ipFile.length());
                this.mbb.order(ByteOrder.LITTLE_ENDIAN);
            }

            int endOffset = (int)this.ipEnd;

            for(int offset = (int)this.ipBegin + 4; offset <= endOffset; offset += 7) {
                int temp = this.readInt3(offset);
                if (temp != -1) {
                    IPLocation ipLoc = this.getIPLocation(temp);
                    if (ipLoc.getCountry().indexOf(s) != -1 || ipLoc.getArea().indexOf(s) != -1) {
                        IPEntry entry = new IPEntry();
                        entry.country = ipLoc.getCountry();
                        entry.area = ipLoc.getArea();
                        this.readIP(offset - 4, this.b4);
                        entry.beginIp = Util.getIpStringFromBytes(this.b4);
                        this.readIP(temp, this.b4);
                        entry.endIp = Util.getIpStringFromBytes(this.b4);
                        ret.add(entry);
                    }
                }
            }
        } catch (IOException var8) {
            LogFactory.log("", Level.ERROR, var8);
        }

        return ret;
    }

    private int readInt3(int offset) {
        this.mbb.position(offset);
        return this.mbb.getInt() & 16777215;
    }

    private int readInt3() {
        return this.mbb.getInt() & 16777215;
    }

    public String getCountry(byte[] ip) {
        if (this.ipFile == null) {
            return "IP地址库文件错误";
        } else {
            String ipStr = Util.getIpStringFromBytes(ip);
            IPLocation ipLoc;
            if (this.ipCache.containsKey(ipStr)) {
                ipLoc = (IPLocation)this.ipCache.get(ipStr);
                return ipLoc.getCountry();
            } else {
                ipLoc = this.getIPLocation(ip);
                this.ipCache.put(ipStr, ipLoc.getCopy());
                return ipLoc.getCountry();
            }
        }
    }

    public String getCountry(String ip) {
        return this.getCountry(Util.getIpByteArrayFromString(ip));
    }

    public String getArea(byte[] ip) {
        if (this.ipFile == null) {
            return "IP地址库文件错误";
        } else {
            String ipStr = Util.getIpStringFromBytes(ip);
            IPLocation ipLoc;
            if (this.ipCache.containsKey(ipStr)) {
                ipLoc = (IPLocation)this.ipCache.get(ipStr);
                return ipLoc.getArea();
            } else {
                ipLoc = this.getIPLocation(ip);
                this.ipCache.put(ipStr, ipLoc.getCopy());
                return ipLoc.getArea();
            }
        }
    }

    public String getArea(String ip) {
        return this.getArea(Util.getIpByteArrayFromString(ip));
    }

    private IPLocation getIPLocation(byte[] ip) {
        IPLocation info = null;
        long offset = this.locateIP(ip);
        if (offset != -1L) {
            info = this.getIPLocation(offset);
        }

        if (info == null) {
            info = new IPLocation();
            info.setCountry("未知国家");
            info.setArea("未知地区");
        }

        return info;
    }

    private long readLong4(long offset) {
        long ret = 0L;

        try {
            this.ipFile.seek(offset);
            ret |= (long)(this.ipFile.readByte() & 255);
            ret |= (long)(this.ipFile.readByte() << 8 & '\uff00');
            ret |= (long)(this.ipFile.readByte() << 16 & 16711680);
            ret |= (long)(this.ipFile.readByte() << 24 & -16777216);
            return ret;
        } catch (IOException var6) {
            return -1L;
        }
    }

    private long readLong3(long offset) {
        long ret = 0L;

        try {
            this.ipFile.seek(offset);
            this.ipFile.readFully(this.b3);
            ret |= (long)(this.b3[0] & 255);
            ret |= (long)(this.b3[1] << 8 & '\uff00');
            ret |= (long)(this.b3[2] << 16 & 16711680);
            return ret;
        } catch (IOException var6) {
            return -1L;
        }
    }

    private long readLong3() {
        long ret = 0L;

        try {
            this.ipFile.readFully(this.b3);
            ret |= (long)(this.b3[0] & 255);
            ret |= (long)(this.b3[1] << 8 & '\uff00');
            ret |= (long)(this.b3[2] << 16 & 16711680);
            return ret;
        } catch (IOException var4) {
            return -1L;
        }
    }

    private void readIP(long offset, byte[] ip) {
        try {
            this.ipFile.seek(offset);
            this.ipFile.readFully(ip);
            byte temp = ip[0];
            ip[0] = ip[3];
            ip[3] = temp;
            temp = ip[1];
            ip[1] = ip[2];
            ip[2] = temp;
        } catch (IOException var5) {
            LogFactory.log("", Level.ERROR, var5);
        }

    }

    private void readIP(int offset, byte[] ip) {
        this.mbb.position(offset);
        this.mbb.get(ip);
        byte temp = ip[0];
        ip[0] = ip[3];
        ip[3] = temp;
        temp = ip[1];
        ip[1] = ip[2];
        ip[2] = temp;
    }

    private int compareIP(byte[] ip, byte[] beginIp) {
        for(int i = 0; i < 4; ++i) {
            int r = this.compareByte(ip[i], beginIp[i]);
            if (r != 0) {
                return r;
            }
        }

        return 0;
    }

    private int compareByte(byte b1, byte b2) {
        if ((b1 & 255) > (b2 & 255)) {
            return 1;
        } else {
            return (b1 ^ b2) == 0 ? 0 : -1;
        }
    }

    private long locateIP(byte[] ip) {
        long m = 0L;
        this.readIP(this.ipBegin, this.b4);
        int r = this.compareIP(ip, this.b4);
        if (r == 0) {
            return this.ipBegin;
        } else if (r < 0) {
            return -1L;
        } else {
            long i = this.ipBegin;
            long j = this.ipEnd;

            while(i < j) {
                m = this.getMiddleOffset(i, j);
                this.readIP(m, this.b4);
                r = this.compareIP(ip, this.b4);
                if (r > 0) {
                    i = m;
                } else {
                    if (r >= 0) {
                        return this.readLong3(m + 4L);
                    }

                    if (m == j) {
                        j -= 7L;
                        m = j;
                    } else {
                        j = m;
                    }
                }
            }

            m = this.readLong3(m + 4L);
            this.readIP(m, this.b4);
            r = this.compareIP(ip, this.b4);
            if (r <= 0) {
                return m;
            } else {
                return -1L;
            }
        }
    }

    private long getMiddleOffset(long begin, long end) {
        long records = (end - begin) / 7L;
        records >>= 1;
        if (records == 0L) {
            records = 1L;
        }

        return begin + records * 7L;
    }

    private IPLocation getIPLocation(long offset) {
        try {
            this.ipFile.seek(offset + 4L);
            byte b = this.ipFile.readByte();
            if (b == 1) {
                long countryOffset = this.readLong3();
                this.ipFile.seek(countryOffset);
                b = this.ipFile.readByte();
                if (b == 2) {
                    this.loc.setCountry(this.readString(this.readLong3()));
                    this.ipFile.seek(countryOffset + 4L);
                } else {
                    this.loc.setCountry(this.readString(countryOffset));
                }

                this.loc.setArea(this.readArea(this.ipFile.getFilePointer()));
            } else if (b == 2) {
                this.loc.setCountry(this.readString(this.readLong3()));
                this.loc.setArea(this.readArea(offset + 8L));
            } else {
                this.loc.setCountry(this.readString(this.ipFile.getFilePointer() - 1L));
                this.loc.setArea(this.readArea(this.ipFile.getFilePointer()));
            }

            return this.loc;
        } catch (IOException var6) {
            return null;
        }
    }

    private IPLocation getIPLocation(int offset) {
        this.mbb.position(offset + 4);
        byte b = this.mbb.get();
        if (b == 1) {
            int countryOffset = this.readInt3();
            this.mbb.position(countryOffset);
            b = this.mbb.get();
            if (b == 2) {
                this.loc.setCountry(this.readString(this.readInt3()));
                this.mbb.position(countryOffset + 4);
            } else {
                this.loc.setCountry(this.readString(countryOffset));
            }

            this.loc.setArea(this.readArea(this.mbb.position()));
        } else if (b == 2) {
            this.loc.setCountry(this.readString(this.readInt3()));
            this.loc.setArea(this.readArea(offset + 8));
        } else {
            this.loc.setCountry(this.readString(this.mbb.position() - 1));
            this.loc.setArea(this.readArea(this.mbb.position()));
        }

        return this.loc;
    }

    private String readArea(long offset) throws IOException {
        this.ipFile.seek(offset);
        byte b = this.ipFile.readByte();
        if (b != 1 && b != 2) {
            return this.readString(offset);
        } else {
            long areaOffset = this.readLong3(offset + 1L);
            return areaOffset == 0L ? "未知地区" : this.readString(areaOffset);
        }
    }

    private String readArea(int offset) {
        this.mbb.position(offset);
        byte b = this.mbb.get();
        if (b != 1 && b != 2) {
            return this.readString(offset);
        } else {
            int areaOffset = this.readInt3();
            return areaOffset == 0 ? "未知地区" : this.readString(areaOffset);
        }
    }

    private String readString(long offset) {
        try {
            this.ipFile.seek(offset);
            int i = 0;

            for(this.buf[i] = this.ipFile.readByte(); this.buf[i] != 0; this.buf[i] = this.ipFile.readByte()) {
                ++i;
            }

            if (i != 0) {
                return Util.getString(this.buf, 0, i, "GBK");
            }
        } catch (IOException var4) {
            LogFactory.log("", Level.ERROR, var4);
        }

        return "";
    }

    private String readString(int offset) {
        try {
            this.mbb.position(offset);
            int i = 0;

            for(this.buf[i] = this.mbb.get(); this.buf[i] != 0; this.buf[i] = this.mbb.get()) {
                ++i;
            }

            if (i != 0) {
                return Util.getString(this.buf, 0, i, "GBK");
            }
        } catch (IllegalArgumentException var3) {
            LogFactory.log("", Level.ERROR, var3);
        }

        return "";
    }
}
