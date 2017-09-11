package com.apass.esp.service.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.LogAttr;
import com.apass.esp.domain.entity.log.LogInfoEntity;
import com.apass.esp.domain.enums.LogAttrEnums;
import com.apass.esp.mapper.LogAttrMapper;
import com.apass.esp.repository.log.LogInfoRepository;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;

@Service
public class LogService {

    @Autowired
    private LogInfoRepository logInfoRepository;
    
    @Autowired
    private LogAttrMapper logAttrMapper;
    
    /**
     * 数据库content字段最大长度
     */
    public static int varchar = 250;
    
    /**
     * 添加日志信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveLog(LogInfoEntity logInfoEntity){
    	String content = logInfoEntity.getContent();
    	logInfoEntity.setContent("");
        logInfoRepository.insert(logInfoEntity);
    	
    	insertArrt(logInfoEntity.getId(), content);
    }
    
    
    public void insertArrt(Long id,String content){
    	int length = StringUtils.length(content);
    	int c = length % varchar == 0 ? length / varchar:length / varchar + 1;
    	
    	List<LogAttr> list = new ArrayList<LogAttr>();
    	for(int i = 0 ; i < c ; i++){
    		LogAttr attr = new LogAttr();
    		String title = null;
    		if(i == c-1){
    			 title = content.substring((i * 250));
    		}else{
    			 title = content.substring((i * 250) , (i+1)*250);
    		}
    		attr.setExtId(id+"");
    		attr.setContent(title);
    		attr.setSortType(LogAttrEnums.LOG.getCode());
    		attr.setCreateDate(new Date());
    		attr.setUpdateDate(new Date());
    		list.add(attr);
    	}
    	logAttrMapper.insertAttr(list);
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
