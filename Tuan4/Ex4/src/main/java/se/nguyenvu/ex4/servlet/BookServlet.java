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
            int bookId = Integer.parseInt(strBookId);
            Book book = bookDAO.findById(bookId);
            if(book != null) {
                req.setAttribute("book", book);
                getServletContext().getRequestDispatcher("/book.jsp").forward(req, resp);
            }
            else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Book not found with ID: " + bookId);
            }
        }
    }

    private void listBooks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("books", bookDAO.findAll());
        getServletContext().getRequestDispatcher("/books.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
