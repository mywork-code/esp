package com.apass.esp.third.party.jd.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public abstract class JdApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdApiClient.class);
    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final int TIMEOUT = 5000;
    static {
        HttpClientHelper.config(20000, 20000);

    }

    public <T> JdApiResponse<T> request(String method, JSONObject requestObject, String key, Class<T> clazz) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("method", UrlUtils.encode(method));
        params.put("app_key", UrlUtils.encode(JdConstants.APP_KEY));
        JSONObject token = JdTokenManager.getInstance().getToken();
        params.put("access_token", UrlUtils.encode(token.getString("access_token")));
        params.put("timestamp", UrlUtils.encode(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")));
        params.put("format", UrlUtils.encode("json"));
        params.put("v", UrlUtils.encode(JdConstants.API_VERSION));
        String param_json = requestObject == null ? "{}" : requestObject.toJSONString();
        params.put("param_json", UrlUtils.encode(param_json));

        String url = UrlUtils.build(JdConstants.API_URL, params);
//        String response = HttpClientHelper.getUriRequestContent(url, true, null, null, "utf-8");
        HttpUriRequest httpResult = post0(url,params);
        String response = OpenClient.execute(httpResult, false);
        JdApiResponse res = new JdApiResponse(key, response, clazz);
        if (!res.isSuccess()) {
            if ("0010".equals(res.getResultCode()) && "0".equals(res.getCode())) {
                return res;
            }
            LOGGER.error("request error,token: {},url: {},response: {}", token, url, response);
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
}
