package com.exlservice.timesheet.data.model;


public class TimesheetModel {
    private String date;

    private Integer hours;

    private int approvalStatus;

    public TimesheetModel(){

    }

    public TimesheetModel(String date, Integer hours, int approvalStatus) {
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

    public int getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    @Override
    public String toString() {
        return "TimesheetModel{" +
                "date='" + date + '\'' +
                ", hours=" + hours +
                ", approvalStatus=" + approvalStatus +
                '}';
    }
}
