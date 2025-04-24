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

@WebServlet("/Faculty")
public class AddFacultyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("facultyName");
        String dept = request.getParameter("facultyDept");
        String email = request.getParameter("facultyEmail");

        response.setContentType("text/plain"); // ✅ Plain text response for AJAX
        PrintWriter out = response.getWriter();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO faculty (name, email, department) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, dept);
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                out.print ("✅ Faculty added successfully!") ;
                
            } else {
                out.print ("❌ Failed to add faculty.") ;
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("❌ Error: " + e.getMessage());
        }   
    }
    
    }

