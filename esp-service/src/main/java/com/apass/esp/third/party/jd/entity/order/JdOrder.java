package com.apass.esp.third.party.jd.entity.order;

import org.apache.commons.lang3.StringUtils;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class JdOrder implements Serializable {

	private long jdOrderId;

    private String orderNo;

    private BigDecimal freight;

    private BigDecimal orderPrice;
    
    private BigDecimal jdPrice;
    private BigDecimal showPrice;//页面显示价格（jd价格加邮费）
   
    
    private int showNum;//页面显示数量
    
    private BigDecimal orderNakedPrice;
    private BigDecimal orderTaxPrice;
    private String skuIds;
    private int orderState = -1;
    private int submitState;
    private int state;
    private int showState;//用于页面显示的订单状态
    private int type;
    private long pOrderId = 0;
    private String cOrderIds = "";
    

    private transient List<Long> cOrderIdList;
    
    private List<OrderSku> orderSkus;
    

	private List<OrderTrack> orderTracks;
    
    public List<Long> getcOrderIdList() {
        if (cOrderIdList == null) {
            if (StringUtils.isNotEmpty(cOrderIds)) {
                cOrderIdList = new ArrayList<>();
                for (String c : cOrderIds.split(",")) {
                    cOrderIdList.add(Long.parseLong(c));
                }
            } else {
                cOrderIdList = Collections.EMPTY_LIST;
            }
        }
        return cOrderIdList;
    }

    public long getJdOrderId() {
        return jdOrderId;
    }

    public void setJdOrderId(long jdOrderId) {
        this.jdOrderId = jdOrderId;
    }

	public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }
    
    public BigDecimal getShowPrice() {
		return showPrice;
	}
     
	public void setShowPrice(BigDecimal showPrice) {
		this.showPrice = showPrice;
	}
	
	public int getShowNum() {
		return showNum;
	}

	public void setShowNum(int showNum) {
		this.showNum = showNum;
	}

	public BigDecimal getJdPrice() {
		return jdPrice;
	}

	public void setJdPrice(BigDecimal jdPrice) {
		this.jdPrice = jdPrice;
	}

	public BigDecimal getOrderNakedPrice() {
        return orderNakedPrice;
    }

    public void setOrderNakedPrice(BigDecimal orderNakedPrice) {
        this.orderNakedPrice = orderNakedPrice;
    }

    public BigDecimal getOrderTaxPrice() {
        return orderTaxPrice;
    }

    public void setOrderTaxPrice(BigDecimal orderTaxPrice) {
        this.orderTaxPrice = orderTaxPrice;
    }


    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public int getSubmitState() {
        return submitState;
    }

    public void setSubmitState(int submitState) {
        this.submitState = submitState;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getpOrderId() {
        return pOrderId;
    }

    public void setpOrderId(long pOrderId) {
        this.pOrderId = pOrderId;
    }

    public String getcOrderIds() {
        return cOrderIds;
    }

    public void setcOrderIds(String cOrderIds) {
        this.cOrderIds = cOrderIds;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSkuIds() {
        return skuIds;
    }

    public void setSkuIds(String skuIds) {
        this.skuIds = skuIds;
    }

	public List<OrderSku> getOrderSkus() {
		return orderSkus;
	}

	public void setOrderSkus(List<OrderSku> orderSkus) {
		this.orderSkus = orderSkus;
	}

	public List<OrderTrack> getOrderTracks() {
		return orderTracks;
	}

	public void setOrderTracks(List<OrderTrack> orderTracks) {
		this.orderTracks = orderTracks;
	}

	public int getShowState() {
		return showState;
	}

	public void setShowState(int showState) {
		this.showState = showState;
	}
	
}
