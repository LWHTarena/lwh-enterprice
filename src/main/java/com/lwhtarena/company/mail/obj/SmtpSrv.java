package com.lwhtarena.company.mail.obj;

/**
 * @Author：liwh
 * @Description:
 * @Date 21:13 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class SmtpSrv {

    private String sender;
    private String host;
    private String user;
    private String password;
    private boolean auth;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }
}
