package com.apass.esp.schedule;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.apass.esp.domain.dto.activity.AwardDetailDto;
import com.apass.esp.domain.vo.AwardActivityInfoVo;
import com.apass.esp.service.activity.AwardActivityInfoService;
import com.apass.esp.service.activity.AwardDetailService;

@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class RebateActivityScheduleTask {
	private static final Logger LOGGER = LoggerFactory.getLogger(RebateActivityScheduleTask.class);

	@Autowired
	public AwardDetailService awardDetailService;

	@Autowired
	public AwardActivityInfoService awardActivityInfoService;

	/**
	 * 每天凌晨一点执行,邀请人获得返点结算
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void validateActivityEndtime() {
		try {
			LOGGER.info("邀请人获得返点结算定时任务开始");
			List<AwardActivityInfoVo> list = awardActivityInfoService.listActivity();
			// 没有有效的活动
			if (CollectionUtils.isEmpty(list)) {
				return;
			}
			
			Date date = new Date();
			AwardActivityInfoVo  awardActivityInfoVo = list.get(0);
			
			//

			//AwardDetailDto awardDetailDto = new AwardDetailDto();
			//awardDetailService.addAwardDetail(awardDetailDto);
			LOGGER.info("邀请人获得返点结算定时任务结束");
		} catch (Exception e) {
			LOGGER.error("邀请人获得返点结算", e);
		}
	}
}
