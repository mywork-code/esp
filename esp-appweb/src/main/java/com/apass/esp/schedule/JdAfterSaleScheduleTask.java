package com.apass.esp.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.entity.refund.RefundInfoEntity;
import com.apass.esp.domain.entity.refund.ServiceProcessEntity;
import com.apass.esp.domain.enums.RefundStatus;
import com.apass.esp.repository.refund.OrderRefundRepository;
import com.apass.esp.service.aftersale.AfterSaleService;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.service.refund.OrderRefundService;
import com.apass.esp.service.refund.ServiceProcessService;
import com.apass.esp.third.party.jd.client.JdAfterSaleApiClient;
import com.apass.esp.third.party.jd.client.JdApiResponse;
import com.apass.esp.third.party.jd.entity.aftersale.AfsInfo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * type: class
 * 京东订单售后进度处理
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class JdAfterSaleScheduleTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdAfterSaleScheduleTask.class);


    @Autowired
    private JdAfterSaleApiClient jdAfterSaleApiClient;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AfterSaleService afterSaleService;

    @Autowired
    private OrderRefundService orderRefundService;

    @Autowired
    private OrderRefundRepository orderRefundDao;

    @Autowired
    private ServiceProcessService serviceProcessService;

    @Scheduled(cron = "0 0/30 * * * *")
    public void handleJdConfirmPreInventoryTask() {
        List<Integer> appendInfoSteps = Arrays.asList(new Integer[]{1, 2, 3, 4, 5});
        List<OrderInfoEntity> orderInfoEntityList = orderService.getJdOrderByOrderStatus("D05");
        for (OrderInfoEntity orderInfoEntity : orderInfoEntityList) {
            long jdOrderId = Long.valueOf(orderInfoEntity.getExtOrderId());
            JdApiResponse<JSONObject> afsInfo = jdAfterSaleApiClient.afterSaleServiceListPageQuery(jdOrderId, 1, 10);
            if (!afsInfo.isSuccess() || afsInfo.getResult() == null) {
                continue;
            }
            String result = afsInfo.getResult().getString("serviceInfoList");
            if (result == null || "".equals(result)) {
                continue;
            }
            JSONArray array = JSONArray.parseArray(result);
            Integer customerExpect = getCustomerExpect(array);
            Map<String, Object> map = new HashMap<>();
            map.put("orderId", orderInfoEntity.getOrderId());
            if (customerExpect == 10) {
                map.put("refundType", "0");
            } else {
                map.put("refundType", "1");
            }
            RefundInfoEntity refundInfoEntity = orderRefundService.queryRefundInfoByOrderIdAndRefundType(map);
            process(array, refundInfoEntity.getId());
            String refundStatus = getStatus(array);//所有的京东售后单完成才把该售后单变成完成
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("orderId", orderInfoEntity.getOrderId());
            if (!refundStatus.equalsIgnoreCase(RefundStatus.REFUND_STATUS01.getCode())) {
                //根据状态改为售后完成 或者售后失败
                paramMap.put("status", refundStatus);
                orderRefundDao.updateRefundStatusAndCtimeByOrderId(paramMap);

                //为退货 且京东已退款完成
                if (customerExpect == 10 && refundStatus.equalsIgnoreCase(RefundStatus.REFUND_STATUS05.getCode())) {
                    //订单状态改为退款处理中
                    //orderInfoEntity.setStatus(OrderStatus.ORDER_COMPLETED.getCode());
                    //orderService.updateOrderStatus(orderInfoEntity);
                }
            }

            //改变refundDetail里的状态

            for (int i = 0; i < array.size(); i++) {
                JSONObject jsonObject = (JSONObject) array.get(i);
                AfsInfo newAfsInfo = AfsInfo.fromOriginalJson(jsonObject);
                //详细信息
                long afsServiceId = jsonObject.getLong("afsServiceId");
                JdApiResponse<JSONObject> afterSaleDetail = jdAfterSaleApiClient
                        .afterSaleServiceDetailInfoQuery(afsServiceId, appendInfoSteps);
                if (!afterSaleDetail.isSuccess()) {
                    LOGGER.info("afsServiceId: " + afsServiceId + "query jd afterSaleDetail is not success");
                }
                if (afterSaleDetail == null || afterSaleDetail.getResult() == null) {
                    return;
                }
                //退货
                if (newAfsInfo.getCustomerExpect() == 10) {
                    //是否退款完成
                }
                JSONObject jb = (JSONObject) afterSaleDetail.getResult();
            }
        }

    }

    /**
     * 得到状态
     *
     * @param jsonArray
     * @return
     */
    private String getStatus(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            AfsInfo newAfsInfo = AfsInfo.fromOriginalJson(jsonObject);
            Integer afsServiceStep = newAfsInfo.getAfsServiceStep();
            if (afsServiceStep == 20 || afsServiceStep == 60) {
                return RefundStatus.REFUND_STATUS06.getCode();
            }
            if (afsServiceStep != 40 && afsServiceStep != 50) {
                return RefundStatus.REFUND_STATUS01.getCode();
            }
        }
        return RefundStatus.REFUND_STATUS05.getCode();
    }

    /**
     * 插入进度
     *
     * @param jsonArray
     * @param refundId
     * @return
     */
    private void process(JSONArray jsonArray, long refundId) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            AfsInfo newAfsInfo = AfsInfo.fromOriginalJson(jsonObject);
            Integer afsServiceStep = newAfsInfo.getAfsServiceStep();
            list.add(afsServiceStep);
        }
        Integer i = Collections.min(list);
        if (i == 20 || i == 60) {
            insertProcess(refundId, RefundStatus.REFUND_STATUS06.getCode());
        } else if (i == 31 || i == 32) {
            insertProcess(refundId, RefundStatus.REFUND_STATUS03.getCode());
        } else if (i == 33 || i == 34) {
            //insertProcess(refundId,RefundStatus.REFUND_STATUS03.getCode());
        } else if (i == 40 || i == 50) {
            insertProcess(refundId, RefundStatus.REFUND_STATUS05.getCode());
        }
    }


    /**
     * 得到 退货(10)、换货(20)
     *
     * @param jsonArray
     * @return
     */
    private Integer getCustomerExpect(JSONArray jsonArray) {
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        AfsInfo newAfsInfo = AfsInfo.fromOriginalJson(jsonObject);
        return newAfsInfo.getAfsServiceStep();
    }

    /**
     * 插入进度
     *
     * @param refundId
     * @param status
     */
    private void insertProcess(long refundId, String status) {
        Map<String, String> map = new HashMap<>();
        try {
            map.put("refundId", String.valueOf(refundId));
            map.put("nodeName", status);
            List<ServiceProcessEntity> list1 = serviceProcessService.queryServiceProcessByParam(map);
            if (CollectionUtils.isEmpty(list1)) {
                afterSaleService.insertServiceProcessInfo(refundId, status);
            }
        } catch (Exception e) {
            LOGGER.error("refundId {} status {} 插入进度失败", refundId, status);
        }
    }
}
