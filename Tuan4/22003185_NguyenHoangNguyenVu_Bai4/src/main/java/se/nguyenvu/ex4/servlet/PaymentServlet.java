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

        if(cartBean == null || cartBean.getCartBooks().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/books");
            return;
        }

//        cartBean.getCartBooks().forEach(book -> {
//            book.getBook().setPrice(bookDAO.findById(book.getBook().getBookId()).getPrice());
//        });

        req.setAttribute("pageTitle", "Checkout");
        req.setAttribute("contentPage", "payment.jsp");
        req.getRequestDispatcher("/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        CartBean cartBean = (CartBean) session.getAttribute("cart");

        if(cartBean == null || cartBean.getCartBooks().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/books");
            return;
        }
        String action = req.getParameter("action");

        if("pay".equals(action)) {
            String fullname = req.getParameter("fullname");
            String address = req.getParameter("address");
            String paymentMethod = req.getParameter("paymentMethod");
            String totalPrice = req.getParameter("totalPrice");

        }
        session.removeAttribute("cart");
        resp.sendRedirect(req.getContextPath() + "/payment-success.jsp");
    }

    private boolean processPayment(CartBean cartBean) {
        try {
            cartBean.getCartBooks().stream()
                    .allMatch(book -> bookDAO.updateQuantity(
                            book.getBook().getBookId(),
                            book.getQuantity()
                    ));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
