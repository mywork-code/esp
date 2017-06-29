package com.apass.esp.service.common;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.common.SystemParamEntity;
import com.apass.esp.repository.common.SystemParamRepository;
import com.apass.gfb.framework.exception.BusinessException;

@Service
public class SystemParamService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemParamService.class);
	@Autowired
	public SystemParamRepository systemParamRepository;

	/**
	 * 查询系统参数信息
	 * 
	 * @param customerInfo
	 * @return
	 */
	public List<SystemParamEntity> querySystemParamInfo() throws BusinessException {
		List<SystemParamEntity> systemParamList = null;
		try {
			systemParamList = systemParamRepository.querySystemParamInfo();
		} catch (Exception e) {
			LOGGER.error("查询系统参数信息失败===>", e);
			throw new BusinessException("查询系统参数信息失败！", e);
		}
		return systemParamList;
	}

	/**
	 * 修改系统参数信息
	 * 
	 * @param customerInfo
	 * @return
	 */
	 @Transactional(rollbackFor = Exception.class)
	public void updateSystemParamInfo(Map<String, String> map) throws BusinessException {
		try {
			systemParamRepository.updateSystemParamInfo(map);
		} catch (Exception e) {
			LOGGER.error(" 修改系统参数信息失败===>", e);
			throw new BusinessException("修改系统参数信息失败！", e);
		}
	}

}
