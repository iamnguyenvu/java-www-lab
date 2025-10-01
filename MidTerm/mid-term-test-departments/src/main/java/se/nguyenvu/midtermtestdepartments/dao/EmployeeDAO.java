package se.nguyenvu.midtermtestdepartments.dao;

import se.nguyenvu.midtermtestdepartments.model.Employee;

import java.util.List;

public interface EmployeeDAO extends GenericDAO<Employee, Long> {
    List<Employee> findByDepartmentId(Long departmentId);
    Long countByDepartmentId(Long departmentId);
}
