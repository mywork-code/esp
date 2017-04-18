package com.apass.esp.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.apass.esp.service.goods.GoodsService;

@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class GoodsScheduleTask {

	private static final Logger logger = LoggerFactory.getLogger(GoodsScheduleTask.class);

	@Autowired
	private GoodsService goodsService;

	/**
	 * 每天凌晨一点执行，校验商品下架时间
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void validateGoodsDelistime() {
		try {
			logger.info("执行校验商品下架时间定时任务开始");
			goodsService.updateGoodsStatusByDelisttime();
			logger.info("执行校验商品下架时间定时任务结束");
		} catch (Exception e) {
			logger.error("执行校验商品下架时间异常", e);
		}
	}
}
