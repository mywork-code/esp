package com.apass.esp.mapper;

import org.apache.ibatis.annotations.Param;

import com.apass.esp.domain.entity.CashRefund;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface CashRefundMapper extends GenericMapper<CashRefund, Long>{

	/**
	 * 根据订单号，查询该订单申请退款被拒绝的记录
	 * @param orderId
	 * @return
	 */

	CashRefund getCashRefundByOrderId(@Param("orderId") String orderId);
}
