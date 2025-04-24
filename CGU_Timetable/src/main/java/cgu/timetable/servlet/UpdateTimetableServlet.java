package cgu.timetable.servlet;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/UpdateTimetableServlet")
public class UpdateTimetableServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String courseCode = request.getParameter("course_code");
        String roomNumber = request.getParameter("room_number");
        String section = request.getParameter("section");
        String day = request.getParameter("day");
        String time = request.getParameter("time");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cgu_timetable", "root", "1234");

            String sql = "UPDATE timetable SET course_code=?, room_number=?, section=?, day=?, time=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, courseCode);
            ps.setString(2, roomNumber);
            ps.setString(3, section);
            ps.setString(4, day);
            ps.setString(5, time);
            ps.setInt(6, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                response.sendRedirect("addTimetableEntry.html");
            } else {
                response.getWriter().println("❌ No record updated. Maybe ID is wrong?");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace(); // Logs to console
            response.setContentType("text/plain");
            e.printStackTrace(response.getWriter()); // Print to browser
        }
    }
}
