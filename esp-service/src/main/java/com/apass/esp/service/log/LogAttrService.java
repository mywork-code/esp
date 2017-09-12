package com.apass.esp.service.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.LogAttr;
import com.apass.esp.mapper.LogAttrMapper;

@Service
public class LogAttrService {
	
	@Autowired
    private LogAttrMapper logAttrMapper;
	
	/**
	 * 根据外检ID，获取相关信息
	 * @param extId
	 * @return
	 */
	public List<LogAttr> selectLogAttr(String extId){
		
		return logAttrMapper.selectLogAttr(extId);
	}
	
	/**
	 * 根据外检ID，获取相关content信息
	 * @param extId
	 * @return
	 */
	public String getContent(String extId){
		
		List<LogAttr> logList = selectLogAttr(extId);
		
		StringBuffer buffer = new StringBuffer("");
		
		for (LogAttr log : logList) {
			buffer.append(log.getContent());
		}
		return buffer.toString();
	}
}
