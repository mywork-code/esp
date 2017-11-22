package com.apass.esp.schedule;

import java.util.Date;
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

import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.entity.ServiceError;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.ServiceErrorType;
import com.apass.esp.mapper.ServiceErrorMapper;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.third.party.jd.client.JdApiResponse;
import com.apass.esp.third.party.weizhi.client.WeiZhiOrderApiClient;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import com.apass.gfb.framework.exception.BusinessException;

/**
 * type: class
 * 确认预占库存，拆单情况处理
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */

@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class JdConfirmPreInventoryTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdConfirmPreInventoryTask.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private WeiZhiOrderApiClient orderApi;

    @Autowired
    private ServiceErrorMapper serviceErrorMapper;

    @Autowired
    private SystemEnvConfig systemEnvConfig;

//    @Scheduled(cron = "0 0/30 * * * *")
//    public void handleJdConfirmPreInventoryTask() {
//        if(!systemEnvConfig.isPROD()){
//            return;
//        }
//        List<OrderInfoEntity> orderInfoEntityList = orderService.getOrderByOrderStatusAndPreStatus();
//        if (CollectionUtils.isEmpty(orderInfoEntityList)) {
//            return;
//        }
//        for (OrderInfoEntity orderInfoEntity : orderInfoEntityList
//                ) {
//            String jdOrderIdp = orderInfoEntity.getExtOrderId();
//            LOGGER.info(" JdConfirmPreInventoryTask  jdOrderIdp {}  begin....", jdOrderIdp);
//            long jdOrderId = Long.valueOf(jdOrderIdp);
//            JdApiResponse<Boolean> confirmResponse = jdOrderApiClient.orderOccupyStockConfirm(jdOrderId);
//            LOGGER.info("confirm order jdOrderIdp {} confirmResponse {}", jdOrderIdp, confirmResponse.toString());
//            int confirmStatus = 0;
//            if (confirmResponse.isSuccess() && confirmResponse.getResult()) {
//                JdApiResponse<JSONObject> jdApiResponse = jdOrderApiClient.orderJdOrderQuery(jdOrderId);
//                if (!jdApiResponse.isSuccess()) {
//                    LOGGER.info("confirm order jdOrderIdp {} confirmResponse {} orderJdOrderQuery result {}", jdOrderIdp, confirmResponse.toString(), jdApiResponse);
//                    continue;
//                }
//                JSONObject jsonObject = jdApiResponse.getResult();
//                try {
//                    orderService.jdSplitOrderMessageHandle(jsonObject, orderInfoEntity);
//                } catch (BusinessException e) {
//                    LOGGER.info("jdSplitOrderMessageHandle do not have split ", jdOrderIdp);
//                    continue;
//                } catch (Exception e) {
//                    continue;
//                }
//            } else {
//                //确认预占库存失败
//                ServiceError serviceError = new ServiceError();
//                serviceError.setCreateDate(new Date());
//                serviceError.setOrderId(jdOrderIdp);
//                serviceError.setUpdateDate(new Date());
//                serviceError.setType(ServiceErrorType.JD_ORDER_PAY.getDesc());
//                serviceErrorMapper.insertSelective(serviceError);
//                LOGGER.info("confirm order jdOrderIdp {}  error confirmResponse: {}", jdOrderIdp, confirmResponse);
//            }
//        }
//    }

    
    @Scheduled(cron = "0 0/30 * * * *")
    public void handleJdConfirmPreInventoryTask() {
        if(!systemEnvConfig.isPROD()){
            return;
        }
        /**
         * 查询订单状态D03(已发货)，预占库存状态为1(1.预占 2.确认)，并且source 为京东的订单
         */
        List<OrderInfoEntity> orderInfoEntityList = orderService.getOrderByOrderStatusAndPreStatus();
        if (CollectionUtils.isEmpty(orderInfoEntityList)) {
            return;
        }
        for (OrderInfoEntity orderInfoEntity : orderInfoEntityList) {
            String wzOrderIdp = orderInfoEntity.getExtOrderId();//微知订单号
            LOGGER.info(" WZConfirmPreInventoryTask  wzOrderIdp {}  begin....", wzOrderIdp);
            boolean confirmResponse = false;
            try {
            	 confirmResponse = orderApi.orderOccupyStockConfirm(wzOrderIdp);//确认预占预占库存完成
			} catch (Exception e) {
				continue;
			}
            LOGGER.info("confirm order wzOrderIdp {} confirmResponse {}", wzOrderIdp, confirmResponse);
            if (confirmResponse) {
            	try {
            	orderService.wzSplitOrderMessageHandle(orderInfoEntity);
            	} catch (BusinessException e) {
                  LOGGER.info("wzSplitOrderMessageHandle do not have split ", wzOrderIdp);
                  continue;
                } catch (Exception e) {
                  continue;
                }
            } else {
                //确认预占库存失败
                ServiceError serviceError = new ServiceError();
                serviceError.setCreateDate(new Date());
                serviceError.setOrderId(wzOrderIdp);
                serviceError.setUpdateDate(new Date());
                serviceError.setType(ServiceErrorType.JD_ORDER_PAY.getDesc());
                serviceErrorMapper.insertSelective(serviceError);
                LOGGER.info("confirm order wzOrderIdp {}  error confirmResponse: {}", wzOrderIdp, confirmResponse);
            }
        }
    }
}
