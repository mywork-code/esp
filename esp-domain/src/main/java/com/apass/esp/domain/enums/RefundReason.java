package com.apass.esp.domain.enums;

import java.util.Arrays;

/**
 * 
 * @description 退货原因枚举
 *
 * @author liuchao01
 * @version $Id: RefundReason.java, v 0.1 2016年12月23日 下午6:12:06 liuchao01 Exp ${0xD}
 */
public enum RefundReason {

    REFUND_REASON00("RR00","7天无理由退货"),
    REFUND_REASON01("RR01","商品瑕疵"),
    REFUND_REASON02("RR02","质量问题"),
    REFUND_REASON03("RR03","颜色/尺寸/参数不符"),
    REFUND_REASON04("RR04","少发/漏发"),
    REFUND_REASON05("RR05","收到商品是有有划痕或破损"),
    REFUND_REASON06("RR06","假冒品牌");
    
    private String code;
    
    private String message;
    
    private RefundReason(String code ,String message){
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
     * 判断退换货原因是否在枚举范围内 
     * 
     * @param code
     * @return
     */
    public static final boolean isLegal(String code){
        return Arrays.asList(
            new String[]{RefundReason.REFUND_REASON00.getCode(),RefundReason.REFUND_REASON01.getCode(),
                         RefundReason.REFUND_REASON02.getCode(),RefundReason.REFUND_REASON03.getCode(),
                         RefundReason.REFUND_REASON04.getCode(),RefundReason.REFUND_REASON05.getCode(),
                         RefundReason.REFUND_REASON06.getCode(),}).contains(code);
    }
}
