package com.apass.gfb.framework.mq;

import com.apass.gfb.framework.constants.MQConstants;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by jie.xu on 17/7/14.
 */
public abstract class DefaultAbstractAmqpAccess implements AmqpAccess {

  private RabbitTemplate rabbitTemplate;

  @Autowired
  private SystemEnvConfig envConfig;



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

    DefaultMqConnectionFactoryConfiguration conn = new DefaultMqConnectionFactoryConfiguration();
    return conn.getDefaultCachingConnectionFactory(envConfig);
  }

  public abstract MQConstants.MQRouting getMqRouting();
}
