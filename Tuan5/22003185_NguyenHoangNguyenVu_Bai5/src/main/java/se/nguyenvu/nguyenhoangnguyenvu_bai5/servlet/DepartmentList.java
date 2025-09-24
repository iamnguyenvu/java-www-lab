package se.nguyenvu.nguyenhoangnguyenvu_bai5.servlet;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import se.nguyenvu.nguyenhoangnguyenvu_bai5.dao.DepartmentDAO;
import se.nguyenvu.nguyenhoangnguyenvu_bai5.model.Department;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet({"/departments", "/departments/list"})
public class DepartmentList extends HttpServlet {
    private DepartmentDAO departmentDAO;
    @Resource(name = "jdbc/employee_management")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        departmentDAO = new DepartmentDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Department> departments = departmentDAO.findAll();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
