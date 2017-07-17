package com.apass.esp.mq.listener;

import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.enums.GoodStatus;
import com.apass.esp.domain.enums.JdMessageEnum;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.third.party.jd.entity.base.JdApiMessage;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by jie.xu on 17/7/14.
 */
@Component("jdTaskListener")
public class JDTaskListener implements MessageListener {

    private static Logger log = LoggerFactory.getLogger(JDTaskListener.class);

    @Autowired
    private GoodsService goodsService;

    @Override
    public void onMessage(Message message) {
        JdApiMessage jdApiMessage = JSONObject.parseObject(message.getBody(), JdApiMessage.class);
        if (jdApiMessage == null) {
            log.info("jdApiMessage null error...");
        }
        JSONObject result = jdApiMessage.getResult();
        if (jdApiMessage.getType() == JdMessageEnum.WITHDRAW_SKU.getType()) {//商品下架消息
            long skuId = result.getLongValue("skuId");
            GoodsInfoEntity goodsInfoEntity = goodsService.selectGoodsByExternalId(String.valueOf(skuId));
            goodsInfoEntity.setStatus(GoodStatus.GOOD_DOWN.getCode());
            goodsInfoEntity.setUpdateDate(new Date());
            goodsInfoEntity.setDelistTime(new Date());
            goodsService.updateService(goodsInfoEntity);
            return;
        }
        if (jdApiMessage.getType() == JdMessageEnum.DELIVERED_ORDER.getType()) {//订单妥投消息
            long orderId = result.getLongValue("orderId");
            int state = result.getIntValue("state");
            //更新
            return;
        }
        if (jdApiMessage.getType() == JdMessageEnum.SPLIT_ORDER.getType()) {
            //新增
            return;
        }

        log.info("jdTaskListener start consume message............");
    }
}
