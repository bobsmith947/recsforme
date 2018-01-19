<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="author" content="Lucas Kitaev">
        <meta name="keywords" content="">
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="style.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
        <script src="script.js" type="text/javascript" charset="UTF-8" async></script>
        <title>recs for me :: Search</title>
    </head>
    <body>
        <jsp:useBean id="search" scope="request" class="me.recsfor.search_engine.MediaQuery" />
        <jsp:setProperty name="search" property="query" />
        <h1><jsp:getProperty name="search" property="query" /></h1>
    </body>
</html>
