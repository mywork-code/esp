package com.apass.esp.service.dataanalysis;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.DataAppuserRetention;
import com.apass.esp.domain.entity.dataanalysis.DataAppuserRetentionVo;
import com.apass.esp.mapper.DataAppuserRetentionMapper;
import com.apass.gfb.framework.utils.DateFormatUtil;
@Service
public class DataAppuserRetentionService {
	@Autowired
	private DataAppuserRetentionMapper dataAppuserRetentionMapper;
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
	 * @param dateType
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
		newEntity.setDayType("new");
		newEntity.setDay1(entity.getDay1retention());
		newEntity.setDay3(entity.getDay3retention());
		newEntity.setDay7(entity.getDay7retention());
		newEntity.setDay14(entity.getDay14retention());
		newEntity.setDay30(entity.getDay30retention());
		newEntity.setDay7churnuser(entity.getDay7churnuser());
		newEntity.setDay14churnuser(entity.getDay14churnuser());
		newEntity.setDay7backuser(entity.getDay7backuser());
		newEntity.setDay14backuser(entity.getDay14backuser());
		activityEntity.setDayType("activity");
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
}