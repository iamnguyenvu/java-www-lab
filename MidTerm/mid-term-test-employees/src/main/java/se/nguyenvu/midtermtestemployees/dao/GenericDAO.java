package se.nguyenvu.midtermtestemployees.dao;

import java.util.Optional;

public interface GenericDAO<T, ID> {
    Optional<T> findById(ID id);
    Iterable<T> findAll();
    T save(T t);
    T update(T t);
    void delete(ID id);
}
