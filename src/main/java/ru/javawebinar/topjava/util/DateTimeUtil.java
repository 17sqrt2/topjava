package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<? super T>> boolean isBetween(T val, T start, T end) {
        return val.compareTo(start) >= 0 && val.compareTo(end) <= 0;
    }

    public static LocalDate strToDate(String str) {
        return str.isEmpty() ? null : LocalDate.parse(str);
    }

    public static LocalTime strToTime(String str) {
        return str.isEmpty() ? null : LocalTime.parse(str);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
