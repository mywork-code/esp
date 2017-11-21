package com.apass.esp.third.party.weizhi.client;

import com.alibaba.fastjson.JSONObject;
import com.apass.esp.service.wz.WeiZhiTokenService;
import com.apass.gfb.framework.utils.HttpClientUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/11/21.
 */
@Service
public class WeiZhiMessageClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeiZhiMessageClient.class);
    @Autowired
    private WeiZhiTokenService weiZhiTokenService;
    /**
     * 删除消息
     */
    public WeiZhiResponse delMsg(String messageId,String messageType) throws Exception{
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        BasicNameValuePair param1 = new BasicNameValuePair("token", weiZhiTokenService.getTokenFromRedis());
        BasicNameValuePair param2 = new BasicNameValuePair("clientId",WeiZhiConstants.CLIENT_ID);
        BasicNameValuePair param3 = new BasicNameValuePair("messageId",messageId);
        BasicNameValuePair param4 = new BasicNameValuePair("messageType",messageType);
        parameters.add(param1);
        parameters.add(param2);
        parameters.add(param3);
        parameters.add(param4);

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);

        String responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_MESSAGE_DEL, entity);

        LOGGER.info("----del message ------ response:{}",responseJson);
        WeiZhiResponse response = (WeiZhiResponse) JSONObject.parse(responseJson);
        return response;
    }

    /**
     * 获取消息
     */
    public WeiZhiResponse<Map> getMsg(String messageType) throws Exception{
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        BasicNameValuePair param1 = new BasicNameValuePair("token", weiZhiTokenService.getTokenFromRedis());
        BasicNameValuePair param2 = new BasicNameValuePair("clientId",WeiZhiConstants.CLIENT_ID);
        BasicNameValuePair param3 = new BasicNameValuePair("messageType",messageType);
        parameters.add(param1);
        parameters.add(param2);
        parameters.add(param3);

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);

        String responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_MESSAGE_GET, entity);
        LOGGER.info("----get message ------ response:{}",responseJson);
        WeiZhiResponse response = (WeiZhiResponse) JSONObject.parse(responseJson);
        return response;
    }
}
