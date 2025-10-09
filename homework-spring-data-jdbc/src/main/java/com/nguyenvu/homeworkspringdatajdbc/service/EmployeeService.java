package com.nguyenvu.homeworkspringdatajdbc.service;

import com.nguyenvu.homeworkspringdatajdbc.model.Employee;
import com.nguyenvu.homeworkspringdatajdbc.repository.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    public List<Employee> findAll() {
        return (List<Employee>) employeeRepo.findAll();
    }

    public Employee findById(Long id) {
        return employeeRepo.findById(id).orElse(null);
    }

    @Transactional
    public Employee save(Employee employee) {
        return employeeRepo.save(employee);
    }

    @Transactional
    public void delete(Long id) {
        employeeRepo.deleteById(id);
    }

    public List<Employee> findByEmpNameContainingIgnoreCase(String empName) {
        return employeeRepo.findByEmpNameContainingIgnoreCase(empName);
    }

    public List<Employee> findBySalaryGreaterThanEqual(BigDecimal salary) {
        return employeeRepo.findBySalaryGreaterThanEqual(salary);
    }

    public List<Employee> findBySalaryBetween(BigDecimal min, BigDecimal max) {
        return employeeRepo.findBySalaryBetween(min, max);
    }

    public List<Employee> findByDobAfter(Date dob) {
        return employeeRepo.findByDobAfter(dob);
    }

    public List<Employee> findByDobBefore(Date dob) {
        return employeeRepo.findByDobBefore(dob);
    }
}
