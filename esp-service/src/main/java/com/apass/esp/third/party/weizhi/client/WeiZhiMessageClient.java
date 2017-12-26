package com.apass.esp.third.party.weizhi.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.enums.JdMessageEnum;
import com.apass.esp.service.wz.WeiZhiTokenService;
import com.apass.esp.third.party.jd.entity.base.JdApiMessage;
import com.apass.gfb.framework.utils.HttpClientUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/11/21.
 */
@Component
public class WeiZhiMessageClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeiZhiMessageClient.class);
    @Autowired
    private WeiZhiTokenService weiZhiTokenService;
    @Autowired
    private WeiZhiConstants weiZhiConstants;
    /**
     * 删除消息
     */
    public WeiZhiResponse delMsg(Long messageId,int messageType) {
        try {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            BasicNameValuePair param1 = new BasicNameValuePair("token", weiZhiTokenService.getTokenFromRedis());
            BasicNameValuePair param2 = new BasicNameValuePair("clientId",weiZhiConstants.getClientId());
            BasicNameValuePair param3 = new BasicNameValuePair("messageId",messageId + "");
            BasicNameValuePair param4 = new BasicNameValuePair("messageType",messageType +"");
            parameters.add(param1);
            parameters.add(param2);
            parameters.add(param3);
            parameters.add(param4);

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);

            String responseJson = HttpClientUtils.getMethodPostResponse(weiZhiConstants.getWZRequestUrl(WeiZhiConstants.WZAPI_MESSAGE_DEL), entity);

            LOGGER.info("----del message ------ response:{}",responseJson);
            WeiZhiResponse response = JSONObject.parseObject(responseJson,WeiZhiResponse.class);
            return response;
        }catch (Exception e){
            LOGGER.error("del weizhi msg error,messageType={},messageId={}",messageType,messageId,e);
            return null;
        }

    }

    /**
     * 获取消息
     * type=5 订单妥投的区别对待
     */
    public List<JdApiMessage> getMsg(int messageType) throws Exception{
        Long startTime = System.currentTimeMillis();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        BasicNameValuePair param1 = new BasicNameValuePair("token", weiZhiTokenService.getTokenFromRedis());
        BasicNameValuePair param2 = new BasicNameValuePair("clientId",weiZhiConstants.getClientId());
        BasicNameValuePair param3 = new BasicNameValuePair("messageType",String.valueOf(messageType));
        parameters.add(param1);
        parameters.add(param2);
        parameters.add(param3);

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
        String responseJson = HttpClientUtils.getMethodPostResponse(weiZhiConstants.getWZRequestUrl(WeiZhiConstants.WZAPI_MESSAGE_GET), entity);
        LOGGER.info("----get message ------ response:{},接口响应时间:{}",responseJson,System.currentTimeMillis() - startTime);
        WeiZhiResponse<JSONArray> response = JSONObject.parseObject(responseJson,WeiZhiResponse.class);
        if (response.successResp()) {
            if(messageType == JdMessageEnum.DELIVERED_ORDER.getType()){
                List<JdApiMessage> results = new ArrayList<>();
                for (Object o : response.getData()) {
                    JdApiMessage jdApiMessage = new JdApiMessage();
                    jdApiMessage.setResult((JSONObject) o);
                    jdApiMessage.setType(JdMessageEnum.DELIVERED_ORDER.getType());
                    results.add(jdApiMessage);
                }
                return results;
            }else {
                List<JdApiMessage> results = new ArrayList<>();
                for (Object o : response.getData()) {
                    results.add(new JdApiMessage((JSONObject) o));
                }
                return results;
            }

        }
        return Collections.emptyList();
    }
}
