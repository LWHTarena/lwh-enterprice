package com.lwhtarena.company.mail.util;

import com.lwhtarena.company.analyze.util.AnalyzeUtil;
import com.lwhtarena.company.mail.obj.Mail;
import com.lwhtarena.company.mail.obj.Message;
import com.lwhtarena.company.mail.obj.SmtpSrv;
import com.lwhtarena.company.sys.util.StringUtil;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Session;
import javax.mail.Message.RecipientType;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * @Author：liwh
 * @Description:
 * @Date 21:14 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class MailUtil {

    public static SmtpSrv build(ResourceBundleMessageSource messageSource) {
        SmtpSrv srv = new SmtpSrv();
        srv.setHost(messageSource.getMessage("mail.server.host", null, "localhost", null));
        srv.setUser(messageSource.getMessage("mail.server.login.user", null, "guest", null));
        srv.setPassword(messageSource.getMessage("mail.server.login.password", null, "guest", null));
        srv.setSender(messageSource.getMessage("mail.server.sender", null, "guest@localhost", null));
        if (messageSource.getMessage("mail.server.auth", null, "true", null).trim().equalsIgnoreCase("true")) {
            srv.setAuth(true);
        } else {
            srv.setAuth(false);
        }
        return srv;
    }

    public static int verify(SmtpSrv srv) {
        if ((srv.getHost() == null) || (srv.getHost().trim().equals(""))) {
            return -3;
        }
        if ((srv.getUser() == null) || (srv.getUser().trim().equals(""))) {
            return -4;
        }
        if ((srv.getPassword() == null) || (srv.getPassword().trim().equals(""))) {
            return -5;
        }
        return 0;
    }

    public static Mail verify(Mail mail, ResourceBundleMessageSource messageSource) {
        if ((mail.getMessage().getRecipient() == null) || (mail.getMessage().getRecipient().trim().equals(""))) {
            int status = -2;
            mail.setStatus(status);
            return mail;
        }
        int status = verify(mail.getSmtpSrv());
        if (status < 0) {
            mail.setStatus(status);
            return mail;
        }
        if (!StringUtil.emailTest(mail.getMessage().getRecipient())) {
            status = -6;
            mail.setStatus(status);
            return mail;
        }
        SmtpSrv srv = mail.getSmtpSrv();
        Message message = mail.getMessage();
        String subject = message.getSubject();
        if ((subject == null) || (subject.trim().equals(""))) {
            if (messageSource != null) {
                subject = messageSource.getMessage("mail.subject.title.default", null, "New mail from {$tag:sender$}", null);
            } else {
                subject = "New mail from {$tag:sender$}";
            }
        }
        subject = AnalyzeUtil.replace(subject, "tag", "sender", srv.getSender());
        message.setSubject(subject);
        if ((message.getTxtType() == null) || (message.getTxtType().trim().equals(""))) {
            message.setTxtType("text/html");
        }
        String charset = messageSource.getMessage("mail.charset", null, "UTF-8", null);
        message.setCharset(charset);
        mail.setMessage(message);
        return mail;
    }

    public static int send(Mail mail, ResourceBundleMessageSource messageSource, boolean build) {
        if ((build) && (messageSource != null)) {
            SmtpSrv srv = build(messageSource);
            mail.setSmtpSrv(srv);
        }
        mail = verify(mail, messageSource);
        if (mail.getStatus() < 0) {
            return mail.getStatus();
        }
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", mail.getSmtpSrv().getHost());
            if (mail.getSmtpSrv().isAuth()) {
                props.put("mail.smtp.auth", "true");
            } else {
                props.put("mail.smtp.auth", "false");
            }
            Authentication authentication = new Authentication(mail.getSmtpSrv().getUser(), mail.getSmtpSrv().getPassword());
            Session s = Session.getDefaultInstance(props, (Authenticator) authentication);

            String debug = messageSource.getMessage("mail.debug", null, "false", null);
            if (debug.trim().equalsIgnoreCase("true")) {
                s.setDebug(true);
            } else {
                s.setDebug(false);
            }
            MimeMessage message = new MimeMessage(s);
            MimeMultipart mp = new MimeMultipart();

            InternetAddress from = new InternetAddress(mail.getSmtpSrv().getSender());
            message.setFrom(from);

            InternetAddress[] to = new InternetAddress[1];
            to[0] = new InternetAddress(mail.getMessage().getRecipient());
            message.setRecipients(RecipientType.TO, to);
            message.setSubject(mail.getMessage().getSubject(), mail.getMessage().getCharset());

            BodyPart html = new MimeBodyPart();

            html.setContent(mail.getMessage().getBody(), mail.getMessage().getTxtType() + ";charset=" + mail.getMessage().getCharset());
            mp.addBodyPart(html);
            message.setContent(mp);

            message.saveChanges();

            Transport transport = s.getTransport("smtp");
            transport.connect(mail.getSmtpSrv().getHost(), mail.getSmtpSrv().getUser(),
                    mail.getSmtpSrv().getPassword());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return 0;
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return -1;
    }
}
