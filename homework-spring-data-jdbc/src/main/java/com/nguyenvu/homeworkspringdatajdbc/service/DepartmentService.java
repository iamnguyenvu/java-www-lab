package com.nguyenvu.homeworkspringdatajdbc.service;

import com.nguyenvu.homeworkspringdatajdbc.model.Department;
import com.nguyenvu.homeworkspringdatajdbc.repository.DepartmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepo departmentRepo;

    public List<Department> getAllDepartments() {
        return (List<Department>) departmentRepo.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepo.findById(id).orElse(null);
    }

    @Transactional
    public Department saveDepartment(Department department) {
        return departmentRepo.save(department);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        departmentRepo.deleteById(id);
    }

    public Department findByDeptName(String deptName) {
        return departmentRepo.findByDeptName(deptName).orElse(null);
    }
}
