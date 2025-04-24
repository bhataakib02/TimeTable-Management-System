package cgu.timetable.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SearchTimetableServlet")
public class SearchTimetableServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String section = request.getParameter("section");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cgu_timetable", "root", "1234");

            String query = "SELECT * FROM timetable WHERE section = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, section);
            ResultSet rs = ps.executeQuery();

            out.println("<table border='1' cellpadding='10'>");
            out.println("<tr><th>Course Code</th><th>Faculty Name</th><th>Room Number</th><th>Day</th><th>Time Slot</th></tr>");

            boolean found = false;
            while (rs.next()) {
                found = true;
                out.println("<tr>");
                out.println("<td>" + rs.getString("course_code") + "</td>");
                out.println("<td>" + rs.getString("faculty_name") + "</td>");
                out.println("<td>" + rs.getString("room_number") + "</td>");
                out.println("<td>" + rs.getString("day") + "</td>");
                out.println("<td>" + rs.getString("time_slot") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            if (!found) {
                out.println("<p style='color:red;'>No records found for section: " + section + "</p>");
            }

            con.close();
        } catch (Exception e) {
            out.println("<p style='color:red;'>❌ Failed to load timetable.<br>" + e.getMessage() + "</p>");
        }
    }

    // Optional: support GET also
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
