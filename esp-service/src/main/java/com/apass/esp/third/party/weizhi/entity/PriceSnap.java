package com.apass.esp.third.party.weizhi.entity;

import java.math.BigDecimal;

/**
 * 
 * @author pyc
 * @email  E-mail: pengyingchao@apass.cn
 * @version V2.0
 * @createTime：2017年11月16日 下午5:33:35 
 * @description
 */
public class PriceSnap {
	
    private long skuId;
    private BigDecimal price;

    public PriceSnap(long skuId, BigDecimal price) {
        this.skuId = skuId;
        this.price = price;
    }

    public PriceSnap() {
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
