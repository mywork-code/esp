package com.apass.esp.web.dataanalysis;

import com.apass.esp.domain.Response;
import com.apass.esp.service.dataanalysis.DataAppuserAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by xiaohai on 2018/1/26.
 */
@RequestMapping("/dataanalysis/generate")
public class DataAppuserGeneralColler {
    @Autowired
    private DataAppuserAnalysisService dataAppuserAnalysisService;

    /**
     * 应用概况
     */
    @RequestMapping("/getData")
    @ResponseBody
    public Response getGenerate(Map<String, Object> paramMap){
        /**
         *  思路：
         *  1,参数传当前时间
         *  2,查询t_data_appuser_analysis列表
         *  3,循环 获取的list集合，计算：今日新增，今日活跃，今日启动数。
         *
         *  4,当前时间-1天，获取昨日新增，昨日活跃，昨日启动数。并计算环比，（环比=（今日新增-昨日新增）/昨日新增*100%）
         *  结果为正：增，结果为负：减
         *
         */
        return null;
    }
}
