package com.apass.esp.domain.query;

import java.math.BigDecimal;
import java.util.Date;

import com.apass.esp.common.model.QueryParams;

public class ProGroupGoodsQuery extends QueryParams{
	  private Long id;

	    private Long goodsId;

	    private String skuId;

	    private Long groupId;
	    
	    private String groupName ;
	    //成本价
	    private BigDecimal goodsCostPrice;
	    //售价
	    private BigDecimal goodsPrice;
	    //市场价
	    private BigDecimal marketPrice;
	    //活动价
	    private BigDecimal activityPrice;
	    
	    private Long orderSort;

	    private String goodsCode;
	    
	    private String goodsName;
	    
	    private String goodsStatus;
	    
	    private String goodsCategory;
	    
	    /** 商品一级分类 */
	    private Long categoryId1;

	    /** 商品二级分类 */
	    private Long categoryId2;

	    /** 商品三级分类 */
	    private Long categoryId3;


	    private String createUser;

	    private String updateUser;

	    private Date createDate;

	    private Date updateDate;

	    private String detailDesc;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getGoodsId() {
			return goodsId;
		}

		public void setGoodsId(Long goodsId) {
			this.goodsId = goodsId;
		}

		public String getSkuId() {
			return skuId;
		}

		public void setSkuId(String skuId) {
			this.skuId = skuId;
		}

		public Long getGroupId() {
			return groupId;
		}

		public void setGroupId(Long groupId) {
			this.groupId = groupId;
		}

		public String getGroupName() {
			return groupName;
		}

		public void setGroupName(String groupName) {
			this.groupName = groupName;
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

		public BigDecimal getMarketPrice() {
			return marketPrice;
		}

		public void setMarketPrice(BigDecimal marketPrice) {
			this.marketPrice = marketPrice;
		}

		public BigDecimal getActivityPrice() {
			return activityPrice;
		}

		public void setActivityPrice(BigDecimal activityPrice) {
			this.activityPrice = activityPrice;
		}

		public Long getOrderSort() {
			return orderSort;
		}

		public void setOrderSort(Long orderSort) {
			this.orderSort = orderSort;
		}

		public String getGoodsCode() {
			return goodsCode;
		}

		public void setGoodsCode(String goodsCode) {
			this.goodsCode = goodsCode;
		}

		public String getGoodsName() {
			return goodsName;
		}

		public void setGoodsName(String goodsName) {
			this.goodsName = goodsName;
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

		public String getDetailDesc() {
			return detailDesc;
		}

		public void setDetailDesc(String detailDesc) {
			this.detailDesc = detailDesc;
		}

		public Long getCategoryId1() {
			return categoryId1;
		}

		public void setCategoryId1(Long categoryId1) {
			this.categoryId1 = categoryId1;
		}

		public Long getCategoryId2() {
			return categoryId2;
		}

		public void setCategoryId2(Long categoryId2) {
			this.categoryId2 = categoryId2;
		}

		public Long getCategoryId3() {
			return categoryId3;
		}

		public void setCategoryId3(Long categoryId3) {
			this.categoryId3 = categoryId3;
		}
		
}
