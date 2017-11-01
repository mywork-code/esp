package com.apass.esp.mq.listener;

import com.apass.gfb.framework.constants.MQConstants;
import com.apass.gfb.framework.mq.DefaultAbstractAmqpAccess;
import org.springframework.stereotype.Component;

/**
 * Created by jie.xu on 17/11/1.
 */
@Component("creditAuthActivityTaskAmqpAccess")
public class CreditAuthActivityTaskAmqpAccess extends DefaultAbstractAmqpAccess {
  @Override
  public MQConstants.MQRouting getMqRouting() {
    return MQConstants.MQRouting.CREDIT_AUTH_ACTIVITY;
  }
}
