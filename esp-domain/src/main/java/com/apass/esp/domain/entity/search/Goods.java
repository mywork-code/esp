package com.apass.esp.domain.entity.search;

import com.apass.esp.domain.enums.GoodStatus;
import com.apass.esp.domain.enums.GoodsType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by xianzhi.wang on 2017/5/22.
 */
public class Goods implements IdAble{

    private Long goodId;
    //商品编号
    private String goodsCode;

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    /** 商品类目编码 **/
    private String categoryCode;

    /** 商品名称 **/
    private String goodsName;

    /** 商品小标题 **/
    private String goodsTitle;

    /** 商品卖点 **/
    private String goodsSellPt;

    /** 商品类型 -1：正常，精选**/
    private String goodsType;

    /** 商品类型描述 **/
    private String goodsTypeDesc;

    /** 商品logo地址 **/
    private String goodsLogoUrl;

    /** 商品logo地址 (新)**/
    private String goodsLogoUrlNew;

    /** 精选商品地址 **/
    private String goodsSiftUrl;

    /** 精选商品地址 (新)**/
    private String goodsSiftUrlNew;

    public String getGoodsSiftUrlNew() {
        return goodsSiftUrlNew;
    }

    public void setGoodsSiftUrlNew(String goodsSiftUrlNew) {
        this.goodsSiftUrlNew = goodsSiftUrlNew;
    }

    /** 商品上架时间 **/
    private Date listTime;

    /** 商品下架时间 **/
    private Date delistTime;

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

    public String getColFalgt() {
        return colFalgt;
    }

    public void setColFalgt(String colFalgt) {
        this.colFalgt = colFalgt;
    }

    private String colFalgt;// 标识

    /**
     * 商品排序
     */
    private Integer sordNo;

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Integer getSordNo() {
        return sordNo;
    }

    public void setSordNo(Integer sordNo) {
        this.sordNo = sordNo;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }

    public String getGoodsSkuType() {
        return goodsSkuType;
    }

    public void setGoodsSkuType(String goodsSkuType) {
        this.goodsSkuType = goodsSkuType;
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
        // 对商品类型进行翻译 ，前段展示统一显示中文
        String content = "";
        GoodsType[] goodsType = GoodsType.values();
        for (GoodsType goodType : goodsType) {
            if (goodType.getCode().equals(goodsTypeDesc)) {
                content = goodType.getMessage();
            }
        }
        this.goodsTypeDesc = content;
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

    public Date getListTime() {
        return listTime;
    }

    public void setListTime(Date listTime) {
        this.listTime = listTime;
    }

    public Date getDelistTime() {
        return delistTime;
    }

    public void setDelistTime(Date delistTime) {
        this.delistTime = delistTime;
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
        // 对商品状态进行翻译 ，前段展示统一显示中文
        String content = "";
        GoodStatus[] goodsStatus = GoodStatus.values();
        for (GoodStatus goodStatus : goodsStatus) {
            if (goodStatus.getCode().equals(statusDesc)) {
                content = goodStatus.getMessage();
            }
        }
        this.statusDesc = content;
    }

    public String getGoogsDetail() {
        return googsDetail;
    }

    public void setGoogsDetail(String googsDetail) {
        this.googsDetail = googsDetail;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }
}