package com.apass.gfb.framework.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description Listening Security Properties
 * @author listening
 * @version $Id: ListeningSecurityProperties.java, v 0.1 2015年8月11日 下午11:04:02 listening Exp $
 */
@ConfigurationProperties(prefix = "listening.security")
public class ListeningSecurityProperties {
    /**
     * Init Default User
     */
    private boolean withDefaultUser  = true;
    /**
     * Logout Success Url
     */
    private String  logoutSuccessUrl = "/login";

    public boolean isWithDefaultUser() {
        return withDefaultUser;
    }

    public void setWithDefaultUser(boolean withDefaultUser) {
        this.withDefaultUser = withDefaultUser;
    }

    public String getLogoutSuccessUrl() {
        return logoutSuccessUrl;
    }

    public void setLogoutSuccessUrl(String logoutSuccessUrl) {
        this.logoutSuccessUrl = logoutSuccessUrl;
    }

}
