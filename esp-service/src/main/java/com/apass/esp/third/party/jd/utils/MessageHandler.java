package com.apass.esp.third.party.jd.utils;

import com.apass.esp.third.party.jd.entity.base.JdApiMessage;

import java.util.List;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public interface MessageHandler {

    int[] getMessageTypes();

    List<Long> handle(List<JdApiMessage> jdApiMessages);
}
