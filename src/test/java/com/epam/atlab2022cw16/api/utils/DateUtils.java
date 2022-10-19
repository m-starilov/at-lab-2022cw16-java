package com.epam.atlab2022cw16.api.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String getISO8601CurrentDate() {
        Date date = new Date(System.currentTimeMillis());
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String getISO8601PastDaysDate(int pastDays) {
        return new SimpleDateFormat("yyyy-MM-dd").format(pastDays(pastDays));
    }

    private static Date pastDays(int pastDays) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -pastDays);
        return cal.getTime();
    }
}
