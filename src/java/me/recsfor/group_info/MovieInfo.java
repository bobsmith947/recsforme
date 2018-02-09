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
package me.recsfor.group_info;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import me.recsfor.search_engine.MovieQuery;
/**
 * A servlet to build group pages for movies and TV shows. It can be initialized using the request parameter (the title of the move/show). The request parameter has no associated name. For example, <code>MovieInfo?Blade+Runner</code>.
 * @author lkitaev
 */
public class MovieInfo extends HttpServlet {
    //TODO implement init() and destroy() support
    private String title;
    private String year;
    private String type;
    private String plot;
    private String id;
    public MovieInfo() {
        title = null;
        year = null;
        type = null;
        plot = null;
        id = null;
    }
    public MovieInfo(String title) {
        MovieQuery query = new MovieQuery(title);
        this.title = query.getQuery();
        year = query.printYear();
        type = query.printType();
        plot = query.printPlot();
        id = query.printId();
    }
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
        MovieInfo info;
        try {
            info = new MovieInfo(URLDecoder.decode(q, "UTF-8"));
            //info = new MovieInfo(URLDecoder.decode(q.substring(q.indexOf("=")+1), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            info = new MovieInfo();
            info.setTitle(e.getMessage());
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
            out.println("<title>recsforme :: " + info.getTitle() + "</title></head><body>");
            out.println("<h1>recsforme</h1>");
            out.println("<h2>" + info.getTitle() + " (" + info.getYear() + ") - " + info.getType() + "</h2>");
            out.println("<p>" + info.getPlot() + "</p>");
            out.println("<a style=\"display:block;text-align:center;margin:20px\" href=\"https://imdb.com/title/" + info.getId() + "\">View on IMDb</a>");
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
        return "Provides information for movie/TV show groups.";
    }// </editor-fold>

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the plot
     */
    public String getPlot() {
        return plot;
    }

    /**
     * @param plot the plot to set
     */
    public void setPlot(String plot) {
        this.plot = plot;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

}
