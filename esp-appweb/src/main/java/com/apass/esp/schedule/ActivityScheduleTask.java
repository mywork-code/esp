package com.apass.esp.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.apass.esp.service.activity.ActivityInfoService;

@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class ActivityScheduleTask {

	private static final Logger logger = LoggerFactory.getLogger(ActivityScheduleTask.class);

	@Autowired
	private ActivityInfoService activityInfoService;

	/**
	 * 每天凌晨一点执行，校验活动过期时间
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void validateActivityEndtime() {
		try {
			 logger.info("执行校验商品活动过期时间定时任务开始");
			activityInfoService.updateActivityStatusByEndtime();
			logger.info("执行校验商品活动过期时间时间定时任务结束");
		} catch (Exception e) {
			logger.error("执行校验商品活动过期时间异常", e);
		}
	}
}
