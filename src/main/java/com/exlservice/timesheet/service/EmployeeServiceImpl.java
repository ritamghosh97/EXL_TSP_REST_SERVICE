package com.exlservice.timesheet.service;

import com.exlservice.timesheet.constant.EmployeeAttributeConstants;
import com.exlservice.timesheet.entity.Timesheet;
import com.exlservice.timesheet.repository.EmployeeRepository;
import com.exlservice.timesheet.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;


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
    public Employee findById(int id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        Employee theEmployee = null;

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
    public List<Employee> findEmployeesByManagerId(int managerId) {
        List<Employee> employeesByManagerId= employeeRepository.findEmployeesByManagerId(managerId);


        //sort each employee's timesheet by date
        employeesByManagerId.forEach(employee -> employee.setTimesheet(employee.getTimesheet().stream()
                .sorted(Comparator.comparing(Timesheet::getDate)).toList()));

        //sort employees by firstName then by lastName then by id
        List<Employee> sortedEmployees;
        sortedEmployees = employeesByManagerId.stream()
                                            .sorted(Comparator.comparing(Employee::getFirstName)
                                                .thenComparing(Employee::getLastName).thenComparing(Employee::getId))
                                            .toList();

        return sortedEmployees;
    }

    @Override
    public List<Employee> filterEmployeesTimesheetByDateRangeUnderManager(int theId, String startDate, String endDate) {
        List<Employee> employees = employeeRepository.findEmployeesByManagerId(theId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate startDateLocal = LocalDate.parse(startDate, formatter);
        LocalDate endDateLocal = LocalDate.parse(endDate, formatter);

        //filter timesheet of each employee by date range
        employees.forEach(employee ->
                            employee.setTimesheet(employee
                                                    .getTimesheet()
                                                    .stream()
                                                    .sorted(Comparator.comparing(Timesheet::getDate))
                                                    .filter(timesheet -> (timesheet.getDate().isEqual(startDateLocal)
                                                            || timesheet.getDate().isAfter(startDateLocal)))
                                                    .filter(timesheet -> timesheet.getDate().isEqual(endDateLocal)
                                                            || timesheet.getDate().isBefore(endDateLocal))
                                                    .toList()));

        //add absent timesheet dummy data to each employee's timesheet
        employees.forEach(employee -> employee.setTimesheet(getAdditionalAbsentDatesTimesheet(employee.getTimesheet(), startDateLocal, endDateLocal)));

        return employees;
    }

    private List<Timesheet> getAdditionalAbsentDatesTimesheet(List<Timesheet> timesheet, LocalDate startDate, LocalDate endDate) {
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
                    timesheetDummy.setApprovalStatus(0);
                    modifiedTimesheet.add(timesheetDummy);
        });
        modifiedTimesheet.addAll(timesheet);
        return modifiedTimesheet.stream().sorted(Comparator.comparing(Timesheet::getDate)).collect(Collectors.toList());
    }

    private static List<LocalDate> getAllDatesBetween(LocalDate startDate, LocalDate endDate) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return IntStream
                .iterate(0, i->i+1)
                .limit(numOfDaysBetween+1) //doing plus to also include last date
                .mapToObj(startDate::plusDays) //i->startDate.plusDays(i)
                .collect(Collectors.toList());
    }
}
