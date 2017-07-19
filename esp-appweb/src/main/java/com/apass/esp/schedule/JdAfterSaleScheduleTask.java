package com.apass.esp.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.RefundStatus;
import com.apass.esp.repository.refund.OrderRefundRepository;
import com.apass.esp.service.aftersale.AfterSaleService;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.third.party.jd.client.JdAfterSaleApiClient;
import com.apass.esp.third.party.jd.client.JdApiResponse;
import com.apass.esp.third.party.jd.entity.aftersale.AfsInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * type: class
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
    private OrderRefundRepository orderRefundDao;

    @Scheduled(cron = "0 0/30 * * * *")
    public void handleJdConfirmPreInventoryTask() {

        List<Integer> appendInfoSteps = Arrays.asList(new Integer[]{1, 2, 3, 4, 5});

        List<OrderInfoEntity> orderInfoEntityList = orderService.getJdOrderByOrderStatus("D05");
        for (OrderInfoEntity orderInfoEntity : orderInfoEntityList) {
            long jdOrderId = Long.valueOf(orderInfoEntity.getExtOrderId());
            JdApiResponse<JSONObject> afsInfo = jdAfterSaleApiClient.afterSaleServiceListPageQuery(jdOrderId, 1, 10);
            if (!afsInfo.isSuccess() || afsInfo.getResult() == null) {
                return;
            }
            String result = afsInfo.getResult().getString("serviceInfoList");
            if (result == null || "".equals(result)) {
                return;
            }
            JSONArray array = JSONArray.parseArray(result);
            for (int i = 0; i < array.size(); i++) {
                JSONObject jsonObject = (JSONObject) array.get(i);
                AfsInfo newAfsInfo = AfsInfo.fromOriginalJson(jsonObject);
                Integer afsServiceStep = newAfsInfo.getAfsServiceStep();
                String refundStatus = RefundStatus.REFUND_STATUS01.getCode();
                switch (afsServiceStep) {
                    case 20:
                        refundStatus = RefundStatus.REFUND_STATUS06.getCode();
                        break;
                    case 31:
                        refundStatus = RefundStatus.REFUND_STATUS03.getCode();
                        break;
                    case 32:
                        refundStatus = RefundStatus.REFUND_STATUS03.getCode();
                        break;
                    case 33:
                        refundStatus = RefundStatus.REFUND_STATUS03.getCode();
                        break;
                    case 34:
                        refundStatus = RefundStatus.REFUND_STATUS03.getCode();
                        break;
                    case 40:
                        refundStatus = RefundStatus.REFUND_STATUS05.getCode();
                        break;
                    case 50:
                        refundStatus = RefundStatus.REFUND_STATUS05.getCode();
                        break;
                    case 60:
                        refundStatus = RefundStatus.REFUND_STATUS06.getCode();
                        break;
                }
                Map<String, String> paramMap = new HashMap<String, String>();
                String jdOrderId1 = String.valueOf(newAfsInfo.getJdOrderId());

                paramMap.put("orderId", jdOrderId1);
                paramMap.put("status", refundStatus);

                int updateFlag = orderRefundDao.updateRefundStatusAndCtimeByOrderId(paramMap);
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

}
