package com.apass.gfb.framework.constants;

/**
 * Created by jie.xu on 17/7/14.
 */
public class MQConstants {

  public enum MQRouting {
    JD_MSG("esp.topic.notification", "esp.routing.jdmsg", "esp.queue.jdmsg"),
    ;


    private String exchange;
    private String routingKey;
    private String queue;

    MQRouting(String exchange,String routingKey,String queue){
      this.exchange = exchange;
      this.routingKey = routingKey;
      this.queue = queue;
    }

    public String getExchange() {
      return exchange;
    }

    public void setExchange(String exchange) {
      this.exchange = exchange;
    }

    public String getRoutingKey() {
      return routingKey;
    }

    public void setRoutingKey(String routingKey) {
      this.routingKey = routingKey;
    }

    public String getQueue() {
      return queue;
    }

    public void setQueue(String queue) {
      this.queue = queue;
    }
  }
}
