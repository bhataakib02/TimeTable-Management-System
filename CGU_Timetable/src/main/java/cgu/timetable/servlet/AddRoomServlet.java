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

@WebServlet("/AddRoomServlet")
public class AddRoomServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String roomNumber = request.getParameter("roomNumber");
        String roomType = request.getParameter("roomType");

        response.setContentType("text/plain"); // 👈 important: return plain text
        PrintWriter out = response.getWriter();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO room (room_number, room_type) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, roomNumber);
            stmt.setString(2, roomType);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                out.print("✅ Room added successfully!");
            } else {
                out.print("❌ Failed to add room.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("❌ Error: " + e.getMessage());
        }
    }
}
