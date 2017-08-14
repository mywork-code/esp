package com.apass.esp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.apass.esp.domain.entity.SearchKeys;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface SearchKeysMapper extends GenericMapper<SearchKeys, Long> {
	
	/**
	 * 获取热门搜索
	 * @return
	 */
	public List<SearchKeys> hotSearch(@Param("startDate")String startDate,@Param("endDate")String endDate);
	
	/**
	 * 获取10条常用搜素
	 * @return
	 */
	public List<SearchKeys> commonSearch(String userId);
	
	/**
	 * 删除keys
	 * @return
	 */
	public Integer deleteSearchKey(Long keyId);
}
