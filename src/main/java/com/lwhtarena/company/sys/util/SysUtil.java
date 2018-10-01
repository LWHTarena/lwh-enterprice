package com.lwhtarena.company.sys.util;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:49 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class SysUtil {

    public static String uploadTarget(ResourceBundleMessageSource messageSource, String file, String targetFile, long uid) {
        String target = "";
        if (targetFile != null && targetFile.trim().equals("tempRandom")) {
            target = "temp/" + StringUtil.randomString(6) + "." + FileUtil.getFileExt(file);
        } else {
            String ext = FileUtil.getFileExt(file);
            String appDir = FileUtil.appPath();
            String rootDir = messageSource.getMessage("file.upload.path.root", (Object[])null, "uploadFiles", (Locale)null).toLowerCase();
            target = target + rootDir;
            (new StringBuilder(String.valueOf(appDir))).append(File.separator).append(rootDir).toString();
            String outFilePath = messageSource.getMessage("file.upload.result.path", (Object[])null, "yyyy/MM-dd", (Locale)null);
            String outFmt = messageSource.getMessage("file.upload.result.name", (Object[])null, "original", (Locale)null);
            SimpleDateFormat fmt = new SimpleDateFormat(outFilePath);
            outFilePath = fmt.format(new Timestamp((new Date()).getTime()));
            if (outFmt == null) {
                outFmt = "";
            }

            if (outFmt.equalsIgnoreCase("original")) {
                outFmt = FileUtil.getFileName(file);
            } else if (outFmt.equalsIgnoreCase("random")) {
                outFmt = StringUtil.randomString(6);
            } else {
                fmt = new SimpleDateFormat(outFmt);
                outFmt = fmt.format(new Timestamp((new Date()).getTime()));
            }

            if (uid > 0L) {
                outFmt = outFmt + "-" + uid;
            }

            target = target + "/" + outFilePath + "/" + outFmt + "." + ext;
        }

        return target;
    }

    public static File getConfigFile(ResourceBundleMessageSource messageSource, String target) {
        String configFile = messageSource.getMessage("configFile." + target, (Object[])null, (Locale)null);
        String separator;
        if (File.separator.equals("/")) {
            separator = File.separator;
        } else {
            separator = "\\";
        }

        String classesPath = FileUtil.classesPath();
        String endChar = classesPath.substring(classesPath.length() - 1, classesPath.length());
        if (!endChar.equals("/") && !endChar.equals("\\")) {
            configFile = classesPath + separator + configFile;
        } else {
            configFile = classesPath + configFile;
        }

        if (!File.separator.equals("/")) {
            configFile = StringUtil.strReplace(configFile, "/", separator);
        }

        File file = new File(configFile);
        return file.exists() ? file : null;
    }

    public static long srvRuntime() {
        String srvRunFile = FileUtil.webinfPath() + "/srvstart";
        String separator = FileUtil.separator();
        String absPath = FileUtil.dir(srvRunFile);
        if (!File.separator.equals("/")) {
            absPath = StringUtil.strReplace(absPath, "/", separator);
        }

        File fp = new File(absPath.toString());
        if (!fp.exists()) {
            return -1L;
        } else {
            String line = (String)FileUtil.readLineInFile(srvRunFile, "UTF-8").get(0);
            return line != null && !line.trim().equals("") ? Long.valueOf(line) : 0L;
        }
    }

    public static String getLocalMac(String delimiter, boolean caps) {
        StringBuffer sb = new StringBuffer();

        try {
            InetAddress ia = InetAddress.getLocalHost();
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

            for(int i = 0; i < mac.length; ++i) {
                if (i != 0 && delimiter != null && !delimiter.equals("")) {
                    sb.append("-");
                }

                int temp = mac[i] & 255;
                String str = Integer.toHexString(temp);
                if (str.length() == 1) {
                    sb.append("0" + str);
                } else {
                    sb.append(str);
                }
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return caps ? sb.toString().toUpperCase() : sb.toString().toLowerCase();
    }

    public static int getCpuRatioLinux() {
        try {
            Map<?, ?> map1 = cpuInfoLinux();
            Thread.sleep(200L);
            Map<?, ?> map2 = cpuInfoLinux();
            long user1 = Long.parseLong(map1.get("user").toString());
            long nice1 = Long.parseLong(map1.get("nice").toString());
            long system1 = Long.parseLong(map1.get("system").toString());
            long idle1 = Long.parseLong(map1.get("idle").toString());
            long user2 = Long.parseLong(map2.get("user").toString());
            long nice2 = Long.parseLong(map2.get("nice").toString());
            long system2 = Long.parseLong(map2.get("system").toString());
            long idle2 = Long.parseLong(map2.get("idle").toString());
            long total1 = user1 + system1 + nice1;
            long total2 = user2 + system2 + nice2;
            float total = (float)(total2 - total1);
            long totalIdle1 = user1 + nice1 + system1 + idle1;
            long totalIdle2 = user2 + nice2 + system2 + idle2;
            float totalidle = (float)(totalIdle2 - totalIdle1);
            float cpusage = total / totalidle * 100.0F;
            return (int)cpusage;
        } catch (InterruptedException var29) {
            var29.printStackTrace();
            return 0;
        }
    }

    public static Map<?, ?> cpuInfoLinux() {
        InputStreamReader inputs = null;
        BufferedReader buffer = null;
        HashMap map = new HashMap();

        try {
            inputs = new InputStreamReader(new FileInputStream("/proc/stat"));
            buffer = new BufferedReader(inputs);
            String line = "";

            while(true) {
                line = buffer.readLine();
                if (line == null) {
                    break;
                }

                if (line.startsWith("cpu")) {
                    StringTokenizer tokenizer = new StringTokenizer(line);
                    ArrayList temp = new ArrayList();

                    while(tokenizer.hasMoreElements()) {
                        String value = tokenizer.nextToken();
                        temp.add(value);
                    }

                    map.put("user", temp.get(1));
                    map.put("nice", temp.get(2));
                    map.put("system", temp.get(3));
                    map.put("idle", temp.get(4));
                    map.put("iowait", temp.get(5));
                    map.put("irq", temp.get(6));
                    map.put("softirq", temp.get(7));
                    map.put("stealstolen", temp.get(8));
                    break;
                }
            }
        } catch (Exception var15) {
            var15.printStackTrace();
        } finally {
            try {
                buffer.close();
                inputs.close();
            } catch (Exception var14) {
                var14.printStackTrace();
            }

        }

        return map;
    }

    public static int getCpuRatioWin() {
        try {
            String procCmd = System.getenv("windir") + "//system32//wbem//wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
            Thread.sleep(200L);
            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
            if (c0 != null && c1 != null) {
                long idletime = c1[0] - c0[0];
                long busytime = c1[1] - c0[1];
                return Double.valueOf((double)(100L * busytime) * 1.0D / (double)(busytime + idletime)).intValue();
            } else {
                return 0;
            }
        } catch (Exception var7) {
            var7.printStackTrace();
            return 0;
        }
    }

    private static long[] readCpu(Process proc) {
        long[] retn = new long[2];

        try {
            proc.getOutputStream().close();
            InputStreamReader ir = new InputStreamReader(proc.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line = input.readLine();
            if (line != null && line.length() >= 10) {
                int capidx = line.indexOf("Caption");
                int cmdidx = line.indexOf("CommandLine");
                int rocidx = line.indexOf("ReadOperationCount");
                int umtidx = line.indexOf("UserModeTime");
                int kmtidx = line.indexOf("KernelModeTime");
                int wocidx = line.indexOf("WriteOperationCount");
                long idletime = 0L;
                long kneltime = 0L;
                long usertime = 0L;

                while((line = input.readLine()) != null) {
                    if (line.length() >= wocidx) {
                        String caption = substring(line, capidx, cmdidx - 1).trim();
                        String cmd = substring(line, cmdidx, kmtidx - 1).trim();
                        if (cmd.indexOf("wmic.exe") < 0) {
                            String s1 = substring(line, kmtidx, rocidx - 1).trim();
                            String s2 = substring(line, umtidx, wocidx - 1).trim();
                            if (!caption.equals("System Idle Process") && !caption.equals("System")) {
                                if (s1.length() > 0) {
                                    kneltime += Long.valueOf(s1);
                                }

                                if (s2.length() > 0) {
                                    usertime += Long.valueOf(s2);
                                }
                            } else {
                                if (s1.length() > 0) {
                                    idletime += Long.valueOf(s1);
                                }

                                if (s2.length() > 0) {
                                    idletime += Long.valueOf(s2);
                                }
                            }
                        }
                    }
                }

                retn[0] = idletime;
                retn[1] = kneltime + usertime;
                long[] var22 = retn;
                return var22;
            }

            return null;
        } catch (Exception var31) {
            var31.printStackTrace();
        } finally {
            try {
                proc.getInputStream().close();
            } catch (Exception var30) {
                var30.printStackTrace();
            }

        }

        return null;
    }

    private static String substring(String src, int start_idx, int end_idx) {
        byte[] b = src.getBytes();
        String tgt = "";

        for(int i = start_idx; i <= end_idx; ++i) {
            tgt = tgt + (char)b[i];
        }

        return tgt;
    }
}

