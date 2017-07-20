package com.apass.esp.domain.entity.refund;

import java.math.BigDecimal;

import com.apass.gfb.framework.annotation.MyBatisEntity;

/**
 * 退货详情表
 * @description 
 *
 * @author liuchao01
 * @version $Id: RefundDetailInfoEntity.java, v 0.1 2016年12月26日 下午1:33:57 liuchao01 Exp ${0xD}
 */
@MyBatisEntity
public class RefundDetailInfoEntity {

    private Long id;
    
    /** 子订单id **/
    private String orderId;
    
    /** 订单详情id **/
    private Long orderDetailId;
    
    /** 商品价格 **/
    private BigDecimal goodsPrice;
    
    /** 商品数量 **/
    private Long goodsNum;
    
    /** 标记 **/
    private String remark;
    
    /**
     * 来源
     */
    private String source;
    /**
     * 售后状态
     */
    private String status;
    
    /**
     * 商品id
     */
    private Long goodsId;

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

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

    
}
