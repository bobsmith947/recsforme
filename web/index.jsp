<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="java.net.URLEncoder"%>
<!DOCTYPE html>
<html>
  <title>recsforme :: Home</title>
  <body>
    <main>
      <h3>Welcome to recsforme, a media recommendation web app.</h3>
      <p>recsforme is an open source project aimed at creating a fast and simple application for providing media recommendations. The source code is available under the <a href="https://github.com/bobsmith947/recsforme/blob/master/LICENSE">Apache License 2.0</a>. If you have a bug report, feature request, or anything else of that nature, be sure to give the <a href="https://github.com/bobsmith947/recsforme/blob/master/CONTRIBUTING.md">contributing guidelines</a> a read and submit an issue with any appropriate labels. More information on the project itself can be found <a href="https://bobsmith947.github.io">here</a>.</p>
      <h4>Top searches:</h4>
	  <sql:query var="top" dataSource="jdbc/MediaRecom">
		  SELECT COUNT(*), contents, type FROM user_query
		  GROUP BY contents, type
		  ORDER BY COUNT(*) DESC
		  LIMIT 10
	  </sql:query>
      <div class="list-group text-center mt-3">
        <c:forEach var="query" items="${top.getRows()}">
			<c:set var="contents" value="${query.get('contents')}" />
			<c:set var="type" value="${query.get('type').toString().toLowerCase()}" />
			<a href="search.jsp?query=${URLEncoder.encode(contents, 'UTF-8')}&type=${type}" class="list-group-item list-group-item-action">${contents}</a>
        </c:forEach>
      </div>
    </main>
  </body>
</html>
