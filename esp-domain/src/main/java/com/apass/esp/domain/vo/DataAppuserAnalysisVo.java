package com.apass.esp.domain.vo;

public class DataAppuserAnalysisVo {
	
	/**
	 * 某个时间格式yyyyMMddHH
	 */
	private String txnId;
	/**
	 * 平台（'0代表查询全平台,1代表Android，2代表iOS'）
	 */
	private String platformId;
	
	/**
	 * 统计单位：1-hour;2-daily
	 */
	private String type;
	
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DataAppuserAnalysisVo(String txnId, String platformId, String type) {
		super();
		this.txnId = txnId;
		this.platformId = platformId;
		this.type = type;
	}

	public DataAppuserAnalysisVo() {
		super();
	}
	
}
