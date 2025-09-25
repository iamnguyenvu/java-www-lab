package se.nguyenvu.nguyenhoangnguyenvu_bai6.servlet;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import se.nguyenvu.nguyenhoangnguyenvu_bai6.dao.TinTucDAO;

import javax.sql.DataSource;
import java.io.IOException;

@WebServlet({"/danh-sach-tin-tuc"})
public class DanhSachTinTucQuanLy extends HttpServlet {
    private TinTucDAO tinTucDAO;

    @Resource(name = "news_management")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        tinTucDAO = new TinTucDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
