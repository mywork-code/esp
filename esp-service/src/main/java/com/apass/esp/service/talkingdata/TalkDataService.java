package com.apass.esp.service.talkingdata;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.dto.statement.Filter;
import com.apass.esp.domain.dto.statement.TalkingDataDto;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import com.apass.gfb.framework.utils.DateFormatUtil;

/**
 * 从talkingData获取数据
 * @author xiaohai
 *
 */
@Service
public class TalkDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TalkDataService.class);
    @Autowired
    private CommonHttpClient commonHttpClient;

    /**
     * @param beginDate:开始时间
     * @param date:结束时间
     * @param metrics:查询指标（可以多个）
     * @param groupby:数据输出方式
     * @return
     */
    public String getTalkingData(Date beginDate, Date date, String metrics, String groupby) {
        TalkingDataDto talkingDataDto = new TalkingDataDto();
        talkingDataDto.setGroupby(groupby);
        
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(metrics);
        talkingDataDto.setMetrics(arrayList);
        
        Filter filter = new Filter();
        filter.setStart(DateFormatUtil.dateToString(beginDate, DateFormatUtil.YYYY_MM_DD));
        filter.setEnd(DateFormatUtil.dateToString(date, DateFormatUtil.YYYY_MM_DD));
        talkingDataDto.setFilter(filter);
        
        String str = commonHttpClient.talkingData(talkingDataDto);
        return str;
    }


    public String getTalkingData1(Date beginDate, Date date, String metrics, String groupby,String type) {
        try {
            TimeUnit.SECONDS.sleep(11);
        } catch (InterruptedException e) {
            LOGGER.error("-----getTalkingData1 Exception---->",e);
        }
        TalkingDataDto talkingDataDto = new TalkingDataDto();
        talkingDataDto.setGroupby(groupby);

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(metrics);
        talkingDataDto.setMetrics(arrayList);

        Filter filter = new Filter();
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        if(type.equalsIgnoreCase("ios")){
            integerArrayList.add(2);
        }else{
            integerArrayList.add(1);
        }
        filter.setChannelids(integerArrayList);
        filter.setStart(DateFormatUtil.dateToString(beginDate, DateFormatUtil.YYYY_MM_DD));
        filter.setEnd(DateFormatUtil.dateToString(date, DateFormatUtil.YYYY_MM_DD));
        talkingDataDto.setFilter(filter);

        String str = commonHttpClient.talkingData(talkingDataDto);
        return str;
    }

}
