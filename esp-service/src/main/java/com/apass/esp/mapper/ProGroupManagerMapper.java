package com.apass.esp.mapper;

import java.util.List;

import com.apass.esp.domain.entity.ProGroupManager;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface ProGroupManagerMapper extends GenericMapper<ProGroupManager, Long>{

	/**
	 * 根据活动的Id，获取所属的分组
	 * @return
	 */
	List<ProGroupManager> getGroupByActivityId(Long activityId);
}
