package com.apass.esp.task.handler;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apass.esp.domain.dto.statement.Filter;
import com.apass.esp.domain.dto.statement.TalkingDataDto;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;

@Component
@JobHander(value="talkingDate")
public class TalkingDateScheduleTask extends IJobHandler{
    @Autowired
    private CommonHttpClient commonHttpClient;
    
    @Override
    public ReturnT<String> execute(String... params) throws Exception {
        XxlJobLogger.log("XXL-JOB start......");
        TalkingDataDto talkingDataDto = new TalkingDataDto();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("newuser");
        talkingDataDto.setFilter(new Filter());
        talkingDataDto.setMetrics(arrayList);
        String str = commonHttpClient.talkingData(talkingDataDto);
        XxlJobLogger.log(str);
        return ReturnT.SUCCESS;
    }

}
