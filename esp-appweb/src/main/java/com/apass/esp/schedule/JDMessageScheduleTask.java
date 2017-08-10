package com.apass.esp.schedule;

import com.apass.esp.domain.enums.JdMessageEnum;
import com.apass.esp.mq.listener.JDTaskAmqpAccess;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.third.party.jd.client.JdMessager;
import com.apass.esp.third.party.jd.entity.base.JdApiMessage;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * type: class
 * 京东消息接收
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class JDMessageScheduleTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(JDMessageScheduleTask.class);

    @Autowired
    protected JdMessager jdMessager;


    @Autowired
    private JDTaskAmqpAccess jdTaskAmqpAccess;

    @Autowired
    private SystemEnvConfig systemEnvConfig;

    @Scheduled(cron = "0 0/30 * * * *")
    public void handleJDMessageScheduleTask() {
        if (!systemEnvConfig.isPROD()) {
            return;
        }
        List<JdApiMessage> jdApiMessageList = jdMessager.getJdApiMessages(JdMessageEnum.DELIVERED_ORDER.getType(), JdMessageEnum.SPLIT_ORDER.getType(), JdMessageEnum.WITHDRAW_SKU.getType(), JdMessageEnum.DELETEADD_SKU.getType());
        for (JdApiMessage jdApiMessage : jdApiMessageList
                ) {
            try {
                jdTaskAmqpAccess.directSend(jdApiMessage);
            } catch (Exception e) {
                LOGGER.info("handleJDMessageScheduleTask jdApiMessage", jdApiMessage.getType(), jdApiMessage.getResult());
                continue;
            }
            jdMessager.delete(jdApiMessage.getId());
        }
    }
}
