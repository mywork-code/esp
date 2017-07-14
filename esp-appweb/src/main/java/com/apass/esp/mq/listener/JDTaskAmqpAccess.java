package com.apass.esp.mq.listener;

import com.apass.gfb.framework.constants.MQConstants;
import com.apass.gfb.framework.mq.AbstractAmqpAccess;
import org.springframework.stereotype.Component;

/**
 * Created by jie.xu on 17/7/14.
 */
@Component("jdTaskAmqpAccess")
public class JDTaskAmqpAccess extends AbstractAmqpAccess{
  @Override
  public MQConstants.MQRouting getMqRouting() {
    return MQConstants.MQRouting.JD_MSG;
  }
}
