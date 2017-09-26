package com.apass.esp.service.offer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.HomeConfig;
import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.enums.ActivityStatus;
import com.apass.esp.domain.enums.ActivityType;
import com.apass.esp.domain.vo.ActivityCfgVo;
import com.apass.esp.domain.vo.HomeConfigVo;
import com.apass.esp.mapper.ProActivityCfgMapper;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.BaseConstants;
import com.apass.gfb.framework.utils.DateFormatUtil;

@Service
public class ActivityCfgService {
	
	@Autowired
	private ProActivityCfgMapper activityCfgMapper;
	
	/**
	 * 获取活动配置信息
	 * @param query
	 * @return
	 * @throws BusinessException 
	 */
	public ResponsePageBody<ActivityCfgVo> getActivityCfgListPage() throws BusinessException{
		ResponsePageBody<ActivityCfgVo> pageBody = new ResponsePageBody<ActivityCfgVo>();
		List<ProActivityCfg> configList = activityCfgMapper.getActivityCfgListPage();
		Integer count = activityCfgMapper.getActivityCfgListPageCount();
		
		List<ActivityCfgVo> configVoList = getPoToVoList(configList);
		
		pageBody.setTotal(count);
		pageBody.setRows(configVoList);
		pageBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);
		return pageBody;
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
		vo.setId(cfg.getId());
		vo.setActivityName(cfg.getActivityName());
		vo.setActivityType(ActivityType.getMessage(cfg.getActivityType()));
		vo.setDiscountAmonut1(cfg.getDiscountAmonut1());
		vo.setDiscountAmount2(cfg.getDiscountAmount2());
		vo.setEndTime(DateFormatUtil.dateToString(cfg.getEndTime(), ""));
		vo.setStartTime(DateFormatUtil.dateToString(cfg.getStartTime(), ""));
		vo.setOfferSill1(cfg.getOfferSill1());
		vo.setOfferSill2(cfg.getOfferSill2());
		vo.setStatus(getActivityStatus(cfg));
		
		return vo;
	}
	/**
	 * 判断活动的状态
	 * @param cfg
	 * @return
	 */
	public String getActivityStatus(ProActivityCfg cfg){
		Date startTime = cfg.getStartTime();
		Date endTime = cfg.getEndTime();
		Date now = new Date();
		if(null == startTime || null == endTime){
			return ActivityStatus.END.getMessage();
		}
		if(startTime.getTime() > now.getTime()){
			return ActivityStatus.NO.getMessage();
		}
		
		if(startTime.getTime() <= now.getTime() && endTime.getTime() >= now.getTime()){
			return ActivityStatus.PROCESSING.getMessage();
		}
	    return ActivityStatus.END.getMessage();
	}
}
