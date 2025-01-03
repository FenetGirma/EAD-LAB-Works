package com.ead.lab;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;

public class DeleteBookServlet {
    private static final String query = "delete from books where id = ?";

    @Setter
    private DBConnectionManager db;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        db.openConnection();
        Connection connection = db.getConnection();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            int count = ps.executeUpdate();
            if (count == 1) {
                out.println("<h2>Record is deleted successfully.</h2>");
            } else {
                out.println("<h2>Record not deleted.</h2>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            out.println("<h1>" + se.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h1>" + e.getMessage() + "</h1>");
        }

        db.closeConnection();
        out.close();
    }
}
