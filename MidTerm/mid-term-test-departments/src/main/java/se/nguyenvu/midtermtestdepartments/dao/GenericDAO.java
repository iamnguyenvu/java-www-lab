package se.nguyenvu.midtermtestdepartments.dao;

import java.util.Iterator;
import java.util.Optional;

public interface GenericDAO<T, ID> {
    Optional<T> findById(ID id);
    Iterator<T> findAll();
    T save(T t);
    T update(T t);
    void delete(ID id);
}
