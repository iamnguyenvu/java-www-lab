package se.nguyenvu.midtermtestdrug.servlet;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import se.nguyenvu.midtermtestdrug.dao.LoaiThuocDAO;
import se.nguyenvu.midtermtestdrug.dao.ThuocDAO;
import se.nguyenvu.midtermtestdrug.dao.jpa.LoaiThuocJpaDAO;
import se.nguyenvu.midtermtestdrug.dao.jpa.ThuocJpaDAO;
import se.nguyenvu.midtermtestdrug.model.LoaiThuoc;
import se.nguyenvu.midtermtestdrug.model.Thuoc;
import se.nguyenvu.midtermtestdrug.util.JPAUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@WebServlet({"/quan-ly-thuoc"})
public class QuanLyThuocServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String action = Optional.of(req.getParameter("action"))
//                .or(() -> Optional.of("list")).get();

        String action = req.getParameter("action");
        if(action == null) action = "list";

        try(EntityManager em = JPAUtil.getEntityManager()) {
            ThuocDAO thuocDAO = new ThuocJpaDAO(em);
            LoaiThuocDAO loaiThuocDAO = new LoaiThuocJpaDAO(em);

            switch (action) {
                default -> {
                    req.setAttribute("listThuoc", thuocDAO.findAll());
                    req.setAttribute("loaiThuoc", loaiThuocDAO.findAll());
                    req.getRequestDispatcher("/DanhSachThuoc.jsp").forward(req, resp);
                }
                case "search" -> {
                    String keyword = req.getParameter("keyword");
                    req.setAttribute("listThuoc", thuocDAO.findByKeyword(keyword));
                    req.setAttribute("loaiThuoc", loaiThuocDAO.findAll());
                    req.getRequestDispatcher("/DanhSachThuoc.jsp").forward(req, resp);
                }
                case "insert" -> {
                    req.setAttribute("loaiThuoc", loaiThuocDAO.findAll());
                    req.getRequestDispatcher("/ThuocForm.jsp").forward(req, resp);
                }
                case "filter" -> {
                    String maLoaiStr = req.getParameter("maLoai");
                    if(maLoaiStr == null || maLoaiStr.isBlank()) {
                        req.setAttribute("listThuoc", thuocDAO.findAll());
                    }
                    else req.setAttribute("listThuoc", thuocDAO.findByMaLoai(Long.parseLong(maLoaiStr)));
                    req.setAttribute("loaiThuoc", loaiThuocDAO.findAll());
                    req.getRequestDispatcher("/DanhSachThuoc.jsp").forward(req, resp);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tenThuoc = req.getParameter("tenThuoc");
        BigDecimal gia = new BigDecimal(req.getParameter("gia"));
        int namSX = Integer.parseInt(req.getParameter("namSX"));
        Long maLoai = Long.parseLong(req.getParameter("maLoai"));

        try(EntityManager em = JPAUtil.getEntityManager()) {
            ThuocDAO thuocDAO = new ThuocJpaDAO(em);
            LoaiThuocDAO loaiThuocDAO = new LoaiThuocJpaDAO(em);

            em.getTransaction().begin();
            Thuoc thuoc = Thuoc.builder()
                    .tenThuoc(tenThuoc)
                    .gia(gia)
                    .namSX(namSX)
                    .loaiThuoc(em.getReference(LoaiThuoc.class, maLoai))
                    .build();

            thuocDAO.save(thuoc);
            em.getTransaction().commit();

            resp.sendRedirect(req.getContextPath() + "/quan-ly-thuoc");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
