package com.exlservice.timesheet.data.model;

import com.exlservice.timesheet.view.ignore.View;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdminJsonResponse {

    @JsonView(View.AdminResponse.class)
    private Integer id;

    @JsonView(View.AdminResponse.class)
    private String firstName;

    @JsonView(View.AdminResponse.class)
    private String lastName;

    @JsonView(View.AdminResponse.class)
    private String email;

    @JsonView(View.AdminResponse.class)
    private Integer managerId;

    @JsonView(View.AdminResponse.class)
    private Set<String> roles;

    @JsonView(View.AdminResponse.class)
    private Map<String, String> weekDatesToDay;

    @JsonView(View.AdminResponse.class)
    private List<ManagerJsonResponse> managers;

    public AdminJsonResponse() {
    }

    public AdminJsonResponse(Integer id, String firstName, String lastName,
                             String email, Integer managerId, Set<String> roles,
                             Map<String, String> weekDatesToDay,
                             List<ManagerJsonResponse> managers) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.managerId = managerId;
        this.roles = roles;
        this.weekDatesToDay = weekDatesToDay;
        this.managers = managers;
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

    public Map<String, String> getWeekDatesToDay() {
        return weekDatesToDay;
    }

    public void setWeekDatesToDay(Map<String, String> weekDatesToDay) {
        this.weekDatesToDay = weekDatesToDay;
    }

    public List<ManagerJsonResponse> getManagers() {
        return managers;
    }

    public void setManagers(List<ManagerJsonResponse> managers) {
        this.managers = managers;
    }

    @Override
    public String toString() {
        return "AdminJsonResponse{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", managerId=" + managerId +
                ", roles=" + roles +
                ", weekDatesToDay=" + weekDatesToDay +
                ", managers=" + managers +
                '}';
    }
}
