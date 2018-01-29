package com.apass.esp.domain.vo;

public class DataAnalysisVo {
	
	/**
	 * 某个时间格式yyyyMMddHH或者yyyyMMdd
	 */
	private String txnId;
	/**
	 * 平台（'0代表查询全平台,1代表Android，2代表iOS'）
	 */
	private String platformids;
	
	/**
	 * 统计单位：1-hour;2-daily
	 */
	private String type;
	
	/**
	 * 是否删除
	 */
	private String isDelete;
	
	public String getTxnId() {
		return txnId;
	}
	
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	
	public String getPlatformids() {
		return platformids;
	}

	public void setPlatformids(String platformids) {
		this.platformids = platformids;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public DataAnalysisVo(String txnId, String platformids, String type,String isDelete) {
		super();
		this.txnId = txnId;
		this.platformids = platformids;
		this.type = type;
		this.isDelete = isDelete;
	}

	public DataAnalysisVo() {
		super();
	}
	
}
