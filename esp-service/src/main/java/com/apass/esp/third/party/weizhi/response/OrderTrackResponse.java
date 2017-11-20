package com.apass.esp.third.party.weizhi.response;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author pyc
 * @email  E-mail: pengyingchao@apass.cn
 * @version V2.0
 * @createTime：2017年11月17日 下午6:32:15 
 * @description
 */
public class OrderTrackResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String wzOrderId; 

	private List<OrderTrack> orderTrack;

	public String getWzOrderId() {
		return wzOrderId;
	}

	public void setWzOrderId(String wzOrderId) {
		this.wzOrderId = wzOrderId;
	}

	public List<OrderTrack> getOrderTrack() {
		return orderTrack;
	}

	public void setOrderTrack(List<OrderTrack> orderTrack) {
		this.orderTrack = orderTrack;
	}

	public OrderTrackResponse() {
		super();
	}

	public OrderTrackResponse(String wzOrderId, List<OrderTrack> orderTrack) {
		super();
		this.wzOrderId = wzOrderId;
		this.orderTrack = orderTrack;
	}
	
}
