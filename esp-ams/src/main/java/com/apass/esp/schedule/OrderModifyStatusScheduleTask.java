package com.apass.esp.schedule;

import com.apass.esp.domain.entity.Kvattr;
import com.apass.esp.domain.kvattr.ShipmentTimeConfigAttr;
import com.apass.esp.service.common.KvattrService;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.utils.CronTools;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class OrderModifyStatusScheduleTask{

	private static final Logger logger = LoggerFactory.getLogger(OrderModifyStatusScheduleTask.class);
	
	@Autowired
    private OrderService orderService;
	
	@Autowired
    private KvattrService kvattrService;
	
	@PostConstruct
	public void init() {
		threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(10);
		threadPoolTaskScheduler.initialize();

		List<Kvattr> attrs = kvattrService.getTypeName(new ShipmentTimeConfigAttr());
		if(!CollectionUtils.isEmpty(attrs)){
			for (Kvattr kvattr : attrs) {
				if(StringUtils.equals(kvattr.getKey(), "time1")){
					startCron1(CronTools.getCron(kvattr.getValue()));
				}
				if(StringUtils.equals(kvattr.getKey(), "time2")){
					startCron2(CronTools.getCron(kvattr.getValue()));
				}
				if(StringUtils.equals(kvattr.getKey(), "time3")){
					startCron3(CronTools.getCron(kvattr.getValue()));
				}
			}
		}
	}


	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
	private ScheduledFuture<?> future1;
	private ScheduledFuture<?> future2;
	private ScheduledFuture<?> future3;
	
	@Scheduled(cron = "59 59 23 * * ?")
	public void updateOrderStatusAndPreDelivery(){
        try {
					logger.info("【OrderModifyStatusScheduleTask】--------> cron:{59 59 23 * * ?} execute:" + new Date());
        	orderService.updateOrderStatusAndPreDelivery();
        } catch (Exception e) {
        	logger.error("修改订单状态任务出错",e);
        }
	}

	/**
	 * task1
	 * @param cron
	 * @return
	 */
	public void startCron1(String cron){
		stopCron1();
		future1 = threadPoolTaskScheduler.schedule(new Runnable() {
			public void run() {
				try {
					logger.info("【OrderModifyStatusScheduleTask】--------> cron1:{"+cron+"} execute:" + new Date());
					orderService.updateOrderStatusAndPreDelivery();
				} catch (Exception e) {
					logger.error("修改订单状态任务出错",e);
				}
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				 return new CronTrigger(cron).nextExecutionTime(triggerContext);
			}
		});
	}
	
	/**
	 * task2
	 * @param cron
	 * @return
	 */
	public void startCron2(String cron){
		stopCron2();
		future2 = threadPoolTaskScheduler.schedule(new Runnable() {
			public void run() {
				try {
					logger.info("【OrderModifyStatusScheduleTask】--------> cron2:{"+cron+"} execute:" + new Date());
					orderService.updateOrderStatusAndPreDelivery();
				} catch (Exception e) {
					logger.error("修改订单状态任务出错",e);
				}
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				 return new CronTrigger(cron).nextExecutionTime(triggerContext);
			}
		});
	}
	
	/**
	 * task3
	 * @param cron
	 * @return
	 */
	public void startCron3(String cron){
		stopCron3();
		future3 = threadPoolTaskScheduler.schedule(new Runnable() {
			public void run() {
				try {
					logger.info("【OrderModifyStatusScheduleTask】--------> cron3:{"+cron+"} execute:" + new Date());
					orderService.updateOrderStatusAndPreDelivery();
				} catch (Exception e) {
					logger.error("修改订单状态任务出错",e);
				}
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				 return new CronTrigger(cron).nextExecutionTime(triggerContext);
			}
		});
	}
	
	public void stopCron1(){
	   if(future1 != null) {
           future1.cancel(true);
       }
	}
	public void stopCron2(){
	   if(future2 != null) {
		   future2.cancel(true);
       }
	}
	public void stopCron3(){
	   if(future3 != null) {
		   future3.cancel(true);
       }
	}
}
