<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="author" content="Lucas Kitaev">
    <meta name="keywords" content="recommendation engine, recommender system, open source, java, web app, web application">
    <meta name="description" content="recsforme (recs for me) is an open source Java web application for media recommendations.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
    <link href="style.css" rel="stylesheet" type="text/css">
    <script src="bundle.js" type="text/javascript" charset="UTF-8" async></script>
    <title>recsforme :: Home</title>
  </head>
  <body>
    <h1>recsforme</h1>
    <h3>Welcome to recsforme, a media recommendation web app.</h3>
    <h2>Enter a search:</h2>
    <form id="media-search" action="search.jsp">
      <input type="search" name="query" maxlength="100" autocomplete="off" autofocus>
      <select name="type">
        <option value="movie" selected>TV Show/Movie</option>
        <option value="artist">Artist</option>
        <option value="album">Album/EP/Single</option>
      </select>
      <button type="submit">Search</button>
    </form>
  </body>
</html>
