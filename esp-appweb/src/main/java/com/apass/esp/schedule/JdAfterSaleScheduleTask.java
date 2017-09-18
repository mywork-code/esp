package com.apass.esp.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.entity.CashRefund;
import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.entity.refund.RefundDetailInfoEntity;
import com.apass.esp.domain.entity.refund.RefundInfoEntity;
import com.apass.esp.domain.entity.refund.ServiceProcessEntity;
import com.apass.esp.domain.enums.RefundStatus;
import com.apass.esp.mapper.CashRefundMapper;
import com.apass.esp.mapper.TxnInfoMapper;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.repository.refund.OrderRefundRepository;
import com.apass.esp.repository.refund.RefundDetailInfoRepository;
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

import java.math.BigDecimal;
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
    private RefundDetailInfoRepository refundDetailInfoRepository;

    @Autowired
    private ServiceProcessService serviceProcessService;

    @Autowired
    private OrderRefundRepository orderRefundRepository;

    @Autowired
    private CashRefundMapper cashRefundMapper;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private TxnInfoMapper txnInfoMapper;
    /**
     * 京东售后状态更新
     */
    @Scheduled(cron = "0 0/30 * * * *")
    public void handleJdConfirmPreInventoryTask() {
        //List<Integer> appendInfoSteps = Arrays.asList(new Integer[]{1, 2, 3, 4, 5});
        List<OrderInfoEntity> orderInfoEntityList = orderService.getJdOrderByOrderStatus("D05");
        LOGGER.info("refund task begin...");
        for (OrderInfoEntity orderInfoEntity : orderInfoEntityList) {
            LOGGER.info("orderInfoEntity.getExtOrderId() {}",orderInfoEntity.getExtOrderId());
            long jdOrderId = Long.valueOf(orderInfoEntity.getExtOrderId());
            JdApiResponse<JSONObject> afsInfo = jdAfterSaleApiClient.afterSaleServiceListPageQuery(jdOrderId, 1, 10);
            if (!afsInfo.isSuccess() || afsInfo.getResult() == null) {
                continue;
            }
            String result = afsInfo.getResult().getString("serviceInfoList");
            if (result == null || "".equals(result)) {
                continue;
            }
            LOGGER.info("orderInfoEntity.getExtOrderId() {},result {}",orderInfoEntity.getExtOrderId(),result);
            JSONArray array = JSONArray.parseArray(result);
            Integer customerExpect = getCustomerExpect(array, orderInfoEntity.getOrderId());

            Map<String, Object> map = new HashMap<>();
            map.put("orderId", orderInfoEntity.getOrderId());
            if (customerExpect == 10) {
                map.put("refundType", "0");
            } else {
                map.put("refundType", "1");
            }
            RefundInfoEntity refundInfoEntity = orderRefundService.queryRefundInfoByOrderIdAndRefundType(map);
            if (refundInfoEntity == null) {
                return;
            }
            process(array, refundInfoEntity.getId(),orderInfoEntity.getOrderId());
            //该售后单的状态改为进度最慢的子售后单进度
            String refundStatus = getStatus(array);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("orderId", orderInfoEntity.getOrderId());
            if (!refundStatus.equalsIgnoreCase(RefundStatus.REFUND_STATUS01.getCode())) {
                //根据状态改变
                paramMap.put("status", refundStatus);
                if(refundStatus.equals(RefundStatus.REFUND_STATUS05.getCode())){
                    paramMap.put("completionTime",new Date());
                }
                LOGGER.info("updateRefundStatusJDByOrderId  orderId {}",orderInfoEntity.getOrderId());
                orderRefundDao.updateRefundStatusJDByOrderId(paramMap);
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
        int j = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            AfsInfo newAfsInfo = AfsInfo.fromOriginalJson(jsonObject);
            Integer afsServiceStep = newAfsInfo.getAfsServiceStep();
            list.add(afsServiceStep);
            if (afsServiceStep == 40 || afsServiceStep == 50) {
                j++;
            }
        }
        if (j == jsonArray.size()) {
            return RefundStatus.REFUND_STATUS05.getCode();
        } else {
            Integer step = Collections.min(list);
            if (step == 20 || step == 60) {
                return RefundStatus.REFUND_STATUS06.getCode();
            } else if (step == 31) {
                return RefundStatus.REFUND_STATUS02.getCode();
            } else if (step == 32) {
                return RefundStatus.REFUND_STATUS03.getCode();
            } else if (step == 33 || step == 34) {
                return RefundStatus.REFUND_STATUS04.getCode();
            } else if (step == 40 || step == 50) {
                return RefundStatus.REFUND_STATUS05.getCode();
            } else {
                return RefundStatus.REFUND_STATUS01.getCode();
            }
        }
    }

    /**
     * 插入进度
     *
     * @param jsonArray
     * @param refundId
     * @return
     */
    private void process(JSONArray jsonArray, long refundId,String orderId) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            AfsInfo newAfsInfo = AfsInfo.fromOriginalJson(jsonObject);
            Integer afsServiceStep = newAfsInfo.getAfsServiceStep();
            list.add(afsServiceStep);
        }
        Integer i = Collections.min(list);
        LOGGER.info("process orderId {} ,i {}",orderId,i);
        if (i == 20 || i == 60) {
            insertProcess(refundId, RefundStatus.REFUND_STATUS06.getCode(), "",orderId);
        } else if (i == 33 || i == 34||i == 32||i == 31) {
            Map<String, String> map = new HashMap<>();
            map.put("orderId", orderId);
            map.put("refundId", String.valueOf(refundId));
            map.put("approvalUser", "jdAdmin");
            map.put("approvalComments", "同意");
            orderRefundRepository.agreeRefundByOrderId(map);
            insertProcess(refundId, RefundStatus.REFUND_STATUS02.getCode(), "",orderId);
            insertProcess(refundId, RefundStatus.REFUND_STATUS03.getCode(), "",orderId);
            insertProcess(refundId, RefundStatus.REFUND_STATUS04.getCode(), "",orderId);
        } else if (i == 40 || i == 50) {
            insertProcess(refundId, RefundStatus.REFUND_STATUS05.getCode(), "",orderId);
        }
    }


    /**
     * 得到 退货(10)、换货(20)
     *
     * @param jsonArray
     * @return
     */
    private Integer getCustomerExpect(JSONArray jsonArray, String orderId) {
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        AfsInfo newAfsInfo = AfsInfo.fromOriginalJson(jsonObject);
        LOGGER.info("newAfsInfo.. {}",JSONObject.toJSONString(newAfsInfo));
        for (Object object : jsonArray) {
            JSONObject jsonObject1 = (JSONObject) object;
            AfsInfo newAfsInfo1 = AfsInfo.fromOriginalJson(jsonObject1);
            RefundDetailInfoEntity refundDetailInfoEntity = new RefundDetailInfoEntity();
            refundDetailInfoEntity.setGoodsId(newAfsInfo1.getWareId());
            refundDetailInfoEntity.setOrderId(orderId);
            Integer i = newAfsInfo1.getAfsServiceStep();
            if (i == 20 || i == 60) {
                refundDetailInfoEntity.setStatus(RefundStatus.REFUND_STATUS06.getCode());
            } else if (i == 31) {
                refundDetailInfoEntity.setStatus(RefundStatus.REFUND_STATUS02.getCode());
            } else if (i == 32) {
                refundDetailInfoEntity.setStatus(RefundStatus.REFUND_STATUS03.getCode());
            } else if (i == 34 || i == 40) {
                refundDetailInfoEntity.setStatus(RefundStatus.REFUND_STATUS04.getCode());
            } else if (i == 50) {
                refundDetailInfoEntity.setStatus(RefundStatus.REFUND_STATUS05.getCode());
            } else {
                refundDetailInfoEntity.setStatus(RefundStatus.REFUND_STATUS01.getCode());
            }
            refundDetailInfoRepository.updateByStatusAndGoodsId(refundDetailInfoEntity);
        }

        return newAfsInfo.getCustomerExpect();
    }

    /**
     * 插入进度
     *
     * @param refundId
     * @param status
     */
    private void insertProcess(long refundId, String status, String modeMessage,String orderId) {
        Map<String, String> map = new HashMap<>();
        try {
            map.put("refundId", String.valueOf(refundId));
            map.put("nodeName", status);
            List<ServiceProcessEntity> list1 = serviceProcessService.queryServiceProcessByParam(map);
            if (CollectionUtils.isEmpty(list1)) {
                if(status.equalsIgnoreCase(RefundStatus.REFUND_STATUS05.getCode())){
                    //RS05时 退款  退款额度
                    CashRefund cashRefund = cashRefundMapper.getCashRefundByOrderId(orderId);
                    OrderInfoEntity orderEntity = orderInfoRepository.selectByOrderId(orderId);
                    List<TxnInfoEntity> txnInfoEntityList = txnInfoMapper.selectByOrderId(cashRefund.getMainOrderId());
                    if (CollectionUtils.isNotEmpty(txnInfoEntityList)) {
                        BigDecimal txnAmt = new BigDecimal(0);
                        Date date = new Date();
                        if (txnInfoEntityList.size() == 1) {
                            
                        }
                    }

                }
                afterSaleService.insertServiceProcessInfo(refundId, status, modeMessage);
            }
        } catch (Exception e) {
            LOGGER.error("refundId {} status {} 插入进度失败", refundId, status);
        }
    }
}
