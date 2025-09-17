package se.nguyenvu.ex4.servlet;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.nguyenvu.ex4.dao.BookDAO;
import se.nguyenvu.ex4.model.CartBean;

import javax.sql.DataSource;
import java.io.IOException;

@WebServlet({"/cart"})
public class CartServlet extends HttpServlet {
    private BookDAO bookDAO;
    @Resource(name = "jdbc/iuhbookstore")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        bookDAO = new BookDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        CartBean cartBean = (CartBean) session.getAttribute("cart");
        if (cartBean == null) {
            cartBean = new CartBean();
            session.setAttribute("cart", cartBean);
        }

        String action = req.getParameter("action");
        switch (action) {
            case "add" -> {
                Integer bookId = Integer.parseInt(req.getParameter("bookId"));
                cartBean.addBook(bookDAO.findById(bookId));
            }
            case "remove" -> {
                Integer bookId = Integer.parseInt(req.getParameter("bookId"));
                cartBean.removeBook(bookId);
            }
            case "clear" -> cartBean.clear();
            case "pay" -> {
                resp.sendRedirect(req.getContextPath() + "/payment");
                return;
            }
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}
