package com.apass.esp.mapper;

import com.apass.esp.domain.entity.RepayFlow;
import com.apass.gfb.framework.mybatis.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RepayFlowMapper extends GenericMapper<RepayFlow, Long>{
	
	RepayFlow queryLatestSuccessOrderInfo(Long userId);

	/**
	 * 查询 【未出账+已出账】成功还款的流水
	 * @param dateBegin
	 * @param endDate
	 * @return
	 */
	List<RepayFlow> querySuccessByDate(@Param("dateBegin") String dateBegin,
																		 @Param("endDate") String endDate);
	
}
