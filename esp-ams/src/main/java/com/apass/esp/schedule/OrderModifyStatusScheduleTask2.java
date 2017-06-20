package com.apass.esp.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.apass.esp.service.order.OrderService;

@Component
@Configurable
@EnableScheduling
//@Profile("Schedule")
public class OrderModifyStatusScheduleTask2 implements SchedulingConfigurer{

	private static final Logger logger = LoggerFactory.getLogger(OrderModifyStatusScheduleTask2.class);
	
	private static final String DEFAULT_CRON = "0 0 17 * * ?";
	
	private String cron = DEFAULT_CRON;
	
	@Autowired
    private OrderService orderService;
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				try {
					orderService.updateOrderStatusAndPreDelivery();
				} catch (Exception e) {
					logger.error("修改订单状态任务出错",e);
				}
				
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				// 定时任务触发，可修改定时任务的执行周期
			    CronTrigger trigger = new CronTrigger(cron);
			    Date nextExecDate = trigger.nextExecutionTime(triggerContext);
			    return nextExecDate;
			}
		});
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}
}
