package com.apass.esp.service.feedback;
/**
 * 反馈意见操作service
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.common.model.QueryParams;
import com.apass.esp.domain.entity.FeedBack;
import com.apass.esp.domain.enums.FeedBackModule;
import com.apass.esp.domain.enums.FeedBackType;
import com.apass.esp.domain.vo.FeedBackVo;
import com.apass.esp.mapper.FeedBackMapper;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.utils.BaseConstants;
import com.apass.gfb.framework.utils.DateFormatUtil;
@Service
public class FeedBackService {
 
	@Autowired
	private FeedBackMapper feedbackMapper;
	
	/**
	 * 获取反馈信息列表
	 * @param query
	 * @return
	 */
	public ResponsePageBody<FeedBackVo> getFeedBackList(QueryParams query){
		ResponsePageBody<FeedBackVo> pageBody = new ResponsePageBody<FeedBackVo>();
		List<FeedBack> backList = feedbackMapper.pageEffectiveList(query);
		List<FeedBackVo> backVoList = new ArrayList<FeedBackVo>();
		
		backVoList=changFeedBack(backList);
		
		if(CollectionUtils.isEmpty(backList)){
			pageBody.setTotal(0);
		}else{
			pageBody.setTotal(feedbackMapper.count());
		}
		pageBody.setRows(backVoList);
		pageBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);
		return pageBody;
	}
	
	public Integer insert(FeedBack fb) {
		return feedbackMapper.insertSelective(fb);
	}
	public List<FeedBackVo> changFeedBack(List<FeedBack> list) {
		List<FeedBackVo> backVoList = new ArrayList<FeedBackVo>();
		for (FeedBack f : list) {
			FeedBackVo v = new FeedBackVo();
			v.setId(f.getId());
			v.setType(f.getType());
			if (FeedBackModule.SHOPPING.getCode().equals(f.getModule())) {
				v.setModule(FeedBackModule.SHOPPING.getMessage());
			} else if (FeedBackModule.CREDIT.getCode().equals(f.getModule())) {
				v.setModule(FeedBackModule.CREDIT.getMessage());
			}
			v.setPicture(f.getPicture());
			v.setComments(f.getComments());
			v.setCreateDate(DateFormatUtil.datetime2String(f.getCreateDate()));
			v.setFeedbackType(FeedBackType.valueOf(f.getFeedbackType().toUpperCase()).getMessage());
			v.setMobile(f.getMobile());
			backVoList.add(v);
		}
		return backVoList;
	}
}
