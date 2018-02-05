package com.apass.esp.service.dataanalysis;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.DataAppuserAnalysis;
import com.apass.esp.domain.entity.DataAppuserRetention;
import com.apass.esp.domain.vo.DataAnalysisVo;
import com.apass.esp.domain.vo.DataAppuserAnalysisVo;
import com.apass.esp.domain.vo.DataAppuserRetentionDto;
import com.apass.esp.domain.vo.DataAppuserRetentionVo;
import com.apass.esp.domain.vo.DataRetentionVo;
import com.apass.esp.mapper.DataAppuserAnalysisMapper;
import com.apass.esp.mapper.DataAppuserRetentionMapper;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
@Service
public class DataAppuserRetentionService {
	@Autowired
	private DataAppuserRetentionMapper dataAppuserRetentionMapper;
	@Autowired
	private DataAppuserAnalysisMapper dataAppuserAnalysisMapper;
	@Autowired
	private DataAppuserAnalysisService dataAppuserAnalysisService;
	/**
	 * CREATE
	 * @param entity
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public Integer createdEntity(DataAppuserRetention entity){
		return dataAppuserRetentionMapper.insertSelective(entity);
	}
	/**
	 * DELETE
	 * @param id
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public Integer deleteEntity(Long id){
		return dataAppuserRetentionMapper.deleteByPrimaryKey(id);
	}
	/**
	 * DELETE
	 * @param entity
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public Integer deleteEntity(DataAppuserRetention entity){
		return dataAppuserRetentionMapper.deleteByPrimaryKey(entity.getId());
	}
	/**
	 * UPDATE
	 * @param entity
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public Integer updateEntity(DataAppuserRetention entity){
		return dataAppuserRetentionMapper.updateByPrimaryKeySelective(entity);
	}
	/**
	 * 用户留存数据载入
	 * 参数含有
	 * @param dateStart
	 * @param dateEnd
	 * @param platformids
	 * @return
	 * @throws BusinessException 
	 */
	public Response getAppuserRetentionList(Map<String, Object> map) throws BusinessException {
		map = conversionParam(map);
		List<DataAppuserRetention> list = dataAppuserRetentionMapper.getAppuserRetentionList(map);
		list = conversionList(list);
		List<DataAppuserRetentionVo> newList = new ArrayList<DataAppuserRetentionVo>();
		List<DataAppuserRetentionVo> activityList = new ArrayList<DataAppuserRetentionVo>();
		for(DataAppuserRetention entity : list){
			DataAppuserAnalysis appuser = dataAppuserAnalysisService.getDataAnalysisByTxnId(new DataAnalysisVo(entity.getTxnId(), map.get("platformids").toString(), "2","00"));
			Map<String, DataAppuserRetentionVo> mapEntity = conversionEntity(entity,appuser);
			DataAppuserRetentionVo newEntity = mapEntity.get("newEntity");
			DataAppuserRetentionVo activityEntity = mapEntity.get("activityEntity");
			newList.add(newEntity);
			activityList.add(activityEntity);
		}
		map.put("newList", newList);
		map.put("activityList", activityList);
		return Response.success("用户留存数据载入成功！", map);
	}
	/**
	 * 转化参数
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	private Map<String, Object> conversionParam(Map<String, Object> map) throws BusinessException {
		String days = CommonUtils.getValue(map, "days");
		Date now = new Date();
		Date date = null;
		String dateStart = null;
		String dateEnd = DateFormatUtil.dateToString(now, "yyyyMMdd");
		if(StringUtils.isBlank(days)){
			dateStart = CommonUtils.getValue(map, "dateStart");
			dateEnd = CommonUtils.getValue(map, "dateEnd");
			dateStart = DateFormatUtil.string2string(dateStart, "yyyy-MM-dd","yyyyMMdd");
			dateEnd = DateFormatUtil.string2string(dateEnd, "yyyy-MM-dd","yyyyMMdd");
			map.put("dateStart", dateStart);
			map.put("dateEnd", dateEnd);
			return map;
		}
		map.put("dateEnd", dateEnd);
		switch (days) {
			case "0":
				dateStart = dateEnd;
				map.put("dateStart", dateStart);
				break;
			case "-1":
				date = DateFormatUtil.addDays(now, -1);
				dateStart = DateFormatUtil.dateToString(date, "yyyyMMdd");
				map.put("dateStart", dateStart);
				break;
			case "-7":
				date = DateFormatUtil.addDays(now, -7);
				dateStart = DateFormatUtil.dateToString(date, "yyyyMMdd");
				map.put("dateStart", dateStart);
				break;
			case "-30":
				date = DateFormatUtil.addDays(now, -30);
				dateStart = DateFormatUtil.dateToString(date, "yyyyMMdd");
				map.put("dateStart", dateStart);
				break;
		}
		return map;
	}
	/**
	 * 转化集合
	 * @param list
	 * @return
	 */
	private List<DataAppuserRetention> conversionList(List<DataAppuserRetention> list) {
		for(DataAppuserRetention entity : list){
			entity.setDay1retention(formartString(entity.getDay1retention()));
			entity.setDay3retention(formartString(entity.getDay3retention()));
			entity.setDay7retention(formartString(entity.getDay7retention()));
			entity.setDay14retention(formartString(entity.getDay14retention()));
			entity.setDay30retention(formartString(entity.getDay30retention()));
			entity.setDauday1retention(formartString(entity.getDauday1retention()));
			entity.setDauday3retention(formartString(entity.getDauday3retention()));
			entity.setDauday7retention(formartString(entity.getDauday7retention()));
			entity.setDauday14retention(formartString(entity.getDauday14retention()));
			entity.setDauday30retention(formartString(entity.getDauday30retention()));
		}
		return list;
	}
	/**
	 * 格式化字符串
	 * @param str
	 * @return
	 */
	private String formartString(String str){
		if(str.indexOf(".")!=-1){
			if(str.length()>str.indexOf(".")+5){
				str = str.substring(0, str.indexOf(".")+5);
			}
		}
		BigDecimal data = new BigDecimal(str);
		data = data.multiply(new BigDecimal(100));
		return data.toString() + "%";
	}
	/**
	 * 转化实体类
	 * @param entity
	 * @return
	 */
	private Map<String, DataAppuserRetentionVo> conversionEntity(DataAppuserRetention entity,DataAppuserAnalysis appuser) {
		Map<String, DataAppuserRetentionVo> map = new HashMap<String, DataAppuserRetentionVo>();
		DataAppuserRetentionVo newEntity = new DataAppuserRetentionVo();
		DataAppuserRetentionVo activityEntity = new DataAppuserRetentionVo();
		String dayData = DateFormatUtil.string2string(entity.getTxnId(), "yyyyMMdd", "MM月dd日");
		newEntity.setDataType(appuser.getNewuser());
		newEntity.setDay1(entity.getDay1retention());
		newEntity.setDay3(entity.getDay3retention());
		newEntity.setDay7(entity.getDay7retention());
		newEntity.setDay14(entity.getDay14retention());
		newEntity.setDay30(entity.getDay30retention());
		newEntity.setDay7churnuser(entity.getDay7churnuser());
		newEntity.setDay14churnuser(entity.getDay14churnuser());
		newEntity.setDay7backuser(entity.getDay7backuser());
		newEntity.setDay14backuser(entity.getDay14backuser());
		activityEntity.setDataType(appuser.getActiveuser());
		activityEntity.setDay1(entity.getDauday1retention());
		activityEntity.setDay3(entity.getDauday3retention());
		activityEntity.setDay7(entity.getDauday7retention());
		activityEntity.setDay14(entity.getDauday14retention());
		activityEntity.setDay30(entity.getDauday30retention());
		activityEntity.setDay7churnuser(entity.getDay7churnuser());
		activityEntity.setDay14churnuser(entity.getDay14churnuser());
		activityEntity.setDay7backuser(entity.getDay7backuser());
		activityEntity.setDay14backuser(entity.getDay14backuser());
		newEntity.setDayData(dayData);
		activityEntity.setDayData(dayData);
		map.put("newEntity", newEntity);
		map.put("activityEntity", activityEntity);
		return map;
	}
	/**
	 * getDataAnalysisByTxnId
	 * @param analysis
	 * @return
	 */
	public DataAppuserRetention getDataAnalysisByTxnId(DataAnalysisVo analysis){
		return dataAppuserRetentionMapper.getDataAnalysisByTxnId(analysis);
	}
	/**
	 * 每天跑一次
	 * @param dto
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public void insertRetention(DataAppuserRetentionDto dto){
		if(null != dto){
			dto.setDaily(dto.getDaily().replace("-", ""));
			DataAppuserRetention retention = dataAppuserRetentionMapper.getDataAnalysisByTxnId(new DataAnalysisVo(dto.getDaily(),dto.getPlatformids().toString(),"2","00"));
			if(null == retention){
				retention = new DataAppuserRetention();
			}
			Date date = new Date();
			retention.setUpdatedTime(date);
			retention.setPlatformids(dto.getPlatformids());
			retention.setTxnId(dto.getDaily());
			retention.setDauday1retention(dto.getDauday1retention());
			retention.setDauday3retention(dto.getDauday3retention());
			retention.setDauday7retention(dto.getDauday7retention());
			retention.setDauday14retention(dto.getDauday14retention());
			retention.setDauday30retention(dto.getDauday30retention());
			
			retention.setDay1retention(dto.getDay1retention());
			retention.setDay3retention(dto.getDay3retention());
			retention.setDay7retention(dto.getDay7retention());
			retention.setDay14retention(dto.getDay14retention());
			retention.setDay30retention(dto.getDay30retention());
			
			retention.setDay7churnuser(dto.getDay7churnuser());
			retention.setDay14churnuser(dto.getDay14churnuser());
			
			retention.setDay7backuser(dto.getDay7backuser());
			retention.setDay14backuser(dto.getDay14backuser());
			
			if(null == retention.getId()){
				retention.setCreatedTime(date);
				dataAppuserRetentionMapper.insertSelective(retention);
			}else{
				dataAppuserRetentionMapper.updateByPrimaryKeySelective(retention);
			}
		}
	}
	
	/**
	 * 
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param days 近几天（如果有值，应该是数字）
	 * @param platformId(平台（1.安卓 2.苹果 3.全平台）)
	 * @return
	 */
	public Map<String,Object> getDateByTimeAndTypeAndPlatFormId(String startDate,String endDate,String days,String platformId){
		/*** 返回参数的map*/
		Map<String,Object> values = Maps.newHashMap();
		
		/*** 如果传过来的days不为空，优先使用days*/
		Map<String,Object> params = getTimeInterval(startDate, endDate, days);
		if(params.isEmpty()){
			return null;
		}
		params.put("platformids", platformId);
		params.put("isDelete","00");
		params.put("type","2");
		
		/**** 获取数据趋势的第一块数据 */
		Map<String,Object> userSessionMap = getDataAboutNewUserSessionAvg(params);
		
		/*** 活跃分析*/
		List<DataAppuserAnalysis> analysis = dataAppuserAnalysisMapper.getAppuserAnalysisList(params);
		List<DataAppuserAnalysisVo> dataActivityUserVo = Lists.newArrayList();
		for (DataAppuserAnalysis data : analysis) {
			DataAppuserAnalysisVo vo =  new DataAppuserAnalysisVo();
			vo.setDaily(data.getTxnId());
			vo.setActiveuser(data.getActiveuser());//日活跃
			vo.setWau(data.getWau());//周活跃
			vo.setMau(data.getMau());//月活跃
			dataActivityUserVo.add(vo);
		}
		
		
		/*** 留存分析*/
		BigDecimal day1Sum = BigDecimal.ZERO;
		BigDecimal day1Avg = BigDecimal.ZERO;
		
		BigDecimal day7Sum = BigDecimal.ZERO;
		BigDecimal day7Avg = BigDecimal.ZERO;
		
		BigDecimal day30Sum = BigDecimal.ZERO;
		BigDecimal day30Avg = BigDecimal.ZERO;
		List<DataAppuserRetention> list = dataAppuserRetentionMapper.getAppuserRetentionList(params);
		List<DataRetentionVo> retentionVo = Lists.newArrayList(); 
		for (DataAppuserRetention data : list) {
			DataRetentionVo vo = new DataRetentionVo();
			BigDecimal day1 = new BigDecimal(data.getDay1retention()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal day7 = new BigDecimal(data.getDay7retention()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal day30 = new BigDecimal(data.getDay30retention()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
			
			day1Sum = day1Sum.add(day1);
			day7Sum = day7Sum.add(day7);
			day30Sum = day30Sum.add(day30);
			
			vo.setDaily(data.getTxnId());
			vo.setDay1retention(String.valueOf(day1)+"%");
			vo.setDay7retention(String.valueOf(day7)+"%");
			vo.setDay30retention(String.valueOf(day30)+"%");
			retentionVo.add(vo);
		}
		
		/*** 次日留存均值    7日留存均值    30日留存均值*/
		if(CollectionUtils.isNotEmpty(list)){
			BigDecimal size = BigDecimal.valueOf(list.size());
			day1Avg = day1Sum.divide(size, 2, BigDecimal.ROUND_HALF_UP);
			day7Avg = day7Sum.divide(size, 2, BigDecimal.ROUND_HALF_UP);
			day30Avg = day30Sum.divide(size, 2, BigDecimal.ROUND_HALF_UP);
		}
		values.putAll(userSessionMap);
		values.put("activeAnalysis", dataActivityUserVo);
		values.put("retainAnalysis", retentionVo);
		values.put("day1RetentionAvg", String.valueOf(day1Avg)+"%");
		values.put("day7RetentionAvg", String.valueOf(day7Avg)+"%");
		values.put("day30RetentionAvg", String.valueOf(day30Avg)+"%");
		return values;
	}
	
	/**
	 * 获取新增用户和启动和使用时长
	 * @param params
	 * @return
	 */
	public Map<String,Object> getDataAboutNewUserSessionAvg(Map<String,Object> params){
		
		Map<String,Object> values = Maps.newHashMap();
		
		Long newuserSum = 0l;//新增用户总数
		Long newuserAvg = 0l;//平均新增用户数
		
		Long sessionSum = 0l;//启动时长总计
		Long sessionAvg = 0l;//平均启动时长
		
		Double avgsessionSum = 0.0;//平均使用时长总计
		Double avgsessionLength = 0.0;//平均使用时长
		/*** 新增用户  区间内每天对应的  新增人数 启动  平均使用时长*/
		List<DataAppuserAnalysisVo> dataNewUserVo = Lists.newArrayList();
		List<DataAppuserAnalysis> analysis = dataAppuserAnalysisMapper.getAppuserAnalysisList(params);
		for (DataAppuserAnalysis data : analysis) {
			DataAppuserAnalysisVo vo =  new DataAppuserAnalysisVo();
			newuserSum += Integer.parseInt(data.getNewuser());
			sessionSum += Integer.parseInt(data.getSession());
			avgsessionSum += Double.parseDouble(data.getAvgsessionlength());
			vo.setDaily(data.getTxnId());
			vo.setNewuser(data.getNewuser());
			vo.setSession(data.getSession());
			vo.setSessionAvg(data.getAvgsessionlength());
			dataNewUserVo.add(vo);
		}
		/*** 平均新增用户 ，平均启动时长 ，平均使用时长*/
		if(CollectionUtils.isNotEmpty(analysis)){
			long size = analysis.size();
			newuserAvg = newuserSum / size;
			sessionAvg = sessionSum / size;
			avgsessionLength = avgsessionSum / size;
		}
		
		//数据趋势 新增 启动 平均使用时长的图填充数据
		values.put("dataAnalysis", dataNewUserVo);
		values.put("newuserSum",newuserSum);//新增用户总计
		values.put("newuserAvg",newuserAvg);//日均新增用户
		values.put("sessionSum",sessionSum);//启动次数总计
		values.put("sessionAvg",sessionAvg);//日均启动次数
		values.put("avgsessionLength",avgsessionLength);//平均单次使用时长
		return values;
	}
	
	/**
	 * 根据传入的值，获取区间
	 * @param startDate
	 * @param endDate
	 * @param days
	 * @return
	 */
	public Map<String,Object> getTimeInterval(String startDate,String endDate,String days){
		
		Map<String,Object> params = Maps.newHashMap();
		Date now = new Date();
		if(StringUtils.isNotBlank(days)){
			startDate = DateFormatUtil.getAddDaysString(now, Integer.parseInt(days));
			endDate = DateFormatUtil.dateToString(now);
		}else{
			if(StringUtils.isBlank(endDate)){
				endDate = DateFormatUtil.dateToString(now);
			}
			if(StringUtils.isBlank(startDate)){
				startDate = DateFormatUtil.getAddDaysString(now, -7);
			}
		}
		startDate = startDate.replace("-", "");
		endDate = endDate.replace("-", "");
		params.put("dateStart", startDate);
		params.put("dateEnd",endDate);
		return params;
	}
	
}