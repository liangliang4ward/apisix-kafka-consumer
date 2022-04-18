package com.demo.utils;

import cn.hutool.core.date.DateUtil;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author gaoll
 * @time 2022/4/7 16:19
 **/
public class DateUtils {
    public static String getYearStartTime(Date baseTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(DateUtil.beginOfYear(baseTime));
    }

    public static String getDayStartTime(Date baseTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(DateUtil.beginOfDay(baseTime));
    }

    public static String getMonthStartTime(Date baseTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(DateUtil.beginOfMonth(baseTime));
    }

    public static String getPreEndWithMinute0or5(Date baseTime){

        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseTime);

        int minute = calendar.get(Calendar.MINUTE);
        DecimalFormat df = new DecimalFormat("00");
        String minuteStr = df.format(minute);
        int intV = Integer.parseInt(minuteStr.substring(1, 2));
        minuteStr = minuteStr.substring(0,1)+(intV>=5?5:0);
        calendar.set(Calendar.MINUTE,Integer.parseInt(minuteStr));
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        return sdf.format(calendar.getTime());
    }

    public static Date parseDate(String pattern,String dateString){
        SimpleDateFormat dsf = new SimpleDateFormat(pattern);

        try {
            return dsf.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }
}
