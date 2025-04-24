package cgu.timetable.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import cgu.timetable.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ViewTimetableServlet")
public class ViewTimetableServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Timetable</title>");
        out.println("<link rel='stylesheet' href='style.css'></head><body>");
        out.println("<div class='header'><img src='cgu_logo.png' class='logo'><h1 class='welcome-text'>View Timetable</h1></div>");
        out.println("<div class='container'>");
        out.println("<h2>All Timetable Entries</h2>");
        out.println("<table border='1'>");
        out.println("<tr><th>Course Code</th><th>Faculty Name</th><th>Room Number</th><th>Section</th><th>Day</th><th>Time Slot</th></tr>");

        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement()) {
            String sql = "SELECT course_code, faculty_name, room_number, section, day, time_slot FROM timetable";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("course_code") + "</td>");
                out.println("<td>" + rs.getString("faculty_name") + "</td>");
                out.println("<td>" + rs.getString("room_number") + "</td>");
                out.println("<td>" + rs.getString("section") + "</td>");
                out.println("<td>" + rs.getString("day") + "</td>");
                out.println("<td>" + rs.getString("time_slot") + "</td>");
                out.println("</tr>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }

        out.println("</table>");
        out.println("<div class='buttons' style='margin-top: 20px;'>");
        out.println("<a href='admin.html' class='btn'>Back to Admin</a>");
        out.println("<a href='index.html' class='btn'>Home</a>");
        out.println("</div></div></body></html>");
    }
}
