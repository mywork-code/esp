package com.apass.esp.service.dataanalysis;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.DataAppuserRetention;
import com.apass.esp.mapper.DataAppuserRetentionMapper;
import com.apass.gfb.framework.utils.CommonUtils;
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
		String dateType = CommonUtils.getValue(map, "dateType");
		if(StringUtils.isBlank(dateType)){
			
		}
		List<DataAppuserRetention> list = dataAppuserRetentionMapper.getAppuserRetentionList(map);
		return null;
	}
}