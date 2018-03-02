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
import java.util.LinkedList;
import java.util.List;
import me.recsfor.search.AlbumQuery;
import org.musicbrainz.modelWs2.Entity.ReleaseWs2;
import org.musicbrainz.modelWs2.MediumListWs2;
/**
 * A servlet to build group pages for albums, EP's, singles, and other types, including the available releases and recordings.
 * It can process HTTP methods by being given a request parameter containing the MusicBrainz ID of the respective release group. The request parameter has no associated name.
 * An ampersand MUST be present after the ID. It will grab editions in the group if the <code>full</code> parameter is present after this.
 * For example, <code>AlbumInfo?00054665-89fa-33d5-a8f0-1728ea8c32c3{ampersand}</code> generates a page for <i>Homework</i> by Daft Punk.
 * Similarly, <code>AlbumInfo?00054665-89fa-33d5-a8f0-1728ea8c32c3{ampersand}full</code> generates a page for <i>Homework</i> by Daft Punk, with editions present.
 * @author lkitaev
 */
public class AlbumInfo extends AbstractInfo {
  private static final long serialVersionUID = 3558291301985484615L; //just in case
  private String title, type, artist, artistId, date;
  private List<ReleaseWs2> releases;
  private MediumListWs2 info;
  private LinkedList<MediumListWs2> releaseInfo;

  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String q = request.getQueryString();
    String id = q.substring(q.indexOf("?")+1, q.indexOf("&"));
    String f = q.substring(q.indexOf("&")+1);
    boolean full = f.equals("full");
    if (full) {
      populate(id, true);
    } else {
      populate(id, false);
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
      out.println("<h3>Release group by: <a href=\"ArtistInfo?" + artistId + "\">" + artist + "</a></h3>");
      out.println("<h3>Released: <span class=\"date\">" + date + "</span></h3>");
      if (full) {
        //TODO fix ordering
        //TODO fix duration times
        out.println("<h3>Editions:</h3><div>");
        releases.forEach(rel -> {
          MediumListWs2 list = releaseInfo.pop();
          out.println("<div><h4>" + rel.getUniqueTitle() + " - <span class=\"date\">" + rel.getDateStr()
                  + "</span> (" + list.getFormat() + ")</h4><ol>");
          printTracks(list).forEach(t -> out.println(t));
          out.println("</ol><h5>Total length: " + list.getDuration() + "</h5></div>");
        });
        out.println("</div><h6>May not be exhausitve. Check MusicBrainz if you can't find what you're looking for.</h6>");
      } else {
        out.println("<h3>Tracklist:</h2><ol>");
        printTracks(info).forEach(t -> out.println(t));
        out.println("</ol><h5>Total length: " + info.getDuration() + "</h5>");
        out.println("<a class=\"block\" href=\"AlbumInfo?"
                + id + "&full\">Retrieve editions (may take a while)</a>");
      }
      out.println("<a class=\"block\" href=\"https://musicbrainz.org/release-group/"
              + q.substring(q.indexOf("=")+1, q.indexOf("&")) + "\">View on MusicBrainz</a>");
      request.getRequestDispatcher("WEB-INF/jspf/footer.jspf").include(request, response);
      out.println("</body></html>");
    }
  }
  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Provides information for album groups.";
  }
  /**
   * Gives values to instance variables.
   * @param id the release-group id
   * @param full whether to generate full edition info or not
   */
  private void populate(String id, boolean full) {
    AlbumQuery query = new AlbumQuery(id, true, full);
    title = query.getQuery();
    type = query.listType();
    artist = query.listArtist();
    artistId = query.listArtistId();
    date = query.listDate();
    releases = query.getAlbums();
    info = query.getInfo();
    releaseInfo = query.getFullInfo();
  }
  /**
   * Compiles tracks to be printed out with the servlet's <code>PrintWriter</code>.
   * @param media the release medium containing the tracks
   * @return a LinkedList containing the tracks
   */
  private LinkedList<String> printTracks(MediumListWs2 media) {
    LinkedList<String> ret = new LinkedList<>();
    media.getCompleteTrackList().forEach(track -> ret.add("<li>" + track.getRecording().getTitle()
                + " - " + track.getDuration() + "</li>"));
    return ret;
  }
}
