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
import se.nguyenvu.midtermtestemployees.model.Employee;
import se.nguyenvu.midtermtestemployees.util.JpaUtil;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet({"/employees", "/employees/*"})
public class EmployeeServlet extends HttpServlet {
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
                    req.getRequestDispatcher("/employees.jsp").forward(req, resp);
                }
                case "create" -> {
                    req.setAttribute("departments", departmentDAO.findAll());
                    req.getRequestDispatcher("/employee-form.jsp").forward(req, resp);
                }

                case "edit" -> {
                    req.setAttribute("departments", departmentDAO.findAll());
                    Employee employee = employeeDAO.findById(Long.parseLong(req.getParameter("id"))).orElse(null);
                    req.setAttribute("employee", employee);
                    req.getRequestDispatcher("/employee-form.jsp").forward(req, resp);
                }

                case "delete" -> {
                    String id = req.getParameter("id");
                    if (id != null) {
                        em.getTransaction().begin();
                        employeeDAO.delete(Long.parseLong(id));
                        em.getTransaction().commit();
                    }
                    resp.sendRedirect(req.getContextPath() + "/employees");
                }
                default -> resp.sendRedirect(req.getContextPath() + "/employees");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try(EntityManager em = JpaUtil.getEmf()) {
            EmployeeDAO employeeDAO = new EmployeeJpaDAO(em);
            DepartmentDAO departmentDAO = new DepartmentJpaDAO(em);

            switch (action) {
                case "create" -> {
                    String name = req.getParameter("name");
                    BigDecimal salary = new BigDecimal(req.getParameter("salary"));
                    Long departmentId = Long.parseLong(req.getParameter("departmentId"));
                    Employee employee = Employee.builder()
                            .name(name)
                            .salary(salary)
                            .department(em.getReference(Department.class, departmentId))
                            .build();
                    em.getTransaction().begin();
                    employeeDAO.save(employee);
                    em.getTransaction().commit();
                    resp.sendRedirect(req.getContextPath() + "/employees");
                }
                case "edit" -> {
                    String id = req.getParameter("id");
                    Employee employee = employeeDAO.findById(Long.parseLong(id)).orElse(null);
                    if (employee != null) {
                        String name = req.getParameter("name");
                        BigDecimal salary = new BigDecimal(req.getParameter("salary"));
                        Long departmentId = Long.parseLong(req.getParameter("departmentId"));
                        employee.setName(name);
                        employee.setSalary(salary);
                        employee.setDepartment(em.getReference(Department.class, departmentId));
                        em.getTransaction().begin();
                        employeeDAO.update(employee);
                        em.getTransaction().commit();
                    }
                    resp.sendRedirect(req.getContextPath() + "/employees");
                }
            }
        }
    }
}
