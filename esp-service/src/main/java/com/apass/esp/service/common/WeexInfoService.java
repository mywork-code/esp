package com.apass.esp.service.common;

import com.apass.esp.domain.entity.WeexInfoEntity;
import com.apass.esp.mapper.WeexInfoEntityMapper;
import com.apass.gfb.framework.utils.DateFormatUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WeexInfoService {
	@Autowired
	private WeexInfoEntityMapper weexInfoMapper;

	private static final Logger LOGGER = LoggerFactory.getLogger(WeexInfoService.class);

    /**
     * 查询weex列表
     * @return
     */
	public List<WeexInfoEntity> queryWeexInfoList() {
		List<WeexInfoEntity> entityList = null;
		try {
			entityList = weexInfoMapper.queryWeexInfoList();
            if(CollectionUtils.isEmpty(entityList)){
                return null;
            }
            for (WeexInfoEntity weexInfoEntity:  entityList) {
                weexInfoEntity.setUpdateDateStr(DateFormatUtil.dateToString(weexInfoEntity.getUpdateDate(),""));
            }
        } catch (Exception e) {
			LOGGER.error("查询weex信息列表失败====>", e);
		}

		return entityList;
	}

	/**
	 * 修改数据库对应weex信息
	 * @return
     */
	@Transactional
	public Integer updateWeexJs(WeexInfoEntity weexInfoEntity) {
		return weexInfoMapper.updateWeexJs(weexInfoEntity);
	}
}
