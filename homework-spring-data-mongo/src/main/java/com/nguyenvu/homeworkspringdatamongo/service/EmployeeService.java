package com.nguyenvu.homeworkspringdatamongo.service;

import com.nguyenvu.homeworkspringdatamongo.dto.EmployeeDTO;
import com.nguyenvu.homeworkspringdatamongo.model.Department;
import com.nguyenvu.homeworkspringdatamongo.model.Employee;
import com.nguyenvu.homeworkspringdatamongo.repository.DepartmentRepository;
import com.nguyenvu.homeworkspringdatamongo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public EmployeeDTO getEmployeeById(String id) {
        Employee employee = employeeRepository.findByEmpId(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        return convertToDTO(employee);
    }
    
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    public Employee updateEmployee(String id, Employee employeeDetails) {
        Employee employee = employeeRepository.findByEmpId(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employee.setEmpName(employeeDetails.getEmpName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setAge(employeeDetails.getAge());
        employee.setStatus(employeeDetails.getStatus());
        employee.setDeptId(employeeDetails.getDeptId());
        employee.setSalary(employeeDetails.getSalary());
        return employeeRepository.save(employee);
    }
    
    public void deleteEmployee(String id) {
        Employee employee = employeeRepository.findByEmpId(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }
    
    public List<EmployeeDTO> searchEmployeesByName(String name) {
        return employeeRepository.findByEmpNameContainingIgnoreCase(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<EmployeeDTO> searchEmployeesByAge(Integer age) {
        return employeeRepository.findByAge(age).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<EmployeeDTO> searchEmployeesBySalaryGreaterThan(Integer salary) {
        return employeeRepository.findBySalaryGreaterThanEqual(salary).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<EmployeeDTO> searchEmployeesBySalaryLessThan(Integer salary) {
        return employeeRepository.findBySalaryLessThanEqual(salary).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<EmployeeDTO> searchEmployeesBySalaryRange(Integer minSalary, Integer maxSalary) {
        return employeeRepository.findBySalaryBetween(minSalary, maxSalary).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<EmployeeDTO> getEmployeesByDepartment(String deptId) {
        return employeeRepository.findByDeptId(deptId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private EmployeeDTO convertToDTO(Employee employee) {
        String deptName = null;
        if (employee.getDeptId() != null) {
            deptName = departmentRepository.findByDeptId(employee.getDeptId())
                    .map(d -> d.getDeptName() != null ? d.getDeptName() : d.getName())
                    .orElse(null);
        }
        return EmployeeDTO.builder()
                .empId(employee.getEmpId())
                .empName(employee.getEmpName())
                .email(employee.getEmail())
                .age(employee.getAge())
                .salary(employee.getSalary())
                .status(employee.getStatus())
                .deptId(employee.getDeptId())
                .deptName(deptName)
                .build();
    }
}
