package com.apass.esp.mapper;

import com.apass.esp.domain.entity.AwardActivityInfo;
import com.apass.gfb.framework.mybatis.GenericMapper;

import java.util.List;

public interface AwardActivityInfoMapper extends GenericMapper<AwardActivityInfo, Long> {
  List<AwardActivityInfo> selectLastEffectiveActivities();

}
