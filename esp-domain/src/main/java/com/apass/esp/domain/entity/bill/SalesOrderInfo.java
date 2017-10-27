package com.apass.esp.domain.entity.bill;

import java.math.BigDecimal;

/**
 * Created by xiaohai on 2017/10/19.
 */
public class SalesOrderInfo {
    /**
     * 订单详情表主键
     */
    private Long orderdetailId;
    /**
     * 订单表主键
     */
    private Long orderPrimayId;

    /**
     * 商品编码
     */
    private String goodsCode;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品规格
     */
    private String goodsModel;

    /**
     *单价
     */
    private BigDecimal goodsPrice;

    /**
     * 单位
     */
    private String goodsSkuAttr;

    /**
     * 数量
     */
    private Long goodNum;

    public Long getOrderdetailId() {
        return orderdetailId;
    }

    public void setOrderdetailId(Long orderdetailId) {
        this.orderdetailId = orderdetailId;
    }

    public Long getOrderPrimayId() {
        return orderPrimayId;
    }

    public void setOrderPrimayId(Long orderPrimayId) {
        this.orderPrimayId = orderPrimayId;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsSkuAttr() {
        return goodsSkuAttr;
    }

    public void setGoodsSkuAttr(String goodsSkuAttr) {
        this.goodsSkuAttr = goodsSkuAttr;
    }

    public Long getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Long goodNum) {
        this.goodNum = goodNum;
    }
}
