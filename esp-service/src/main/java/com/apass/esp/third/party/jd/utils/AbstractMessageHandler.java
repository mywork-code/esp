package com.apass.esp.third.party.jd.utils;

import javax.annotation.PostConstruct;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public abstract class AbstractMessageHandler implements Runnable {
    @PostConstruct
    public final void start() {
        final MessageHandler messageHandler = getMessageHandler();
        if (messageHandler == null) {
            return;
        }
    }

    protected MessageHandler getMessageHandler() {
        return null;
    }

    @Override
    public void run() {

    }
}
