package com.exlservice.timesheet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name="timesheet")
public class Timesheet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer timesheetId;

    @Column(name = "date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    @Column(name = "hours")
    private Integer hours;

    @Column(name = "approval_status")
    private Integer approvalStatus;

    @ManyToOne(fetch = FetchType.LAZY ,cascade= {CascadeType.DETACH, CascadeType.REFRESH,
                                                CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "emp_id")
    @JsonIgnore
    private Employee employee;

    public Timesheet() {

    }

    public Timesheet(Integer id, LocalDate date, Integer hours, Employee employee) {
        this.timesheetId = id;
        this.date = date;
        this.hours = hours;
        this.employee = employee;
    }

    public Integer getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(Integer timesheetId) {
        this.timesheetId = timesheetId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Timesheet{" +
                "timesheetId=" + timesheetId +
                ", date=" + date +
                ", hours=" + hours +
                ", approvalStatus=" + approvalStatus +
                ", employee=" + employee +
                '}';
    }
}
