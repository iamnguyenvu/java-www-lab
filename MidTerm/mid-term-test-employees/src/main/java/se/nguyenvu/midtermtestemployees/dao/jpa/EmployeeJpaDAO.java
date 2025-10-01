package se.nguyenvu.midtermtestemployees.dao.jpa;

import jakarta.persistence.EntityManager;
import se.nguyenvu.midtermtestemployees.dao.EmployeeDAO;
import se.nguyenvu.midtermtestemployees.model.Employee;

import java.util.List;

public class EmployeeJpaDAO extends AbstractJpaDAO<Employee, Long> implements EmployeeDAO {
    public EmployeeJpaDAO(EntityManager em) {
        super(em, Employee.class);
    }

    @Override
    public List<Employee> findByDepartmentId(Long departmentId) {
        String jpql = "select e from Employee e where e.department.id = :departmentId";
        return em.createQuery(jpql, Employee.class)
                .setParameter("departmentId", departmentId)
                .getResultList();
    }
}
