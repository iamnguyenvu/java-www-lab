package com.nguyenvu.homeworkspringdatajpa.repository;

import com.nguyenvu.homeworkspringdatajpa.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    List<Employee> findByEmpNameContainingIgnoreCase(String empName);
    List<Employee> findBySalaryBetween(BigDecimal min, BigDecimal max);
    List<Employee> findBySalaryGreaterThan(BigDecimal salary);
    List<Employee> findBySalaryLessThan(BigDecimal salary);

    @Query("SELECT e FROM Employee e WHERE YEAR(CURRENT_DATE) - YEAR(e.dob) BETWEEN :min AND :max")
    List<Employee> findByAgeBetween(@Param("min") Integer min, @Param("max") Integer max);

    List<Employee> findByDepartment_DeptId(Long deptId);
    
    List<Employee> findByDepartment_DeptName(String deptName);
}
