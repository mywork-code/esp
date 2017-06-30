package com.apass.esp.utils.mailUtils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by xianzhi.wang on 2017/5/18.
 */
public class MyAuthenticator extends Authenticator {
    private static volatile MyAuthenticator myAuthenticator;

    private MyAuthenticator() {
    }

    public static MyAuthenticator getInstance() {
        if (myAuthenticator == null) {
            synchronized (MyAuthenticator.class) {
                if (myAuthenticator == null) {
                    myAuthenticator = new MyAuthenticator();
                }
            }
        }
        return myAuthenticator;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("itsupport@apass.cn", "support0511");
    }

}
