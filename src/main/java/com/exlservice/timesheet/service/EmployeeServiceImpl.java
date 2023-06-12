package com.exlservice.timesheet.service;

import com.exlservice.timesheet.constant.EmployeeAttributeConstants;
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
    public List<Employee> filterEmployeesByName(String firstName, String lastName) {
        String likeFirstName = new StringJoiner("", "%", "%").add(firstName.toLowerCase(Locale.ROOT)).toString();
        String likeLastName = new StringJoiner("", "%", "%").add(lastName.toLowerCase(Locale.ROOT)).toString();
        return employeeRepository.findEmployeesByName(likeFirstName, likeLastName);
    }

    @Override
    public EmployeeJsonResponse findEmployeeTimesheetByWeek(int id, String status, Set<String> userRoles, Optional<String> currentWeekDate) {
        LocalDate current;

        current = ServiceUtil.getCurrentDateBasedOnWeek(status, currentWeekDate, formatter);

        LocalDate startDayOfWeek = current.with(DayOfWeek.MONDAY);
        LocalDate endDayOfWeek = startDayOfWeek.plusDays(6);

        Employee employee = findById(id);

        employee.setTimesheet(ServiceUtil.getTimesheetByRange(employee, startDayOfWeek, endDayOfWeek));

        List<TimesheetJsonResponse> timesheetModels = ServiceUtil.formatTimesheetHours(formatter, employee);

        List<String> formattedDates = ServiceUtil.formatDates(ServiceUtil.getAllDatesBetween(startDayOfWeek, endDayOfWeek));

        return new EmployeeJsonResponse(employee.getId(),
                employee.getFirstName(), employee.getLastName(),
                employee.getEmail(), employee.getManagerId(),
                timesheetModels, formattedDates, userRoles);
    }

    @Override
    public ManagerJsonResponse findEmployeesWeeklyTimesheetByManagerId(Integer id, String action, Set<String> userRoles, Optional<String> currentWeekDate) {
        List<Employee> employees = employeeRepository.findEmployeesByManagerId(id);

        LocalDate current;

        current = ServiceUtil.getCurrentDateBasedOnWeek(action, currentWeekDate, formatter);

        LocalDate startDayOfWeek = current.with(DayOfWeek.MONDAY);
        LocalDate endDayOfWeek = startDayOfWeek.plusDays(6);

        //filter timesheet of each employee by date range
        employees.forEach(employee ->
                employee.setTimesheet(ServiceUtil.getTimesheetByRange(employee, startDayOfWeek, endDayOfWeek)));

        //add absent timesheet dummy data to each employee's timesheet
        employees.forEach(employee -> employee
                .setTimesheet(ServiceUtil.getAdditionalAbsentDatesTimesheet(employee.getTimesheet(), startDayOfWeek, endDayOfWeek)));

        List<String> formattedDates = ServiceUtil.formatDates(ServiceUtil.getAllDatesBetween(startDayOfWeek, endDayOfWeek));

        List<EmployeeJsonResponse> modifiedEmployees = new LinkedList<>();

        employees.forEach(employee -> {
            List<TimesheetJsonResponse> timesheetModels = ServiceUtil.formatTimesheetHours(formatter, employee);
            modifiedEmployees.add(new EmployeeJsonResponse(
                    employee.getId(), employee.getFirstName(), employee.getLastName(),
                    employee.getEmail(), employee.getManagerId(), timesheetModels));
        });

        return ServiceUtil.populateManagerJsonResponse(findById(id), userRoles, formattedDates, modifiedEmployees);
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

        return ServiceUtil.populateManagerJsonResponse(findById(managerId), userRoles, new LinkedList<>(), employees);
    }

    @Override
    public ManagerJsonResponse filterEmployeesTimesheetByDateRangeUnderManager(int theId, String startDate, String endDate, Set<String> userRoles) {
        List<Employee> employees = employeeRepository.findEmployeesByManagerId(theId);

        LocalDate startDateLocal = LocalDate.parse(startDate, formatter);
        LocalDate endDateLocal = LocalDate.parse(endDate, formatter);

        //filter timesheet of each employee by date range
        employees.forEach(employee ->
                            employee.setTimesheet(ServiceUtil.getTimesheetByRange(employee, startDateLocal, endDateLocal)));

        //add absent timesheet dummy data to each employee's timesheet
        employees.forEach(employee -> employee
                .setTimesheet(ServiceUtil.getAdditionalAbsentDatesTimesheet(employee.getTimesheet(), startDateLocal, endDateLocal)));

        List<String> formattedDates = ServiceUtil.formatDates(ServiceUtil.getAllDatesBetween(startDateLocal, endDateLocal));

        List<EmployeeJsonResponse> modifiedEmployees = new LinkedList<>();

        employees.forEach(employee -> {
            List<TimesheetJsonResponse> timesheetModels = ServiceUtil.formatTimesheetHours(formatter, employee);
            modifiedEmployees.add(new EmployeeJsonResponse(
                    employee.getId(), employee.getFirstName(), employee.getLastName(),
                    employee.getEmail(), employee.getManagerId(), timesheetModels));
        });

        return ServiceUtil.populateManagerJsonResponse(findById(theId), userRoles, formattedDates, modifiedEmployees);
    }

}
