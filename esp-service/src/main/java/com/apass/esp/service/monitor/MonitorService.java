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
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xianzhi.wang on 2017/5/18.
 */
@Service
public class MonitorService {
    @Autowired
    public MonitorEntityMapper monitorEntityMapper;


    public volatile ConcurrentHashMap<String, MonitorEntity> concurrentHashMap = new ConcurrentHashMap<String, MonitorEntity>();

    /**
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateMonitor(MonitorEntity monitorEntity) {
        return monitorEntityMapper.updateByPrimaryKey(monitorEntity);
    }

    /**
     * @return
     */

    public synchronized void Monitorlog(MonitorDto monitorDto) {
        monitorDto.setFlag("0");
        if (monitorDto.getStatus() == 1) {
            String key = monitorDto.getEnv() + monitorDto.getApplication() + monitorDto.getApplication();
            if (!concurrentHashMap.containsKey(key)) {
                MonitorEntity monitorEntity = monitorEntityMapper.getByCurrentDay(new Date(), monitorDto.getMethodName(), monitorDto.getEnv(), monitorDto.getApplication());
                concurrentHashMap.putIfAbsent(key, monitorEntity);
                monitorDto.setNotice(1);
                MonitorEntity monitorEntity1 = new MonitorEntity();
                BeanUtils.copyProperties(monitorEntity1, monitorDto);
                monitorEntityMapper.insert(monitorEntity1);
            } else {
                MonitorEntity monitorEntity = concurrentHashMap.get(key);
                Integer str = Integer.valueOf(monitorDto.getTime()) + Integer.valueOf(monitorEntity.getTime());
                monitorEntity.setNotice(monitorEntity.getNotice() + 1);
                monitorEntity.setTime(String.valueOf(str));
                monitorEntityMapper.updateByPrimaryKey(monitorEntity);
            }
        } else {
            //int record = monitorEntityMapper.insert(monitorDto);
            MonitorEntity monitorEntity = new MonitorEntity();
            BeanUtils.copyProperties(monitorEntity, monitorDto);
            monitorEntityMapper.insert(monitorEntity);
        }
    }

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

    public MonitorEntity getByCurrentDay(Date date, String methodName, String env, String application) {
        return monitorEntityMapper.getByCurrentDay(date, methodName, env, application);
    }


    /**
     * 次数
     *
     * @param date
     * @return
     */
    public List<MonitorEntityStatistics> getMonitorEntitybyTime(Date date, String env) {
        Date currentDate = new Date();
        return monitorEntityMapper.getMonitorEntitybyTime(currentDate, date, env);
    }

    /**
     * @param date
     * @param methodName
     * @return
     */
    public List<MonitorEntity> getMonitorEntityByMethodName(Date date, String methodName, String env, String application) {
        Date currentDate = new Date();
        return monitorEntityMapper.getMonitorEntityByMethodName(currentDate, date, methodName, env, application);
    }

    /**
     * 非配置数据分页
     *
     * @param query
     * @return
     */
    public ResponsePageBody<MonitorVo> pageListMonitor(MonitorQuery query) {
        ResponsePageBody<MonitorVo> respBody = new ResponsePageBody<>();
        List<MonitorEntity> list = monitorEntityMapper.monitorList(query);
        List<MonitorVo> result = new ArrayList<MonitorVo>();

        for (MonitorEntity ms : list) {
            MonitorVo vo = new MonitorVo();
            vo.setApplication(ms.getApplication());
            vo.setHost(ms.getHost());
            vo.setMethodDesciption(ms.getMethodDesciption());
            vo.setMethodName(ms.getMethodName());
            result.add(vo);
        }
        if (CollectionUtils.isEmpty(list)) {
            respBody.setTotal(0);
        } else {
            respBody.setTotal(monitorEntityMapper.monitorListCount(query));
        }
        respBody.setRows(result);
        respBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);
        return respBody;
    }

    public ResponsePageBody<MonitorVo> pageListMonitorLog(MonitorQuery query) {
        ResponsePageBody<MonitorVo> respBody = new ResponsePageBody<>();
        List<MonitorEntityStatistics> list = monitorEntityMapper.pageList(query);
        List<MonitorVo> result = new ArrayList<>();
        for (MonitorEntityStatistics ms : list) {
            MonitorVo vo = new MonitorVo();
            vo.setApplication(ms.getApplication());
            vo.setHost(ms.getHost());
            vo.setMethodDesciption(ms.getMethodDescrption());
            vo.setMethodName(ms.getMethodName());
            MonitorEntityStatistics successStatistics =  monitorEntityMapper.statisticsTimeAndNum(query.getStartCreateDate(),
                query.getEndCreateDate(),ms.getMethodName(),ms.getEnv(),ms.getApplication()
                , MonitorStatus.SUCCESS.getVal());
            vo.setSuccessInvokeNum(successStatistics.getTotalMonitorNum());
            vo.setFailInvokeNum(ms.getTotalMonitorNum() - successStatistics.getTotalMonitorNum());
            vo.setTotalInvokeNum(ms.getTotalMonitorNum());
            long time = successStatistics.getTime()!=null?successStatistics.getTime():0;
            int totalMonitorNum = successStatistics.getNotice() == null ? 0:successStatistics.getNotice();

            if(totalMonitorNum==0){
            	vo.setAvgTime(0L);
            }else{
            	vo.setAvgTime(time / totalMonitorNum);
            }
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
