package com.apass.esp.third.party.weizhi.response;

import java.io.Serializable;
import java.util.List;

import com.apass.esp.third.party.weizhi.entity.SkuNum;
/**
 * 
 * @author pyc
 * @email  E-mail: pengyingchao@apass.cn
 * @version V2.0
 * @createTime：2017年11月17日 下午3:38:52 
 * @description
 */
public class OrderUnitResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String wzOrderId;//微知订单号
	
	private String thirdOrderId;//第三方的订单单号
	
	private String supplierId;//京东订单号
	
	private String freight;
	
	private String orderPrice;
	
	private List<SkuNum> sku;
	
	
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getWzOrderId() {
		return wzOrderId;
	}

	public void setWzOrderId(String wzOrderId) {
		this.wzOrderId = wzOrderId;
	}

	public String getThirdOrderId() {
		return thirdOrderId;
	}

	public void setThirdOrderId(String thirdOrderId) {
		this.thirdOrderId = thirdOrderId;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public List<SkuNum> getSku() {
		return sku;
	}

	public void setSku(List<SkuNum> sku) {
		this.sku = sku;
	}

}
