package com.apass.esp.service.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.AwardBindRel;
import com.apass.esp.mapper.AwardBindRelMapper;

@Service
public class AwardBindRelService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AwardBindRelService.class);

	@Autowired
	public AwardBindRelMapper wihdrawBindRelMapper;

    @Transactional(rollbackFor=Exception.class) 
	public int insertAwardBindRel(AwardBindRel awardBindRel){
		return wihdrawBindRelMapper.insert(awardBindRel);
	}
	
	public Integer selectCountByInviteMobile(String moblie){
		return wihdrawBindRelMapper.selectCountByInviteMobile(moblie);
	}

	public AwardBindRel getByInviterUserId(String userId,int activityId){
		return wihdrawBindRelMapper.getByInviterUserId(userId,activityId);
	}
}
