package com.apass.esp.domain.entity.refund;

import java.math.BigDecimal;
import java.util.Date;

import com.apass.esp.domain.enums.RefundReason;
import com.apass.esp.domain.enums.RefundStatus;
import com.apass.gfb.framework.annotation.MyBatisEntity;

/**
 * 
 * 售后流程详情表
 * 
 * @description
 *
 * @author liuchao01
 * @version $Id: ServiceProcessEntity.java, v 0.1 2017年1月3日 上午10:24:06 liuchao01
 *          Exp ${0xD}
 */
@MyBatisEntity
public class ServiceProcessEntity {

    private Long       id;

    /** 退换货主键id **/
    private Long       refundId;

    /** 订单id **/
    private String     orderId;

    /** 商品图片 **/
    private String     goodsUrl;

    /** 售后类型（退货、换货） **/
    private String     refundType;

    /** 售后状态 **/
    private String     status;

    /** 售后状态描述 **/
    private String     statusDesc;

    /** 售后数量 **/
    private String     goodsNum;

    /** 售后金额 **/
    private BigDecimal goodsPrice;

    /** 申请原因 **/
    private String     refundReason;

    /** 申请原因 **/
    private String     refundReasonDes;

    /** 申请原因--翻译 **/
    public String getRefundReasonDes() {
        return refundReasonDes;
    }

    public void setRefundReasonDes(String refundReasonDes) {
        // 对申请原因进行翻译 ，前段展示统一显示中文
        String content = "";
        RefundReason[] refundReasonArray = RefundReason.values();
        for (RefundReason refundReason : refundReasonArray) {
            if (refundReason.getCode().equals(refundReasonDes)) {
                content = refundReason.getMessage();
            }
        }
        this.refundReasonDes = content;
    }

    /** 节点名称 **/
    private String nodeName;

    /**备注信息**/
    private String nodeMessage = "";

    /** 换货商户发货物流厂商 **/
    private String rLogisticsName;

    /** 换货商户发货物流单号 **/
    private String rLogisticsNo;

    /** 客户发货物流厂商 名称**/
    private String sLogisticsName;

    /** 客户发货物流厂商  单号**/
    private String sLogisticsNo;

    /** 审批人 **/
    private String approvalUser;

    /** 审批内容 **/
    private String approvalComments;

    /** 是否同意 **/
    private String isAgree;

    private Date   createDate;

    private Date   updateDate;

    private String remark;


    public String getNodeMessage() {
        return nodeMessage;
    }

    public void setNodeMessage(String nodeMessage) {
        this.nodeMessage = nodeMessage;
    }
    /**
     * 支付方式
     */
    private String payType;
    
    public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRefundId() {
        return refundId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public void setRefundId(Long refundId) {
        this.refundId = refundId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getrLogisticsName() {
        return rLogisticsName;
    }

    public void setrLogisticsName(String rLogisticsName) {
        this.rLogisticsName = rLogisticsName;
    }

    public String getrLogisticsNo() {
        return rLogisticsNo;
    }

    public void setrLogisticsNo(String rLogisticsNo) {
        this.rLogisticsNo = rLogisticsNo;
    }

    public String getsLogisticsName() {
        return sLogisticsName;
    }

    public void setsLogisticsName(String sLogisticsName) {
        this.sLogisticsName = sLogisticsName;
    }

    public String getsLogisticsNo() {
        return sLogisticsNo;
    }

    public void setsLogisticsNo(String sLogisticsNo) {
        this.sLogisticsNo = sLogisticsNo;
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

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
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
        // 对售后状态进行翻译 ，前段展示统一显示中文
        String content = "";
        RefundStatus[] refundStatusArray = RefundStatus.values();
        for (RefundStatus refundStatus : refundStatusArray) {
            if (refundStatus.getCode().equals(statusDesc)) {
                content = refundStatus.getMessage();
            }
        }
        this.statusDesc = content;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(String isAgree) {
        this.isAgree = isAgree;
    }
}
