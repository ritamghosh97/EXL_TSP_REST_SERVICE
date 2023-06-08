package com.exlservice.timesheet.data.model;

import java.util.List;
import java.util.Set;

public class EmployeeModelForManager {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer managerId;
    private List<TimesheetModel> timesheet;

    public EmployeeModelForManager(){

    }

    public EmployeeModelForManager(int id, String firstName, String lastName, String email,
                         Integer managerId, List<TimesheetModel> timesheet) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.managerId = managerId;
        this.timesheet = timesheet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public List<TimesheetModel> getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(List<TimesheetModel> timesheet) {
        this.timesheet = timesheet;
    }

    @Override
    public String toString() {
        return "EmployeeModelForManager{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", managerId=" + managerId +
                ", timesheet=" + timesheet +
                '}';
    }
}
