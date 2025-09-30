package se.nguyenvu.midtermtestdrug.dao.jpa;

import jakarta.persistence.EntityManager;
import se.nguyenvu.midtermtestdrug.dao.ThuocDAO;
import se.nguyenvu.midtermtestdrug.model.Thuoc;

import java.util.List;

public class ThuocJpaDAO extends AbstractJpaDAO<Thuoc, Long> implements ThuocDAO {
    public ThuocJpaDAO(EntityManager em) {
        super(em, Thuoc.class);
    }
    public List<Thuoc> findByMaLoai(Long maLoai) throws Exception {
        String jpql = "select t from Thuoc t where t.loaiThuoc.maLoai = :maLoai";
        return em.createQuery(jpql, Thuoc.class)
                .setParameter("maLoai", maLoai)
                .getResultList();
    }

    @Override
    public List<Thuoc> findByKeyword(String keyword) throws Exception {
        String jpql = "select t from Thuoc t where t.tenThuoc like :keyword or t.loaiThuoc.tenLoai like :keyword";
        return em.createQuery(jpql, Thuoc.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }
}
