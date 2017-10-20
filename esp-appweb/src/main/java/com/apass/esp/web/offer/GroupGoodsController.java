package com.apass.esp.web.offer;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.vo.GroupManagerVo;
import com.apass.esp.service.offer.GroupManagerService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/activity/group")
public class GroupGoodsController {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupGoodsController.class);
	
	@Autowired
	private GroupManagerService groupManagerService;
	
	@RequestMapping("/getGroupAndGoods")
	@ResponseBody
	public Response getGroupAndGoodsByGroupId(@RequestBody Map<String, Object> paramMap){
		String activityId = CommonUtils.getValue(paramMap, "activityId");
		String bannerId = CommonUtils.getValue(paramMap, "bannerId");
		if(StringUtils.isBlank(activityId)){
			logger.error("活动编号不能为空!");
			return Response.fail("活动编号不能为空!");
		}
		Map<String,Object> maps = Maps.newHashMap();
		try {
			List<GroupManagerVo> groupList = groupManagerService.getGroupsAndGoodsByActivityId(activityId,bannerId);
			maps.put("groups", groupList);
			return Response.success("查询成功!", maps);
		} catch(BusinessException e){
			logger.error("business activityId :{}",e);
			return Response.fail(e.getErrorDesc());
		} catch (Exception e) {
			logger.error("exception activityId :{}",e);
			return Response.fail("活动查询失败!");
		}
	}
}
