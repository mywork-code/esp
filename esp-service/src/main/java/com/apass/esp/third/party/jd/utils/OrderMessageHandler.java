package com.apass.esp.third.party.jd.utils;

import org.springframework.stereotype.Service;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */

@Service
public class OrderMessageHandler extends AbstractMessageHandler {
    @Override
    protected String getName() {
        return null;
    }

    @Override
    protected int getIntervalSeconds() {
        return 0;
    }

    @Override
    public void run() {

    }
}
