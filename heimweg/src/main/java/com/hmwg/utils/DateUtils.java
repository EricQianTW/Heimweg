package com.hmwg.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;

/**
 * Created by Abaddon on 2016/3/8.
 */
public class DateUtils {
    public static String F19 = "yyyy-MM-dd HH:mm:ss";
    public static String F20 = "yyyy-MM-dd";
    public static String F17 = "yyyyMMddHHmmssSSS";
    public static String F14 = "yyyyMMddHHmmss";
    public static String F8 = "yyyyMMdd";

    /**
     *
     * @param cal
     * @return
     */
    public static String calendarToString(final Calendar cal,String formatStr) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        return dateFormat.format(cal.getTime());
    }

    /*
     * Change date to format string.
     *
     * @param date
     * @param format
     *
     * @return date string
     */
    public static String dateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /*
     * Change timestamp to format: yyyy-MM-dd HH:mm:ss.
     *
     * @param t timestamp
     *
     * @return date string
     */
    public static String format(long t) {
        return dateToString(new Date(t), F19);
    }

    /*
     * Change now time to specified format.
     *
     * @param f format
     *
     * @return date string
     */
    public static String getCurrenTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    /*
     * Parse time string
     *
     * @param source time
     * @param format
     *
     * @return date string
     * @throws ParseException
     */
    public static Date parseDate(String source, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(source);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDate(String source) {
        if (source.length() == F8.length()) {
            return parseDate(source, F8);
        } else if (source.length() == F19.length()) {
            return parseDate(source, F19);
        } else if (source.length() == F14.length()) {
            return parseDate(source, F14);
        }

        return null;
    }

    public static String parseDateString(String source, String sf, String tf) {
        Date date = parseDate(source, sf);
        if (date != null) {
            return dateToString(date, tf);
        } else {
            return source;
        }
    }
}