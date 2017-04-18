package com.apass.gfb.framework.security.domains;

/**
 * 
 * @description Security User Info
 * @author lixining
 * @version $Id: SecurityUserInfo.java, v 0.1 2015年8月10日 下午4:38:01 lixining Exp $
 */
public class SecurityUserInfo {
    /**
     * 用户账户
     */
    private String  username;
    /**
     * 用户密码
     */
    private String  password;
    /**
     * 是否可用
     */
    private boolean enabled;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
