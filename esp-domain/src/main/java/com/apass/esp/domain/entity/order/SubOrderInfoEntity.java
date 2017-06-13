package com.apass.esp.domain.entity.order;

import java.math.BigDecimal;
import java.util.Date;

import com.apass.gfb.framework.annotation.MyBatisEntity;

/**
 * 商户订单实体
 * @description 
 *
 * @author liuming
 * @version $Id: SubOrderInfoEntity.java, v 0.1 2016年12月23日 上午11:31:38 liuming Exp $
 */
@MyBatisEntity
public class SubOrderInfoEntity {
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
     * 订单编号
     */
    private String   orderId;
    /**
     * 状态
     */
    private String status;
    /**
     * 子订单号
     */
    private String subOrderId;
    /**
     * 商户编码
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
     * 订单备注
     */
    private String   remark;
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
    /**
     * 创建日期
     */
    private String createDate;
    /**
     * 更新日期
     */
    private String updateDate;
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
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getSubOrderId() {
        return subOrderId;
    }
    public void setSubOrderId(String subOrderId) {
        this.subOrderId = subOrderId;
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
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
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
	
}
