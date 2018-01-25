package com.apass.esp.mapper;

import com.apass.esp.domain.entity.DataAppuserAnalysis;
import com.apass.esp.domain.vo.DataAppuserAnalysisVo;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface DataAppuserAnalysisMapper extends GenericMapper<DataAppuserAnalysis,Long>{
	
	/**
	 * 根据txnId获取某一个时间的数据
	 * @param txnId
	 * @return
	 */
	DataAppuserAnalysis getDataAnalysisByTxnId(DataAppuserAnalysisVo analysis);
}
