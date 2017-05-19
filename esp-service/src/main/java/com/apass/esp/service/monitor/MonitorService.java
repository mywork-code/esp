package com.apass.esp.service.monitor;


import com.apass.esp.domain.dto.monitor.MonitorDto;
import com.apass.esp.domain.entity.MonitorEntity;
import com.apass.esp.domain.extentity.MonitorEntityStatistics;
import com.apass.esp.mapper.MonitorEntityMapper;
import com.apass.esp.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by xianzhi.wang on 2017/5/18.
 */
@Service
public class MonitorService {
    @Autowired
    public MonitorEntityMapper monitorEntityMapper;

    /**
     * @param monitorDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertMonitor(MonitorDto monitorDto) {
        MonitorEntity monitorEntity = new MonitorEntity();
        BeanUtils.copyProperties(monitorEntity, monitorDto);
        return monitorEntityMapper.insert(monitorEntity);
    }

    /**
     * @param date
     * @return
     */
    public List<MonitorEntityStatistics> getMonitorEntitybyTime(Date date) {
        Date currentDate = new Date();
        return monitorEntityMapper.getMonitorEntitybyTime(currentDate, date);
    }

    /**
     * @param date
     * @param methodName
     * @return
     */
    public List<MonitorEntity> getMonitorEntityByMethodName(Date date, String methodName) {
        Date currentDate = new Date();
        return monitorEntityMapper.getMonitorEntityByMethodName(currentDate, date, methodName);
    }

    public List<MonitorEntity> getMonitorEntityGroupByHostAppMethodStatus(){
    	
    	List<MonitorEntity> mList = monitorEntityMapper.getMonitorEntityGroupByHostAppMethodStatus();
    	
    	
    	
    	return mList;
    }
}
