<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<c:set var="d" target="q" property="delegation" value="${q.delegateQuery()}" scope="request" />
<!DOCTYPE html>
<html>
  <head>
    <meta name="keywords" content="<jsp:getProperty name="q" property="query" />, <jsp:getProperty name="q" property="type" />, search, query, results">
    <meta name="description" content="recsforme (recs for me) search results for <jsp:getProperty name="q" property="query" /> in <jsp:getProperty name="q" property="type" />s.">
    <title>recsforme :: <jsp:getProperty name="q" property="type" /> Search - <jsp:getProperty name="q" property="query" /></title>
  </head>
  <body>
    <c:set var="con" value="${q.context}" />
    <c:set var="len" value="${d.len}" />
    <div id="results">
      <c:if test="${len > 0}" var="hasResults">
        <sql:update var="added" scope="request" dataSource="jdbc/MediaRecom">
          INSERT INTO qlist (query, qtype)
          VALUES ('<jsp:getProperty name="q" property="query" />', '<jsp:getProperty name="q" property="type" />')
        </sql:update>
        <c:forEach var="i" begin="0" end="${len}" step="1">
          <c:set var="id" value="${d.listIds()[i]}" />
          <c:set var="name" value="${d.listNames()[i]}" />
          <c:url value="/${con}" var="serv" scope="request" />
          <a class="block" href="<c:out value="${serv.concat(id)}" />"><c:out value="${name}" /></a>
        </c:forEach>
      </c:if>
      <c:if test="${!hasResults && q.query.length() != 0}">
        <h3>No results found!</h3>
      </c:if>
    </div>
  </body>
</html>
