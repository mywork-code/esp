package com.apass.esp.service.monitor;


import com.apass.esp.domain.dto.monitor.MonitorDto;
import com.apass.esp.domain.entity.MonitorEntity;
import com.apass.esp.mapper.MonitorEntityMapper;
import com.apass.esp.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xianzhi.wang on 2017/5/18.
 */
@Service
public class MonitorService {
    @Autowired
    public MonitorEntityMapper monitorEntityMapper;

    @Transactional(rollbackFor=Exception.class)
    public int insertMonitor(MonitorDto monitorDto){
        MonitorEntity monitorEntity = new MonitorEntity();
        BeanUtils.copyProperties(monitorEntity,monitorDto);
       return  monitorEntityMapper.insert(monitorEntity);
    }
}
