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
 * A servlet to build group pages for albums, EP's, singles, and other types, including the available releases and recordings.
 * It can process HTTP methods by being given a request parameter containing the MusicBrainz ID of the respective release group. The request parameter has no associated name.
 * For example, <code>AlbumInfo?00054665-89fa-33d5-a8f0-1728ea8c32c3</code> will generate a page for <i>Homework</i> by Daft Punk.
 * @author lkitaev
 */
public class AlbumInfo extends AbstractInfo {
  private static final long serialVersionUID = 3558291301985484615L; //just in case
  private String title, type, artist, artistId, date;
  private MediumListWs2 info;

  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String id = request.getQueryString();
    try {
      id = checkId(id) ? AlbumQuery.switchId(id) : id;
      populate(id);
    } catch (MBWS2Exception | NullPointerException e) {
      this.log(e.getMessage(), e);
      populate(id);
    }
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
      out.println("<h2>" + title + " (" + type + ")</h2>");
      out.println("<h3>Released by: <a href=\"ArtistInfo?" + artistId + "\">" + artist + "</a></h3>");
      out.println("<h3>Released on: <span class=\"date\">" + date + "</span></h3>");
      //TODO fix duration time
      out.println("<h3>Tracklist:</h2>");
      try {
        List<TrackWs2> tracks = info.getCompleteTrackList();
        out.println("<ol>");
        tracks.forEach(track -> out.println("<li>" + track.getRecording().getTitle()
                + " - " + track.getDuration() + "</li>"));
        out.println("</ol>");
      } catch (NullPointerException e) {
        this.log(e.getMessage(), e);
        out.println("<h4>No tracks!</h4>");
      }
      out.println("<h5>Total length: " + info.getDuration() + "</h5>");
      out.println("<a class=\"block\" href=\"https://musicbrainz.org/release-group/"
              + id + "\">View on MusicBrainz</a>");
      request.getRequestDispatcher("WEB-INF/jspf/footer.jspf").include(request, response);
      out.println("</body></html>");
    }
  }

  @Override
  public String getServletInfo() {
    return "Provides information for album groups.";
  }
  /**
   * Gives values to instance variables.
   * @param id the release-group id
   * @param full whether to generate full edition info or not
   */
  private void populate(String id) {
    AlbumQuery query = new AlbumQuery(id, true);
    title = query.getQuery();
    type = query.listType();
    artist = query.listArtist();
    artistId = query.listArtistId();
    date = query.listDate();
    info = query.getInfo();
  }
  /**
   * Check if the ID needs to be switched.
   * True if it does, false otherwise.
   * @param id the id
   */
  private boolean checkId(String id) {
    return new AlbumQuery(id, false).isIsNotGroup();
  }
}
