package com.apass.esp.mapper;

import java.util.List;

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
}
