package com.apass.esp.service.appuser;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.UserSessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.DataAppuserAnalysis;
import com.apass.esp.domain.vo.UserSessionVo;
import com.apass.esp.mapper.DataAppuserAnalysisMapper;
import com.apass.gfb.framework.utils.DateFormatUtil;

@Component
public class DataAppuserAnalysisService {

	private static final Logger logger = LoggerFactory.getLogger(DataAppuserAnalysisService.class);

	@Autowired
	private DataAppuserAnalysisMapper analysisMapper;
	
	@Transactional(rollbackFor = { Exception.class })
	public void insertAnalysis(UserSessionVo vo,String type,String platformids){
		DataAppuserAnalysis analysis = new DataAppuserAnalysis();
		Date date = new Date();
		String dataStr = DateFormatUtil.dateToString(new Date(), "yyyyMMdd");
		analysis.setNewuser(vo.getNewuser());
		analysis.setSession(vo.getSession());
		analysis.setCreatedTime(date);
		analysis.setUpdatedTime(date);
		analysis.setType(Byte.valueOf(type));
		analysis.setPlatformids(Byte.valueOf(platformids));
		analysis.setTxnId(dataStr + vo.getHourly().split(":")[0]);
		analysisMapper.insertSelective(analysis);
	}
	
}
