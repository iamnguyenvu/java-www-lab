package se.nguyenvu.ex3.servlet;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.nguyenvu.ex3.beans.CartBean;
import se.nguyenvu.ex3.dao.ProductDAO;

import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private ProductDAO productDAO;
    @Resource(name = "jdbc/shopdb")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        System.out.println("CartServlet init() called...");
        if (dataSource == null) {
            System.err.println("DataSource injection failed in CartServlet!");
            throw new ServletException("DataSource is null - check resource configuration");
        }
        System.out.println("CartServlet DataSource injected successfully");
        productDAO = new ProductDAO(dataSource);
        System.out.println("CartServlet ProductDAO initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        CartBean cartBean = (CartBean) session.getAttribute("cart");
        if(cartBean == null) {
            cartBean = new CartBean();
            session.setAttribute("cart", cartBean);
        }

        String action = req.getParameter("action");
        System.out.println("Cart action: " + action);
        
        try {
            switch (String.valueOf(action)) {
                case "add" -> {
                    Integer productId = Integer.parseInt(req.getParameter("productId"));
                    System.out.println("Adding product ID: " + productId);
                    cartBean.addProduct(productDAO.findById(productId));
                    System.out.println("Product added successfully");
                }
                case "remove" -> {
                    Integer productId = Integer.parseInt(req.getParameter("productId"));
                    cartBean.removeProduct(productId);
                }
                case "update" -> {
                    Integer productId = Integer.parseInt(req.getParameter("productId"));
                    Integer quantity = Integer.parseInt(req.getParameter("quantity"));
                    cartBean.updateQuantity(productId, quantity);
                }
                case "clear" -> cartBean.clear();
                default -> {
                    System.out.println("Unknown action: " + action);
                }
            }
            
            // Redirect to cart page after processing
            resp.sendRedirect(req.getContextPath() + "/cart");
            
        } catch (Exception e) {
            System.err.println("Error in CartServlet doPost: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error processing cart action", e);
        }
    }
}
