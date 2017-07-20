package com.apass.esp.mapper;

import com.apass.esp.domain.entity.CashRefund;
import com.apass.gfb.framework.mybatis.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CashRefundMapper extends GenericMapper<CashRefund, Long>{

	/**
	 * 根据订单号，查询该订单申请退款被拒绝的记录
	 * @param orderId
	 * @return
	 */

	CashRefund getCashRefundByOrderId(@Param("orderId") String orderId);
	
	List<CashRefund> getCashRefundByMainOrderId(@Param("mainOrderId") String mainOrderId);
	
    Integer updateByOrderIdSelective(CashRefund crfd);

	List<CashRefund> queryCashRefundByStatus(@Param("status")Integer status);


	List<CashRefund> queryByMainOrderIdAndStatus(@Param("mainOrderId") String mainOrderId,
																							 @Param("status")Integer status);
}
