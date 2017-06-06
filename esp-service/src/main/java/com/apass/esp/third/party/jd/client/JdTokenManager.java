package com.apass.esp.third.party.jd.client;

import com.alibaba.fastjson.JSONObject;
import com.apass.gfb.framework.cache.CacheManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Service
public class JdTokenManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdTokenManager.class);
    private static final String JD_TOKEN_REDIS_KEY = "JD_TOKEN_REDIS_KEY";

    @Autowired
    private CacheManager cacheManager;

    private JdTokenManager() {
    }

    private volatile JSONObject token;

    private long lastUpdateTime;

    private long UpdateInterval = 6 * 3600 * 1000l;

    public JSONObject getToken() {
        if (token == null) {
            return getToken0();
        }
        if ((System.currentTimeMillis() - lastUpdateTime) > UpdateInterval) {
            LOGGER.info("lastUpdateTime {},UpdateInterval {} ",lastUpdateTime,UpdateInterval);
            token = getToken0();
        }
        return token;
    }


    private JSONObject getToken0() {
        String value = cacheManager.get(JD_TOKEN_REDIS_KEY);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        lastUpdateTime = System.currentTimeMillis();
        return JSONObject.parseObject(value);
    }


}
