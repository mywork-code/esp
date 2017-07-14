package com.apass.gfb.framework.mq;

import com.apass.gfb.framework.constants.MQConstants;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;

/**
 * Created by jie.xu on 17/7/14.
 */
public abstract class AbstractAmqpAccess implements AmqpAccess {

  private RabbitTemplate rabbitTemplate;

  @Autowired
  @Qualifier("mqCachingConnectionFactory")
  private CachingConnectionFactory connectionFactory;


  @Override
  public void directSend(Object message) {
    if (null == rabbitTemplate) {
      throw new RuntimeException("exchange:[" + getMqRouting().getExchange() + "] not regist.");
    }
    rabbitTemplate.convertAndSend(message);
  }

  @PostConstruct
  protected void postConstruct() {
    this.rabbitTemplate = new RabbitTemplate();
    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    rabbitTemplate.setConnectionFactory(getConnectionFactory());
    rabbitTemplate.setExchange(getMqRouting().getExchange());
    rabbitTemplate.setRoutingKey(getMqRouting().getRoutingKey());
  }

  public CachingConnectionFactory getConnectionFactory() {
    return connectionFactory;
  }

  public abstract MQConstants.MQRouting getMqRouting();
}
