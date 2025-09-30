package se.nguyenvu.midtermtestdrug.dao.jpa;

import jakarta.persistence.EntityManager;
import se.nguyenvu.midtermtestdrug.dao.GenericDAO;

import java.util.Optional;

public class AbstractJpaDAO<T, ID> implements GenericDAO<T, ID> {
    protected EntityManager em;
    protected Class<T> cls;

    protected AbstractJpaDAO(EntityManager em, Class<T> cls) {
        this.em = em;
        this.cls = cls;
    }

    @Override
    public Optional<T> findById(ID id) throws Exception {
        return Optional.ofNullable(em.find(cls, id));
    }

    @Override
    public Iterable<T> findAll() throws Exception {
        return em.createQuery("select  e from " + cls.getSimpleName() + " e", cls).getResultList();
    }

    @Override
    public T save(T entity) throws Exception {
        em.persist(entity);
        return entity;
    }

    @Override
    public T update(T entity) throws Exception {
        em.merge(entity);
        return entity;
    }

    @Override
    public void delete(ID id) throws Exception {
        findById(id).ifPresent(em::remove);
    }
}
