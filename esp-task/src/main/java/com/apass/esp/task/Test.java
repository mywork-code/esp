package com.apass.esp.task;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Test {
    public static void main(String[] args) {
        Integer uv = 654645;
        Integer count = 26;
        BigDecimal confi = new BigDecimal(count).divide(new BigDecimal(uv),8,BigDecimal.ROUND_HALF_UP);
        System.out.println(confi);        
    }
}
