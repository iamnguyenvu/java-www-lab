package se.nguyenvu.midtermtestemployees.dao.jpa;

import jakarta.persistence.EntityManager;
import se.nguyenvu.midtermtestemployees.dao.DepartmentDAO;
import se.nguyenvu.midtermtestemployees.model.Department;

import java.util.List;

public class DepartmentJpaDAO extends AbstractJpaDAO<Department, Long> implements DepartmentDAO {
    public DepartmentJpaDAO(EntityManager em) {
        super(em, Department.class);
    }

    @Override
    public List<Department> findByName(String name) {
        String jpql = "SELECT d FROM Department d WHERE d.name like :name";
        return em.createQuery(jpql, Department.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }
}
