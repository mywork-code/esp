package com.apass.esp.schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.apass.esp.service.dataanalysis.DataEsporderAnalysisService;
/**
 * 订单统计定时任务
 * @author Administrator
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class TalkingDataOrderAnalysisTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(TalkingDataOrderAnalysisTask.class);
    @Autowired
    private DataEsporderAnalysisService dataEsporderAnalysisService;
    /**
     * 商城订单统计
     * 每日2点执行，刷新昨日订单统计
     * 针对 t_data_esporder_analysis 和  t_data_esporderdetail
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void flushEsporderAnalysis(){
        try {
        	dataEsporderAnalysisService.flushEsporderAnalysis();
        } catch (Exception e) {
            LOGGER.error("商城订单统计出现异常", e);
        }
    }
}
