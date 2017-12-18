package com.apass.esp.schedule;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.apass.esp.service.activity.ActivityInfoService;
import com.apass.esp.service.activity.LimitBuyActService;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.DateFormatUtil;
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
/**
 * 活动定时任务
 * @author Administrator
 *
 */
public class ActivityScheduleTask {
	private static final Logger logger = LoggerFactory.getLogger(ActivityScheduleTask.class);
	@Autowired
	private ActivityInfoService activityInfoService;
	@Autowired
    private LimitBuyActService limitBuyActService;
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
	/**
	 * 每天 10点 14点 18点 22点各执行一次
	 * 限时购活动自动开始和自动结束
	 */
	@Scheduled(cron = "0 0 10,14,18,22 * * ?")
    public void limitbuyActStartOver() {
	    try{
	        String now = DateFormatUtil.datetime2String(new Date());
	        String sb = limitBuyActService.limitbuyActStartOver();
	        logger.info("限时购活动状态自动更新成功,当前时间"+now+",任务执行详情 {}  "+sb);
	    } catch (Exception e) {
            logger.error("限时购活动状态自动更新异常", e);
        }
	}
	/**
	 * 限时购活动开始前五分钟 每个商品校验  发送短信提醒
	 */
	@Scheduled(cron = "0 55 9,13,17,21 * * ?")
	public void limitbuyGoodsRemind() {
	    try{
    	    String now = DateFormatUtil.datetime2String(new Date());
            String sb = limitBuyActService.limitbuyGoodsRemind();
            logger.info("限时购活动即将开始，发送短信提醒成功,当前时间"+now+",任务执行详情 {}  "+sb);
        } catch (Exception e) {
            logger.error("限时购活动即将开始，发送短信提醒异常", e);
        }
	}
}