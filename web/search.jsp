<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>recs for me :: Search</title>
    </head>
    <body>
        <jsp:useBean id="search" scope="request" class="search_engine.MediaQuery" />
        <jsp:setProperty name="search" property="query" />
        <h1><jsp:getProperty name="search" property="query" /></h1>
    </body>
</html>
