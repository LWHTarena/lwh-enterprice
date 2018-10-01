package com.lwhtarena.company.sys.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:33 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class HttpPostUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpPostUtil.class);
    private static final int TIME_OUT = 100000;
    private static final String CHARSET = "utf-8";

    public HttpPostUtil() {
    }

    public static String uploadFile(File file, String RequestURL) throws IOException {
        String result = null;
        String BOUNDARY = "letv";
        String PREFIX = "--";
        String LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";

        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(100000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "utf-8");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            if (file != null) {
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/ctet-stream" + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1048576];
                boolean var13 = false;

                int len;
                while((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }

                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                int res = conn.getResponseCode();
                log.info("response code:" + res);
                log.info("request success");
                InputStream input = conn.getInputStream();
                StringBuffer sb1 = new StringBuffer();

                int ss;
                while((ss = input.read()) != -1) {
                    sb1.append((char)ss);
                }

                result = sb1.toString();
                result = new String(result.getBytes("iso8859-1"), "utf-8");
                log.info("result : " + result);
            }

            return result;
        } catch (MalformedURLException var19) {
            var19.printStackTrace();
            return null;
        } catch (IOException var20) {
            var20.printStackTrace();
            return null;
        }
    }
}
