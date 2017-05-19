package com.apass.esp.mapper;

import com.apass.esp.common.model.QueryParams;
import com.apass.esp.domain.entity.MonitorEntity;
import com.apass.esp.domain.extentity.MonitorEntityStatistics;
import com.apass.gfb.framework.mybatis.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MonitorEntityMapper extends GenericMapper<MonitorEntity, Long> {

    List<MonitorEntityStatistics> getMonitorEntitybyTime(@Param("currentDate") Date currentDate, @Param("date") Date date);

    /**
     * 查询一段时间内的数据
     * @param currentDate
     * @param date
     * @return
     */
    List<MonitorEntity> getMonitorEntityByMethodName(@Param("currentDate") Date currentDate, @Param("date") Date date,@Param("methodName") String methodName);

    /**
     * 查询一段时间内，同一个主机、应用、方法、状态，执行成功、失败的时间
     * @return
     */
    List<MonitorEntity> getMonitorEntityGroupByHostAppMethodStatus(QueryParams query);
}