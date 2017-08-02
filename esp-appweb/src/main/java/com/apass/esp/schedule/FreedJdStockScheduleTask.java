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

/**
 * 释放京东商品库存
 * 
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class FreedJdStockScheduleTask {

    private static final Logger logger = LoggerFactory.getLogger(FreedJdStockScheduleTask.class);
    
    @Autowired
    private OrderService  orderService;
    
    /**
     * 取消京东预占库存
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void freedJdStock(){
        try {
            orderService.freedJdStock();
        } catch (Exception e) {
        	logger.error("cancle jd order stock is failture! ", e);
        }
        
    }

}
