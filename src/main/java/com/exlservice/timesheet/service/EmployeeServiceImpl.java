package com.exlservice.timesheet.service;

import com.exlservice.timesheet.constant.EmployeeAttributeConstants;
import com.exlservice.timesheet.data.model.AdminJsonResponse;
import com.exlservice.timesheet.data.model.EmployeeJsonResponse;
import com.exlservice.timesheet.data.model.ManagerJsonResponse;
import com.exlservice.timesheet.data.model.TimesheetJsonResponse;
import com.exlservice.timesheet.entity.Timesheet;
import com.exlservice.timesheet.repository.EmployeeRepository;
import com.exlservice.timesheet.entity.Employee;
import com.exlservice.timesheet.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");


    @Override
    public List<Employee> findAll() {

        //return all the employees
        //sort the list by firstName, then by lastName and then by id
        return employeeRepository.findAll(Sort.by(EmployeeAttributeConstants.EMP_FIRST_NAME,
                EmployeeAttributeConstants.EMP_LAST_NAME, EmployeeAttributeConstants.EMP_ID));
    }

    @Override
    public Employee findById(int id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        Employee theEmployee;

        if(optionalEmployee.isPresent()) {
            theEmployee = optionalEmployee.get();
        }
        else {
            //couldn't find employee with the given id
            throw new RuntimeException("Employee does not exist with the id="+id);
        }

        return theEmployee;
    }

    @Override
    public List<Employee> filterEmployeesByName(String firstName, String lastName) {
        String likeFirstName = new StringJoiner("", "%", "%").add(firstName.toLowerCase(Locale.ROOT)).toString();
        String likeLastName = new StringJoiner("", "%", "%").add(lastName.toLowerCase(Locale.ROOT)).toString();
        return employeeRepository.findEmployeesByName(likeFirstName, likeLastName);
    }

    @Override
    public EmployeeJsonResponse findEmployeeTimesheetByWeek(int id, String status, Set<String> userRoles, Optional<String> currentWeekDate) {
        LocalDate current = ServiceUtil.getCurrentDateBasedOnWeek(status, currentWeekDate, formatter);

        LocalDate startDayOfWeek = current.with(DayOfWeek.MONDAY);
        LocalDate endDayOfWeek = startDayOfWeek.plusDays(6);

        Employee employee = findById(id);

        //filter timesheet of employee by date range
        employee.setTimesheet(ServiceUtil.getTimesheetByRange(employee, startDayOfWeek, endDayOfWeek));

        //add absent timesheet dummy data to each employee's timesheet
        employee.setTimesheet(ServiceUtil
                .getAdditionalAbsentDatesTimesheet(employee.getTimesheet(), startDayOfWeek, endDayOfWeek));

        List<TimesheetJsonResponse> timesheetModels = ServiceUtil.formatTimesheetHours(formatter, employee);

        Map<String, String> dayDateMap = ServiceUtil.mapDayToDate(ServiceUtil.getAllDatesBetween(startDayOfWeek, endDayOfWeek));

        return new EmployeeJsonResponse(employee.getId(),
                employee.getFirstName(), employee.getLastName(),
                employee.getEmail(), employee.getManagerId(),
                timesheetModels, dayDateMap, userRoles);
    }

    @Override
    public ManagerJsonResponse findEmployeesByManagerId(int managerId, Set<String> userRoles) {
        List<Employee> employeesByManagerId= employeeRepository.findEmployeesByManagerId(managerId);


        //sort each employee's timesheet by date
        employeesByManagerId.forEach(employee -> employee.setTimesheet(employee.getTimesheet().stream()
                .sorted(Comparator.comparing(Timesheet::getDate)).toList()));



        //sort employees by firstName then by lastName then by id
        List<Employee> sortedEmployees =  employeesByManagerId
                .stream()
                .sorted(Comparator
                        .comparing(Employee::getFirstName)
                        .thenComparing(Employee::getLastName)
                        .thenComparing(Employee::getId))
                .toList();


        List<EmployeeJsonResponse> employees = new LinkedList<>();


        sortedEmployees.forEach(employee -> {
            List<TimesheetJsonResponse> timesheetModels = ServiceUtil.formatTimesheetHours(formatter, employee);
            employees.add(new EmployeeJsonResponse(
                    employee.getId(), employee.getFirstName(), employee.getLastName(),
                    employee.getEmail(), employee.getManagerId(), timesheetModels));
        });

        return ServiceUtil.populateManagerJsonResponse(findById(managerId), userRoles, employees, new HashMap<>());
    }

    @Override
    public ManagerJsonResponse findEmployeesWeeklyTimesheetByManagerId(Integer id, String action, Set<String> userRoles, Optional<String> currentWeekDate) {
        List<Employee> employees = employeeRepository.findEmployeesByManagerId(id);

        LocalDate current = ServiceUtil.getCurrentDateBasedOnWeek(action, currentWeekDate, formatter);

        LocalDate startDayOfWeek = current.with(DayOfWeek.MONDAY);
        LocalDate endDayOfWeek = startDayOfWeek.plusDays(6);

        //filter timesheet of each employee by date range
        employees.forEach(employee ->
                employee.setTimesheet(ServiceUtil.getTimesheetByRange(employee, startDayOfWeek, endDayOfWeek)));

        //add absent timesheet dummy data to each employee's timesheet
        employees.forEach(employee -> employee
                .setTimesheet(ServiceUtil.getAdditionalAbsentDatesTimesheet(employee.getTimesheet(), startDayOfWeek, endDayOfWeek)));

        Map<String, String> dayDateMap = ServiceUtil.mapDayToDate(ServiceUtil.getAllDatesBetween(startDayOfWeek, endDayOfWeek));

        List<EmployeeJsonResponse> modifiedEmployees = new LinkedList<>();

        employees.forEach(employee -> {
            List<TimesheetJsonResponse> timesheetResp = ServiceUtil.formatTimesheetHours(formatter, employee);
            modifiedEmployees.add(new EmployeeJsonResponse(
                    employee.getId(), employee.getFirstName(), employee.getLastName(),
                    employee.getEmail(), employee.getManagerId(), timesheetResp));
        });

        return ServiceUtil.populateManagerJsonResponse(findById(id), userRoles, modifiedEmployees, dayDateMap);
    }

    @Override
    public ManagerJsonResponse filterEmployeesTimesheetByDateRangeUnderManager(int theId, LocalDate startDate, LocalDate endDate, Set<String> userRoles) {
        List<Employee> employees = employeeRepository.findEmployeesByManagerId(theId);

        //filter timesheet of each employee by date range
        employees.forEach(employee ->
                employee.setTimesheet(ServiceUtil.getTimesheetByRange(employee, startDate, endDate)));

        //add absent timesheet dummy data to each employee's timesheet
        employees.forEach(employee -> employee
                .setTimesheet(ServiceUtil.getAdditionalAbsentDatesTimesheet(employee.getTimesheet(), startDate, endDate)));

        Map<String, String> dayDateMap = ServiceUtil.mapDayToDate(ServiceUtil.getAllDatesBetween(startDate, endDate));

        List<EmployeeJsonResponse> modifiedEmployees = new LinkedList<>();

        employees.forEach(employee -> {
            List<TimesheetJsonResponse> timesheetModels = ServiceUtil.formatTimesheetHours(formatter, employee);
            modifiedEmployees.add(new EmployeeJsonResponse(
                    employee.getId(), employee.getFirstName(), employee.getLastName(),
                    employee.getEmail(), employee.getManagerId(), timesheetModels));
        });

        return ServiceUtil.populateManagerJsonResponse(findById(theId), userRoles, modifiedEmployees, dayDateMap);
    }

    @Override
    public AdminJsonResponse findAllManagersAndTheirEmployeesWeeklyTimesheet(Integer id, String action, Optional<String> currentWeekDate, Set<String> adminUserRoles) {
        Employee admin = findById(id);
        List<Employee> managers = employeeRepository.findAllManagers();
        List<ManagerJsonResponse> managerJsonResponses = new LinkedList<>();
        managers.forEach(manager -> {
            Set<String> userRoles = employeeRepository.findUserRoles(manager.getId());
            ManagerJsonResponse managerJsonResponse = findEmployeesWeeklyTimesheetByManagerId(manager.getId(), action, userRoles, currentWeekDate);
            managerJsonResponses.add(managerJsonResponse);
        });

        Map<String, String> dayDateMap = managerJsonResponses.get(0).getWeekDatesToDay();

        return ServiceUtil.populateAdminJsonResponse(id, adminUserRoles, admin, managerJsonResponses, dayDateMap);
    }

    @Override
    public AdminJsonResponse findManagersAndTheirEmployeesTimesheetInDateRange(int id, LocalDate startDateLocal, LocalDate endDateLocal, Set<String> adminUserRoles) {
        Employee admin = findById(id);
        List<Employee> managers = employeeRepository.findAllManagers();
        List<ManagerJsonResponse> managerJsonResponses = new LinkedList<>();

        managers.forEach(manager -> {
            Set<String> userRoles = employeeRepository.findUserRoles(manager.getId());
            ManagerJsonResponse managerJsonResponse = filterEmployeesTimesheetByDateRangeUnderManager(manager.getId(), startDateLocal, endDateLocal, userRoles);
            managerJsonResponses.add(managerJsonResponse);
        });

        Map<String, String> dayDateMap = managerJsonResponses.get(0).getWeekDatesToDay();

        return ServiceUtil.populateAdminJsonResponse(id, adminUserRoles, admin, managerJsonResponses, dayDateMap);
    }

}
