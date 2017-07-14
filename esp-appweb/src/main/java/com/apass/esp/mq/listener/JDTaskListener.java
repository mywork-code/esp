package com.apass.esp.mq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

/**
 * Created by jie.xu on 17/7/14.
 */
@Component("jdTaskListener")
public class JDTaskListener implements MessageListener {
  private static Logger log = LoggerFactory.getLogger(JDTaskListener.class);

  @Override
  public void onMessage(Message message) {
    log.info("jdTaskListener start consume message............");
  }
}
