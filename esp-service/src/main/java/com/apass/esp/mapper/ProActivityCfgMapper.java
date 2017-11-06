package com.apass.esp.mapper;

import java.util.List;
import java.util.Map;

import com.apass.esp.domain.dto.offo.ActivityfgDto;
import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface ProActivityCfgMapper extends GenericMapper<ProActivityCfg, Long>{
	/**
	 * 分页
	 * @return
	 */
    
	List<ProActivityCfg> getActivityCfgListPage(ActivityfgDto activityfgDto);
	/**
	 * 总条数
	 * @return
	 */
	Integer getActivityCfgListPageCount(ActivityfgDto activityfgDto);

	//根据活动名称查询是否存在活动
	ProActivityCfg selectProActivityCfgByName(String activityName);

	List<ProActivityCfg> selectProActivityCfgByEntity(Map<String, Object> map);
}
