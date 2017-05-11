package com.apass.esp.schedule;

import cn.jpush.api.utils.StringUtils;
import com.apass.esp.domain.dto.activity.AwardDetailDto;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.entity.refund.RefundInfoEntity;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.service.activity.AwardActivityInfoService;
import com.apass.esp.service.activity.AwardDetailService;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.service.refund.OrderRefundService;
import com.apass.gfb.framework.utils.DateFormatUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 邀请人获得返点结算 Task
 *
 * @author xianzhi.wang
 */
@Component
@Configurable
@EnableScheduling
//@Profile("Schedule")
public class RebateActivityScheduleTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(RebateActivityScheduleTask.class);

    @Autowired
    public AwardDetailService awardDetailService;

    @Autowired
    public AwardActivityInfoService awardActivityInfoService;

    @Autowired
    public OrderService orderService;

    @Autowired
    public OrderRefundService orderRefundService;

    /**
     * 5分钟执行一次 测试
     */

//	@Scheduled(cron = "0 0 1 * * ?")
    @Scheduled(cron = "0 0/5 * * * *")
    public void validateActivityEndtime() {
        try {
            LOGGER.info("邀请人获得返点结算定时任务开始");
            Date date = new Date();
            List<AwardDetailDto> list = awardDetailService.queryAwardDetailByStatusAndType(
                    (byte) AwardActivity.AWARD_TYPE.GAIN.getCode(),
                    (byte) AwardActivity.AWARD_STATUS.PROCESSING.getCode());
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            for (AwardDetailDto awardDetailDto : list) {
                String orderId = awardDetailDto.getOrderId();
                if (StringUtils.isEmpty(orderId)) {
                    continue;
                }
                OrderInfoEntity orderInfoEntity = orderService.selectByOrderId(orderId);
                if (orderInfoEntity == null || orderInfoEntity.getLogisticsSignDate() == null) {
                    continue;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("orderId", orderInfoEntity.getOrderId());
                map.put("refundType", "0");
                RefundInfoEntity refundInfoEntity = orderRefundService.queryRefundInfoByOrderIdAndRefundType(map);
                // 订单状态已完成 且订单已完成37天 ，没有发生过退货行为 才能获得返点
                Date newDate = DateFormatUtil.addDays(orderInfoEntity.getLogisticsSignDate(), 37);
                LOGGER.info("orderId {},  newDate {} ,orderInfoEntity.getStatus {},refundInfoEntity {}", orderId, newDate, orderInfoEntity.getStatus(), refundInfoEntity);
                if (refundInfoEntity == null) {
                    //37天以后
                    if ("D04".equals(orderInfoEntity.getStatus()) && newDate.before(date)) {
                        // 更新状态
                        awardDetailDto.setArrivedDate(new Date());
                        awardDetailDto.setUpdateDate(new Date());
                        awardDetailDto.setStatus((byte) 0);
                        try {
                            awardDetailService.updateAwardDetail(awardDetailDto);
                        } catch (Exception e) {
                            LOGGER.error("orderId {} 更新 返现获得 失败=====", orderId, e);
                        }
                        LOGGER.info("activity id {},订单ID {}  newDate {} ,orderInfoEntity.getStatus {},refundInfoEntity {}获得返现成功,金额 ,{}", orderId
                                , newDate, orderInfoEntity.getStatus(), refundInfoEntity, awardDetailDto.getAmount());
                    }
                } else {
                    //退货完成 删除记录
                    if ("RS05".equals(refundInfoEntity.getStatus())) {
                        awardDetailService.delete(awardDetailDto.getId());
                    }
                    //售后失败  再判断  订单状态已完成 且订单已完成37天
                    if ("RS06".equals(refundInfoEntity.getStatus())) {
                        if ("D04".equals(orderInfoEntity.getStatus()) && newDate.before(date)) {
                            // 更新状态
                            awardDetailDto.setArrivedDate(new Date());
                            awardDetailDto.setUpdateDate(new Date());
                            awardDetailDto.setStatus((byte) 0);
                            try {
                                awardDetailService.updateAwardDetail(awardDetailDto);
                            } catch (Exception e) {
                                LOGGER.error("orderId {} 更新 返现获得 失败=====", orderId, e);
                            }
                            LOGGER.info("activity id {},订单ID {}  newDate {} ,orderInfoEntity.getStatus {},refundInfoEntity {}获得返现成功,金额 ,{}", orderId
                                    , newDate, orderInfoEntity.getStatus(), refundInfoEntity, awardDetailDto.getAmount());

                        }
                    }

                }

            }
            LOGGER.info("邀请人获得返点结算定时任务结束");
        } catch (Exception e) {
            LOGGER.error("邀请人获得返点结算", e);
        }
    }
}
