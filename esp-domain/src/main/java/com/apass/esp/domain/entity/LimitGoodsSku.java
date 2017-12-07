package com.apass.esp.domain.entity;
import java.math.BigDecimal;
import java.util.Date;

import com.apass.esp.common.model.CreatedUser;
public class LimitGoodsSku extends CreatedUser{
    private Long id;
    private Long limitBuyActId;
    private Long goodsId;
    private String skuId;
    private BigDecimal marketPrice;
    private BigDecimal activityPrice;
    private Long limitNumTotal;
    private Long limitNum;
    private Long sortNo;
    private String url;
    private Long time;//离活动开始时间或活动开始时间与服务器时间差
    private Date startTime;
    private Date endTime;
    private String limitFalg;//活动标准（活动未开始NotBeginning；活动进行中  InProgress）
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getLimitBuyActId() {
        return limitBuyActId;
    }
    public void setLimitBuyActId(Long limitBuyActId) {
        this.limitBuyActId = limitBuyActId;
    }
    public Long getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
    public String getSkuId() {
        return skuId;
    }
    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }
    public BigDecimal getActivityPrice() {
        return activityPrice;
    }
    public void setActivityPrice(BigDecimal activityPrice) {
        this.activityPrice = activityPrice;
    }
    public Long getLimitNumTotal() {
        return limitNumTotal;
    }
    public void setLimitNumTotal(Long limitNumTotal) {
        this.limitNumTotal = limitNumTotal;
    }
    public Long getLimitNum() {
        return limitNum;
    }
    public void setLimitNum(Long limitNum) {
        this.limitNum = limitNum;
    }
    public Long getSortNo() {
        return sortNo;
    }
    public void setSortNo(Long sortNo) {
        this.sortNo = sortNo;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getLimitFalg() {
		return limitFalg;
	}
	public void setLimitFalg(String limitFalg) {
		this.limitFalg = limitFalg;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
    
}