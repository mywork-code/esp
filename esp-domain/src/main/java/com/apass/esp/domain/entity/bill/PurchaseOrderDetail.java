package com.apass.esp.domain.entity.bill;
import java.math.BigDecimal;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
/**
 * 
 * @author ht
 *
 */
public class PurchaseOrderDetail extends OrderInfoEntity{
    /*商品流水号*/
    private Long orderInfoId;
    /*商品流水号*/
    private String goodsSerialNum;
    /*商品编号*/
    private String goodsCode;
    /*商品名称*/
    private String goodsName;
    /*商品最小单位规格*/
    private String goodsSkuAttr;
    /*商品采购成本价格*/
    private BigDecimal goodsCostPrice;
    /*商品采购当前库存*/
    private Long stockCurrAmt;
    public Long getOrderInfoId() {
        return orderInfoId;
    }
    public void setOrderInfoId(Long orderInfoId) {
        this.orderInfoId = orderInfoId;
    }
    public String getGoodsSerialNum() {
        return goodsSerialNum;
    }
    public void setGoodsSerialNum(String goodsSerialNum) {
        this.goodsSerialNum = goodsSerialNum;
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
    public String getGoodsSkuAttr() {
        return goodsSkuAttr;
    }
    public void setGoodsSkuAttr(String goodsSkuAttr) {
        this.goodsSkuAttr = goodsSkuAttr;
    }
    public BigDecimal getGoodsCostPrice() {
        return goodsCostPrice;
    }
    public void setGoodsCostPrice(BigDecimal goodsCostPrice) {
        this.goodsCostPrice = goodsCostPrice;
    }
    public Long getStockCurrAmt() {
        return stockCurrAmt;
    }
    public void setStockCurrAmt(Long stockCurrAmt) {
        this.stockCurrAmt = stockCurrAmt;
    }
}