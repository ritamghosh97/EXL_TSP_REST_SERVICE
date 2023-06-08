package com.exlservice.timesheet.data.model;

import com.exlservice.timesheet.view.ignore.View;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;
import java.util.Set;

public class EmployeeModel {

    @JsonView({View.Include.class, View.IncludeForFiltration.class})
    private int id;

    @JsonView({View.Include.class, View.IncludeForFiltration.class})
    private String firstName;

    @JsonView({View.Include.class, View.IncludeForFiltration.class})
    private String lastName;

    @JsonView({View.Include.class, View.IncludeForFiltration.class})
    private String email;

    private Integer managerId;

    @JsonView({View.Include.class, View.IncludeForFiltration.class})
    private List<TimesheetModel> timesheet;

    private Set<String> roles;
    private List<String> weekDates;

    public EmployeeModel(){

    }

    public EmployeeModel(int id, String firstName, String lastName, String email, Integer managerId, List<TimesheetModel> timesheet) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.managerId = managerId;
        this.timesheet = timesheet;
    }

    public EmployeeModel(int id, String firstName, String lastName, String email,
                         Integer managerId, List<TimesheetModel> timesheet,
                         List<String> weekDates, Set<String> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.managerId = managerId;
        this.timesheet = timesheet;
        this.weekDates = weekDates;
        this.roles = roles;
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

    public List<String> getWeekDates() {
        return weekDates;
    }

    public void setWeekDates(List<String> weekDates) {
        this.weekDates = weekDates;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "EmployeeModel{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", managerId=" + managerId +
                ", timesheet=" + timesheet +
                ", weekDates=" + weekDates +
                ", roles=" + roles +
                '}';
    }
}
