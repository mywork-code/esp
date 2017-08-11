package com.apass.esp.schedule;

import com.apass.esp.domain.dto.statement.TalkingDataDto;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @date 2017/8/11
 * @see
 * @since JDK 1.8
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class TalkingDateScheduleTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(TalkingDateScheduleTask.class);

    @Autowired

    private CommonHttpClient commonHttpClient;

    @Scheduled(cron = "0 0/2 * * * ?")
    public void TalkingDateSchedule() {
        TalkingDataDto talkingDataDto = new TalkingDataDto();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("newuser");
        talkingDataDto.setMetrics(arrayList);
        String str = commonHttpClient.talkingData(talkingDataDto);
        LOGGER.info("response {}", str);
    }
}
