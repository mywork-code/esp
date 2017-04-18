package com.apass.gfb.framework.template;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "listening.freemarker")
public class ListeningFreeMarkerProperties {
    /**
     * 是否启用OSS
     */
    private boolean ossSwitch = false;
    /**
     * OSS Address
     */
    public String   ossAddress;

    public boolean isOssSwitch() {
        return ossSwitch;
    }

    public void setOssSwitch(boolean ossSwitch) {
        this.ossSwitch = ossSwitch;
    }

    public String getOssAddress() {
        return ossAddress;
    }

    public void setOssAddress(String ossAddress) {
        this.ossAddress = ossAddress;
    }
}
