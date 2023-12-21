package com.example.basic.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * 日期处理工具类
 * @author carey
 */
public class DateUtil {

    /**
     * 获得指定日期开始时间
     * @param date 日期入参
     * @return 返参
     */
    public static Date dayStart(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
    /**
     * 获得指定日期结束时间
     * @param date 日期入参
     * @return 返参
     */
    public static Date dayEnd(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取当前时间 格式：yyyyMMdd HH:mm:ss
     * @return 返回当前时间字符串
     */
    public static String now() {
        return formatDateString(LocalDate.now(),"yyyyMMdd HH:mm:ss");
    }

    /**
     * 字符串转换成日期开始时间
     * @param dateStr 时间str
     * @param type 类型
     * @return {@link Date }
     */
    public static Date getDateStart(String dateStr, String type){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(type);
        LocalDate parse = LocalDate.parse(dateStr, formatter);
        ZonedDateTime zonedDateTime = parse.atStartOfDay(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }
    /**
     * 字符串转换成日期结束时间
     * @param dateStr 时间str
     * @param type 类型
     * @return {@link Date }
     */
    public static Date getDateEnd(String dateStr, String type){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(type);
        LocalDate parse = LocalDate.parse(dateStr, formatter);
        ZonedDateTime zonedDateTime = parse.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    /**
     * 将日期转换成指定格式字符串
     * @param date 输入日期
     * @param form 转换格式（String类型）
     * @return 返参
     */
    public static String formatDateString(Date date, String form) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(form);
        return format.format(date);
    }

    /**
     * 将日期转换成指定格式字符串
     * @param date 输入日期
     * @param form 转换格式（String类型）
     * @return 返参
     */
    public static String formatDateString(LocalDate date,String form){
        if(date==null){
            return null;
        }
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern(form);
        return date.format(formatter);
    }
}
