package com.apass.esp.third.party.weizhi.entity;

import java.util.List;


/**
 * 
 * @author pyc
 * @email  E-mail: pengyingchao@apass.cn
 * @version V2.0
 * @createTime：2017年11月16日 下午5:14:28 
 * @description
 */
public class OrderReq {
	
	private String orderNo;//订单Id
	
	private AddressInfo addressInfo;//收货地址
	
	private List<SkuNum> skuNumList;
	 
	private String remark;
	
	private List<PriceSnap> orderPriceSnap;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public AddressInfo getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(AddressInfo addressInfo) {
		this.addressInfo = addressInfo;
	}

	public List<SkuNum> getSkuNumList() {
		return skuNumList;
	}

	public void setSkuNumList(List<SkuNum> skuNumList) {
		this.skuNumList = skuNumList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<PriceSnap> getOrderPriceSnap() {
		return orderPriceSnap;
	}

	public void setOrderPriceSnap(List<PriceSnap> orderPriceSnap) {
		this.orderPriceSnap = orderPriceSnap;
	}
	
}
