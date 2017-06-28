package com.apass.esp.domain.enums;

/**
 * 用户选择首付支付方式
 *
 * @author liuming
 * @version $Id: DownPaymentType.java, v 0.1 2017年3月3日 上午11:17:56 liuming Exp $
 * @description
 */
public enum DownPaymentType {

    CARD_PAYMENT("unionpay", "银行卡支付"),
	
	ALIPAY_PAYMENT("alipay","支付宝支付");

    private String code;

    private String message;

    private DownPaymentType(String code, String message) {
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
     * 是否银行卡支付
     *
     * @param paymentType
     * @return
     */
    public static boolean isCardPayment(String paymentType) {
        return CARD_PAYMENT.getCode().equals(paymentType);
    }
    
    /**
     * 是否银行卡支付
     *
     * @param paymentType
     * @return
     */
    public static boolean alipayPayment(String paymentType) {
        return ALIPAY_PAYMENT.getCode().equals(paymentType);
    }
}
