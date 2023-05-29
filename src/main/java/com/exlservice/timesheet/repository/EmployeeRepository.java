package com.exlservice.timesheet.repository;

import com.exlservice.timesheet.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e FROM Employee e WHERE e.managerId = :managerId")
    List<Employee> findEmployeesByManagerId(@Param("managerId") int managerId);


}
