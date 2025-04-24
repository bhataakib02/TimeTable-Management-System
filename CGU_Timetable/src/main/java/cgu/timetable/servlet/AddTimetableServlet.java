package cgu.timetable.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddTimetableServlet")
public class AddTimetableServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String courseCode = request.getParameter("courseCode");
        String facultyName = request.getParameter("facultyName");
        String roomNumber = request.getParameter("roomNumber");
        String section = request.getParameter("section");
        String day = request.getParameter("day");
        String time = request.getParameter("time");

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL database
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cgu_timetable", "root", "1234");

            // Corrected SQL query (changed 'time' to 'time_slot')
            String sql = "INSERT INTO timetable (course_code, faculty_name, room_number, section, day, time_slot) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, courseCode);
            ps.setString(2, facultyName);
            ps.setString(3, roomNumber);
            ps.setString(4, section);
            ps.setString(5, day);
            ps.setString(6, time);

            int rows = ps.executeUpdate();
            con.close();

            // Redirect based on result
            if (rows > 0) {
                response.sendRedirect("addTimetableEntry.html?message=success");
            } else {
                response.sendRedirect("addTimetableEntry.html?message=error");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addTimetableEntry.html?message=error");
        }
    }
}
