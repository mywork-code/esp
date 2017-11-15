package com.apass.esp.third.party.weizhi.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.common.utils.UrlUtils;
import com.apass.esp.third.party.weizhi.entity.TokenEntity;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;

@Service
public class WeiZhiTokenClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeiZhiTokenClient.class);
    @Autowired
    private WeiZhiConstants weiZhiConstants;
    /**
     * 获取微知token
     * @return
     */
    public TokenEntity getToken(){
    	String timestamp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    	Map<String, String> params = new LinkedHashMap<>();
        params.put("grant_type", WeiZhiConstants.GRANT_TYPE);
        params.put("client_id", WeiZhiConstants.CLIENT_ID);
        params.put("user_name", UrlUtils.encode(WeiZhiConstants.USER_NAME));
        params.put("password",DigestUtils.md5Hex(WeiZhiConstants.PASSWORD));
        params.put("timestamp",timestamp);
        params.put("sign", weiZhiConstants.getSign(timestamp));
		StringEntity entity = new StringEntity(GsonUtils.toJson(params), ContentType.APPLICATION_JSON);
		String responseJson = null;
		TokenEntity token=null;
        try {
        	responseJson=HttpClientUtils.getMethodPostResponse(WeiZhiConstants.TOKEN_URL,entity);
        	LOGGER.info("微知获取token返回Json数据："+responseJson);
        	if(null ==responseJson){
        	 LOGGER.info("微知获取token失败！");
        	 return null;
        	}
        	WeiZhiTokenResponse response = GsonUtils.convertObj(responseJson, WeiZhiTokenResponse.class);       
            if(null !=response && response.getResult()==0){
            	token=response.getData();
            }
		} catch (Exception e) {
			LOGGER.error("getToken response {} return is not 200");
		}
		return token;
    }
}
