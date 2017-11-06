package com.apass.esp.web.offer;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.apass.esp.domain.dto.ProcouponRelListVo;
import com.apass.esp.domain.dto.ProcouponRelVoList;
import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.entity.ProCouponRel;
import com.apass.esp.domain.enums.ActivityCfgCoupon;
import com.apass.esp.domain.vo.ActivityCfgForEditVo;
import com.apass.esp.service.offer.CouponRelService;
import com.apass.gfb.framework.jwt.common.EncodeUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.apass.gfb.framework.log.LogAnnotion;
import com.apass.gfb.framework.log.LogValueTypeEnum;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.DateFormatUtil;

/**
 * 活动配置
 */
@Controller
@RequestMapping(value = "/activity/cfg")
public class ActivityCfgController {

	/**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityCfgController.class);
	
    @Autowired
    private ActivityCfgService activityCfgService;
	@Autowired
	private CouponRelService couponRelService;
    /**
     * 活动配置
     * @return
     */
 	@RequestMapping(value = "/activity")
    public String activityConfig() {
      return "activitycfg/activity";
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
        	LOGGER.error("活动配置查询失败!",e);
            respBody.setMsg("活动配置查询失败");
        }
        return respBody;
    }
    
    /**
     * 活动配置:添加活动
     * @return
     */
 	@RequestMapping(value = "/add")
    public ModelAndView activityAddConfig(String id) {
		ModelAndView mv = null;
		if(StringUtils.isBlank(id)){
			mv = new ModelAndView("activitycfg/activityAdd");
		}else{
			mv = new ModelAndView("activitycfg/activityAddForEdit");
			mv.addObject("activityId",id);
		}

        return mv;
    }
	/**
     * 活动配置:编辑活动
     * @return
     */
 	@RequestMapping(value = "/edit")
    public ModelAndView activityEditConfig(ActivityCfgVo activityCfgVo) {
		ModelAndView mv = new ModelAndView("activitycfg/activityEdit");
		ActivityCfgVo cfg = activityCfgService.getActivityCfgVo(activityCfgVo.getId()+"");
		if(StringUtils.equals("无优惠",cfg.getActivityType())){
			cfg.setActivityType("N");
		}else if(StringUtils.equals("满减",cfg.getActivityType())){
			cfg.setActivityType("Y");
		}
		mv.addObject("activityCfgVo",cfg);
		return mv;
    }
 	/**
 	 * 新增活动保存
 	 * @return
 	 */
 	@ResponseBody
    @RequestMapping(value ="/add/save",method = RequestMethod.POST)
 	@LogAnnotion(operationType = "添加活动信息", valueType = LogValueTypeEnum.VALUE_DTO)
 	public Response activityAddSave(@RequestBody String request){
		String requestDecode = EncodeUtils.urlDecode(request);
		requestDecode = requestDecode.substring(0,requestDecode.length()-1);
		ActivityCfgVo vo = GsonUtils.convertObj(requestDecode, ActivityCfgVo.class);

 		try {
 			validateParams(vo, false);
 			vo.setUserName(SpringSecurityUtils.getLoginUserDetails().getUsername());
 			Long activityId = activityCfgService.saveActivity(vo);

 			return Response.success("添加成功",activityId);
		} catch (BusinessException e) {
			LOGGER.error("新增活动配置失败", e);
			return Response.fail(e.getErrorDesc());
		}catch (Exception e) {
			LOGGER.error("新增活动配置失败", e);
			return Response.fail("新增活动配置失败");
		}
 	}

	@ResponseBody
	@RequestMapping(value ="/editpro/update",method = RequestMethod.POST)
	public Response editSavePro(@RequestBody String request){
		try{
			String requestDecode = EncodeUtils.urlDecode(request);
			requestDecode = requestDecode.substring(0,requestDecode.length()-1);
			ProcouponRelListVo procouponRelListVo = GsonUtils.convertObj(requestDecode,ProcouponRelListVo.class);

			if(procouponRelListVo != null){
				List<ProCouponRel> relList = procouponRelListVo.getRelList();
				if(CollectionUtils.isNotEmpty(relList)){
					Iterator<ProCouponRel> iteratorRel = relList.iterator();
					while(iteratorRel.hasNext()){
						ProCouponRel proRel1 = iteratorRel.next();
						//根据id获取remainNum值
						ProCouponRel proRel2 = couponRelService.getcoupoRelByPrimary(proRel1.getId());
						Integer subtractNum = proRel2.getTotalNum()-proRel2.getRemainNum();
						proRel1.setRemainNum(proRel1.getTotalNum()-subtractNum);
						couponRelService.updateProCouponRel(proRel1);
					}
				}

			}
		}catch (Exception e){
			LOGGER.error("修改优惠券总数失败", e);
			return Response.fail("修改优惠券总数失败");
		}


		return Response.success("修改优惠券总数成功");
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
			LOGGER.error("编辑活动配置失败", e);
			return Response.fail("编辑活动配置失败");
		}
 	}

	@ResponseBody
	@RequestMapping(value ="/edit/find",method = RequestMethod.POST)
	public Response activityEditFind(ActivityCfgVo vo){
		ActivityCfgForEditVo activityCfgForEditVo = new ActivityCfgForEditVo();
		try{
			//根据活动id去活动表查询数据
			ProActivityCfg pro = activityCfgService.getById(vo.getId());

			converToActivityCfgForEditVo(activityCfgForEditVo,pro);

			if(StringUtils.isNotBlank(pro.getCoupon())){
				if(StringUtils.equals(pro.getCoupon(),ActivityCfgCoupon.COUPON_Y.getCode())){
					//如果使用优惠券，去优惠券与活动关系表中查询优惠券想着信息
					List<ProCouponRel> couponRelList = couponRelService.getCouponRelList(String.valueOf(vo.getId()));
					activityCfgForEditVo.setProCouponRels(couponRelList);
				}
			}

		}catch (Exception e){
			LOGGER.error("活动编辑回显查询失败",e);
			return Response.success("活动编辑回显查询失败");
		}
		return Response.success("活动编辑回显查询成功",activityCfgForEditVo);
	}


	private void converToActivityCfgForEditVo(ActivityCfgForEditVo activityCfgForEditVo, ProActivityCfg pro) {
		if(pro != null){
			activityCfgForEditVo.setId(pro.getId());
			activityCfgForEditVo.setActivityName(pro.getActivityName());
			activityCfgForEditVo.setActivityType(pro.getActivityType());
			activityCfgForEditVo.setStartTime(pro.getStartTime());
			activityCfgForEditVo.setEndTime(pro.getEndTime());
			activityCfgForEditVo.setOfferSill1(pro.getOfferSill1());
			activityCfgForEditVo.setOfferSill2(pro.getOfferSill2());
			activityCfgForEditVo.setDiscountAmonut1(pro.getDiscountAmonut1());
			activityCfgForEditVo.setDiscountAmount2(pro.getDiscountAmount2());
			activityCfgForEditVo.setCreateUser(pro.getCreateUser());
			activityCfgForEditVo.setCreatedTime(pro.getCreatedTime());
			activityCfgForEditVo.setUpdatedTime(pro.getUpdatedTime());
			activityCfgForEditVo.setUpdateUser(pro.getUpdateUser());
			activityCfgForEditVo.setCoupon(pro.getCoupon());
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
 		
 		if(end.getTime() < new Date().getTime()){
 			throw new BusinessException("活动结束时间应大于系统当前时间，请重新填写");
 		}
 		String activityType = vo.getActivityType();
 		ValidateUtils.isNotBlank(activityType, "请选择活动类型！");
 		
 		if(StringUtils.equalsIgnoreCase(activityType, ActivityType.LESS.getCode())){
 			ValidateUtils.isNullObject(vo.getOfferSill1(), "请填写第一个优惠门槛！");
 			ValidateUtils.isNullObject(vo.getOfferSill2(), "请填写第二个优惠门槛！");
 			ValidateUtils.isNullObject(vo.getDiscount1(), "请填写第一个优惠金额！");
 			ValidateUtils.isNullObject(vo.getDiscount2(), "请填写第二个优惠金额！");
 			if(vo.getOfferSill1() <= 0){
 				throw new BusinessException("第一个优惠门槛值应大于0！");
 			}
 			if(vo.getOfferSill2() <= 0){
 				throw new BusinessException("第二个优惠门槛值应大于0！");
 			}
 			if(vo.getDiscount1() <= 0){
 				throw new BusinessException("请填写第一个优惠金额应大于0！");
 			}
 			if(vo.getDiscount2() <= 0){
 				throw new BusinessException("请填写第二个优惠金额应大于0！");
 			}
 			if(vo.getOfferSill1() == vo.getOfferSill2()){
 				throw new BusinessException("优惠门槛不能相同，请重新填写！");
 			}
 			if(vo.getDiscount1() >= vo.getOfferSill1()){
 				throw new BusinessException("第一个优惠金额要小于第一个优惠门槛金额！");
 			}
 			
 			if(vo.getDiscount2() >= vo.getOfferSill2()){
 				throw new BusinessException("第二个优惠金额要小于第二个优惠门槛金额！");
 			}
 		}
 		
 		if(bl){
 			ValidateUtils.isNullObject(vo.getId(), "活动编号不能为空!");
 		}
 	}
}
