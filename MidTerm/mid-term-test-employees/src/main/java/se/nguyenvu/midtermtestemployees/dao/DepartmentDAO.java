package se.nguyenvu.midtermtestemployees.dao;

import se.nguyenvu.midtermtestemployees.model.Department;

import java.util.List;

public interface DepartmentDAO extends GenericDAO<Department, Long> {
    List<Department> findByName(String name);
}
