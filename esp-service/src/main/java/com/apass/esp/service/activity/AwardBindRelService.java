package com.apass.esp.service.activity;

import com.apass.esp.domain.entity.AwardBindRel;
import com.apass.esp.domain.extentity.AwardBindRelStatistic;
import com.apass.esp.domain.vo.AwardBindRelStatisticVo;
import com.apass.esp.mapper.AwardBindRelMapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AwardBindRelService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AwardBindRelService.class);

	@Autowired
	public AwardBindRelMapper wihdrawBindRelMapper;
	
	public int insertAwardBindRel(AwardBindRel awardBindRel){
		return wihdrawBindRelMapper.insert(awardBindRel);
	}
	public Integer selectCountByInviteMobile(String moblie){
		return wihdrawBindRelMapper.selectCountByInviteMobile(moblie);
	}

	List<AwardBindRelStatisticVo> pageBindRelStatistic() {
		List<AwardBindRelStatistic> list = wihdrawBindRelMapper.selectBindRelStatistic();
		if(CollectionUtils.isEmpty(list)){
			return Collections.emptyList();
		}
		List<AwardBindRelStatisticVo> result = new ArrayList<>();
		for(AwardBindRelStatistic rs : list){
			AwardBindRelStatisticVo vo = new AwardBindRelStatisticVo();
			vo.setMobile(rs.getMobile());
			vo.setInviteNum(rs.getTotalInviteNum());
			//查询返现金额
		}

		return null;
	}

}
