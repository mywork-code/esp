package com.apass.esp.third.party.jd.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.common.utils.UrlUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Service
public class JdTokenClient extends JdApiClient{
    private static final Logger LOGGER = LoggerFactory.getLogger(JdTokenClient.class);

    public JSONObject getToken() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("grant_type", "password");
        params.put("app_key", JdConstants.APP_KEY);
        params.put("app_secret", JdConstants.APP_SECRET);
        params.put("state", "0");
        params.put("username", UrlUtils.encode(JdConstants.USERNAME));
        params.put("password", DigestUtils.md5Hex(JdConstants.PASSWORD));
        Map<String, String> headerparams = new HashMap<>();
        headerparams.put("Content-Type", "application/json");
        String response = null;
        try {
            response = HttpClientUtils.getMethodGetContent(UrlUtils.build(JdConstants.TOKEN_URL, params), headerparams);
        } catch (Exception e) {
            LOGGER.error("response {} return is not 200", response);
        }
        LOGGER.info("getToken,{}", response);
        return JSON.parseObject(response);
    }


    private JSONObject refreshToken() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("grant_type", "refresh_token");
        params.put("app_key", JdConstants.APP_KEY);
        params.put("app_secret", JdConstants.APP_SECRET);
        params.put("state", "0");
        params.put("username", UrlUtils.encode(JdConstants.USERNAME));
        params.put("password", DigestUtils.md5Hex(JdConstants.PASSWORD));
        Map<String, String> headerparams = new HashMap<>();
        headerparams.put("Content-Type", "application/json");
        String response = null;
        try {
            response = HttpClientUtils.getMethodGetContent(UrlUtils.build(JdConstants.TOKEN_URL, params), headerparams);
        } catch (Exception e) {
            LOGGER.error("response {} return is not 200",response);
        }
        LOGGER.info("refreshToken,{}", response);
        return JSON.parseObject(response);
    }
}
