package com.exlservice.timesheet.controller;

import com.exlservice.timesheet.constant.EmployeeAttributeConstants;
import com.exlservice.timesheet.constant.TimesheetApiCommonConstants;
import com.exlservice.timesheet.data.model.EmployeeModel;
import com.exlservice.timesheet.entity.Employee;
import com.exlservice.timesheet.entity.Timesheet;
import com.exlservice.timesheet.response.handler.ResponseHandler;
import com.exlservice.timesheet.service.EmployeeService;
import com.exlservice.timesheet.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/exl-timesheet-api")
public class ExlTimesheetRestController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TimesheetService timesheetService;


    /**
     * Method to get all the employees
     * @return list of employees
     */
    @GetMapping("/employees")
    public List<Employee> findAllEmployees(){
        return employeeService.findAll();
    }


    /**
     * Method to get all the employees of particular manager.
     * @param employeeId: id of a manager
     * @return list of employees under the manager having id: employeeId
     */
    @GetMapping("/employees/manager/{managerId}")
    public List<Employee> findEmployeesByManagerId(
                        @PathVariable(EmployeeAttributeConstants.EMP_MANAGER_ID) int employeeId){
        return employeeService.findEmployeesByManagerId(employeeId);
    }


    /**
     * Method to find an employee having id employeeId and filter the timesheet
     * data from startDate to endDate
     * @param employeeId: id of an employee
     * @param startDate: the date from which timesheet data will be shown
     * @param endDate: the date up to which timesheet data will be shown
     * @return an employee with id employeeId, along with filtered timesheet by date range
     */
    @GetMapping("/employees/{employeeId}/{startDate}/{endDate}")
    public ResponseEntity<Map<String, Object>> findEmployeeTimesheetWithinDateRange(
                                                @PathVariable(TimesheetApiCommonConstants.EMPLOYEE_ID) int employeeId,
                                                @PathVariable(TimesheetApiCommonConstants.START_DATE) String startDate,
                                                @PathVariable(TimesheetApiCommonConstants.END_DATE) String endDate){

        Employee employee = employeeService.findById(employeeId);

        List<Timesheet> timesheet = timesheetService.findTimesheetBetweenDates(employeeId, startDate, endDate);

        //another way to return an employee is
        //employee.setTimesheet(timesheet)
        //return this employee would serve the purpose

        return ResponseHandler.generateTimesheetResponse(employee, timesheet);
    }


    /**
     * Method to find employees under the manager with id managerId, and also filter
     * each employee's timesheet data from startDate to endDate
     * @param managerId: id of a manager
     * @param startDate: the date from which timesheet data will be shown
     * @param endDate: the date up to which timesheet data will be shown
     * @return list of employees with filtered timesheet by date under the manager having id: managerId
     */
    @GetMapping("/employees/manager/{managerId}/{startDate}/{endDate}")
    public List<Employee> findEmployeesByManagerIdWithinDateRange(
                          @PathVariable(EmployeeAttributeConstants.EMP_MANAGER_ID) int managerId,
                          @PathVariable(TimesheetApiCommonConstants.START_DATE) String startDate,
                          @PathVariable(TimesheetApiCommonConstants.END_DATE) String endDate) {

        return employeeService.filterEmployeesTimesheetByDateRangeUnderManager(managerId, startDate, endDate);
    }


    /**
     * Method to find an employee by firstName or lastName
     * @param firstName: firstName of an employee
     * @param lastName: lastName of an employee
     * @return list of matching employees
     */
    @GetMapping("/employees/{firstName}/{lastName}")
    public List<Employee> findEmployeesByName(
            @PathVariable(EmployeeAttributeConstants.EMP_FIRST_NAME) String firstName,
            @PathVariable(EmployeeAttributeConstants.EMP_LAST_NAME) String lastName) {

        return employeeService.filterEmployeesByName(firstName, lastName);
    }

    @GetMapping("/employees/week/{id}/{status}")
    public EmployeeModel findEmployeeTimesheetByWeeks(@PathVariable("id") int id, @PathVariable("status") String status) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Set<String> userRoles = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return employeeService.findEmployeeTimesheetByWeek(id, status, userRoles);
    }

}
