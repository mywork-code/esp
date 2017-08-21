package com.apass.esp.task.handler;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.dto.statement.Filter;
import com.apass.esp.domain.dto.statement.TalkingDataDto;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import com.apass.gfb.framework.utils.DateFormatUtil;
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
        Filter filter = new Filter();
        filter.setStart("2017-07-14");
        filter.setEnd(DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD));
        talkingDataDto.setFilter(filter);
        talkingDataDto.setMetrics(arrayList);
        String str = commonHttpClient.talkingData(talkingDataDto);
        JSONObject parseObject = JSONObject.parseObject(str);
        System.out.println(parseObject);
        JSONObject parseObject2 = (JSONObject) parseObject.get("result");
        System.out.println(parseObject2);
        Integer integer = parseObject2.getInteger("newuser");
        
        
        XxlJobLogger.log(str);
        return ReturnT.SUCCESS;
    }

}
