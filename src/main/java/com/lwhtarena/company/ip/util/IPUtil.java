package com.lwhtarena.company.ip.util;

import com.lwhtarena.company.ip.ExternalIpAddressFetcher;
import com.lwhtarena.company.ip.Locator;
import com.lwhtarena.company.ip.entities.LocationInfo;
import com.lwhtarena.company.ip.qqwry.IPSeeker;
import com.lwhtarena.company.ip.qqwry.entities.IPLocation;
import com.lwhtarena.company.sys.util.FileUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author：liwh
 * @Description:
 * @Date 22:58 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class IPUtil {
    public static IPLocation queryQQWry(String ip) {
        IPSeeker res = new IPSeeker("qqwry.dat", FileUtil.webinfPath() + File.separator + "res");
        IPLocation ipl = new IPLocation();
        ipl.setCountry(res.getIPLocation(ip).getCountry());
        ipl.setArea(res.getIPLocation(ip).getArea());
        if (res.getIpFile() != null) {
            try {
                res.getIpFile().close();
            } catch (IOException var4) {
                var4.printStackTrace();
            }
        }

        return ipl;
    }

    public static LocationInfo queryIPIP(String ip) {
        try {
            Locator l = Locator.loadFromLocal(FileUtil.webinfPath() + File.separator + "res" + File.separator + "17monipdb.dat");
            LocationInfo info = l.find(ip);
            return info;
        } catch (IOException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static String getRealRemotIP(HttpServletRequest request) {
        String strRemoteAddr;
        if (request == null) {
            strRemoteAddr = null;
        } else {
            strRemoteAddr = request.getRemoteAddr();
        }

        String strXRealIP = request.getHeader("X-Real-IP");
        if (strXRealIP == null || strXRealIP.length() == 0 || "unknown".equalsIgnoreCase(strXRealIP)) {
            strXRealIP = request.getHeader("X-Forwarded-For");
        }

        if (strXRealIP == null || strXRealIP.length() == 0 || "unknown".equalsIgnoreCase(strXRealIP)) {
            strXRealIP = request.getHeader("Proxy-Client-IP");
        }

        if (strXRealIP == null || strXRealIP.length() == 0 || "unknown".equalsIgnoreCase(strXRealIP)) {
            strXRealIP = request.getHeader("WL-Proxy-Client-IP");
        }

        if (strXRealIP == null || strXRealIP.length() == 0 || "unknown".equalsIgnoreCase(strXRealIP)) {
            strXRealIP = request.getHeader("HTTP_CLIENT_IP");
        }

        if (strXRealIP == null || strXRealIP.length() == 0 || "unknown".equalsIgnoreCase(strXRealIP)) {
            strXRealIP = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (strXRealIP == null || strXRealIP.length() == 0 || "unknown".equalsIgnoreCase(strXRealIP)) {
            strXRealIP = request.getRemoteAddr();
        }

        String strReturnIP = "";
        if (strXRealIP == null) {
            strXRealIP = "";
        }

        if (strRemoteAddr == null) {
            strRemoteAddr = "";
        }

        strXRealIP = strXRealIP.toLowerCase().trim();
        strRemoteAddr = strRemoteAddr.toLowerCase().trim();
        if (strXRealIP.equals("localhost") || strXRealIP.equals("127.0.0.1")) {
            strXRealIP = "";
        }

        if (strRemoteAddr.equals("localhost") || strRemoteAddr.equals("127.0.0.1")) {
            strRemoteAddr = "";
        }

        if (strXRealIP.equals("") && !strRemoteAddr.equals("")) {
            strReturnIP = strRemoteAddr;
        } else if (!strXRealIP.equals("") && strRemoteAddr.equals("")) {
            strReturnIP = strXRealIP;
        } else if (!strXRealIP.equals("") && !strRemoteAddr.equals("") && strXRealIP.equals(strRemoteAddr)) {
            strReturnIP = strRemoteAddr;
        } else if (!strXRealIP.equals("") && !strRemoteAddr.equals("") && !strXRealIP.equals(strRemoteAddr)) {
            if (isInnerIP(strXRealIP) && !isInnerIP(strRemoteAddr)) {
                strReturnIP = strRemoteAddr;
            } else if (!isInnerIP(strXRealIP) && isInnerIP(strRemoteAddr)) {
                strReturnIP = strXRealIP;
            } else if (isInnerIP(strXRealIP) && isInnerIP(strRemoteAddr)) {
                strReturnIP = strXRealIP;
            } else {
                strReturnIP = strXRealIP;
            }
        }

        if (strReturnIP.trim().equals("") || strReturnIP.trim().equals("0:0:0:0:0:0:0:1")) {
            strReturnIP = "127.0.0.1";
        }

        return strReturnIP;
    }

    public static boolean isInnerIP(String ipAddress) {
        boolean isInnerIp = false;
        long ipNum = getIpNum(ipAddress);
        long aBegin = getIpNum("10.0.0.0");
        long aEnd = getIpNum("10.255.255.255");
        long bBegin = getIpNum("172.16.0.0");
        long bEnd = getIpNum("172.31.255.255");
        long cBegin = getIpNum("192.168.0.0");
        long cEnd = getIpNum("192.168.255.255");
        isInnerIp = isInner(ipNum, aBegin, aEnd) || isInner(ipNum, bBegin, bEnd) || isInner(ipNum, cBegin, cEnd) || ipAddress.equals("127.0.0.1");
        return isInnerIp;
    }

    private static long getIpNum(String ipAddress) {
        String[] ip = ipAddress.split("\\.");
        long a = (long)Integer.parseInt(ip[0]);
        long b = (long)Integer.parseInt(ip[1]);
        long c = (long)Integer.parseInt(ip[2]);
        long d = (long)Integer.parseInt(ip[3]);
        long ipNum = a * 256L * 256L * 256L + b * 256L * 256L + c * 256L + d;
        return ipNum;
    }

    private static boolean isInner(long userIp, long begin, long end) {
        return userIp >= begin && userIp <= end;
    }

    public static String ipFilter(String ip, String mask) {
        if (mask != null && !mask.trim().equals("")) {
            mask = mask.trim();
        } else {
            mask = "1111";
        }

        String fip = "";
        String tmp = "";
        String[] sArray = ip.split("\\.");

        for(int step = 0; step < sArray.length; ++step) {
            tmp = sArray[step];
            if (mask.length() > step && mask.substring(step, step + 1).equals("0")) {
                tmp = "*";
            }

            if (step == 0) {
                fip = fip + tmp;
            } else {
                fip = fip + "." + tmp;
            }
        }

        return fip;
    }

    public static String ipFilter(String ip, int n) {
        String fip = "";
        String tmp = "";
        String[] sArray = ip.split("\\.");

        for(int step = 0; step < sArray.length; ++step) {
            tmp = sArray[step];
            if (step > n - 1) {
                tmp = "*";
            }

            if (step == 0) {
                fip = fip + tmp;
            } else {
                fip = fip + "." + tmp;
            }
        }

        return fip;
    }

    public static boolean isInRange(String ip, String ipRange) {
        if (ip != null && !ip.trim().equals("")) {
            if (ipRange != null && !ipRange.trim().equals("")) {
                long curIpNum = getIpNum(ip);
                String[] ipRangeArray = ipRange.trim().split(",");

                for(int j = 0; j < ipRangeArray.length; ++j) {
                    String ipCol = "";
                    ipCol = ipRangeArray[j];
                    long ipNumS;
                    if (ipCol.indexOf("-") == -1) {
                        ipNumS = getIpNum(ipCol);
                        if (curIpNum == ipNumS) {
                            return true;
                        }
                    } else {
                        String[] ipRangeTmp = ipCol.trim().split("-");
                        ipNumS = getIpNum(ipRangeTmp[0]);
                        long ipNumE = getIpNum(ipRangeTmp[1]);
                        if (ipNumS < ipNumE) {
                            if (curIpNum >= ipNumS && curIpNum <= ipNumE) {
                                return true;
                            }
                        } else if (ipNumS > ipNumE) {
                            if (curIpNum >= ipNumE && curIpNum <= ipNumS) {
                                return true;
                            }
                        } else if (curIpNum == ipNumE) {
                            return true;
                        }
                    }
                }

                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public static String getLocalHostIp() {
        InetAddress localhost = null;

        try {
            localhost = InetAddress.getLocalHost();
        } catch (UnknownHostException var2) {
            var2.printStackTrace();
        }

        return localhost == null ? null : localhost.getHostAddress();
    }

    public static String getExternalIpAddress() {
        return ExternalIpAddressFetcher.getIP();
    }
}
