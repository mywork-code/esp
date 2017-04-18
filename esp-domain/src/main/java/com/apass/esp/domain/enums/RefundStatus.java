package com.apass.esp.domain.enums;

import java.util.Arrays;

/**
 * 售后处理进度枚举
 * @description 
 *
 * @author liuchao01
 * @version $Id: RefundStatus.java, v 0.1 2016年12月28日 上午10:42:12 liuchao01 Exp ${0xD}
 */
public enum RefundStatus {

    /** 退换货类型(t_esp_refund_info.refund_type: 0退货、1换货) */
    REFUND_STATUS01("RS01","申请退/换货"),
    REFUND_STATUS02("RS02","提交退换货物流单号"),
    REFUND_STATUS03("RS03","商家确认收货"),
    REFUND_STATUS04("RS04","退款完成/商家重新发货"),
    REFUND_STATUS05("RS05","售后完成"),
	REFUND_STATUS06("RS06","售后失败");
    
    private String code;
    
    private String message;
    
    private RefundStatus(String code ,String message){
        this.code=code;
        this.message=message;
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
    
    /**
     * 
     * RS02、RS03、RS04、RS05 客户端显示客户发货物流
     * @param code
     * @return
     */
    public static final boolean showSlogistics(String code){
        return Arrays.asList(
            new String[]{RefundStatus.REFUND_STATUS02.getCode(), 
                         RefundStatus.REFUND_STATUS03.getCode(),RefundStatus.REFUND_STATUS04.getCode(),
                         RefundStatus.REFUND_STATUS05.getCode()}).contains(code);
    }
    
    /**
     * 
     * 换货(refundType=1)时,RS04、RS05 客户端显示商户发货物流
     * @param code
     * @return
     */
    public static final boolean showRlogistics(String code){
        return Arrays.asList(
            new String[]{RefundStatus.REFUND_STATUS04.getCode(),RefundStatus.REFUND_STATUS05.getCode()}).contains(code);
    }
}
