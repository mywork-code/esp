package com.apass.esp.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.apass.esp.domain.entity.AwardDetail;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface AwardDetailMapper extends GenericMapper<AwardDetail, Long> {

    List<AwardDetail> queryAwardDetail(@Param("userId") Long userId);

    List<AwardDetail> queryAwardDetailWithDate(@Param("userId")Long userId,
            @Param("beginMonthDay")String beginMonthDay, @Param("now")String now);
}
