package com.lwhtarena.company.ip;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class ExternalIpAddressFetcher {

    private String myExternalIpAddress;

    public ExternalIpAddressFetcher(String externalIpProviderUrl) {
        String returnedhtml = this.fetchExternalIpProviderHTML(externalIpProviderUrl);
        this.parse(returnedhtml);
    }

    private String fetchExternalIpProviderHTML(String externalIpProviderUrl) {
        InputStream in = null;
        HttpURLConnection httpConn = null;

        try {
            URL url = new URL(externalIpProviderUrl);
            httpConn = (HttpURLConnection)url.openConnection();
            HttpURLConnection.setFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
            in = httpConn.getInputStream();
            byte[] bytes = new byte[1024];
            int offset = 0;

            for(int numRead = 0; offset < bytes.length && (numRead = in.read(bytes, offset, bytes.length - offset)) >= 0; offset += numRead) {
                ;
            }

            String receivedString = new String(bytes, "UTF-8");
            String var10 = receivedString;
            return var10;
        } catch (MalformedURLException var20) {
            var20.printStackTrace();
        } catch (IOException var21) {
            var21.printStackTrace();
        } finally {
            try {
                in.close();
                httpConn.disconnect();
            } catch (Exception var19) {
                var19.printStackTrace();
            }

        }

        return null;
    }

    private void parse(String html) {
        Pattern pattern = Pattern.compile("(\\d{1,3})[.](\\d{1,3})[.](\\d{1,3})[.](\\d{1,3})", 2);

        for(Matcher matcher = pattern.matcher(html); matcher.find(); this.myExternalIpAddress = matcher.group(0)) {
            ;
        }

    }

    public String getMyExternalIpAddress() {
        return this.myExternalIpAddress;
    }

    public static String getIP() {
        ExternalIpAddressFetcher fetcher = new ExternalIpAddressFetcher("http://checkip.dyndns.org/");
        return fetcher.getMyExternalIpAddress();
    }
}
