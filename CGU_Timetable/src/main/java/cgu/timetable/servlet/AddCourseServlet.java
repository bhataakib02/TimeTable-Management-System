package cgu.timetable.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import cgu.timetable.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddCourseServlet")
public class AddCourseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String courseCode = request.getParameter("courseCode");
        String courseName = request.getParameter("courseName");

        response.setContentType("text/plain"); // ✅ Plain text response for AJAX
        PrintWriter out = response.getWriter();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO course (code, name) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, courseCode);
            stmt.setString(2, courseName);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                out.print("✅ Course added successfully!");
            } else {
                out.print("❌ Failed to add course.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("❌ Error: " + e.getMessage());
        }
    }
}
