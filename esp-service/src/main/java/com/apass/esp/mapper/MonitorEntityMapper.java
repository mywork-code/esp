package com.apass.esp.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.apass.esp.domain.entity.MonitorEntity;
import com.apass.esp.domain.extentity.MonitorEntityStatistics;
import com.apass.esp.domain.query.MonitorQuery;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface MonitorEntityMapper extends GenericMapper<MonitorEntity, Long> {

    List<MonitorEntityStatistics> getMonitorEntitybyTime(@Param("currentDate") Date currentDate, @Param("date") Date date,@Param("env") String env);

    MonitorEntity getByCurrentDay(@Param("date") Date date,@Param("methodName") String methodName,@Param("env") String env,@Param("application")String application);

    /**
     * 查询一段时间内的数据
     * @param currentDate
     * @param date
     * @return
     */
    List<MonitorEntity> getMonitorEntityByMethodName(@Param("currentDate") Date currentDate, @Param("date") Date date,@Param("methodName") String methodName,@Param("env") String env,@Param("application")String application);

    /**
     * 统计次数 和 总耗时
     * @return
     */
    MonitorEntityStatistics statisticsTimeAndNum(@Param("startCreateDate") String startDate,
                                                 @Param("endCreateDate") String endDate,
                                                 @Param("methodName") String methodName,
                                                 @Param("env") String env,
                                                 @Param("application")String application,
                                                 @Param("status") Integer status);

    List<MonitorEntityStatistics> pageList(MonitorQuery query);

    Integer count(MonitorQuery query);
    
    /**
     * 非配置数据的分页查询
     * @param query
     * @return
     */
    List<MonitorEntity> monitorList(MonitorQuery query);

    Integer monitorListCount(MonitorQuery query);
}
