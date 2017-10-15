package com.apass.esp.domain.kvattr;

import org.apache.commons.lang3.StringUtils;

public class PaymentVo {
	/**
	 * 支付宝支付方式
	 */
	private String alipay;
	
	private boolean showAlipay;

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public boolean isShowAlipay() {
		String alipay = getAlipay();
		if(StringUtils.equals(alipay, "1")){
			return true;
		}
		return false;
	}
}
