package com.exlservice.timesheet.service;

import com.exlservice.timesheet.repository.TimesheetRepository;
import com.exlservice.timesheet.entity.Timesheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimesheetServiceImpl implements TimesheetService{

    @Autowired
    private TimesheetRepository timesheetRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public List<Timesheet> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Timesheet findById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteById(int id) {
        // TODO Auto-generated method stub
    }

    @Override
    public List<Timesheet> findTimesheetBetweenDates(Integer id, String startDate, String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate startDateLocal = LocalDate.parse(startDate, formatter);
        LocalDate endDateLocal = LocalDate.parse(endDate, formatter);

        List<Timesheet> timesheet = timesheetRepository.findTimesheetBetweenDates(id, startDateLocal, endDateLocal);

        List<Timesheet> timesheetSortedByDate = timesheet
                .stream()
                .sorted(Comparator.comparing(Timesheet::getDate))
                .collect(Collectors.toList());

        return timesheetSortedByDate;
    }
}
