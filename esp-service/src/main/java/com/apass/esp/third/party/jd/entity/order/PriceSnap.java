package com.apass.esp.third.party.jd.entity.order;

import java.math.BigDecimal;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class PriceSnap {
    private long skuId;
    private BigDecimal price;
    private BigDecimal jdPrice;

    public PriceSnap(long skuId, BigDecimal price, BigDecimal jdPrice) {
        this.skuId = skuId;
        this.price = price;
        this.jdPrice = jdPrice;
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

    public BigDecimal getJdPrice() {
        return jdPrice;
    }

    public void setJdPrice(BigDecimal jdPrice) {
        this.jdPrice = jdPrice;
    }
}
