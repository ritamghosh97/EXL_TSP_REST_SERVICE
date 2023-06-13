package com.exlservice.timesheet.exception;

public class DatesOutOfRangeException extends RuntimeException {
    public DatesOutOfRangeException(String message, Throwable cause) {
        super(message, cause);
    }
    public DatesOutOfRangeException(String message) {
        super(message);
    }
    public DatesOutOfRangeException(Throwable cause) {
        super(cause);
    }
}
