package com.houxy.days.common;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Houxy on 2016/9/3.
 */
public class DateFormatUtil {

    public static final String DEFAULT_FORMAT = "yyyy年MM月dd日";
    public static final String DEFAULT_FORMAT_1 = "yyyy年MM月dd日HH:mm:ss";

    /**
     * 将服务器返回的时间戳转化为需要的日期格式
     *
     * @param format 需要的日期格式 如 "yyyy-MM-dd HH:mm:ss"
     * @param timestamp 时间戳
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDate(String format, Long timestamp) {

        try {
            SimpleDateFormat f = new SimpleDateFormat(format);
            return f.format(timestamp);
        }catch (Exception e){
            Log.e("TAG" ,"formatDate?"+e.toString());
        }
        return "-1";
    }


    /**
     * 将服务器返回的时间戳转化为需要的日期格式 yyyy-MM-dd HH:mm:ss
     * @param timestamp 时间戳
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDate(Long timestamp) {

        try {
            SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT_1);
            return f.format(timestamp);
        }catch (Exception e){
            Log.e("TAG" ,"formatDate ? "+e.toString());
        }
        return "-1";
    }

    /**
     * 将字符串转化成时间戳
     *
     * @param user_time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        re_time = re_time + "000";
        return re_time;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTimer(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        re_time = re_time + "000";
        return re_time;
    }

    // 将时间戳转为字符串
    @SuppressLint("SimpleDateFormat")
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        return re_StrTime;
    }
}
