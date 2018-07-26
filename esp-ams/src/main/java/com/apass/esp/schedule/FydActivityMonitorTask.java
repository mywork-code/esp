package com.apass.esp.schedule;

import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.entity.ProGroupGoods;
import com.apass.esp.domain.vo.ActivityCfgQuery;
import com.apass.esp.mapper.ProGroupGoodsMapper;
import com.apass.esp.service.offer.ActivityCfgService;
import com.apass.esp.utils.mailUtils.MailSenderInfo;
import com.apass.esp.utils.mailUtils.MailUtil;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by DELL on 2018/7/26.
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class FydActivityMonitorTask {

    private static final Logger log = LoggerFactory.getLogger(FydActivityMonitorTask.class);
    @Value("${monitor.receive.emails}")
    public String receiveEmails;

    @Value("${monitor.send.address}")
    public String sendAddress;

    @Value("${monitor.send.password}")
    public String sendPassword;

    @Value("${monitor.env}")
    public String env;

    @Autowired
    private ActivityCfgService activityCfgService;

    @Autowired
    private ProGroupGoodsMapper groupGoodsMapper;
    @Autowired
    private SystemEnvConfig systemEnvConfig;
    
    @Scheduled(cron = "0 0/5 * * * ?")
    public void mailStatisSchedule() {
        if(!systemEnvConfig.isPROD()){
            return;
        }
        log.info("---------执行FydActivityMonitorTask------------");
        String content = "";
        ActivityCfgQuery query = new ActivityCfgQuery();
        query.setStatus("");
        query.setActivityCate(Byte.valueOf("1"));
        query.setStatus("processing");
        List<ProActivityCfg> activityCfgs = activityCfgService.selectProActivityCfgByActivitCfgQuery(query);
        if(CollectionUtils.isNotEmpty(activityCfgs)) {
            for(ProActivityCfg cfg : activityCfgs) {
                Long activityId = cfg.getId();
                List<ProGroupGoods> goods = groupGoodsMapper.selectByActivityId(activityId);
                if(goods.size() < 10){
                    content = "活动名称【" + cfg.getActivityName() + "】 ";
                }
            }
        }
        content +=  "中原专属活动商品数量已少于10件,若有疑问请尽快处理.";
        MailSenderInfo mailSenderInfo = new MailSenderInfo();
        mailSenderInfo.setMailServerHost("SMTP.263.net");
        mailSenderInfo.setMailServerPort("25");
        mailSenderInfo.setValidate(true);
        mailSenderInfo.setUserName(sendAddress);
        mailSenderInfo.setPassword(sendPassword);// 您的邮箱密码
        mailSenderInfo.setFromAddress(sendAddress);
        mailSenderInfo.setSubject("活动商品数量预警");
        mailSenderInfo.setContent(content);
        mailSenderInfo.setToAddress("xujie@apass.cn");
        if ("prod".equals(env)) {
            mailSenderInfo.setToAddress("wenlu@apass.cn");
            mailSenderInfo.setCcAddress("liucong@apass.cn,zhangjinfeng@apass.cn");
        }
        MailUtil mailUtil = new MailUtil();
        mailUtil.sendTextMail(mailSenderInfo);
    }

}
