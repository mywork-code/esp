package com.apass.esp.domain.dto.goods;

import java.math.BigDecimal;

import com.apass.gfb.framework.utils.EncodeUtils;

/**
 * 订单展示列表   商品相关信息
 * @description 
 *
 * @author liuming
 * @version $Id: GoodsInfoInOrderDto.java, v 0.1 2017年1月12日 上午10:41:20 liuming Exp $
 */
public class GoodsCategoryDto {

    /**
     *  商品Id
     */
    private Long goodsId;

    /** 商品名称 **/
    private String goodsName;

    /** 商品大标题 **/
    private String goodsTitle;

    /** 商品logo地址 **/
    private String goodsLogoUrl;

    /** 商品logo地址(新) **/
    private String goodsLogoUrlNew;

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;
    
    /**
     * 首付价
     */
    private BigDecimal firstPrice;
    
    /**
     * 来源
     */
    private String source;

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
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
    
}
