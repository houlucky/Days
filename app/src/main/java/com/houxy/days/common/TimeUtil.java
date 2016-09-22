package com.houxy.days.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Houxy on 2016/9/3.
 */
public class TimeUtil {

    /**
     * @return 2016年9月3日22:46:15
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowYMDHMSTime() {

        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");

        return mDateFormat.format(new Date());
    }

    /**
     * MM-dd HH:mm:ss
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowMDHMSTime() {

        SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        return mDateFormat.format(new Date());
    }

    /**
     * MM-dd
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowYMD() {

        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return mDateFormat.format(new Date());
    }

    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getYMD(Date date) {

        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return mDateFormat.format(date);
    }

    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTime(String format, Date date) {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(format);
        return mDateFormat.format(date);
    }


    @SuppressLint("SimpleDateFormat")
    public static String getTime(long time) {

        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        return mDateFormat.format(new Date(time));
    }

    public static String converTime(Long time) {

        Calendar currentCalendar = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        if (currentCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
            if (currentCalendar.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)) {
                if (currentCalendar.get(Calendar.HOUR_OF_DAY) == calendar.get(Calendar.HOUR_OF_DAY)) {
                    int min = currentCalendar.get(Calendar.MINUTE) - calendar.get(Calendar.MINUTE);
                    if (min > 0)
                        return min + "分钟前";
                    else
                        return "刚刚";
                } else {
                    return currentCalendar.get(Calendar.HOUR_OF_DAY) - calendar.get(Calendar.HOUR_OF_DAY) + "小时前";
                }
            } else if (currentCalendar.get(Calendar.DAY_OF_YEAR) - calendar.get(Calendar.DAY_OF_YEAR) == 1) {
                // 这里没有考虑每年最后一天的判断，也就是说1.1日时，昨天的显示的是日期

                return "昨天";
            }

        }

        return DateFormatUtil.formatDate("yyyy-MM-dd", time);
    }

    /**
     *
     * @param context 上下文
     * @return 2016年9月14日 星期三
     */
    public static String getCurrentDate(Context context){

        Calendar calendar = Calendar.getInstance();
        return DateUtils.formatDateTime(context,
                calendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_SHOW_WEEKDAY
                        | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_ABBREV_MONTH
                        | DateUtils.FORMAT_ABBREV_WEEKDAY);

    }

    public static String getAssignDate(Context context,int year, int monthOfYear, int dayOfMonth){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        return DateUtils.formatDateTime(context,
                calendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_SHOW_WEEKDAY
                        | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_ABBREV_MONTH
                        | DateUtils.FORMAT_ABBREV_WEEKDAY);

    }

    /**
     *
     * @param date 2016年9月14日星期三 只使用 2016年9月14日
     * @return calendar
     * Calendar类中年份的数值直接书写，月份的值为实际的月份值减1，日期的值就是实际的日期值。
     */
    public static Calendar getAssignCalendar(String date){

        int year = Integer.valueOf(date.substring(0, date.indexOf("年")));
        int monthOfYear = Integer.valueOf(date.substring(date.indexOf("年") + 1, date.indexOf("月")));
        int dayOfMonth = Integer.valueOf(date.substring(date.indexOf("月") + 1, date.indexOf("日")));
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, monthOfYear -1, dayOfMonth);

        return calendar;
    }


    public static String getDaysApart(Calendar calendarAssign){
        long apartTime;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        apartTime = Math.abs(calendar.getTimeInMillis() - calendarAssign.getTimeInMillis());


        return String.valueOf(apartTime / (1000*3600*24));
    }

    public static boolean isPastDay(Calendar calendarAssign){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        return calendar.getTimeInMillis()  - calendarAssign.getTimeInMillis() > 0 ;
    }

}



