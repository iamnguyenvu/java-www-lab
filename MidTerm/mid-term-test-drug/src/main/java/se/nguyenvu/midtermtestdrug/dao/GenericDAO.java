package se.nguyenvu.midtermtestdrug.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, ID> {
    Optional<T> findById(ID id) throws Exception;
    Iterable<T> findAll() throws Exception;
    T save(T entity) throws Exception;
    T update(T entity) throws Exception;
    void delete(ID id) throws Exception;
}
