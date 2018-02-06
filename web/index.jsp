<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="author" content="Lucas Kitaev">
        <meta name="keywords" content="recommendation engine, recommender system, open source, java, web app, web application">
        <meta name="description" content="recs for me is an open source Java web application for media recommendations.">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="style.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
        <script src="bundle.js" type="text/javascript" charset="UTF-8" async></script>
        <title>recs for me :: Home</title>
    </head>
    <body>
        <h1>Enter a movie, TV show, artist, album, or song:</h1>
        <form name="Search Box" action="search.jsp">
            <input id="media-search" type="text" name="query" maxlength="100" autocomplete="on" autofocus>
        </form>
    </body>
</html>
