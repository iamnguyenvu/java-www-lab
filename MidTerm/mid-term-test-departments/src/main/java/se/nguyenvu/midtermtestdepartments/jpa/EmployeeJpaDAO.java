package se.nguyenvu.midtermtestdepartments.jpa;

import jakarta.persistence.EntityManager;
import se.nguyenvu.midtermtestdepartments.dao.EmployeeDAO;
import se.nguyenvu.midtermtestdepartments.model.Employee;

import java.util.List;

public class EmployeeJpaDAO extends AbstractJpaDAO<Employee, Long> implements EmployeeDAO {
    public EmployeeJpaDAO(EntityManager em) {
        super(em, Employee.class);
    }

    @Override
    public List<Employee> findByDepartmentId(Long departmentId) {
        String jpql = "select e from Employee e where e.department.id = :deptId";
        return em.createQuery(jpql, Employee.class)
                .setParameter("deptId", departmentId)
                .getResultList();
    }

    @Override
    public Long countByDepartmentId(Long departmentId) {
        String jpql = "select count(e) from Employee e where e.department.id = :deptId";
        return em.createQuery(jpql, Long.class)
                .setParameter("deptId", departmentId)
                .getSingleResult();
    }
}
