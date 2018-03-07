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
import javax.servlet.ServletException;
import java.io.PrintWriter;
//import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import java.io.UnsupportedEncodingException;
//import static java.net.URLDecoder.decode;
//import static java.net.URLEncoder.encode;
import me.recsfor.search.MovieQuery;
/**
 * A servlet to build group pages for movies and TV shows.
 * It can process <code>HTTP GET</code> and <code>POST</code> by being given a request parameter (named <code>id</code>) containing the IMDb ID of the respective movie/TV show.
 * For example, <code>MovieInfo?id=tt0083658</code> will generate a page for <i>Blade Runner</i>.
 * @author lkitaev
 */
public class MovieInfo extends AbstractInfo {
  private static final long serialVersionUID = -4184169288250689262L;
  private static final String GROUP_TYPE = "Movie";
  private String title, year, type, plot;

  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String id = request.getParameter("id");
    populate(id);
    //request.setAttribute("name", title);
    //request.setAttribute("type", GROUP_TYPE);
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      out.println("<!DOCTYPE html>");
      out.println("<html><head>");
      out.println("<meta name=\"keywords\" content=\"\">");
      out.println("<meta name=\"description\" content=\"\">");
      out.println("<title>recsforme :: " + title + " - " + GROUP_TYPE + "</title></head><body>");
      request.getRequestDispatcher("WEB-INF/jspf/header_servlet.jspf").include(request, response);
      out.println("<h2>" + title + " (" + year + ") - " + type + "</h2>");
      out.println("<p>" + plot + "</p>");
      out.println("<a class=\"block\" href=\"https://imdb.com/title/"
              + id + "\">View on IMDb</a>");
      request.getRequestDispatcher("WEB-INF/jspf/vote.jspf").include(request, response);
      request.getRequestDispatcher("WEB-INF/jspf/footer.jspf").include(request, response);
      out.println("</body></html>");
    }
  }
  
  @Override
  public String getServletInfo() {
    return "Provides information for movie/TV show groups.";
  }
  /**
   * Gives values to instance variables.
   * @param id the movie/series id
   */
  private void populate(String id) {
    MovieQuery query = new MovieQuery(id, true, "full");
    title = query.getQuery();
    year = query.listYear();
    type = query.listType();
    plot = query.listPlot();
  }
}
