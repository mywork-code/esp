package com.apass.esp.third.party.weizhi.client;

import com.apass.gfb.framework.environment.SystemEnvConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class WeiZhiConstants {
    @Autowired
    private SystemEnvConfig systemEnvConfig;

    private String tokenUrl;//tokenUrl;
    private String clientId;
    private String userName;
    private String password;
    private String clientSecret;
    private  String requestUrl;

    @PostConstruct
    public void init(){
        if(systemEnvConfig.isPROD()){
            tokenUrl = "http://openapi.viphrm.com/jdapi/accessToken";
            clientId = "244953049";
            userName = "zydc";
            password = "zydc123456";
            clientSecret = "pm0G8ZzOQ2XItQnq9q1r";
            requestUrl = "http://openapi.viphrm.com";
        }else{
//            tokenUrl = "http://testopenapi.viphrm.com/jdapi/accessToken";
//            clientId = "100118132";
//            userName = "prezydc";
//            password = "124456";
//            clientSecret = "4U0pYYAbuhNNzXMvF3wy";
//            requestUrl = "http://testopenapi.viphrm.com";
            tokenUrl = "http://testopenapi.viphrm.com/jdapi/accessToken";
            clientId = "100118132";
            userName = "prezydc";
            password = "124456";
            clientSecret = "4U0pYYAbuhNNzXMvF3wy";
            requestUrl = "http://testopenapi.viphrm.com";
        }

    }


    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public static final String GRANT_TYPE = "access_token";
    //Token在redis中的key值
    public  static final String WEIZHI_TOKEN = "WEIZHI_TOKEN";
    
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    
    public static final String EXPIRED_TIME = "EXPIRED_TIME";
    //Token在redis中的有效期
    public static final int TOKEN_EXPIRED = 80000 ;
    
    //获取sign
    public String getSign(String timestamp) throws Exception{
    	
    	String signString= getClientSecret() + getUserName() + DigestUtils.md5Hex(getPassword()) +timestamp+getClientId()
    	    	+GRANT_TYPE+ getClientSecret();
    	return StringUtils.upperCase(DigestUtils.md5Hex(signString));
    }

    /**
     *获取接口完整请求url
     */
    public String getWZRequestUrl(String path){
        return requestUrl + path;
    }

    //微知接口API地址
    //微知获取商品详细信息接口地址
    public static final String WZAPI_PRODUCT_GETDETAIL = "/jdapi/product/getDetail";
    //微知获取商品上下架状态接口地址
    public static final String WZAPI_PRODUCT_SKUSTATE  =   "/jdapi/product/skuState";
    //微知查询一级分类列表信息接口
    public static final String WZAPI_PRODUCT_FIRSTCATEGORYS  =    "/jdapi/product/getFirstCategorys";
    //微知查询二级分类列表信息接口
    public static final String WZAPI_PRODUCT_SECONDCATEGORYS =    "/jdapi/product/getSecondCategorys";
    //微知查询三级分类列表信息接口
    public static final String WZAPI_PRODUCT_THIRDCATEGORYS  =   "/jdapi/product/getThirdCategorys";
    //微知查询分类信息接口
    public static final String WZAPI_PRODUCT_GETCATEGORY  =    "/jdapi/product/getCategory";
    //微知获取分类商品编号接口
    public static final String WZAPI_PRODUCT_GETSKU  =   "/jdapi/product/getSku";
    //微知统一余额查询接口
    public static final String WZAPI_PRODUCT_GETBALANCE  =  "/jdapi/price/getBalance";
    //微知批量获取库存接口
    public static final String WZAPI_PRODUCT_GETNEWSTOCKBYID  =  "/jdapi/stock/getNewStockById";
    //微知商品区域购买限制查询
    public static final String WZAPI_PRODUCT_CHECKAREALIMIT  =   "/jdapi/product/checkAreaLimit";
    //微知商品可售验证接口
    public static final String WZAPI_PRODUCT_CHECKSALE  =   "/jdapi/product/checkSale";
    //微知同类商品查询
    public static final String WZAPI_PRODUCT_SIMILARSKU  =   "/jdapi/product/similarSku";
    //统一下单接口
    public static final String WZAPI_ORDER_SUBMITORDER =   "/jdapi/order/submitOrder";
    //确认预占库存
    public static final String WZAPI_ORDER_CONFIRMORDER =  "/jdapi/order/confirmOrder";
    //查询订单信息接口
    public static final String WZAPI_ORDER_SELECTORDER =  "/jdapi/order/selectOrder";
    //根据订单号查询物流信息
    public static final String WZAPI_ORDER_ORDERTRACK =   "/jdapi/order/orderTrack";
    //根据本地订单号查询微知订单号
    public static final String WZAPI_ORDER_SELECTORDERIDBYTHIRDORDER =  "/jdapi/order/selectOrderIdByThirdOrder";
    //根据订单号取消未确认订单接口
    public static final String WZAPI_ORDER_CANCEL =  "/jdapi/order/cancel";
    //微知获取所有图片信息
    public static final String WZAPI_PRODUCT_SKUIMAGE  =  "/jdapi/product/skuImage";

    /**
     * 小海的weizhi接口
     */
    //服务单保存申请
    public static final String WZAPI_AFTERSALES_AFSAPPLY = "/jdapi/afterSales/createAfsApply";
    //填写客户发运信息
    public static final String WZAPI_AFTERSALES_SENDSKU =  "/jdapi/afterSales/updateSendSku";
    //校验某订单中某商品是否可以提交售后服务
    public static final String WZAPI_AFTERSALE_AVAILABLENUMBERCOMP =   "/jdapi/afterSales/getAvailableNumberComp";
    //根据订单号、商品编号查询支持的服务类型
    public static final String WZAPI_AFTERSALE_CUSTOMEREXPECTCOMP =   "/jdapi/afterSales/getCustomerExpectComp";
    //根据订单号、商品编号查询支持的商品返回微知方式
    public static final String WZAPI_AFTERSALE_WARERETURNJDCOMP = "/jdapi/afterSales/getWareReturnJdComp";
    //根据客户账号和订单号分页查询服务单概要信息
    public static final String WZAPI_AFTERSALE_SERVIVELIST =  "/jdapi/afterSales/getServiveList";

    //批量查询商品价格
    public static final String WZAPI_PRICE_GETWZPRICE =  "/jdapi/price/getWzPrice";
    //单个查询商品价格
    public static final String WZAPI_PRICE_GETWZSINGLEPRICE = "/jdapi/price/getWzSinglePrice";
    //删除消息
    public static final String WZAPI_MESSAGE_DEL =   "/jdapi/message/del";
    //获取消息
    public static final String WZAPI_MESSAGE_GET =   "/jdapi/message/get";
    
}
