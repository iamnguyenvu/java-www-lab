package se.nguyenvu.nguyenhoangnguyenvu_tuan1;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.mariadb.jdbc.Driver;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "fileUploadToDatabase", value = "/fileUploadToDatabase")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class FileUploadToDatabase extends HttpServlet {
    private static final String URL = "jdbc:mariadb://localhost:3306/UploadFileServlet";
    private static final String USER = "root";
    private static final String PASSWORD = "rootpassword";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        InputStream inputStream = null;
        Part filePart = req.getPart("photo");
        if(filePart != null) {
            inputStream = filePart.getInputStream();
        }

        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "insert into contacts (first_name, last_name, photo) values (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setBlob(3, inputStream);
                if(statement.executeUpdate() > 0) {
                    resp.getWriter().println(" Upload and save file to database successfully!");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
        try {
            DriverManager.deregisterDriver(new Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        super.destroy();
    }
}
