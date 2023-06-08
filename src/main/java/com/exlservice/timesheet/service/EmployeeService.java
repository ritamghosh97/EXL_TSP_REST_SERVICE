package com.exlservice.timesheet.service;

import com.exlservice.timesheet.data.model.EmployeeModel;
import com.exlservice.timesheet.data.model.ManagerModel;
import com.exlservice.timesheet.entity.Employee;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeService {
    public Employee findById(int id);

    public ManagerModel findEmployeesByManagerId(int id, Set<String> userRoles);

    ManagerModel filterEmployeesTimesheetByDateRangeUnderManager(int theId, String startDate, String endDate, Set<String> userRoles);

    List<Employee> findAll();

    List<Employee> filterEmployeesByName(String firstName, String lastName);

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    EmployeeModel findEmployeeTimesheetByWeek(int id, String status, Set<String> userRoles, Optional<String> date);
}
