package com.apass.esp.service.dataanalysis;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.DataAppuserAnalysis;
import com.apass.esp.domain.entity.customer.RegisterUser;
import com.apass.esp.domain.enums.TermainalTyps;
import com.apass.esp.domain.vo.DataAnalysisVo;
import com.apass.esp.domain.vo.DataAppuserAnalysisDto;
import com.apass.esp.mapper.DataAppuserAnalysisMapper;
import com.apass.esp.service.bill.CustomerServiceClient;
import com.apass.gfb.framework.utils.DateFormatUtil;
@Service
public class DataAppuserAnalysisService {
	@Autowired
	private DataAppuserAnalysisMapper analysisMapper;
	@Autowired
	private CustomerServiceClient client;
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
			DataAppuserAnalysis analysis = analysisMapper.getDataAnalysisByTxnId(new DataAnalysisVo(retention.getDaily(), retention.getPlatformids().toString(),"2","00"));;
			if(null == analysis){
			   analysis = new DataAppuserAnalysis();
			   /*** 此处是获取昨天的 APP端新增的注册用户数 安卓、苹果、全平台*/
			   String yesterday = DateFormatUtil.getAddDaysString(new Date(), -1).replace("-", "");
			   if(StringUtils.equals(retention.getDaily(), yesterday)){
				   RegisterUser user = client.getRegisteruser();
				   String register = null;
				   if(null != user){
					   if(StringUtils.equals(retention.getPlatformids().toString(),TermainalTyps.TYPE_ANDROID.getCode())){
						   register = user.getAndroidNum();
					   }else if(StringUtils.equals(retention.getPlatformids().toString(),TermainalTyps.TYPE_IOS.getCode())){
						   register = user.getIosNum();
					   }else if(StringUtils.equals(retention.getPlatformids().toString(),TermainalTyps.TYPE_FULL.getCode())){
						   register = user.getTotalNum();
					   }
					   analysis.setRegisteruser(register);
				   }
			   }
			}
			Date date = new Date();
			analysis.setTxnId(retention.getDaily());
			analysis.setActiveuser(retention.getActiveuser());
			analysis.setAvgsessionlength(retention.getAvgsessionlength());
			analysis.setBounceuser(retention.getBounceuser());
			analysis.setMau(retention.getMau());
			analysis.setNewuser(retention.getNewuser());
			analysis.setPlatformids(retention.getPlatformids());
			analysis.setSession(retention.getSession());
			analysis.setSessionlength(retention.getSessionlength());
			analysis.setTotaluser(retention.getTotaluser());
			analysis.setType(retention.getType());
			analysis.setUpdatedTime(date);
			analysis.setVersionupuser(retention.getVersionupuser());
			analysis.setWau(retention.getWau());
			
			if(null == analysis.getId()){
				analysis.setCreatedTime(date);
				analysisMapper.insertSelective(analysis);
			}else{
				analysisMapper.updateByPrimaryKeySelective(analysis);
			}
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
		String txnId = vo.getDaily() + vo.getHourly().split(":")[0];
		DataAppuserAnalysis analysis = analysisMapper.getDataAnalysisByTxnId(new DataAnalysisVo(txnId, vo.getPlatformids().toString(), vo.getType().toString(),"00"));
		if(null == analysis){
			analysis = new DataAppuserAnalysis();
		}
		Date date = new Date();
		analysis.setType(vo.getType());
		analysis.setPlatformids(vo.getPlatformids());
		analysis.setTxnId(txnId);
		analysis.setNewuser(vo.getNewuser());
		analysis.setSession(vo.getSession());
		analysis.setUpdatedTime(date);
		if(null == analysis.getId()){
			analysis.setCreatedTime(date);
			analysisMapper.insertSelective(analysis);
		}else{
			analysisMapper.updateByPrimaryKeySelective(analysis);
		}
	}
	/**
	 * getDataAnalysisByTxnId
	 * @param analysis
	 * @return
	 */
	public DataAppuserAnalysis getDataAnalysisByTxnId(DataAnalysisVo analysis){
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