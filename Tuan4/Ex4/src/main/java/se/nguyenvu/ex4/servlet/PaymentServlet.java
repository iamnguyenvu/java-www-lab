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
import java.math.BigDecimal;

@WebServlet({"/payment"})
public class PaymentServlet extends HttpServlet {
    private BookDAO bookDAO;
    @Resource(name = "jdbc/iuhbookstore")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        bookDAO = new BookDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        CartBean cartBean = (CartBean) session.getAttribute("cart");

        cartBean.getCartBooks().forEach(book -> {
            book.getBook().setPrice(bookDAO.findById(book.getBook().getBookId()).getPrice());
        });

        req.setAttribute("cart", cartBean);
        req.getRequestDispatcher("/payment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        CartBean cartBean = (CartBean) session.getAttribute("cart");

        String bookId = req.getParameter("bookId");
        String title = req.getParameter("title");
        Integer quantity = Integer.valueOf(req.getParameter("quantity"));
        BigDecimal price = new BigDecimal(req.getParameter("price"));
        BigDecimal total = price.multiply(new BigDecimal(quantity));

        session.removeAttribute("cart");
        resp.sendRedirect(req.getContextPath() + "/payment-success.jsp");
    }
}
