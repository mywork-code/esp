package com.apass.esp.domain.query;

import com.apass.esp.common.model.QueryParams;

import java.util.Date;

public class ProCouponQuery extends QueryParams{
	private String name;
	private String extendType;
	private String goodsCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtendType() {
		return extendType;
	}

	public void setExtendType(String extendType) {
		this.extendType = extendType;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
}
