package com.apass.esp.third.party.weizhi.client;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.jd.JdProductState;
import com.apass.esp.service.wz.WeiZhiTokenService;
import com.apass.esp.third.party.jd.entity.product.Product;
import com.apass.gfb.framework.utils.HttpClientUtils;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

@Service
public class WeiZhiProductApiClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(WeiZhiProductApiClient.class);
	@Autowired
	private WeiZhiTokenService weiZhiTokenService;
	/**
	 * 获取微知商品详情信息
	 * @return
	 * @throws Exception
	 */
	public Product getWeiZhiProductDetail(String sku) throws Exception {
		//获取Token
		String token = weiZhiTokenService.getTokenFromRedis();
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		BasicNameValuePair param1 = new BasicNameValuePair("token", token);
		BasicNameValuePair param2 = new BasicNameValuePair("sku", sku);
		parameters.add(param1);
		parameters.add(param2);

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
		String responseJson = null;
		Product wzProductDetail = new Product();
		try {
			responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_PRODUCT_GETDETAIL,entity);
			LOGGER.info("微知获取token返回Json数据：" + responseJson);
			if (null == responseJson) {
				LOGGER.info("微知获取token失败！");
				return null;
			}
			Gson gson = new Gson();
			Type objectType = new TypeToken<WeiZhiResponse<Product>>() {
			}.getType();
			WeiZhiResponse<Product> response = gson.fromJson(responseJson, objectType);
			if (null != response && response.getResult() == 0) {
				wzProductDetail = response.getData();
			}
		} catch (Exception e) {
			LOGGER.error("getToken response {} return is not 200");
		}
		return wzProductDetail;
	}
	/**
	 * 获取商品上下架状态接口
	 */
	public List<JdProductState> getWeiZhiProductSkuState(String sku) throws Exception {
		//获取Token
		String token = weiZhiTokenService.getTokenFromRedis();
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		BasicNameValuePair param1 = new BasicNameValuePair("token", token);
		BasicNameValuePair param2 = new BasicNameValuePair("sku", sku);
		parameters.add(param1);
		parameters.add(param2);

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
		String responseJson = null;
		List<JdProductState>  wzProductState = new ArrayList<>();
		try {
			responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_PRODUCT_SKUSTATE,entity);
			LOGGER.info("微知获取token返回Json数据：" + responseJson);
			if (null == responseJson) {
				LOGGER.info("微知获取token失败！");
				return null;
			}
			Gson gson = new Gson();
			Type objectType = new TypeToken<WeiZhiResponse<List<JdProductState>>>() {
			}.getType();
			WeiZhiResponse<List<JdProductState> > response = gson.fromJson(responseJson, objectType);
			if (null != response && response.getResult() == 0) {
				wzProductState = response.getData();
			}
		} catch (Exception e) {
			LOGGER.error("getToken response {} return is not 200");
		}
		return wzProductState;
	}
	
	
	/**
	 * 查询一级分类列表信息接口
	 */
	public Map<String,Object> getWeiZhiFirstCategorys(Integer pageNo,Integer pageSize) throws Exception {
		Integer Num=0;
	    Integer Size=0;
		if(null ==pageNo || pageNo<1){
			Num=1;
		}else{
			Num=pageNo;
		}
		if(null ==pageSize || pageSize<1 || pageSize>20){
			Size=10;
		}else{
			Size=pageSize;
		}
		//获取Token
		String token = weiZhiTokenService.getTokenFromRedis();
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		BasicNameValuePair param1 = new BasicNameValuePair("token", token);
		BasicNameValuePair param2 = new BasicNameValuePair("pageNo", Num.toString());
		BasicNameValuePair param3 = new BasicNameValuePair("pageSize", Size.toString());

		parameters.add(param1);
		parameters.add(param2);
		parameters.add(param3);
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
		String responseJson = null;
		Map<String,Object>  firstCategorys = new HashMap<String, Object>();
		try {
			responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_PRODUCT_FIRSTCATEGORYS,entity);
			LOGGER.info("微知获取token返回Json数据：" + responseJson);
			if (null == responseJson) {
				LOGGER.info("微知获取token失败！");
				return null;
			}
			Gson gson = new Gson();
			Type objectType = new TypeToken<WeiZhiResponse<Map<String,Object>>>() {
			}.getType();
			WeiZhiResponse<Map<String,Object>> response = gson.fromJson(responseJson, objectType);
			if (null != response && response.getResult() == 0) {
				firstCategorys = response.getData();
			}
		} catch (Exception e) {
			LOGGER.error("getToken response {} return is not 200");
		}
		return firstCategorys;
	}
	
}
