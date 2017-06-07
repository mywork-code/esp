package com.apass.esp.web.activity;

import com.apass.esp.domain.vo.AwardBindRelIntroVo;
import com.apass.esp.service.activity.AwardActivityInfoService;
import com.apass.esp.service.activity.AwardBindRelService;
import com.apass.esp.service.activity.AwardDetailService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.utils.BaseConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jie.xu on 17/4/21.
 */
@Controller
@RequestMapping("/activity/introduce/loans")
public class AwardBindRelIntroController {
	/**
	 * 日志
	 */
  private static final Logger LOGGER  = LoggerFactory.getLogger(AwardBindRelIntroController.class);
  @Autowired
  private AwardActivityInfoService awardActivityInfoService;

  @Autowired
  private AwardDetailService awardDetailService;
  
  @Autowired
  private AwardBindRelService awardBindRelService; 

  /**
   * 转介绍活动配置页
   * @return
   */
  @RequestMapping(value = "/page", method = RequestMethod.GET)
  public String introduceConfig() {
    return "activity/introLoan-list";
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public ResponsePageBody<AwardBindRelIntroVo> queryAwardIntroList(HttpServletRequest request) {
	  ResponsePageBody<AwardBindRelIntroVo> responsePageBody = new ResponsePageBody<AwardBindRelIntroVo>();
	  try{
		  responsePageBody = awardDetailService.queryAwardIntroList(request);
		  
	  }catch(Exception e){
		  LOGGER.error("查询转介绍放款列表失败",e);
		  responsePageBody.setStatus(BaseConstants.CommonCode.FAILED_CODE);
		  responsePageBody.setMsg(e.getMessage());
	  }
	  
	  return responsePageBody;
  }

}
