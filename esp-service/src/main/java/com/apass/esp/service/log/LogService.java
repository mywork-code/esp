package com.apass.esp.service.log;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.log.LogInfoEntity;
import com.apass.esp.repository.log.LogInfoRepository;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;

@Service
public class LogService {

    @Autowired
    private LogInfoRepository logInfoRepository;
    /**
     * 添加日志信息
     */
    public Integer saveLog(LogInfoEntity logInfoEntity){
        return logInfoRepository.insert(logInfoEntity);
    }
    
    /**
     * 根据条件查询日志信息
     */
    public List<LogInfoEntity> getLogListByCondition(Map<String, Object> map){
        
        return logInfoRepository.getLogListByCondition(map);
    }
    
    /**
     * 根据条件查询日志(分页)
     */
    public Pagination<LogInfoEntity> getLogForPageByCondition(Map<String, Object> map,Page page){
        
        return logInfoRepository.getLogForPageByCondition(map, page);
    }
}
