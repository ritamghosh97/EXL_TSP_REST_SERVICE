package com.exlservice.timesheet.data.model;

import com.exlservice.timesheet.entity.Timesheet;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class EmployeeModel {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer managerId;
    private List<Timesheet> timesheet;
    private Set<String> roles;
    private List<LocalDate> weekDates;
    public EmployeeModel() {

    }

    public EmployeeModel(int id, String firstName, String lastName, String email,
                         Integer managerId, List<Timesheet> timesheet,
                         List<LocalDate> weekDates, Set<String> roles) {
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

    public List<Timesheet> getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(List<Timesheet> timesheet) {
        this.timesheet = timesheet;
    }

    public List<LocalDate> getWeekDates() {
        return weekDates;
    }

    public void setWeekDates(List<LocalDate> weekDates) {
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
