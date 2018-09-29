package com.apass.esp.schedule;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.vo.DataAppuserRetentionDto;
import com.apass.gfb.framework.utils.DateFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.apass.esp.service.dataanalysis.DataEsporderAnalysisService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 订单统计定时任务
 * @author Administrator
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
@RequestMapping("/talkdata/order")
public class TalkingDataOrderAnalysisTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(TalkingDataOrderAnalysisTask.class);
    @Autowired
    private DataEsporderAnalysisService dataEsporderAnalysisService;


    @RequestMapping("/test1")
    @ResponseBody
    public Response test1(String startDate, String endDate){
        LOGGER.info("TalkingDataOrderAnalysisTask---->test1()方法如看看开始执行,参数 开始时间:{},结束时间:{}",startDate,endDate);
        try{
            flushEsporderAnalysis();
        }catch (Exception e){
            LOGGER.error("每天商城订单统计异常",e);
            return Response.fail("失败!");
        }
        return Response.success("成功!");
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void testSchedule(){
        LOGGER.info("TalkingDataOrderAnalysisTask---->testSchedule()job方法job方法开始执行,执行时间:{}",
                DateFormatUtil.dateToString(new Date(),DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
        try{
            flushEsporderAnalysis();
        }catch (Exception e){
            LOGGER.error("每天商城订单统计异常",e);
        }
    }
    /**
     * 商城订单统计
     * 每日2点执行，刷新昨日订单统计
     * 针对 t_data_esporder_analysis 和  t_data_esporderdetail
     */
    public void flushEsporderAnalysis(){
        try {
        	dataEsporderAnalysisService.flushEsporderAnalysis();
        } catch (Exception e) {
            LOGGER.error("商城订单统计出现异常", e);
        }
    }
}
