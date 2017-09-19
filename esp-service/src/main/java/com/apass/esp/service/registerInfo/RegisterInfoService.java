package com.apass.esp.service.registerInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.Response;
import com.apass.esp.service.purchase.PurchaseService;
import com.apass.gfb.framework.cache.CacheManager;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;

@Service
public class RegisterInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseService.class);
    
    private static final String CHECKUSER_ISWECHATUSER="/checkUser/isWeChatUser";
    private static final String CHECKUSER_OLDORNEW="/checkUser/oldOrNew";
    private static final String CHECKUSER_REGISTER="/checkUser/register";
    private static final String CHECKUSER_GETINVITERINFO="/checkUser/getInviterInfo";
    private static final String CHECKUSER_ISOLDUSER="/checkUser/isOldUser";
    private static final String ESPCUSTOMER_ISFIRSTCREDIT="/espCustomer/isFirstCredit";
    private static final String ESPCUSTOMER_SAVEREFERENCEINFO="/espCustomer/saveReferenceInfo";

    /**
     * 供房帮服务地址
     */
    @Value("${gfb.service.url}")
    protected String              gfbServiceUrl;
	/**
	 * 缓存服务
	 */
	@Autowired
	private CacheManager cacheManager;
    
    /**
     * 判断是否为是微信端用户
     * @param mobile
     * @return
     */
    public Response isWeChatUser(String mobile){
    	try {
			 Map<String,Object> map=new HashMap<String,Object>();
			 map.put("mobile", mobile);
	         String requestUrl = gfbServiceUrl + CHECKUSER_ISWECHATUSER;
	         String reqJson = GsonUtils.toJson(map);
	         StringEntity entity = new StringEntity(reqJson, ContentType.APPLICATION_JSON);
	         String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
	         Response   response=GsonUtils.convertObj(responseJson, Response.class);
	         return response;
    	} catch (Exception e) {
			LOGGER.error("判断是否为是微信端用户失败！", e);
		}
		return null;
    }
    /**
     * 判断是否为新用户
     * @param mobile
     * @return
     */
    public Response isNewCustomer(String mobile,String InviterId){
    	try {
			 Map<String,Object> map=new HashMap<String,Object>();
			 map.put("mobile", mobile);
			 map.put("InviterId", InviterId);
	         String requestUrl = gfbServiceUrl + CHECKUSER_OLDORNEW;
	         String reqJson = GsonUtils.toJson(map);
	         StringEntity entity = new StringEntity(reqJson, ContentType.APPLICATION_JSON);
	         String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
	         Response   response=GsonUtils.convertObj(responseJson, Response.class);
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
    public Response regsitNew(String mobile,String password,String InviterId){
    	try {
			 Map<String,Object> map=new HashMap<String,Object>();
			 map.put("mobile", mobile);
			 map.put("password", password);
			 map.put("InviterId", InviterId);
	         String requestUrl = gfbServiceUrl + CHECKUSER_REGISTER;
	         String reqJson = GsonUtils.toJson(map);
	         StringEntity entity = new StringEntity(reqJson, ContentType.APPLICATION_JSON);
	         String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
	         Response response = GsonUtils.convertObj(responseJson, Response.class);
	         return response;
    	} catch (Exception e) {
			LOGGER.error("新用户注册失败！", e);
		}
		return null;
    }
    /**
     * 获取邀请人信息
     * @param mobile
     * @return
     */
    public Response getInviterInfo(String InviterId){
    	try {
			 Map<String,Object> map=new HashMap<String,Object>();
			 map.put("InviterId", InviterId);
	         String requestUrl = gfbServiceUrl + CHECKUSER_GETINVITERINFO;
	         String reqJson = GsonUtils.toJson(map);
	         StringEntity entity = new StringEntity(reqJson, ContentType.APPLICATION_JSON);
	         String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
	         Response response = GsonUtils.convertObj(responseJson, Response.class);
	         return response;
    	} catch (Exception e) {
			LOGGER.error("新用户注册失败！", e);
		}
		return null;
    }
    //判断发送短信是否过于频繁
    public Boolean isSendMes(String smsType,String moblie){
    	String tiemKey = smsType + "_" + moblie+"_time";
		String oldtiem = cacheManager.get(tiemKey);
		Date nowtime=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Boolean Flage=true;
		try {
			  if(null !=oldtiem){
					long oldtiem2 = sdf.parse(oldtiem).getTime();
					long nowtime2=nowtime.getTime();
					int timeBetween=(int)((nowtime2-oldtiem2)/1000);
					if(timeBetween<120){
						Flage=false;
					}
			}else{
				String nowTime=sdf.format(nowtime);
			    cacheManager.set(tiemKey, nowTime, 6*60);
			}
		} catch (ParseException e) {
			LOGGER.error("发送短信失败！", e);
		}
    	return Flage;
    }

	/**
	 * 判断是否为老用户(在微信端和App端已经注册过的都为老用户)
	 * 
	 * @param mobile
	 * @return
	 */
	public Response isOldUser(String mobile, String InviterId) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", mobile);
			map.put("InviterId", InviterId);
			String requestUrl = gfbServiceUrl + CHECKUSER_ISOLDUSER;
			String reqJson = GsonUtils.toJson(map);
			StringEntity entity = new StringEntity(reqJson, ContentType.APPLICATION_JSON);
			String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
			Response response = GsonUtils.convertObj(responseJson, Response.class);
			return response;
		} catch (Exception e) {
			LOGGER.error("新用户注册失败！", e);
			return null;
		}
	}

	/**
	 * 判断是否为第一次获取额度
	 * 
	 * @param mobile
	 * @return
	 */
	public Response customerIsFirstCredit(String customerId) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("customerId", customerId);
			String requestUrl = gfbServiceUrl + ESPCUSTOMER_ISFIRSTCREDIT;
			String reqJson = GsonUtils.toJson(map);
			StringEntity entity = new StringEntity(reqJson, ContentType.APPLICATION_JSON);
			String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
			Response response = GsonUtils.convertObj(responseJson, Response.class);
			return response;
		} catch (Exception e) {
			LOGGER.error("判断是否为第一次获取额度失败！", e);
			return null;
		}
	}
	/**
	 *将邀请人和被邀请人的关系存入gfb的t_gfb_customer_reference_info表中
	 * @param userId  inviteUserId
	 * @return
	 */
	public Response saveCustomerReferenceInfo(Long userId, Long inviteUserId) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("inviteUserId", inviteUserId);
			String requestUrl = gfbServiceUrl + ESPCUSTOMER_SAVEREFERENCEINFO;
			String reqJson = GsonUtils.toJson(map);
			StringEntity entity = new StringEntity(reqJson, ContentType.APPLICATION_JSON);
			String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
			Response response = GsonUtils.convertObj(responseJson, Response.class);
			return response;
		} catch (Exception e) {
			LOGGER.error("将邀请人与被邀请人的关系存入gfb失败！", e);
			return null;
		}
	}
    
}
