package com.apass.esp.mapper;

import java.util.List;

import com.apass.esp.domain.entity.HomeConfig;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface HomeConfigMapper extends GenericMapper<HomeConfig, Long> {
	
    List<HomeConfig> getHomeConfigListPage();
	
	Integer getHomeConfigListPageCount();
}

