package com.exlservice.timesheet.response.handler;

public class ExlTimesheetErrorResponse {
    private Integer errorCode;

    private String errorStatus;

    private String errorMessage;

    private Long timestamp;

    public ExlTimesheetErrorResponse(){

    }

    public ExlTimesheetErrorResponse(Integer errorCode, String errorStatus, String errorMessage, Long timestamp) {
        this.errorCode = errorCode;
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }
}
