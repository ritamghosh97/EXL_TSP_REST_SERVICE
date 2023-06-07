package com.exlservice.timesheet.service;

import com.exlservice.timesheet.data.model.EmployeeModel;
import com.exlservice.timesheet.entity.Employee;

import java.util.List;
import java.util.Set;

public interface EmployeeService {
    public Employee findById(int id);

    public List<Employee> findEmployeesByManagerId(int parseInt);

    List<Employee> filterEmployeesTimesheetByDateRangeUnderManager(int theId, String startDate, String endDate);

    List<Employee> findAll();

    List<Employee> filterEmployeesByName(String firstName, String lastName);

    EmployeeModel findEmployeeTimesheetByWeek(int id, String status, Set<String> userRoles);
}
