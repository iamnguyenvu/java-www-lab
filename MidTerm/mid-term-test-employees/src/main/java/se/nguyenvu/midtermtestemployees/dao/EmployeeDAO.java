package se.nguyenvu.midtermtestemployees.dao;

import se.nguyenvu.midtermtestemployees.model.Employee;

import java.util.List;

public interface EmployeeDAO extends GenericDAO<Employee, Long> {
    List<Employee> findByDepartmentId(Long departmentId);
    Long countByDepartmentId(Long departmentId);
//    List<Employee> findBySalaryGreaterThan(double salary);
//    List<Employee> findBySalaryLessThan(double salary);
//    List<Employee> findByName(String name);
}
