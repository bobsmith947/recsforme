<%@page contentType="text/html" pageEncoding="UTF-8" import="java.net.URLEncoder"%>
<jsp:useBean id="q" scope="request" class="me.recsfor.search_engine.QueryBean" />
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
        <select name="type">
          <option value="movie" selected>TV Show/Movie</option>
          <option value="artist">Artist</option>
          <option value="album">Album</option>
          <option value="song">Song</option>
        </select>
        <input type="search" name="query" maxlength="100" autocomplete="off" value="<jsp:getProperty name="q" property="query" />">
        <button type="submit">Search!</button>
      </form>
      <div id="results">
        <% String[] rs = q.sendMovieQuery().printResults();
          for (int i = 0; i < rs.length; i++) { %>
          <a href="MovieInfo?<%= URLEncoder.encode(rs[i], "UTF-8") %>"><h5><%= rs[i] %></h5></a> <% } %>
      </div>
    </body>
</html>
