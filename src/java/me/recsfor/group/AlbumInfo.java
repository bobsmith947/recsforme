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
//import java.util.LinkedList;
import java.util.List;
import me.recsfor.engine.search.AlbumQuery;
import org.musicbrainz.MBWS2Exception;
//import org.musicbrainz.modelWs2.Entity.ReleaseWs2;
import org.musicbrainz.modelWs2.MediumListWs2;
import org.musicbrainz.modelWs2.TrackWs2;
/**
 * A servlet to build group pages for albums, EP's, singles, and other types, including the available track listing.
 * It can process <code>HTTP GET</code> and <code>POST</code> requests by being given a request parameter (named <code>id</code>) containing the MusicBrainz ID of the respective <code>release group</code>.
 * If the ID of a <code>release</code> is given instead, it will attempt to change it to the matching <code>release group</code>.
 * For example, <code>AlbumInfo?id=00054665-89fa-33d5-a8f0-1728ea8c32c3</code> will generate a page for <i>Homework</i> by Daft Punk.
 * @author lkitaev
 */
public class AlbumInfo extends HttpServlet {
  private static final long serialVersionUID = 3558291301985484615L;
  private String title, type, date;
  private String[] artist;
  /**
   * Used for getting track listings.
   */
  private MediumListWs2 info;
  
  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String id = request.getParameter("id");
    try {
      id = checkId(id) ? AlbumQuery.switchId(id) : id;
      populate(id);
    } catch (MBWS2Exception | NullPointerException e) {
      this.log(e.getMessage(), e);
      populate(id);
    }
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      out.println("<!DOCTYPE html><html><title>recsforme :: " + title + "</title><body>");
      request.getRequestDispatcher("WEB-INF/jspf/header.jspf").include(request, response);
      out.println("<noscript class=\"alert alert-danger d-block\">Scripts have been disabled. Some features may not work.</noscript><main>");
      out.println("<h2 id=\"name\">" + title + " - <a href=\"ArtistInfo?id=" + artist[1] + "\">" + artist[0] + "</a></h2>");
      out.println("<h3 id=\"type\">" + type + "</h3>");
      out.println("<h3>Released on: <span class=\"date\">" + date + "</span></h3>");
      //TODO fix duration time
      out.println("<h3>Tracklist:</h2>");
      try {
        List<TrackWs2> tracks = info.getCompleteTrackList();
        out.println("<ol class=\"list-group-ordered\">");
        tracks.forEach(track -> {
          out.println("<li>" + track.getRecording().getTitle()
                  + " - " + track.getDuration() + "</li>");
        });
        out.println("</ol>");
      } catch (NullPointerException e) {
        this.log(e.getMessage(), e);
        out.println("<h4>No tracks found!</h4>");
      }
      //out.println("<h5>Total length: " + info.getDuration() + "</h5>");
      out.println("<h6><a href=\"https://musicbrainz.org/release-group/"
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
    return "Provides information for album groups.";
  }
  
  /**
   * Gives values to instance variables using an album query.
   * @param id the id of the release group
   */
  private void populate(String id) {
    AlbumQuery album = new AlbumQuery(id, true);
    title = album.listTitle();
    type = album.listType();
    artist = album.listArtist();
    date = album.listDate();
    info = album.getInfo();
  }
  /**
   * Check if the ID needs to be switched.
   * True if it does, false otherwise.
   * @param id the id to check
   */
  private static boolean checkId(String id) {
    return new AlbumQuery(id, false).isIsNotGroup();
  }
}
