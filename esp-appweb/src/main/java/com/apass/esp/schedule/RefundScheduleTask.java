package com.apass.esp.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.apass.esp.service.refund.OrderRefundService;

@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class RefundScheduleTask {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RefundScheduleTask.class);
    
    @Autowired
    private OrderRefundService        orderRefundService;

    /**
     * 换货 商家重新发货物流显示已签收， 3天后标记售后完成
     */
    @Scheduled(cron = "0 0 0/3 * * *")
    public void handleExchRefundInfoStatus(){
        
        try {
            orderRefundService.handleExchRefundInfoStatus();
        } catch (Exception e) {
            LOGGER.error("售后完成订单状态修改异常", e);
        }
    }
}
