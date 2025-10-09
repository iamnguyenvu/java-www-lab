package com.nguyenvu.homeworkspringdatamongo.repository;

import com.nguyenvu.homeworkspringdatamongo.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Optional<Employee> findByEmpId(String empId);
    List<Employee> findByDeptId(String deptId);
    List<Employee> findByEmpNameContainingIgnoreCase(String empName);
    List<Employee> findBySalaryGreaterThanEqual(Integer salary);
    List<Employee> findBySalaryLessThanEqual(Integer salary);
    List<Employee> findBySalaryBetween(Integer minSalary, Integer maxSalary);
    List<Employee> findByAge(Integer age);
}
