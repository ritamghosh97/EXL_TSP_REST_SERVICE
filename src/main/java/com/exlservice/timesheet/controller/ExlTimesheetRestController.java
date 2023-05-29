package com.exlservice.timesheet.controller;

import com.exlservice.timesheet.constant.EmployeeAttributeConstants;
import com.exlservice.timesheet.constant.TimesheetApiCommonConstants;
import com.exlservice.timesheet.entity.Employee;
import com.exlservice.timesheet.entity.Timesheet;
import com.exlservice.timesheet.response.handler.ResponseHandler;
import com.exlservice.timesheet.service.EmployeeService;
import com.exlservice.timesheet.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exl-timesheet-api")
public class ExlTimesheetRestController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TimesheetService timesheetService;

    @GetMapping("/employees")
    public List<Employee> findAllEmployees(){
        return employeeService.findAll();
    }

    @GetMapping("/employees/manager/{managerId}")
    public List<Employee> findEmployeesByManagerId(
                        @PathVariable(EmployeeAttributeConstants.EMP_MANAGER_ID) int theId){
        return employeeService.findEmployeesByManagerId(theId);
    }

    @GetMapping("/employees/{employeeId}/{startDate}/{endDate}")
    public ResponseEntity<Map<String, Object>> findEmployeeTimesheetWithinDateRange(
                                                @PathVariable(TimesheetApiCommonConstants.EMPLOYEE_ID) int theId,
                                                @PathVariable(TimesheetApiCommonConstants.START_DATE) String startDate,
                                                @PathVariable(TimesheetApiCommonConstants.END_DATE) String endDate){

        Employee employee = employeeService.findById(theId);

        List<Timesheet> timesheet = timesheetService.findTimesheetBetweenDates(theId, startDate, endDate);

        return ResponseHandler.generateTimesheetResponse(employee, timesheet);
    }

    @GetMapping("/employees/manager/{managerId}/{startDate}/{endDate}")
    public List<Employee> findEmployeesByManagerIdWithinDateRange(
                          @PathVariable(EmployeeAttributeConstants.EMP_MANAGER_ID) int theId,
                          @PathVariable(TimesheetApiCommonConstants.START_DATE) String startDate,
                          @PathVariable(TimesheetApiCommonConstants.END_DATE) String endDate) {

        return employeeService.filterEmployeesTimesheetByDateRangeUnderManager(theId, startDate, endDate);
    }

}
