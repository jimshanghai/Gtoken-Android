package com.netban.edc.wallet.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2017/1/5.
 */

public class DateUtils {

    public static final String PATTERN_DATE = "MM月dd日";
    public static final String PATTERN_WEEKDAY = "EEEE";
    public static final String PATTERN_SPLIT = " ";
    public static final String PATTERN = PATTERN_DATE + PATTERN_SPLIT + PATTERN_WEEKDAY;

    public static String date2str(Date date) {
        return date2str(date, PATTERN);
    }

    public static String date2str(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(date);
    }

    public static Date str2date(String str) {
        return str2date(str, PATTERN);
    }

    public static Date str2date(String str, String format) {
        Date date = null;
        try {
            if (str != null)
            {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                date = sdf.parse(str);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getCurTime(){
        Date date=new Date(System.currentTimeMillis());
        return date2str(date, "yyyy-MM-dd");
    }
    public static String getCurYear(){
        Date date=new Date(System.currentTimeMillis());
        return date2str(date, "yyyy");
    }

    public static String getCurMonth(){
        Date date=new Date(System.currentTimeMillis());
        return date2str(date, "MM");
    }

    public static String getCurDay(){
        Date date=new Date(System.currentTimeMillis());
        return date2str(date, "dd");
    }

    public static String getCurHour(){
        Date date=new Date(System.currentTimeMillis());
        return date2str(date, "HH");
    }

    public static String getCurMin(){
        Date date=new Date(System.currentTimeMillis());
        return date2str(date, "mm");
    }

    public static String getCurSec(){
        Date date=new Date(System.currentTimeMillis());
        return date2str(date, "ss");
    }

    public static String getHms(){
        Date date=new Date(System.currentTimeMillis());
        return date2str(date, "yyyy-MM-dd HH:mm:ss");
    }
}
