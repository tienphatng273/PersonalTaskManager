package com.example.personaltaskmanager.features.calendar_events.utils;

import java.util.Calendar;

public class DateUtils {

    public static Calendar today() {
        return Calendar.getInstance();
    }

    public static boolean isToday(int y, int m, int d) {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR) == y &&
                c.get(Calendar.MONTH) == m &&
                c.get(Calendar.DAY_OF_MONTH) == d;
    }

    public static String formatYMD(int y, int m, int d) {
        return y + "-" + String.format("%02d", (m + 1)) + "-" + String.format("%02d", d);
    }
}
