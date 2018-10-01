package com.lwhtarena.company.web.common;

import com.lwhtarena.company.analyze.util.AnalyzeUtil;
import com.lwhtarena.company.mail.obj.Mail;
import com.lwhtarena.company.mail.obj.Message;
import com.lwhtarena.company.mail.obj.SmtpSrv;
import com.lwhtarena.company.mail.util.MailUtil;
import com.lwhtarena.company.sys.obj.TimeSpanUnit;
import com.lwhtarena.company.sys.util.*;
import com.lwhtarena.company.web.portal.obj.Sms;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
public class CaptchaUtil {

    public static int send(ResourceBundleMessageSource messageSource, HttpServletRequest request, HttpSession session,
                           String portal, int targetType, String target) {
        /*
         * 生成验证字符串 authStr
         */
        int charMode; // 1纯数字 2纯字母 0混合
        String vartmp = messageSource.getMessage("vcode.sendchar.mode", null, "0", null);
        if (StringUtil.isNumber(vartmp)) {
            charMode = Integer.valueOf(vartmp);
        } else {
            charMode = 0;
        }

        int charLength; // 长度或字符数量
        vartmp = messageSource.getMessage("vcode.sendchar.length", null, "4", null);
        if (StringUtil.isNumber(vartmp)) {
            charLength = Integer.valueOf(vartmp);
        } else {
            charLength = 4;
        }

        String authStr = RandomNumUtil.getRandStr(charLength, charMode);

        /*
         * 获取本服务器的安全字符串 security(无则自动生成并读取)
         */
        String securityStr = SecurityUtil.readWords(messageSource);

        /*
         * 根据配置文件获得允许的截止时间剩余
         */
        vartmp = messageSource.getMessage("vcode.sendchar.effective", null, "30m", null);
        TimeSpanUnit tsu = TimeUtil.unixLenAtFmt(vartmp);
        long endTime = System.currentTimeMillis() + tsu.getSeconds();

        if (session.getAttribute("lastSend") != null) {
            long lastSend = (long) session.getAttribute("lastSend");
            if (lastSend > 0 && (System.currentTimeMillis() - lastSend < tsu.getSeconds())) {
                return -8;
            }
        }

        /*
         * 生成加密字符
         */
        String md5 = StringUtil.md5(String.valueOf(endTime) + "|" + authStr + "|" + securityStr);
        String encryptedStr = String.valueOf(endTime) + "|" + authStr + "|" + md5;

        session.setAttribute("validateEnd", endTime);
        session.setAttribute("encryptedStr", encryptedStr);
        String content;
        if (targetType == 0) {
            content = FileUtil.readRes(messageSource, "template_reg_mail");
        } else {
            content = FileUtil.readRes(messageSource, "template_reg_sms");
        }

        content = AnalyzeUtil.replace(content, "tag", "vcode", authStr);
        String dtfmt = messageSource.getMessage("datetime.fmt.default", null, "yyyy-MM-dd HH:mm:ss", null);
        content = AnalyzeUtil.replace(content, "tag", "effective", TimeUtil.coverLongToStr(endTime, dtfmt));
        String spanTitle = messageSource.getMessage("title.date.span." + tsu.getSpanTitle(), null, " ", null);

        content = AnalyzeUtil.replace(content, "tag", "period", tsu.getSpanSize() + spanTitle);
        content = AnalyzeUtil.replace(content, "tag", "encryptedStr", encryptedStr);

        String url = request.getScheme() + "://";
        url += request.getHeader("host");
        url += request.getRequestURI();

        content = AnalyzeUtil.replace(content, "tag", "srv", url);
        content = AnalyzeUtil.replace(content, "tag", "portalName", portal);

        // 发送
        int r = -1;
        if (targetType == 0) { // 如果验证码发向邮箱

            session.setAttribute("targetMode", 0);

            Mail m = new Mail();
            SmtpSrv srv = MailUtil.build(messageSource);
            Message mes = new Message();
            mes.setRecipient(target);
            mes.setBody(content);
            String subject = messageSource.getMessage("mail.subject.title.default", null,
                    "New mail from {$tag:sender$}", null);
            subject = AnalyzeUtil.replace(subject, "tag", "portalName", portal);
            mes.setSubject(subject);

            m.setMessage(mes);
            m.setSmtpSrv(srv);

            r = MailUtil.send(m, messageSource, true);
            if (r == 0) {
                session.setAttribute("lastSend", System.currentTimeMillis());
            }
        } else { // 如果采用手机验证
            session.setAttribute("targetMode", 1);
            Sms sms = SmsUtil.build(messageSource);
            if (sms.getUrl() == null || sms.getUrl().trim().equals("")) {
                r = SmsUtil.sendOnTencent(sms, target, content).getResult();
            } else {
                r = SmsUtil.sendOnIhuyi(sms, target, content).getResult();
            }

        }

        return r;

    }
}
