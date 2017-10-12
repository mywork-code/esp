package com.apass.esp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.apass.esp.common.model.QueryParams;
import com.apass.esp.domain.entity.HomeConfig;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface HomeConfigMapper extends GenericMapper<HomeConfig, Long> {
	
	/**
	 * 分页
	 * @return
	 */
    List<HomeConfig> getHomeConfigListPage(QueryParams query);
    
	/**
	 * 总条数
	 * @return
	 */
	Integer getHomeConfigListPageCount();
	
	/**
	 * 判断开始时间是否在数据库中已存在的时间区间内
	 * @param startTime
	 * @return
	 */
	List<HomeConfig> getContainsTimesList(@Param("time") String time,@Param("id") Long id);
	
	Integer getContainsTimeCount(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("id") Long id);
}

