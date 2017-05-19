package com.apass.esp.service.monitor;


import com.apass.esp.domain.dto.monitor.MonitorDto;
import com.apass.esp.domain.entity.MonitorEntity;
import com.apass.esp.domain.enums.MonitorStatus;
import com.apass.esp.domain.extentity.MonitorEntityStatistics;
import com.apass.esp.domain.query.MonitorQuery;
import com.apass.esp.domain.vo.MonitorVo;
import com.apass.esp.mapper.MonitorEntityMapper;
import com.apass.esp.utils.BeanUtils;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.utils.BaseConstants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
     * 次数
     * @param date
     * @return
     */
    public List<MonitorEntityStatistics> getMonitorEntitybyTime(Date date,String env) {
        Date currentDate = new Date();
        return monitorEntityMapper.getMonitorEntitybyTime(currentDate, date,env);
    }

    /**
     *
     * @param date
     * @param methodName
     * @return
     */
    public List<MonitorEntity> getMonitorEntityByMethodName(Date date, String methodName,String env,String application) {
        Date currentDate = new Date();
        return monitorEntityMapper.getMonitorEntityByMethodName(currentDate, date, methodName,env,application);
    }

    public  ResponsePageBody<MonitorVo> pageListMonitorLog(MonitorQuery query){
        ResponsePageBody<MonitorVo> respBody = new ResponsePageBody<>();
        List<MonitorEntityStatistics> list = monitorEntityMapper.pageList(query);
        List<MonitorVo> result = new ArrayList<>();
        for (MonitorEntityStatistics ms : list) {
            MonitorVo vo = new MonitorVo();
            vo.setApplication(ms.getApplication());
            vo.setHost(ms.getHost());
            vo.setMethodDesciption(ms.getMethodDescrption());
            vo.setMethodName(ms.getMethodName());
            vo.setTotalInvokeNum(ms.getTotalMonitorNum());
            MonitorEntityStatistics failStatistics =  monitorEntityMapper.statisticsTimeAndNum(query.getStartCreateDate(),
                query.getEndCreateDate(),ms.getMethodName(),ms.getEnv(),ms.getApplication()
            , MonitorStatus.FAIL.getVal());
            vo.setFailInvokeNum(failStatistics.getTotalMonitorNum());
            MonitorEntityStatistics successStatistics =  monitorEntityMapper.statisticsTimeAndNum(query.getStartCreateDate(),
                query.getEndCreateDate(),ms.getMethodName(),ms.getEnv(),ms.getApplication()
                ,MonitorStatus.SUCCESS.getVal());
            vo.setSuccessInvokeNum(successStatistics.getTotalMonitorNum());
            vo.setAvgTime(successStatistics.getTime() / successStatistics.getTotalMonitorNum());
            result.add(vo);
        }

        if (CollectionUtils.isEmpty(list)) {
            respBody.setTotal(0);
        } else {
            respBody.setTotal(monitorEntityMapper.count(query));
        }
        respBody.setRows(result);
        respBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);
        return respBody;
    }

}
