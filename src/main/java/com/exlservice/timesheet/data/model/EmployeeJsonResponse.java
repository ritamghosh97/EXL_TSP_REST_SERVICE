package com.exlservice.timesheet.data.model;

import com.exlservice.timesheet.view.ignore.View;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EmployeeJsonResponse {

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
    private List<TimesheetJsonResponse> timesheet;

    private Set<String> roles;
    private Map<String, String> weekDatesToDay;

    public EmployeeJsonResponse(){

    }

    public EmployeeJsonResponse(int id, String firstName, String lastName, String email, Integer managerId, List<TimesheetJsonResponse> timesheet) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.managerId = managerId;
        this.timesheet = timesheet;
    }

    public EmployeeJsonResponse(int id, String firstName, String lastName, String email,
                                Integer managerId, List<TimesheetJsonResponse> timesheet,
                                Map<String, String> weekDatesToDay, Set<String> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.managerId = managerId;
        this.timesheet = timesheet;
        this.weekDatesToDay = weekDatesToDay;
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

    public List<TimesheetJsonResponse> getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(List<TimesheetJsonResponse> timesheet) {
        this.timesheet = timesheet;
    }

    public Map<String, String> getWeekDatesToDay() {
        return weekDatesToDay;
    }

    public void setWeekDatesToDay(Map<String, String> weekDatesToDay) {
        this.weekDatesToDay = weekDatesToDay;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "EmployeeJsonResponse{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", managerId=" + managerId +
                ", timesheet=" + timesheet +
                ", weekDatesToDay=" + weekDatesToDay +
                ", roles=" + roles +
                '}';
    }
}
