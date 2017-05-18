package com.apass.esp.web.activity;

import com.apass.esp.common.model.QueryParams;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.activity.AwardActivityInfoDto;
import com.apass.esp.domain.entity.AwardActivityInfo;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.domain.query.ActivityBindRelStatisticQuery;
import com.apass.esp.domain.vo.AwardBindRelStatisticVo;
import com.apass.esp.service.activity.AwardActivityInfoService;
import com.apass.esp.service.activity.AwardBindRelService;
import com.apass.esp.service.activity.AwardDetailService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.esp.utils.ResponsePageIntroStaticBody;
import com.apass.esp.web.banner.BannerController;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.security.userdetails.ListeningCustomSecurityUserDetails;
import com.apass.gfb.framework.utils.BaseConstants;
import com.apass.gfb.framework.utils.HttpWebUtils;

import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/activity")
public class ActivityAwardController {
    /**
     * 日志
     */
  private static final Logger LOGGER  = LoggerFactory.getLogger(ActivityAwardController.class);
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
  @RequestMapping(value = "/introduce/index", method = RequestMethod.GET)
  public String introduceConfig() {
    return "activity/introduce";
  }

  @RequestMapping(value = "/introduce/list", method = RequestMethod.GET)
  @ResponseBody
  public ResponsePageBody listConfig(QueryParams query) {
    return  awardActivityInfoService.listActivity(query);
  }


  /**
   * 添加配置
   */
  @RequestMapping(value = "/introduce/config", method = RequestMethod.POST)
  @ResponseBody
  public Response addIntroConfig(AwardActivityInfoDto dto) throws BusinessException {
    if (dto.getRebate() == null
        || StringUtils.isEmpty(dto.getStartDate()) || StringUtils.isEmpty(dto.getEndDate())) {
      return Response.fail("请输入完整信息...");
    }
    boolean flag =  awardActivityInfoService.isExistActivity(AwardActivity.ActivityName.INTRO);
    if(!flag){
      ListeningCustomSecurityUserDetails user = SpringSecurityUtils.getLoginUserDetails();
      dto.setCreateBy(user.getUsername());
      AwardActivityInfo info = awardActivityInfoService.addActivity(dto);
      if (info.getId() > 0) {
        return Response.success("操作成功...");
      } else {
        return Response.fail("操作失败...");
      }
    } else{
      return Response.fail("已存在有效的活动配置信息...");
    }
  }
  /**
   * 编辑配置
   */
  @RequestMapping(value = "/introduce/edit", method = RequestMethod.POST)
  @ResponseBody
  public Response editIntroConfig(HttpServletRequest request) throws BusinessException {
      String id = HttpWebUtils.getValue(request, "id");
      String rebate = HttpWebUtils.getValue(request, "rebate");
      String endDate = HttpWebUtils.getValue(request, "endDate");
      if (StringUtils.isAnyBlank(id,rebate,endDate)) {
          return Response.fail("请输入完整信息...");
        }
      
      Integer count = awardActivityInfoService.editActivity(id,rebate,endDate);
      if (count == 1) {
        return Response.success("操作成功...");
      } else {
        return Response.fail("操作失败...");
      }
  }
      
   

  /**
   * 关闭活动
   */
  @RequestMapping(value = "/introduce/delete", method = RequestMethod.POST)
  @ResponseBody
  public Response deleteIntro(Long id) {
    ListeningCustomSecurityUserDetails user = SpringSecurityUtils.getLoginUserDetails();
    awardActivityInfoService.updateUneffectiveActivity(id, user.getUsername());
    return Response.success("操作成功...");
  }

  /**
   * 转介绍统计页
   */
  @RequestMapping(value = "/introduce/statistic/index", method = RequestMethod.GET)
  public String introStatistics(){
    return "activity/introStatistic";
  }

  /**
   * 统计查询
   */
  @RequestMapping(value = "/introduce/statistic/list", method = RequestMethod.GET)
  @ResponseBody
  public ResponsePageIntroStaticBody<AwardBindRelStatisticVo> listIntroStatistic(ActivityBindRelStatisticQuery query){
    ResponsePageIntroStaticBody<AwardBindRelStatisticVo> response = new ResponsePageIntroStaticBody<>();
    try {
        response = awardDetailService.pageBindRelStatistic(query);
    } catch (BusinessException e) {
        LOGGER.error("统计查询失败！",e);
        response.setStatus(BaseConstants.CommonCode.FAILED_CODE);
    }
    return response;
  }
  
  @RequestMapping(value = "/introduce/test", method = RequestMethod.POST)
  @ResponseBody
  public Response getResponse(Integer days){
	  //近二十四小时
	  if(days == null || days == 0){
		  days = -1;
	  }
	  int count = awardBindRelService.getInviterUserCountByTime(days);
	  return Response.success("返回成功!",count);
  }
  
}
