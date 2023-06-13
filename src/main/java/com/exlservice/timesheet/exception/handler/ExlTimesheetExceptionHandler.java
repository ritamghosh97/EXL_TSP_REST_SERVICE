package com.exlservice.timesheet.exception.handler;

import com.exlservice.timesheet.exception.DatesOutOfRangeException;
import com.exlservice.timesheet.response.handler.ExlTimesheetErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExlTimesheetExceptionHandler {

    // exception handler for handling DatesOutOfRangeException
    @ExceptionHandler
    public ResponseEntity<ExlTimesheetErrorResponse> handleDatesOutOfRangeException(DatesOutOfRangeException e){
        ExlTimesheetErrorResponse errorResponse = new ExlTimesheetErrorResponse();

        errorResponse.setErrorCode(HttpStatus.NOT_ACCEPTABLE.value());
        errorResponse.setErrorStatus(HttpStatus.NOT_ACCEPTABLE.toString());
        errorResponse.setErrorMessage(e.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

}
