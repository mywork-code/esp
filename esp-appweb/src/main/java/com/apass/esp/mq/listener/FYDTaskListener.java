package com.apass.esp.mq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.entity.CreditAuthActivityMessage;
import com.apass.esp.domain.vo.FydActivity;
import com.apass.esp.service.offer.MyCouponManagerService;

/**
 * Created by jie.xu on 17/7/14.
 */
@Component("fydTaskListener")
public class FYDTaskListener implements MessageListener {
  private static final Logger logger = LoggerFactory.getLogger(FYDTaskListener.class);

  @Autowired
  private MyCouponManagerService managerService;
  
  @Override
  public void onMessage(Message message) {
		logger.info("message ========>>>>>>>{}",message.toString());
		FydActivity fyd = JSONObject.parseObject(message.getBody(),FydActivity.class);
	    
		if(null == fyd){
			logger.error("fyd return params is null!!!!");
			return;
		}
		try {
			managerService.addFYDYHZY(fyd);
		} catch (Exception e) {
			logger.error("send fyd coupon is failed！exception=====>>>>>{}",e);
		}
  }
}
