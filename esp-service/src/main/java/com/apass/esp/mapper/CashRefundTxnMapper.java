package com.apass.esp.mapper;

import com.apass.esp.domain.entity.CashRefundTxn;
import com.apass.gfb.framework.mybatis.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CashRefundTxnMapper extends GenericMapper<CashRefundTxn, Long>{

    int updateByTxnTypeAndCashRefundId(CashRefundTxn cashRefundTxn);


	/**
	 * 根据退款详情id查询退款流水表数据
	 * @param cashRefunId
	 * @return
	 */
	List<CashRefundTxn> queryCashRefundTxnByCashRefundId(@Param("cashRefunId")Long cashRefunId);

	List<CashRefundTxn> queryCashRefundTxnByStatus(@Param("status")String status);

	List<CashRefundTxn> queryByCashRefundIdAndType(@Param("cashRefunId")Long cashRefunId
			                                          ,@Param("typeCode") String typeCode);


	List<CashRefundTxn> queryByStatusAndDate(@Param("status") String status,
																				@Param("dateBegin") String dateBegin,
																				@Param("endDate") String endDate);
}
