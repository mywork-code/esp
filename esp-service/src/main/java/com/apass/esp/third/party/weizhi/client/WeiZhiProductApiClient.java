package com.apass.esp.third.party.weizhi.client;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.apass.esp.third.party.weizhi.entity.*;
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
import com.apass.esp.domain.entity.jd.JdGoodsBooks;
import com.apass.esp.domain.entity.jd.JdProductState;
import com.apass.esp.service.wz.WeiZhiTokenService;
import com.apass.esp.third.party.jd.entity.base.Region;
import com.apass.esp.third.party.jd.entity.product.Product;
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
	 * 获取微知商品详情信息（sku不为8位）
	 * @return
	 * @throws Exception
	 */
	public Product getWeiZhiProductDetail(String sku) throws Exception {
		Product wzProductDetail = new Product();
		if(sku.length()==8){
			return null;
		}
		//获取Token
		String token = weiZhiTokenService.getTokenFromRedis();
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		BasicNameValuePair param1 = new BasicNameValuePair("token", token);
		BasicNameValuePair param2 = new BasicNameValuePair("sku", sku);
		parameters.add(param1);
		parameters.add(param2);

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
		String responseJson = null;
		try {
			responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_PRODUCT_GETDETAIL,entity);
			LOGGER.info("获取微知商品详情信息返回Json数据：{},参数：sku={}", responseJson,sku);

			if(!StringUtils.equals("0",JSON.parseObject(responseJson).getString("result"))){
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
	 * 获取微知商品详情信息（sku为8位图书音像类目）
	 * @return
	 * @throws Exception
	 */
	public JdGoodsBooks getWeiZhiRelatedProductDetail(String sku) throws Exception {
		JdGoodsBooks wzProductDetail = new JdGoodsBooks();
		if(sku.length()!=8){
			return null;
		}
		//获取Token
		String token = weiZhiTokenService.getTokenFromRedis();
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		BasicNameValuePair param1 = new BasicNameValuePair("token", token);
		BasicNameValuePair param2 = new BasicNameValuePair("sku", sku);
		parameters.add(param1);
		parameters.add(param2);

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
		String responseJson = null;
		try {
			responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_PRODUCT_GETDETAIL,entity);
			LOGGER.info("获取微知商品详情信息返回Json数据：{},参数：sku={}", responseJson,sku);

			if(!StringUtils.equals("0",JSON.parseObject(responseJson).getString("result"))){
				return null;
			}

			Gson gson = new Gson();
			Type objectType = new TypeToken<WeiZhiResponse<JdGoodsBooks>>() {
			}.getType();
			WeiZhiResponse<JdGoodsBooks> response = gson.fromJson(responseJson, objectType);
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
			LOGGER.info("获取商品上下架状态接口返回Json数据：" + responseJson);
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
	public CategoryPage getWeiZhiGetCategorys(Integer pageNo,Integer pageSize,Integer categoryFalge,Long parentId) throws Exception {
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
			if(categoryFalge == 1){
				LOGGER.info("查询一级分类列表信息接口,返回数据：{}", responseJson);
			}else if(categoryFalge == 2){
				LOGGER.info("查询二级分类列表信息接口,返回数据：{}", responseJson);
			}else {
				LOGGER.info("查询三级分类列表信息接口,返回数据：{}", responseJson);
			}
			if (null == responseJson) {
				if(categoryFalge == 1){
					LOGGER.info("查询一级分类列表信息失败");
				}else if(categoryFalge == 2){
					LOGGER.info("查询二级分类列表信息失败");
				}else {
					LOGGER.info("查询三级分类列表信息失败");
				}
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
			LOGGER.info("获取分类商品编号接口返回Json数据:{}", responseJson);
			if (null == responseJson) {
				LOGGER.info("获取分类商品编号失败！");
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
	 *返回参数：sku=1815738
	 *{"result":0,"detail":"OK","data":[{"1815738":[
     * {"path":"http://img13.360buyimg.com/n0/jfs/t1927/130/1244887032/107943/3a4d2a69/56483347Nf58173b4.jpg","orderSort":0,"isPrimary":1},
     * {"path":"http://img13.360buyimg.com/n2/jfs/t1927/130/1244887032/107943/3a4d2a69/56483347Nf58173b4.jpg","orderSort":1,"isPrimary":0},
     * {"path":"http://img13.360buyimg.com/n2/jfs/t1876/146/1223581733/121305/2c6db051/56483358Nc592e907.jpg","orderSort":2,"isPrimary":0},
     * {"path":"http://img13.360buyimg.com/n2/jfs/t2500/143/1212814375/81730/4942c1e2/56483364N42203979.jpg","orderSort":3,"isPrimary":0},
     * {"path":"http://img13.360buyimg.com/n2/jfs/t1921/319/1209091955/143234/3efe3f4b/5648336aN4a555860.jpg","orderSort":4,"isPrimary":0},
     * {"path":"http://img13.360buyimg.com/n2/jfs/t1996/302/1187346914/121250/ef213ac1/56483372N8e315b50.jpg","orderSort":5,"isPrimary":0},
     * {"path":"http://img13.360buyimg.com/n2/jfs/t2413/297/1143079640/121287/75fd8aa/56483375Nb3ef6185.jpg","orderSort":6,"isPrimary":0}]}]}
	 */
	public Map<String,List<WzPicture>> getWeiZhiProductSkuImage(String sku) throws Exception {
		//获取Token
		String token = weiZhiTokenService.getTokenFromRedis();
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		BasicNameValuePair param1 = new BasicNameValuePair("token", token);
		BasicNameValuePair param2 = new BasicNameValuePair("sku", sku);
		parameters.add(param1);
		parameters.add(param2);

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
		String responseJson = null;
		Map<String,List<WzPicture>> map=new HashMap<String, List<WzPicture>>();
		try {
			responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_PRODUCT_SKUIMAGE,entity);
			LOGGER.info("获取所有图片信息返回Json数据：" + responseJson);
			if (null == responseJson) {
				LOGGER.info("微知获取token失败！");
				return null;
			}
			Gson gson = new Gson();
			Type objectType = new TypeToken<WeiZhiResponse<Map<String,List<WzPicture>>>>() {
			}.getType();
			WeiZhiResponse<Map<String,List<WzPicture>>> response = gson.fromJson(responseJson, objectType);
			if (null != response && response.getResult() == 0) {
				map = response.getData();
			}
		} catch (Exception e) {
			LOGGER.error("getWeiZhiProductSkuImage response {} return is not 200");
		}
		return map;
	}
	/**
	 * 商品区域购买限制查询
	 */
	public List<AreaLimitEntity> getWeiZhiCheckAreaLimit(String skuIds,Region region) throws Exception {
		//获取Token
		String token = weiZhiTokenService.getTokenFromRedis();
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		BasicNameValuePair param1 = new BasicNameValuePair("token", token);
		BasicNameValuePair param2 = new BasicNameValuePair("skuIds", skuIds);
		parameters.add(param1);
		parameters.add(param2);
		if (null != region) {
			if (StringUtils.isNotBlank(region.getProvince())) {
				BasicNameValuePair param3 = new BasicNameValuePair("province", region.getProvince());
				parameters.add(param3);
			}
			if (StringUtils.isNotBlank(region.getCity())) {
				BasicNameValuePair param4 = new BasicNameValuePair("city", region.getCity());
				parameters.add(param4);
			}
			if (StringUtils.isNotBlank(region.getCounty())) {
				BasicNameValuePair param5 = new BasicNameValuePair("county", region.getCounty());
				parameters.add(param5);
			}
			if (StringUtils.isNotBlank(region.getTown())) {
				BasicNameValuePair param6 = new BasicNameValuePair("town", region.getTown());
				parameters.add(param6);
			}else{
				BasicNameValuePair param6 = new BasicNameValuePair("town", "0");
				parameters.add(param6);
			}		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
		String responseJson = null;
		List<AreaLimitEntity>  areaLimitEntityList = new ArrayList<>();
		try {
			responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_PRODUCT_CHECKAREALIMIT,entity);
			LOGGER.info("商品区域购买限制查询返回Json数据：" + responseJson);
			if (null == responseJson) {
				LOGGER.info("微知获取token失败！");
				return null;
			}
			Gson gson = new Gson();
			Type objectType = new TypeToken<WeiZhiResponse<List<AreaLimitEntity>>>() {
			}.getType();
			WeiZhiResponse<List<AreaLimitEntity>> response = gson.fromJson(responseJson, objectType);
			if (null != response && response.getResult() == 0) {
				areaLimitEntityList = response.getData();
			}
		} catch (Exception e) {
			LOGGER.error("getWeiZhiCheckAreaLimit response {} return is not 200");
		}
		return areaLimitEntityList;
	}
	/**
	 * 商品可售验证接口
	 */
	public CheckSale getWeiZhiCheckSale(String skuIds) throws Exception {
		//获取Token
		String token = weiZhiTokenService.getTokenFromRedis();
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		BasicNameValuePair param1 = new BasicNameValuePair("token", token);
		BasicNameValuePair param2 = new BasicNameValuePair("skuIds", skuIds);
		parameters.add(param1);
		parameters.add(param2);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
		String responseJson = null;
		CheckSale  checkSale = new com.apass.esp.third.party.weizhi.entity.CheckSale();
		try {
			responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_PRODUCT_CHECKSALE,entity);
			LOGGER.info("商品可售验证接口返回Json数据：" + responseJson);
			if (null == responseJson) {
				LOGGER.info("微知获取token失败！");
				return null;
			}
			Gson gson = new Gson();
			Type objectType = new TypeToken<WeiZhiResponse<CheckSale>>() {
			}.getType();
			WeiZhiResponse<CheckSale> response = gson.fromJson(responseJson, objectType);
			if (null != response && response.getResult() == 0) {
				checkSale = response.getData();
			}
		} catch (Exception e) {
			LOGGER.error("getWeiZhiCheckAreaLimit response {} return is not 200");
		}
		return checkSale;
	}

	/**
	 * 同类商品查询
	 */
	public WZJdSimilarSku getWeiZhiSimilarSku(String skuId) throws Exception {
		//获取Token
		String token = weiZhiTokenService.getTokenFromRedis();
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		BasicNameValuePair param1 = new BasicNameValuePair("token", token);
		BasicNameValuePair param2 = new BasicNameValuePair("skuId", skuId);
		parameters.add(param1);
		parameters.add(param2);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
		String responseJson = null;
		WZJdSimilarSku  wZJdSimilarSku = new WZJdSimilarSku();
		try {
			responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_PRODUCT_SIMILARSKU,entity);
			LOGGER.info("同类商品查询返回Json数据：" + responseJson);
			if (null == responseJson) {
				LOGGER.info("微知获取token失败！");
				return null;
			}
			Gson gson = new Gson();
			Type objectType = new TypeToken<WeiZhiResponse<WZJdSimilarSku>>() {
			}.getType();
			WeiZhiResponse<WZJdSimilarSku> response = gson.fromJson(responseJson, objectType);
			if (null != response && response.getResult() == 0) {
				wZJdSimilarSku = response.getData();
			}
		} catch (Exception e) {
			LOGGER.error("getWeiZhiCheckAreaLimit response {} return is not 200");
		}
		return wZJdSimilarSku;
	}
	/**
	 * 统一余额查询接口
	 */
	public int  getWeiZhiGetBalance() throws Exception {
		//获取Token
		String token = weiZhiTokenService.getTokenFromRedis();
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		BasicNameValuePair param1 = new BasicNameValuePair("token", token);
		parameters.add(param1);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
		String responseJson = null;
		Integer price=0;
		try {
			responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_PRODUCT_GETBALANCE,entity);
			LOGGER.info("统一余额查询接口返回Json数据：" + responseJson);
			if (null == responseJson) {
				LOGGER.info("微知获取token失败！");
				return 0;
			}
			Gson gson = new Gson();
			Type objectType = new TypeToken<WeiZhiResponse<Integer>>() {
			}.getType();
			WeiZhiResponse<Integer> response = gson.fromJson(responseJson, objectType);
			if (null != response && response.getResult() == 0) {
				price = response.getData();
			}
		} catch (Exception e) {
			LOGGER.error("getWeiZhiCheckAreaLimit response {} return is not 200");
		}
		return price;
	}

	/**
	 * 微知批量获取库存接口
	 */
	public List<GoodsStock> getNewStockById(List<StockNum> skuNums, final Region region) throws Exception {
		// 获取Token
		String token = weiZhiTokenService.getTokenFromRedis();
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		BasicNameValuePair param1 = new BasicNameValuePair("token", token);
		BasicNameValuePair param2 = new BasicNameValuePair("skuNums", JSON.toJSONString(skuNums));
		String area = region.getProvinceId() + "_" + region.getCityId() + "_" + region.getCountyId();
		BasicNameValuePair param3 = new BasicNameValuePair("area", area);

		parameters.add(param1);
		parameters.add(param2);
		parameters.add(param3);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
		String responseJson = null;
		List<GoodsStock> goodsStockList = new ArrayList<>();
		try {
			responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_PRODUCT_GETNEWSTOCKBYID, entity);
			LOGGER.info("微知批量获取库存接口返回Json数据：" + responseJson);
			if (null == responseJson) {
				LOGGER.info("微知获取token失败！");
				return null;
			}
			Gson gson = new Gson();
			Type objectType = new TypeToken<WeiZhiResponse<List<GoodsStock>>>() {
			}.getType();
			WeiZhiResponse<List<GoodsStock>> response = gson.fromJson(responseJson, objectType);
			if (null != response && response.getResult() == 0) {
				goodsStockList = response.getData();
			}
		} catch (Exception e) {
			LOGGER.error("getWeiZhiCheckAreaLimit response {} return is not 200");
		}
		return goodsStockList;
	}


	/**
	 * 查询分类信息接口
	 */
	public Category getCategory(String catId) throws Exception {
		// 获取Token
		String token = weiZhiTokenService.getTokenFromRedis();
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();

		parameters.add(new BasicNameValuePair("token", token));
		parameters.add(new BasicNameValuePair("catId", catId));

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
		String responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_PRODUCT_GETCATEGORY, entity);
		LOGGER.info("getCategory查询分类信息返回数据responseJson：{}", responseJson);

		JSONObject datas = JSON.parseObject(responseJson);
		if (null == datas) {
			LOGGER.info("查询分类信息失败,参数 toekn:{},catId:{}",token,catId);
			return null;
		}
		String result = datas.getString("result");
		if(!StringUtils.equals("0",result)){
			LOGGER.info("查询分类信息失败,参数 toekn:{},catId:{}",token,catId);
			return null;
		}

		String data = datas.getString("data");

		return  GsonUtils.convertObj(data, Category.class);
	}

}
