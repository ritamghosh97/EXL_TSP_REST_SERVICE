package com.exlservice.timesheet.response.handler;

import com.exlservice.timesheet.constant.EmployeeAttributeConstants;
import com.exlservice.timesheet.entity.Employee;
import com.exlservice.timesheet.entity.Timesheet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Map<String, Object>> generateTimesheetResponse(Employee employee, List<Timesheet> timesheetResp) {
        Map<String, Object> responseMap = new LinkedHashMap<>();
        responseMap.put(EmployeeAttributeConstants.EMP_ID, employee.getId());
        responseMap.put(EmployeeAttributeConstants.EMP_FIRST_NAME, employee.getFirstName());
        responseMap.put(EmployeeAttributeConstants.EMP_LAST_NAME, employee.getLastName());
        responseMap.put(EmployeeAttributeConstants.EMP_EMAIL, employee.getEmail());
        responseMap.put(EmployeeAttributeConstants.EMP_TIMESHEET, timesheetResp);
        return new ResponseEntity<>(responseMap, HttpStatus.FOUND);
    }
}
