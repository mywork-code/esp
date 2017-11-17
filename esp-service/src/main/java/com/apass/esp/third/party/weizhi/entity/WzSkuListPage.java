package com.apass.esp.third.party.weizhi.entity;

import java.util.List;

public class WzSkuListPage {
	private int pageNo;
	private int pageSize;
	private int totalRows;
	private List<String> skuIds;
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public List<String> getSkuIds() {
		return skuIds;
	}
	public void setSkuIds(List<String> skuIds) {
		this.skuIds = skuIds;
	}
	
}
