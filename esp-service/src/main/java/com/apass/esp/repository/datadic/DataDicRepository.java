package com.apass.esp.repository.datadic;

import java.util.List;
import java.util.Map;

import com.apass.esp.domain.entity.datadic.DataDicInfoEntity;
import com.apass.esp.domain.entity.refund.RefundDetailInfoEntity;
import com.apass.esp.domain.entity.refund.RefundInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

/**
 * 数据字典Dao
 * @description 
 *
 * @author chenbo
 * @version $Id: DataDicRepository.java, v 0.1 2016年12月27日 下午6:44:49 chenbo Exp $
 */
@MyBatisRepository
public class DataDicRepository extends BaseMybatisRepository<DataDicInfoEntity, Long> {
    /**
     * 查询数据字典信息
     * 
     * @throws BusinessException
     */
    public List<DataDicInfoEntity> queryDataDicInfoByParam(Map<String, String> map) throws BusinessException {
        try {
            List<DataDicInfoEntity> datadicList = getSqlSession().selectList(getSQL("queryDataDicInfoByParam"), map);
            return datadicList;
        } catch (Exception e) {
            throw new BusinessException("查询", e);
        }
    }
    
    /**
     * 根据物流厂商编号查询物流厂商名称 
     * 
     * @param dataNo
     * @return
     */
    public String queryDataNameByDataNo(String dataNo){
        return getSqlSession().selectOne(getSQL("queryDataNameByDataNo"), dataNo);
    }

}
