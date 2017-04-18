package com.apass.gfb.framework.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Created by jie.xu on 17/4/14.
 */
@Configuration
public class DataBaseConfiguration {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseConfiguration.class);

  @Value("${spring.datasource.url}")
  private String datasourceUrl;
  @Value("${spring.datasource.driverClassName}")
  private String driverClassName;
  @Value("${spring.datasource.username}")
  private String userName;
  @Value("${spring.datasource.password}")
  private String password;
  private int initialSize = 3;
  @Value("${spring.datasource.max-active}")
  private String maxActive;
  @Value("${spring.datasource.max-wait}")
  private String maxWait;
  @Value("${spring.datasource.min-idle}")
  private String minIdle;

  @Bean(name = "mysqlDataSource", destroyMethod = "close", initMethod = "init")
  @Primary
  public DataSource dataSource() {
    LOGGER.debug("Configruing mysql DataSource");
    return this.createEnvDataSource();
  }// mysql只需要配置数据源即可，sqlsessionFactory和事务已经在app包下配置，同时mybatis也已经在app包中配置


  private DruidDataSource createEnvDataSource() {
    DruidDataSource datasource = new DruidDataSource();
    datasource.setUrl(datasourceUrl);
    datasource.setDriverClassName(driverClassName);
    datasource.setUsername(userName);
    datasource.setPassword(password);
    datasource.setInitialSize(initialSize);
    datasource.setMaxActive(Integer.parseInt(maxActive));
    datasource.setMaxWait(Long.valueOf(maxWait));
    datasource.setMinIdle(Integer.parseInt(minIdle));
    datasource.setValidationQuery("select 1 from DUAL");
    return datasource;
  }
}
