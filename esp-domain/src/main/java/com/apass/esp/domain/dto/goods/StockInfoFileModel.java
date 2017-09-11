package com.apass.esp.domain.dto.goods;

import org.springframework.web.multipart.MultipartFile;

/**
 * 新增库存 上传图片
 * @author zhanwendong
 *
 */
public class StockInfoFileModel {
	
	private Long addstockInfogoodsId;
	
	private String goodsSkuAttr;
	
	private String goodsPrice;
	
	private String marketPrice;
	
	private String goodsCompareUrl;//比价链接1
	
	private String goodsCompareUrl2;//比价链接2
	
	public String getGoodsCompareUrl() {
		return goodsCompareUrl;
	}

	public void setGoodsCompareUrl(String goodsCompareUrl) {
		this.goodsCompareUrl = goodsCompareUrl;
	}

	public Long getAddstockInfogoodsId() {
		return addstockInfogoodsId;
	}

	public void setAddstockInfogoodsId(Long addstockInfogoodsId) {
		this.addstockInfogoodsId = addstockInfogoodsId;
	}

	public String getGoodsSkuAttr() {
		return goodsSkuAttr;
	}

	public void setGoodsSkuAttr(String goodsSkuAttr) {
		this.goodsSkuAttr = goodsSkuAttr;
	}

	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getStockTotalAmt() {
		return stockTotalAmt;
	}

	public void setStockTotalAmt(String stockTotalAmt) {
		this.stockTotalAmt = stockTotalAmt;
	}

	public String getGoodsCostPrice() {
		return goodsCostPrice;
	}

	public void setGoodsCostPrice(String goodsCostPrice) {
		this.goodsCostPrice = goodsCostPrice;
	}

	public MultipartFile getStockLogoFile() {
		return stockLogoFile;
	}

	public void setStockLogoFile(MultipartFile stockLogoFile) {
		this.stockLogoFile = stockLogoFile;
	}
	
	

	public String getGoodsCompareUrl2() {
        return goodsCompareUrl2;
    }

    public void setGoodsCompareUrl2(String goodsCompareUrl2) {
        this.goodsCompareUrl2 = goodsCompareUrl2;
    }



    private String stockTotalAmt;
	
	private String goodsCostPrice;
	
	private MultipartFile stockLogoFile;

	@Override
	public String toString() {
		return "StockInfoFileModel [addstockInfogoodsId=" + addstockInfogoodsId
				+ ", goodsSkuAttr=" + goodsSkuAttr + ", goodsPrice="
				+ goodsPrice + ", marketPrice=" + marketPrice
				+ ", goodsCompareUrl=" + goodsCompareUrl + ", goodsCompareUrl2="
				+ goodsCompareUrl2 + ", stockTotalAmt=" + stockTotalAmt
				+ ", goodsCostPrice=" + goodsCostPrice + ", stockLogoFile="
				+ stockLogoFile.getName() + "]";
	}
	
	
}
