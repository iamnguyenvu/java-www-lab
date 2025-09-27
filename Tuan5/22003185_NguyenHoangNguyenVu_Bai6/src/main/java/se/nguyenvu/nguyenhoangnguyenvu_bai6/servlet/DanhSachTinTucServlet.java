package se.nguyenvu.nguyenhoangnguyenvu_bai6.servlet;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import se.nguyenvu.nguyenhoangnguyenvu_bai6.dao.DanhMucDAO;
import se.nguyenvu.nguyenhoangnguyenvu_bai6.dao.TinTucDAO;
import se.nguyenvu.nguyenhoangnguyenvu_bai6.model.DanhMuc;
import se.nguyenvu.nguyenhoangnguyenvu_bai6.model.TinTuc;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet({"/danh-sach-tin-tuc"})
public class DanhSachTinTucServlet extends HttpServlet {
    private TinTucDAO tinTucDAO;
    private DanhMucDAO danhMucDAO;

    @Resource(name = "jdbc/news_management")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        tinTucDAO = new TinTucDAO(dataSource);
        danhMucDAO = new DanhMucDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null) action = "list";

        switch (action) {
            case "list" -> {
                try {
//                    List<DanhMuc> danhMucList = Arrays.stream(req.getParameterValues("danhMucList"))
//                            .map(Integer::parseInt)
//                            .map(danhMucDAO::findByMaDM)
//                            .filter(Objects::nonNull)
//                            .collect(Collectors.toList());
//                    req.setAttribute("danhMucList", danhMucList);

                    Map<Integer, List<TinTuc>> listTinTucByDanhMuc = tinTucDAO.findAll().stream()
                                    .collect(Collectors.groupingBy(TinTuc::getMaDM, LinkedHashMap::new, Collectors.toList()));

                    req.setAttribute("listTinTucByDanhMuc", listTinTucByDanhMuc);

                    req.setAttribute("danhMucs", danhMucDAO.findAll());
                    req.setAttribute("tinTucs", tinTucDAO.findAll());
                    req.getRequestDispatcher("/DanhSachTinTuc.jsp").forward(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            case "new" -> {
                try {
                    req.setAttribute("danhMucs", danhMucDAO.findAll());
                    req.getRequestDispatcher("/TinTucForm.jsp").forward(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            case "edit" -> {
                try {
                    int maTT = Integer.parseInt(req.getParameter("maTT"));
                    TinTuc tinTuc = tinTucDAO.findByMaTT(maTT);
                    req.setAttribute("tinTuc", tinTuc);
                    req.setAttribute("danhMucs", danhMucDAO.findAll());
                    req.getRequestDispatcher("/TinTucForm.jsp").forward(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            case "delete" -> {
                try {
                    int maTT = Integer.parseInt(req.getParameter("maTT"));
                    tinTucDAO.delete(maTT);
                    req.setAttribute("danhMucs", danhMucDAO.findAll());
                    req.getRequestDispatcher("/QuanLyForm.jsp").forward(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            case "manage" -> {
                try {
                    req.setAttribute("danhMucs", danhMucDAO.findAll());
                    req.setAttribute("tinTucs", tinTucDAO.findAll());
                    req.getRequestDispatcher("/QuanLyForm.jsp").forward(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            case "view" -> {
                int maTT = Integer.parseInt(req.getParameter("maTT"));
                TinTuc tinTuc = tinTucDAO.findByMaTT(maTT);
                req.setAttribute("tinTuc", tinTuc);
                req.getRequestDispatcher("/").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String maTTStr = req.getParameter("maTT");
        int maTT = (maTTStr != null && !maTTStr.isEmpty()) ? Integer.parseInt(maTTStr) : 0;

        String tieuDe = req.getParameter("tieuDe");
        String noiDungTT = req.getParameter("noiDungTT");
        String lienKet = req.getParameter("lienKet");
        int maDM = Integer.parseInt(req.getParameter("maDM"));

        TinTuc tinTuc = TinTuc.builder()
                .maTT(maTT)
                .tieuDe(tieuDe)
                .noiDungTT(noiDungTT)
                .lienKet(lienKet)
                .maDM(maDM)
                .build();

        try {
            if(tinTucDAO.findByMaTT(maTT) == null) {
                tinTucDAO.save(tinTuc);
            }
            else tinTucDAO.update(tinTuc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("danh-sach-tin-tuc");
    }
}
