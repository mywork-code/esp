package com.apass.esp.domain.entity.cart;

import java.math.BigDecimal;
import java.util.Date;

import com.apass.gfb.framework.annotation.MyBatisEntity;
/**
 * 购物车信息表
 * @description 
 *
 * @author zengqingshan
 * @version $Id: CartInfoEntity.java, v 0.1 2016年12月19日 下午3:49:01 zengqingshan Exp $
 */
@MyBatisEntity
public class CartInfoEntity {
    
    private Long id; 
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 商品库存id
     */
    private Long goodsStockId;
    /**
     * 商品选择价格
     */
    private BigDecimal goodsSelectedPrice;
    /**
     * 商品数量
     */
    private int goodsNum;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;
    
    private String isSelect;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getGoodsStockId() {
        return goodsStockId;
    }
    public void setGoodsStockId(Long goodsStockId) {
        this.goodsStockId = goodsStockId;
    }
    public BigDecimal getGoodsSelectedPrice() {
        return goodsSelectedPrice;
    }
    public void setGoodsSelectedPrice(BigDecimal goodsSelectedPrice) {
        this.goodsSelectedPrice = goodsSelectedPrice;
    }
    public int getGoodsNum() {
        return goodsNum;
    }
    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Date getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    public String getIsSelect() {
        return isSelect;
    }
    public void setIsSelect(String isSelect) {
        this.isSelect = isSelect;
    }

}
