package com.nguyenvu.homeworkspringdatajpa.controller;

import com.nguyenvu.homeworkspringdatajpa.dto.EmployeeDTO;
import com.nguyenvu.homeworkspringdatajpa.service.EmployeeService;
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
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(employeeDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<EmployeeDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(employeeService.searchByName(name));
    }

    @GetMapping("/search/salary-range")
    public ResponseEntity<List<EmployeeDTO>> findBySalaryRange(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        return ResponseEntity.ok(employeeService.findBySalaryRange(min, max));
    }

    @GetMapping("/search/salary-greater-than")
    public ResponseEntity<List<EmployeeDTO>> findBySalaryGreaterThan(
            @RequestParam BigDecimal salary) {
        return ResponseEntity.ok(employeeService.findBySalaryGreaterThan(salary));
    }

    @GetMapping("/search/salary-less-than")
    public ResponseEntity<List<EmployeeDTO>> findBySalaryLessThan(
            @RequestParam BigDecimal salary) {
        return ResponseEntity.ok(employeeService.findBySalaryLessThan(salary));
    }

    @GetMapping("/search/age-range")
    public ResponseEntity<List<EmployeeDTO>> findByAgeRange(
            @RequestParam Integer minAge,
            @RequestParam Integer maxAge) {
        return ResponseEntity.ok(employeeService.findByAgeRange(minAge, maxAge));
    }

    @GetMapping("/department/{deptId}")
    public ResponseEntity<List<EmployeeDTO>> findByDepartmentId(@PathVariable Long deptId) {
        return ResponseEntity.ok(employeeService.findByDepartmentId(deptId));
    }

    @GetMapping("/department/name")
    public ResponseEntity<List<EmployeeDTO>> findByDepartmentName(@RequestParam String deptName) {
        return ResponseEntity.ok(employeeService.findByDepartmentName(deptName));
    }
}
