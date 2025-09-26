package se.nguyenvu.ex4.servlet;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import se.nguyenvu.ex4.dao.BookDAO;
import se.nguyenvu.ex4.model.Book;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet({"/search"})
public class SearchServlet extends HttpServlet {
    private BookDAO bookDAO;
    @Resource(name = "jdbc/iuhbookstore")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        if (dataSource == null) {
            throw new ServletException("DataSource not found. Please check JNDI configuration.");
        }
        bookDAO = new BookDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String query = req.getParameter("query");
            String minPriceStr = req.getParameter("minPrice");
            String maxPriceStr = req.getParameter("maxPrice");

            Double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : null;
            Double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : null;
            
            List<Book> books;
            
            if (query != null && !query.trim().isEmpty()) {
                // Search with text query
                if (minPrice != null && maxPrice != null) {
                    // Search with both text and price filter
                    books = bookDAO.searchBooksWithPriceFilter(query.trim(), minPrice, maxPrice);
                } else if (minPrice != null) {
                    // Search with text and min price
                    books = bookDAO.searchBooksWithMinPrice(query.trim(), minPrice);
                } else if (maxPrice != null) {
                    // Search with text and max price
                    books = bookDAO.searchBooksWithMaxPrice(query.trim(), maxPrice);
                } else {
                    // Search with text only
                    books = bookDAO.searchBooks(query.trim());
                }
            } else if (minPrice != null && maxPrice != null) {
                // Filter by price range only
                books = bookDAO.findBooksByPriceRange(minPrice.intValue(), maxPrice.intValue());
            } else if (minPrice != null) {
                // Filter by min price only
                books = bookDAO.findBooksByMinPrice(minPrice);
            } else if (maxPrice != null) {
                // Filter by max price only
                books = bookDAO.findBooksByMaxPrice(maxPrice);
            } else {
                // No filters, show all books
                books = bookDAO.findAll();
            }
            
            req.setAttribute("books", books);
            req.setAttribute("contentPage", "book-list.jsp");
            req.setAttribute("pageTitle", "Search Results - IUH Bookstore");
            req.getRequestDispatcher("layout.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid price format");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }
}
