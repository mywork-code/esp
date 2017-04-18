package com.apass.esp.domain.entity.order;

import java.math.BigDecimal;
import java.util.Date;

import com.apass.gfb.framework.annotation.MyBatisEntity;

/**
 * 商品订单实体
 * @author wyy
 *
 */
@MyBatisEntity
public class OrderInfoEntity {
    /**
     * id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 订单金额
     */
    private BigDecimal orderAmt;
    /**
     * 支付状态
     */
    private String payStatus;
    /**
     * 订单编号
     */
    private String   orderId;
    /**
     * 主订单编号
     */
    private String   mainOrderId;
    /**
     * 支付类型
     */
    private String payType;
    /**
     * 状态
     */
    private String status;
    /**
     * 订单备注
     */
    private String   remark;
    /**
     * 商户code
     */
    private String merchantCode;
    /**
     * 物流厂商
     */
    private String logisticsName;
    /**
     * 物流单号
     */
    private String logisticsNo;
    /**
     * 物流唯一标识
     */
    private String logisticsId;
    /**
     * 物流签收时间
     */
    private Date logisticsSignDate;
    /**
     * 邮寄省份
     */
    private String province;
    /**
     * 邮寄城市
     */
    private String city;
    /**
     * 邮寄地区/县
     */
    private String district;
    /**
     * 邮寄详细地址
     */
    private String address;
    /**
     * 邮寄编码
     */
    private String   postcode;
    /**
     * 客户姓名
     */
    private String name;
    /**
     * 手机号码
     */
    private String telephone;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 更新日期
     */
    private Date updateDate;
    
    /**
     * 发货时间
     */
    private Date sendOutGoodsDate;
    /**
     * 最晚收货时间
     */
    private Date lastAcceptGoodsDate;
    
    /**
     * 确认收货时间
     */
    private Date acceptGoodsDate;
    
    /**
     * 延长收货次数
     */
    private Integer extendAcceptGoodsNum;
    
    /**
     * 收货方式
     */
    private String acceptGoodsType;
    
    /** 地址id */
    private Long addressId;
	
    /**
     * 商品数量
     */
    private Long       goodsNum;
    /**
     * 首付金额
     */
    private BigDecimal downPaymentAmount;
    
	public String getMainOrderId() {
        return mainOrderId;
    }
    public void setMainOrderId(String mainOrderId) {
        this.mainOrderId = mainOrderId;
    }
    public Date getLogisticsSignDate() {
        return logisticsSignDate;
    }
    public void setLogisticsSignDate(Date logisticsSignDate) {
        this.logisticsSignDate = logisticsSignDate;
    }
    public Long getGoodsNum() {
        return goodsNum;
    }
    public void setGoodsNum(Long goodsNum) {
        this.goodsNum = goodsNum;
    }
    public String getLogisticsId() {
        return logisticsId;
    }
    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }
    public Date getSendOutGoodsDate() {
        return sendOutGoodsDate;
    }
    public void setSendOutGoodsDate(Date sendOutGoodsDate) {
        this.sendOutGoodsDate = sendOutGoodsDate;
    }
    public Date getLastAcceptGoodsDate() {
        return lastAcceptGoodsDate;
    }
    public void setLastAcceptGoodsDate(Date lastAcceptGoodsDate) {
        this.lastAcceptGoodsDate = lastAcceptGoodsDate;
    }
    public Date getAcceptGoodsDate() {
        return acceptGoodsDate;
    }
    public void setAcceptGoodsDate(Date acceptGoodsDate) {
        this.acceptGoodsDate = acceptGoodsDate;
    }
    public Integer getExtendAcceptGoodsNum() {
        return extendAcceptGoodsNum;
    }
    public void setExtendAcceptGoodsNum(Integer extendAcceptGoodsNum) {
        this.extendAcceptGoodsNum = extendAcceptGoodsNum;
    }
    public String getAcceptGoodsType() {
        return acceptGoodsType;
    }
    public void setAcceptGoodsType(String acceptGoodsType) {
        this.acceptGoodsType = acceptGoodsType;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public BigDecimal getOrderAmt() {
        return orderAmt;
    }
    public void setOrderAmt(BigDecimal orderAmt) {
        this.orderAmt = orderAmt;
    }
    public String getPayStatus() {
        return payStatus;
    }
    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getPayType() {
        return payType;
    }
    public void setPayType(String payType) {
        this.payType = payType;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getMerchantCode() {
        return merchantCode;
    }
    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }
    public String getLogisticsName() {
        return logisticsName;
    }
    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }
    public String getLogisticsNo() {
        return logisticsNo;
    }
    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
    public Long getAddressId() {
        return addressId;
    }
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public BigDecimal getDownPaymentAmount() {
        return downPaymentAmount;
    }

    public void setDownPaymentAmount(BigDecimal downPaymentAmount) {
        this.downPaymentAmount = downPaymentAmount;
    }
}
