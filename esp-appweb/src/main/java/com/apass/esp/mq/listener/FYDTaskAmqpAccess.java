package com.apass.esp.mq.listener;

import org.springframework.stereotype.Component;

import com.apass.gfb.framework.constants.MQConstants;
import com.apass.gfb.framework.mq.DefaultAbstractAmqpAccess;
@Component("fydTaskAmqpAccess")
public class FYDTaskAmqpAccess extends DefaultAbstractAmqpAccess{
	  @Override
	  public MQConstants.MQRouting getMqRouting() {
	    return MQConstants.MQRouting.FYD_MSG;
	  }
}
