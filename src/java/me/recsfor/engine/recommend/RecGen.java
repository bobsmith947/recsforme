/*
 * Copyright 2018 Lucas Kitaev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.recsfor.engine.recommend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import me.recsfor.app.ListData;

/**
 * Servlet that initializes recommendation generation and displays the results.
 * @author lkitaev
 */
public class RecGen extends HttpServlet {
  private static final long serialVersionUID = 5704127271435930221L;
  /**
   * Injects a JDBC resource from the deployment descriptor.
   */
  @Resource(name = "jdbc/MediaRecom")
  private DataSource ds;
  private LinkedList<User> users;
  private ListData recs;

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   * @throws SQLException if a SQL error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException, SQLException {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter();
            Connection conn = ds.getConnection()) {
      Statement query = conn.createStatement();
      ResultSet res = query.executeQuery("SELECT id, uname, sex, dob FROM users");
      users = findUsers(res);
      generateRecs();
      /* TODO output your page here. You may use following sample code. */
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<title>RecGen</title>");      
      out.println("<body>");
      out.println("<h1>Servlet RecGen at " + request.getContextPath() + "</h1>");
      out.println("</body>");
      out.println("</html>");
    }
  }
  /**
   * Handles the HTTP <code>GET</code> method.
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (SQLException e) {
      this.log(e.getMessage(), e);
    }
  }
  /**
   * Handles the HTTP <code>POST</code> method.
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (SQLException e) {
      this.log(e.getMessage(), e);
    }
  }
  /**
   * Returns a short description of the servlet.
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "This servlet is used for generating recommendations.";
  }
  
  /**
   * Creates a list of similar users based on the database query results.
   * @param results the possible users
   * @return the similar users
   */
  private LinkedList<User> findUsers(ResultSet results) throws SQLException {
    LinkedList<User> list = new LinkedList<>();
    while (results.next()) {
      String name = results.getString("uname");
      String sex = results.getString("sex");
      String dob = results.getDate("dob").toString();
      LocalDate newDob;
      try {
        newDob = LocalDate.parse(dob);
      } catch (DateTimeParseException e) {
        newDob = LocalDate.of(1900, 1, 1);
      }
      String[] userLists = new String[2];
      try (Connection conn = ds.getConnection()) {
        Statement query = conn.createStatement();
        ResultSet listResults = query.executeQuery("SELECT items FROM user_likes WHERE uid = " + results.getString("id"));
        listResults.next();
        userLists[0] = listResults.getString("items");
        listResults = query.executeQuery("SELECT items FROM user_dislikes WHERE uid = " + results.getString("id"));
        listResults.next();
        userLists[1] = listResults.getString("items");
      }
      list.add(new User(name, sex, newDob, userLists[0], userLists[1]));
    }
    return list;
  }
  /**
   * Generates recommendations using the already found users.
   */
  private void generateRecs() {
    //TODO implement
  }
}
