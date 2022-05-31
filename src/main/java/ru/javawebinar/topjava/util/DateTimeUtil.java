package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static boolean DateIsBetweenSegment(LocalDate ld, LocalDate startDate, LocalDate endDate) {
        return ld.compareTo(startDate) >= 0 && ld.compareTo(endDate) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate parseDate(String str, boolean start) {
        try {
            return LocalDate.parse(str);
        } catch (DateTimeParseException exc) {
            return start ? LocalDate.MIN : LocalDate.MAX;
        }
    }

    public static LocalTime parseTime(String str, boolean start) {
        try {
            return LocalTime.parse(str);
        } catch (DateTimeParseException | NullPointerException exc) {
            return start ? LocalTime.MIN : LocalTime.MAX;
        }
    }
}

