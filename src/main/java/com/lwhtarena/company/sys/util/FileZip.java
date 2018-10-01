package com.lwhtarena.company.sys.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.*;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:31 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class FileZip {
    static final int BUFFER = 8192;
    private File zipFile;

    public FileZip(String pathName) {
        this.zipFile = new File(pathName);
    }

    public void compress(String... pathName) {
        ZipOutputStream out = null;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.zipFile);
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
            out = new ZipOutputStream(cos);
            String basedir = "";

            for(int i = 0; i < pathName.length; ++i) {
                this.zip(new File(pathName[i]), out, basedir);
            }

            out.close();
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        }
    }

    public static boolean unZip(File zipFile, String descDir, List<String> urlList) {
        boolean flag = false;
        File pathFile = new File(descDir);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        ZipFile zip = null;

        try {
            zip = new ZipFile(zipFile, Charset.forName("gbk"));
            Enumeration entries = zip.entries();

            while(true) {
                InputStream in;
                String outPath;
                do {
                    if (!entries.hasMoreElements()) {
                        flag = true;
                        zip.close();
                        return flag;
                    }

                    ZipEntry entry = (ZipEntry)entries.nextElement();
                    String zipEntryName = entry.getName();
                    in = zip.getInputStream(entry);
                    outPath = (descDir + zipEntryName).replace("/", File.separator);
                    File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                } while((new File(outPath)).isDirectory());

                urlList.add(outPath);
                OutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[2048];

                int len;
                while((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }

                in.close();
                out.close();
            }
        } catch (IOException var15) {
            var15.printStackTrace();
            return flag;
        }
    }

    public void zip(String srcPathName) {
        File file = new File(srcPathName);
        if (!file.exists()) {
            throw new RuntimeException(srcPathName + "不存在！");
        } else {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(this.zipFile);
                CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
                ZipOutputStream out = new ZipOutputStream(cos);
                String basedir = "";
                this.zip(file, out, basedir);
                out.close();
            } catch (Exception var7) {
                throw new RuntimeException(var7);
            }
        }
    }

    private void zip(File file, ZipOutputStream out, String basedir) {
        if (file.isDirectory()) {
            this.compressDirectory(file, out, basedir);
        } else {
            this.compressFile(file, out, basedir);
        }

    }

    private void compressDirectory(File dir, ZipOutputStream out, String basedir) {
        if (dir.exists()) {
            File[] files = dir.listFiles();

            for(int i = 0; i < files.length; ++i) {
                this.zip(files[i], out, basedir + dir.getName() + "/");
            }

        }
    }

    private void compressFile(File file, ZipOutputStream out, String basedir) {
        if (file.exists()) {
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                ZipEntry entry = new ZipEntry(basedir + file.getName());
                out.putNextEntry(entry);
                byte[] data = new byte[8192];

                int count;
                while((count = bis.read(data, 0, 8192)) != -1) {
                    out.write(data, 0, count);
                }

                bis.close();
            } catch (Exception var8) {
                throw new RuntimeException(var8);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        FileZip zc = new FileZip("D:/tmp/biao.zip");
        zc.compress("/D:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/lerxV5/\\tmp\\portalTemplate_2018_07_05_16_00_04_937.portal", "/D:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/lerxV5/\\tmp\\md5", "/D:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/lerxV5/\\report");
        File f = new File("D:\\tmp\\biao.zip");
        List<String> urlList = new ArrayList();
        unZip(f, "D:\\tmp\\test\\", urlList);
    }
}
