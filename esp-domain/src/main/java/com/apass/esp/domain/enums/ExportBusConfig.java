package com.apass.esp.domain.enums;

/**
 * 
 * @description 订单状态枚举
 * 
 * @author chenbo
 * @version $Id: OrderStatus.java, v 0.1 2016年12月19日 下午3:15:10 chenbo Exp $
 */
public enum ExportBusConfig {

                             BUS_ORDER("E001", "订单信息导出"),

                             BUS_GOODS("E002", "商品信息导出"),

                             BUS_ACTIVITY("E003", "活动推荐导出"),
                             
                             BUS_AWARDINTRO("E004","转介绍放款导出"),
                             
                             BUS_ORDER_EXCEPTION("E005","异常订单信息导出");

    private String code;

    private String message;

    private ExportBusConfig(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
