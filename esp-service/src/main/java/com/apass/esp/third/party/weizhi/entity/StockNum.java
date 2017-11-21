package com.apass.esp.third.party.weizhi.entity;

import java.io.Serializable;

public class StockNum  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long skuId;
    private int num;
	public long getSkuId() {
		return skuId;
	}
	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
}
