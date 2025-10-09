package com.nguyenvu.homeworkspringdatajpa.service;

import com.nguyenvu.homeworkspringdatajpa.dto.EmployeeDTO;
import com.nguyenvu.homeworkspringdatajpa.model.Department;
import com.nguyenvu.homeworkspringdatajpa.model.Employee;
import com.nguyenvu.homeworkspringdatajpa.repository.DepartmentRepo;
import com.nguyenvu.homeworkspringdatajpa.repository.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final DepartmentRepo departmentRepo;

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepo.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepo.findById(id).orElse(null);
        return employee != null ? convertToDTO(employee) : null;
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Department department = departmentRepo.findById(employeeDTO.getDeptId()).orElse(null);
        if (department == null) return null;
        
        Employee employee = Employee.builder()
                .empName(employeeDTO.getEmpName())
                .salary(employeeDTO.getSalary())
                .dob(employeeDTO.getDob())
                .department(department)
                .build();
        
        Employee saved = employeeRepo.save(employee);
        return convertToDTO(saved);
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepo.findById(id).orElse(null);
        if (employee == null) return null;
        
        employee.setEmpName(employeeDTO.getEmpName());
        employee.setSalary(employeeDTO.getSalary());
        employee.setDob(employeeDTO.getDob());
        
        if (employeeDTO.getDeptId() != null) {
            Department department = departmentRepo.findById(employeeDTO.getDeptId()).orElse(null);
            if (department != null) {
                employee.setDepartment(department);
            }
        }
        
        Employee updated = employeeRepo.save(employee);
        return convertToDTO(updated);
    }

    public void deleteEmployee(Long id) {
        employeeRepo.deleteById(id);
    }

    public List<EmployeeDTO> searchByName(String name) {
        return employeeRepo.findByEmpNameContainingIgnoreCase(name).stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<EmployeeDTO> findBySalaryRange(BigDecimal min, BigDecimal max) {
        return employeeRepo.findBySalaryBetween(min, max).stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<EmployeeDTO> findBySalaryGreaterThan(BigDecimal salary) {
        return employeeRepo.findBySalaryGreaterThan(salary).stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<EmployeeDTO> findBySalaryLessThan(BigDecimal salary) {
        return employeeRepo.findBySalaryLessThan(salary).stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<EmployeeDTO> findByAgeRange(Integer minAge, Integer maxAge) {
        return employeeRepo.findByAgeBetween(minAge, maxAge).stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<EmployeeDTO> findByDepartmentId(Long deptId) {
        return employeeRepo.findByDepartment_DeptId(deptId).stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<EmployeeDTO> findByDepartmentName(String deptName) {
        return employeeRepo.findByDepartment_DeptName(deptName).stream()
                .map(this::convertToDTO)
                .toList();
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        Integer age = null;
        if (employee.getDob() != null) {
            LocalDate birthDate = employee.getDob().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            age = LocalDate.now().getYear() - birthDate.getYear();
        }
        
        return EmployeeDTO.builder()
                .empId(employee.getEmpId())
                .empName(employee.getEmpName())
                .salary(employee.getSalary())
                .dob(employee.getDob())
                .age(age)
                .deptId(employee.getDepartment().getDeptId())
                .deptName(employee.getDepartment().getDeptName())
                .build();
    }
}
