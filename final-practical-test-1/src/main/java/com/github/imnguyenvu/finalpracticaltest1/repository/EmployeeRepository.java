package com.github.imnguyenvu.finalpracticaltest1.repository;

import com.github.imnguyenvu.finalpracticaltest1.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    Page<Employee> findAllByActiveTrue(Pageable pageable);

    @Query("SELECT e FROM Employee e WHERE e.active = true AND " +
           "(LOWER(e.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Employee> searchByKeyword(String keyword, Pageable pageable);

    @Query("SELECT e FROM Employee e WHERE e.active = true AND e.department.id = :departmentId")
    Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable);
    
    @Query("SELECT e FROM Employee e WHERE e.active = true AND e.department.id = :departmentId AND " +
           "(LOWER(e.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Employee> searchByDepartmentAndKeyword(Long departmentId, String keyword, Pageable pageable);

    boolean existsByEmail(String email);
    
    boolean existsByEmailAndIdNot(String email, Long id);
    
    long countByDepartmentId(Long departmentId);
}
