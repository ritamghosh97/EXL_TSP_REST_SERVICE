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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
        List<Employee> sortedEmployees = employeesByManagerId.stream()
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
                                                    .filter(timesheet -> (timesheet.getDate().isEqual(startDateLocal)
                                                            || timesheet.getDate().isAfter(startDateLocal)))
                                                    .filter(timesheet -> timesheet.getDate().isEqual(endDateLocal)
                                                            || timesheet.getDate().isBefore(endDateLocal))
                                                    .toList()));

        return employees;
    }
}
