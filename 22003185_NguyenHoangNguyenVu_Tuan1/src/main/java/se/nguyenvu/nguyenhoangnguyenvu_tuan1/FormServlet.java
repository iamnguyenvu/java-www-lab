package se.nguyenvu.nguyenhoangnguyenvu_tuan1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "formServlet", value = "/processFormUpload")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,
        maxFileSize = 1024 * 1024 * 15
)
public class FormServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String gender = req.getParameter("gender");
        List<String> hobbies = List.of(req.getParameterValues("hobbies"));
        String country = req.getParameter("country");
        String birthDate = req.getParameter("birthDate");
        Part file = req.getPart("profilePicture");


        out.println("<h2>Form Submission Result</h2>");
        out.println("Name: " + name);
        out.println("Gender: " +gender);

    }
}
