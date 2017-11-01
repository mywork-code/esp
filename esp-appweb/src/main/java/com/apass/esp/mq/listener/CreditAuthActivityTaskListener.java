package com.apass.esp.mq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

/**
 * Created by jie.xu on 17/7/14.
 */
@Component("creditAuthActivityTaskListener")
public class CreditAuthActivityTaskListener implements MessageListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(CreditAuthActivityTaskListener.class);


  @Override
  public void onMessage(Message message) {
    System.out.println(message);
  }

}
