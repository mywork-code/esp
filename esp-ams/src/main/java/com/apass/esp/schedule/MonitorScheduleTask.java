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

    //
    @Scheduled(cron = "0 0/60 * * * ?")
    public void monitorSchedule() {
        String time = cacheManager.get("monitor_time");//间隔时间
        String times = cacheManager.get("monitor_times");//该时间的次数
        if (StringUtils.isAnyEmpty(time, times)) {
            LOGGER.info("请先配置间隔时间，该时间内的次数");
            return;
        }
        Date date = new Date();
        date = DateFormatUtil.addDMinutes(date, Integer.valueOf(time) * (-1));
        date = DateFormatUtil.addDMinutes(date, -60);
        List<MonitorEntityStatistics> monitorEntityStatisticsList = monitorService.getMonitorEntitybyTime(date);
        if (CollectionUtils.isEmpty(monitorEntityStatisticsList)) {
            return;
        }
        int confTimes = Integer.valueOf(times);
        for (MonitorEntityStatistics monitorEntityStatistics : monitorEntityStatisticsList
                ) {

            if (confTimes > monitorEntityStatistics.getTotalMonitorNum()) {
                continue;
            }
            List<MonitorEntity> list = monitorService.getMonitorEntityByMethodName(date, monitorEntityStatistics.getMethodName());
            MailSenderInfo mailSenderInfo = new MailSenderInfo();
            mailSenderInfo.setMailServerHost("SMTP.163.com");
            mailSenderInfo.setMailServerPort("25");
            mailSenderInfo.setValidate(true);
            mailSenderInfo.setUserName(sendAddress);
            mailSenderInfo.setPassword(sendPassword);// 您的邮箱密码
            mailSenderInfo.setFromAddress(sendAddress);
            mailSenderInfo.setSubject("1");//邮箱标题

            String[] emailAddress = receiveEmails.split(";");
            for (int i=0;i<emailAddress.length;i++) {
                mailSenderInfo.setToAddress(emailAddress[0]);
                mailSenderInfo.setContent(list.get(0).getMessage());
                MailUtil mailUtil = new MailUtil();
                mailUtil.sendTextMail(mailSenderInfo);
            }
        }
    }
}
