package com.apass.gfb.framework.security.domains;

/**
 * 
 * @description Client Get LoginInfo
 *
 * @author lixining
 * @version $Id: SecurityAuthentication.java, v 0.1 2016年1月8日 下午3:32:41 lixining Exp $
 */
public class SecurityAuthentication {
    /**
     * Set is Login
     */
    private boolean login;
    /**
     * UserName
     */
    private String  username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

}
