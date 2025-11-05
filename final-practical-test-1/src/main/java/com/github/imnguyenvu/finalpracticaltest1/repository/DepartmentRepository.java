package com.github.imnguyenvu.finalpracticaltest1.repository;

import com.github.imnguyenvu.finalpracticaltest1.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Page<Department> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    Optional<Department> findByCode(String code);
    
    boolean existsByCode(String code);
    
    boolean existsByName(String name);
    
    boolean existsByCodeAndIdNot(String code, Long id);
    
    boolean existsByNameAndIdNot(String name, Long id);
    
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department.id = :departmentId")
    long countEmployeesByDepartmentId(Long departmentId);
}
