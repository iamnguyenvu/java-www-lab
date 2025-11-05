package com.github.imnguyenvu.finalpracticaltest1.service;

import com.github.imnguyenvu.finalpracticaltest1.dto.EmployeeForm;
import com.github.imnguyenvu.finalpracticaltest1.entity.Department;
import com.github.imnguyenvu.finalpracticaltest1.entity.Employee;
import com.github.imnguyenvu.finalpracticaltest1.repository.DepartmentRepository;
import com.github.imnguyenvu.finalpracticaltest1.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public Page<Employee> search(String keyword, Long departmentId, Pageable pageable) {
        boolean hasKeyword = keyword != null && !keyword.isBlank();
        
        if (departmentId != null && hasKeyword) {
            return employeeRepository.searchByDepartmentAndKeyword(departmentId, keyword.trim(), pageable);
        } else if (departmentId != null) {
            return employeeRepository.findByDepartmentId(departmentId, pageable);
        } else if (hasKeyword) {
            return employeeRepository.searchByKeyword(keyword.trim(), pageable);
        }
        
        return employeeRepository.findAllByActiveTrue(pageable);
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với ID: " + id));
    }

    @Transactional
    public Employee create(EmployeeForm form) {
        if (employeeRepository.existsByEmail(form.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }
        
        if (form.getHireDate().isAfter(LocalDate.now())) {
            throw new RuntimeException("Ngày vào làm không được sau hôm nay");
        }
        
        Department department = departmentRepository.findById(form.getDepartmentId())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban"));
        
        Employee employee = Employee.builder()
            .fullName(form.getFullName().trim())
            .email(form.getEmail().trim())
            .salary(form.getSalary())
            .hireDate(form.getHireDate())
            .department(department)
            .active(form.getActive() != null ? form.getActive() : true)
            .build();
            
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee update(Long id, EmployeeForm form) {
        Employee employee = findById(id);
        
        if (!employee.getEmail().equals(form.getEmail()) && 
            employeeRepository.existsByEmail(form.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }
        
        if (form.getHireDate().isAfter(LocalDate.now())) {
            throw new RuntimeException("Ngày vào làm không được sau hôm nay");
        }
        
        Department department = departmentRepository.findById(form.getDepartmentId())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban"));
        
        employee.setFullName(form.getFullName().trim());
        employee.setEmail(form.getEmail().trim());
        employee.setSalary(form.getSalary());
        employee.setHireDate(form.getHireDate());
        employee.setDepartment(department);
        if (form.getActive() != null) {
            employee.setActive(form.getActive());
        }
        
        return employeeRepository.save(employee);
    }

    @Transactional
    public void delete(Long id) {
        Employee employee = findById(id);
        employee.setActive(false);
        employeeRepository.save(employee);
    }
}
