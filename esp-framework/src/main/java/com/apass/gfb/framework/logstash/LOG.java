package com.apass.gfb.framework.logstash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LOGSTASH
 */
public class LOG {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LOG.class);

    /**
     * 普通info日志
     *
     * @param content 日志内容
     */
    public static void info(String content) {
        LOGGER.info(content);
    }

    /**
     * 普通error日志
     *
     * @param content 日志描述内容
     * @param t       -异常信息
     */
    public static void error(String content, Throwable t) {
        LOGGER.error(content, t);
    }

    /**
     * 记录请求日志
     *
     * @param requestId      请求标识
     * @param requestDesc    调用描述
     * @param requestContent 请求内容
     */
    public static void info(String requestId, String requestDesc, String requestContent) {
        LOGGER.info(getContent(requestId, requestDesc, requestContent));
    }

    /**
     * 记录请求日志
     *
     * @param requestId      请求标识
     * @param requestDesc    调用描述
     * @param requestContent 请求内容
     */
    public static void logstashRequest(String requestId, String requestDesc, String requestContent) {
        LOGGER.info("Request -> " + getContent(requestId, requestDesc, requestContent));
    }

    /**
     * 记录返回日志
     *
     * @param requestId      请求标识
     * @param requestDesc    调用描述
     * @param requestContent 请求内容
     */
    public static void logstashResponse(String requestId, String requestDesc, String requestContent) {
        LOGGER.info("Response -> " + getContent(requestId, requestDesc, requestContent));
    }

    /**
     * 记录返回日志
     *
     * @param requestId      请求标识
     * @param requestDesc    调用描述
     * @param requestContent 请求内容
     * @param t              异常信息
     */
    public static void logstashException(String requestId, String requestDesc, String requestContent, Throwable t) {
        LOGGER.error("Exception ->" + getContent(requestId, requestDesc, requestContent), t);
    }

    private static String getContent(String requestId, String requestDesc, String requestContent) {
        return "ID=" + requestId + ", Description=" + requestDesc + ", Content=" + requestContent;
    }
}
