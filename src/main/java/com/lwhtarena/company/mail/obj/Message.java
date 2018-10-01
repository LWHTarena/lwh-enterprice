package com.lwhtarena.company.mail.obj;

/**
 * @Author：liwh
 * @Description:
 * @Date 21:12 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class Message {

    private String subject;
    private String body;
    private String recipient;
    private String txtType;
    private String charset;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getTxtType() {
        return txtType;
    }

    public void setTxtType(String txtType) {
        this.txtType = txtType;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
