package com.exlservice.timesheet.repository;

import com.exlservice.timesheet.constant.EmployeeAttributeConstants;
import com.exlservice.timesheet.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e FROM Employee e WHERE e.managerId = :managerId")
    List<Employee> findEmployeesByManagerId(@Param("managerId") int managerId);


    @Query("SELECT e FROM Employee e WHERE LOWER(firstName) LIKE :firstName OR LOWER(lastName) LIKE :lastName")
    List<Employee> findEmployeesByName(
            @Param(EmployeeAttributeConstants.EMP_FIRST_NAME) String likeFirstName,
            @Param(EmployeeAttributeConstants.EMP_LAST_NAME) String likeLastName);
}
