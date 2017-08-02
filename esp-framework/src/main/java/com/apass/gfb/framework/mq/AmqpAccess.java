package com.apass.gfb.framework.mq;

/**
 * Created by jie.xu on 17/7/14.
 */
public interface AmqpAccess {

  void directSend(Object message);
}
