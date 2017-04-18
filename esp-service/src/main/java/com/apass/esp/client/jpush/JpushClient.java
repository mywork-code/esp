package com.apass.esp.client.jpush;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;
import com.apass.gfb.framework.utils.RSAUtils;
import com.google.gson.JsonObject;


@Component
public class JpushClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpushClient.class);
    
    @Value("${ajp.base.url}")
    private String baseUrl;
    
    @Value("${esp-to-ajp.publickey}")
    private String espClientPublicKey;
    
    private static final String jpushAliasUrl = "/message/pushAccountMsg";  //给个人推送消息
    
    /**
     * 向指定别名的客户端（个人用户）发送消息
     * @throws Exception 
     */
    public void jpushSendPushAlias(String userId, String mailTitle, String mailContent) throws Exception{
        
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("userId", userId);
        paramsMap.put("mailTitle", mailTitle);
        paramsMap.put("mailContent", mailContent);
        paramsMap.put("busType", "esp");
        
        String address = baseUrl + jpushAliasUrl;
        String paramsJson = GsonUtils.toJson(paramsMap);
        paramsMap.clear();
        
        String paramsRSAJson=RSAUtils.encryptByPublicKey(paramsJson, espClientPublicKey);
        paramsMap.put("data", paramsRSAJson);
        
        String reqJson = GsonUtils.toJson(paramsMap);
        StringEntity entity = new StringEntity(reqJson, ContentType.APPLICATION_JSON);
        String respJson = HttpClientUtils.getMethodPostResponse(address, entity);
        LOGGER.info("jpushSendPushAlias" + respJson);
        JsonObject jsonObject = GsonUtils.convertObj(respJson, JsonObject.class);
        String status = GsonUtils.getJsonObjectAsString(jsonObject, "status");
        
        if(!"1".equals(status)){
            throw new BusinessException("交易完成消息推送失败");
        }
    }
}
