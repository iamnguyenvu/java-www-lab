package com.nguyenvu.homeworkspringdatajpa.repository;

import com.nguyenvu.homeworkspringdatajpa.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepo extends JpaRepository<Department, Long> {
    List<Department> findByDeptName(String deptName);
}
