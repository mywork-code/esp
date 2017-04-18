package com.apass.gfb.framework.logstash;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.LoggerContext;
import net.logstash.logback.appender.LogstashSocketAppender;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 
 * @description LogstashConfiguration
 *
 * @author lixining
 * @version $Id: LogstashConfiguration.java, v 0.1 2016年11月8日 下午3:26:08 lixining Exp $
 */
@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnProperty(prefix = "apass.logging.logstash", name = "enabled", havingValue = "true", matchIfMissing = false)
public class LogstashConfiguration {
    /**
     * 日志
     */
    private final Logger LOG     = LoggerFactory.getLogger(LogstashConfiguration.class);

    private LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    @Value("${spring.application.name}")
    private String            appName;

    @Value("${server.port}")
    private String            serverPort;

    @Value("${apass.logging.logstash.instanceId:${spring.application.name}}")
    private String            instanceId;

    @Autowired
    private LoggingProperties logProperties;

    @PostConstruct
    private void init() {
        if (logProperties.getLogstash().isEnabled()) {
            addLogstashAppender();
        }
    }

    /**
     * Config Logstash Appender 
     */
    public void addLogstashAppender() {
        LOG.info("Initializing Logstash logging");
        LoggingProperties.Logstash logstash = logProperties.getLogstash();

        LogstashSocketAppender logstashAppender = new LogstashSocketAppender();
        logstashAppender.setName("LOGSTASH");
        logstashAppender.setContext(context);
        String customFields = "{\"app_name\":\"" + appName + "\",\"app_port\":\"" + serverPort + "\","
                              + "\"instance_id\":\"" + instanceId + "\"}";

        // Set the Logstash appender config from JHipster properties
        logstashAppender.setSyslogHost(logstash.getHost());
        logstashAppender.setPort(logstash.getPort());
        logstashAppender.setCustomFields(customFields);

        // Limit the maximum length of the forwarded stacktrace so that it won't exceed the 8KB UDP limit of logstash
        ShortenedThrowableConverter throwableConverter = new ShortenedThrowableConverter();
        throwableConverter.setMaxLength(7500);
        throwableConverter.setRootCauseFirst(true);
        logstashAppender.setThrowableConverter(throwableConverter);

        logstashAppender.start();

        // Wrap the appender in an Async appender for performance
        AsyncAppender asyncLogstashAppender = new AsyncAppender();
        asyncLogstashAppender.setContext(context);
        asyncLogstashAppender.setName("ASYNC_LOGSTASH");
        asyncLogstashAppender.setQueueSize(logstash.getQueueSize());
        asyncLogstashAppender.addAppender(logstashAppender);
        asyncLogstashAppender.start();

        context.getLogger("ROOT").addAppender(asyncLogstashAppender);
    }
}
