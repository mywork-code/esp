package com.apass.esp.repository.log;

import java.util.List;
import java.util.Map;

import com.apass.esp.domain.entity.log.LogInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

@MyBatisRepository
public class LogInfoRepository extends BaseMybatisRepository<LogInfoEntity, Long>  {
    
    /**
     * 根据条件查询日志信息
     */
    public List<LogInfoEntity> getLogListByCondition(Map<String, Object> map){
        
        return getSqlSession().selectList("queryLogInfoList", map);
    }
    
    /**
     * 根据条件查询日志(分页)
     */
    public Pagination<LogInfoEntity> getLogForPageByCondition(Map<String, Object> map,Page page){
        
        return page(map, page, getSQL("queryLogInfoPageList"));
    }
}
