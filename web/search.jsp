<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="vq" scope="request" class="me.recsfor.search_engine.MovieQuery" />
<jsp:setProperty name="vq" property="query" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="author" content="Lucas Kitaev">
        <meta name="keywords" content="<jsp:getProperty name="vq" property="query" />">
        <meta name="description" content="recsforme (recs for me) search results for <jsp:getProperty name="vq" property="query" />.">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
        <link href="style.css" rel="stylesheet" type="text/css">
        <script src="bundle.js" type="text/javascript" charset="UTF-8" async></script>
        <title>recsforme :: Search - <jsp:getProperty name="vq" property="query" /></title>
    </head>
    <body>
      <h1>recsforme</h1>
      <h2>Enter a movie or TV show:</h2>
      <form action="search.jsp">
        <input id="media-search" type="text" name="query" maxlength="100" autocomplete="off" value="<jsp:getProperty name="vq" property="query" />">
      </form>
      <% String[] rs = vq.printResults();
        for (int i = 0; i < rs.length; i++) { %>
      <h5><%= rs[i] %></h5> <% } %>
    </body>
</html>
