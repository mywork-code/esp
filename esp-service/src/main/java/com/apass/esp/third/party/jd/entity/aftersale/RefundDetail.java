package com.apass.esp.third.party.jd.entity.aftersale;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RefundDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long afsServiceId;// 服务单号
	private Long personId;
	private Long jdOrderId;
	private Long skuId;
	private String wareName;
	private String orderNo;
	private BigDecimal refundTotal;// 退款总额
	private String attach;// 退款附加信息
    private String mallId;
    private Date refundTime;
    private String trade_no;
	public Long getJdOrderId() {
		return jdOrderId;
	}

	public void setJdOrderId(Long jdOrderId) {
		this.jdOrderId = jdOrderId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public Long getAfsServiceId() {
		return afsServiceId;
	}

	public void setAfsServiceId(Long afsServiceId) {
		this.afsServiceId = afsServiceId;
	}

	public BigDecimal getRefundTotal() {
		return refundTotal;
	}

	public void setRefundTotal(BigDecimal refundTotal) {
		this.refundTotal = refundTotal;
	}

	public String getMallId() {
		return mallId;
	}

	public void setMallId(String mallId) {
		this.mallId = mallId;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}


}
