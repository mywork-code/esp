package com.apass.esp.domain.vo;

public class DataAppuserAnalysisVo {
	
	/**
	 * 某个时间格式YYYYMMhhHH
	 */
	private String txnId;
	/**
	 * 平台（'0代表查询全平台,1代表Android，2代表iOS'）
	 */
	private String platformId;
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getPlatformId() {
		return platformId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	
}
