package com.apass.esp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.apass.esp.domain.entity.AwardDetail;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface AwardDetailMapper extends GenericMapper<AwardDetail, Long> {

    List<AwardDetail> queryAwardDetail(@Param("userId") Long userId);
}
