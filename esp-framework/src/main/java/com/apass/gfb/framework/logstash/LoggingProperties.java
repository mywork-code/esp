package com.apass.gfb.framework.logstash;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lixining
 * @version $Id: LoggingProperties.java, v 0.1 2016年11月8日 下午3:18:19 lixining Exp $
 * @description LoggingProperties
 */
@ConfigurationProperties(prefix = "apass.logging")
public class LoggingProperties {
    /**
     * LogStash
     */
    private Logstash logstash = new Logstash();

    /**
     * Setter method for property <tt>logstash</tt>.
     *
     * @param logstash value to be assigned to property logstash
     */
    public void setLogstash(Logstash logstash) {
        this.logstash = logstash;
    }

    /**
     * Getter method for property <tt>logstash</tt>.
     *
     * @return property value of logstash
     */
    public Logstash getLogstash() {
        return logstash;
    }

    /**
     * @description Logstash Config
     */
    public static class Logstash {
        /**
         * 是否使用Logstash
         */
        private boolean enabled = false;
        /**
         * Logstash 地址IP
         */
        private String host = "localhost";
        /**
         * Logstash 端口
         */
        private Integer port = 5000;
        /**
         * 队列大小
         */
        private Integer queueSize = 512;

        /**
         * Getter method for property <tt>enabled</tt>.
         *
         * @return property value of enabled
         */
        public boolean isEnabled() {
            return enabled;
        }

        /**
         * Setter method for property <tt>enabled</tt>.
         *
         * @param enabled value to be assigned to property enabled
         */
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        /**
         * Getter method for property <tt>host</tt>.
         *
         * @return property value of host
         */
        public String getHost() {
            return host;
        }

        /**
         * Setter method for property <tt>host</tt>.
         *
         * @param host value to be assigned to property host
         */
        public void setHost(String host) {
            this.host = host;
        }

        /**
         * Getter method for property <tt>port</tt>.
         *
         * @return property value of port
         */
        public Integer getPort() {
            return port;
        }

        /**
         * Setter method for property <tt>port</tt>.
         *
         * @param port value to be assigned to property port
         */
        public void setPort(Integer port) {
            this.port = port;
        }

        /**
         * Getter method for property <tt>queueSize</tt>.
         *
         * @return property value of queueSize
         */
        public Integer getQueueSize() {
            return queueSize;
        }

        /**
         * Setter method for property <tt>queueSize</tt>.
         *
         * @param queueSize value to be assigned to property queueSize
         */
        public void setQueueSize(Integer queueSize) {
            this.queueSize = queueSize;
        }

    }

}
