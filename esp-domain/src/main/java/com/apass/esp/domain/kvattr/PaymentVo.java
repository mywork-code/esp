package com.apass.esp.domain.kvattr;

import org.apache.commons.lang3.StringUtils;

public class PaymentVo {
	/**
	 * 支付宝支付方式
	 */
	private String alipay;
	/**
	 * 是否显示支付宝
	 */
	private boolean showAlipay;
	/**
	 * 银联支付方式
	 */
	private String backUnion;
	/**
	 * 是否显示银联支付
	 */
	private boolean showBanckUnion;

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

	public String getBackUnion() {
		return backUnion;
	}

	public void setBackUnion(String backUnion) {
		this.backUnion = backUnion;
	}

	public boolean getShowBanckUnion() {
		
		String backUnion = getBackUnion();
		if(StringUtils.equals(backUnion, "1")){
			return true;
		}
		return false;
	}
	
}
