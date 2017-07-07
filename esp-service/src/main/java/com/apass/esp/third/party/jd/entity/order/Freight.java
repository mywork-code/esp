package com.apass.esp.third.party.jd.entity.order;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class Freight implements Serializable {
    private BigDecimal freight;
    private BigDecimal baseFreight;
    private BigDecimal remoteRegionFreight;
    private String remoteSku;

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public BigDecimal getBaseFreight() {
        return baseFreight;
    }

    public void setBaseFreight(BigDecimal baseFreight) {
        this.baseFreight = baseFreight;
    }

    public BigDecimal getRemoteRegionFreight() {
        return remoteRegionFreight;
    }

    public void setRemoteRegionFreight(BigDecimal remoteRegionFreight) {
        this.remoteRegionFreight = remoteRegionFreight;
    }

    public String getRemoteSku() {
        return remoteSku;
    }

    public void setRemoteSku(String remoteSku) {
        this.remoteSku = remoteSku;
    }
}
