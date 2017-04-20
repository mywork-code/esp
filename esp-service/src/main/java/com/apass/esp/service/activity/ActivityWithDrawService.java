package com.apass.esp.service.activity;

import com.apass.esp.domain.entity.WithdrawActivityInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.mapper.AwardDetailMapper;
import com.apass.esp.mapper.WithdrawActivityInfoMapper;
import com.apass.esp.mapper.WithdrawBindRelMapper;

@Service
public class ActivityWithDrawService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityWithDrawService.class);
	@Autowired
	public WithdrawBindRelMapper WihdrawBindRelMapper;

	@Autowired
	public AwardDetailMapper awardDetailMapper;

	@Autowired
	public WithdrawActivityInfoMapper withdrawActivityInfoMapper;

	public WithdrawActivityInfo addActivity(WithdrawActivityInfo record){
		withdrawActivityInfoMapper.insert(record);
		return record;
	}
	
}
