package com.jsmsframework.common.util;


import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    /**
     * 判断是否实在时间段内 HH:mm:ss
     * 如果结束时间小于开始时间则是跨天，结束时间等于开始时间则不限时
     * @param startTime  开始时间(不含) HH:mm:ss
     * @param endTime   结束时间(不含) HH:mm:ss
     * @return
     */
    public static boolean isBetween(String startTime, String endTime) {
        if(startTime==null||endTime==null)
            throw  new NullPointerException();
        if(startTime.trim().equals(endTime.trim()))
            return true;
        DateTime now = new DateTime();

        String[] sts = startTime.split(":");
        int hourOfDayStart = Integer.valueOf(sts[0]);
        int minuteOfHourStart = Integer.valueOf(sts[1]);
        int secondOfMinuteStart = Integer.valueOf(sts[2]);

        String[] ets = endTime.split(":");
        int hourOfDayEnd = Integer.valueOf(ets[0]);
        int minuteOfHourEnd = Integer.valueOf(ets[1]);
        int secondOfMinuteEnd = Integer.valueOf(ets[2]);

        DateTime begin = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), hourOfDayStart,
                minuteOfHourStart, secondOfMinuteStart);
        DateTime end = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), hourOfDayEnd,
                minuteOfHourEnd, secondOfMinuteEnd);

        // 开始时间为23:00
        // 结束时间为08:00则为跨天，需开始时间减一天
        if (begin.isAfter(end.getMillis())) {
            begin = begin.minusDays(1);
        }

        //昨天
        if (now.isAfter(begin.getMillis()) && now.isBefore(end.getMillis())) {
            return true;
        }
        begin = begin.plusDays(1);
        end = end.plusDays(1);
        //明天
        if (now.isAfter(begin.getMillis()) && now.isBefore(end.getMillis())) {
            return true;
        }

        return false;

    }

    public static String dateToStr(Date date, String pattern) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(pattern);
    }


    /**
     * 时间格式转换
     *
     * @param str
     * @param format
     * @return
     */
    public static DateTime parseDate(String str, String format) {
        DateTime dateTime = null;
        try {
            dateTime = new DateTime(DateUtils.parseDateStrictly(str, format));
        } catch (ParseException e) {
            logger.error("时间格式转换失败：str=" + str + ", format=" + format, e);
        }
        return dateTime;
    }

    /**
     * 增加天
     *
     * @param str
     * @param format
     *            时间格式
     * @param plus
     *            增量
     * @return
     */
    public static String plusDays(String str, String format, int plus) {
        return parseDate(str, format).plusDays(plus).toString(format);
    }
    /**
     * 获取指定日期所在周的星期一
     * @param date
     * @return
     */
    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 获取时间区间所在所有周一字符串
     * 格式：20171120
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getAllWeekMonday4aduitTable(Date startTime, Date endTime){
        List<String> timeStr=new ArrayList<>();
        Date eMonday=getThisWeekMonday(startTime);
        Date endMonday=getThisWeekMonday(endTime);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String endDayStr=formatter.format(endMonday);
        String mondayStr=formatter.format(eMonday);
        timeStr.add(mondayStr);

        //开始时间与结束时间在一周内
        if(Objects.equals(mondayStr,endDayStr)){
            return timeStr;
        }

        //非一周循环带出区间周一字符串
        while (!Objects.equals(mondayStr,endDayStr)){
            mondayStr=plusDays(mondayStr,"yyyyMMdd",7);
            timeStr.add(mondayStr);
        }

        return timeStr;

    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }


    /**
     * 获取当前月第一天，返回日期
     * @return
     */
    public static Date getNowMonthFirstDay(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
                 c.add(Calendar.MONTH, 0);
                 c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
                 String first = format.format(c.getTime());
        ParsePosition pos = new ParsePosition(0);
        return format.parse(first,pos);

    }


}
