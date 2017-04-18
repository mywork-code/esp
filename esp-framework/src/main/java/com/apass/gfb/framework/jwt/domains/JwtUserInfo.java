package com.apass.gfb.framework.jwt.domains;

/**
 * 
 * @description 用户登陆信息 
 *
 * @author lixining
 * @version $Id: UserInfo.java, v 0.1 2015年10月28日 下午1:33:56 lixining Exp $
 */
public class JwtUserInfo {
    /**
     * 用户账号
     */
    private String username;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * Token
     */
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
