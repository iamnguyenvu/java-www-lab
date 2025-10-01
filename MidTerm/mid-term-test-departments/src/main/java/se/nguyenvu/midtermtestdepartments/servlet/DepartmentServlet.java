package se.nguyenvu.midtermtestdepartments.servlet;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import se.nguyenvu.midtermtestdepartments.dao.DepartmentDAO;
import se.nguyenvu.midtermtestdepartments.dao.EmployeeDAO;
import se.nguyenvu.midtermtestdepartments.jpa.DepartmentJpaDAO;
import se.nguyenvu.midtermtestdepartments.jpa.EmployeeJpaDAO;
import se.nguyenvu.midtermtestdepartments.model.Department;
import se.nguyenvu.midtermtestdepartments.util.JpaUtil;

import java.io.IOException;

@WebServlet({"/departments", "/departments/*"})
public class DepartmentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null) {action = "list";}
        try(EntityManager em = JpaUtil.getEmf()) {
            DepartmentDAO departmentDAO = new DepartmentJpaDAO(em);
            EmployeeDAO employeeDAO = new EmployeeJpaDAO(em);

            switch (action) {
                default -> {
                    req.setAttribute("departments", departmentDAO.findAll());
                    req.getRequestDispatcher("departments.jsp").forward(req, resp);
                }

                case "search" -> {
                    String keyword = req.getParameter("keyword");
                    req.setAttribute("departments", departmentDAO.findByName(keyword));
                    req.getRequestDispatcher("departments.jsp").forward(req, resp);
                }

                case "edit" -> {
                    String id = req.getParameter("id");
                    Department department = departmentDAO.findById(Long.parseLong(id)).orElse(null);
                    req.setAttribute("department", department);
                    req.getRequestDispatcher("department-form.jsp").forward(req, resp);
                }
                case "delete" -> {
                    String id = req.getParameter("id");
                    if(id != null) {
                        try {
                            em.getTransaction().begin();
                            departmentDAO.delete(Long.parseLong(id));
                            em.getTransaction().commit();
                        } catch (Exception e) {
                            resp.sendRedirect(req.getContextPath() + "/departments");
                            throw e;
                        }
                    }
                    req.setAttribute("departments", departmentDAO.findAll());
                    req.getRequestDispatcher("departments.jsp").forward(req, resp);
                }

                case "create" -> {
                    req.getRequestDispatcher("department-form.jsp").forward(req, resp);
                }

                case "view" -> {
                    String id = req.getParameter("id");
                    Department department = departmentDAO.findById(Long.parseLong(id)).orElse(null);
                    req.setAttribute("department", department);
                    req.setAttribute("employees", employeeDAO.findByDepartmentId(Long.parseLong(id)));
                    req.getRequestDispatcher("employees.jsp").forward(req, resp);
                }
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try(EntityManager em = JpaUtil.getEmf()) {
            DepartmentDAO departmentDAO = new DepartmentJpaDAO(em);


            switch (action) {
                case "create" -> {
                    String id = req.getParameter("id");
                    String name = req.getParameter("name");
                    Department department = Department.builder().name(name).build();
                    em.getTransaction().begin();
                    departmentDAO.save(department);
                    em.getTransaction().commit();
                    resp.sendRedirect(req.getContextPath() + "/departments");
                }

                case "edit" -> {
                    String id = req.getParameter("id");
                    String name = req.getParameter("name");
                    Department department = departmentDAO.findById(Long.parseLong(id)).orElse(null);
                    department.setName(name);
                    em.getTransaction().begin();
                    departmentDAO.update(department);
                    em.getTransaction().commit();
                    resp.sendRedirect(req.getContextPath() + "/departments");
                }
            }
        }
    }
}
