package com.exlservice.timesheet.data.model;


import com.exlservice.timesheet.view.ignore.View;
import com.fasterxml.jackson.annotation.JsonView;

public class TimesheetJsonResponse {

    @JsonView({View.Include.class, View.IncludeForFiltration.class})
    private String date;


    @JsonView({View.Include.class, View.IncludeForFiltration.class})
    private Integer hours;


    @JsonView({View.Include.class, View.IncludeForFiltration.class})
    private Integer approvalStatus;

    public TimesheetJsonResponse(){

    }

    public TimesheetJsonResponse(String date, Integer hours, Integer approvalStatus) {
        this.date = date;
        this.hours = hours;
        this.approvalStatus = approvalStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    @Override
    public String toString() {
        return "TimesheetJsonResponse{" +
                "date='" + date + '\'' +
                ", hours=" + hours +
                ", approvalStatus=" + approvalStatus +
                '}';
    }
}
