package com.apass.esp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CronTools {
	
	public static String cron = "ss mm HH * * ?";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CronTools.class);
	
	/**
	 * 根据传入的时分秒,获取cron（HH:mm:ss）
	 * @param dateStr
	 * @return
	 */
	public static String getCron(String dateStr){
		
		//是否为空
		if(StringUtils.isBlank(dateStr)){
			return "";
		}
		
		//是否存在：分隔符
		int exist = dateStr.indexOf(":");
		if(exist == -1){
			return "";
		}
		
		//用：分隔符分割后是否为三段
		String[] splits = dateStr.split(":");
		
		if(splits.length != 3){
			return "";
		}
		
		int[] splitInts = new int[3];
		for (int i = 0;i<splits.length;i++) {
			String str = splits[i];
			splitInts[i] = Integer.valueOf(str);
		}
		
		String cronNew = cron.replace("HH", splitInts[0]+"")
				             .replace("mm",splitInts[1]+"")
				             .replace("ss",splitInts[2]+"");
		
		
		return cronNew;
	}
}
