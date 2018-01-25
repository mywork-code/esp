package com.apass.esp.service.dataanalysis;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apass.esp.domain.entity.DataEsporderdetail;
import com.apass.esp.mapper.DataEsporderdetailMapper;
@Service
public class DataEsporderdetailService {
	@Autowired
	private DataEsporderdetailMapper dataEsporderdetailMapper;
	/**
	 * CREATE
	 * @param entity
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public Integer createdEntity(DataEsporderdetail entity){
		return dataEsporderdetailMapper.insertSelective(entity);
	}
	/**
	 * DELETE
	 * @param id
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public Integer deleteEntity(Long id){
		return dataEsporderdetailMapper.deleteByPrimaryKey(id);
	}
	/**
	 * DELETE
	 * @param entity
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public Integer deleteEntity(DataEsporderdetail entity){
		return dataEsporderdetailMapper.deleteByPrimaryKey(entity.getId());
	}
	/**
	 * UPDATE
	 * @param entity
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public Integer updateEntity(DataEsporderdetail entity){
		return dataEsporderdetailMapper.updateByPrimaryKeySelective(entity);
	}
	/**
	 * 根据order_analysis_id 外键 查询
	 * @param map
	 * 参数含有
	 * @param order_analysis_id
	 * @return
	 */
	public List<DataEsporderdetail> getDataEsporderdetailList(Map<String, Object> map) {
		return dataEsporderdetailMapper.getDataEsporderdetailList(map);
	}
}