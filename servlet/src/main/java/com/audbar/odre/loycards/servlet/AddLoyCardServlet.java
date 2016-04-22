package com.audbar.odre.loycards.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Audrius on 2016-04-17.
 */
public class AddLoyCardServlet extends HttpServlet {


    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        Statement stmt;
        Connection con;
        ResultSet rs;
        try {
            Class.forName("com.mysql.jdbc.GoogleDriver");
            con = DriverManager.getConnection("jdbc:google:mysql://loycards-eb0bc:loycardssql/LoyCards?user=odre");
            stmt = con.createStatement();
            int i = stmt.executeUpdate("insert into users (user_name, device_id) values ('Bipul', '24')");
            if (i != 0) {
                pw.println("Record has been inserted successfully<br>");
            } else {
                pw.println("Inserting record get failure");
            }
            rs = stmt.executeQuery("SELECT * FROM users ");
            pw.println("user_name\t device_id");
            while (rs.next()) {
                pw.println("<br>" + rs.getString(1) + ",\t " + rs.getInt(2));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            pw.println(e.getMessage());
        }
    }
}