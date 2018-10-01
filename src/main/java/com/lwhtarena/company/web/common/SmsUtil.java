package com.lwhtarena.company.web.common;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.lwhtarena.company.sys.util.StringUtil;
import com.lwhtarena.company.web.portal.obj.Sms;
import com.lwhtarena.company.web.portal.obj.SmsReturn;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONException;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;


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
public class SmsUtil {

    public static Sms build(ResourceBundleMessageSource messageSource) {
        Sms sms = new Sms();
        sms.setAccount(messageSource.getMessage("sms.account", null, "", null));
        sms.setUrl(messageSource.getMessage("sms.url", null, "", null));
        sms.setPwd(messageSource.getMessage("sms.pwd", null, "", null));
        sms.setAppid(Integer.valueOf(messageSource.getMessage("sms.appid", null, "0", null)));
        sms.setCharset(messageSource.getMessage("charset", null, "UTF-8", null));
        if (sms.getPwd() == null || sms.getPwd().trim().equals("")) {
            return null;
        }
        return sms;
    }


    /*
     * 互亿无线、秒赛
     */
    public static SmsReturn sendOnIhuyi(Sms sms, String mobile, String content) {
        SmsReturn sr = new SmsReturn();
        if (sms == null) {
            sr.setResult(-11);
            return sr;
        }

        if (mobile == null || mobile.trim().equals("")) {
            sr.setResult(-13);
            return sr;
        }

        if (content == null || content.trim().equals("")) {
            sr.setResult(-12);
            return sr;
        }


        HttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(sms.getUrl());
        httpPost.setHeader("ContentType", "application/x-www-form-urlencoded;charset=" + sms.getCharset());

        /***
         * NameValuePair方式传参数已过时
         */
        NameValuePair[] data = {//提交短信
                new BasicNameValuePair("account", sms.getAccount()),
                new BasicNameValuePair("password", sms.getPwd()), //查看密码请登录用户中心->验证码、通知短信->帐户及签名设置->APIKEY
                //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
                new BasicNameValuePair("mobile", mobile),
                new BasicNameValuePair("content", content),
        };
        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(Arrays.asList(data),"UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse =client.execute(httpPost);
            String strResult =null;
            if(StringUtil.isNotNull(httpResponse)){
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());

                    Document doc = DocumentHelper.parseText(strResult);
                    Element root = doc.getRootElement();
                    String code = root.elementText("code");
                    if ("2".equals(code)) {
//                        System.out.println("短信提交成功");
                        sr.setResult(0);
                    }
                } else if (httpResponse.getStatusLine().getStatusCode() == 400) {
                    strResult = "Error Response: " + httpResponse.getStatusLine().toString();
                } else if (httpResponse.getStatusLine().getStatusCode() == 500) {
                    strResult = "Error Response: " + httpResponse.getStatusLine().toString();
                } else {
                    strResult = "Error Response: " + httpResponse.getStatusLine().toString();
                }
            }
        } catch (UnsupportedEncodingException e) {
            sr.setResult(-14);
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            sr.setResult(-14);
            e.printStackTrace();
        } catch (IOException e) {
            sr.setResult(-16);
            e.printStackTrace();
        } catch (DocumentException e) {
            sr.setResult(-15);
            e.printStackTrace();
        }

        return sr;

    }


    /*
     * 腾讯云短信
     */
    public static SmsReturn sendOnTencent(Sms sms, String mobile, String content) {
        SmsReturn sr = new SmsReturn();
        if (sms == null) {
            sr.setResult(-11);
            return sr;
        }

        if (mobile == null || mobile.trim().equals("")) {
            sr.setResult(-13);
            return sr;
        }

        if (content == null || content.trim().equals("")) {
            sr.setResult(-12);
            return sr;
        }

        try {

            SmsSingleSender ssender = new SmsSingleSender(sms.getAppid(), sms.getPwd());
            SmsSingleSenderResult result = null;
            try {
                result = ssender.send(0, "86", mobile, content, "", "");
            } catch (com.github.qcloudsms.httpclient.HTTPException e) {
                e.printStackTrace();
            }
            sr.setResult(0 - result.result);
            sr.setMsg(result.errMsg);
            sr.setFee(result.fee);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
            sr.setResult(-14);
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
            sr.setResult(-15);
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
            sr.setResult(-16);
        }

        return sr;

    }

}
