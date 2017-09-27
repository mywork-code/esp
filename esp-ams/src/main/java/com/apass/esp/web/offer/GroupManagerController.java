package com.apass.esp.web.offer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.vo.GroupManagerVo;
import com.apass.esp.service.offer.GroupManagerService;
import com.apass.esp.utils.ValidateUtils;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.jwt.common.ListeningRegExpUtils;

/**
 * 分组管理
 */
@Controller
@RequestMapping(value = "/group/manager")
public class GroupManagerController {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupManagerController.class);
	
	@Autowired
	private GroupManagerService groupManagerService;
	
	@RequestMapping(value ="/list")
	public Response getGroupListByActivityId(String activityId){
		List<GroupManagerVo> voList = groupManagerService.getGroupByActivityId(activityId);
		return Response.successResponse(voList);
	}
	
	@ResponseBody
    @RequestMapping(value ="/add/save",method = RequestMethod.POST)
 	public Response groupAddSave(@RequestBody GroupManagerVo vo){
		try {
			validateParams(vo);
			groupManagerService.saveGroup(vo);
			return Response.success("添加成功!");
		}catch (BusinessException e) {
			return Response.fail(e.getErrorDesc());
		}catch (Exception e) {
			logger.error("新增活动配置失败", e);
			return Response.fail("新增活动配置失败");
		}
	}
	
	
	@ResponseBody
    @RequestMapping(value ="/edit/save",method = RequestMethod.POST)
 	public Response groupEditSave(@RequestBody GroupManagerVo vo){
		try {
			validateParams(vo);
			groupManagerService.editGroup(vo);
			return Response.success("修改成功!");
		}catch (BusinessException e) {
			return Response.fail(e.getErrorDesc());
		}catch (Exception e) {
			logger.error("修改分组信息失败", e);
			return Response.fail("修改分组信息失败");
		}
	}
	
	@ResponseBody
    @RequestMapping(value ="/edit/sort/save",method = RequestMethod.POST)
	public Response groupEditSortSave(@RequestBody GroupManagerVo vo){
		try {
			validateParams(vo);
			groupManagerService.editGroup(vo);
			return Response.success("修改成功!");
		}catch (BusinessException e) {
			return Response.fail(e.getErrorDesc());
		}catch (Exception e) {
			logger.error("修改分组信息失败", e);
			return Response.fail("修改分组信息失败");
		}
	}
	
	public void validateParams(GroupManagerVo vo) throws BusinessException{
		ValidateUtils.isNullObject(vo.getActivityId(),"活动Id不能为空!");
		ValidateUtils.isNotBlank(vo.getGroupName(), "分组名称不能为空!");
		if(!ListeningRegExpUtils.isChineseOrLetterOrMath(vo.getGroupName())){
 			throw new BusinessException("分组名称格式不正确，只能输入汉字、字母和数字,请重新输入");
 		}
 		if (!ListeningRegExpUtils.lengthValue(vo.getGroupName(), 1, 12)) {
            throw new BusinessException("分组名称格式不正确，最多只能输入6个汉字,请重新输入");
        }
 		ValidateUtils.isNullObject(vo.getOrderSort(), "显示顺序");
	}
	
	public void validateEditSortParams(GroupManagerVo vo) throws BusinessException{
		
	}
}
