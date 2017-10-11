package com.apass.esp.web.offer;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.offo.ActivityfgDto;
import com.apass.esp.domain.enums.ActivityType;
import com.apass.esp.domain.vo.ActivityCfgVo;
import com.apass.esp.service.offer.ActivityCfgService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.esp.utils.ValidateUtils;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.jwt.common.ListeningRegExpUtils;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.google.common.collect.Maps;

/**
 * 活动配置
 */
@Controller
@RequestMapping(value = "/activity/cfg")
public class ActivityCfgController {

	/**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ActivityCfgController.class);
	
    @Autowired
    private ActivityCfgService activityCfgService;
    /**
     * 活动配置
     * @return
     */
 	@RequestMapping(value = "/activity")
    public String activityConfig() {
      return "activitycfg/activity";
    }
 	  /**
     * 商品池初始化配置
     * @return
     */
 	@RequestMapping(value = "/importInit")
    public ModelAndView activityimportFileConfig(ActivityCfgVo activityCfgVo) {
		ModelAndView mv = new ModelAndView("activitycfg/activityImportFile");
		mv.addObject("activityCfgVo",activityCfgVo);
		return mv;
    }
 	/**
     * 活动配置分页json
     */
    @ResponseBody
    @RequestMapping(value ="/list")
    public ResponsePageBody<ActivityCfgVo> ActivityCfgPageList(ActivityfgDto activityfgDto) {
	    ResponsePageBody<ActivityCfgVo> respBody = new ResponsePageBody<ActivityCfgVo>();

		try {
			ResponsePageBody<ActivityCfgVo> pagination= activityCfgService.getActivityCfgListPage(activityfgDto);
            respBody.setTotal(pagination.getTotal());
            respBody.setRows(pagination.getRows());
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
        	logger.error("活动配置查询失败!",e);
            respBody.setMsg("活动配置查询失败");
        }
        return respBody;
    }
    
    /**
     * 活动配置
     * @return
     */
 	@RequestMapping(value = "/add")
    public String activityAddConfig() {
       return "activitycfg/activityAdd";
    }
 	/**
 	 * 新增活动保存
 	 * @return
 	 */
 	@ResponseBody
    @RequestMapping(value ="/add/save",method = RequestMethod.POST)
 	public Response activityAddSave(ActivityCfgVo vo){
 		try {
 			validateParams(vo, false);
 			vo.setUserName(SpringSecurityUtils.getLoginUserDetails().getUsername());
 			activityCfgService.saveActivity(vo);
 			return Response.success("添加成功");
		} catch (BusinessException e) {
			return Response.fail(e.getErrorDesc());
		}catch (Exception e) {
			logger.error("新增活动配置失败", e);
			return Response.fail("新增活动配置失败");
		}
 	}
 	
 	@RequestMapping(value = "/edit")
    public ModelAndView activityEditConfig(String id) {
 	   Map<String, Object> map = Maps.newHashMap();
       map.put("vo", activityCfgService.getActivityCfgVo(id));
       return new ModelAndView("activitycfg/activityEdit",map);
    }
 	
 	@ResponseBody
    @RequestMapping(value ="/edit/save",method = RequestMethod.POST)
 	public Response activityEditSave(ActivityCfgVo vo){
 		try {
 			validateParams(vo, true);
 			vo.setUserName(SpringSecurityUtils.getLoginUserDetails().getUsername());
 			activityCfgService.editActivity(vo);
 			return Response.success("编辑成功");
		} catch (BusinessException e) {
			return Response.fail(e.getErrorDesc());
		}catch (Exception e) {
			logger.error("编辑活动配置失败", e);
			return Response.fail("编辑活动配置失败");
		}
 	}
 	
 	public void validateParams(ActivityCfgVo vo,boolean bl) throws BusinessException{
 		
 		String activityName = vo.getActivityName();
 		ValidateUtils.isNotBlank(activityName, "请填写活动名称！");
 		if(!ListeningRegExpUtils.isChineseOrLetterOrMath(activityName)){
 			throw new BusinessException("活动名称格式不正确，只能输入汉字、字母和数字,请重新输入");
 		}
 		if (!ListeningRegExpUtils.lengthValue(activityName, 1, 12)) {
            throw new BusinessException("活动名称格式不正确，最多只能输入6个汉字,请重新输入");
        }
 		
 		String startTime = vo.getStartTime();
 		ValidateUtils.isNotBlank(startTime, "请填写开始时间！");
 		String endTime = vo.getEndTime();
 		ValidateUtils.isNotBlank(endTime, "请填写结束时间！");
 		
 		Date start = DateFormatUtil.string2date(startTime, "");
 		Date end = DateFormatUtil.string2date(endTime, "");
 		
 		if(start.getTime() >= end.getTime()){
 			throw new BusinessException("开始时间应大于结束时间，请重新填写！");
 		}
 		
 		String activityType = vo.getActivityType();
 		ValidateUtils.isNotBlank(activityType, "请选择活动类型！");
 		
 		if(StringUtils.equalsIgnoreCase(activityType, ActivityType.LESS.getCode())){
 			ValidateUtils.isNullObject(vo.getOfferSill1(), "请填写第一个优惠门槛！");
 			ValidateUtils.isNullObject(vo.getOfferSill2(), "请填写第二个优惠门槛！");
 			ValidateUtils.isNullObject(vo.getDiscount1(), "请填写第一个优惠金额！");
 			ValidateUtils.isNullObject(vo.getDiscount2(), "请填写第二个优惠金额！");
 			
 			if(vo.getOfferSill1() == vo.getOfferSill2()){
 				throw new BusinessException("优惠门槛不能相同，请重新填写！");
 			}
 		}
 		
 		if(bl){
 			ValidateUtils.isNullObject(vo.getId(), "活动编号不能为空!");
 		}
 	}
}
