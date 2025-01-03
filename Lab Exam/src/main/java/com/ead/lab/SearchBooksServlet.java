package com.ead.lab;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;

public class SearchBooksServlet {
    private static final String query = "select * from books where title like ?";

    @Setter
    private DBConnectionManager db;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        StringBuilder htmlResponse = new StringBuilder();
        db.openConnection();
        Connection connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, "%" + title + "%");
            ResultSet rs = ps.executeQuery();
            ArrayList<Book> books = new ArrayList<Book>();
            while (rs.next()) {
                books.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4)));
            }

            htmlResponse.append("<html><head> </head><body>");
            htmlResponse.append("<h2>Showing search results for: '" + title + "'</h2>");
            htmlResponse.append("<table style='border: 2px solid black; border-spacing: 10px;'>");
            htmlResponse.append("<tr>");
            htmlResponse.append("<th>book ID</th>");
            htmlResponse.append("<th>title</th>");
            htmlResponse.append("<th>author</th>");
            htmlResponse.append("<th>price</th>");
            htmlResponse.append("</tr>");
            for (Book book : books) {
                htmlResponse.append("<tr>");
                htmlResponse.append("<td>").append(book.id).append("</td>");
                htmlResponse.append("<td>").append(book.title).append("</td>");
                htmlResponse.append("<td>").append(book.author).append("</td>");
                htmlResponse.append("<td>").append(book.price).append("</td>");
                htmlResponse.append("</tr>");
            }
            htmlResponse.append("</table>");
            htmlResponse.append("</body></html>");
        } catch (SQLException se) {
            se.printStackTrace();
            htmlResponse.append("<h1>").append(se.getMessage()).append("</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            htmlResponse.append("<h1>").append(e.getMessage()).append("</h1>");
        }

        htmlResponse.append("</body></html>");
        db.closeConnection();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(htmlResponse.toString());
    }
}
