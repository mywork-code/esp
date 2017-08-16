package com.apass.esp.search.entity;


import com.apass.esp.domain.enums.GoodStatus;
import com.apass.esp.domain.enums.GoodsType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xianzhi.wang on 2017/5/22.
 */
public class Goods implements IdAble{

    private Long goodId;
    //商品编号
    private String goodsCode;
    /** 商品类目编码 **/
    private String categoryCode;
    /** 商品名称 **/
    private String goodsName;
    /** 商品小标题 **/
    private String goodsTitle;
    /** 商品卖点 **/
    private String goodsSellPt;
    /** 商品类型 -1：正常，精选 **/
    private String goodsType;
    /** 商品类型描述 **/
    private String goodsTypeDesc;
    /** 商品logo地址 **/
    private String goodsLogoUrl;
    /** 商品logo地址 (新) **/
    private String goodsLogoUrlNew;
    /** 精选商品地址 **/
    private String goodsSiftUrl;
    /** 精选商品地址 (新) **/
    private String goodsSiftUrlNew;
    /** 商品上架时间 **/
    private Date listTime;
    private String listTimeString;
    /** 商品下架时间 **/
    private Date delistTime;
    private String delistTimeString;
    /** 商品新建时间 **/
    private String newCreatDate = "1900-01-01 00:00:00";
    /** 商品生产日期 **/
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date proDate;
    /** 商品保质期多少月 **/
    private String keepDate;
    /** 生产厂家 **/
    private String supNo;
    /** 创建人 **/
    private String createUser;
    /** 修改人 **/
    private String updateUser;
    /** 创建时间 **/
    private Date createDate;
    /** 修改时间 **/
    private Date updateDate;
    /** 商户号 **/
    private String merchantCode;
    /** 备注 **/
    private String remark;
    /** 是否删除 **/
    private String isDelete;
    /** 商品状态 **/
    private String status;
    /** 商品状态描述 **/
    private String statusDesc;
    /** 商品详情 **/
    private String googsDetail;
    /**
     * 商品最小单元分类-规格类型(颜色，尺寸等)
     */
    private String goodsSkuType;
    /**
     * 商品型号
     */
    private String goodsModel;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 商户类型（个人、企业）
     */
    private String merchantType;
    /** 商品一级分类 */
    private Long categoryId1;
    /** 商品二级分类 */
    private Long categoryId2;
    /** 商品三级分类 */
    private Long categoryId3;
    /** 商品三级分类名称 */
    private String categoryName3;
    /**
     * 不支持配送区域
     */
    private String unSupportProvince;
    /**
     * 外部商品id,唯一标识(如：对应t_esp_jd_goods表中jd_id)
     */
    private String externalId = "";
    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;
    /**
     * 首付价
     */
    private BigDecimal firstPrice;
    /**
     * 属性描述
     */
    private String attrDesc;

    private String colFalgt;// 标识
    /**
     * 商品排序
     */
    private Integer sordNo;
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

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
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

    public String getGoodsSellPt() {
        return goodsSellPt;
    }

    public void setGoodsSellPt(String goodsSellPt) {
        this.goodsSellPt = goodsSellPt;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsTypeDesc() {
        return goodsTypeDesc;
    }

    public void setGoodsTypeDesc(String goodsTypeDesc) {
        this.goodsTypeDesc = goodsTypeDesc;
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

    public String getGoodsSiftUrl() {
        return goodsSiftUrl;
    }

    public void setGoodsSiftUrl(String goodsSiftUrl) {
        this.goodsSiftUrl = goodsSiftUrl;
    }

    public String getGoodsSiftUrlNew() {
        return goodsSiftUrlNew;
    }

    public void setGoodsSiftUrlNew(String goodsSiftUrlNew) {
        this.goodsSiftUrlNew = goodsSiftUrlNew;
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

    public Date getProDate() {
        return proDate;
    }

    public void setProDate(Date proDate) {
        this.proDate = proDate;
    }

    public String getKeepDate() {
        return keepDate;
    }

    public void setKeepDate(String keepDate) {
        this.keepDate = keepDate;
    }

    public String getSupNo() {
        return supNo;
    }

    public void setSupNo(String supNo) {
        this.supNo = supNo;
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

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getGoogsDetail() {
        return googsDetail;
    }

    public void setGoogsDetail(String googsDetail) {
        this.googsDetail = googsDetail;
    }

    public String getGoodsSkuType() {
        return goodsSkuType;
    }

    public void setGoodsSkuType(String goodsSkuType) {
        this.goodsSkuType = goodsSkuType;
    }

    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
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

    public String getCategoryName3() {
        return categoryName3;
    }

    public void setCategoryName3(String categoryName3) {
        this.categoryName3 = categoryName3;
    }

    public String getUnSupportProvince() {
        return unSupportProvince;
    }

    public void setUnSupportProvince(String unSupportProvince) {
        this.unSupportProvince = unSupportProvince;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
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

    public String getAttrDesc() {
        return attrDesc;
    }

    public void setAttrDesc(String attrDesc) {
        this.attrDesc = attrDesc;
    }

    public String getColFalgt() {
        return colFalgt;
    }

    public void setColFalgt(String colFalgt) {
        this.colFalgt = colFalgt;
    }

    public Integer getSordNo() {
        return sordNo;
    }

    public void setSordNo(Integer sordNo) {
        this.sordNo = sordNo;
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
        return null;
    }

    @Override
    public void setId(Integer id) {

    }
}