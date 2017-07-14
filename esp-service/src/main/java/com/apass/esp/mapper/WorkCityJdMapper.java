package com.apass.esp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.apass.esp.domain.dto.WorkCityJdDto;
import com.apass.esp.domain.entity.WorkCityJd;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface WorkCityJdMapper extends GenericMapper<WorkCityJd, Long>{
	/**
	 * 查询所有的城市id
	 * @return
	 */
	List<String> selectAllCity();
	List<String> selectDistrict();
	Integer selectByCode(String code);
	
	/**
	 * 根据省份/市/县/乡镇名称，查询编码
	 */
	WorkCityJd selectByNameAndParent(WorkCityJdDto cityJdDto);
	
}
