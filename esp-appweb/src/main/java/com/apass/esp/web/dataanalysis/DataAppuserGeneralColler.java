package com.apass.esp.web.dataanalysis;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.DataAppuserAnalysis;
import com.apass.esp.domain.entity.vo.DataAppuserAnalysisVo;
import com.apass.esp.service.dataanalysis.DataAppuserAnalysisService;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaohai on 2018/1/26.
 */
@Controller
@RequestMapping("/dataanalysis/generate")
public class DataAppuserGeneralColler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataAppuserGeneralColler.class);

    @Autowired
    private DataAppuserAnalysisService dataAppuserAnalysisService;

    /**
     * 应用概况
     */
    @RequestMapping("/getData")
    @ResponseBody
    public Response getGenerate(@RequestBody com.apass.esp.domain.vo.DataAppuserAnalysisVo analysis){
        /**
         *  思路：
         *  1,平台类型platformids。封装当天时间00:00为dateStart，当前时间为dateEnd，
         *  2,type是1（hour）查询t_data_appuser_analysis列表，存储在list中
         *  3,type是2（daily）查询t_data_appuser_analysis,得到一条数据，获取今日新增，活跃，启动，平均使用时长
         *  4,当前时间-1天，type是2（daily）获取昨日新增，昨日活跃，昨日启动数。并计算环比，（环比=（今日新增-昨日新增）/昨日新增*100%）
         *  结果为正：增，结果为负：减
         *
         */
        DataAppuserAnalysisVo analyVo = new DataAppuserAnalysisVo();
        try{
            if(StringUtils.isEmpty(analysis.getPlatformId())){
                LOGGER.error("参数有误,参数：{}",GsonUtils.toJson(analysis));
                throw new RuntimeException("参数有误,platformId不能为空");
            }

            //2,封装参数：type:1
            byte type = 1;
            Map<String, Object> paramMap = packaParam(analysis.getPlatformId(),type,getDateBegin(),DateFormatUtil.dateToString(new Date(),"yyyyMMddHH"));
            LOGGER.info("应用概况查询拆线图相关数据参数:{}", GsonUtils.toJson(paramMap));
            List<DataAppuserAnalysis> dataAppuserAnalysises = dataAppuserAnalysisService.getAppuserAnalysisList(paramMap);
            analyVo.setDataAppuserAnalysises(dataAppuserAnalysises);

            //3,type:2（daily）,今天数据
            analysis.setTxnId(DateFormatUtil.dateToString(new Date(),"yyyyMMdd"));
            analysis.setType("2");
            DataAppuserAnalysis entity = dataAppuserAnalysisService.getDataAnalysisByTxnId(analysis);
            analyVo.setTodayIncrease(entity.getNewuser());
            analyVo.setTodayActivity(entity.getActiveuser());
            analyVo.setTodayLaunch(entity.getSession());
            analyVo.setAvgsessionlength(entity.getAvgsessionlength());
            analyVo.setBounceuser(entity.getBounceuser());
            analyVo.setTotaluser(entity.getTotaluser());

            //4,type:2（daily）昨天数据
            Date yestodayDate = DateFormatUtil.addDays(new Date(),-1);
            analysis.setTxnId(DateFormatUtil.dateToString(yestodayDate,"yyyyMMdd"));
            DataAppuserAnalysis entityOld = dataAppuserAnalysisService.getDataAnalysisByTxnId(analysis);
            analyVo.setYestodayIncrease(entityOld.getNewuser());
            analyVo.setYestodayActivity(entityOld.getActiveuser());
            analyVo.setYestodayLaunch(entityOld.getSession());
            //计算环比（环比=（今日新增-昨日新增）/昨日新增*100%）
            double increaseLink = (Integer.valueOf(analyVo.getTodayIncrease())-Integer.valueOf(analyVo.getYestodayIncrease()))/Integer.valueOf(analyVo.getYestodayIncrease());
            double activityLink = (Integer.valueOf(analyVo.getTodayActivity())-Integer.valueOf(analyVo.getYestodayActivity()))/Integer.valueOf(analyVo.getYestodayActivity());
            double launchyLink = (Integer.valueOf(analyVo.getTodayLaunch())-Integer.valueOf(analyVo.getYestodayLaunch()))/Integer.valueOf(analyVo.getYestodayLaunch());
            analyVo.setIncreaseLinkRatio(increaseLink);
            analyVo.setActivityLinkRatio(activityLink);
            analyVo.setLaunchLinkRatio(launchyLink);

            LOGGER.error("应用概况相关数据:{}",GsonUtils.toJson(analyVo));
        }catch (Exception e){
            LOGGER.error("应用概况相关数据获取成功",e);
            return Response.fail("应用概况相关数据获取失败");
        }
        return Response.success("应用概况相关数据获取成功！",analyVo);
    }



    /**
     * 参数封装
     * @param paramMap
     * @return
     */
    private Map<String,Object> packaParam(String platformids,byte type,String dateStart,String dateEnd) {
        Map<String, Object> returnMap = Maps.newHashMap();

        returnMap.put("dateStart",dateStart);
        returnMap.put("dateEnd",dateEnd);
        returnMap.put("type",type);
        returnMap.put("platformids",platformids);

        return returnMap;
    }

    public String getDateBegin() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR) - 1900;
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        StringBuffer sb = new StringBuffer();
        sb.append(year);
        sb.append(month);
        sb.append(day);
        sb.append("00");
        return sb.toString();
    }
}
