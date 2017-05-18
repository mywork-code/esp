package com.apass.esp.utils.mailUtils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by xianzhi.wang on 2017/5/18.
 */
public class MyAuthenticator extends Authenticator {
    String userName = null;
    String password = null;

    public MyAuthenticator() {

    }

    public MyAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }

}
