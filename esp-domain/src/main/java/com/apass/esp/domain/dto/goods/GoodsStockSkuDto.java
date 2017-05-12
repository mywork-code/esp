package com.apass.esp.domain.dto.goods;

import java.math.BigDecimal;

import com.apass.gfb.framework.utils.EncodeUtils;

/**
 * 商品规格
 * @description 
 *
 * @author liuchao01
 * @version $Id: GoodsStockSkuDto.java, v 0.1 2017年1月6日 下午2:47:27 liuchao01 Exp ${0xD}
 */
public class GoodsStockSkuDto {

    /** 商品库存信息表 主键 */
    private Long goodsStockId;

    /** 商品价格 **/
    private BigDecimal goodsPrice;
    
    /** 商品最小单元sku属性 */
    private String goodsSkuAttr;

    /** 当前库存量 **/
    private Long stockCurrAmt;

    /** 商品规格logo url **/
    private String stockLogo;

    public String getStockLogoNew() {
        return stockLogoNew;
    }

    public void setStockLogoNew(String stockLogoNew) {
        this.stockLogoNew = stockLogoNew;
    }

    /** 商品规格logo url (new)**/
    private String stockLogoNew;

    public Long getGoodsStockId() {
        return goodsStockId;
    }

    public void setGoodsStockId(Long goodsStockId) {
        this.goodsStockId = goodsStockId;
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

    public Long getStockCurrAmt() {
        return stockCurrAmt;
    }

    public void setStockCurrAmt(Long stockCurrAmt) {
        this.stockCurrAmt = stockCurrAmt;
    }

    public String getStockLogo() {
        return EncodeUtils.base64Encode(stockLogo);
    }

    public void setStockLogo(String stockLogo) {
        this.stockLogo = stockLogo;
    }

}
