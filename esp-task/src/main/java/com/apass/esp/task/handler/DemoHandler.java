package com.apass.esp.task.handler;

import com.apass.esp.domain.dto.statement.Filter;
import com.apass.esp.domain.dto.statement.TalkingDataDto;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @date 2017/8/14
 * @see
 * @since JDK 1.8
 */

@JobHander(value = "demoJobHandler")
@Service
public class DemoHandler extends IJobHandler {


    @Autowired

    private CommonHttpClient commonHttpClient;

    @Override
    public ReturnT<String> execute(String... params) throws Exception {
        XxlJobLogger.log("XXL-JOB, Hello World.");

        TalkingDataDto talkingDataDto = new TalkingDataDto();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("newuser");
        talkingDataDto.setFilter(new Filter());
        talkingDataDto.setMetrics(arrayList);
        String str = commonHttpClient.talkingData(talkingDataDto);

        XxlJobLogger.log("talkingData str:" + str);
        for (int i = 0; i < 5; i++) {
            XxlJobLogger.log("beat at:" + i);
            TimeUnit.SECONDS.sleep(2);
        }
        return ReturnT.SUCCESS;
    }

}
