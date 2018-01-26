package com.apass.esp.service.dataanalysis;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.DataAppuserAnalysis;
import com.apass.esp.domain.entity.DataAppuserRetention;
import com.apass.esp.domain.vo.DataAppuserRetentionDto;
import com.apass.esp.domain.vo.DataAppuserRetentionVo;
import com.apass.esp.mapper.DataAppuserRetentionMapper;
import com.apass.gfb.framework.utils.DateFormatUtil;
@Service
public class DataAppuserRetentionService {
	@Autowired
	private DataAppuserRetentionMapper dataAppuserRetentionMapper;
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
	 */
	public Response getAppuserRetentionList(Map<String, Object> map) {
		List<DataAppuserRetention> list = dataAppuserRetentionMapper.getAppuserRetentionList(map);
		List<DataAppuserRetentionVo> newList = new ArrayList<DataAppuserRetentionVo>();
		List<DataAppuserRetentionVo> activityList = new ArrayList<DataAppuserRetentionVo>();
		for(DataAppuserRetention entity : list){
			Map<String, DataAppuserRetentionVo> mapEntity = conversionEntity(entity);
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
	 * 转化实体类
	 * @param entity
	 * @return
	 */
	private Map<String, DataAppuserRetentionVo> conversionEntity(DataAppuserRetention entity) {
		Map<String, DataAppuserRetentionVo> map = new HashMap<String, DataAppuserRetentionVo>();
		DataAppuserRetentionVo newEntity = new DataAppuserRetentionVo();
		DataAppuserRetentionVo activityEntity = new DataAppuserRetentionVo();
		String dayData = DateFormatUtil.string2string(entity.getTxnId(), "yyyyMMdd", "MM月dd日");
		newEntity.setDataType("new");
		newEntity.setDay1(entity.getDay1retention());
		newEntity.setDay3(entity.getDay3retention());
		newEntity.setDay7(entity.getDay7retention());
		newEntity.setDay14(entity.getDay14retention());
		newEntity.setDay30(entity.getDay30retention());
		newEntity.setDay7churnuser(entity.getDay7churnuser());
		newEntity.setDay14churnuser(entity.getDay14churnuser());
		newEntity.setDay7backuser(entity.getDay7backuser());
		newEntity.setDay14backuser(entity.getDay14backuser());
		activityEntity.setDataType("activity");
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
	public Response getOperationAnalysisList(Map<String, Object> map) {
		List<DataAppuserAnalysis> list = dataAppuserAnalysisService.getAppuserAnalysisList(map);
		return null;
	}
	
	/**
	 * 每天跑一次
	 * @param dto
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public void insertRetention(DataAppuserRetentionDto dto){
		if(null != dto){
			DataAppuserRetention retention = new DataAppuserRetention();
			Date date = new Date();
			
			retention.setCreatedTime(date);
			retention.setUpdatedTime(date);
			retention.setPlatformids(dto.getPlatformids());
			retention.setTxnId(dto.getDaily().replace("-", ""));
			
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
			
			dataAppuserRetentionMapper.insertSelective(retention);
		}
	}
	
	/**
	 * 根据参数类型，来确认需要统计的数据
	 *   新增  启动  平均使用时长
	 * @return
	 */
	public Map<String,Object> getDateByType(Map<String,Object> params){
		
		List<DataAppuserRetention> list = dataAppuserRetentionMapper.getAppuserRetentionList(params);
		
		
		
		return null;
	}
}