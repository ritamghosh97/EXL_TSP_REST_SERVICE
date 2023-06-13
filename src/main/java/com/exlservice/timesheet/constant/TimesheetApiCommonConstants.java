package com.exlservice.timesheet.constant;

import java.util.HashMap;
import java.util.Map;

public class TimesheetApiCommonConstants {
    public static final String EMPLOYEE_ID = "employeeId";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";

    public static final Map<String, String> DAY_ABBR_MAP = getDayAbbrMap();

    private static Map<String, String> getDayAbbrMap() {
        Map<String, String> dayMap = new HashMap<>();
        dayMap.put("MONDAY", "Mon");
        dayMap.put("TUESDAY", "Tue");
        dayMap.put("WEDNESDAY", "Wed");
        dayMap.put("THURSDAY", "Thu");
        dayMap.put("FRIDAY", "Fri");
        dayMap.put("SATURDAY", "Sat");
        dayMap.put("SUNDAY", "Sun");
        return dayMap;
    }
}
