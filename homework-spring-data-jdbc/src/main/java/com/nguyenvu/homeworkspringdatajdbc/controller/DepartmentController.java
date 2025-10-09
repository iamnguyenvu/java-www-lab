package com.nguyenvu.homeworkspringdatajdbc.controller;

import com.nguyenvu.homeworkspringdatajdbc.model.Department;
import com.nguyenvu.homeworkspringdatajdbc.service.DepartmentService;
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

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department dept = departmentService.saveDepartment(department);
        return new ResponseEntity<>(dept, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        Department existing = departmentService.getDepartmentById(id);
        if (existing == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existing.setDeptName(department.getDeptName());
        Department updated = departmentService.saveDepartment(existing);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long id) {
        Department dept = departmentService.getDepartmentById(id);
        if (dept != null) {
            return new ResponseEntity<>(dept, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> depts = departmentService.getAllDepartments();
        return new ResponseEntity<>(depts, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Department> getDepartmentByName(@RequestParam String name) {
        Department dept = departmentService.findByDeptName(name);
        if (dept != null) {
            return new ResponseEntity<>(dept, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        Department dept = departmentService.getDepartmentById(id);
        if (dept == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
