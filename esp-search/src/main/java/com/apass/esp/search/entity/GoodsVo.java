package com.apass.esp.search.entity;


import java.math.BigDecimal;

/**
 * Created by xianzhi.wang on 2017/5/22.
 */
public class GoodsVo implements IdAble{
	
	private Integer id;
	/**商品ID*/
    private Long goodId;
    /**商品库存ID*/
    private Long goodsStockId;
    /** 商品名称 **/
    private String goodsName;
    /** 商品小标题 **/
    private String goodsTitle;
    /** 商品logo地址 **/
    private String goodsLogoUrl;
    /** 商品logo地址 (新) **/
    private String goodsLogoUrlNew;
    /*** 商品价格*/
    private BigDecimal goodsPrice;
    /*** 首付价*/
    private BigDecimal firstPrice;
    /*** 来源（京东或非京东）*/
    private String source="";
    
	public Long getGoodId() {
		return goodId;
	}
	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}
	public Long getGoodsStockId() {
		return goodsStockId;
	}
	public void setGoodsStockId(Long goodsStockId) {
		this.goodsStockId = goodsStockId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsTitle() {
		return goodsTitle;
	}
	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}
	public String getGoodsLogoUrl() {
		return goodsLogoUrl;
	}
	public void setGoodsLogoUrl(String goodsLogoUrl) {
		this.goodsLogoUrl = goodsLogoUrl;
	}
	public String getGoodsLogoUrlNew() {
		return goodsLogoUrlNew;
	}
	public void setGoodsLogoUrlNew(String goodsLogoUrlNew) {
		this.goodsLogoUrlNew = goodsLogoUrlNew;
	}
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public BigDecimal getFirstPrice() {
		return firstPrice;
	}
	public void setFirstPrice(BigDecimal firstPrice) {
		this.firstPrice = firstPrice;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
    	this.id = id;
    }
}