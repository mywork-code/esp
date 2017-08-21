package com.apass.esp.task.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.apass.esp.service.activity.ActivityInfoService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;


@JobHander(value = "validateActivityEndtime")
@Service
public class ActivityScheduleTask extends IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(ActivityScheduleTask.class);

    @Autowired
    private ActivityInfoService activityInfoService;

    @Override
    public ReturnT<String> execute(String... paramVarArgs) throws Exception {
        try {
            XxlJobLogger.log("执行校验商品活动过期时间定时任务开始");
            activityInfoService.updateActivityStatusByEndtime();
            XxlJobLogger.log("执行校验商品活动过期时间时间定时任务结束");
        } catch (Exception e) {
            logger.error("执行校验商品活动过期时间异常", e);
        }
        return ReturnT.SUCCESS;
    }
}
