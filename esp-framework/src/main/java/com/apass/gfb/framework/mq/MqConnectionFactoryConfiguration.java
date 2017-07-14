package com.apass.gfb.framework.mq;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jie.xu on 17/7/14.
 */
@Configuration
public class MqConnectionFactoryConfiguration {

  @Bean(name = "mqCachingConnectionFactory")
  public CachingConnectionFactory cachingConnectionFactory() {

    ConnectionFactory rabbitMqConnFactory = new ConnectionFactory();
    rabbitMqConnFactory.setAutomaticRecoveryEnabled(true);
    rabbitMqConnFactory.setHost("10.254.60.12");
    rabbitMqConnFactory.setPort(5672);
    rabbitMqConnFactory.setVirtualHost("esp");
    rabbitMqConnFactory.setUsername("admin");
    rabbitMqConnFactory.setPassword("admin");
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitMqConnFactory);
    connectionFactory.setChannelCacheSize(10);

    return connectionFactory;
  }

}
