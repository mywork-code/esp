package com.apass.esp.third.party.weizhi.entity.aftersale;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class AsDetailDto {
    private Long skuId;//商品编号
    private Integer skuNum;//申请数量
    public Long getSkuId() {
        return skuId;
    }
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
    public Integer getSkuNum() {
        return skuNum;
    }
    public void setSkuNum(Integer skuNum) {
        this.skuNum = skuNum;
    }

}

