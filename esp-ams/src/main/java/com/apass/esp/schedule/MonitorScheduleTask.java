package com.apass.esp.schedule;

import com.apass.esp.domain.entity.MonitorEntity;
import com.apass.esp.domain.extentity.MonitorEntityStatistics;
import com.apass.esp.service.monitor.MonitorService;
import com.apass.esp.utils.mailUtils.MailSenderInfo;
import com.apass.esp.utils.mailUtils.MailUtil;
import com.apass.gfb.framework.cache.CacheManager;
import com.apass.gfb.framework.utils.DateFormatUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by xianzhi.wang on 2017/5/18.
 */

@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class MonitorScheduleTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorScheduleTask.class);

    @Autowired
    private MonitorService monitorService;

    @Autowired
    private CacheManager cacheManager;


    @Value("${monitor.receive.emails}")
    public String receiveEmails;

    @Value("${monitor.send.address}")
    public String sendAddress;

    @Value("${monitor.send.password}")
    public String sendPassword;

    @Value("${monitor.env}")
    public String env;

    @Scheduled(cron = "0 0/30 * * * *")
    public void monitorSchedule() {
        String time = cacheManager.get("monitor_time");//间隔时间
        String times = cacheManager.get("monitor_times");//该时间的次数
       // time="1";
        //times="2";
        if (StringUtils.isAnyEmpty(time, times)) {
            LOGGER.info("请先配置间隔时间，该时间内的次数");
            return;
        }
        Date date = new Date();
        date = DateFormatUtil.addDMinutes(date, Integer.valueOf(time) * (-1));
        date = DateFormatUtil.addDMinutes(date, -30);

        List<MonitorEntityStatistics> monitorEntityStatisticsList = monitorService.getMonitorEntitybyTime(date,env);
        if (CollectionUtils.isEmpty(monitorEntityStatisticsList)) {
            return;
        }
        int confTimes = Integer.valueOf(times);
        for (MonitorEntityStatistics monitorEntityStatistics : monitorEntityStatisticsList
                ) {

            if (confTimes > monitorEntityStatistics.getTotalMonitorNum()) {
                continue;
            }
            List<MonitorEntity> list = monitorService.getMonitorEntityByMethodName(date, monitorEntityStatistics.getMethodName(),env,monitorEntityStatistics.getApplication());
            if(CollectionUtils.isEmpty(list)){
                continue;
            }
            MailSenderInfo mailSenderInfo = new MailSenderInfo();
            mailSenderInfo.setMailServerHost("SMTP.263.net");
            mailSenderInfo.setMailServerPort("25");
            mailSenderInfo.setValidate(true);
            mailSenderInfo.setUserName(sendAddress);
            mailSenderInfo.setPassword(sendPassword);// 您的邮箱密码
            mailSenderInfo.setFromAddress(sendAddress);
            mailSenderInfo.setSubject(env+"环境下 "+monitorEntityStatistics.getApplication()+"应用"+monitorEntityStatistics.getMethodDescrption()+"接口在"+time+"分钟内出错"+list.size()+"次");//邮箱标题

            String[] emailAddress = receiveEmails.split(";");
            StringBuffer sb = new StringBuffer(0);

            sb.append("详细信息如下:\n");
            for (int i=0;i<list.size();i++
                    ) {
                sb.append("应用: "+list.get(i).getApplication()+" \n");
                sb.append("方法名称: "+list.get(i).getMethodName()+" \n");
                sb.append("调用时间 "+list.get(i).getInvokeDate()+"\n");
                sb.append("方法用时"+list.get(i).getTime()+"ms"+"\n");
                sb.append("堆栈信息 ："+list.get(i).getMessage()+"\n");
                if(i!=list.size()-1){
                    sb.append("-----------------------------\n");
                }
            }
            mailSenderInfo.setContent(sb.toString());
            for (int i=0;i<emailAddress.length;i++) {
                mailSenderInfo.setToAddress(emailAddress[i]);
                MailUtil mailUtil = new MailUtil();
                mailUtil.sendTextMail(mailSenderInfo);
            }
        }
    }
}
