package com.smt.wxdj.swxdj.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gbh on 16/11/25.
 */

public class TimeUtils {

    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HHmmss.sss");
        return simpleDateFormat.format(date);
    }

    /**
     * 转换时间格式
     *
     * @param time 接收的时间格式 2016-07-26 17:48
     * @return 转为 yy/MM/dd HH:mm
     */
    public static String chanceTime(String time) {
        DateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm");
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date2 = dateFormat1.parse(time);
            return dateFormat.format(date2);
        } catch (ParseException e) {
            return "";
        }
    }

    public static String chanceTime1(String time) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            Date date2 = dateFormat1.parse(time);
            return dateFormat.format(date2);
        } catch (ParseException e) {
            return "";
        }
    }
}
