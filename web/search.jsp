<%@page import="me.recsfor.search.AbstractQuery"%>
<%@page import="java.net.URLEncoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="q" scope="request" class="me.recsfor.search.QueryBean" />
<jsp:setProperty name="q" property="type" />
<jsp:setProperty name="q" property="query" />
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="author" content="Lucas Kitaev">
    <meta name="keywords" content="<jsp:getProperty name="q" property="query" />">
    <meta name="description" content="recsforme (recs for me) search results for <jsp:getProperty name="q" property="query" />.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
    <link href="style.css" rel="stylesheet" type="text/css">
    <script src="bundle.js" type="text/javascript" charset="UTF-8" async></script>
    <title>recsforme :: Search - <jsp:getProperty name="q" property="query" /></title>
  </head>
  <body>
    <h1>recsforme</h1>
    <h2>Enter a search:</h2>
    <form id="media-search" action="search.jsp">
      <input type="search" name="query" maxlength="100" autocomplete="off" value="<jsp:getProperty name="q" property="query" />" autofocus>
      <select name="type">
        <%  String t = q.getType(); %>
        <option value="movie" <% if (t.equals("movie")) out.print("selected"); %> >TV Show/Movie</option>
        <option value="artist" <% if (t.equals("artist")) out.print("selected"); %> >Artist</option>
        <option value="album" <% if (t.equals("album")) out.print("selected"); %> >Album/EP/Single</option>
      </select>
      <button type="submit">Search</button>
    </form>
    <div id="results">
      <%  String[] nms;
          String[] ids;
          String serv;
          AbstractQuery r;
          switch (t) {
            case "movie":
              r = q.sendMovieQuery();
              nms = r.listNames();
              ids = r.listIds();
              serv = "MovieInfo?";
              break;
            case "artist":
              r = q.sendArtistQuery();
              nms = r.listNames();
              ids = r.listIds();
              serv = "ArtistInfo?";
              break;
            case "album":
              r = q.sendAlbumQuery();
              nms = r.listNames();
              ids = r.listIds();
              serv = "AlbumInfo?";
              break;
            default:
              r = null;
              nms = null;
              ids = null;
              serv = "search.jsp?query=";
              break;
          }
          //if (nms != null) {
            for (int i = 0; i < r.getLen(); i++) { %>
              <a href="<%= serv + URLEncoder.encode(ids[i], "UTF-8") %>">
              <%= nms[i] %></a> <%
            }
          //} else out.println("<h3>No results found!</h3>"); %>
    </div>
  </body>
</html>
