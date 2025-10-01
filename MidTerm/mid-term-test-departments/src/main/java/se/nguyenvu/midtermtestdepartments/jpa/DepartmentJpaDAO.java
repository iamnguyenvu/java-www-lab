package se.nguyenvu.midtermtestdepartments.jpa;

import jakarta.persistence.EntityManager;
import se.nguyenvu.midtermtestdepartments.dao.DepartmentDAO;
import se.nguyenvu.midtermtestdepartments.model.Department;

import java.util.List;

public class DepartmentJpaDAO extends AbstractJpaDAO<Department, Long> implements DepartmentDAO {
    public DepartmentJpaDAO(EntityManager em) {
        super(em, Department.class);
    }

    @Override
    public List<Department> findByName(String name) {
        String jpql = "select d from Department d where d.name like :name";
        return em.createQuery(jpql, Department.class)
                .setParameter("name", "%"+name+"%")
                .getResultList();
    }
}
