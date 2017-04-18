package com.apass.gfb.framework.security.userdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.apass.gfb.framework.security.domains.SecurityMenus;


/**
 * @description Security Login Users Info
 * @author lixining
 * @version $Id: SecurityUserDetails.java, v 0.1 2015年7月25日 下午8:48:26 lixining Exp $
 */
public class ListeningCustomSecurityUserDetails implements UserDetails {

    /**  */
    private static final long                      serialVersionUID = -5769319137904785295L;
    /**
     * 权限资源
     */
    private Collection<? extends GrantedAuthority> authorities;
    /**
     * Security Menus
     */
    private SecurityMenus                          securityMenus;
    /**
     * 邮件
     */
    private String                                 email;
    /**
     * 是否可用
     */
    private boolean                                enabled;
    /**
     * 手机
     */
    private String                                 mobile;
    /**
     * 密码
     */
    private String                                 password;
    /**
     * 真实姓名
     */
    private String                                 realName;
    /**
     * 用户名
     */
    private String                                 username;
    /**
     * 用户ID
     */
    private String                                 userId;
    /**
     * 商户Code
     */
    private String                                 merchantCode;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getRealName() {
        return realName;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SecurityMenus getSecurityMenus() {
        return securityMenus;
    }

    public void setSecurityMenus(SecurityMenus securityMenus) {
        this.securityMenus = securityMenus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof ListeningCustomSecurityUserDetails) {
            return username.equals(((ListeningCustomSecurityUserDetails) rhs).username);
        }
        return false;
    }

    /**
     * Returns the hashcode of the {@code username}.
     */
    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
