package se.nguyenvu.midtermtestdrug.dao;

import se.nguyenvu.midtermtestdrug.model.Thuoc;

import java.util.List;

public interface ThuocDAO extends GenericDAO<Thuoc, Long> {
    List<Thuoc> findByMaLoai(Long maLoai) throws Exception;
    List<Thuoc> findByKeyword(String keyword) throws Exception;
}
