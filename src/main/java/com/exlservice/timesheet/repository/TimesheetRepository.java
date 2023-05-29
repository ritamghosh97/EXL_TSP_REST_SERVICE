package com.exlservice.timesheet.repository;

import com.exlservice.timesheet.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TimesheetRepository extends JpaRepository<Timesheet, Integer> {
    @Query("SELECT t FROM Timesheet t WHERE t.employee.id = :employeeId AND t.date BETWEEN :sDate AND :eDate")
    List<Timesheet> findTimesheetBetweenDates(@Param("employeeId") Integer id, @Param("sDate") LocalDate startDate, @Param("eDate") LocalDate endDate);
}
