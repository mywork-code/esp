package com.apass.esp.third.party.weizhi.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.service.wz.WeiZhiTokenService;
import com.apass.esp.third.party.weizhi.response.OrderUnitResponse;
import com.apass.esp.third.party.weizhi.response.WZPriceResponse;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;
import com.google.common.collect.Lists;
/**
 * 
 * @author pyc
 * @email  E-mail: pengyingchao@apass.cn
 * @version V2.0
 * @createTime：2017年11月17日 下午3:22:42 
 * @description
 */
@Service
public class WeiZhiPriceApiClient {
	
	private static final Logger logger = LoggerFactory.getLogger(WeiZhiPriceApiClient.class);
	
	@Autowired
	private WeiZhiTokenService weiZhiTokenService;
	
	/**
	 * 根据skuID获取
	 * @param skuId
	 * @return
	 * @throws Exception 
	 */
	public List<WZPriceResponse> getWzPrice(List<String> skuList) throws Exception{
		if(CollectionUtils.isEmpty(skuList)){
			throw new BusinessException("传参不能为空!");
		}
		String join = StringUtils.join(skuList, ",");
		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		BasicNameValuePair param1 = new BasicNameValuePair("token", weiZhiTokenService.getTokenFromRedis());
		BasicNameValuePair param2 = new BasicNameValuePair("sku_ids",join);
		parameters.add(param1);
		parameters.add(param2);
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
		
		String responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_PRICE_GETWZPRICE, entity);
	    
		logger.info("----getWzPrice------ response:{}",responseJson);
	    /**
	     * 返回json
	     */
	    JSONObject datas = JSON.parseObject(responseJson);
	    
	    if(null == datas){
	    	logger.error("----getWzPrice--- callback is null");
	    	return null;
	    }
	    
	    String result = datas.getString("result");
	    
	    if(!StringUtils.equals(result, "0")){
	    	String message = datas.getString("detail");
	    	logger.error("---getWzPrice---- callback result:{},message:{}",result,message);
	    	return null;
	    }
	    
	    String text = JSON.toJSONString(datas.getJSONArray("data"));
	    
	    List<WZPriceResponse> priceList = JSON.parseArray(text, WZPriceResponse.class);
	    return priceList;
	}
	
}
