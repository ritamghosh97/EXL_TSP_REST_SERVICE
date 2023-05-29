package com.exlservice.timesheet.service;

import com.exlservice.timesheet.entity.Timesheet;

import java.util.List;

public interface TimesheetService {
    public List<Timesheet> findAll();

    public Timesheet findById(int id);

    public void deleteById(int id);

    public List<Timesheet> findTimesheetBetweenDates(Integer i, String startDate, String endDate);
}
