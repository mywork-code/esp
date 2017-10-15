package com.apass.esp.domain.kvattr;

public class PaymentVo {
	/**
	 * 支付宝支付方式
	 */
	private String alipay;

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	@Override
	public String toString() {
		return "PaymentVo [alipay=" + alipay + "]";
	}
}
