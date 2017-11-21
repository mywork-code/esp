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
    
    //微知开发环境
    public static final String WZ_TEST_IP = "http://180.168.49.94:65530";
    //微知生产环境
    public static final String WZ_PRODUCTION_IP = "";
    //公共路径
    public static final String COMMON_URL = WZ_TEST_IP;

    
    //获取sign
    public String getSign(String timestamp) throws Exception{
    	
    	String signString= CLIENT_SECRET + USER_NAME + DigestUtils.md5Hex(PASSWORD) +timestamp+CLIENT_ID
    	    	+GRANT_TYPE+ CLIENT_SECRET;
    	return StringUtils.upperCase(DigestUtils.md5Hex(signString));
    }
    //微知接口API地址
    //微知获取商品详细信息接口地址
    public static final String WZAPI_PRODUCT_GETDETAIL = COMMON_URL + "/jdapi/product/getDetail";
    //微知获取商品上下架状态接口地址
    public static final String WZAPI_PRODUCT_SKUSTATE  = COMMON_URL + "/jdapi/product/skuState";
    //微知查询一级分类列表信息接口
    public static final String WZAPI_PRODUCT_FIRSTCATEGORYS  =  COMMON_URL + "/jdapi/product/getFirstCategorys";
    //微知查询二级分类列表信息接口
    public static final String WZAPI_PRODUCT_SECONDCATEGORYS =  COMMON_URL + "/jdapi/product/getSecondCategorys";
    //微知查询三级分类列表信息接口
    public static final String WZAPI_PRODUCT_THIRDCATEGORYS  = COMMON_URL +  "/jdapi/product/getThirdCategorys";
    //微知查询分类信息接口
    public static final String WZAPI_PRODUCT_GETCATEGORY  =  COMMON_URL + "/jdapi/product/getCategory";
    //微知获取分类商品编号接口
    public static final String WZAPI_PRODUCT_GETSKU  =  COMMON_URL + "/jdapi/product/getSku";
    //微知统一余额查询接口
    public static final String WZAPI_PRODUCT_GETBALANCE  =  COMMON_URL + "/jdapi/price/getBalance";
    //微知批量获取库存接口
    public static final String WZAPI_PRODUCT_GETNEWSTOCKBYID  =  COMMON_URL+ "/jdapi/stock/getNewStockById";
    
    
    
    
    //微知商品区域购买限制查询
    public static final String WZAPI_PRODUCT_CHECKAREALIMIT  =  COMMON_URL + "/jdapi/product/checkAreaLimit";
    //微知商品可售验证接口
    public static final String WZAPI_PRODUCT_CHECKSALE  =  COMMON_URL + "/jdapi/product/checkSale";
    //微知同类商品查询
    public static final String WZAPI_PRODUCT_SIMILARSKU  =  COMMON_URL + "/jdapi/product/similarSku";
    
    //统一下单接口
    public static final String WZAPI_ORDER_SUBMITORDER =   COMMON_URL + "/jdapi/order/submitOrder";
    //确认预占库存
    public static final String WZAPI_ORDER_CONFIRMORDER =  COMMON_URL + "/jdapi/order/confirmOrder"; 
    //查询订单信息接口
    public static final String WZAPI_ORDER_SELECTORDER =  COMMON_URL + "/jdapi/order/selectOrder";
    //根据订单号查询物流信息
    public static final String WZAPI_ORDER_ORDERTRACK =  COMMON_URL + "/jdapi/order/orderTrack";
    //根据本地订单号查询微知订单号
    public static final String WZAPI_ORDER_SELECTORDERIDBYTHIRDORDER =  COMMON_URL + "/jdapi/order/selectOrderIdByThirdOrder";
    //根据订单号取消未确认订单接口
    public static final String WZAPI_ORDER_CANCEL =  COMMON_URL + "/jdapi/order/cancel";

    //微知获取所有图片信息
    public static final String WZAPI_PRODUCT_SKUIMAGE  =  COMMON_URL + "/jdapi/product/skuImage";


    /**
     * 小海的weizhi接口
     */
    //服务单保存申请
    public static final String WZAPI_AFTERSALES_AFSAPPLY = "/jdapi/afterSales/createAfsApply";
    //填写客户发运信息
    public static final String WZAPI_AFTERSALES_SENDSKU = "/jdapi/afterSales/updateSendSku";//TODO
    //校验某订单中某商品是否可以提交售后服务
    public static final String WZAPI_AFTERSALE_AVAILABLENUMBERCOMP = "/jdapi/afterSales/getAvailableNumberComp";
    //根据订单号、商品编号查询支持的服务类型
    public static final String WZAPI_AFTERSALE_CUSTOMEREXPECTCOMP = "/jdapi/afterSales/getCustomerExpectComp";
    //根据订单号、商品编号查询支持的商品返回微知方式
    public static final String WZAPI_AFTERSALE_WARERETURNJDCOMP = "/jdapi/afterSales/getWareReturnJdComp";
    //根据客户账号和订单号分页查询服务单概要信息
    public static final String WZAPI_AFTERSALE_SERVIVELIST = "/jdapi/afterSales/getServiveList";

    //批量查询商品价格
    public static final String WZAPI_PRICE_GETWZPRICE = "/jdapi/price/getWzPrice";


    //删除消息
    public static final String WZAPI_MESSAGE_DEL = "/jdapi/message/del";
    //获取消息
    public static final String WZAPI_MESSAGE_GET = "/jdapi/message/get";
    
}
