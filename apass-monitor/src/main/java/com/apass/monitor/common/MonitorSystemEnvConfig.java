package com.apass.monitor.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.StringTokenizer;

@Component
public class MonitorSystemEnvConfig {
    /**
     *  当前系统的活动Profile
     */
    @Value("${spring.profiles.active}")
    private String activeProfile;

    /**
     * 是否为生产环境
     */
    public boolean isPROD() {
        return checkProfileMatch("production");
    }

    /**
     * 是否为UAT环境
     */
    public boolean isUAT() {
        return checkProfileMatch("uat");
    }

    /**
     * 是否为开发环境
     */
    public boolean isDEV() {
        return checkProfileMatch("dev");
    }

    // Check Profiles
    private boolean checkProfileMatch(String compare) {
        if (StringUtils.isBlank(activeProfile)) {
            return false;
        }
        StringTokenizer st = new StringTokenizer(activeProfile, ",");
        boolean result = false;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (StringUtils.isBlank(token)) {
                continue;
            }
            if (StringUtils.equals(compare, token.trim())) {
                result = true;
                break;
            }
        }
        return result;
    }
}
