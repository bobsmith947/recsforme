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
import me.recsfor.engine.search.ArtistQuery;
import org.musicbrainz.modelWs2.Entity.ReleaseGroupWs2;
import org.musicbrainz.modelWs2.Entity.ReleaseWs2;
/**
 * A servlet to build group pages for artists.
 * It can process <code>HTTP GET</code> and <code>POST</code> requests by being given a request parameter (named <code>id</code>) containing the MusicBrainz ID of the respective <code>artist</code>.
 * For example, <code>ArtistInfo?id=056e4f3e-d505-4dad-8ec1-d04f521cbb56</code> will generate a page for Daft Punk.
 * @author lkitaev
 */
public class ArtistInfo extends HttpServlet {
  private static final long serialVersionUID = -8210213618927548383L;
  private String name, type;
  private String[] years;
  private List<ReleaseGroupWs2> albums;
  private List<ReleaseWs2> contrib;
  
  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String id = request.getParameter("id");
    populate(id);
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      out.println("<!DOCTYPE html><html><title>recsforme :: " + name + "</title><body>");
      request.getRequestDispatcher("WEB-INF/jspf/header.jspf").include(request, response);
      out.println("<noscript class=\"alert alert-danger d-block\">Scripts have been disabled. Some features may not work.</noscript><main>");
      out.println("<h2 id=\"name\">" + name + "</h2>");
      out.println("<h3 id=\"type\">" + type + "</h3>");
      String term;
      switch (type) {
        case "Person":
          term = "Alive";
          break;
        case "Group":
          term = "Active";
          break;
        default:
          term = "Alive/Active";
          break;
      }
      out.println("<h3>" + term + ": <span class=\"date\">" + years[0] 
              + "</span> to <span class=\"date\">" + years[1] + "</span></h3>");
      //TODO order release groups by date
      //TODO print out more entries
      out.println("<h3>Discography:</h3><div class=\"list-group text-right my-2\">");
      albums.forEach(album -> {
        out.println("<a class=\"list-group-item list-group-item-action p-2\" href=\"AlbumInfo?id="
                + album.getId() + "\"><h5 class=\"mb-0\">" + album.getTitle()
                + "</h5><small class=\"date\">" + album.getFirstReleaseDateStr() + "</small></a>");
      });
      out.println("</div><h4>Contributions:</h4><div class=\"list-group text-right my-2\">");
      contrib.forEach(cont -> {
        out.println("<a class=\"list-group-item list-group-item-action p-2\" href=\"AlbumInfo?id="
                + cont.getId() + "\"><h5 class=\"mb-0\">" + cont.getTitle()
                + "</h5><small class=\"date\">" + cont.getDateStr() + "</small></a>");
      });
      out.println("</ul></div><h6>May not be exhausitve. Check MusicBrainz if you can't find what you're looking for.</h6>");
      out.println("<h6><a href=\"https://musicbrainz.org/artist/"
              + id + "\">View on MusicBrainz</a></h6>");
      request.getRequestDispatcher("WEB-INF/jspf/vote.jspf").include(request, response);
      out.println("</main>");
      request.getRequestDispatcher("WEB-INF/jspf/footer.jspf").include(request, response);
      out.println("</body></html>");
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
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }
  /**
   * Handles the HTTP <code>POST</code> method.
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
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Provides information for artist groups.";
  }
  
  /**
   * Gives values to instance variables using an artist query.
   * @param id the id of the artist
   */
  private void populate(String id) {
    ArtistQuery artist = new ArtistQuery(id, true);
    name = artist.listTitles()[0];
    type = artist.listType();
    years = artist.listYears();
    albums = artist.listAlbums();
    contrib = artist.listContrib();
  }
}
