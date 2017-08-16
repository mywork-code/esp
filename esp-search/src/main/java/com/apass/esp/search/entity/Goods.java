package com.apass.esp.search.entity;


import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xianzhi.wang on 2017/5/22.
 */
public class Goods implements IdAble{
	
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

    /** 商品上架时间 **/
    private Date listTime;
    private String listTimeString;
    /** 商品下架时间 **/
    private Date delistTime;
    private String delistTimeString;
    /** 商品新建时间 **/
    private String newCreatDate = "1900-01-01 00:00:00";
    
    /** 创建时间 **/
    private Date createDate;
    /** 修改时间 **/
    private Date updateDate;
  
    /** 商品一级分类 */
    private Long categoryId1;
    /** 商品一级分类名称 */
    private String categoryName1;
    /** 商品二级分类 */
    private Long categoryId2;
    /** 商品二级分类名称 */
    private String categoryName2;
    /** 商品三级分类 */
    private Long categoryId3;
    /** 商品三级分类名称 */
    private String categoryName3;
    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;
    /**
     * 首付价
     */
    private BigDecimal firstPrice;
    
    /**
     * 市场价
     */
    private BigDecimal marketPrice;
    /**
     * 来源（京东或非京东）
     * @return
     */
    private String source="";
    /**
     * 总销量
     */
    private int saleNum;
    /**
     * 近30天销量
     */
    private int saleNumFor30;

   
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

	public Date getListTime() {
		return listTime;
	}

	public void setListTime(Date listTime) {
		this.listTime = listTime;
	}

	public String getListTimeString() {
		return listTimeString;
	}

	public void setListTimeString(String listTimeString) {
		this.listTimeString = listTimeString;
	}

	public Date getDelistTime() {
		return delistTime;
	}

	public void setDelistTime(Date delistTime) {
		this.delistTime = delistTime;
	}

	public String getDelistTimeString() {
		return delistTimeString;
	}

	public void setDelistTimeString(String delistTimeString) {
		this.delistTimeString = delistTimeString;
	}

	public String getNewCreatDate() {
		return newCreatDate;
	}

	public void setNewCreatDate(String newCreatDate) {
		this.newCreatDate = newCreatDate;
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

	public Long getCategoryId1() {
		return categoryId1;
	}

	public void setCategoryId1(Long categoryId1) {
		this.categoryId1 = categoryId1;
	}

	public String getCategoryName1() {
		return categoryName1;
	}

	public void setCategoryName1(String categoryName1) {
		this.categoryName1 = categoryName1;
	}

	public Long getCategoryId2() {
		return categoryId2;
	}

	public void setCategoryId2(Long categoryId2) {
		this.categoryId2 = categoryId2;
	}

	public String getCategoryName2() {
		return categoryName2;
	}

	public void setCategoryName2(String categoryName2) {
		this.categoryName2 = categoryName2;
	}

	public Long getCategoryId3() {
		return categoryId3;
	}

	public void setCategoryId3(Long categoryId3) {
		this.categoryId3 = categoryId3;
	}

	public String getCategoryName3() {
		return categoryName3;
	}

	public void setCategoryName3(String categoryName3) {
		this.categoryName3 = categoryName3;
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

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}

	public int getSaleNumFor30() {
		return saleNumFor30;
	}

	public void setSaleNumFor30(int saleNumFor30) {
		this.saleNumFor30 = saleNumFor30;
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