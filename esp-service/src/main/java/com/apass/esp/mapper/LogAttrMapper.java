package com.apass.esp.mapper;

import java.util.List;

import com.apass.esp.domain.entity.LogAttr;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface LogAttrMapper extends GenericMapper<LogAttr, Long> {

	/**
	 * 批量插入数据
	 * @param attrList
	 */
	public void insertAttr(List<LogAttr> list);
	
	/**
	 * 根据外部的id，获取相关的数据
	 * @param extId
	 * @return
	 */
	public List<LogAttr> getLogAttr(String extId);
}
