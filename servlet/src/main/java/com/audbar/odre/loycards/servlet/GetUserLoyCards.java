/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.audbar.odre.loycards.servlet;

import com.google.appengine.api.utils.SystemProperty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetUserLoyCards extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String url = null;
        handleRequest(req, resp);
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url = "jdbc:google:mysql://loycards-eb0bc:loycardssql/LoyCards?user=root";
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                url = "jdbc:mysql://173.194.242.44:3306/LoyCards?user=odre";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        PrintWriter out = resp.getWriter();
        try {
            Connection conn = DriverManager.getConnection(url);
            try {

                Enumeration<String> parameterNames = req.getParameterNames();
                String fname = req.getParameter("fname");
                String content = req.getParameter("content");

                String user_name = "kukuuuu";
                user_name = req.getParameter("user_name");
                String device_id = req.getParameter("device_id");
                if (parameterNames.hasMoreElements())
                    content = parameterNames.nextElement();
                String body =  getBody(req);
                String dateCreated = "2016-04-22";


                if (fname == "" || content == "") {
                    out.println(
                            "<html><head></head><body>You are missing either a message or a name! Try again! " +
                                    "Redirecting in 3 seconds...</body></html>");
                } else {
                    String statement = "INSERT INTO users (user_name, date_created, device_id, user_last_name) VALUES( ? , ? , ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(statement);
                    stmt.setString(1, user_name);
                    stmt.setString(2, dateCreated);
                    stmt.setString(3, content);
                    stmt.setString(4, body);
                    int success = 2;
                    success = stmt.executeUpdate();
                    if (success == 1) {
                        out.println(
                                "body: " + body + "--- device_id:" + device_id);
                    } else if (success == 0) {
                        out.println(
                                "<html><head></head><body>Failure! Please try again! " +
                                        "Redirecting in 3 seconds...</body></html>");
                    }
                }
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Please use the form to GET to this url");
    }

    public static String getBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }


    public void handleRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {

        PrintWriter out = res.getWriter();

        res.setContentType("text/plain");

        Enumeration<String> parameterNames = req.getParameterNames();

        while (parameterNames.hasMoreElements()) {

            String paramName = parameterNames.nextElement();

            String[] paramValues = req.getParameterValues(paramName);
        }
    }


    public void testMethod() throws IOException {
        String url = null;
        HttpServletResponse resp = null;
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url = "jdbc:google:mysql://loycards-eb0bc:LoyCards?user=odre";
            } else {
//                Class.forName("com.mysql.jdbc.Driver");
//                url = "jdbc:mysql://173.194.242.44:3306/LoyCards?user=odre";
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url = "jdbc:google:mysql://loycards-eb0bc:loycardssql/LoyCards?user=odre";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        PrintWriter out = resp.getWriter();
        try {
            Connection conn = DriverManager.getConnection(url);
            try {
                String dateCreated = "2016-04-17";


                String statement = "INSERT INTO users (user_name, date_created) VALUES( ? , ? )";
                PreparedStatement stmt = conn.prepareStatement(statement);
                stmt.setString(1, "tst");
                stmt.setString(2, dateCreated);
                int success = 2;
                success = stmt.executeUpdate();
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}