package com.apass.esp.domain.entity.jd;

import java.math.BigDecimal;
import java.util.List;

public class JdSimilarSkuVo {
	private String skuId;
	private String goodsId;
	private String goodsStockId;
	private Long stockCurrAmt;
	private BigDecimal price;
	private BigDecimal priceFirst;
	private Boolean  isLimitActivity;//是否是限时购
	private BigDecimal priceOriginal;//原价
	private Long limitNum;//每人限购几件
	private Long limitBuyActId;//限购活动id
	private String limitBuyFalg;//限购状态标准（活动未开始NotBeginning；活动进行中  InProgress）
	private Long limitBuyTime;//距离开始时间或距离结束时间
	private String stockDesc;
	private String activityCfg;
	private Long proActivityId;
	private List<String> proCouponList;
	private String support7dRefund;
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getStockDesc() {
		return stockDesc;
	}
	public void setStockDesc(String stockDesc) {
		this.stockDesc = stockDesc;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getPriceFirst() {
		return priceFirst;
	}
	public void setPriceFirst(BigDecimal priceFirst) {
		this.priceFirst = priceFirst;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsStockId() {
		return goodsStockId;
	}
	public void setGoodsStockId(String goodsStockId) {
		this.goodsStockId = goodsStockId;
	}
	public Long getStockCurrAmt() {
		return stockCurrAmt;
	}
	public void setStockCurrAmt(Long stockCurrAmt) {
		this.stockCurrAmt = stockCurrAmt;
	}
	public String getActivityCfg() {
		return activityCfg;
	}
	public void setActivityCfg(String activityCfg) {
		this.activityCfg = activityCfg;
	}
	public String getSupport7dRefund() {
		return support7dRefund;
	}
	public void setSupport7dRefund(String support7dRefund) {
		this.support7dRefund = support7dRefund;
	}
	public Long getProActivityId() {
		return proActivityId;
	}
	public void setProActivityId(Long proActivityId) {
		this.proActivityId = proActivityId;
	}
	public List<String> getProCouponList() {
		return proCouponList;
	}
	public void setProCouponList(List<String> proCouponList) {
		this.proCouponList = proCouponList;
	}
	public BigDecimal getPriceOriginal() {
		return priceOriginal;
	}
	public void setPriceOriginal(BigDecimal priceOriginal) {
		this.priceOriginal = priceOriginal;
	}
	public Boolean getIsLimitActivity() {
		return isLimitActivity;
	}
	public void setIsLimitActivity(Boolean isLimitActivity) {
		this.isLimitActivity = isLimitActivity;
	}
	public Long getLimitNum() {
		return limitNum;
	}
	public void setLimitNum(Long limitNum) {
		this.limitNum = limitNum;
	}
	public Long getLimitBuyActId() {
		return limitBuyActId;
	}
	public void setLimitBuyActId(Long limitBuyActId) {
		this.limitBuyActId = limitBuyActId;
	}
	public String getLimitBuyFalg() {
		return limitBuyFalg;
	}
	public void setLimitBuyFalg(String limitBuyFalg) {
		this.limitBuyFalg = limitBuyFalg;
	}
	public Long getLimitBuyTime() {
		return limitBuyTime;
	}
	public void setLimitBuyTime(Long limitBuyTime) {
		this.limitBuyTime = limitBuyTime;
	}
	
}
