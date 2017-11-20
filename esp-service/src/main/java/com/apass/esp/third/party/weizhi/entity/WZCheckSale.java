package com.apass.esp.third.party.weizhi.entity;

public class WZCheckSale {
	 private  int is7ToReturn;
     private int isCanVAT;
     private String name;
     private int  saleState;
     private long skuId;
	public int getIs7ToReturn() {
		return is7ToReturn;
	}
	public void setIs7ToReturn(int is7ToReturn) {
		this.is7ToReturn = is7ToReturn;
	}
	public int getIsCanVAT() {
		return isCanVAT;
	}
	public void setIsCanVAT(int isCanVAT) {
		this.isCanVAT = isCanVAT;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSaleState() {
		return saleState;
	}
	public void setSaleState(int saleState) {
		this.saleState = saleState;
	}
	public long getSkuId() {
		return skuId;
	}
	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}

}
