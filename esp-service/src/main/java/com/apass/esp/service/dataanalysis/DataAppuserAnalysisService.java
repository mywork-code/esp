package com.apass.esp.service.dataanalysis;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.DataAppuserAnalysis;
import com.apass.esp.domain.vo.DataAppuserAnalysisVo;
import com.apass.esp.domain.vo.DataAppuserAnalysisDto;
import com.apass.esp.mapper.DataAppuserAnalysisMapper;
import com.apass.gfb.framework.utils.DateFormatUtil;
@Service
public class DataAppuserAnalysisService {
	@Autowired
	private DataAppuserAnalysisMapper analysisMapper;
	/**
	 * CREATE
	 * @param entity
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public Integer createdEntity(DataAppuserAnalysis entity){
		return analysisMapper.insertSelective(entity);
	}
	/**
	 * DELETE
	 * @param id
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public Integer deleteEntity(Long id){
		return analysisMapper.deleteByPrimaryKey(id);
	}
	/**
	 * DELETE
	 * @param entity
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public Integer deleteEntity(DataAppuserAnalysis entity){
		return analysisMapper.deleteByPrimaryKey(entity.getId());
	}
	/**
	 * UPDATE
	 * @param entity
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public Integer updateEntity(DataAppuserAnalysis entity){
		return analysisMapper.updateByPrimaryKeySelective(entity);
	}
	
	/**
	 * 此方法对应的是每天插入一次数据
	 * @param vo
	 * @param type
	 * @param platformids
	 */
	@Transactional(rollbackFor = { Exception.class,RuntimeException.class })
	public void insertAnalysisData(DataAppuserAnalysisDto retention){
		if(null != retention){
			DataAppuserAnalysis analysis = new DataAppuserAnalysis();
			Date date = new Date();
			analysis.setActiveuser(retention.getActiveuser());
			analysis.setAvgsessionlength(retention.getAvgsessionlength());
			analysis.setBounceuser(retention.getBounceuser());
			analysis.setCreatedTime(date);
			analysis.setMau(retention.getMau());
			analysis.setNewuser(retention.getNewuser());
			analysis.setPlatformids(retention.getPlatformids());
			analysis.setSession(retention.getSession());
			analysis.setSessionlength(retention.getSessionlength());
			analysis.setTotaluser(retention.getTotaluser());
			analysis.setTxnId(retention.getDaily().replace("-", ""));
			analysis.setType(retention.getType());
			analysis.setUpdatedTime(date);
			analysis.setVersionupuser(retention.getVersionupuser());
			analysis.setWau(retention.getWau());
			analysisMapper.insertSelective(analysis);
		}
	}
	
	/**
	 * 此方法对应的是每小时更新或者插入的数据
	/**
	 * insertAnalysis
	 * @param vo
	 * @param type
	 * @param platformids
	 */
	@Transactional(rollbackFor = { Exception.class,RuntimeException.class })
	public void insertAnalysis(DataAppuserAnalysisDto vo){
		DataAppuserAnalysis analysis = new DataAppuserAnalysis();
		Date date = new Date();
		String dataStr = DateFormatUtil.dateToString(new Date(), "yyyyMMdd");
		String txnId = dataStr + vo.getHourly().split(":")[0];
		
		if(null == vo.getId()){
			analysis.setCreatedTime(date);
			analysis.setType(vo.getType());
			analysis.setPlatformids(vo.getPlatformids());
			analysis.setTxnId(txnId);
			analysis.setNewuser(vo.getNewuser());
			analysis.setSession(vo.getSession());
			analysis.setUpdatedTime(date);
			analysisMapper.insertSelective(analysis);
		}else{
			analysis = analysisMapper.getDataAnalysisByTxnId(new DataAppuserAnalysisVo(txnId, vo.getPlatformids().toString(), vo.getType().toString()));
			analysis.setNewuser(vo.getNewuser());
			analysis.setSession(vo.getSession());
			analysis.setUpdatedTime(date);
			analysisMapper.updateByPrimaryKeySelective(analysis);
		}
	}
	/**
	 * getDataAnalysisByTxnId
	 * @param analysis
	 * @return
	 */
	public DataAppuserAnalysis getDataAnalysisByTxnId(DataAppuserAnalysisVo analysis){
		return analysisMapper.getDataAnalysisByTxnId(analysis);
	}
	/**
	 * 运营分析数据载入  查询时间区间
	 * 参数含有
	 * @param map
	 * @param dateStart  起止日期
	 * @param dateEnd  起止日期
	 * @param platformids  平台类型
	 * @return
	 */
	public List<DataAppuserAnalysis> getAppuserAnalysisList(Map<String, Object> map) {
		return analysisMapper.getAppuserAnalysisList(map);
	}
}