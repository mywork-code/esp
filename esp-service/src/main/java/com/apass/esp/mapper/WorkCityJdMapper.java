package com.apass.esp.mapper;

import java.util.List;

import com.apass.esp.domain.entity.WorkCityJd;
import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface WorkCityJdMapper extends GenericMapper<WorkCityJd, Long>{
	/**
	 * 查询所有的城市id
	 * @return
	 */
	List<String> selectAllCity();
	List<String> selectDistrict();
	Integer selectByCode(String code);
}
