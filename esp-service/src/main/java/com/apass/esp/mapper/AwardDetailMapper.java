package com.apass.esp.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.apass.esp.domain.entity.AwardDetail;
import com.apass.esp.domain.query.ActivityBindRelStatisticQuery;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface AwardDetailMapper extends GenericMapper<AwardDetail, Long> {


        List<AwardDetail> queryAwardDetail(@Param("userId") Long userId);

        List<AwardDetail> queryAwardDetailWithDate(@Param("userId")Long userId,
            @Param("beginMonthDay")String beginMonthDay, @Param("now")String now);


	List<AwardDetail> queryAwardDetailByStatusAndType(@Param("status") byte status, @Param("type") byte type);
	
	/**
	 * 统计在某一时间段内，已放款和预计放款总金额
	 * @param query
	 * @return
	 */
	List<AwardDetail> querySumAmountGroupByTypeStatus(ActivityBindRelStatisticQuery query);

	/**
	 * 查询有提现纪录的所有用户
	 * @param paramMap
	 * @return
	 */
	List<AwardDetail> queryAwardIntroList(Map<String, Object> paramMap);

	/**
	 * 查询有提现纪录的所有用户总数
	 * @param paramMap
	 * @return
	 */
	Integer countAwardIntroList(Map<String, Object> paramMap);

	BigDecimal getAllAwardByUserId(Long userId);
}
