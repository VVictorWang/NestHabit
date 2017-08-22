package com.victor.nesthabit.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by victor on 7/2/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 */

public class DateUtils {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式化时间
     *
     * @param time
     * @return
     */
    public static String getTime(long time) {
        return format(time, DEFAULT_DATE_FORMAT);
    }

    /**
     * 格式化时间,自定义标签
     *
     * @param time    时间
     * @param pattern 格式化时间用的标签 yyyy-MM-dd hh:MM:ss  HH为24小时
     * @return
     */
    public static String format(long time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(time));
    }

    public static String formatDateTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(StringToDatebyPattern(DEFAULT_DATE_FORMAT, time));
    }

    public static long getTimeinMills(int hour, int minute) {
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonth(), date.getDate(), hour, minute);
        return calendar.getTimeInMillis();
    }

    public static boolean isNight(int hour) {
        if (hour > 19 || hour < 6) {
            return true;
        }
        return false;
    }

    public static String DatetoString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String result = null;
        try {
            result = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Date StringToDate(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date result = null;
        try {
            result = dateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Date StringToDatebyPattern(String pattern,String dateString) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date result = null;
        try {
            result = dateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String getCurrentHour() {
        Date date = getCurDate();
        DateFormat format = new SimpleDateFormat("hh");
        return format.format(date);
    }

    public static String getCurrentMinute() {
        Date date = getCurDate();
        DateFormat format = new SimpleDateFormat("mm");
        return format.format(date);
    }

    public static long stringToLong(String dateString) {
        Date date = StringToDate(dateString);
        return date.getTime();
    }

    public static List<Date> formatStrings(List<String> dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (dateString != null && !dateString.isEmpty()) {
            List<Date> result = new ArrayList<>();
            for (String datestr : dateString) {
                try {
                    result.add(format.parse(datestr));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;
        }
        return null;
    }

    public static List<Date> sortDateDesc(List<Date> origin) {
        List<Date> result = origin;
        if (origin != null && !origin.isEmpty()) {
            for (int i = 0; i < result.size(); i++) {
                for (int j = i + 1; j < result.size(); j++) {
                    if (result.get(j).after(result.get(i))) {
                        Date temp = result.get(i);
                        result.set(i, result.get(j));
                        result.set(j, temp);
                    }
                }
            }
        }
        return result;
    }

    public static int getConstantDays(List<Date> dates) {
        int count = 0;
        if (dates != null && !dates.isEmpty()) {
            for (int i = 0; i <dates.size() ; i++) {
                for (int j = i + 1; j < dates.size(); j++) {
                    if ((Math.abs(dates.get(j).getDay() - dates.get(i).getDay()) == 1)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }


    /**
     * 获取当前日期
     */
    public static Date getCurDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 获取当前年份
     */
    public static int getCurYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     */
    public static int getCurMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前是几号
     */
    public static int getCurDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }
}
