<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="q" scope="request" class="me.recsfor.engine.search.QueryBean" />
<jsp:setProperty name="q" property="type" />
<jsp:setProperty name="q" property="query" />
<% String t = q.getType().toLowerCase(); %>
<c:set var="d" target="q" property="delegation" value="${q.delegateQuery()}" scope="request" />
<!DOCTYPE html>
<html>
  <title>recsforme :: <jsp:getProperty name="q" property="type" /> Search - <jsp:getProperty name="q" property="query" /></title>
  <body>
    <noscript class="alert alert-danger">Scripts have been disabled. Some features may not work.</noscript>
    <h1><a href="index.jsp">recsforme</a></h1>
    <a href="user.jsp" style="position:absolute;top:5%;left:5%">Your List</a>
    <h2>Search for something:</h2>
    <form id="media-search" action="search.jsp">
      <input type="search" name="query" maxlength="100" autocomplete="off" value="<jsp:getProperty name="q" property="query" />" autofocus required>
      <select name="type">
        <option value="movie" <% if (t.equals("movie")) out.print("selected"); %>>Movie/TV Show/Game</option>
        <option value="artist" <% if (t.equals("artist")) out.print("selected"); %>>Artist</option>
        <option value="album" <% if (t.equals("album")) out.print("selected"); %>>Album/EP/Single</option>
      </select>
      <button type="submit">Search</button>
    </form>
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
          <a class="block" href="<c:out value="${con.concat(id)}" />"><c:out value="${name}" /></a>
        </c:forEach>
      </c:if>
      <c:if test="${!hasResults && q.query.length() != 0}">
        <h3>No results found!</h3>
      </c:if>
    </div>
    <c:choose>
      <c:when test='${t.equals("movie")}'>
        <h6>Search results provided by <a href="https://www.omdbapi.com/">OMDb</a>.</h6>
      </c:when>
      <c:otherwise>
        <h6>Search results provided by <a href="https://musicbrainz.org/">MusicBrainz</a>.</h6>
      </c:otherwise>
    </c:choose>
  </body>
</html>
