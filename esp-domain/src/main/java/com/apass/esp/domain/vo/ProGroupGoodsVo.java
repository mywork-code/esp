package com.apass.esp.domain.vo;
import java.math.BigDecimal;
import com.apass.esp.domain.entity.ProGroupGoods;
public class ProGroupGoodsVo extends ProGroupGoods{
    private String groupName ;
    //成本价
    private BigDecimal goodsCostPrice;
    //售价
    private BigDecimal goodsPrice;
    private String goodsName;
    private String skuAttr;
    private String goodsStatus;
    private String goodsCategory;
    private String detailDesc;
	//判断是否为第一个
	private Boolean isFirstOne;
	//判断是否为最后一个
	private Boolean isLastOne;
	public Boolean getIsFirstOne() {
		return isFirstOne;
	}
	public void setIsFirstOne(Boolean isFirstOne) {
		this.isFirstOne = isFirstOne;
	}
	public Boolean getIsLastOne() {
		return isLastOne;
	}
	public void setIsLastOne(Boolean isLastOne) {
		this.isLastOne = isLastOne;
	}
	public BigDecimal getGoodsCostPrice() {
		return goodsCostPrice;
	}
	public void setGoodsCostPrice(BigDecimal goodsCostPrice) {
		this.goodsCostPrice = goodsCostPrice;
	}
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getSkuAttr() {
        return skuAttr;
    }
    public void setSkuAttr(String skuAttr) {
        this.skuAttr = skuAttr;
    }
	public String getGoodsStatus() {
		return goodsStatus;
	}
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	public String getGoodsCategory() {
		return goodsCategory;
	}
	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}
	public String getDetailDesc() {
		return detailDesc;
	}
	public void setDetailDesc(String detailDesc) {
		this.detailDesc = detailDesc;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}