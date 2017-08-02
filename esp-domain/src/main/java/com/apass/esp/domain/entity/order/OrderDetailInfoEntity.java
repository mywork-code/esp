package com.apass.esp.domain.entity.order;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.apass.gfb.framework.annotation.MyBatisEntity;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.ctc.wstx.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 商品订单详情实体
 * 
 * @author wyy
 *
 */
@MyBatisEntity
public class OrderDetailInfoEntity {
    /**
     * id
     */
    private Long id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品库存Id
     */
    private Long goodsStockId;

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 商品数量
     */
    private Long goodsNum;

    /**
     * 商品标题
     */
    private String goodsTitle;

    /**
     * 商品类目编码
     */
    private String categoryCode;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品卖点
     */
    private String goodsSellPt;

    /**
     * 商品类型
     */
    private String goodsType;

    /**
     * 商品logo地址
     */
    private String goodsLogoUrl;

    /**
     * 商户code
     */
    private String merchantCode;

    /**
     * 商品上架时间
     */
    private Date listTime;

    /**
     * 下架时间
     */
    private Date delistTime;

    /**
     * 商品生产日期
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date proDate;

    /**
     * 商品有效期多少月
     */
    private String keepDate;

    /**
     * 生产厂家
     */
    private String supNo;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 更新日期
     */
    private Date updateDate;

    /**
     * 商品型号
     */
    private String goodsModel;

    /**
     * 商品规格
     */
    private String goodsSkuAttr;

    /**
     * 
     * 商品的当前库存
     */
    private Long stockCurrAmt;

    /**
     * 来源（比如：京东（jd））
     */
    private String source;

    /**
     * 京东商品的编号
     */
    private String skuId;

    // 商品金额
    private BigDecimal goodsAmt;

    // 退款金额
    private BigDecimal refundAmt;

    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }

    public String getGoodsSkuAttr() {
        return goodsSkuAttr;
    }

    public void setGoodsSkuAttr(String goodsSkuAttr) {
        this.goodsSkuAttr = goodsSkuAttr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsStockId() {
        return goodsStockId;
    }

    public void setGoodsStockId(Long goodsStockId) {
        this.goodsStockId = goodsStockId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Long getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Long goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
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

    public String getGoodsLogoUrl() {
        return goodsLogoUrl;
    }

    public void setGoodsLogoUrl(String goodsLogoUrl) {
        this.goodsLogoUrl = goodsLogoUrl;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
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

    public Long getStockCurrAmt() {
        return stockCurrAmt;
    }

    public void setStockCurrAmt(Long stockCurrAmt) {
        this.stockCurrAmt = stockCurrAmt;
    }

    public String getSource() {
        if(StringUtils.isBlank(source)){
            return "";
        }
        return source;
    }

    public void setSource(String source) {
        if(StringUtils.isBlank(source)){
            this.source = "";
        }
        this.source = source;
    }

    public String getSkuId() {
        if(StringUtils.isBlank(skuId)){
            return "";
        }
        return skuId;
    }

    public void setSkuId(String skuId) {
        if(StringUtils.isBlank(skuId)){
            this.skuId = "";
        }
        this.skuId = skuId;
    }

    public BigDecimal getGoodsAmt() {
        return goodsAmt;
    }

    public void setGoodsAmt(BigDecimal goodsAmt) {
        this.goodsAmt = goodsAmt;
    }

    public BigDecimal getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(BigDecimal refundAmt) {
        this.refundAmt = refundAmt;
    }

}
