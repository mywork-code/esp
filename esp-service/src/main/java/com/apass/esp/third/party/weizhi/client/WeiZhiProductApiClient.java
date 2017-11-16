package com.apass.esp.third.party.weizhi.client;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
import com.apass.esp.third.party.weizhi.entity.CategoryPage;
import com.apass.esp.third.party.weizhi.entity.WzPicture;
import com.apass.esp.third.party.weizhi.entity.WzSkuListPage;
import com.apass.gfb.framework.utils.GsonUtils;
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
			LOGGER.error("getWeiZhiProductDetail response {} return is not 200");
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
			LOGGER.error("getWeiZhiProductSkuState response {} return is not 200");
		}
		return wzProductState;
	}
	
	
	/**
	 * 查询一级分类列表信息接口
	 * categoryFalge=1 为一级类目
	 * categoryFalge=2 为二级类目
	 * categoryFalge=3 为三级类目
	 */
	public CategoryPage getWeiZhiGetCategorys(Integer pageNo,Integer pageSize,Integer categoryFalge,Integer parentId) throws Exception {
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
		String url=WeiZhiConstants.WZAPI_PRODUCT_FIRSTCATEGORYS;//一级类目URL
		if(categoryFalge==2 ){
			BasicNameValuePair param4 = new BasicNameValuePair("parentId", parentId.toString());
			parameters.add(param4);	
			url=WeiZhiConstants.WZAPI_PRODUCT_SECONDCATEGORYS;//二级类目URL
		}else if(categoryFalge==3){
			BasicNameValuePair param4 = new BasicNameValuePair("parentId", parentId.toString());
			parameters.add(param4);	
			url=WeiZhiConstants.WZAPI_PRODUCT_THIRDCATEGORYS;//三级类目URL
		}

		parameters.add(param1);
		parameters.add(param2);
		parameters.add(param3);
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
		String responseJson = null;
		CategoryPage firstCategorys =null;
		try {
			responseJson = HttpClientUtils.getMethodPostResponse(url,entity);
			LOGGER.info("微知获取token返回Json数据：" + responseJson);
			if (null == responseJson) {
				LOGGER.info("微知获取token失败！");
				return null;
			}			
			WeiZhiCategorysResponse response =GsonUtils.convertObj(responseJson, WeiZhiCategorysResponse.class);
			
			if (null != response && response.getResult() == 0) {
				firstCategorys = response.getData();
			}
		} catch (Exception e) {
			LOGGER.error("getWeiZhiGetCategorys response {} return is not 200");
		}
		return firstCategorys;
	}
	/**
	 * 获取分类商品编号接口
	 */
	public WzSkuListPage getWeiZhiGetSku(Integer pageNo,Integer pageSize,String catId) throws Exception {
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
		BasicNameValuePair param4 = new BasicNameValuePair("catId", catId);		

		parameters.add(param1);
		parameters.add(param2);
		parameters.add(param3);
		parameters.add(param4);
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
		String responseJson = null;
		WzSkuListPage wzSkuListPage =null;
		try {
			responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_PRODUCT_GETSKU,entity);
			LOGGER.info("微知获取token返回Json数据：" + responseJson);
			if (null == responseJson) {
				LOGGER.info("微知获取token失败！");
				return null;
			}			
			WeiZhiSkuListResponse response =GsonUtils.convertObj(responseJson, WeiZhiSkuListResponse.class);
			
			if (null != response && response.getResult() == 0) {
				wzSkuListPage = response.getData();
			}
		} catch (Exception e) {
			LOGGER.error("getWeiZhiGetSku response {} return is not 200");
		}
		return wzSkuListPage;
	}
	/**
	 *获取所有图片信息
	 */
	public List<Map<String,List<WzPicture>>> getWeiZhiProductSkuImage(String sku) throws Exception {
		//获取Token
		String token = weiZhiTokenService.getTokenFromRedis();
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		BasicNameValuePair param1 = new BasicNameValuePair("token", token);
		BasicNameValuePair param2 = new BasicNameValuePair("sku", sku);
		parameters.add(param1);
		parameters.add(param2);

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
		String responseJson = null;
		List<Map<String,List<WzPicture>>> map=new ArrayList<>();
		try {
			responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_PRODUCT_SKUIMAGE,entity);
			LOGGER.info("微知获取token返回Json数据：" + responseJson);
			if (null == responseJson) {
				LOGGER.info("微知获取token失败！");
				return null;
			}
			Gson gson = new Gson();
			Type objectType = new TypeToken<WeiZhiResponse<List<Map<String,List<WzPicture>>>>>() {
			}.getType();
			WeiZhiResponse<List<Map<String,List<WzPicture>>>> response = gson.fromJson(responseJson, objectType);
			if (null != response && response.getResult() == 0) {
				map = response.getData();
			}
		} catch (Exception e) {
			LOGGER.error("getWeiZhiProductSkuImage response {} return is not 200");
		}
		return map;
	}

}
