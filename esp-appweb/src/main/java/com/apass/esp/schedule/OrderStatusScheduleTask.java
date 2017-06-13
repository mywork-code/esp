package com.apass.esp.schedule;

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

import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.enums.PreDeliveryType;
import com.apass.esp.service.order.OrderService;

@Component  
@Configurable  
@EnableScheduling
@Profile("Schedule")
public class OrderStatusScheduleTask {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderStatusScheduleTask.class);
	
	@Autowired
    private OrderService orderService;
    
    @Scheduled(cron="0 0 10,17 * * ?")
    public void updateOrderStatusAndPreDelivery(){
        try {
        	/**
             * 1.查询订单的状态为待发货的状态
             * 2.循环遍历所有代发货的订单，然后更新订单的状态和预发货状态
             */
        	List<OrderInfoEntity> orderList = orderService.toBeDeliver();
        	if(!CollectionUtils.isEmpty(orderList)){
        		for (OrderInfoEntity order : orderList) {
        			order.setPreDelivery(PreDeliveryType.PRE_DELIVERY_Y.getCode());
        			order.setStatus(OrderStatus.ORDER_SEND.getCode());
        			orderService.updateOrderStatusAndPreDelivery(order);
				}
        	}
        } catch (Exception e) {
            logger.error("订单状态修改异常", e);
        }
    }
}
