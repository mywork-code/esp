package com.apass.gfb.framework.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: DateFormatUtil
 * @Description:与Date相关的公用类
 * @author dk
 * @date 2014年12月5日 下午2:29:31
 * 
 */
public class DateFormatUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DateFormatUtil.class);
	
    private static SimpleDateFormat sdf                    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String      ENG_DATE_FROMAT        = "EEE, d MMM yyyy HH:mm:ss z";
    public static final String      YYYY_MM_DD_HH_MM_SS    = "yyyy-MM-dd HH:mm:ss";
    public static final String      YYYY_MM_DD_HH_MM       = "yyyy-MM-dd HH:mm";
    public static final String      YYYY_MM_DD_HH_MM_SS_CN = "yyyy年MM月dd日   HH:mm:ss";
    public static final String      MM_DD_HH_MM            = "MM-dd HH:mm";
    public static final String      MM_DD_HH_MM_CN         = "MM月dd日 HH:mm";
    public static final String      YYYY_MM_DD             = "yyyy-MM-dd";
    public static final String      YYYY                   = "yyyy";
    public static final String      MM                     = "MM";
    public static final String      DD                     = "dd";

    /**
     * @Title: dateToString
     * @Description: (这里用一句话描述这个方法的作用)
     * @param @param date
     * @param @param formate
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public static String dateToString(Date date, String formate) {
        if (date == null) {
            return "";
        }
		if (StringUtils.isBlank(formate)) {
			formate = YYYY_MM_DD_HH_MM_SS;
		}
        DateFormat dateFormat = new SimpleDateFormat(formate);
        return dateFormat.format(date);
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
        return dateFormat.format(date);
    }

    public static String datetime2String(Date date) {
        if (date == null) {
            return "";
        }
        return sdf.format(date);
    }

    /**
     * 得到本周周一
     * 
     * @return yyyy-MM-dd
     */
    public static String getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofweek == 0)
            dayofweek = 7;
        c.add(Calendar.DATE, -dayofweek + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(c.getTime());
    }

    /**
     * 得到本周周日
     * 
     * @return yyyy-MM-dd
     */
    public static String getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofweek == 0)
            dayofweek = 7;
        c.add(Calendar.DATE, -dayofweek + 7);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(c.getTime());
    }

    /**
     * @描述 —— 格式化日期对象
     */
    public static Date date2date(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        String str = sdf.format(date);
        try {
            date = sdf.parse(str);
        } catch (Exception e) {
        	LOGGER.error("格式化日期对象date2date出错==>",e);
            return null;
        }
        return date;
    }

    /**
     * @描述 —— sql时间对象转换成字符串
     */
    public static String timestamp2string(Timestamp timestamp, String formatStr) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        strDate = sdf.format(timestamp);
        return strDate;
    }

    /**
     * @描述 —— 字符串转换成时间对象
     */
    public static Date string2date(String dateString, String formatStr) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        Date formateDate = null;
		if (StringUtils.isBlank(formatStr)) {
			formatStr = YYYY_MM_DD_HH_MM_SS;
		}
        DateFormat format = new SimpleDateFormat(formatStr);
        try {
            formateDate = format.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
        return formateDate;
    }

    /**
     * @描述 —— Date类型转换为Timestamp类型
     */
    public static Timestamp date2timestamp(Date date) {
        if (date == null)
            return null;
        return new Timestamp(date.getTime());
    }

    /**
     * @描述 —— 获得当前年份
     */
    public static String getNowYear() {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY);
        return sdf.format(new Date());
    }

    /**
     * @描述 —— 获得当前月份
     */
    public static String getNowMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat(MM);
        return sdf.format(new Date());
    }

    /**
     * @描述 —— 获得当前日期中的日
     */
    public static String getNowDay() {
        SimpleDateFormat sdf = new SimpleDateFormat(DD);
        return sdf.format(new Date());
    }

    /**
     * @描述 —— 指定时间距离当前时间的中文信息
     */
    public static String getLnow(long time) {
        Calendar cal = Calendar.getInstance();
        long timel = cal.getTimeInMillis() - time;
        if (timel / 1000 < 60) {
            return "1分钟以内";
        } else if (timel / 1000 / 60 < 60) {
            return timel / 1000 / 60 + "分钟前";
        } else if (timel / 1000 / 60 / 60 < 24) {
            return timel / 1000 / 60 / 60 + "小时前";
        } else {
            return timel / 1000 / 60 / 60 / 24 + "天前";
        }
    }

    /**
     * 当前时间一星期前的时间
     * 
     * @return
     */
    public static final String getLastWeek() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -7);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取当前日期的指定格式的字符串
     *
     * @param format
     *            指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:mm"
     * @return
     */
    public static String getCurrentTime(String format) {
        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(YYYY_MM_DD_HH_MM_SS);
        } else {
            sdf.applyPattern(format);
        }
        return sdf.format(new Date());
    }

    public static String getCurrentDate() {
        return dateToString(new Date(), YYYY_MM_DD_HH_MM_SS);
    } 
    public static Boolean checkIsTime(String time) {
        DateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        try {
            format.parse(time);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    
    /**
     * 获得当前时间(yyyy年MM月dd日   HH:mm)
     * @return
     */
    public static String getCurrentTime(){
    	return dateToString(new Date(),YYYY_MM_DD_HH_MM_SS_CN);
    }

    /**
     * 比较日期先后
     * 
     * @param date
     * @param interval
     * @return boolean
     */
    public static boolean isExpired(Date date, int interval) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, interval);
        Calendar now = Calendar.getInstance();
        return calendar.before(now);
    }

    public static Date string2date(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return string2date(value, "yyyy-MM-dd");
    }

    public static String string2string(String value, String from, String to) {
        return dateToString(string2date(value, from), to);
    }

    /**
     * 加1天
     * 
     * @param value
     * @return Date
     */
    public static Date addOneDay(String value) {
        return addDays(value, 1);
    }

    public static Date addOneDay(Date value) {
        return addDays(value, 1);
    }

    public static Date addDays(String value, int num) {
        return addDays(string2date(value), num);
    }

    public static Date addDays(Date value, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(value);
        c.add(Calendar.DATE, num);
        return c.getTime();
    }

    public static Date addMonth(Date value, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(value);
        c.add(Calendar.MONTH, num);
        return c.getTime();
    }

    public static int getBetweenTwoDays(Date start, Date end) {
        if (start == null) {
            return 0;
        }
        if (end == null) {
            end = new Date();
        }
        long longs = (end.getTime() - start.getTime());
        if(longs % (24 * 60 * 60 * 1000)==0){
        	int days = (int) (longs / (24 * 60 * 60 * 1000));
        	return days;
        }else{
        	int days = (int) (longs / (24 * 60 * 60 * 1000)) + 1;
        	return days;
        }
    }

    public static int getBetweenTwoDays(String start, String end) {
        return getBetweenTwoDays(string2date(start), string2date(end));
    }

    /**
     * 获取当前日期(只精确到天)
     */
    public static Date getNow() {
        return date2date(new Date(), "yyyy-MM-dd");
    }
    /**
     * 获取明天日期（精确到天）
     */
    public static Date getTomorrow() {
        return addOneDay(getNow());
    }

    public static String now() {
        return datetime2String(getNow());
    }

    public static Date getLastMonth() {
        return addMonth(getNow(), -1);
    }

    public static Date getNextMonth() {
        return addMonth(getNow(), 1);
    }

    public static Integer getAge(String identityNo) {
        String birthDay = CommonUtils.getIdentityBirth(identityNo);
        int days = getBetweenTwoDays(string2date(birthDay, "yyyyMMdd"), new Date());
        return days / 365;
    }
    
    public static String getAddDaysString(Date value, int days) {
        return dateToString(addDays(value, days));
    }
    
    public static String getAddMonthsString(Date value, int days) {
        return dateToString(addMonth(value, days));
    }
    //加30天
    public static Date get30addOneDay(Date value) {
        return addDays(value, 30);
    }
    //判断日期是否当前月
    public static boolean isThisTime(Date date,String pattern) {  
         SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
         String param = sdf.format(date);//参数时间  
         String now = sdf.format(new Date());//当前时间  
         if(param.equals(now)){  
           return true;  
         }  
         return false;  
    } 
    
    @SuppressWarnings("unused")
    public static boolean isOverlap(Date leftStartDate, Date leftEndDate,Date rightStartDate, Date rightEndDate) {    
        return     
            ((leftStartDate.getTime() >= rightStartDate.getTime())     
                    && leftStartDate.getTime() < rightEndDate.getTime())    
            ||    
            ((leftStartDate.getTime() > rightStartDate.getTime())     
                    && leftStartDate.getTime() <= rightEndDate.getTime())    
            ||    
            ((rightStartDate.getTime() >= leftStartDate.getTime())     
                    && rightStartDate.getTime() < leftEndDate.getTime())    
            ||    
            ((rightStartDate.getTime() > leftStartDate.getTime())     
                    && rightStartDate.getTime() <= leftEndDate.getTime());    
                
    }
    
    /**
     * 计算时间差
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static Date getDateDiff(Date date1, Date date2) {
        long dateDiff = date1.getTime() - date2.getTime();
        return new Date(dateDiff);
    }
    
    /**
     * 合并日期
     * @param date 日期
     * @param day 日
     * @return
     */
    public static Date mergeDate(Date date, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, day);
        
        return calendar.getTime();
    }

    
}
