package com.apass.gfb.framework.environment;

import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemEnvConfig {
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

    public String getEve(){
        if(isDEV()){
            return "sit";
        }
        if(isUAT()){
            return "uat";
        }
        if(isPROD()){
            return "prod";
        }

        return "环境有误 ";
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
