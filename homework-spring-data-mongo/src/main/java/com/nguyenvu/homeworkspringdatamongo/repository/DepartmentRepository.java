package com.nguyenvu.homeworkspringdatamongo.repository;

import com.nguyenvu.homeworkspringdatamongo.model.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {
    Optional<Department> findByDeptId(String deptId);
    List<Department> findByDeptNameContainingIgnoreCase(String deptName);
}
