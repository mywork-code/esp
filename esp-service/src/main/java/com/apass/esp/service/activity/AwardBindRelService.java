package com.apass.esp.service.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.AwardBindRel;
import com.apass.esp.mapper.AwardBindRelMapper;
@Service
public class AwardBindRelService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AwardBindRelService.class);

	@Autowired
	public AwardBindRelMapper WihdrawBindRelMapper;
	
	public int insertAwardBindRel(AwardBindRel awardBindRel){
		return WihdrawBindRelMapper.insert(awardBindRel);
	}
}
