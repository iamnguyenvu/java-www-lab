package com.nguyenvu.homeworkspringdatamongo.controller;

import com.nguyenvu.homeworkspringdatamongo.dto.EmployeeDTO;
import com.nguyenvu.homeworkspringdatamongo.model.Employee;
import com.nguyenvu.homeworkspringdatamongo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    
    private final EmployeeService employeeService;
    
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }
    
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(employee));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable String id,
            @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search/name")
    public ResponseEntity<List<EmployeeDTO>> searchEmployeesByName(
            @RequestParam String name) {
        return ResponseEntity.ok(employeeService.searchEmployeesByName(name));
    }
    
    @GetMapping("/search/age")
    public ResponseEntity<List<EmployeeDTO>> searchEmployeesByAge(
            @RequestParam Integer age) {
        return ResponseEntity.ok(employeeService.searchEmployeesByAge(age));
    }

    @GetMapping("/search/age/greater")
    public ResponseEntity<List<EmployeeDTO>> searchEmployeesByAgeGreaterThan(@RequestParam Integer age) {
        return ResponseEntity.ok(employeeService.searchEmployeesByAge(age));
    }
    
    @GetMapping("/search/salary/greater")
    public ResponseEntity<List<EmployeeDTO>> searchEmployeesBySalaryGreaterThan(
            @RequestParam Integer salary) {
        return ResponseEntity.ok(employeeService.searchEmployeesBySalaryGreaterThan(salary));
    }
    @GetMapping("/search/salary/less")
    public ResponseEntity<List<EmployeeDTO>> searchEmployeesBySalaryLessThan(
            @RequestParam Integer salary) {
        return ResponseEntity.ok(employeeService.searchEmployeesBySalaryLessThan(salary));
    }
    @GetMapping("/search/salary/range")
    public ResponseEntity<List<EmployeeDTO>> searchEmployeesBySalaryRange(
            @RequestParam Integer minSalary,
            @RequestParam Integer maxSalary) {
        return ResponseEntity.ok(employeeService.searchEmployeesBySalaryRange(minSalary, maxSalary));
    }
    
    @GetMapping("/department/{deptId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartment(@PathVariable String deptId) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartment(deptId));
    }
}
