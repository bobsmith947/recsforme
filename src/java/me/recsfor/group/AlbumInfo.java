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
import java.io.UnsupportedEncodingException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static java.net.URLDecoder.decode;
import java.util.List;
//import static java.net.URLEncoder.encode;
import me.recsfor.search.AlbumQuery;
import org.musicbrainz.modelWs2.Entity.ReleaseWs2;
/**
 * A servlet to build group pages for albums. It can be initialized using the request parameter (the title of the album). The request parameter has no associated name. For example, <code>AlbumInfo?Homework</code>.
 * @author lkitaev
 */
public class AlbumInfo extends HttpServlet {
  private static final long serialVersionUID = 3558291301985484615L; //just in case
  private String title;
  private String type;
  private String artist;
  private List<ReleaseWs2> releases;
  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String q = request.getQueryString();
    try {
      populate(decode(q, "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      populate();
      title = e.getMessage();
    }
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      out.println("<!DOCTYPE html>");
      out.println("<html><head>");
      out.println("<meta name=\"author\" content=\"Lucas Kitaev\">");
      out.println("<meta name=\"keywords\" content=\"\">");
      out.println("<meta name=\"description\" content=\"\">");
      out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
      out.println("<link href=\"https://fonts.googleapis.com/css?family=Roboto:400,700\" rel=\"stylesheet\">");
      out.println("<link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\">");
      out.println("<script src=\"bundle.js\" type=\"text/javascript\" charset=\"UTF-8\" async></script>");
      out.println("<title>recsforme :: " + title + "</title></head><body>");
      out.println("<h1>recsforme</h1>");
      out.println("<h2>" + title + " (" + type + ")" + "</h2>");
      out.println("<h2>Release group by: " + artist + "</h2>");
      out.println("<h3>Releases:</h3>");
      out.println("<ul>");
      releases.forEach(rel -> {
        String title = rel.getTitle();
        String date = rel.getDateStr();
        out.println("<li>" + title + " - " + date + "</li>");
      });
      //TODO print out recordings
      out.println("</body>");
      out.println("</html>");
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Provides information for album groups.";
  }// </editor-fold>
  
  private void populate(String id) {
    AlbumQuery query = new AlbumQuery(id, true);
    title = query.getQuery();
    type = query.listType();
    artist = query.listArtist();
    releases = query.getAlbums();
  }
  
  private void populate() {
    title = "Unknown title";
    type = "Unknown type";
    artist = "Unknown artist";
    releases = null;
  }
}
