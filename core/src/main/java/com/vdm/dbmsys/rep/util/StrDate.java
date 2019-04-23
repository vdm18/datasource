package com.vdm.dbmsys.rep.util;

import java.util.Arrays;

public class StrDate {
    public static boolean in(String date, String from, String to) {
        return after(date, from) && before(date, to)
            && (identical(date, from) || identical(date, to));
    }

    public static boolean before(String date, String when) {
        int length = minLength(date, when);
        return date.substring(0, length)
            .compareToIgnoreCase(when.substring(0, length)) < 0;
    }

    public static boolean after(String date, String when) {
        int length = minLength(date, when);
        return date.substring(0, length)
            .compareToIgnoreCase(when.substring(0, length)) > 0;
    }

    public static boolean identical(String date1, String date2) {
        int length = minLength(date1, date2);
        return date1.substring(0, length)
            .compareToIgnoreCase(date2.substring(0, length)) == 0;
    }

    private static int minLength(String... dates) {
        return Arrays.asList(dates).stream()
            .mapToInt(String::length)
            .min().orElse(0);
    }
}
