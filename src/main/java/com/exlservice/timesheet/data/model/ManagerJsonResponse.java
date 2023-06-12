package com.exlservice.timesheet.data.model;

import com.exlservice.timesheet.view.ignore.View;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;
import java.util.Set;

public class ManagerJsonResponse {

    @JsonView({View.Include.class, View.IncludeForFiltration.class})
    private Integer id;

    @JsonView({View.Include.class, View.IncludeForFiltration.class})
    private String firstName;

    @JsonView({View.Include.class, View.IncludeForFiltration.class})
    private String lastName;

    @JsonView({View.Include.class, View.IncludeForFiltration.class})
    private String email;

    @JsonView({View.Include.class, View.IncludeForFiltration.class})
    private Integer managerId;

    @JsonView({View.Include.class, View.IncludeForFiltration.class})
    private Set<String> roles;

    @JsonView(View.IncludeForFiltration.class)
    private List<String> weekDates;

    @JsonView({View.Include.class, View.IncludeForFiltration.class})
    private List<EmployeeJsonResponse> employees;

    public ManagerJsonResponse(){

    }

    public ManagerJsonResponse(Integer id, String firstName, String lastName,
                               String email, Integer managerId, Set<String> roles,
                               List<String> weekDates, List<EmployeeJsonResponse> employees) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.managerId = managerId;
        this.roles = roles;
        this.weekDates = weekDates;
        this.employees = employees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public List<String> getWeekDates() {
        return weekDates;
    }

    public void setWeekDates(List<String> weekDates) {
        this.weekDates = weekDates;
    }

    public List<EmployeeJsonResponse> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeJsonResponse> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "ManagerJsonResponse{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", managerId='" + managerId + '\'' +
                ", roles=" + roles +
                ", weekDates=" + weekDates +
                ", employees=" + employees +
                '}';
    }
}
