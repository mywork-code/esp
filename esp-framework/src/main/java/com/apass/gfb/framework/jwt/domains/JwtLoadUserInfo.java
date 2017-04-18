package com.apass.gfb.framework.jwt.domains;

/**
 * @description 用户登陆信息
 * @author lixining
 * @version $Id: UserInfo.java, v 0.1 2015年10月28日 下午1:33:56 lixining Exp $
 */
public class JwtLoadUserInfo {
    /**
     * 用户账号
     */
    private String username;
    /**
     * Token
     */
    private String password;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 加密盐
     */
    private String salt;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}
