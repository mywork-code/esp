package com.apass.esp.service.talkingdata;

import java.util.ArrayList;
import java.util.Date;

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
    

}
