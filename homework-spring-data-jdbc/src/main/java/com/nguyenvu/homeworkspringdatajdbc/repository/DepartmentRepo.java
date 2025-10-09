package com.nguyenvu.homeworkspringdatajdbc.repository;

import com.nguyenvu.homeworkspringdatajdbc.model.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DepartmentRepo extends CrudRepository<Department, Long> {
    Optional<Department> findByDeptName(String deptName);
}


