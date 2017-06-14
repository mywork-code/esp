package com.apass.esp.domain.vo;

import java.util.Date;

public class CashRefundVo {
	
	/**
	 * 操作表Id
	 */
	private Long operateId;
	
	/**
	 * 记录表Id
	 */
	private Long refundId;
	/**
	 * 订单Id
	 */
    private String orderId;
    /**
	 * 申请退款原因
	 */
    private String reason;
    /**
	 * 申请退款时间
	 */
    private Date applyDate;
    /**
	 * 订单Id
	 */
    private Integer status;
    /**
     * 申请退货的数量
     */
    private Integer goodsNum;
    
    /**
     * 申请结果
     */
    private String applayResult;
    
    /**
     * 退款类型（分为退货，换货，退款）
     */
    private String refundType;
    
    /**
     * 退款处理进度
     */
    private String refundProgress;
    
    /**
     * 审核时间
     */
    private Date reviewDate;
    
    /**
     * 审核人(商户名字)
     */
    private String reviewUser;
    
    /**
     * 审核人(平台管理者)
     */
    private String platformUser;
    
    /**
     * 商户备注
     */
    private String merchantNote;
    
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public Long getRefundId() {
		return refundId;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getApplayResult() {
		return applayResult;
	}

	public void setApplayResult(String applayResult) {
		this.applayResult = applayResult;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getRefundProgress() {
		return refundProgress;
	}

	public void setRefundProgress(String refundProgress) {
		this.refundProgress = refundProgress;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getReviewUser() {
		return reviewUser;
	}

	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}

	public String getMerchantNote() {
		return merchantNote;
	}

	public void setMerchantNote(String merchantNote) {
		this.merchantNote = merchantNote;
	}

	public String getPlatformUser() {
		return platformUser;
	}

	public void setPlatformUser(String platformUser) {
		this.platformUser = platformUser;
	}
	
}
