package com.apass.esp.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.third.party.jd.client.JdApiResponse;
import com.apass.esp.third.party.jd.client.JdOrderApiClient;
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
import java.util.List;

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
    private JdOrderApiClient jdOrderApiClient;

    @Autowired
    private GoodsService goodsService;

    @Scheduled(cron = "0 0/5 * * * *")
    public void handleJdConfirmPreInventoryTask() {

        List<OrderInfoEntity> orderInfoEntityList = orderService.getOrderByOrderStatusAndPreStatus();
        if (CollectionUtils.isEmpty(orderInfoEntityList)) {
            return;
        }
        for (OrderInfoEntity orderInfoEntity : orderInfoEntityList
                ) {
            String jdOrderIdp = orderInfoEntity.getExtOrderId();
            LOGGER.info(" JdConfirmPreInventoryTask  jdOrderIdp {}  begin....", jdOrderIdp);
            long jdOrderId = Long.valueOf(jdOrderIdp);
            JdApiResponse<Boolean> confirmResponse = jdOrderApiClient.orderOccupyStockConfirm(jdOrderId);
            LOGGER.info("confirm order jdOrderIdp {} confirmResponse {}", jdOrderIdp, confirmResponse.toString());
            int confirmStatus = 0;
            if (confirmResponse.isSuccess() && confirmResponse.getResult()) {
                JdApiResponse<JSONObject> jdApiResponse = jdOrderApiClient.orderJdOrderQuery(jdOrderId);
                if (!jdApiResponse.isSuccess()) {
                    LOGGER.info("confirm order jdOrderIdp {} confirmResponse {} orderJdOrderQuery result {}", jdOrderIdp, confirmResponse.toString(), jdApiResponse);
                }
                JSONObject jsonObject = jdApiResponse.getResult();
                Object pOrderV = jsonObject.get("pOrder");
                if (pOrderV instanceof Number) {
                    //未拆单
                    long pOrderId = ((Number) pOrderV).longValue();
                } else {
                    //拆单
                    JSONObject pOrderJsonObject = (JSONObject) pOrderV;
                    //父订单状态
                    pOrderJsonObject.getIntValue("type");
                    pOrderJsonObject.getIntValue("submitState");
                    pOrderJsonObject.getIntValue("orderState");

                    JSONArray cOrderArray = jsonObject.getJSONArray("cOrder");
                    for (int i = 0; i < cOrderArray.size(); i++) {
                        JSONObject cOrderJsonObject = cOrderArray.getJSONObject(i);
                        if (cOrderJsonObject.getLongValue("pOrder") != jdOrderId) {
                            LOGGER.info("cOrderJsonObject.getLongValue(\"pOrder\") {}, jdOrderId", cOrderJsonObject.getLongValue("pOrder"), jdOrderId);
                        }
                        long cOrderId = cOrderJsonObject.getLongValue("jdOrderId");//京东子订单ID
                        JSONArray cOrderSkuList = cOrderJsonObject.getJSONArray("sku");
                        BigDecimal jdPrice = BigDecimal.ZERO;//订单金额
                        Integer sumNum = 0;
                        for (int j = 0; j < cOrderSkuList.size(); j++) {
                            long skuId = cOrderSkuList.getJSONObject(j).getLongValue("skuId");
                            goodsService.selectGoodsByExternalId(String.valueOf(skuId));
                            
                            BigDecimal price = cOrderSkuList.getJSONObject(j).getBigDecimal("price");
                            int num = cOrderSkuList.getJSONObject(j).getIntValue("num");
                            String name = cOrderSkuList.getJSONObject(j).getString("name");
                            jdPrice = jdPrice.add(price.multiply(new BigDecimal(num)));
                            sumNum = sumNum + num;

                        }

                    }

                }

            } else {
                LOGGER.info("confirm order jdOrderIdp {}  error confirmResponse: {}", jdOrderIdp, confirmResponse);
            }
        }
    }

}