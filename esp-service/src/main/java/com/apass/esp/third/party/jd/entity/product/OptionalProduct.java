package com.apass.esp.third.party.jd.entity.product;

import java.io.Serializable;
/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class OptionalProduct implements Serializable {
	private long skuId;
	private int sales = 0;
	private int careNess = 0;
	private String mallId;

	public long getSkuId() {
		return skuId;
	}

	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public int getCareNess() {
		return careNess;
	}

	public void setCareNess(int careNess) {
		this.careNess = careNess;
	}

	public String getMallId() {
		return mallId;
	}

	public void setMallId(String mallId) {
		this.mallId = mallId;
	}
	
}
