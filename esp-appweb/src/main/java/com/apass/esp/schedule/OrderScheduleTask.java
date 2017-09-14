package com.apass.esp.schedule;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.apass.esp.client.jpush.JpushClient;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.AcceptGoodsType;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.service.refund.OrderRefundService;
import com.apass.gfb.framework.utils.DateFormatUtil;

/**
 * 定时任务
 * @description  
 *
 * @author liuming
 * @version $Id: InvalidOrderSchedule.java, v 0.1 2017年1月12日 下午5:55:42 liuming Exp $
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class OrderScheduleTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderScheduleTask.class);
    
    @Autowired
    private OrderInfoRepository orderInfoDao;
    
    @Autowired
    private OrderRefundService        orderRefundService;
    
    @Autowired
    private JpushClient        jpushClient;
    
    @Autowired
    private OrderService orderService;
    
    /**
     * 订单24小时未支付自动取消[五分钟一次]
     */
    @Scheduled(cron = "0 0/5 * * * *")
    public void handleOrderInvalidTask() {

        Date now = new Date();
        List<OrderInfoEntity> orderNoPays = orderInfoDao.loadNoPayList();
        for (OrderInfoEntity order : orderNoPays) {
            try {
                Date createDate = order.getCreateDate();
                Date invalidDate = DateFormatUtil.addDays(createDate, 1);
                //  超过24小时未支付
                if (now.after(invalidDate)) {
                    if (OrderStatus.ORDER_NOPAY.getCode().equals(order.getStatus())) {
                        //处理订单失效
                        orderService.dealWithInvalidOrder("requestId", order);
                        LOGGER.info("自动取消订单成功!orderId:{}", order.getOrderId());
                    }
                }
            } catch (Exception e) {
                LOGGER.error("handleOrderInvalidTask-orderId:{} 订单失效验证失败:{}", order.getOrderId(), e);
                continue;
            }
        }
        LOGGER.info("handleOrderInvalidTask执行时间:{}ms", System.currentTimeMillis() - now.getTime());
    }

    /**
     * 订单签收7天后，自动确认收货[1小时一次 0 0 0/1 * * *]
     */
    @Scheduled(cron = "0 0 0/1 * * *")
    public void handleAutoSignOrder() {
        Date now = new Date();
        List<OrderInfoEntity> orderNoSigns = orderInfoDao.loadNoSignOrders();
        for (OrderInfoEntity order : orderNoSigns) {
            try {
                Date lastAcceptGoodsDate = order.getLastAcceptGoodsDate();
                if (null != lastAcceptGoodsDate && now.after(lastAcceptGoodsDate)) {
                    if (OrderStatus.ORDER_SEND.getCode().equals(order.getStatus())) {
                        order.setStatus(OrderStatus.ORDER_COMPLETED.getCode());
                        order.setAcceptGoodsType(AcceptGoodsType.AUTOCONFIRM.getCode());
                        order.setAcceptGoodsDate(now);
                        orderInfoDao.update(order);
                        LOGGER.info("自动确认收货成功!orderId:{}", order.getOrderId());
                        
                        jpushClient.jpushSendPushAlias(order.getUserId().toString(), "确认收货", "您的订单" + order.getOrderId() + "已自动确认收货");
                    }
                }
            } catch (Exception e) {
                LOGGER.error("handleAutoSignOrder-orderId:{} 订单失效验证失败:{}", order.getOrderId(), e);
                continue;
            }
        }
        LOGGER.info("handleAutoSignOrder执行时间:{}ms", System.currentTimeMillis() - now.getTime());
    }

    /**
     * 售后完成的订单1天后订单状态改为交易完成(sprint8中修改为交易关闭)；每3小时处理一次，订单状态售后服务中、售后流程状态售后完成
     */
    @Scheduled(cron = "0 0 0/3 * * *")
    public void handleReturningOrders(){
        
        try {
            orderRefundService.handleReturningOrders();
        } catch (Exception e) {
            LOGGER.error("售后完成订单状态修改异常", e);
        }
        
    }
    
    /**
     * 售后失败信息亮起后 该订单3天后由“售后服务中”转入“交易完成状态”后
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateReturningOrderStatus(){
        
        try {
            orderRefundService.updateReturningOrderStatus();
        } catch (Exception e) {
            LOGGER.error("[售后失败信息亮起后 该订单3天后由“售后服务中”转入“交易完成状态”],订单状态修改异常", e);
        }
        
    }

}
