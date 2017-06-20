package com.apass.esp.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.apass.esp.service.order.OrderService;

@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class OrderModifyStatusScheduleTask{

	private static final Logger logger = LoggerFactory.getLogger(OrderModifyStatusScheduleTask.class);
	
	@Autowired
    private OrderService orderService;
	
	@Scheduled(cron = "59 59 23 * * ?")
	public void updateOrderStatusAndPreDelivery(){
        try {
        	orderService.updateOrderStatusAndPreDelivery();
        } catch (Exception e) {
        	logger.error("修改订单状态任务出错",e);
        }
	}

}
