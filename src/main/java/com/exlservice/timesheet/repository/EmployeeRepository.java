package com.exlservice.timesheet.repository;

import com.exlservice.timesheet.constant.EmployeeAttributeConstants;
import com.exlservice.timesheet.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e FROM Employee e WHERE e.managerId = :managerId")
    List<Employee> findEmployeesByManagerId(@Param("managerId") int managerId);


    @Query("SELECT e FROM Employee e WHERE LOWER(firstName) LIKE :firstName OR LOWER(lastName) LIKE :lastName")
    List<Employee> findEmployeesByName(
            @Param(EmployeeAttributeConstants.EMP_FIRST_NAME) String likeFirstName,
            @Param(EmployeeAttributeConstants.EMP_LAST_NAME) String likeLastName);

    @Query("SELECT e1 FROM Employee e1 WHERE e1.id IN (SELECT DISTINCT e2.managerId FROM Employee e2)")
    List<Employee> findAllManagers();

    @Query("SELECT e1 FROM Employee e1 WHERE e1.id IN (SELECT DISTINCT e2.managerId FROM Employee e2) AND LOWER(CONCAT(e1.firstName,' ',e1.lastName)) LIKE :name")
    List<Employee> findManagersByName(@Param("name") String managerName);

    @Query(value = "SELECT r.role FROM roles r WHERE r.emp_id=:employeeId", nativeQuery = true)
    Set<String> findUserRoles(@Param("employeeId") int id);

    @Query("SELECT e FROM Employee e WHERE e.managerId=:managerId AND LOWER(CONCAT(e.firstName,' ',e.lastName)) LIKE :name")
    List<Employee> findEmployeesByManagerIdAndName(@Param("managerId") int theId, @Param("name") String employeeName);
}
