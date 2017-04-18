package com.apass.esp.domain.entity.goods;

import java.math.BigDecimal;
import java.util.Date;

import com.apass.gfb.framework.annotation.MyBatisEntity;

/**
 * @description 
 * 
 * 商品库存信息表
 * t_ajp_goods_stock_info
 * 
 * @author liuchao01
 * @version $Id: ProductInfo.java, v 0.1 2016年12月19日 下午2:33:38 liuchao01 Exp $
 */
@MyBatisEntity
public class GoodsStockInfoEntity {

    private Long id;

    /** 商品库存信息表 主键 */
    private Long goodsStockId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** 商品id **/
    private Long       goodsId;

    /** 商品价格 **/
    private BigDecimal goodsPrice;

    private String     goodsName;

    /** 成本价 **/
    private BigDecimal goodsCostPrice;

    /** 市场价格间 **/
    private BigDecimal marketPrice;

    /** 库存规格**/
    private String     goodsSkuAttr;

    /** 库存总量 **/
    private Long       stockTotalAmt;

    /** 当前库存量 **/
    private Long       stockCurrAmt;

    /** 库存量 **/
    private Long       stockAmt;

    /** 商品规格logo url **/
    private String     stockLogo;

    /** 创建人 **/
    private String     createUser;

    /** 修改人 **/
    private String     updateUser;

    /** 创建时间 **/
    private Date       createDate;

    /** 修改时间 **/
    private Date       updateDate;

    /**比价链接1URL**/
    private String     goodsCompareUrl;
    
    /**比价链接2URL**/
    private String     goodsCompareUrl2;

    public String getGoodsCompareUrl() {
        return goodsCompareUrl;
    }

    public void setGoodsCompareUrl(String goodsCompareUrl) {
        this.goodsCompareUrl = goodsCompareUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getGoodsStockId() {
        return goodsStockId;
    }

    public void setGoodsStockId(Long goodsStockId) {
        this.goodsStockId = goodsStockId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getGoodsCostPrice() {
        return goodsCostPrice;
    }

    public void setGoodsCostPrice(BigDecimal goodsCostPrice) {
        this.goodsCostPrice = goodsCostPrice;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getGoodsSkuAttr() {
        return goodsSkuAttr;
    }

    public void setGoodsSkuAttr(String goodsSkuAttr) {
        this.goodsSkuAttr = goodsSkuAttr;
    }

    public Long getStockTotalAmt() {
        return stockTotalAmt;
    }

    public void setStockTotalAmt(Long stockTotalAmt) {
        this.stockTotalAmt = stockTotalAmt;
    }

    public Long getStockCurrAmt() {
        return stockCurrAmt;
    }

    public void setStockCurrAmt(Long stockCurrAmt) {
        this.stockCurrAmt = stockCurrAmt;
    }

    public Long getStockAmt() {
        return stockAmt;
    }

    public void setStockAmt(Long stockAmt) {
        this.stockAmt = stockAmt;
    }

    public String getStockLogo() {
        return stockLogo;
    }

    public void setStockLogo(String stockLogo) {
        this.stockLogo = stockLogo;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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

    public String getGoodsCompareUrl2() {
        return goodsCompareUrl2;
    }

    public void setGoodsCompareUrl2(String goodsCompareUrl2) {
        this.goodsCompareUrl2 = goodsCompareUrl2;
    }
    
    
}
