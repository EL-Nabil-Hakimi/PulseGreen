package utils;

import models.Consumption;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class CheckDate {

    public static boolean isAvailableOn(LocalDate startDate, LocalDate endDate, List<Consumption> dates) {

        for (Consumption date : dates) {
            for (LocalDate sdate = date.getStartDate(); sdate.isBefore(date.getEndDate().plusDays(1L)); sdate = sdate.plusDays(1L)) {
                if (sdate.equals(startDate) || sdate.equals(endDate)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static double calculationDaysByPeriod(List<Consumption> consumption) {
        AtomicLong days = new AtomicLong();
        consumption.forEach(e->{
            days.addAndGet(ChronoUnit.DAYS.between(e.getStartDate(), e.getEndDate()));
        });
        return days.get() * -1;
    }



}
