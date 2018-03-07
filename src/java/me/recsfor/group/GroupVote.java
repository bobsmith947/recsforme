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
package me.recsfor.group;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet for sending vote information to <code>vote.jsp</code>.
 * It should not be accessed on its own. Instead, it should be included in another servlet's response using <code>getRequestDispatcher</code>.
 * @author lkitaev
 */
public class GroupVote extends AbstractInfo {
  private static final long serialVersionUID = -8242564446587809256L;

  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String name, id, type;
    try {
      name = request.getParameter("name");
      id = request.getParameter("id");
      type = request.getParameter("type");
    } catch (NullPointerException e) {
      this.log(e.getMessage(), e);
      name = "";
      id = "";
      type = "";
    }
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      out.println("<p>" + name + id + type + "</p>");
    }
  }

  @Override
  public String getServletInfo() {
    return "Included with Info servlets to send vote information.";
  }
}
