package com.epam.atlab2022cw16.api.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtils {

    public static String getISO8601CurrentDate() {
        Date date = new Date(System.currentTimeMillis());
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String getISO8601PastDaysDate(int pastDays) {
        return new SimpleDateFormat("yyyy-MM-dd").format(pastDays(pastDays));
    }

    public static String getStringFromFile(String fileName) throws IOException {
        URL path = DataUtils.class.getResource(fileName);
        assert path != null;
        return FileUtils.readFileToString(new File(path.getFile()), "utf-8");
    }

    private static Date pastDays(int pastDays) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -pastDays);
        return cal.getTime();
    }
}
