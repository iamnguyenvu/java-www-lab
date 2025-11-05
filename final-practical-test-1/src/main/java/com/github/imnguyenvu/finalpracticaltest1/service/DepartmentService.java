package com.github.imnguyenvu.finalpracticaltest1.service;

import com.github.imnguyenvu.finalpracticaltest1.dto.DepartmentForm;
import com.github.imnguyenvu.finalpracticaltest1.entity.Department;
import com.github.imnguyenvu.finalpracticaltest1.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public Page<Department> search(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return departmentRepository.findAll(pageable);
        }
        return departmentRepository.findByNameContainingIgnoreCase(keyword.trim(), pageable);
    }

    public Department findById(Long id) {
        return departmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban với ID: " + id));
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Transactional
    public Department create(DepartmentForm form) {
        String code = form.getCode().trim().toUpperCase();
        String name = form.getName().trim();
        
        if (departmentRepository.existsByCode(code)) {
            throw new RuntimeException("Mã phòng ban đã tồn tại");
        }
        
        if (departmentRepository.existsByName(name)) {
            throw new RuntimeException("Tên phòng ban đã tồn tại");
        }
        
        Department department = Department.builder()
            .code(code)
            .name(name)
            .description(form.getDescription())
            .createdAt(Instant.now())
            .build();
            
        return departmentRepository.save(department);
    }

    @Transactional
    public Department update(Long id, DepartmentForm form) {
        Department department = findById(id);
        
        String code = form.getCode().trim().toUpperCase();
        String name = form.getName().trim();
        
        if (!department.getCode().equals(code) && departmentRepository.existsByCode(code)) {
            throw new RuntimeException("Mã phòng ban đã tồn tại");
        }
        
        if (!department.getName().equals(name) && departmentRepository.existsByName(name)) {
            throw new RuntimeException("Tên phòng ban đã tồn tại");
        }
        
        department.setCode(code);
        department.setName(name);
        department.setDescription(form.getDescription());
        
        return departmentRepository.save(department);
    }

    @Transactional
    public void delete(Long id) {
        long employeeCount = departmentRepository.countEmployeesByDepartmentId(id);
        if (employeeCount > 0) {
            throw new RuntimeException("Không thể xóa phòng ban vì còn " + employeeCount + " nhân viên trực thuộc");
        }
        departmentRepository.deleteById(id);
    }

    public long getEmployeeCount(Long departmentId) {
        return departmentRepository.countEmployeesByDepartmentId(departmentId);
    }
}
