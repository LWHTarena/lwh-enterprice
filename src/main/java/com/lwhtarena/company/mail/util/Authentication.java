package com.lwhtarena.company.mail.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @Author：liwh
 * @Description:
 * @Date 21:16 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class Authentication extends Authenticator {

    String username = null;
    String password = null;

    public Authentication() {
    }

    public Authentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        PasswordAuthentication pa = new PasswordAuthentication(this.username, this.password);
        return pa;
    }
}
