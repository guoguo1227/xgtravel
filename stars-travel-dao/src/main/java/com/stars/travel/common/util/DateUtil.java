package com.stars.travel.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {

    private static String parseFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前时间
     * @return
     */
    public static Date getNowDate(){
        return new Date();
    }

    /**
     * 增加n天，返回制定格式字符串
     * @param s
     * @param n
     * @return
     */
    public static String addDay(String s, int n, String parseFormat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(parseFormat);

            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            cd.add(Calendar.DATE, n);//增加一天
            //cd.add(Calendar.MONTH, n);//增加一个月
            return sdf.format(cd.getTime());

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 增加n天，返回制定格式字符串
     * @param s
     * @param n
     * @return
     */
    public static String subDay(String s, int n) {
        try {
            SimpleDateFormat dft = new SimpleDateFormat(parseFormat);
            Calendar cd = Calendar.getInstance();
            cd.setTime(dft.parse(s));
            cd.set(Calendar.DATE, cd.get(Calendar.DATE) - n);
            return dft.format(cd.getTime());
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 字符串转换为日期类型时间
     * @param dateTimeString
     * @param pattern
     * @return
     */
    public static Date string2dateFormate(String dateTimeString, String pattern) throws ParseException {
        if(dateTimeString == null){return null;}
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(dateTimeString);
    }
    /**
     * 日期类型转换字符串为时间
     * @param dateTime
     * @return
     */
    public static String date2stringFormate(Date dateTime){
        if(dateTime == null){return null;}
        SimpleDateFormat format = new SimpleDateFormat(parseFormat);
        return format.format(dateTime);
    }

    /**
     * 当天0:0:0点时刻的日期
     * @return
     */
    public static Date dateOfStartDay(Date inputDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(inputDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return  cal.getTime();
    }

    /**
     * 当天23:59:59点时刻的日期
     * @return
     */
    public static Date dateOfEndDay(Date inputDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(inputDate);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return  cal.getTime();
    }

    /**
     * 两个日期是否为同一天
     * @return
     */
    public static boolean isSameDay(Date d1,Date d2){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(d1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(d2);

        boolean year = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) ;
        boolean month = cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) ;
        boolean day = cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) ;
        //同年同月同日
        return year && month && day;
    }


//    public static void main(String[] args) {
//        isSameDay(new Date(), new Date());
//    }
}
