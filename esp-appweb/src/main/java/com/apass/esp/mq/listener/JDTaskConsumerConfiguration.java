package com.apass.esp.mq.listener;

import com.apass.gfb.framework.mq.AbstractAmqpAccess;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by jie.xu on 17/7/14.
 */
@Component
public class JDTaskConsumerConfiguration {
  @Autowired
  @Qualifier(value = "jdTaskListener")
  private MessageListener listener;

  @Autowired
  @Qualifier("jdTaskAmqpAccess")
  private AbstractAmqpAccess amqpAccess;


  @Bean(name = "jdTaskMessageListenerContainer")
  public SimpleMessageListenerContainer listenerContainer() {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(amqpAccess.getConnectionFactory());
    container.setMessageListener(listener);
    container.setQueueNames(amqpAccess.getMqRouting().getQueue());
    return container;
  }
}
