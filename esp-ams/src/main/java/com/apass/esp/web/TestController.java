package com.apass.esp.web;

import com.apass.esp.schedule.MailStatisScheduleTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jie.xu on 17/7/11.
 */
@Controller
public class TestController {

  @Autowired
  private MailStatisScheduleTask mailStatisScheduleTask;

  @RequestMapping("/test/order/mailstatisc")
  @ResponseBody
  public String orderMail(){
    mailStatisScheduleTask.mailStatisSchedule();
    return "success";
  }
}
