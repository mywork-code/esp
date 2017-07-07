package com.apass.esp.third.party.jd.entity.product;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class ProductComment implements Serializable{
	private long skuId;
	private BigDecimal averageScore;
	private BigDecimal goodRate;
	private BigDecimal generalRate;
	private BigDecimal poorRate;
	
	public long getSkuId() {
		return skuId;
	}
	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}
	public BigDecimal getAverageScore() {
		return averageScore;
	}
	public void setAverageScore(BigDecimal averageScore) {
		this.averageScore = averageScore;
	}
	public BigDecimal getGoodRate() {
		return goodRate;
	}
	public void setGoodRate(BigDecimal goodRate) {
		this.goodRate = goodRate;
	}
	public BigDecimal getGeneralRate() {
		return generalRate;
	}
	public void setGeneralRate(BigDecimal generalRate) {
		this.generalRate = generalRate;
	}
	public BigDecimal getPoorRate() {
		return poorRate;
	}
	public void setPoorRate(BigDecimal poorRate) {
		this.poorRate = poorRate;
	}
}
