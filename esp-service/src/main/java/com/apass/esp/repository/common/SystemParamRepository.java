package com.apass.esp.repository.common;

import java.util.List;
import java.util.Map;

import com.apass.esp.domain.entity.common.SystemParamEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

/**
 * 系统参数Dao
 * 
 * @description
 *
 * @author chenbo
 * @version $Id: SystemParamRepository.java, v 0.1 2017年1月11日 下午3:44:49 chenbo
 *          Exp $
 */
@MyBatisRepository
public class SystemParamRepository extends BaseMybatisRepository<SystemParamEntity, Long> {
    /**
     * 查询数据参数信息
     * 
     * @throws BusinessException
     */
    public List<SystemParamEntity> querySystemParamInfo() throws BusinessException {
        try {
            List<SystemParamEntity> systemParamList = getSqlSession().selectList(getSQL("querySystemParamInfo"));
            return systemParamList;
        } catch (Exception e) {
            throw new BusinessException("查询系统参数异常", e);
        }
    }

    /**
     * 修改系统参数信息
     * 
     * @throws BusinessException
     */
    public void updateSystemParamInfo(Map<String, String> map) throws BusinessException {
        try {
            getSqlSession().update("updateSystemParamInfo", map);
        } catch (Exception e) {
            throw new BusinessException("更新系统参数异常", e);
        }
    }

}
