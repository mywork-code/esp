package com.apass.esp.domain.utils;

import java.math.BigDecimal;

public class ConstantsUtils {

    //  app 首页banner类型
    public static final String BANNERTYPEINDEX = "index";
    //  商品精选页banner
    public static final String BANNERTYPERECOMMEND = "recommend";
    //  快递鸟支持查询的物流商家
    public static final String KDNIAO_DATATYPENO = "100001";
    
    //  trackingmore 支持查询的物流商家
    public static final String TRACKINGMORE_DATATYPENO = "100003";
    //  物流签收7天后确认收货
    public static final Integer DELAYCONFIRMGOODSSEVEN=7;
    //  物流签收10天后确认收货
    public static final Integer DELAYCONFIRMGOODSTEN=10;
    
    public static class PayMethodPageShow{
        // 支付方式选择页  [展示额度支付和银行卡支付  确认跳转到钱包]
        public static final String CHOOSEPAYONE = "1";
        //支付方式选择页  [展示额度支付和银行卡支付  确认弹出支付详情]
        public static final String CHOOSEPAYTWO = "2";
        //支付方式选择页  [展示银行卡支付 ]
        public static final String CHOOSEPAYTHREE = "3";
        
    }
    //银行卡支付比例
    public static final BigDecimal CASHRATIO=BigDecimal.valueOf(0.1);
    
    public static final String MERCHANTNAME = "京东";
}
