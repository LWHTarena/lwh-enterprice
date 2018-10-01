package com.lwhtarena.company.sys.util;

import com.lwhtarena.company.font.obj.WaterMark;
import com.lwhtarena.company.image.util.ImageUtil;
import com.lwhtarena.company.sys.obj.FileInf;
import com.lwhtarena.company.sys.obj.IniReader;
import com.lwhtarena.company.sys.obj.ResFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:34 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class FileUtil {
    static int length = 134217728;

    public FileUtil() {
    }

    public static String realpath(String contextPath) {
        return Thread.currentThread().getContextClassLoader().getResource(contextPath).getPath();
    }

    public String realFileDir(String paramValue) {
        return this.getClass().getResource(paramValue).getPath();
    }

    public static String classesPath() {
        String classesPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        return classesPath;
    }

    public static String appPath() {
        String classesPath = classesPath();
        classesPath = classesPath();
        int pos = classesPath.indexOf("WEB-INF");
        return classesPath.substring(0, pos);
    }

    public static String webinfPath() {
        String classesPath = classesPath();
        classesPath = classesPath();
        int pos = classesPath.indexOf("WEB-INF");
        return classesPath.substring(0, pos + 7);
    }

    public static String getFileExt(String fileName) {
        int p = fileName.lastIndexOf(".");
        String extType;
        if (p > 0) {
            extType = fileName.substring(p + 1, fileName.length());
            extType = extType.toLowerCase().trim();
        } else {
            extType = "";
        }

        return extType;
    }

    public static String getFileName(String fileName) {
        boolean find = true;

        while(find) {
            int p = fileName.lastIndexOf(".");
            int separator = fileName.lastIndexOf(File.separator);
            if (p > 0) {
                if (separator < p) {
                    fileName = fileName.substring(0, p);
                } else {
                    find = false;
                }
            } else {
                find = false;
            }
        }

        return fileName;
    }

    public static boolean imgFileCheck(String fileName, String imgFileEtcStr) {
        String extType = getFileExt(fileName);
        if (extType.equals("")) {
            return false;
        } else {
            String[] sArray = imgFileEtcStr.split(",");

            for(int step = 0; step < sArray.length; ++step) {
                if (extType.trim().equalsIgnoreCase(sArray[step].trim())) {
                    return true;
                }
            }

            return false;
        }
    }

    public static String repairFilePath(String path) {
        for(path = StringUtil.strReplace(path, "\\", "/"); path.indexOf("//") != -1; path = StringUtil.strReplace(path, "//", "/")) {
            ;
        }

        path = StringUtil.strReplace(path, "/", File.separator);
        return path;
    }

    public static String repairUrl(String path) {
        path = StringUtil.strReplace(path, "\\", "/");
        int pos = path.indexOf("://");
        String pre = null;
        String suf = null;
        if (pos != -1) {
            pre = path.substring(0, pos);
            suf = path.substring(pos + 3, path.length());
        } else {
            suf = path;
        }

        while(suf.indexOf("//") != -1) {
            suf = StringUtil.strReplace(suf, "//", "/");
        }

        if (pre == null) {
            path = suf;
        } else {
            path = pre + "://" + suf;
        }

        return path;
    }

    public static String getFileFromPath(String path) {
        for(path = StringUtil.strReplace(path, "\\", "/"); path.indexOf("//") != -1; path = StringUtil.strReplace(path, "//", "/")) {
            ;
        }

        int p = path.lastIndexOf("/");
        String filename;
        if (p > 0) {
            filename = path.substring(p + 1, path.length());
        } else {
            filename = path;
        }

        return filename;
    }

    public static String dir(String file) {
        boolean isUrl = false;
        if (file.indexOf("://") != -1) {
            isUrl = true;
            file = StringUtil.strReplace(file, "://", "___tmp___lerx_char___");
        }

        for(file = StringUtil.strReplace(file, "\\", "/"); file.indexOf("//") != -1; file = StringUtil.strReplace(file, "//", "/")) {
            ;
        }

        int p = file.lastIndexOf("/");
        String filename;
        if (p > 0) {
            filename = file.substring(0, p + 1);
        } else {
            filename = file;
        }

        if (isUrl) {
            filename = StringUtil.strReplace(filename, "\\", "/");
            filename = StringUtil.strReplace(filename, "___tmp___lerx_char___", "://");
        } else if (File.separator.trim().equals("\\")) {
            filename = StringUtil.strReplace(filename, "/", "\\");
        }

        return filename;
    }

    public static boolean extTypeChk(String extType, String allExtType) {
        String[] typeArray = allExtType.split(",");

        for(int step = 0; step < typeArray.length; ++step) {
            if (typeArray[step].trim().equalsIgnoreCase(extType)) {
                return true;
            }
        }

        return false;
    }

    public static String separator() {
        String separator;
        if (File.separator.equals("/")) {
            separator = File.separator;
        } else {
            separator = "\\";
        }

        return separator;
    }

    public static boolean delete(String path, String file) {
        String separator = separator();
        if (!File.separator.equals("/")) {
            path = StringUtil.strReplace(path, "/", separator);
        }

        if (!path.substring(path.length() - 1, path.length()).equals(separator)) {
            path = path + separator;
        }

        String fe = path + file;
        File f = new File(fe);
        if (f.exists()) {
            f.delete();
            return true;
        } else {
            return false;
        }
    }

    public static boolean delete(String path) {
        File f = new File(path);
        if (f.exists()) {
            f.delete();
            File fc = new File(path);
            return !fc.exists();
        } else {
            return false;
        }
    }

    public static void copy(String oldPath, String newPath) {
        try {
            String dir = dir(newPath);
            File f = new File(dir);
            if (!f.exists()) {
                f.mkdirs();
            }

            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fos = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];

                while((byteread = inStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteread);
                }

                fos.flush();
                fos.close();
                inStream.close();
                fos = null;
                inStream = null;
            }
        } catch (Exception var9) {
            System.out.println("复制单个文件操作出错");
            var9.printStackTrace();
        }

    }

    public void copyFolder(String oldPath, String newPath) {
        try {
            (new File(newPath)).mkdirs();
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;

            for(int i = 0; i < file.length; ++i) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + temp.getName().toString());
                    byte[] b = new byte[5120];

                    int len;
                    while((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }

                    output.flush();
                    output.close();
                    input.close();
                    output = null;
                    input = null;
                }

                if (temp.isDirectory()) {
                    this.copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception var11) {
            System.out.println("复制整个文件夹内容操作出错");
            var11.printStackTrace();
        }

    }

    public static boolean download(HttpServletResponse response, String path, String filename) {
        try {
            String charset = "utf-8";
            OutputStream o = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(o);
            byte[] b = new byte[500];
            File fileLoad = new File(path, filename);
            response.setCharacterEncoding(charset);
            response.setHeader("Content-type", "text/html;charset=" + charset);
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            response.setContentType("application/x-tar");
            long fileLength = fileLoad.length();
            String length = String.valueOf(fileLength);
            response.setHeader("Content_Length", length);
            FileInputStream in = new FileInputStream(fileLoad);
            boolean var12 = false;

            int n;
            while((n = in.read(b)) != -1) {
                bos.write(b, 0, n);
            }

            in.close();
            bos.flush();
            bos.close();
            o.flush();
            o.close();
            in = null;
            bos = null;
            o = null;
            return true;
        } catch (Exception var13) {
            return false;
        }
    }

    public static List<String> readLineInFile(String file, String charset) {
        ArrayList strList = new ArrayList();

        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, charset);
            BufferedReader reader = new BufferedReader(isr);

            String strLine;
            while((strLine = reader.readLine()) != null) {
                if (strLine != null && !strLine.trim().equals("")) {
                    strList.add(strLine);
                }
            }

            fis.close();
            isr.close();
            reader.close();
            fis = null;
            isr = null;
            reader = null;
            return strList;
        } catch (FileNotFoundException var7) {
            var7.printStackTrace();
            return null;
        } catch (IOException var8) {
            var8.printStackTrace();
            return null;
        }
    }

    public static String readLargeFile4(String filepath, String breaks, String charset) {
        String allTemp = "";
        Charset cset = Charset.forName(charset);
        FileInputStream fis = null;
        FileChannel fcin = null;

        try {
            fis = new FileInputStream(filepath);
            fcin = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(5120);

            while(fcin.read(buffer) != -1) {
                buffer.flip();
                allTemp = allTemp + cset.decode(buffer);
                buffer.clear();
            }

            fcin.close();
            fis.close();
            fcin = null;
            fis = null;
        } catch (IOException var8) {
            var8.printStackTrace();
        }

        if (allTemp != null && !allTemp.trim().equals("") && breaks != null && !breaks.trim().equals("")) {
            if (breaks.trim().equalsIgnoreCase("p")) {
                allTemp = allTemp.replaceAll("[\\t\\n\\r]", "</p><p>");
                allTemp = allTemp.replaceAll("</p><p></p><p>", "</p><p>");
                allTemp = "<p>" + allTemp + "</p>";
            } else if (breaks.trim().equalsIgnoreCase("br")) {
                allTemp = allTemp.replaceAll("[\\t\\n\\r]", "<br>");
            }
        }

        return allTemp;
    }

    public static String readLargeFile3(String filepath, boolean breaks, String charset) throws IOException {
        File file = new File(filepath);
        LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        String tempAll = "";
        String lineSeparator = System.lineSeparator();

        try {
            while(it.hasNext()) {
                String line = it.nextLine();
                tempAll = tempAll + lineSeparator + line;
            }
        } finally {
            LineIterator.closeQuietly(it);
        }

        it.close();
        it = null;
        return tempAll;
    }

    public static String readLargeFile2(String filepath, boolean breaks, String charset) throws IOException {
        File file = new File(filepath);
        Scanner sc = new Scanner(file);

        String temp;
        String d;
        for(d = ""; sc.hasNextLine(); d = d + new String(temp.getBytes("ISO-8859-1"), charset)) {
            temp = System.lineSeparator() + sc.nextLine();
        }

        sc.close();
        sc = null;
        return d;
    }

    public static String readLargeFile(String filepath, boolean breaks, String charset) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(new File(filepath), "r");

        String s;
        String allTmp;
        for(allTmp = ""; (s = raf.readLine()) != null; allTmp = allTmp + System.lineSeparator() + s) {
            ;
        }

        raf.close();
        raf = null;
        return allTmp;
    }

    public static String readFile(String file, boolean breaks, String charset) {
        String out = "";

        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, charset);
            BufferedReader reader = new BufferedReader(isr);

            String strLine;
            while((strLine = reader.readLine()) != null) {
                if (strLine != null && !strLine.trim().equals("")) {
                    if (breaks) {
                        out = out + "\r\n";
                    }

                    out = out + strLine;
                }
            }

            fis.close();
            isr.close();
            reader.close();
            fis = null;
            isr = null;
            reader = null;
            return out;
        } catch (FileNotFoundException var8) {
            var8.printStackTrace();
            return null;
        } catch (IOException var9) {
            var9.printStackTrace();
            return null;
        }
    }

    public static boolean findStrInFile(String tagStr, String file, String charset) {
        if (tagStr == null) {
            return false;
        } else {
            try {
                FileInputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis, charset);
                BufferedReader reader = new BufferedReader(isr);

                String strLine;
                while((strLine = reader.readLine()) != null) {
                    if (tagStr.indexOf(strLine) != -1) {
                        fis.close();
                        isr.close();
                        reader.close();
                        fis = null;
                        isr = null;
                        reader = null;
                        return true;
                    }
                }

                fis.close();
                isr.close();
                reader.close();
                fis = null;
                isr = null;
                reader = null;
                return false;
            } catch (FileNotFoundException var7) {
                var7.printStackTrace();
                return false;
            } catch (IOException var8) {
                var8.printStackTrace();
                return false;
            }
        }
    }

    public static boolean writeStringToFile(String absFile, String body, boolean writeCreateTime, String charset) {
        String separator = separator();
        String absPath = dir(absFile);
        if (!File.separator.equals("/")) {
            absPath = StringUtil.strReplace(absPath, "/", separator);
        }

        File fp = new File(absPath.toString());
        if (!fp.exists()) {
            fp.mkdirs();
        }

        File f = new File(absFile);
        if (f.exists()) {
            f.delete();
        }

        BufferedReader br = new BufferedReader(new StringReader(body));

        try {
            FileOutputStream fw = new FileOutputStream(absFile);
            String strt = null;
            String strn = "\n";

            while((strt = br.readLine()) != null) {
                fw.write(strt.getBytes(charset));
                fw.write(strn.getBytes());
            }

            if (writeCreateTime) {
                String strCreateDateTime = "";
                Date pd_now = new Date();
                SimpleDateFormat IndexHtmlDate = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
                strCreateDateTime = strCreateDateTime + "<!--Created Time:";
                strCreateDateTime = strCreateDateTime + IndexHtmlDate.format(pd_now);
                strCreateDateTime = strCreateDateTime + "-->";
                fw.write(strCreateDateTime.getBytes(charset));
            }

            fw.flush();
            fw.close();
            br.close();
            fw = null;
            br = null;
        } catch (FileNotFoundException var15) {
            var15.printStackTrace();
        } catch (UnsupportedEncodingException var16) {
            var16.printStackTrace();
        } catch (IOException var17) {
            var17.printStackTrace();
        }

        return true;
    }

    public static boolean writeStringToFile(String absPath, String fileName, String body, boolean writeCreateTime, String charset) {
        String separator = separator();
        if (!File.separator.equals("/")) {
            absPath = StringUtil.strReplace(absPath, "/", separator);
        }

        File fp = new File(absPath.toString());
        if (!fp.exists()) {
            fp.mkdirs();
        }

        if (!absPath.substring(absPath.length() - 1, absPath.length()).equals(separator)) {
            absPath = absPath + separator;
        }

        String absFile = absPath + fileName;
        File f = new File(absFile);
        if (f.exists()) {
            f.delete();
        }

        BufferedReader br = new BufferedReader(new StringReader(body));

        try {
            FileOutputStream fw = new FileOutputStream(absFile);
            String strt = null;
            String strn = "\n";

            while((strt = br.readLine()) != null) {
                fw.write(strt.getBytes(charset));
                fw.write(strn.getBytes());
            }

            if (writeCreateTime) {
                String strCreateDateTime = "";
                Date pd_now = new Date();
                SimpleDateFormat IndexHtmlDate = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
                strCreateDateTime = strCreateDateTime + "<!--Created Time:";
                strCreateDateTime = strCreateDateTime + IndexHtmlDate.format(pd_now);
                strCreateDateTime = strCreateDateTime + "-->";
                fw.write(strCreateDateTime.getBytes(charset));
            }

            fw.flush();
            fw.close();
            br.close();
            fw = null;
            br = null;
        } catch (FileNotFoundException var16) {
            var16.printStackTrace();
        } catch (UnsupportedEncodingException var17) {
            var17.printStackTrace();
        } catch (IOException var18) {
            var18.printStackTrace();
        }

        return true;
    }

    public static FileInf upload(MultipartFile file, String target) throws IOException {
        FileInf fi = new FileInf();
        InputStream in = file.getInputStream();
        String appDir = appPath();
        String outFileFull = appDir + File.separator + target;
        outFileFull = StringUtil.strReplace(outFileFull, "/", File.separator);
        outFileFull = StringUtil.strReplace(outFileFull, "//", "/");
        outFileFull = StringUtil.strReplace(outFileFull, "\\\\", "\\");
        String dirpath = dir(outFileFull);
        String fileTmp = getFileFromPath(outFileFull);
        fi.setRealPath(dirpath);
        fi.setExt(getFileExt(fileTmp));
        fi.setNameNoExt(getFileName(fileTmp));
        fi.setSize(file.getSize());
        File filePath = new File(dirpath);
        if (!filePath.exists()) {
            filePath.mkdirs();
            filePath.setExecutable(true, false);
            filePath.setReadable(true, false);
        }

        FileOutputStream fos = new FileOutputStream(outFileFull);
        byte[] buffer = new byte[1024];
        boolean var12 = false;

        int length;
        while((length = in.read(buffer)) > 0) {
            fos.write(buffer, 0, length);
        }

        in.close();
        fos.flush();
        fos.close();
        in = null;
        fos = null;
        filePath = new File(outFileFull);
        if (!filePath.exists()) {
            return null;
        } else {
            filePath.setReadable(true);
            filePath.setReadable(true, false);

            String url;
            for(url = StringUtil.strReplace(target, "\\", "/"); url.indexOf("//") != -1; url = StringUtil.strReplace(url, "//", "/")) {
                ;
            }

            fi.setUrl(url);
            fi.setAddtime(System.currentTimeMillis());
            fi.setFullPath(outFileFull);
            FileInputStream fis = new FileInputStream(outFileFull);
            String md5 = DigestUtils.md5DigestAsHex(IOUtils.toByteArray(fis));
            IOUtils.closeQuietly(fis);
            fi.setMd5(md5);
            fis.close();
            fis = null;
            filePath = new File(fi.getFullPath());
            return filePath.exists() ? fi : null;
        }
    }

    public static FileInf upload(MultipartFile file, String target, int dw, int dh) throws IOException {
        FileInf fi = new FileInf();
        InputStream in = file.getInputStream();
        String appDir = appPath();
        String outFileFull = appDir + File.separator + target;
        outFileFull = StringUtil.strReplace(outFileFull, "/", File.separator);
        outFileFull = StringUtil.strReplace(outFileFull, "//", "/");
        outFileFull = StringUtil.strReplace(outFileFull, "\\\\", "\\");
        String dirpath = dir(outFileFull);
        String fileTmp = getFileFromPath(outFileFull);
        fi.setRealPath(dirpath);
        fi.setExt(getFileExt(fileTmp));
        fi.setNameNoExt(getFileName(fileTmp));
        fi.setSize(file.getSize());
        File filePath = new File(dirpath);
        if (!filePath.exists()) {
            filePath.mkdirs();
            filePath.setExecutable(true, false);
            filePath.setReadable(true, false);
        }

        FileOutputStream fos = new FileOutputStream(outFileFull);
        byte[] buffer = new byte[1024];
        boolean var14 = false;

        int length;
        while((length = in.read(buffer)) > 0) {
            fos.write(buffer, 0, length);
        }

        in.close();
        fos.flush();
        fos.close();
        in = null;
        fos = null;
        if (dw > 0 && dh > 0 && getFileExt(outFileFull).equalsIgnoreCase("jpg")) {
            ImageUtil.cutSmart(outFileFull, dw, dh, (WaterMark)null);
        }

        filePath = new File(outFileFull);
        if (!filePath.exists()) {
            return null;
        } else {
            filePath.setReadable(true);
            filePath.setReadable(true, false);

            String url;
            for(url = StringUtil.strReplace(target, "\\", "/"); url.indexOf("//") != -1; url = StringUtil.strReplace(url, "//", "/")) {
                ;
            }

            fi.setUrl(url);
            fi.setAddtime(System.currentTimeMillis());
            fi.setFullPath(outFileFull);
            FileInputStream fis = new FileInputStream(outFileFull);
            String md5 = DigestUtils.md5DigestAsHex(IOUtils.toByteArray(fis));
            IOUtils.closeQuietly(fis);
            fi.setMd5(md5);
            fis.close();
            fis = null;
            filePath = new File(fi.getFullPath());
            return filePath.exists() ? fi : null;
        }
    }

    public static String iniReader(ResourceBundleMessageSource messageSource, String iniFileKey, String section, String key) throws IOException {
        if (messageSource == null) {
            return null;
        } else {
            String iniFile = messageSource.getMessage("iniFile." + iniFileKey, (Object[])null, (Locale)null);
            if (iniFile == null) {
                iniFile = iniFileKey + ".ini";
            }

            String val = readKey(iniFile, section, key);
            if (val.equals(key)) {
                val = "";
            }

            return val;
        }
    }

    public static void iniAppWriter(ResourceBundleMessageSource messageSource, String iniFileKey, String section, String key, String value) {
        if (messageSource != null) {
            String iniFile = messageSource.getMessage("iniFile." + iniFileKey, (Object[])null, (Locale)null);

            try {
                IniReader reader = new IniReader(iniFile);
                reader.setValue(section, key, value);
            } catch (IOException var8) {
                var8.printStackTrace();
            }

        }
    }

    private static String readKey(String iniFile, String section, String key) throws IOException {
        String separator;
        if (File.separator.equals("/")) {
            separator = File.separator;
        } else {
            separator = "\\";
        }

        String classesPath = classesPath();
        String endChar = classesPath.substring(classesPath.length() - 1, classesPath.length());
        if (!endChar.equals("/") && !endChar.equals("\\")) {
            iniFile = classesPath + separator + iniFile;
        } else {
            iniFile = classesPath + iniFile;
        }

        String val = null;
        iniFile = StringUtil.strReplace(iniFile, "//", "/");
        IniReader reader = new IniReader(iniFile);
        val = reader.getValue(section, key);
        reader = null;
        if (val == null || val.trim().equals("")) {
            val = key;
        }

        return val;
    }

    public static List<ResFile> getResFiles(ResourceBundleMessageSource messageSource, String realpath) {
        List<ResFile> lfArray = new ArrayList();
        File file = new File(realpath);
        File[] fileList = file.listFiles();
        Arrays.sort(fileList, new FileUtil.CompratorByLastModified((FileUtil.CompratorByLastModified)null));
        new ResFile();
        File[] var9 = fileList;
        int var8 = fileList.length;

        for(int var7 = 0; var7 < var8; ++var7) {
            File resfile = var9[var7];
            ResFile lf = new ResFile();
            lf.setFilename(resfile.getName());
            lf.setRealpath(DesUtils.encrypt(messageSource, resfile.getAbsolutePath(), true));
            lf.setSize(resfile.length());
            lf.setLastModified(resfile.lastModified());
            lfArray.add(lf);
        }

        return lfArray;
    }

    public static String readRes(ResourceBundleMessageSource messageSource, String fileName) {
        String folder = messageSource.getMessage("res", (Object[])null, "res", (Locale)null);
        String resFile = webinfPath() + File.separator + folder + File.separator + fileName + ".res";
        resFile = repairFilePath(resFile);
        String charset = messageSource.getMessage("charset", (Object[])null, "utf-8", (Locale)null);
        charset = charset.toLowerCase();
        String txt = readLargeFile4(resFile, (String)null, charset);
        return txt;
    }

    public static boolean htmlBySniff(String fromurl, String realFile, String strCreateDateTime, String charset) {
        String folder = dir(realFile);
        File fp = new File(folder.toString());
        if (!fp.exists()) {
            fp.mkdirs();
        }

        String fileName = getFileFromPath(realFile);
        File f = new File(folder, fileName);
        if (f.exists()) {
            f.delete();
        }

        try {
            URL url = new URL(fromurl);
            URLConnection urlCon = url.openConnection();
            urlCon.setRequestProperty("Accept-Language", "zh-CN");
            urlCon.setRequestProperty("Accept-Charset", charset);
            urlCon.setConnectTimeout(30000);
            urlCon.setReadTimeout(30000);
            InputStreamReader isr = new InputStreamReader(urlCon.getInputStream(), charset);
            BufferedReader br = new BufferedReader(isr);
            String separator;
            if (File.separator.equals("/")) {
                separator = File.separator;
            } else {
                separator = "\\";
            }

            FileOutputStream fw = new FileOutputStream(folder + separator + fileName);
            String str = null;
            String strn = "\n";

            while((str = br.readLine()) != null) {
                fw.write(str.getBytes(charset));
                fw.write(strn.getBytes());
            }

            if (strCreateDateTime != null && !strCreateDateTime.trim().equals("")) {
                Date pd_now = new Date();
                SimpleDateFormat htmlDateFmt = new SimpleDateFormat(strCreateDateTime);
                strCreateDateTime = htmlDateFmt.format(pd_now);
                fw.write(strCreateDateTime.getBytes(charset));
            }

            fw.flush();
            fw.close();
            urlCon.getInputStream().close();
            urlCon = null;
            isr.close();
            br.close();
            urlCon = null;
            isr = null;
            fw = null;
            br = null;
            return true;
        } catch (IOException var18) {
            var18.printStackTrace();
            return false;
        }
    }

    private static class CompratorByLastModified implements Comparator<File> {
        private CompratorByLastModified(CompratorByLastModified compratorByLastModified) {
        }

        @Override
        public int compare(File f1, File f2) {
            long diff = f2.lastModified() - f1.lastModified();
            if (diff > 0L) {
                return 1;
            } else {
                return diff == 0L ? 0 : -1;
            }
        }

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }
}
