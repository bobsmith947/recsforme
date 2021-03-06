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

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

//import java.io.UnsupportedEncodingException;
//import static java.net.URLDecoder.decode;
//import static java.net.URLEncoder.encode;

import me.recsfor.engine.search.sql.ArtistQuerySQL;
import me.recsfor.group.model.Artist;

/**
 * A servlet to build group pages for artists via <code>GET</code> request.
 * The request parameter <code>id</code> must contain the MusicBrainz ID of the artist.
 * For example, <code>ArtistInfo?id=056e4f3e-d505-4dad-8ec1-d04f521cbb56</code>
 * will generate a page for Daft Punk.
 * @author lkitaev
 */
public class ArtistInfo extends HttpServlet {
	private static final long serialVersionUID = -8210213618927548383L;
	private Artist artist;
	  
	@Resource(name="jdbc/MediaRecom")
	private DataSource db;
	  
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		ArtistQuerySQL query = null;
		try {
			query = new ArtistQuerySQL(id, db);
			artist = query.query();
		} catch (SQLException e) {
			throw new ServletException(e);
		} finally {
			if (query != null)
				query.close();
		}
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			out.println("<!DOCTYPE html><html><title>recsforme :: " + artist.getName() + "</title><body>");
			request.getRequestDispatcher("WEB-INF/jspf/header.jspf").include(request, response);
			out.println("<main><h2 id=\"name\">" + artist.getName()
					+ " <small class=\"text-muted\">(" + artist.getSortName() + ")</small></h2>");
			out.println("<h3 id=\"type\">" + artist.getType()
					+ " <small class=\"text-muted\">" + artist.getGender() + "</small></h3>");
			if (artist.getComment() != null) {
				out.println("<h4>" + artist.getComment() + "<h4>");
			}
			out.println("<h3>" + yearsType() + ": <span class=\"date\">" + artist.getBegin()
					+ "</span> to <span class=\"date\">" + artist.getEnd() + "</span></h3>");
			request.getRequestDispatcher("WEB-INF/jspf/vote.jspf").include(request, response);
			out.println("<h3>Discography:</h3><div class=\"text-right my-2\">");
			out.println("<a href=\"#\" class=\"orderer\" data-target=\"#discog\">Toggle order</a>");
			out.println("<div class=\"list-group\" id=\"discog\">");
			StringBuilder discog = new StringBuilder();
			artist.getDiscog().forEach(album -> {
				discog.append("<a class=\"list-group-item list-group-item-action p-2\" href=\"AlbumInfo?id=")
						.append(album.getId())
						.append("\"><h5 class=\"mb-0\">")
						.append(album.getTitle())
						.append("</h5><small class=\"date\">")
						.append(album.getFirstRelease())
						.append("</small></a>");
			});
			out.println(discog.toString());
			out.println("</div></div>");
			out.println("</main>");
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
		return "Provides information for artist groups.";
	}
	  
	/**
	 * Determines how the years of an artist should be referred to as based on its type.
	 * @return the proper to term to use
	 */
	private String yearsType() {
		switch (artist.getType()) {
			case "Person":
				return "Alive";
			case "Group":
				return "Active";
			default:
				return "Alive/Active";
		}
	}
}
