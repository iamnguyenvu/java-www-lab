package se.nguyenvu.midtermtestdepartments.dao;

import se.nguyenvu.midtermtestdepartments.model.Department;

import java.util.List;

public interface DepartmentDAO extends GenericDAO<Department,Long> {
    List<Department> findByName(String name);
}
