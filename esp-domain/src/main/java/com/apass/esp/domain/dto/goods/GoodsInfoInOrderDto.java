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
public class GoodsInfoInOrderDto {

    /**
     *  商品Id
     */
    private Long goodsId;
    /**
     * 商品库存Id
     */
    private Long goodsStockId;

    /** 商品名称 **/
    private String goodsName;

    /** 商品大标题 **/
    private String goodsTitle;

    /** 商品logo地址 **/
    private String goodsLogoUrl;

    /** 商品logo地址(新) **/
    private String goodsLogoUrlNew;

    public String getGoodsLogoUrlNew() {
        return goodsLogoUrlNew;
    }

    public void setGoodsLogoUrlNew(String goodsLogoUrlNew) {
        this.goodsLogoUrlNew = goodsLogoUrlNew;
    }

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;
    /**
     * 活动优惠金额
     */
    private BigDecimal disCountAmt;
    /**
     * 优惠券优惠金额
     */
    private BigDecimal CouponAmt;
    /**
     * 每个订单详情的活动优惠金额
     */
    private BigDecimal orderDetailDisCountAmt;
    /**
     * 每个订单详情的优惠券优惠金额
     */
    private BigDecimal orderDetailCouponDisCountAmt;
    /**
     * 市场价
     */
    private BigDecimal marketPrice;
    /**
     * 商品购买数量
     */
    private Long buyNum;
    /**
     * 商品属性
     */
    private String goodsSkuAttr;
    /**
     * 商户code
     */
    private String merchantCode;
    
    /**
     * 不支持配送区域
     */
    private String unSupportProvince;
    
    /**
     * 商品来源
     */
    private String source;
    
    public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMerchantCode() {
        return merchantCode;
    }
    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }
    public String getGoodsSkuAttr() {
        return goodsSkuAttr;
    }
    public void setGoodsSkuAttr(String goodsSkuAttr) {
        this.goodsSkuAttr = goodsSkuAttr;
    }
    public Long getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
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
        return EncodeUtils.base64Encode(goodsLogoUrl);
    }
    public void setGoodsLogoUrl(String goodsLogoUrl) {
        this.goodsLogoUrl = goodsLogoUrl;
    }
    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }
    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }
    public Long getBuyNum() {
        return buyNum;
    }
    public void setBuyNum(Long buyNum) {
        this.buyNum = buyNum;
    }

	public String getUnSupportProvince() {
		return unSupportProvince;
	}

	public void setUnSupportProvince(String unSupportProvince) {
		this.unSupportProvince = unSupportProvince;
	}

	public BigDecimal getDisCountAmt() {
		return disCountAmt;
	}

	public void setDisCountAmt(BigDecimal disCountAmt) {
		this.disCountAmt = disCountAmt;
	}

	public BigDecimal getOrderDetailDisCountAmt() {
		return orderDetailDisCountAmt;
	}

	public void setOrderDetailDisCountAmt(BigDecimal orderDetailDisCountAmt) {
		this.orderDetailDisCountAmt = orderDetailDisCountAmt;
	}

	public BigDecimal getOrderDetailCouponDisCountAmt() {
		return orderDetailCouponDisCountAmt;
	}

	public void setOrderDetailCouponDisCountAmt(BigDecimal orderDetailCouponDisCountAmt) {
		this.orderDetailCouponDisCountAmt = orderDetailCouponDisCountAmt;
	}

	public BigDecimal getCouponAmt() {
		return CouponAmt;
	}

	public void setCouponAmt(BigDecimal couponAmt) {
		CouponAmt = couponAmt;
	}
	
}
