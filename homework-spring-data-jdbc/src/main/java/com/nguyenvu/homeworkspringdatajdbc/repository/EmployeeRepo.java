package com.nguyenvu.homeworkspringdatajdbc.repository;

import com.nguyenvu.homeworkspringdatajdbc.model.Employee;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface EmployeeRepo extends CrudRepository<Employee, Long> {
    List<Employee> findByEmpNameContainingIgnoreCase(@Param("empName") String empName);
    List<Employee> findBySalaryGreaterThanEqual(@Param("salary") BigDecimal salary);
    List<Employee> findBySalaryBetween(@Param("salary") BigDecimal min, @Param("salary") BigDecimal max);

    @Query("select * from employees where dob >= :dob")
    List<Employee> findByDobAfter(@Param("dob") Date dob);

    @Query("select * from employees where dob <= :dob")
    List<Employee> findByDobBefore(@Param("dob") Date dob);
}
