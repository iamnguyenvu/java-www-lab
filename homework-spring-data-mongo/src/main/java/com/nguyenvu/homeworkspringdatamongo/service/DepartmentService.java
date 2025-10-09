package com.nguyenvu.homeworkspringdatamongo.service;

import com.nguyenvu.homeworkspringdatamongo.dto.DepartmentDTO;
import com.nguyenvu.homeworkspringdatamongo.model.Department;
import com.nguyenvu.homeworkspringdatamongo.model.Employee;
import com.nguyenvu.homeworkspringdatamongo.repository.DepartmentRepository;
import com.nguyenvu.homeworkspringdatamongo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public DepartmentDTO getDepartmentById(String id) {
        Department department = departmentRepository.findByDeptId(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        return convertToDTO(department);
    }
    
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }
    
    public Department updateDepartment(String id, Department departmentDetails) {
        Department department = departmentRepository.findByDeptId(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        department.setDeptName(departmentDetails.getDeptName());
        return departmentRepository.save(department);
    }
    
    public void deleteDepartment(String id) {
        Department department = departmentRepository.findByDeptId(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        departmentRepository.delete(department);
    }
    
    public List<DepartmentDTO> searchDepartmentsByName(String name) {
        return departmentRepository.findByDeptNameContainingIgnoreCase(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<Employee> getEmployeesByDepartment(String deptId) {
        return employeeRepository.findByDeptId(deptId);
    }
    
    private DepartmentDTO convertToDTO(Department department) {
        int employeeCount = 0;
        if (department.getDeptId() != null) {
            employeeCount = employeeRepository.findByDeptId(department.getDeptId()).size();
        }
        return DepartmentDTO.builder()
                .deptId(department.getDeptId())
                .deptName(department.getDeptName() != null ? department.getDeptName() : department.getName())
                .employeeCount(employeeCount)
                .build();
    }
}
