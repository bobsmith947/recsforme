<%@page contentType="text/html" pageEncoding="UTF-8" import="java.net.URLEncoder"%>
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
      <h2>Enter another search:</h2>
      <form id="media-search" action="search.jsp">
        <input type="search" name="query" maxlength="100" autocomplete="off" value="<jsp:getProperty name="q" property="query" />">
        <select name="type">
          <%  String t = q.getType(); %>
          <option value="movie" <% if (t.equals("movie")) out.print("selected"); %> >TV Show/Movie</option>
          <option value="artist" <% if (t.equals("artist")) out.print("selected"); %> >Artist</option>
          <!--<option value="album" <% if (t.equals("album")) out.print("selected"); %> >Album</option>
          <option value="song" <% if (t.equals("song")) out.print("selected"); %> >Song</option>-->
          <%-- TODO implement album and song searchability --%>
        </select>
        <button type="submit">Search!</button>
      </form>
      <div id="results">
        <%  String[] rs;
            String u;
            switch (t) {
              case "movie":
                rs = q.sendMovieQuery().printResults();
                u = "MovieInfo?";
                break;
              case "artist":
                rs = q.sendArtistQuery().printResults();
                u = "ArtistInfo?";
                break;
              case "album":
                rs = q.sendAlbumQuery().printResults();
                u = "AlbumInfo?";
                break;
              case "song":
                rs = q.sendSongQuery().printResults();
                u = "SongInfo?";
                break;
              default:
                rs = null;
                u = "search.jsp?query=";
                break;
            }
            if (rs != null) {
                for (int i = 0; i < rs.length; i++) { %>
        <a href="<%= u + URLEncoder.encode(rs[i], "UTF-8") %>">
          <%= rs[i] %></a> <%  }
            } else out.println("<h3>No results found!"); %>
      </div>
    </body>
</html>
