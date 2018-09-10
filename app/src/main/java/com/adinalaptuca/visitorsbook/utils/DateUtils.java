package com.adinalaptuca.visitorsbook.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static Date timelessDate(Date date) {
        if (date == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date dateWithComponents(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        return timelessDate(calendar.getTime());
    }

    public static String dateToString(Date date, String pattern) {
        try {
            DateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
            return formatter.format(date);
        }
        catch (Exception e) {
            Log.e("DateUtils", "error: " + e.getMessage());
        }

        return null;
    }
}
