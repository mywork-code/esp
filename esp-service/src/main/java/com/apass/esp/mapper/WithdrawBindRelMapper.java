package com.apass.esp.mapper;

import org.springframework.stereotype.Repository;

import com.apass.esp.domain.entity.activity.WithdrawBindRel;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.GenericMapper;

@MyBatisRepository
public interface WithdrawBindRelMapper extends GenericMapper<WithdrawBindRel, Long> {
}