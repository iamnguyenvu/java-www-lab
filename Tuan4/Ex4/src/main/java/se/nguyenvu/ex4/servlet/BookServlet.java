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

@WebServlet({"/books", "/book"})
public class BookServlet extends HttpServlet {
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
        String path = req.getServletPath();
        switch (path) {
            case "/books" -> listBooks(req, resp);
            case "/book" -> showBook(req, resp);
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String strBookId = req.getParameter("id");
        if(strBookId != null && !strBookId.isEmpty()) {
            try {
                int bookId = Integer.parseInt(strBookId);
                Book book = bookDAO.findById(bookId);
                if(book != null) {
                    req.setAttribute("book", book);
                    req.setAttribute("pageTitle", book.getTitle());
                    req.setAttribute("contentPage", "book-detail.jsp");
                    getServletContext().getRequestDispatcher("/layout.jsp").forward(req, resp);
                }
                else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Book not found with ID: " + bookId);
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book ID format");
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
            }
        }
        else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Book ID is required");
        }
    }

    private void listBooks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("books", bookDAO.findAll());
            req.setAttribute("pageTitle","Book List");
            req.setAttribute("contentPage", "book-list.jsp");
            getServletContext().getRequestDispatcher("/layout.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
