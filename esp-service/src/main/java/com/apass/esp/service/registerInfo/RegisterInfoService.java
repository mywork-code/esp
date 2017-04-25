package com.apass.esp.service.registerInfo;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.manage.CommonResponse;
import com.apass.esp.service.purchase.PurchaseService;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;

@Service
public class RegisterInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseService.class);

    private static final String CHECKUSER_OLDORNEW="/checkUser/oldOrNew";
    private static final String CHECKUSER_REGISTER="/checkUser/register";

    /**
     * 供房帮服务地址
     */
    @Value("${gfb.service.url}")
    protected String              gfbServiceUrl;
    /**
     * 判断是否为新用户
     * @param mobile
     * @return
     */
    public CommonResponse isNewCustomer(String mobile,String InviterId){
    	try {
			 Map<String,Object> map=new HashMap<String,Object>();
			 map.put("mobile", mobile);
			 map.put("InviterId", InviterId);
	         String requestUrl = gfbServiceUrl + CHECKUSER_OLDORNEW;
	         String reqJson = GsonUtils.toJson(map);
	         StringEntity entity = new StringEntity(reqJson, ContentType.APPLICATION_JSON);
	         String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
	         CommonResponse response=GsonUtils.convertObj(responseJson, CommonResponse.class);
	         return response;
    	} catch (Exception e) {
			LOGGER.error("判断是否新用户失败！", e);
		}
		return null;
    }
    /**
     * 新用户注册
     * @param mobile
     * @return
     */
    public CommonResponse regsitNew(String mobile,String password,String InviterId){
    	try {
			 Map<String,Object> map=new HashMap<String,Object>();
			 map.put("mobile", mobile);
			 map.put("password", password);
			 map.put("InviterId", InviterId);
	         String requestUrl = gfbServiceUrl + CHECKUSER_REGISTER;
	         String reqJson = GsonUtils.toJson(map);
	         StringEntity entity = new StringEntity(reqJson, ContentType.APPLICATION_JSON);
	         String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
	         CommonResponse response = GsonUtils.convertObj(responseJson, CommonResponse.class);
	         return response;
    	} catch (Exception e) {
			LOGGER.error("新用户注册失败！", e);
		}
		return null;
    }
}
