package com.apass.esp.third.party.weizhi.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderInfoResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 订单编号
	 */
	private String wzOrderId;
	/**
	 * 物流状态 0 是新建  1是妥投   2是拒收
	 */
	private String state;
	/**
	 * 订单类型   1是父订单   2是子订单
	 */
	private String type;
	/**
	 * 订单价格
	 */
	private BigDecimal orderPrice;
	/**
	 * 运费（合同配置了才返回）
	 */
	private BigDecimal freight;
	/**
	 * 商品列表
	 */
	private List<String> sku;
	/**
	 * 父订单号
	 */
	private String pOrder;
	/**
	 * 订单状态  0为取消订单  1为有效
	 */
	private String orderState;
	/**
	 * 0为未确认下单订单   1为确认下单订单
	 */
	private String submitState;
	
	public String getWzOrderId() {
		return wzOrderId;
	}
	public void setWzOrderId(String wzOrderId) {
		this.wzOrderId = wzOrderId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	public List<String> getSku() {
		return sku;
	}
	public void setSku(List<String> sku) {
		this.sku = sku;
	}
	public String getpOrder() {
		return pOrder;
	}
	public void setpOrder(String pOrder) {
		this.pOrder = pOrder;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getSubmitState() {
		return submitState;
	}
	public void setSubmitState(String submitState) {
		this.submitState = submitState;
	}


}
