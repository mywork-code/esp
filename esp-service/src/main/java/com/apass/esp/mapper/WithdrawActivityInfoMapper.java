package com.apass.esp.mapper;

import org.springframework.stereotype.Repository;

import com.apass.esp.domain.entity.activity.WithdrawActivityInfo;
import com.apass.gfb.framework.mybatis.GenericMapper;

@Repository
public interface WithdrawActivityInfoMapper extends GenericMapper<WithdrawActivityInfo, Long> {
}