package se.nguyenvu.midtermtestdrug.dao.jpa;

import se.nguyenvu.midtermtestdrug.dao.LoaiThuocDAO;
import se.nguyenvu.midtermtestdrug.model.LoaiThuoc;

public class LoaiThuocJpaDAO extends AbstractJpaDAO<LoaiThuoc, Long> implements LoaiThuocDAO {
    public LoaiThuocJpaDAO(jakarta.persistence.EntityManager em) {
        super(em, LoaiThuoc.class);
    }
}
