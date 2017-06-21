package com.apass.esp.domain.dto.statement;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.apass.esp.domain.enums.PaymentType;
import com.apass.gfb.framework.annotation.MyBatisEntity;
import com.apass.gfb.framework.utils.DateFormatUtil;

/**
 * @description 报表信息查询dto
 *
 * @author dell
 * @version $Id: StatementDto.java, v 0.1 2017年2月22日 下午4:01:00 dell Exp $
 */
/**
 * @description 
 *
 * @author dell
 * @version $Id: StatementDto.java, v 0.1 2017年2月28日 下午8:01:54 dell Exp $
 */
/**
 * @description 
 *
 * @author dell
 * @version $Id: StatementDto.java, v 0.1 2017年3月1日 下午6:03:48 dell Exp $
 */
@MyBatisEntity
public class StatementDto {
    /**订单生成时间**/
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date   orderCreateDate;

    /** 订单编号   **/
    private String orderId;
    /**商品供应商**/
    private String merchantName;
    /**商品名称**/
    private String goodsName;
    /**商品型号**/
    private String goodsModel;
    /**商品价格**/
    private String goodsPrice;
    /**商品市场价格**/
    private String marketPrice;
    
    public String getMarketPrice() {
        return marketPrice;
    }
    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**商品成本价**/
    private String goodsCostPrice;
    /**商品购买数量**/
    private String goodsNum;
    /**商品库存数量**/
    private String stockCurrAmt;
    /**客户姓名**/
    private String customerName;
    /**客户电话**/
    private String customerTelephone;
    /**领取地址**/
    private String address;
    /**付款时间**/
    private String payTime;
    

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = DateFormatUtil.datetime2String(payTime);
    }

    /**付款方式**/
    private String payType;
    /**发货时间**/
    private String sendOutgoodsdate;
    /**快递单号**/
    private String logisticsNo;
    /**物流厂商编码：快递厂商**/
    private String logisticsName;
    /**退货或换货**/
    private String refundType;
    /**退换货发起时间**/
    private String refundCreateDate;
    /**批复结果**/
    private String isAgree;
    /**批复人**/
    private String approvalUser;
    /**客户退货快递单号**/
    private String sLogisticsNo;
    /**供应商换货快递单号**/
    private String rLogisticsNo;
    /**备注**/
    private String remark;
    /**签收时间**/
    private Date   signTime;
    /**结算时间**/
    private Date   settlementTime;
    /**售后时间**/
    private Date   completionTime;
    /**
     * 退/换货商品数量
     */
    private Long goodsNumR;
    
    public Long getGoodsNumR() {
		return goodsNumR;
	}
	public void setGoodsNumR(String refundType,Long goodsNumR) {
		this.goodsNumR = goodsNumR;
		if(!StringUtils.isBlank(refundType)){
			if(refundType.equals("退货")){
				this.returnGoodNum = goodsNumR;
			}else if(refundType.equals("换货")){
				this.swapGoodNum = goodsNumR;
			}
		}
	}

	/**
     * 退货数量
     */
    private Long returnGoodNum;
    
    /**
     * 换货数量
     */
    private Long swapGoodNum;
    
    /**
     * 退款金额
     */
    private BigDecimal refundAmt;
    
	public Long getReturnGoodNum() {
		return returnGoodNum;
	}
	public void setReturnGoodNum(Long returnGoodNum) {
		this.returnGoodNum = returnGoodNum;
	}
	public Long getSwapGoodNum() {
		return swapGoodNum;
	}
	public void setSwapGoodNum(Long swapGoodNum) {
		this.swapGoodNum = swapGoodNum;
	}
	public BigDecimal getRefundAmt() {
		return refundAmt;
	}
	public void setRefundAmt(BigDecimal refundAmt) {
		this.refundAmt = refundAmt;
	}
	public String getCompletionTime() {
        return DateFormatUtil.dateToString(completionTime, DateFormatUtil.YYYY_MM_DD);
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

    public String getSignTime() {
        return DateFormatUtil.dateToString(signTime, DateFormatUtil.YYYY_MM_DD);
    }

    public void set (Date signTime) {
        this.signTime = signTime;
    }

    public String getSettlementTime() {
        return DateFormatUtil.dateToString(settlementTime, DateFormatUtil.YYYY_MM_DD);
    }

    public void setSettlementTime(Date settlementTime) {
        this.settlementTime = settlementTime;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getOrderCreateDate() {
        return DateFormatUtil.dateToString(orderCreateDate, DateFormatUtil.YYYY_MM_DD_HH_MM_SS);
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

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsCostPrice() {
        return goodsCostPrice;
    }

    public void setGoodsCostPrice(String goodsCostPrice) {
        this.goodsCostPrice = goodsCostPrice;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getStockCurrAmt() {
        return stockCurrAmt;
    }

    public void setStockCurrAmt(String stockCurrAmt) {
        this.stockCurrAmt = stockCurrAmt;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTelephone() {
        return customerTelephone;
    }

    public void setCustomerTelephone(String customerTelephone) {
        this.customerTelephone = customerTelephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        String content = "";
        PaymentType[] payTypeArr = PaymentType.values();
        for (PaymentType paymentType : payTypeArr) {
            if(payType.equals(paymentType.getCode())){
                content = paymentType.getMessage();
            }
        }
        
        this.payType = content;
    }

    public String getSendOutgoodsdate() {
        return sendOutgoodsdate;
    }

    public void setSendOutgoodsdate(Date sendOutgoodsdate) {
        this.sendOutgoodsdate = DateFormatUtil.dateToString(sendOutgoodsdate, DateFormatUtil.YYYY_MM_DD_HH_MM_SS);
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        String content = "";
        if(refundType.equals("0")){
            content = "退货";
        }else if(refundType.equals("1")){
            content = "换货";
        }
        this.refundType = content;
    }

    public String getRefundCreateDate() {
        return refundCreateDate;
    }

    public void setRefundCreateDate(Date refundCreateDate) {
        this.refundCreateDate = DateFormatUtil.dateToString(refundCreateDate, DateFormatUtil.YYYY_MM_DD_HH_MM_SS);
    }

    public String getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(String isAgree) {
        String content = "";
        if(isAgree.equals("0")){
            content = "拒绝";
        }else if(isAgree.equals("1")){
            content = "同意";
        }
        this.isAgree = content;
    }

    public String getApprovalUser() {
        return approvalUser;
    }

    public void setApprovalUser(String approvalUser) {
        this.approvalUser = approvalUser;
    }

    public String getsLogisticsNo() {
        return sLogisticsNo;
    }

    public void setsLogisticsNo(String sLogisticsNo) {
        this.sLogisticsNo = sLogisticsNo;
    }
    
    public String getrLogisticsNo() {
        return rLogisticsNo;
    }

    public void setrLogisticsNo(String rLogisticsNo) {
        this.rLogisticsNo = rLogisticsNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
