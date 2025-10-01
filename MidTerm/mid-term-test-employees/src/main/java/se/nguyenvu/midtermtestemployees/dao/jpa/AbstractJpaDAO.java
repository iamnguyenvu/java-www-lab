package se.nguyenvu.midtermtestemployees.dao.jpa;

import jakarta.persistence.EntityManager;
import se.nguyenvu.midtermtestemployees.dao.GenericDAO;

import java.util.Optional;

public class AbstractJpaDAO<T, ID> implements GenericDAO<T, ID> {
    protected EntityManager em;
    protected Class<T> cls;

    protected AbstractJpaDAO(EntityManager em, Class<T> cls) {
        this.em = em;
        this.cls = cls;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(em.find(cls, id));
    }

    @Override
    public Iterable<T> findAll() {
        return em.createQuery("select e from " + cls.getSimpleName() + " e", cls).getResultList();
    }

    @Override
    public T save(T t) {
        em.persist(t);
        return t;
    }

    @Override
    public T update(T t) {
        em.merge(t);
        return t;
    }

    @Override
    public void delete(ID id) {
        findById(id).ifPresent(em::remove);
    }
}
