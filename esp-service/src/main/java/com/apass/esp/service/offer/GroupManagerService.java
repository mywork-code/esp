package com.apass.esp.service.offer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.ProGroupManager;
import com.apass.esp.domain.vo.GroupManagerVo;
import com.apass.esp.mapper.ProGroupManagerMapper;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;

@Service
public class GroupManagerService {
	
	@Autowired
	private ProGroupManagerMapper groupManagerMapper;
	
	/**
	 * 根据活动配置的id，获取活动所属的分组
	 * @param activityId
	 * @return
	 * @throws BusinessException
	 */
	public List<GroupManagerVo> getGroupByActivityId(String activityId){
		List<ProGroupManager> groupList =  groupManagerMapper.getGroupByActivityId(Long.parseLong(activityId));
		return getGroupManageVoList(groupList);
	}
	
	/**
	 * 保存新添加分组信息
	 * @param vo
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class})
	public Integer saveGroup(GroupManagerVo vo){
		ProGroupManager manager = getProGroupManager(vo,true);
		return groupManagerMapper.insertSelective(manager);
	}
	
	/**
	 * 保存修改分组信息
	 * @param vo
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class})
	public Integer editGroup(GroupManagerVo vo){
		ProGroupManager manager = getProGroupManager(vo,true);
		return groupManagerMapper.updateByPrimaryKeySelective(manager);
	}
	
	public List<GroupManagerVo> getGroupManageVoList(List<ProGroupManager> groupList){
		List<GroupManagerVo> voList = new ArrayList<GroupManagerVo>();
		for (ProGroupManager pro : groupList) {
			voList.add(getGroupManagerVo(pro));
		}
		return voList;
	}
	
	public GroupManagerVo getGroupManagerVo(ProGroupManager pro){
		GroupManagerVo vo = new GroupManagerVo();
		vo.setActivityId(pro.getActivityId());
		vo.setGoodsSum(pro.getGoodsSum());
		vo.setGroupName(pro.getGroupName());
		vo.setId(pro.getId());
		vo.setOrderSort(pro.getOrderSort());
		return vo;
	}
	
	public ProGroupManager getProGroupManager(GroupManagerVo vo,boolean bl){
		
		ProGroupManager group = new ProGroupManager();
		group.setActivityId(vo.getActivityId());
		group.setGoodsSum(vo.getGoodsSum());
		group.setGroupName(vo.getGroupName());
		group.setOrderSort(vo.getOrderSort());
		String userName = SpringSecurityUtils.getLoginUserDetails().getUsername();
		if(bl){
			group.setCreateDate(new Date());
			group.setCreateUser(userName);
		}
		group.setUpdateDate(new Date());
		group.setUpdateUser(userName);
		group.setId(vo.getId());
		return group;
	}
}
