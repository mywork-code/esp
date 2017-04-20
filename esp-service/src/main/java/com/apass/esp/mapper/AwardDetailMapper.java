package com.apass.esp.mapper;

import org.springframework.stereotype.Repository;

import com.apass.esp.domain.entity.activity.AwardDetail;
import com.apass.gfb.framework.mybatis.GenericMapper;

@Repository
public interface AwardDetailMapper extends GenericMapper<AwardDetail, Long> {
}