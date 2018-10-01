package com.lwhtarena.company.mail.obj;

/**
 * @Author：liwh
 * @Description:
 * @Date 21:12 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class Mail {

    private SmtpSrv smtpSrv;
    private Message message;
    private int status;

    public SmtpSrv getSmtpSrv() {
        return smtpSrv;
    }

    public void setSmtpSrv(SmtpSrv smtpSrv) {
        this.smtpSrv = smtpSrv;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
