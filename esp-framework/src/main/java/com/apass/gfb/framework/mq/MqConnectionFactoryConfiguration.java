package com.apass.gfb.framework.mq;

import com.apass.gfb.framework.environment.SystemEnvConfig;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jie.xu on 17/7/14.
 */
@Configuration
public class MqConnectionFactoryConfiguration {

  @Autowired
  private SystemEnvConfig envConfig;

  @Bean(name = "mqCachingConnectionFactory")
  public CachingConnectionFactory cachingConnectionFactory() {

    ConnectionFactory rabbitMqConnFactory = new ConnectionFactory();
    rabbitMqConnFactory.setAutomaticRecoveryEnabled(true);
    if(envConfig.isPROD()){
      //生产环境
      rabbitMqConnFactory.setHost("10.1.12.178");
      rabbitMqConnFactory.setPort(5672);
    }else{
      rabbitMqConnFactory.setHost("10.254.60.12");
      rabbitMqConnFactory.setPort(5672);
    }
    rabbitMqConnFactory.setVirtualHost("esp");
    rabbitMqConnFactory.setUsername("admin");
    rabbitMqConnFactory.setPassword("admin");
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitMqConnFactory);
    connectionFactory.setChannelCacheSize(10);

    return connectionFactory;
  }

}
