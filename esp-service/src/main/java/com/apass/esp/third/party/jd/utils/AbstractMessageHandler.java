package com.apass.esp.third.party.jd.utils;

import com.apass.esp.third.party.jd.client.JdMessager;
import com.apass.esp.third.party.jd.entity.base.JdApiMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public abstract class AbstractMessageHandler implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMessageHandler.class);

    @Autowired
    protected JdMessager jdMessager;

    protected abstract String getName();

    protected abstract int getIntervalSeconds();

    protected void init() {
    }


    @PostConstruct
    public final void start() {
        final MessageHandler messageHandler = getMessageHandler();
        if (messageHandler == null) {
            return;
        }
        ExecutorService messageExecutorService = Executors.newSingleThreadExecutor(Threads.newDaemonThreadFactory("message_" + getName(),
                Thread.MIN_PRIORITY));
        messageExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                Threads.sleepSeconds(60);
                while (true) {
                    List<JdApiMessage> jdApiMessages = jdMessager.getJdApiMessages(messageHandler.getMessageTypes());


                }
            }
        });

    }

    protected MessageHandler getMessageHandler() {
        return null;
    }


}
