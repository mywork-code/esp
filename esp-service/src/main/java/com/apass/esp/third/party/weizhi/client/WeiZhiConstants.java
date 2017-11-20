package com.apass.esp.third.party.weizhi.client;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
@Component
public class WeiZhiConstants {
	//tokenUrl
    public static final String TOKEN_URL = "http://180.168.49.94:65530/jdapi/accessToken";
	//
    public static final String GRANT_TYPE = "access_token";
	//对接账号
    public static final String CLIENT_ID = "200034525";
    //用户名
    public static final String USER_NAME = "zydc";
    //密码
    public static final String PASSWORD = "132654";
    //
    public static final String CLIENT_SECRET = "kLKYak8a9xHsUW2TBgdf";
    //Token在redis中的key值
    public  static final String WEIZHI_TOKEN = "WEIZHI_TOKEN";
    
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    
    public static final String EXPIRED_TIME = "EXPIRED_TIME";
    //Token在redis中的有效期
    public static final int TOKEN_EXPIRED = 80000 ;

    
    //获取sign
    public String getSign(String timestamp) throws Exception{
    	
    	String signString= CLIENT_SECRET + USER_NAME + DigestUtils.md5Hex(PASSWORD) +timestamp+CLIENT_ID
    	    	+GRANT_TYPE+ CLIENT_SECRET;
    	return StringUtils.upperCase(DigestUtils.md5Hex(signString));
    }
    //微知接口API地址
    //微知获取商品详细信息接口地址
    public static final String WZAPI_PRODUCT_GETDETAIL = "http://180.168.49.94:65530/jdapi/product/getDetail";
    //微知获取商品上下架状态接口地址
    public static final String WZAPI_PRODUCT_SKUSTATE  = "http://180.168.49.94:65530/jdapi/product/skuState";
    //微知查询一级分类列表信息接口
    public static final String WZAPI_PRODUCT_FIRSTCATEGORYS  = "http://180.168.49.94:65530/jdapi/product/getFirstCategorys";
    //微知查询二级分类列表信息接口
    public static final String WZAPI_PRODUCT_SECONDCATEGORYS = "http://180.168.49.94:65530/jdapi/product/getSecondCategorys";
    //微知查询三级分类列表信息接口
    public static final String WZAPI_PRODUCT_THIRDCATEGORYS  = "http://180.168.49.94:65530/jdapi/product/getThirdCategorys";
    //微知查询分类信息接口
    public static final String WZAPI_PRODUCT_GETCATEGORY  = "http://180.168.49.94:65530/jdapi/product/getCategory";
    //微知获取分类商品编号接口
    public static final String WZAPI_PRODUCT_GETSKU  = "http://180.168.49.94:65530/jdapi/product/getSku";
    //微知获取所有图片信息
    public static final String WZAPI_PRODUCT_SKUIMAGE  = "http://180.168.49.94:65530/jdapi/product/skuImage";

    
    //微知商品区域购买限制查询
    public static final String WZAPI_PRODUCT_CHECKAREALIMIT  = "http://180.168.49.94:65530/jdapi/product/checkAreaLimit";
    //微知商品可售验证接口
    public static final String WZAPI_PRODUCT_CHECKSALE  = "http://180.168.49.94:65530/jdapi/product/checkSale";
    //微知同类商品查询
    public static final String WZAPI_PRODUCT_SIMILARSKU  = "http://180.168.49.94:65530/jdapi/product/similarSku";
    
    //统一下单接口
    public static final String WZAPI_ORDER_SUBMITORDER =  "http://180.168.49.94:65530/jdapi/order/submitOrder";
    //确认预占库存
    public static final String WZAPI_ORDER_CONFIRMORDER = "http://180.168.49.94:65530/jdapi/order/confirmOrder"; 
    //查询订单信息接口
    public static final String WZAPI_ORDER_SELECTORDER = "http://180.168.49.94:65530/jdapi/order/selectOrder";
    //根据订单号查询物流信息
    public static final String WZAPI_ORDER_ORDERTRACK = "http://180.168.49.94:65530/jdapi/order/orderTrack";
    //根据本地订单号查询微知订单号
    public static final String WZAPI_ORDER_SELECTORDERIDBYTHIRDORDER = "http://180.168.49.94:65530/jdapi/order/selectOrderIdByThirdOrder";
    
    
    //批量查询商品价格
    public static final String WZAPI_PRICE_GETWZPRICE = "http://180.168.49.94:65530/jdapi/price/getWzPrice";
    
}
