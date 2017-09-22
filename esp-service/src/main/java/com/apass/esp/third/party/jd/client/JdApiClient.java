package com.apass.esp.third.party.jd.client;

import com.alibaba.fastjson.JSONObject;
import com.apass.esp.common.utils.UrlUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.*;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Service
public  class JdApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdApiClient.class);
    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final int TIMEOUT = 5000;

    @Autowired
    private JdTokenManager jdTokenManager;

    public <T> JdApiResponse<T> request(String method, JSONObject requestObject, String key, Class<T> clazz) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", UrlUtils.encode(method));
        params.put("app_key", UrlUtils.encode(JdConstants.APP_KEY));
        JSONObject token = jdTokenManager.getToken();
        params.put("access_token",
               UrlUtils.encode(token.getString("access_token")));
        params.put("timestamp", UrlUtils.encode(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")));
        params.put("format", UrlUtils.encode("json"));
        params.put("v", UrlUtils.encode(JdConstants.API_VERSION));
        String param_json = requestObject == null ? "{}" : requestObject.toJSONString();
        params.put("param_json", UrlUtils.encode(param_json));
        String url = UrlUtils.build(JdConstants.API_URL, params);
        Map<String, String> headerparams = new HashMap<>();
        headerparams.put("Content-Type", "application/json");
        String response = null;
        try {
            response = HttpClientUtils.getMethodGetContent(url, headerparams);
        } catch (Exception e) {
            LOGGER.error("response {} return is not 200, param_json {}", response,UrlUtils.encode(param_json));
            return  new JdApiResponse(key, "");
        }
        JdApiResponse res = new JdApiResponse(key, response, clazz);
        if (!res.isSuccess()) {
            if ("0010".equals(res.getResultCode()) && "0".equals(res.getCode())) {
                return res;
            }
            LOGGER.error("request error,token: {},url: {},response: {}", url, response);
        }
        return res;
    }

    public <T> JdApiResponse<T> requestWithParam(String method, JSONObject param, String key, Class<T> clazz) {
        JSONObject requestObject = new JSONObject();
        requestObject.put("param", param);
        return request(method, requestObject, key, clazz);
    }

    private static HttpUriRequest post0(String url, Map<String, String> params) {
        RequestBuilder requestBuilder = RequestBuilder.post().setCharset(CHARSET).setUri(url);
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                .setConnectionRequestTimeout(TIMEOUT)
                .setSocketTimeout(TIMEOUT)
                .setConnectTimeout(TIMEOUT).setCookieSpec(CookieSpecs.IGNORE_COOKIES);
        requestBuilder.setConfig(requestConfigBuilder.build());
        if (params != null && params.size() > 0) {
            List<NameValuePair> nvList = new ArrayList<>();
            for (Map.Entry<String, String> param : params.entrySet()) {
                Object value = param.getValue();
                if (value == null) {
                    continue;
                }
                nvList.add(new BasicNameValuePair(param.getKey(), value.toString()));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvList, CHARSET);
            requestBuilder.setEntity(formEntity);
        }
//        requestBuilder.addHeader("Connection", "Keep-Alive");
        return requestBuilder.build();

    }

    public String request(String method,String jdParams) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", UrlUtils.encode(method));
        params.put("app_key", UrlUtils.encode(JdConstants.APP_KEY));
        JSONObject token = jdTokenManager.getToken();
        params.put("access_token",
            UrlUtils.encode(token.getString("access_token")));
        params.put("timestamp", UrlUtils.encode(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")));
        params.put("format", UrlUtils.encode("json"));
        params.put("v", UrlUtils.encode(JdConstants.API_VERSION));
        String param_json = StringUtils.isNotBlank(jdParams) ? jdParams: "{}" ;
        params.put("param_json", UrlUtils.encode(param_json));
        String url = UrlUtils.build(JdConstants.API_URL, params);
        Map<String, String> headerparams = new HashMap<>();
        headerparams.put("Content-Type", "application/json");
        return HttpClientUtils.getMethodGetContent(url, headerparams);
    }
}
