package com.exlservice.timesheet.service;

import com.exlservice.timesheet.data.model.EmployeeJsonResponse;
import com.exlservice.timesheet.data.model.ManagerJsonResponse;
import com.exlservice.timesheet.entity.Employee;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeService {
    public Employee findById(int id);

    public ManagerJsonResponse findEmployeesByManagerId(int id, Set<String> userRoles);

    ManagerJsonResponse filterEmployeesTimesheetByDateRangeUnderManager(int theId, String startDate, String endDate, Set<String> userRoles);

    List<Employee> findAll();

    List<Employee> filterEmployeesByName(String firstName, String lastName);

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    EmployeeJsonResponse findEmployeeTimesheetByWeek(int id, String status, Set<String> userRoles, Optional<String> date);

    ManagerJsonResponse findEmployeesWeeklyTimesheetByManagerId(Integer id, String action, Set<String> userRoles, Optional<String> currentWeekDate);
}
