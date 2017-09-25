/**
 * @description Copyright (c) 2015-2016 liuchao01,Inc.All Rights Reserved.
 */
package com.apass.esp.domain.entity.goods;

import com.apass.esp.domain.enums.GoodStatus;
import com.apass.esp.domain.enums.GoodsType;
import com.apass.gfb.framework.annotation.MyBatisEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @description
 *
 * 				商品基本信息表 t_ajp_goods_base_info
 *
 * @author liuchao01
 * @version $Id: ProductInfo.java, v 0.1 2016年12月19日 下午1:46:38 liuchao01 Exp $
 */
@MyBatisEntity
public class GoodsInfoEntity {

    private Long id;

    // 等同id避免IOS关键字
    private Long goodId;

    // 商品编号
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

    public String getGoodsSiftUrlNew() {
        return goodsSiftUrlNew;
    }

    public void setGoodsSiftUrlNew(String goodsSiftUrlNew) {
        this.goodsSiftUrlNew = goodsSiftUrlNew;
    }

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
    private List<String> statuList;

    public List<String> getStatuList() {
        return statuList;
    }

    public void setStatuList(List<String> statuList) {
        this.statuList = statuList;
    }

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
     * 商品来源标识(如：jd)
     */
    private String source;

    /**
     * 外部商品id,唯一标识(如：对应t_esp_jd_goods表中jd_id)
     */
    private String externalId;

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

    public String getAttrDesc() {
        return attrDesc;
    }

    public void setAttrDesc(String attrDesc) {
        this.attrDesc = attrDesc;
    }

    /**
     * 起始索引
     */
    private Integer begin;

    /**
     * 页面大小
     */
    private Integer pageSize;

    /**
     * 是否支持7天退货
     */
    private String support7dRefund;

    public String getSupport7dRefund() {
        return support7dRefund;
    }

    public void setSupport7dRefund(String support7dRefund) {
        this.support7dRefund = support7dRefund;
    }

    public String getSource() {
        if(StringUtils.isBlank(source)){
            return "";
        }
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getExternalId() {
        if(StringUtils.isBlank(externalId)){
            return "";
        }
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getUnSupportProvince() {
        return unSupportProvince;
    }

    public void setUnSupportProvince(String unSupportProvince) {
        this.unSupportProvince = unSupportProvince;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNewCreatDate() {
        if(StringUtils.isBlank(newCreatDate)){
            return "1900-01-01 00:00:00";
        }
        return newCreatDate;
    }

    public void setNewCreatDate(Date newCreatDate) {
        this.newCreatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(newCreatDate);
    }

    public String getListTimeString() {
        return listTimeString;
    }

    public void setListTimeString(Date listTime) {
        this.listTimeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(listTime);
    }

    public String getDelistTimeString() {
        return delistTimeString;
    }

    public void setDelistTimeString(Date delistTime) {
        this.delistTimeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(delistTime);
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

	@Override
	public String toString() {
		return "GoodsInfoEntity [id=" + id + ", goodId=" + goodId
				+ ", goodsCode=" + goodsCode + ", categoryCode=" + categoryCode
				+ ", goodsName=" + goodsName + ", goodsTitle=" + goodsTitle
				+ ", goodsSellPt=" + goodsSellPt + ", goodsType=" + goodsType
				+ ", goodsTypeDesc=" + goodsTypeDesc + ", goodsLogoUrl="
				+ goodsLogoUrl + ", goodsLogoUrlNew=" + goodsLogoUrlNew
				+ ", goodsSiftUrl=" + goodsSiftUrl + ", goodsSiftUrlNew="
				+ goodsSiftUrlNew + ", listTime=" + listTime
				+ ", listTimeString=" + listTimeString + ", delistTime="
				+ delistTime + ", delistTimeString=" + delistTimeString
				+ ", newCreatDate=" + newCreatDate + ", proDate=" + proDate
				+ ", keepDate=" + keepDate + ", supNo=" + supNo
				+ ", createUser=" + createUser + ", updateUser=" + updateUser
				+ ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", merchantCode=" + merchantCode + ", remark=" + remark
				+ ", isDelete=" + isDelete + ", status=" + status
				+ ", statuList=" + statuList + ", statusDesc=" + statusDesc
				+ ", googsDetail=" + googsDetail + ", goodsSkuType="
				+ goodsSkuType + ", goodsModel=" + goodsModel
				+ ", merchantName=" + merchantName + ", merchantType="
				+ merchantType + ", categoryId1=" + categoryId1
				+ ", categoryId2=" + categoryId2 + ", categoryId3="
				+ categoryId3 + ", categoryName3=" + categoryName3
				+ ", unSupportProvince=" + unSupportProvince + ", source="
				+ source + ", externalId=" + externalId + ", goodsPrice="
				+ goodsPrice + ", firstPrice=" + firstPrice + ", attrDesc="
				+ attrDesc + ", begin=" + begin + ", pageSize=" + pageSize
				+ ", colFalgt=" + colFalgt + ", sordNo=" + sordNo + "]";
	}

}
