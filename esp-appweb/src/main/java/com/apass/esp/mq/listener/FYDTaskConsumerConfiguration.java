package com.apass.esp.mq.listener;

import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.apass.gfb.framework.mq.DefaultAbstractAmqpAccess;

/**
 * Created by jie.xu on 17/7/14.
 */
@Component
public class FYDTaskConsumerConfiguration {
  @Autowired
  @Qualifier(value = "fydTaskListener")
  private MessageListener listener;

  @Autowired
  @Qualifier("fydTaskAmqpAccess")
  private DefaultAbstractAmqpAccess amqpAccess;


  @Bean(name = "fydTaskMessageListenerContainer")
  public SimpleMessageListenerContainer listenerContainer() {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(amqpAccess.getConnectionFactory());
    container.setMessageListener(listener);
    container.setQueueNames(amqpAccess.getMqRouting().getQueue());
    return container;
  }
}
