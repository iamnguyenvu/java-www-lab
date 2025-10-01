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
import se.nguyenvu.midtermtestdepartments.model.Employee;
import se.nguyenvu.midtermtestdepartments.util.JpaUtil;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet({"/employees", "/employees/*"})
public class EmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null) {action = "list";}
        try(EntityManager em = JpaUtil.getEmf()) {
            DepartmentDAO departmentDAO = new DepartmentJpaDAO(em);
            EmployeeDAO employeeDAO = new EmployeeJpaDAO(em);

            switch (action) {
                default -> {
                    req.setAttribute("employees", employeeDAO.findAll());
                    req.getRequestDispatcher("employees.jsp").forward(req, resp);
                }

                case "edit" -> {
                    String id = req.getParameter("id");
                    Employee employee = employeeDAO.findById(Long.parseLong(id)).orElse(null);
                    req.setAttribute("employee", employee);
                    req.setAttribute("departments", departmentDAO.findAll());
                    req.getRequestDispatcher("employee-form.jsp").forward(req, resp);
                }
                case "delete" -> {
                    String id = req.getParameter("id");
                    if(id != null) {
                        try {
                            em.getTransaction().begin();
                            employeeDAO.delete(Long.parseLong(id));
                            em.getTransaction().commit();
                        } catch (Exception e) {
                            resp.sendRedirect(req.getContextPath() + "/employees");
                            throw e;
                        }
                    }
                    req.setAttribute("employees", employeeDAO.findAll());
                    req.getRequestDispatcher("employees.jsp").forward(req, resp);
                }

                case "create" -> {
                    req.setAttribute("departments", departmentDAO.findAll());
                    req.getRequestDispatcher("employee-form.jsp").forward(req, resp);
                }
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
                    String id = req.getParameter("departmentId");
                    String name = req.getParameter("name");
                    BigDecimal salary = new BigDecimal(req.getParameter("salary"));
                    Department department = departmentDAO.findById(Long.parseLong(id)).orElse(null);

                    Employee employee = Employee.builder()
                            .name(name)
                            .salary(salary)
                            .department(department)
                            .build();
                    em.getTransaction().begin();
                    employeeDAO.save(employee);
                    em.getTransaction().commit();
                    resp.sendRedirect(req.getContextPath() + "/departments?action=view&id=" + id);
                }

                case "edit" -> {
                    String id = req.getParameter("id");
                    String name = req.getParameter("name");
                    BigDecimal salary = new BigDecimal(req.getParameter("salary"));
                    String departmentId = req.getParameter("departmentId");
                    Department department = departmentDAO.findById(Long.parseLong(departmentId)).orElse(null);
                    Employee employee = employeeDAO.findById(Long.parseLong(id)).orElse(null);

                    employee.setName(name);
                    employee.setSalary(salary);
                    employee.setDepartment(em.getReference(Department.class, Long.parseLong(id)));

                    em.getTransaction().begin();
                    employeeDAO.update(employee);
                    em.getTransaction().commit();
                    resp.sendRedirect(req.getContextPath() + "/employees");
                }
            }
        }
    }
}
