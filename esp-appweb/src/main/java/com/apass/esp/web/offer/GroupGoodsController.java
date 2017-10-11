package com.apass.esp.web.offer;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.vo.GroupManagerVo;
import com.apass.esp.service.offer.GroupManagerService;
import com.apass.gfb.framework.utils.CommonUtils;
import com.google.common.collect.Maps;

@Path("/activity/group")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class GroupGoodsController {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupGoodsController.class);
	
	@Autowired
	private GroupManagerService groupManagerService;
	
	@POST
    @Path("/getGroupAndGoods")
	public Response getGroupAndGoodsByGroupId(@RequestBody Map<String, Object> paramMap){
		String activityId = CommonUtils.getValue(paramMap, "activityId");
		if(StringUtils.isBlank(activityId)){
			return Response.fail("活动编号不能为空!");
		}
		Map<String,Object> maps = Maps.newHashMap();
		List<GroupManagerVo> groupList = groupManagerService.getGroupAndGoodsByActivityId(activityId);
		maps.put("groups", groupList);
	    return Response.success("查询成功!", maps);
	}
}
