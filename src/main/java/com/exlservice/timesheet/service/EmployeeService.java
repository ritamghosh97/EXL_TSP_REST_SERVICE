package com.exlservice.timesheet.service;

import com.exlservice.timesheet.entity.Employee;

import java.util.List;

public interface EmployeeService {
    public Employee findById(int id);

    public List<Employee> findEmployeesByManagerId(int parseInt);

    List<Employee> filterEmployeesTimesheetByDateRangeUnderManager(int theId, String startDate, String endDate);

    List<Employee> findAll();

    List<Employee> filterEmployeesByName(String firstName, String lastName);
}
