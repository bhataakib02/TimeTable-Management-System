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

@WebServlet("/SearchFacultyTimetableServlet")
public class SearchFacultyTimetableServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String facultyName = request.getParameter("facultyName");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cgu_timetable", "root", "1234");

            String sql = "SELECT * FROM timetable WHERE faculty_name = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, facultyName);
            ResultSet rs = ps.executeQuery();

            out.println("<html><head><title>Faculty Timetable</title>");
            out.println("<style>");
            out.println("body { font-family: Arial; padding: 20px; }");
            out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
            out.println("th, td { border: 1px solid #ccc; padding: 10px; }");
            out.println("th { background-color: #f2f2f2; }");
            out.println(".btn { padding: 5px 10px; margin: 2px; border: none; cursor: pointer; }");
            out.println(".edit { background-color: orange; color: white; }");
            out.println(".delete { background-color: red; color: white; }");
            out.println("</style></head><body>");

            if (!rs.isBeforeFirst()) {
                out.println("<p style='color:red;'>❌ No timetable found for faculty: <b>" + facultyName + "</b></p>");
            } else {
                out.println("<h2>Timetable for Faculty: " + facultyName + "</h2>");
                out.println("<table>");
                out.println("<tr><th>ID</th><th>Course</th><th>Room</th><th>Section</th><th>Day</th><th>Time</th><th>Actions</th></tr>");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    out.println("<tr>");
                    out.println("<td>" + id + "</td>");
                    out.println("<td>" + rs.getString("course_code") + "</td>");
                    out.println("<td>" + rs.getString("room_number") + "</td>");
                    out.println("<td>" + rs.getString("section") + "</td>");
                    out.println("<td>" + rs.getString("day") + "</td>");
                    out.println("<td>" + rs.getString("time_slot") + "</td>");  // ✅ fixed here
                    out.println("<td>");
                    out.println("<form action='EditTimetableServlet' method='post' style='display:inline;'>");
                    out.println("<input type='hidden' name='id' value='" + id + "'/>");
                    out.println("<input type='submit' class='btn edit' value='Edit'/>");
                    out.println("</form>");
                    out.println("<form action='DeleteTimetableServlet' method='post' style='display:inline;'>");
                    out.println("<input type='hidden' name='id' value='" + id + "'/>");
                    out.println("<input type='submit' class='btn delete' value='Delete'/>");
                    out.println("</form>");
                    out.println("</td>");
                    out.println("</tr>");
                }

                out.println("</table>");
            }

            con.close();
            out.println("<br><a href='addTimetableEntry.html'>⬅ Back to Add Timetable</a>");
            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p style='color:red;'>❌ Error retrieving data</p>");
        }
    }
}
