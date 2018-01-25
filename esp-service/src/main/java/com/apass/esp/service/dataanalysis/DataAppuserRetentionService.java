package com.apass.esp.service.dataanalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.DataAppuserRetention;
import com.apass.esp.mapper.DataAppuserRetentionMapper;
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
}