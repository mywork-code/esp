package com.apass.esp.service.activity;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.AwardBindRel;
import com.apass.esp.domain.query.ActivityBindRelStatisticQuery;
import com.apass.esp.mapper.AwardBindRelMapper;
import com.apass.gfb.framework.utils.DateFormatUtil;

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

	public Integer selectByMobileAndActivityId(AwardBindRel abr){
		return wihdrawBindRelMapper.selectByMobileAndActivityId(abr);
	}
	
	public Integer selectByMobile(AwardBindRel abr){
		return wihdrawBindRelMapper.selectByMobile(abr);
	}

	public AwardBindRel getByInviterUserId(String userId,int activityId){
		return wihdrawBindRelMapper.getByInviterUserId(userId,activityId);

	}
	
	public List<AwardBindRel> getAllByInviterUserId(String userId){
		return wihdrawBindRelMapper.getAllByInviterUserId(userId);
	}
	/**
	  * 统计查询在某一时间内邀请的总人数
	  */
	public Integer getInviterUserCountByTime(int days){
		ActivityBindRelStatisticQuery query = new ActivityBindRelStatisticQuery();
		//在当前时间的基础上减去days
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, days);
		query.setStartCreateDate(DateFormatUtil.dateToString(cal.getTime(),""));
		return wihdrawBindRelMapper.getInviterUserCountByTime(query);
	}
}
