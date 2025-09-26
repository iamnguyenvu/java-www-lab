package se.nguyenvu.nguyenhoangnguyenvu_bai5.servlet;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import se.nguyenvu.nguyenhoangnguyenvu_bai5.dao.DepartmentDAO;
import se.nguyenvu.nguyenhoangnguyenvu_bai5.dao.EmployeesDAO;
import se.nguyenvu.nguyenhoangnguyenvu_bai5.model.Department;
import se.nguyenvu.nguyenhoangnguyenvu_bai5.model.Employees;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet({"/departments", "/departments/list"})
public class DepartmentServlet extends HttpServlet {
    private EmployeesDAO employeesDAO;
    private DepartmentDAO departmentDAO;
    @Resource(name = "jdbc/employee_management")
    private DataSource dataSource;


    @Override
    public void init() throws ServletException {
        departmentDAO = new DepartmentDAO(dataSource);
        employeesDAO = new EmployeesDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        action = (action == null) ? "list" : action;

        switch (action) {
            case "list" -> {
                List<Department> departments = departmentDAO.findAll();
                req.setAttribute("departments", departments);
                req.getRequestDispatcher("departments.jsp").forward(req, resp);
            }

            case "new" -> {
                req.getRequestDispatcher("department-form.jsp").forward(req, resp);
            }

            case "edit" -> {
                String id = req.getParameter("id");
                try {
                    Department department = departmentDAO.findById(Integer.parseInt(id));
                    req.setAttribute("department", department);
                    req.getRequestDispatcher("department-form.jsp").forward(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            case "delete" -> {
                String id = req.getParameter("id");
                try {
                    departmentDAO.delete(Integer.parseInt(id));
                    resp.sendRedirect("departments");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            case "view" -> {
                String id = req.getParameter("id");

                try {
                    List<Employees> employees = employeesDAO.findByDepartmentId(Integer.parseInt(id));
                    req.setAttribute("employees", employees);
                    req.setAttribute("departmentId", id);
                    req.getRequestDispatcher("employees?action=view&departmentId=" + id).forward(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
    }
}
