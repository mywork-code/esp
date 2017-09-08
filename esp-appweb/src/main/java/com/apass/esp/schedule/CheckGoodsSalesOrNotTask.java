package com.apass.esp.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.apass.esp.service.check.CheckGoodsSalesService;
import com.apass.esp.service.goods.GoodsService;

@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class CheckGoodsSalesOrNotTask {
	private static final Logger logger = LoggerFactory.getLogger(CheckGoodsSalesOrNotTask.class);
	@Autowired
	private CheckGoodsSalesService checkGoodsSalesService;

	/**
	 * 每天凌晨一点执行，校验商品是否可售
	 */
//	@Scheduled(cron = "0 0 1 * * ?")
	public void checkGoodsSales() {
		try {
			logger.info("执行校验商品是否可售定时任务开始");
			checkGoodsSalesService.checkGoodsSales();
			logger.info("执行校验商品是否可售定时任务结束");
		} catch (Exception e) {
			logger.error("执行校验商品是否可售异常", e);
		}
	}
}
