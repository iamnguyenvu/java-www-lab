package se.nguyenvu.nguyenhoangnguyenvu_bai5.servlet;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import se.nguyenvu.nguyenhoangnguyenvu_bai5.dao.DepartmentDAO;
import se.nguyenvu.nguyenhoangnguyenvu_bai5.dao.EmployeesDAO;
import se.nguyenvu.nguyenhoangnguyenvu_bai5.model.Employees;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet({"/employees"})
public class EmployeeServlet extends HttpServlet {
    @Resource(name = "employee_management")
    private DataSource dataSource;

    private EmployeesDAO employeesDAO;
    private DepartmentDAO departmentDAO;

    @Override
    public void init() throws ServletException {
        employeesDAO = new EmployeesDAO(dataSource);
        departmentDAO = new DepartmentDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";
        switch (action) {
            case "list" -> {
                try {
                    List<Employees> employees = employeesDAO.findAll();
                    req.setAttribute("employees", employees);
                    req.getRequestDispatcher("employee-list.jsp").forward(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            case "new" -> {
                req.setAttribute("departments", departmentDAO.findAll());
                req.getRequestDispatcher("employee-form.jsp").forward(req, resp);
            }

            case "delete" -> {}

            case "view" -> {
                String departmentId = req.getParameter("departmentId");
                try {
                    List<Employees> employees = employeesDAO.findByDepartmentId(Integer.parseInt(departmentId));
                    req.setAttribute("employees", employees);
                    req.setAttribute("departments", departmentDAO.findAll());
                    req.getRequestDispatcher("employee-list.jsp").forward(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        int id = (idStr != null && !idStr.isEmpty()) ? Integer.parseInt(idStr) : 0;

        String name = req.getParameter("name");
        double salary = Double.parseDouble(req.getParameter("salary"));
        int departmentId = Integer.parseInt(req.getParameter("departmentId"));

        Employees employees = Employees.builder()
                .id(id)
                .salary(salary)
                .departmentId(departmentId)
                .name(name)
                .build();

        if(id != 0) {
            try {
                employeesDAO.save(employees);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                employeesDAO.update(employees);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        resp.sendRedirect("employees?departmentId="+departmentId);
    }
}
