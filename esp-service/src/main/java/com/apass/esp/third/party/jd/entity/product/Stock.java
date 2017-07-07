package com.apass.esp.third.party.jd.entity.product;

import java.io.Serializable;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class Stock implements Serializable {
    private long skuId;
    private String areaId;
    private int stockStateId;

    private String stockStateDesc;
    private int remainNum;
    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public int getStockStateId() {
        return stockStateId;
    }

    public void setStockStateId(int stockStateId) {
        this.stockStateId = stockStateId;
    }

    public String getStockStateDesc() {
        return stockStateDesc;
    }

    public void setStockStateDesc(String stockStateDesc) {
        this.stockStateDesc = stockStateDesc;
    }

    public int getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(int remainNum) {
        this.remainNum = remainNum;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "skuId=" + skuId +
                ", areaId='" + areaId + '\'' +
                ", stockStateId=" + stockStateId +
                ", stockStateDesc='" + stockStateDesc + '\'' +
                ", remainNum=" + remainNum +
                '}';
    }
}
