package com.apass.esp.domain.dto.cart;

import java.util.List;

import com.apass.esp.domain.dto.goods.GoodsStockSkuDto;

public class GoodsSkuInCartDto {

    /** 商品id */
    private Long goodsId;
    
    /** 商品最小单元分类 */
    private String goodsSkuType;
    
    private List<GoodsStockSkuDto> goodsStockSkuList;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsSkuType() {
        return goodsSkuType;
    }

    public void setGoodsSkuType(String goodsSkuType) {
        this.goodsSkuType = goodsSkuType;
    }

    public List<GoodsStockSkuDto> getGoodsStockSkuList() {
        return goodsStockSkuList;
    }

    public void setGoodsStockSkuList(List<GoodsStockSkuDto> goodsStockSkuList) {
        this.goodsStockSkuList = goodsStockSkuList;
    }
    
    
}
