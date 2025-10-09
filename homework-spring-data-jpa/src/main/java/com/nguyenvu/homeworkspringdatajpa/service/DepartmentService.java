package com.nguyenvu.homeworkspringdatajpa.service;

import com.nguyenvu.homeworkspringdatajpa.dto.DepartmentDTO;
import com.nguyenvu.homeworkspringdatajpa.dto.EmployeeDTO;
import com.nguyenvu.homeworkspringdatajpa.model.Department;
import com.nguyenvu.homeworkspringdatajpa.model.Employee;
import com.nguyenvu.homeworkspringdatajpa.repository.DepartmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentService {
    private final DepartmentRepo departmentRepo;

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepo.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepo.findById(id).orElse(null);
        return department != null ? convertToDTOWithEmployees(department) : null;
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = Department.builder()
                .deptName(departmentDTO.getDeptName())
                .build();
        
        Department saved = departmentRepo.save(department);
        return convertToDTO(saved);
    }

    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Department department = departmentRepo.findById(id).orElse(null);
        if (department == null) return null;
        
        department.setDeptName(departmentDTO.getDeptName());
        Department updated = departmentRepo.save(department);
        return convertToDTO(updated);
    }

    public void deleteDepartment(Long id) {
        departmentRepo.deleteById(id);
    }

    public List<DepartmentDTO> searchByName(String name) {
        return departmentRepo.findByDeptName(name).stream()
                .map(this::convertToDTO)
                .toList();
    }

    public DepartmentDTO getDepartmentWithEmployees(Long id) {
        Department department = departmentRepo.findById(id).orElse(null);
        return department != null ? convertToDTOWithEmployees(department) : null;
    }

    private DepartmentDTO convertToDTO(Department department) {
        return DepartmentDTO.builder()
                .deptId(department.getDeptId())
                .deptName(department.getDeptName())
                .empCount(department.getEmployees() != null ? department.getEmployees().size() : 0)
                .build();
    }

    private DepartmentDTO convertToDTOWithEmployees(Department department) {
        List<EmployeeDTO> employeeDTOs = department.getEmployees().stream()
                .map(this::convertEmployeeToDTO)
                .toList();
        
        return DepartmentDTO.builder()
                .deptId(department.getDeptId())
                .deptName(department.getDeptName())
                .empCount(department.getEmployees().size())
                .employees(employeeDTOs)
                .build();
    }

    private EmployeeDTO convertEmployeeToDTO(Employee employee) {
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
