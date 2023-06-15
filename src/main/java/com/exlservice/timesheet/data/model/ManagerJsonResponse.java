package com.exlservice.timesheet.data.model;

import com.exlservice.timesheet.view.ignore.View;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ManagerJsonResponse {

    @JsonView({View.EmployeeResponse.class, View.ManagerResponse.class, View.AdminResponse.class})
    private Integer id;

    @JsonView({View.EmployeeResponse.class, View.ManagerResponse.class, View.AdminResponse.class})
    private String firstName;

    @JsonView({View.EmployeeResponse.class, View.ManagerResponse.class, View.AdminResponse.class})
    private String lastName;

    @JsonView({View.EmployeeResponse.class, View.ManagerResponse.class, View.AdminResponse.class})
    private String email;

    @JsonView({View.EmployeeResponse.class, View.ManagerResponse.class, View.AdminResponse.class})
    private Integer managerId;

    @JsonView({View.EmployeeResponse.class, View.ManagerResponse.class, View.AdminResponse.class})
    private Set<String> roles;

    @JsonView(View.ManagerResponse.class)
    private Map<String, String> weekDatesToDay;

    @JsonView({View.EmployeeResponse.class, View.ManagerResponse.class, View.AdminResponse.class})
    private List<EmployeeJsonResponse> employees;

    public ManagerJsonResponse(){

    }

    public ManagerJsonResponse(Integer id, String firstName, String lastName, String email,
                               Integer managerId, Set<String> roles,
                               Map<String, String> weekDatesToDay,
                               List<EmployeeJsonResponse> employees) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.managerId = managerId;
        this.roles = roles;
        this.weekDatesToDay = weekDatesToDay;
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

    public List<EmployeeJsonResponse> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeJsonResponse> employees) {
        this.employees = employees;
    }

    public Map<String, String> getWeekDatesToDay() {
        return weekDatesToDay;
    }

    public void setWeekDatesToDay(Map<String, String> weekDatesToDay) {
        this.weekDatesToDay = weekDatesToDay;
    }

    @Override
    public String toString() {
        return "ManagerJsonResponse{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", managerId=" + managerId +
                ", roles=" + roles +
                ", weekDatesToDay=" + weekDatesToDay +
                ", employees=" + employees +
                '}';
    }
}
