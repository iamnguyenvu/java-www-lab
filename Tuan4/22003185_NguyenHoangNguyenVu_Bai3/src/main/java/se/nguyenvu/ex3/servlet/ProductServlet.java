package se.nguyenvu.ex3.servlet;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import se.nguyenvu.ex3.beans.Product;
import se.nguyenvu.ex3.dao.ProductDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet({"/products", "/product"})
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO;
    @Resource(name = "jdbc/shopdb")
    private DataSource dataSource;
    
    @Override
    public void init() throws ServletException {
        System.out.println("ProductServlet init() called...");
        if (dataSource == null) {
            System.err.println("DataSource injection failed!");
            throw new ServletException("DataSource is null - check resource configuration");
        }
        System.out.println("DataSource injected successfully");
        productDAO = new ProductDAO(dataSource);
        System.out.println("ProductDAO initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/products":
                listProducts(req, resp);
                break;
            case "/product":
                showProduct(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void showProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String strId = req.getParameter("productId");
        if(strId != null && !strId.isEmpty()) {
            int id = Integer.parseInt(strId);
            Product product = productDAO.findById(id);
            if(product != null) {
                req.setAttribute("product", product);
                getServletContext().getRequestDispatcher("/product.jsp").forward(req, resp);
            }
            else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }
        }
    }

    private void listProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("listProducts() called");
        try {
            List<Product> products = productDAO.findAll();
            System.out.println("Products retrieved from DAO: " + (products == null ? "null" : products.size()));
            
            if (products != null && !products.isEmpty()) {
                System.out.println("Setting products attribute with " + products.size() + " products");
                for (Product p : products) {
                    System.out.println("Product: " + p.getModel() + " - Price: " + p.getPrice());
                }
            } else {
                System.out.println("No products found in database");
            }
            
            req.setAttribute("products", products);
            System.out.println("Forwarding to product-list.jsp");
            getServletContext().getRequestDispatcher("/product-list.jsp").forward(req, resp);
        } catch (Exception e) {
            System.err.println("Error in listProducts(): " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error retrieving products", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
    }
}
