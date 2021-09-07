<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="java.net.URLEncoder"%>
<!DOCTYPE html>
<html>
  <title>recsforme :: Home</title>
  <body>
    <main>
      <h3>Welcome to recsforme, a media recommendation web app.</h3>
      <p>
      recsforme is an open source project aimed at creating a fast and simple website for providing media recommendations.
      The source code is available under the Apache License 2.0 on <a href="https://github.com/bobsmith947/recsforme">GitHub</a>.
      If you have a bug report, feature request, or anything else of that nature, you may submit an issue with any appropriate labels to the GitHub repository.
      </p>
      <p>
      Use the search bar at the top to find your favorite (or least favorite) media.
      If nothing comes to mind at the moment, use the below list to explore random entries from the database.
      </p>
      <h4>Random entries:</h4>
	  <sql:query var="top" dataSource="jdbc/MediaRecom">
		  SELECT * FROM groups
		  TABLESAMPLE BERNOULLI (0.0005)
	  </sql:query>
      <div class="list-group text-center mt-3">
        <c:forEach var="query" items="${top.getRows()}">
			<c:set var="name" value="${query.get('name')}" />
			<c:set var="type" value="${query.get('type').toString().toLowerCase()}" />
			<c:set var="gid" value="${query.get('gid')}" />
			<c:if test="${type == 'artist'}">
				<a href="ArtistInfo?id=${gid}" class="list-group-item list-group-item-action">${name}</a>
			</c:if>
			<c:if test="${type == 'album'}">
				<a href="AlbumInfo?id=${gid}" class="list-group-item list-group-item-action">${name}</a>
			</c:if>
        </c:forEach>
      </div>
    </main>
  </body>
</html>
