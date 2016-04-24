/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.audbar.odre.loycards.servlet;

import com.google.appengine.api.utils.SystemProperty;
import com.google.appengine.repackaged.com.google.gson.JsonArray;
import com.google.appengine.repackaged.com.google.gson.JsonObject;
import com.google.appengine.repackaged.com.google.gson.JsonPrimitive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Objects;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet {

    private static String url;


    private static final String ID = "id";
    private static final String CARD_NUMBER = "cardNumber";
    private static final String DATE_REGISTERED = "dateRegistered";
    private static final String CARD_TYPE = "cardType";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE_URL = "imgUrl";
    private static final String USER_NAME = "userName";
    private static final String BIRTH_DATE = "birthDate";
    private static final String USER_ID = "userId";
    private static final String CARD_TYPE_ID = "cardTypeId";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url = "jdbc:google:mysql://loycards-eb0bc:loycardssql/LoyCards?user=root";
            } else {
                Class.forName("com.mysql.jdbc.Driver");
                url = "jdbc:mysql://173.194.242.44:3306/LoyCards?user=odre";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        //PrintWriter out = resp.getWriter();
        try {
            Connection conn = DriverManager.getConnection(url);
            try {
                String action = req.getParameter("action");

                switch (action)
                {
                    case "GetUserLoyCards":
                        getUserLoyCards(conn, req, resp);
                }
/*                Enumeration<String> parameterNames = req.getParameterNames();
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
                }*/
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url = "jdbc:google:mysql://loycards-eb0bc:loycardssql/LoyCards?user=root";
            } else {
                Class.forName("com.mysql.jdbc.Driver");
                url = "jdbc:mysql://173.194.242.44:3306/LoyCards?user=odre";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

       //PrintWriter out = resp.getWriter();
        try {
            Connection conn = DriverManager.getConnection(url);
            try {
                String action = req.getParameter("action");

                switch (action)
                {
                    case "getUserLoyCards":
                        getUserLoyCards(conn, req, resp);
                        break;

                    case "registerLoyCard":
                        registerLoyCard(conn, req, resp);
                        break;
                    default:
                        resp.getWriter().println("Neatpazinta komanda");


                }
/*                Enumeration<String> parameterNames = req.getParameterNames();
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
                }*/
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       // resp.setHeader("Refresh", "3; url=/guestbook.jsp");
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


    public void registerLoyCard(Connection conn, HttpServletRequest req, HttpServletResponse resp){
        JsonObject jsonResponse = new JsonObject();
        JsonArray data = new JsonArray();

        try
        {
            String loy_card_type = req.getParameter("loy_card_type");
            String card_number = req.getParameter("card_number");
            String user_name = req.getParameter("user_name");
            String birth_date = req.getParameter("birth_date");
            String password = req.getParameter("password");
            String user_id = req.getParameter("user_id");


            ResultSet result = conn.createStatement().executeQuery("SELECT id FROM loyalty_card_types WHERE title = '"+ loy_card_type +"';");
            int loy_card_type_id = -1;
            while (result.next()) {
                loy_card_type_id  = result.getInt("id");
            }
//                conn.createStatement().executeQuery("INSERT INTO users_cards(date_registered, loy_card_type_id, card_number, user_name, birth_date, password, user_id) " +
//                "values(curdate(), "+
//                loy_card_type_id + ", '" +card_number + "', '" + user_name + "', '" + birth_date + "', '" + password + "', '" + user_id + "');"
//                );

            String statement = "INSERT INTO users_cards (date_registered, loy_card_type_id, card_number, user_name, birth_date, password, user_id) VALUES( ? , ? , ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(statement);
            stmt.setString(1, "2016-04-24");
            stmt.setInt(2, loy_card_type_id);
            stmt.setString(3, card_number);
            stmt.setString(4, user_name);
            stmt.setString(5, "2000-01-01");
            stmt.setString(6, password);
            stmt.setString(7, user_id);
            int success = 2;
            success = stmt.executeUpdate();
            PrintWriter out = resp.getWriter();
            if (success == 1) {
                out.println(
                        "Kortelė užregistruota sėkmingai");
            } else if (success == 0) {
                out.println(
                        "Nepavyko užregistruoti kortelės");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }

    public void getUserLoyCards(Connection conn, HttpServletRequest req, HttpServletResponse resp){
        JsonObject jsonResponse = new JsonObject();
        JsonArray data = new JsonArray();

        try
        {
            String google_id = req.getParameter("google_id");

            if (Objects.equals(google_id, "") || google_id == null) {
                resp.getWriter().println(
                        "klaida sinchronizuojant naudotojo korteles");
            } else {
                ResultSet result = conn.createStatement().executeQuery("SELECT uc.id, uc.card_number, uc.date_registered, uc.loy_card_type_id, lct.title, lct.image_url, lct.description, uc.user_name, u.google_id, uc.birth_date"+
                        " FROM users u"+
                        " INNER JOIN users_cards uc ON u.google_id = uc.user_id"+
                        " LEFT JOIN loyalty_card_types lct ON uc.loy_card_type_id = lct.id"+
                        " WHERE u.google_id = " + google_id +";");

                while (result.next())
                {

                    JsonArray row = new JsonArray();
                    JsonObject loyCard = new JsonObject();
                    loyCard.addProperty(ID, result.getString("id"));
                    loyCard.addProperty(CARD_NUMBER, result.getString("card_number"));
                    loyCard.addProperty(DATE_REGISTERED, result.getString("date_registered"));
                    loyCard.addProperty(CARD_TYPE, result.getString("title"));
                    loyCard.addProperty(IMAGE_URL, result.getString("image_url"));
                    loyCard.addProperty(DESCRIPTION, result.getString("description"));
                    loyCard.addProperty(USER_NAME, result.getString("user_name"));
                    loyCard.addProperty(BIRTH_DATE, result.getString("birth_date"));
                    loyCard.addProperty(USER_ID, result.getString("google_id"));
                    loyCard.addProperty(CARD_TYPE_ID, result.getString("loy_card_type_id"));

                    row.add(loyCard);

                    data.add(loyCard);
                }
                jsonResponse.add("loyCardList", data);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                resp.getWriter().write(jsonResponse.toString());
//                out.print(jsonResponse);
                //out.flush();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }
}