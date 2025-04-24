package cgu.timetable.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/EditTimetableServlet")
public class EditTimetableServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cgu_timetable", "root", "1234");

            String sql = "SELECT * FROM timetable WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<html><head><title>Edit Timetable</title></head><body>");
                out.println("<h2>Edit Timetable Entry (ID: " + id + ")</h2>");
                out.println("<form method='post' action='UpdateTimetableServlet'>");
                out.println("<input type='hidden' name='id' value='" + id + "'/>");
                out.println("Course Code: <input type='text' name='course_code' value='" + rs.getString("course_code") + "'/><br/>");
                out.println("Faculty Name: <input type='text' name='faculty_name' value='" + rs.getString("faculty_name") + "'/><br/>");
                out.println("Room Number: <input type='text' name='room_number' value='" + rs.getString("room_number") + "'/><br/>");
                out.println("Section: <input type='text' name='section' value='" + rs.getString("section") + "'/><br/>");
                out.println("Day: <input type='text' name='day' value='" + rs.getString("day") + "'/><br/>");
                out.println("Time Slot: <input type='text' name='time_slot' value='" + rs.getString("time_slot") + "'/><br/>");
                out.println("<input type='submit' value='Update'/>");
                out.println("</form>");
                out.println("<br><a href='addTimetableEntry.html'>⬅ Back</a>");
                out.println("</body></html>");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p style='color:red;'>❌ Error loading data</p>");
        }
    }
}
