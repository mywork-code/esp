package com.apass.esp.domain.dto.cart;

/**
 * 同步客户端与数据库购物车商品信息
 * @description 
 *
 * @author liuchao01
 * @version $Id: GoodsStockIdNumDto.java, v 0.1 2016年12月23日 上午10:50:25 liuchao01 Exp ${0xD}
 */
public class GoodsStockIdNumDto {

    /**
     * 商品库存id
     */
    private Long goodsStockId;

    /**
     * 商品数量
     */
    private int goodsNum;

    public Long getGoodsStockId() {
        return goodsStockId;
    }

    public void setGoodsStockId(Long goodsStockId) {
        this.goodsStockId = goodsStockId;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }
    
}
