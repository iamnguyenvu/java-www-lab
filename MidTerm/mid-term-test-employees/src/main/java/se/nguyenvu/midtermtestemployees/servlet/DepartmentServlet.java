package se.nguyenvu.midtermtestemployees.servlet;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import se.nguyenvu.midtermtestemployees.dao.DepartmentDAO;
import se.nguyenvu.midtermtestemployees.dao.EmployeeDAO;
import se.nguyenvu.midtermtestemployees.dao.jpa.DepartmentJpaDAO;
import se.nguyenvu.midtermtestemployees.dao.jpa.EmployeeJpaDAO;
import se.nguyenvu.midtermtestemployees.model.Department;
import se.nguyenvu.midtermtestemployees.util.JpaUtil;

import java.io.IOException;
import java.util.Optional;

@WebServlet({"/departments", "/departments/*"})
public class DepartmentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try(EntityManager em = JpaUtil.getEmf()) {
            EmployeeDAO employeeDAO = new EmployeeJpaDAO(em);
            DepartmentDAO departmentDAO = new DepartmentJpaDAO(em);

            switch (action) {
                case "list" -> {
                    req.setAttribute("employees", employeeDAO.findAll());
                    req.setAttribute("departments", departmentDAO.findAll());
                    req.getRequestDispatcher("/departments.jsp").forward(req, resp);
                }
                case "create" -> {
                    req.setAttribute("departments", departmentDAO.findAll());
                    req.getRequestDispatcher("/department-form.jsp").forward(req, resp);
                }

                case "edit" -> {
                    Department department = departmentDAO.findById(Long.parseLong(req.getParameter("id"))).orElse(null);
                    req.setAttribute("department", department);
                    req.getRequestDispatcher("/department-form.jsp").forward(req, resp);
                }

                case "search" -> {
                    String keyword = req.getParameter("keyword");
                    req.setAttribute("departments", departmentDAO.findByName(keyword));
                    req.getRequestDispatcher("/departments.jsp").forward(req, resp);
                }

                case "delete" -> {
                    String id = req.getParameter("id");

                    if(id != null) {
                        em.getTransaction().begin();
                        try {
                            departmentDAO.delete(Long.parseLong(id));
                            em.getTransaction().commit();
                        } catch (Exception e) {
                            em.getTransaction().rollback();
                            throw e;
                        }
                    }
                    resp.sendRedirect(req.getContextPath() + "/departments");
                }

                case "view" -> {
                    String id = req.getParameter("id");
                    req.setAttribute("departments", departmentDAO.findAll());
                    req.setAttribute("employees", employeeDAO.findByDepartmentId(Long.parseLong(req.getParameter("id"))));
                    req.getRequestDispatcher("/employees.jsp").forward(req, resp);
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
                    String name = req.getParameter("name");
                    em.getTransaction().begin();
                    departmentDAO.save(Department.builder()
                                    .name(name)
                            .build());
                    em.getTransaction().commit();
                    resp.sendRedirect(req.getContextPath() + "/departments");
                }

                case "edit" -> {
                    em.getTransaction().begin();
                    Optional<Department> department = departmentDAO.findById(Long.parseLong(req.getParameter("id")));
                    if (department.isPresent()) {
                        department.get().setName(req.getParameter("name"));
                        departmentDAO.update(department.get());
                    }
                    em.getTransaction().commit();
                    resp.sendRedirect(req.getContextPath() + "/departments");
                }

                default -> resp.sendRedirect(req.getContextPath() + "/departments");
            }
        }
    }
}
