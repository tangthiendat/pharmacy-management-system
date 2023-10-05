package com.ttdat.application.utilities;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class DateUtils {

    public static List<LocalDate> getCurrentWeekDays() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfCurrentWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate firstDayOfNextWeek = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate currentWeekDay = firstDayOfCurrentWeek;
        List<LocalDate> currentWeekDays = new ArrayList<>();
        while (currentWeekDay.isBefore(firstDayOfNextWeek)) {
            currentWeekDays.add(currentWeekDay);
            currentWeekDay = currentWeekDay.plusDays(1);
        }
        return currentWeekDays;
    }
    
}
