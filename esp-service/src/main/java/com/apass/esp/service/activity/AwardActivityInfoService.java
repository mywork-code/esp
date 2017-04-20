package com.apass.esp.service.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.AwardActivityInfo;
import com.apass.esp.mapper.AwardActivityInfoMapper;
import com.apass.esp.mapper.AwardBindRelMapper;
import com.apass.esp.mapper.AwardDetailMapper;

@Service
public class AwardActivityInfoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AwardActivityInfoService.class);
	@Autowired
	public AwardBindRelMapper WihdrawBindRelMapper;

	@Autowired
	public AwardDetailMapper awardDetailMapper;

	@Autowired
	public AwardActivityInfoMapper awardActivityInfoMapper;

	public AwardActivityInfo addActivity(AwardActivityInfo record){
		awardActivityInfoMapper.insert(record);
		return record;
	}
	
}
