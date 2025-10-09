package com.nguyenvu.homeworkspringdatamongo.controller;

import com.nguyenvu.homeworkspringdatamongo.dto.DepartmentDTO;
import com.nguyenvu.homeworkspringdatamongo.model.Department;
import com.nguyenvu.homeworkspringdatamongo.model.Employee;
import com.nguyenvu.homeworkspringdatamongo.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    
    private final DepartmentService departmentService;
    
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable String id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }
    
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(departmentService.createDepartment(department));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(
            @PathVariable String id,
            @RequestBody Department department) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, department));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable String id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<DepartmentDTO>> searchDepartmentsByName(
            @RequestParam String name) {
        return ResponseEntity.ok(departmentService.searchDepartmentsByName(name));
    }
    
    @GetMapping("/{id}/employees")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable String id) {
        return ResponseEntity.ok(departmentService.getEmployeesByDepartment(id));
    }
}
