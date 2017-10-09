package com.apass.esp.service.offer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.apass.esp.domain.dto.offo.ActivityfgDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.enums.ActivityStatus;
import com.apass.esp.domain.enums.ActivityType;
import com.apass.esp.domain.vo.ActivityCfgVo;
import com.apass.esp.mapper.ProActivityCfgMapper;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.BaseConstants;
import com.apass.gfb.framework.utils.DateFormatUtil;

@Service
public class ActivityCfgService {
	
	@Autowired
	private ProActivityCfgMapper activityCfgMapper;

	public ProActivityCfg getById(Long activityId){
		return activityCfgMapper.selectByPrimaryKey(activityId);
	}

	
	/**
	 * 获取活动配置信息
	 * @param query
	 * @return
	 * @throws BusinessException 
	 */
	public ResponsePageBody<ActivityCfgVo> getActivityCfgListPage(ActivityfgDto activityfgDto) throws BusinessException{
		ResponsePageBody<ActivityCfgVo> pageBody = new ResponsePageBody<ActivityCfgVo>();
		List<ProActivityCfg> configList = activityCfgMapper.getActivityCfgListPage(activityfgDto);
		Integer count = activityCfgMapper.getActivityCfgListPageCount();
		
		List<ActivityCfgVo> configVoList = getPoToVoList(configList);
		
		pageBody.setTotal(count);
		pageBody.setRows(configVoList);
		pageBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);
		return pageBody;
	}
	
	/**
	 * 保存添加活动配置信息
	 */
	@Transactional(rollbackFor = { Exception.class})
	public Integer saveActivity(ActivityCfgVo vo){
		ProActivityCfg record = getActivityCfg(vo,true);
		return activityCfgMapper.insertSelective(record);
	}
	
	/**
	 * 保存编辑活动配置信息
	 */
	@Transactional(rollbackFor = { Exception.class})
	public Integer editActivity(ActivityCfgVo vo){
		ProActivityCfg record = getActivityCfg(vo,false);
		return activityCfgMapper.updateByPrimaryKeySelective(record);
	}
	
	public ActivityCfgVo getActivityCfgVo(String id){
		ProActivityCfg cfg = activityCfgMapper.selectByPrimaryKey(Long.parseLong(id));
		return ActivityCfgPoToVo(cfg);
	}
	/**
	 * 添加或者修改 保存到数据库
	 * @param vo
	 * @param bl
	 * @return
	 */
	public ProActivityCfg getActivityCfg(ActivityCfgVo vo,boolean bl){
		ProActivityCfg record = new ProActivityCfg();
		record.setActivityName(vo.getActivityName());
		record.setActivityType(vo.getActivityType());
		record.setDiscountAmonut1(vo.getDiscount1());
		record.setDiscountAmount2(vo.getDiscount2());
		record.setEndTime(DateFormatUtil.string2date(vo.getEndTime(),""));
		record.setOfferSill1(vo.getOfferSill1());
		record.setOfferSill2(vo.getOfferSill2());
		record.setStartTime(DateFormatUtil.string2date(vo.getStartTime(),""));
		if(bl){
			record.setCreateDate(new Date());
			record.setCreateUser(vo.getUserName());
		}
		record.setUpdateDate(new Date());
		record.setUpdateUser(vo.getUserName());
		record.setId(vo.getId());
		return record;
	}
	
	/**
	 * po 2 vo
	 * @param configList
	 * @return
	 */
	public List<ActivityCfgVo> getPoToVoList(List<ProActivityCfg> configList){
		
		List<ActivityCfgVo> voList = new ArrayList<ActivityCfgVo>();
		for (ProActivityCfg cfg : configList) {
			voList.add(ActivityCfgPoToVo(cfg));
		}
		return voList;
		
	}
	
	/**
	 * po 2 vo
	 * @param cfg
	 * @return
	 */
	public ActivityCfgVo ActivityCfgPoToVo(ProActivityCfg cfg){
		ActivityCfgVo vo = new ActivityCfgVo();
		if(null == cfg){
			return vo;
		}
		vo.setId(cfg.getId());
		vo.setActivityName(cfg.getActivityName());
		vo.setActivityType(ActivityType.getMessage(cfg.getActivityType()));
		vo.setDiscount1(cfg.getDiscountAmonut1());
		vo.setDiscount2(cfg.getDiscountAmount2());
		vo.setEndTime(DateFormatUtil.dateToString(cfg.getEndTime(), ""));
		vo.setStartTime(DateFormatUtil.dateToString(cfg.getStartTime(), ""));
		vo.setOfferSill1(cfg.getOfferSill1());
		vo.setOfferSill2(cfg.getOfferSill2());
		vo.setStatus(getActivityStatus(cfg).getMessage());
		
		return vo;
	}
	/**
	 * 判断活动的状态
	 * @param cfg
	 * @return
	 */
	public ActivityStatus getActivityStatus(ProActivityCfg cfg){
		Date startTime = cfg.getStartTime();
		Date endTime = cfg.getEndTime();
		Date now = new Date();
		if(null == startTime || null == endTime){
			return ActivityStatus.NO;
		}
		if(startTime.getTime() > now.getTime()){
			return ActivityStatus.NO;
		}
		
		if(startTime.getTime() <= now.getTime() && endTime.getTime() >= now.getTime()){
			return ActivityStatus.PROCESSING;
		}
	    return ActivityStatus.END;
	}
}
