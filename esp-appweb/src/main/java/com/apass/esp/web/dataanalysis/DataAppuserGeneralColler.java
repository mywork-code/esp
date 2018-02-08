package com.apass.esp.web.dataanalysis;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.DataAppuserAnalysis;
import com.apass.esp.domain.entity.vo.DataAppAnalysisVo;
import com.apass.esp.domain.entity.vo.DataAppuserAnalysisVo;
import com.apass.esp.domain.vo.DataAnalysisVo;
import com.apass.esp.service.dataanalysis.DataAppuserAnalysisService;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
    public Response getGenerate(@RequestBody Map<String, Object> map){
        /**
         *  思路：
         *  1,平台类型platformids。封装当天时间00:00为dateStart，当前时间为dateEnd，
         *  2,type是1（hour）查询t_data_appuser_analysis列表，存储在list中
         *  3,type是2（daily）查询t_data_appuser_analysis,得到一条数据，获取今日新增，活跃，启动，平均使用时长
         *  4,当前时间-1天，type是2（daily）获取昨日新增，昨日活跃，昨日启动数。并计算环比，（环比=（今日新增-昨日新增）/昨日新增*100%）
         *  结果为正：增，结果为负：减
         *
         */
        String platformids = CommonUtils.getValue(map, "platformids");

        DataAnalysisVo analysis = new DataAnalysisVo();
        analysis.setPlatformids(platformids);
        DataAppuserAnalysisVo analyVo = new DataAppuserAnalysisVo();
        List<DataAppAnalysisVo> voLsit = Lists.newArrayList();
        try{
            if(StringUtils.isEmpty(analysis.getPlatformids())){
                LOGGER.error("参数有误,参数：{}",GsonUtils.toJson(analysis));
                throw new RuntimeException("参数有误,platformids不能为空");
            }

            //2,封装参数：type:1
            byte type = 1;
            Map<String, Object> paramMap = packaParam(analysis.getPlatformids(),type,getDateBegin(true),DateFormatUtil.dateToString(new Date(),"yyyyMMddHH"));
            Map<String, Object> paramMap2 = packaParam(analysis.getPlatformids(),type,getDateBegin(false),getDateEnd(false));
            LOGGER.info("应用概况查询拆线图相关数据参数，今日:{},昨日:{}", GsonUtils.toJson(paramMap),GsonUtils.toJson(paramMap2));
            List<DataAppuserAnalysis> dataAppuserAnalysises = dataAppuserAnalysisService.getAppuserAnalysisList(paramMap);
            List<DataAppuserAnalysis> dataAppuserAnalysises2 = dataAppuserAnalysisService.getAppuserAnalysisList(paramMap2);
            if(CollectionUtils.isEmpty(dataAppuserAnalysises)){
                return Response.success("应用概况相关数据获取成功！",analyVo);
            }
            for(int i=0; i<dataAppuserAnalysises.size(); i++){
                DataAppAnalysisVo vo = new DataAppAnalysisVo();
                vo.setTodayNewuser(dataAppuserAnalysises.get(i).getNewuser());
                vo.setYesetodayNewuser(dataAppuserAnalysises2.get(i).getNewuser());
                vo.setTodaySession(dataAppuserAnalysises.get(i).getSession());
                vo.setYesetodaySession(dataAppuserAnalysises2.get(i).getSession());
                Date txnIdDate = DateFormatUtil.string2date(dataAppuserAnalysises.get(i).getTxnId(),"yyyyMMddHH");
                vo.setTxnId(DateFormatUtil.dateToString(txnIdDate,"HH:mm"));

                voLsit.add(vo);
            }
            analyVo.setDataAppAnalysisVos(voLsit);

            //3,type:2（daily）,今天数据
            analysis.setTxnId(DateFormatUtil.dateToString(new Date(),"yyyyMMdd"));
            analysis.setType("2");
            analysis.setIsDelete("00");
            DataAppuserAnalysis entity = dataAppuserAnalysisService.getDataAnalysisByTxnId(analysis);
            if(entity == null){
                LOGGER.error("数据有误,参数：{}对应数据为空",GsonUtils.toJson(analysis));
                throw new RuntimeException("数据有误");
            }
            analyVo.setTodayIncrease(entity.getNewuser());
            analyVo.setTodaySession(entity.getSession());
            analyVo.setBounceuser(entity.getBounceuser());
            analyVo.setTotaluser(entity.getTotaluser());

            //4,type:2（daily）昨天数据
            Date yestodayDate = DateFormatUtil.addDays(new Date(),-1);
            analysis.setTxnId(DateFormatUtil.dateToString(yestodayDate,"yyyyMMdd"));
            DataAppuserAnalysis entityOld = dataAppuserAnalysisService.getDataAnalysisByTxnId(analysis);
            if(entityOld == null){
                LOGGER.error("数据有误,参数：{}对应数据为空",GsonUtils.toJson(analysis));
                throw new RuntimeException("数据有误");
            }
            analyVo.setYestodayIncrease(entityOld.getNewuser());
            analyVo.setYestodaySession(entityOld.getSession());
            //计算环比（环比=（今日新增-昨日新增）/昨日新增*100%）
            double toincre = Double.valueOf(analyVo.getTodayIncrease());
            double yetincre = Double.valueOf(analyVo.getYestodayIncrease());
            double increaseLink = (toincre-yetincre)/yetincre;

            double toSession = Double.valueOf(analyVo.getTodayIncrease());
            double yetSession = Double.valueOf(analyVo.getYestodayIncrease());
            double sessionLink = (toSession-yetSession)/yetSession;
            analyVo.setIncreaseLinkRatio(increaseLink);
            analyVo.setSessionLinkRatio(sessionLink);

            LOGGER.info("应用概况相关数据:{}",GsonUtils.toJson(analyVo));
        }catch (Exception e){
            LOGGER.error("应用概况相关数据获取失败",e);
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

    /**
     * 获取一天中的开始时间
     * @param flag true:今日，false:昨日
     * @return
     */
    public String getDateBegin(boolean flag) {
        String str = null;
        if(flag){
            str = DateFormatUtil.dateToString(new Date(),"yyyyMMdd");
            str = str+"00";
        }else{
            Date yestodayDate = DateFormatUtil.addDays(new Date(),-1);
            str = DateFormatUtil.dateToString(yestodayDate,"yyyyMMdd");
            str = str+"00";
        }

        return str;
    }

    /**
     * 获取一天中的结束时间
     * @param flag true:今日，false:昨日
     * @return
     */
    public String getDateEnd(boolean flag) {
        String str = null;
        if(flag){
            str = DateFormatUtil.dateToString(new Date(),"yyyyMMdd");
            str = str+"24";
        }else{
            Date yestodayDate = DateFormatUtil.addDays(new Date(),-1);
            str = DateFormatUtil.dateToString(yestodayDate,"yyyyMMdd");
            str = str+"24";
        }

        return str;
    }
}
