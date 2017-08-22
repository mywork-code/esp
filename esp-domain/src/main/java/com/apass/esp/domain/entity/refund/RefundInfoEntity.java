package com.apass.esp.domain.entity.refund;

import com.apass.gfb.framework.annotation.MyBatisEntity;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 退货信息实体
 * 
 * @author wyy
 *
 */
@MyBatisEntity
public class RefundInfoEntity {
    /**
     * id
     */
    private Long id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 订单金额
     */
    private BigDecimal orderAmt;

    /**
     * 退货金额
     */
    private BigDecimal refundAmt;

    /**
     * 退换货类型(下拉 ：退货、换货)
     */
    private String refundType;

    /**
     * 客户发货物流厂商
     */
    private String slogisticsName;

    /**
     * 客户发货物流单号
     */
    private String slogisticsNo;

    /**
     * 换货商户发货物流唯一标识
     */
    private String rlogisticsId;

    /**
     * 换货商户发货物流厂商
     */
    private String rlogisticsName;

    /**
     * 换货商户发货物流单号
     */
    private String rlogisticsNo;

    /**
     * 状态
     */
    private String status;

    /**
     * 拒绝原因
     */
    private String refundReason;

    /**
     * 驳回原因
     */
    private String rejectReason;

    /** 批复人 */
    private String approvalUser;

    /** 批复结果 */
    private String approvalComments;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 更新日期
     */
    private Date updateDate;

    /** 　图片url */
    private String goodsUrl;

    /** 售后是否同意退换货 1 : 同意 0：拒绝 */
    private String isAgree;

    private Date completionTime;

    /**
     * 京东退换货返回方式
     */
    private String jdReturnType ;

    public String getJdReturnType() {
        if(StringUtils.isBlank(jdReturnType)){
            return "";            
        }
        return jdReturnType;
    }

    public void setJdReturnType(String jdReturnType) {
        this.jdReturnType = jdReturnType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(BigDecimal orderAmt) {
        this.orderAmt = orderAmt;
    }

    public BigDecimal getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(BigDecimal refundAmt) {
        this.refundAmt = refundAmt;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getSlogisticsName() {
        return slogisticsName;
    }

    public void setSlogisticsName(String slogisticsName) {
        this.slogisticsName = slogisticsName;
    }

    public String getSlogisticsNo() {
        return slogisticsNo;
    }

    public void setSlogisticsNo(String slogisticsNo) {
        this.slogisticsNo = slogisticsNo;
    }

    public String getRlogisticsId() {
        return rlogisticsId;
    }

    public void setRlogisticsId(String rlogisticsId) {
        this.rlogisticsId = rlogisticsId;
    }

    public String getRlogisticsName() {
        return rlogisticsName;
    }

    public void setRlogisticsName(String rlogisticsName) {
        this.rlogisticsName = rlogisticsName;
    }

    public String getRlogisticsNo() {
        return rlogisticsNo;
    }

    public void setRlogisticsNo(String rlogisticsNo) {
        this.rlogisticsNo = rlogisticsNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public String getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(String isAgree) {
        this.isAgree = isAgree;
    }

    public String getApprovalUser() {
        return approvalUser;
    }

    public void setApprovalUser(String approvalUser) {
        this.approvalUser = approvalUser;
    }

    public String getApprovalComments() {
        return approvalComments;
    }

    public void setApprovalComments(String approvalComments) {
        this.approvalComments = approvalComments;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

}
