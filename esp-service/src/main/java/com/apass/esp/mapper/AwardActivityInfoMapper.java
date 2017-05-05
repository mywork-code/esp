package com.apass.esp.mapper;

import com.apass.esp.common.model.QueryParams;
import com.apass.esp.domain.dto.activity.AwardActivityInfoDto;
import com.apass.esp.domain.entity.AwardActivityInfo;
import com.apass.gfb.framework.mybatis.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AwardActivityInfoMapper extends GenericMapper<AwardActivityInfo, Long> {
    List<AwardActivityInfo> selectLastEffectiveActivities();

    AwardActivityInfo selectByName(@Param("name") String name);

    Integer updateActivityByPK(AwardActivityInfoDto dto);

    List<AwardActivityInfo> pageEffectiveList(QueryParams query);

    Integer count();

}
