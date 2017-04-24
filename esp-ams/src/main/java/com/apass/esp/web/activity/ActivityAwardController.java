package com.apass.esp.web.activity;

import com.apass.esp.domain.dto.activity.AwardActivityInfoDto;
import com.apass.esp.service.activity.AwardActivityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jie.xu on 17/4/21.
 */
@Controller
@RequestMapping("/activity")
public class ActivityAwardController {

  @Autowired
  private AwardActivityInfoService awardActivityInfoService;

  @RequestMapping(value = "/introduce/index",method = RequestMethod.GET)
  public String introduceConfig(){
    return "activity/introduce";
  }

  /**
   * 添加配置
   */
  @RequestMapping(value = "/introduce/config",method = RequestMethod.POST)
  public String addIntroConfig(AwardActivityInfoDto dto){

    return null;
  }

}
