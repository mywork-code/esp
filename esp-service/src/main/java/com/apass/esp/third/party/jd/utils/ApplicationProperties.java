package com.apass.esp.third.party.jd.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.StringTokenizer;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Component
public class ApplicationProperties {

    public static String activeProfile;

    @Value("${spring.profiles.active}")
    public void setEnv(String env) {
        ApplicationProperties.activeProfile = env;
    }

    /**
     * 是否为生产环境
     */
    public static boolean isPROD() {
        return checkProfileMatch("production");
    }

    /**
     * 是否为UAT环境
     */
    public static boolean isUAT() {
        return checkProfileMatch("uat");
    }

    /**
     * 是否为开发环境
     */
    public static boolean isDEV() {
        return checkProfileMatch("dev");
    }

    // Check Profiles
    private static boolean checkProfileMatch(String compare) {
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
