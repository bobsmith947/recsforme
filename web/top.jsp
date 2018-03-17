<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta name="author" content="Lucas Kitaev">
    <meta name="keywords" content="recommendation engine, recommender system, open source, java, web app, web application">
    <meta name="description" content="recsforme (recs for me) top movie, artist, and album searches. Find out what's popular!">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="post.css" rel="stylesheet" type="text/css">
    <script src="bundle.js" type="text/javascript" charset="UTF-8" async></script>
    <title>recsforme :: Top</title>
  </head>
  <body>
    <h2>Top searches:</h2>
    <sql:query var="result" dataSource="jdbc/MediaRecom">
      SELECT query, qtype FROM qlist
    </sql:query>
    <table border="1">
      <!-- column headers -->
      <tr>
        <c:forEach var="columnName" items="${result.columnNames}">
          <th><c:out value="${columnName}"/></th>
        </c:forEach>
      </tr>
      <!-- column data -->
      <c:forEach var="row" items="${result.rowsByIndex}">
        <tr>
          <c:forEach var="column" items="${row}">
            <td><c:out value="${column}"/></td>
          </c:forEach>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>
