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
import me.recsfor.engine.search.MovieQuery;
/**
 * A servlet to build group pages for movies and TV shows.
 * It can process HTTP methods by being given a request parameter containing the IMDb ID of the respective movie/TV show. The request parameter has no associated name.
 * For example, <code>MovieInfo?tt0083658</code> will generate a page for <i>Blade Runner</i>.
 * @author lkitaev
 */
public class MovieInfo extends AbstractInfo {
  private static final long serialVersionUID = -4184169288250689262L; //just in case
  private String title, year, type, plot;

  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String q = request.getQueryString();
    populate(q);
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      out.println("<!DOCTYPE html>");
      out.println("<html><head>");
      out.println("<meta name=\"author\" content=\"Lucas Kitaev\">");
      out.println("<meta name=\"keywords\" content=\"\">");
      out.println("<meta name=\"description\" content=\"\">");
      out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
      out.println("<link href=\"post.css\" rel=\"stylesheet\" type=\"text/css\">");
      out.println("<script src=\"bundle.js\" type=\"text/javascript\" charset=\"UTF-8\" async></script>");
      out.println("<title>recsforme :: " + title + "</title></head><body>");
      request.getRequestDispatcher("WEB-INF/jspf/header_servlet.jspf").include(request, response);
      out.println("<h2>" + title + " (" + year + ") - " + type + "</h2>");
      out.println("<p>" + plot + "</p>");
      out.println("<a class=\"block\" href=\"https://imdb.com/title/"
              + q + "\">View on IMDb</a>");
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
