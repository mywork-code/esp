package com.apass.esp.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
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
import java.util.List;

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
                Integer afsServiceStep =  newAfsInfo.getAfsServiceStep();

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
                JSONObject jb = (JSONObject) afterSaleDetail.getResult();


            }
        }

    }

}
