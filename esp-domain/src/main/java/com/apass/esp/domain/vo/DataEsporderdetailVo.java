package com.apass.esp.domain.vo;
import com.apass.esp.domain.entity.DataEsporderdetail;
public class DataEsporderdetailVo extends DataEsporderdetail{
	private String goodsName;
	private String percent;
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
}
