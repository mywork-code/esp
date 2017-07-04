package com.apass.esp.third.party.jd.entity.order;

import java.io.Serializable;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class SkuNum implements Serializable {

    private long skuId;
    private int num;

    public SkuNum(long skuId, int num) {
        this.skuId = skuId;
        this.num = num;
    }

    public SkuNum() {
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkuNum)) return false;

        SkuNum skuNum = (SkuNum) o;

        if (skuId != skuNum.skuId) return false;
        return num == skuNum.num;

    }

    @Override
    public int hashCode() {
        int result = (int) (skuId ^ (skuId >>> 32));
        result = 31 * result + num;
        return result;
    }

}
