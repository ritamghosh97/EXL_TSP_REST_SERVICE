package com.exlservice.timesheet.exception;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ExlTimesheetExceptionUtil {
    public static void handleDateOutRangeException(LocalDate startDateLocal, LocalDate endDateLocal) {
        if(startDateLocal.isAfter(endDateLocal)){
            throw new DatesOutOfRangeException("From Date must be before To Date!");
        }
        else if(!(ChronoUnit.DAYS.between(startDateLocal, endDateLocal)+1>=7 && ChronoUnit.DAYS.between(startDateLocal, endDateLocal)+1<=30)){
            throw new DatesOutOfRangeException("Date interval must be greater than 6 and less than 31 days!");
        }
    }
}
