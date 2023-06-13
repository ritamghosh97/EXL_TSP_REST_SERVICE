package com.exlservice.timesheet.service.util;

import com.exlservice.timesheet.constant.TimesheetApiCommonConstants;
import com.exlservice.timesheet.data.model.AdminJsonResponse;
import com.exlservice.timesheet.data.model.EmployeeJsonResponse;
import com.exlservice.timesheet.data.model.ManagerJsonResponse;
import com.exlservice.timesheet.data.model.TimesheetJsonResponse;
import com.exlservice.timesheet.entity.Employee;
import com.exlservice.timesheet.entity.Timesheet;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServiceUtil {

    public static List<TimesheetJsonResponse> formatTimesheetHours(DateTimeFormatter formatter, Employee employee) {
        List<TimesheetJsonResponse> timesheetModels = new LinkedList<>();
        employee.getTimesheet()
                        .forEach(timesheet -> {
                            TimesheetJsonResponse timesheetModel
                                    = new TimesheetJsonResponse(formatter.format(timesheet.getDate()),
                                    timesheet.getHours(), timesheet.getApprovalStatus());
                            timesheetModels.add(timesheetModel);
                        });
        return timesheetModels;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static LocalDate getCurrentDateBasedOnWeek(String status, Optional<String> currentWeekDate, DateTimeFormatter formatter) {
        LocalDate current;
        current = currentWeekDate
                        .map(date -> LocalDate.parse(date, formatter))
                        .orElseGet(LocalDate::now);

        if(StringUtils.hasLength(status) && status.equalsIgnoreCase("prev")) {
            current = current.minusWeeks(1);
        } else if(StringUtils.hasLength(status) && status.equalsIgnoreCase("next")){
            current = current.plusWeeks(1);
        }
        return current;
    }

    public static List<String> formatDates(List<LocalDate> dates) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        return dates.stream()
                .map(formatter::format)
                .collect(Collectors.toList());
    }

    public static List<Timesheet> getTimesheetByRange(Employee employee, LocalDate startDateLocal, LocalDate endDateLocal) {
        return employee
                .getTimesheet()
                .stream()
                .sorted(Comparator.comparing(Timesheet::getDate))
                .filter(timesheet -> (timesheet.getDate().isEqual(startDateLocal)
                        || timesheet.getDate().isAfter(startDateLocal)))
                .filter(timesheet -> timesheet.getDate().isEqual(endDateLocal)
                        || timesheet.getDate().isBefore(endDateLocal))
                .toList();
    }

    public static List<Timesheet> getAdditionalAbsentDatesTimesheet(List<Timesheet> timesheet, LocalDate startDate, LocalDate endDate) {
        List<LocalDate> allDatesBetween = getAllDatesBetween(startDate, endDate);

        List<LocalDate> existingTimesheetDates = timesheet.stream().map(Timesheet::getDate).toList();
        List<Timesheet> modifiedTimesheet = new ArrayList<>();

        //add absent Timesheet data to modifiedTimesheet
        allDatesBetween
                .stream()
                .filter(localDate -> !existingTimesheetDates.contains(localDate))
                .forEach(localDate -> {
                    Timesheet timesheetDummy = new Timesheet();
                    timesheetDummy.setDate(localDate);
                    timesheetDummy.setHours(null);
                    timesheetDummy.setApprovalStatus(null);
                    modifiedTimesheet.add(timesheetDummy);
        });
        modifiedTimesheet.addAll(timesheet);
        return modifiedTimesheet.stream().sorted(Comparator.comparing(Timesheet::getDate)).collect(Collectors.toList());
    }

    public static List<LocalDate> getAllDatesBetween(LocalDate startDate, LocalDate endDate) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return IntStream
                .iterate(0, i->i+1)
                .limit(numOfDaysBetween+1) //doing plus to also include last date
                .mapToObj(startDate::plusDays) //i->startDate.plusDays(i)
                .collect(Collectors.toList());
    }

    public static ManagerJsonResponse populateManagerJsonResponse(Employee manager,
                                      Set<String> userRoles,
                                      List<EmployeeJsonResponse> modifiedEmployees,
                                      Map<String, String> dayDateMap) {

        ManagerJsonResponse managerModel = new ManagerJsonResponse();
        managerModel.setId(manager.getId());
        managerModel.setFirstName(manager.getFirstName());
        managerModel.setLastName(manager.getLastName());
        managerModel.setEmail(manager.getEmail());
        managerModel.setManagerId(manager.getManagerId());
        managerModel.setRoles(userRoles);
        managerModel.setEmployees(modifiedEmployees);
        managerModel.setWeekDatesToDay(dayDateMap);
        return managerModel;
    }

    public static Map<String, String> mapDayToDate(List<LocalDate> dates) {
        Map<String, String> dayDateMap = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        dates.forEach(date -> dayDateMap.put(date.format(formatter), TimesheetApiCommonConstants.DAY_ABBR_MAP.get(date.getDayOfWeek().toString())));

        return dayDateMap;
    }

    public static AdminJsonResponse populateAdminJsonResponse(int id, Set<String> adminUserRoles, Employee admin, List<ManagerJsonResponse> managerJsonResponses, Map<String, String> dayDateMap) {
        AdminJsonResponse adminJsonResponse = new AdminJsonResponse();
        adminJsonResponse.setId(id);
        adminJsonResponse.setFirstName(admin.getFirstName());
        adminJsonResponse.setLastName(admin.getLastName());
        adminJsonResponse.setEmail(admin.getEmail());
        adminJsonResponse.setManagerId(admin.getManagerId());
        adminJsonResponse.setRoles(adminUserRoles);
        adminJsonResponse.setWeekDatesToDay(dayDateMap);
        adminJsonResponse.setManagers(managerJsonResponses);
        return adminJsonResponse;
    }
}
