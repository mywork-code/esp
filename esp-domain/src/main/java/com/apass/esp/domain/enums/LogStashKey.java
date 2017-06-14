package com.apass.esp.domain.enums;

/**
 * LOGSTASh 日志KEY
 */
public enum LogStashKey {
    
    /** 购物车相关方法 */
    CART_ADD("添加商品到购物车", "cartAdd"),
    CART_DELETE("删除购物车中商品", "cartDelete"),
    CART_UPDATE("修改购物车中商品数量", "cartUpdate"),
    CART_LIST("查看购物车", "cartList"),
    CART_VIEWSKU("查看购物车中商品规格", "cartViewSku"),
    CART_REELECTSKU("修改购物车中商品规格", "cartReelectSku"),
    CART_ISSELECT("同步购物车商品勾选标记", "cartIsselect"),
    
    /***订单相关***/
    ORDER_GENERATE("订单生成","orderGenerate"),
    ORDER_CANCEL("取消订单","orderCancel"),
    ORDER_DELAY("延迟收货","orderDelay"),
    ORDER_CONFIRM_RECEIVE("确认收货","orderConfirmReceive"),
    ORDER_DELETE("删除订单","orderDelete"),
    ORDER_REPEAT_CONFIRM("重新下单","orderRepeatConfrim"),
    ORDER_CONFIRM_FAIL_ADDCART("重新下单失败添加购物车","orderConfirmFailAddCart"),
    ORDER_MODIFY_SHIPADDRESS("修改订单邮寄地址","orderModifyAddress"),
    ORDER_QUERY("订单查询[查询所有或根据状态查询]","orderQuery"),
    ORDER_LOADADDR("查询订单收货地址","orderLoadAddr"),
    ORDER_QUERY_DETAIL("查询订单详情[根据订单号查询订单明细]","orderQueryDetail"),
    ORDER_NOPAY_MODIFYADDRESS("待付款订单收货地址修改","orderNopayModifyaddress"),
    ORDER_REQUEST_REFUND("退款申请","orderRequestRefund"),
    
    
    
    /***支付相关***/
    PAY_INIT_METHOD("支付方式选择初始化","payInitMethod"),
    PAY_CONFIRM_METHOD("支付方式选择确认","payConfrimMethod"),
    PAY_DEFARY("支付确认付款","payDefary"),
    PAY_QUERY_STATUS("支付状态查询","payDefary"),
    PAY_LEAVE_ROLLSTOCK("确认离开支付页未支付尝试回滚库存","leavePayRollStock"),
    PAY_CALLBACK("付款成功Bss回调","payCallBack"),
    
    
    /***物流相关***/
    LOGISTICS_SHOW("查看物流","logisticShow"),
    
    /** 售后相关方法 */
    AFTERSALE_UPLOADIMAGE("售后退换货上传图片", "aftersale_uploadImage"),
    AFTERSALE_UPLOADIMAGEASCEND("售后退换货上传图片升级版", "aftersale_uploadImageascend"),
    AFTERSALE_RETURNGOODS("售后退换货提交数据", "aftersale_returnGoods"),
    AFTERSALE_PROGRESS("售后进度查询", "aftersale_progress"), 
    AFTERSALE_SUBMITLOGISTICS("售后提交退换货物流信息", "aftersale_submitLogistics"),
    
    /** 物流相关方法 */
    WEBHOOK_LOGISTICS("trackingmore物流回调", "webHook_logistics"),
    
    /** 供房帮调用电商项目中字段   */
 	GFB_ESP_SIGNLOAN("gfb调用esp字段","gfb_esp_signloan"),
 	
    /** 图片查看工具 */
    FILEVIEW("查看图片","fileView_query");
 
	
    private String name;

    private String value;

    private LogStashKey(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
