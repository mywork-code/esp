package com.apass.esp.service.offer;

import com.apass.esp.domain.entity.ProGroupGoods;
import com.apass.esp.domain.entity.ProGroupManager;
import com.apass.esp.domain.vo.GroupManagerVo;
import com.apass.esp.mapper.ProGroupGoodsMapper;
import com.apass.esp.mapper.ProGroupManagerMapper;
import com.apass.gfb.framework.exception.BusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GroupManagerService {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupManagerService.class);
	
	@Autowired
	private ProGroupManagerMapper groupManagerMapper;
	
	@Autowired
	private ProGroupGoodsMapper groupGoodsMapper;
	
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
	public Integer saveGroup(GroupManagerVo vo,String userName){
		ProGroupManager manager = getProGroupManager(vo,true,userName);
		return groupManagerMapper.insertSelective(manager);
	}
	
	/**
	 * 保存修改分组信息
	 * @param vo
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class})
	public Integer editGroup(GroupManagerVo vo,String userName){
		ProGroupManager manager = getProGroupManager(vo,true,userName);
		return groupManagerMapper.updateByPrimaryKeySelective(manager);
	}
	
	@Transactional(rollbackFor = { Exception.class})
	public Integer deleteGroup(Long id) throws BusinessException{
		if(null == id){
			throw new BusinessException("分组编号不能为空!");
		}
		List<ProGroupGoods> goodsList = groupGoodsMapper.selectGoodsByGroupId(id);
		if(CollectionUtils.isNotEmpty(goodsList)){
			logger.error("分组编号为{}下存在关联商品，不能删除!",id);
			throw new BusinessException("该分组下存在关联的商品，不能删除!");
		}
		return groupManagerMapper.deleteByPrimaryKey(id);
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
	
	public ProGroupManager getProGroupManager(GroupManagerVo vo,boolean bl,String userName){
		
		ProGroupManager group = new ProGroupManager();
		group.setActivityId(vo.getActivityId());
		group.setGoodsSum(vo.getGoodsSum());
		group.setGroupName(vo.getGroupName());
		group.setOrderSort(vo.getOrderSort());
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
