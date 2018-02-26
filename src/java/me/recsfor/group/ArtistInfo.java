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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import java.io.UnsupportedEncodingException;
//import static java.net.URLDecoder.decode;
//import static java.net.URLEncoder.encode;
import java.util.List;
import me.recsfor.search.ArtistQuery;
import org.musicbrainz.modelWs2.Entity.ReleaseGroupWs2;
import org.musicbrainz.modelWs2.Entity.ReleaseWs2;
/**
 * A servlet to build group pages for artists.
 * It can process HTTP methods by being given a request parameter containing the MusicBrainz ID of the respective artist. The request parameter has no associated name.
 * For example, <code>ArtistInfo?056e4f3e-d505-4dad-8ec1-d04f521cbb56</code> will generate a page for Daft Punk.
 * @author lkitaev
 */
public class ArtistInfo extends HttpServlet {
  private static final long serialVersionUID = -8210213618927548383L; //just in case
  private String name;
  private String type;
  private String[] years;
  private List<ReleaseGroupWs2> albums;
  private List<ReleaseWs2> contrib;
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
    populate(q);
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
      out.println("<title>recsforme :: " + name + "</title></head><body>");
      out.println("<h1>recsforme</h1>");
      out.println("<h2>" + name + " - " + type + "</h2>");
      out.println("<h3>" + years[0] + " to " + years[1] + "</h3>");
      //TODO order albums by date
      out.println("<h3>Discography:</h3>");
      out.println("<ul>");
      albums.forEach(album -> out.println("<li><a href=\"AlbumInfo?" 
              + album.getId() + "&\">" + album.getTitle() + "</a></li>"));
      out.println("</ul>");
      out.println("<h4>Contributions:</h4>");
      out.println("<ul>");
      contrib.forEach(cont -> out.println("<li><a href=\"AlbumInfo?" 
              + cont.getId() + "\">" + cont.getTitle() + "</a></li>"));
      out.println("</ul></div><h6>May not be exhausitve. Check MusicBrainz if you can't find what you're looking for.</h6>");
      out.println("<a style=\"display:block;text-align:center;margin:20px\" href=\"https://musicbrainz.org/artist/" 
              + q + "\">View on MusicBrainz</a>");
      out.println("</body></html>");
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
    return "Provides information for artist groups.";
  }// </editor-fold>

  /**
   * Gives values to instance variables.
   * @param id the artist id
   */
  private void populate(String id) {
    ArtistQuery query = new ArtistQuery(id, true);
    name = query.getQuery();
    type = query.listType();
    years = query.listYears();
    albums = query.listAlbums();
    contrib = query.listContrib();
  }
}
