package com.apass.esp.domain.entity.order;

import java.math.BigDecimal;
import java.util.Date;

import com.apass.esp.domain.enums.GoodsType;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.enums.PaymentType;
import com.apass.esp.domain.enums.PreDeliveryType;
import com.apass.gfb.framework.annotation.MyBatisEntity;
import com.apass.gfb.framework.utils.DateFormatUtil;

/**
 * 订单信息实体（所以订单相关信息实体）
 * 
 * @author chenbo
 *
 */
/**
 * @description 
 *
 * @author dell
 * @version $Id: OrderSubInfoEntity.java, v 0.1 2017年2月18日 上午11:39:12 dell Exp $
 */
/**
 * @description 
 *
 * @author xiaohai
 * @version $Id: OrderSubInfoEntity.java, v 0.1 2017年3月15日 下午7:29:26 xiaohai Exp $
 */
@MyBatisEntity
public class OrderSubInfoEntity {
    /**
     * id
     */
    private Long       id;

    /**
     * 用户id
     */
    private Long       userId;

    /**
     * 订单金额
     */
    private BigDecimal orderAmt;

    /**
     * 订单编号
     */
    private String     orderId;

    /**
     * 支付类型
     */
    private String     payType;
    /**
     * 支付时间
     */
    private String    payDate;

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = DateFormatUtil.datetime2String(payDate);
    }

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 订单状态描述
     */
    private String orderStatusDsc;

    /**
     * 支付状态
     */
    private String payStatus;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 售后Id
     */
    private String refundId;
    
    /**
     * 售后类型
     */
    private String refundType;

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

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
     * 邮寄完整地址
     */
    private String fullAddress;

    /**
     * 邮寄编码
     */
    private String postcode;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String telephone;

    /**
     * 物流厂商
     */
    private String logisticsName;
    private String logisticsNameDes;

    
    /**
     * 商户是否发货
     * @return
     */
    private String preDelivery;
    
    /**
     * 商户是否发货详细信息
     */
    private String preDeliveryMsg;
    
    public String getLogisticsNameDes() {
        return logisticsNameDes;
    }

    public void setLogisticsNameDes(String logisticsNameDes) {
        this.logisticsNameDes = logisticsNameDes;
    }

    /**
     * 物流单号
     */
    private String logisticsNo;

    /**
     * 物流状态
     */
    private String logisticsStatus;

    public String getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(String logisticsStatus) {
        // 对物流状态进行翻译 ，前段展示统一显示中文
        //        String content = "";
        //        TrackingmoreStatus[] trackingmoreStatusArray = TrackingmoreStatus.values();
        //        for (TrackingmoreStatus trackingmoreStatus : trackingmoreStatusArray) {
        //            if (trackingmoreStatus.getCode().equals(logisticsStatus)) {
        //                content = trackingmoreStatus.getMessage();
        //            }
        //        }
        //
        //        this.logisticsStatus = content;
        this.logisticsStatus = logisticsStatus;
    }

    /**
     * 物流公司联系电话
     */
    private String logisticsTel;

    public String getLogisticsTel() {
        return logisticsTel;
    }

    public void setLogisticsTel(String logisticsTel) {
        this.logisticsTel = logisticsTel;
    }

    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 商户编码
     */
    private String merchantCode;

    //商品编号，商品名称，商品类型，商品型号，商品规格，购买量，价格
    private String goodsId;
    private String goodsName;
    private String goodsType;
    private String goodsTypeDesc;
    private String goodsModel;
    private String goodsSkuType;
    private String goodsNum;
    private String goodsSkuAttr;
    private String goodsPrice;
    
    public String getGoodsSkuAttr() {
        return goodsSkuAttr;
    }

    public void setGoodsSkuAttr(String goodsSkuAttr) {
        this.goodsSkuAttr = goodsSkuAttr;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
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
        // 对订单状态进行翻译 ，前段展示统一显示中文
        String content = "";
        GoodsType[] goodsTypeArray = GoodsType.values();
        for (GoodsType goodsTypeEnum : goodsTypeArray) {
            if (goodsTypeEnum.getCode().equals(goodsTypeDesc)) {
                content = goodsTypeEnum.getMessage();
            }
        }
        this.goodsTypeDesc = content;
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

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

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

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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
        // 对订单状态进行翻译 ，前段展示统一显示中文
        String content = "";
        PaymentType[] paymentTypeArray = PaymentType.values();
        for (PaymentType paymentType : paymentTypeArray) {
            if(payType.equals(paymentType.getCode())){
                content = paymentType.getMessage();
            }
        }
        
        this.payType = content;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusDsc() {
        return orderStatusDsc;
    }

    public void setOrderStatusDsc(String orderStatusDsc) {
        // 对订单状态进行翻译 ，前段展示统一显示中文
        String content = "";
        OrderStatus[] orderStatusArray = OrderStatus.values();
        for (OrderStatus orderStatus : orderStatusArray) {
            if (orderStatus.getCode().equals(orderStatusDsc)) {
                content = orderStatus.getMessage();
            }
        }
        this.orderStatusDsc = content;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
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

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
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

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = DateFormatUtil.datetime2String(createDate);
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = DateFormatUtil.datetime2String(updateDate);
    }

	public String getPreDelivery() {
		return preDelivery;
	}

	public void setPreDelivery(String preDelivery) {
		this.preDelivery = preDelivery;
	}

	public String getPreDeliveryMsg() {
		return preDeliveryMsg;
	}

	public void setPreDeliveryMsg(String preDeliveryMsg) {
		 // 对订单状态进行翻译 ，前段展示统一显示中文
        String content = "";
        PreDeliveryType[] preDeliveryArray = PreDeliveryType.values();
        for (PreDeliveryType preDeliveryEnum : preDeliveryArray) {
            if (preDeliveryEnum.getCode().equals(preDeliveryMsg)) {
                content = preDeliveryEnum.getMessage();
            }
        }
        this.preDeliveryMsg = content;
	}
}
