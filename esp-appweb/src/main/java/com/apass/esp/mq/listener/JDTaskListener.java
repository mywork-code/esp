package com.apass.esp.mq.listener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.GoodStatus;
import com.apass.esp.domain.enums.JdMessageEnum;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.third.party.jd.client.JdApiResponse;
import com.apass.esp.third.party.jd.client.JdOrderApiClient;
import com.apass.esp.third.party.jd.client.JdProductApiClient;
import com.apass.esp.third.party.jd.entity.base.JdApiMessage;
import com.apass.gfb.framework.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jie.xu on 17/7/14.
 */
@Component("jdTaskListener")
public class JDTaskListener implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(JDTaskListener.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private JdOrderApiClient jdOrderApiClient;

    @Autowired
    private JdProductApiClient jdProductApiClient;

    @Override
    public void onMessage(Message message) {
        JdApiMessage jdApiMessage = JSONObject.parseObject(message.getBody(), JdApiMessage.class);
        if (jdApiMessage == null) {
            LOGGER.info("jdApiMessage null error...");
        }
        JSONObject result = jdApiMessage.getResult();
        if (jdApiMessage.getType() == JdMessageEnum.WITHDRAW_SKU.getType()) {//商品下架消息
            long skuId = result.getLongValue("skuId");
            Set<Long> skus = new HashSet<>();
            skus.add(skuId);
            JdApiResponse<JSONArray> productPrice = jdProductApiClient.productStateQuery(skus);
            for (Object o : productPrice.getResult()) {
                JSONObject jsonObject = (JSONObject) o;
                //long skuId1 = jsonObject.getLong("sku");
                int state = jsonObject.getIntValue("state");
                //下架
                if (state == 0) {
                    //直接将商品下架
                    GoodsInfoEntity goodsInfoEntity = goodsService.selectGoodsByExternalId(String.valueOf(skuId));
                    goodsInfoEntity.setStatus(GoodStatus.GOOD_DOWN.getCode());
                    goodsInfoEntity.setUpdateDate(new Date());
                    goodsInfoEntity.setDelistTime(new Date());
                    goodsService.updateService(goodsInfoEntity);
                    //TODO
                    //上架处理  京东表处理
                }
            }
            return;
        }
        if (jdApiMessage.getType() == JdMessageEnum.DELIVERED_ORDER.getType()) {//订单妥投消息
            long orderId = result.getLongValue("orderId");
            int state = result.getIntValue("state");
            if (state == 1) {
                OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
                orderInfoEntity.setStatus(OrderStatus.ORDER_COMPLETED.getCode());
                orderInfoEntity.setExtOrderId(String.valueOf(orderId));
                orderInfoRepository.updateOrderStatusByExtOrderId(orderInfoEntity);
            }

            //TODO
            //拒收处理
            return;
        }
        //拆单消息接收
        if (jdApiMessage.getType() == JdMessageEnum.SPLIT_ORDER.getType()) {
            long jdOrderId = result.getLongValue("pOrder");
            JdApiResponse<JSONObject> jdApiResponse = jdOrderApiClient.orderJdOrderQuery(jdOrderId);
            if (!jdApiResponse.isSuccess()) {
                LOGGER.info("confirm order result {}", jdApiResponse);
                return;
            }
            JSONObject jsonObject = jdApiResponse.getResult();
            OrderInfoEntity orderInfoEntity = orderInfoRepository.getOrderInfoByExtOrderId(String.valueOf(jdOrderId));
            try {
                orderService.jdSplitOrderMessageHandle(jsonObject, orderInfoEntity);
            } catch (BusinessException e) {
                return;
            }

        }
        LOGGER.info("jdTaskListener start consume message............");
    }
}
