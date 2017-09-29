package com.apass.esp.domain.dto.order;

import com.apass.esp.domain.dto.goods.GoodsInfoInOrderDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品订单信息实体
 * @author wyy
 *
 */
public class OrderDetailInfoDto {
    
    /** 订单编号   **/
    private String   orderId;
    
    /** 订单金额    **/
    private BigDecimal orderAmt;
    
    /** 状态  **/
    private String status;
    
    /** 订单中商品总数量    **/
    private int goodsNumSum;
    /**
     * 订单生成时间
     */
    private Date orderCreateDate;

    private String orderCreateDateStr;

    private Long userId;

    private String mainOrderId;

    public String getOrderCreateDateStr() {
        return orderCreateDateStr;
    }

    public void setOrderCreateDateStr(String orderCreateDateStr) {
        this.orderCreateDateStr = orderCreateDateStr;
    }

    /**
     * 待付款订单剩余时间
     */
    private Date remainingTime;
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
     * 客户姓名
     */
    private String name;
    /**
     * 手机号码
     */
    private String telephone;
    
    private Long addressId;
    
    /** 交易完成状态的订单 是否允许售后操作 1/0 */
    private String refundAllowedFlag;
    
    /**
     * 商品延长收货标记     0:显示延长收货    1:不显示
     */
    private String delayAcceptGoodFlag="0";
    
    private List<GoodsInfoInOrderDto> orderDetailInfoList ;
    
    /**
     * 商户是否发货
     * @return
     */
    private String preDelivery;
    
    /**
     * 退款状态(0.退款 1.处理中 2.退款中 3.退款成功 4.取消退款)
     */
    private String cashRefundStatus;
    /**
     * 来源
     */
    private String source;
    /**
     * 支付方式
     */
    private String payType;
    
    /**
     * 优惠金额
     */
    private BigDecimal disCountAmt;
    
    /**
     * 总金额
     */
    private BigDecimal totalAmt;
    
	public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDelayAcceptGoodFlag() {
        return delayAcceptGoodFlag;
    }

    public void setDelayAcceptGoodFlag(String delayAcceptGoodFlag) {
        this.delayAcceptGoodFlag = delayAcceptGoodFlag;
    }

    public Date getOrderCreateDate() {
        return orderCreateDate;
    }

    public void setOrderCreateDate(Date orderCreateDate) {
        this.orderCreateDate = orderCreateDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getGoodsNumSum() {
        return goodsNumSum;
    }

    public void setGoodsNumSum(int goodsNumSum) {
        this.goodsNumSum = goodsNumSum;
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

    public List<GoodsInfoInOrderDto> getOrderDetailInfoList() {
        return orderDetailInfoList;
    }

    public void setOrderDetailInfoList(List<GoodsInfoInOrderDto> orderDetailInfoList) {
        this.orderDetailInfoList = orderDetailInfoList;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getRefundAllowedFlag() {
        return refundAllowedFlag;
    }

    public void setRefundAllowedFlag(String refundAllowedFlag) {
        this.refundAllowedFlag = refundAllowedFlag;
    }

    public Date getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(Date remainingTime) {
        this.remainingTime = remainingTime;
    }

	public String getPreDelivery() {
		return preDelivery;
	}

	public void setPreDelivery(String preDelivery) {
		this.preDelivery = preDelivery;
	}

	public String getCashRefundStatus() {
		return cashRefundStatus;
	}

	public void setCashRefundStatus(String cashRefundStatus) {
		this.cashRefundStatus = cashRefundStatus;
	}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMainOrderId() {
        return mainOrderId;
    }

    public void setMainOrderId(String mainOrderId) {
        this.mainOrderId = mainOrderId;
    }

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public BigDecimal getDisCountAmt() {
		return disCountAmt;
	}

	public void setDisCountAmt(BigDecimal disCountAmt) {
		this.disCountAmt = disCountAmt;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}
	
}
