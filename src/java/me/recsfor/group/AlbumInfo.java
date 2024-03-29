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
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
//import java.io.UnsupportedEncodingException;
//import static java.net.URLDecoder.decode;
//import static java.net.URLEncoder.encode;
import me.recsfor.engine.search.sql.AlbumQuerySQL;
import me.recsfor.group.model.Album;
import me.recsfor.group.model.Artist;

/**
 * A servlet to build group pages for albums via <code>GET</code> request.
 * The request parameter <code>id</code> must contain the MusicBrainz ID of the release group</code>.
 * For example, <code>AlbumInfo?id=00054665-89fa-33d5-a8f0-1728ea8c32c3</code>
 * will generate a page for <i>Homework</i> by Daft Punk.
 * @author lkitaev
 */
public class AlbumInfo extends HttpServlet {
	private static final long serialVersionUID = 3558291301985484615L;
	private Album album;
	private List<Artist> artistCredit;
	private String artistCreditString;
	private String coverArt;
	
	@Resource(name="jdbc/MediaRecom")
	private DataSource db;

	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		AlbumQuerySQL query = null;
		try {
			query = new AlbumQuerySQL(id, db);
			album = query.query();
			artistCredit = query.queryArtistCredit();
			artistCreditString = query.queryArtistCreditString();
			//coverArt = query.queryCoverArt();
			coverArt = "https://coverartarchive.org/release-group/" + id + "/front-250";
		} catch (SQLException e) {
			throw new ServletException(e);
		} finally {
			if (query != null)
				query.close();
		}
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			out.println("<!DOCTYPE html><html><title>recsforme :: " + album.getTitle() + "</title><body>");
			request.getRequestDispatcher("WEB-INF/jspf/header.jspf").include(request, response);
			out.println("<main><h2 id=\"name\">" + album.getTitle() 
					+ " - <a href=\"ArtistInfo?id=" + artistCredit.get(0).getId() + "\">"
					+ artistCreditString + "</a></h2>");
			out.println("<img class=\"mx-auto d-block\" src=\"" + coverArt + "\" alt=\"Cover Art\">");
			if (artistCredit.size() > 1) {
				StringBuilder credits = new StringBuilder();
				credits.append("<p>All contributing artists: ");
				artistCredit.forEach(artist -> {
					credits.append("<a href=\"ArtistInfo?id=")
							.append(artist.getId())
							.append("\">")
							.append(artist.getName())
							.append("</a>, ");
				});
				credits.delete(credits.length() - 2, credits.length());
				credits.append("</p>");
				out.println(credits.toString());
			}
			out.println("<h3 id=\"type\">" + chooseType() + "</h3>");
			out.println("<h3>Released on: <span class=\"date\">" + album.getFirstRelease() + "</span></h3>");
			request.getRequestDispatcher("WEB-INF/jspf/vote.jspf").include(request, response);
			out.println("<h4>Tracklist:</h4>");
			out.println("<ol class=\"list-group text-dark mb-3\">");
			StringBuilder tracks = new StringBuilder();
			album.getTrackList().forEach(song -> {
				tracks.append("<li class=\"list-group-item d-flex justify-content-between align-items-center\">")
						.append("<span class=\"badge badge-primary\">")
						.append(song.getPosition())
						.append("</span>")
						.append(song.getTitle())
						.append("<span></span></li>");
			});
			out.println(tracks.toString());
			out.println("</ol></main>");
			request.getRequestDispatcher("WEB-INF/jspf/footer.jspf").include(request, response);
			out.println("</body></html>");
		}
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
	 * Determines how to display the album type.
	 * @return the secondary type if available, otherwise the primary type if known
	 */
	private String chooseType() {
		if (!album.getSecondaryType().isEmpty())
			return album.getSecondaryType();
		if (album.getPrimaryType().isEmpty())
			return "Unknown type";
		return album.getPrimaryType();
	}

}
