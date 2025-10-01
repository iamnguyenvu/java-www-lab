package se.nguyenvu.midtermtestdepartments.jpa;

import jakarta.persistence.EntityManager;
import se.nguyenvu.midtermtestdepartments.dao.GenericDAO;

import java.util.Iterator;
import java.util.Optional;

public class AbstractJpaDAO<T, ID> implements GenericDAO<T, ID> {
    protected EntityManager em;
    protected Class<T> cls;

    public AbstractJpaDAO(EntityManager em, Class<T> cls) {
        this.em = em;
        this.cls = cls;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(em.find(cls, id));
    }

    @Override
    public Iterator<T> findAll() {
        return em.createQuery("FROM " + cls.getName(), cls).getResultList().iterator();
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
