package com.nguyenvu.homeworkspringdatajdbc.controller;

import com.nguyenvu.homeworkspringdatajdbc.model.Employee;
import com.nguyenvu.homeworkspringdatajdbc.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee emp = employeeService.save(employee);
        return new ResponseEntity<>(emp, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee existing = employeeService.findById(id);
        if (existing == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existing.setEmpName(employee.getEmpName());
        existing.setDob(employee.getDob());
        existing.setSalary(employee.getSalary());
        Employee updated = employeeService.save(existing);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        Employee emp = employeeService.findById(id);
        if (emp != null) {
            return new ResponseEntity<>(emp, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> emps = employeeService.findAll();
        return new ResponseEntity<>(emps, HttpStatus.OK);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Employee>> searchByName(@RequestParam String name) {
        List<Employee> emps = employeeService.findByEmpNameContainingIgnoreCase(name);
        return new ResponseEntity<>(emps, HttpStatus.OK);
    }

    @GetMapping("/search/salary")
    public ResponseEntity<List<Employee>> searchBySalary(@RequestParam BigDecimal salary) {
        List<Employee> emps = employeeService.findBySalaryGreaterThanEqual(salary);
        return new ResponseEntity<>(emps, HttpStatus.OK);
    }

    @GetMapping("/search/salary-range")
    public ResponseEntity<List<Employee>> searchBySalaryRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        List<Employee> emps = employeeService.findBySalaryBetween(min, max);
        return new ResponseEntity<>(emps, HttpStatus.OK);
    }

    @GetMapping("/search/dob-after")
    public ResponseEntity<List<Employee>> searchByDobAfter(@RequestParam Date dob) {
        List<Employee> emps = employeeService.findByDobAfter(dob);
        return new ResponseEntity<>(emps, HttpStatus.OK);
    }

    @GetMapping("/search/dob-before")
    public ResponseEntity<List<Employee>> searchByDobBefore(@RequestParam Date dob) {
        List<Employee> emps = employeeService.findByDobBefore(dob);
        return new ResponseEntity<>(emps, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        Employee emp = employeeService.findById(id);
        if (emp == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        employeeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
