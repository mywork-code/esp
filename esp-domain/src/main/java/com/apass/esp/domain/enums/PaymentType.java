package com.apass.esp.domain.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 用户选择支付方式
 *
 * @author liuming
 * @version $Id: PaymentType.java, v 0.1 2017年3月3日 上午11:17:56 liuming Exp $
 * @description
 */
public enum PaymentType {

    CREDIT_PAYMENT("T02", "额度支付"),//退了信用支付

    CARD_PAYMENT("T05", "银行卡支付"),
	
	ALIPAY_PAYMENT("T10","支付宝支付");

    private String code;

    private String message;

    private PaymentType(String code, String message) {
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

    /**
     * 是否额度支付
     *
     * @param paymentType
     * @return
     */
    public static boolean isCreditPayment(String paymentType) {
        return CREDIT_PAYMENT.getCode().equals(paymentType);
    }

    /**
     * 是否银行卡支付
     *
     * @param paymentType
     * @return
     */
    public static boolean isCardPayment(String paymentType) {
        return CARD_PAYMENT.getCode().equals(paymentType);
    }
    
    public static String getMessage(String code){
    	PaymentType[] types = PaymentType.values();
    	for (PaymentType type : types) {
			if(StringUtils.equalsIgnoreCase(code, type.getCode())){
				return type.getMessage();
			}
		}
    	return "";
    }
}
