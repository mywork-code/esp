package com.apass.esp.repository.common;

import com.apass.esp.domain.entity.common.ConstantEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

@MyBatisRepository
public class ConstantRepository extends BaseMybatisRepository<ConstantEntity, Long>{
    /**
     * dataTypeNo和dataNo查询常量唯一值
     * 
     * @param dataTypeNo
     * @param dataNo
     * @return
     */
    public ConstantEntity selectByDataNoAndDataTypeNo(String dataTypeNo,String dataNo){
        ConstantEntity param = new ConstantEntity();
        param.setDataNo(dataNo);
        param.setDataTypeNo(dataTypeNo);
        return this.getSqlSession().selectOne("selectByDataNoAndDataTypeNo", param);
    }
    
}
