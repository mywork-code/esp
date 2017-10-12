package com.apass.esp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.apass.esp.domain.entity.ProGroupManager;
import com.apass.esp.domain.query.GroupQuery;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface ProGroupManagerMapper extends GenericMapper<ProGroupManager, Long>{

	/**
	 * 根据活动的Id，获取所属的分组
	 * @return
	 */
	List<ProGroupManager> getGroupByActivityId(@Param("activityId")Long activityId);
	
	List<ProGroupManager> getGroupByActIdListPage(GroupQuery group);
	
	Integer getGroupByActIdListPageCount(GroupQuery group);
	
	Long getMaxOrderSort(@Param("activityId") Long activityId);
}
