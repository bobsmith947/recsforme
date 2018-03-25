<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="q" scope="session" class="me.recsfor.engine.search.QueryBean" />
<c:set var="old" value="${q.query}" />
<jsp:setProperty name="q" property="type" />
<jsp:setProperty name="q" property="query" />
<c:set var="d" target="q" property="delegation" value="${q.delegateQuery()}" scope="request" />
<!DOCTYPE html>
<html>
  <title>recsforme :: <jsp:getProperty name="q" property="type" /> Search - <jsp:getProperty name="q" property="query" /></title>
  <body>
    <noscript class="alert alert-danger d-block">Scripts have been disabled. Some features may not work.</noscript>
    <main>
      <form id="adv-search" action="search.jsp">
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text">Searching for</span>
          </div>
          <input id="name" class="form-control" type="search" name="query" maxlength="100" autocomplete="off" value="<jsp:getProperty name="q" property="query" />" required>
        </div>
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text">in the</span>
          </div>
          <select id="choose" class="form-control" name="type">
            <% String t = q.getType().toLowerCase(); %>
            <option value="movie" <% if (t.equals("movie")) out.print("selected"); %>>Movie/TV Show/Video Game</option>
            <option value="artist" <% if (t.equals("artist")) out.print("selected"); %>>Artist</option>
            <option value="album" <% if (t.equals("album")) out.print("selected"); %>>Album/EP/Single</option>
          </select>
          <div class="input-group-append">
            <span class="input-group-text">category.</span>
          </div>
        </div>
        <button class="btn btn-light btn-lg btn-block mt-2 mb-4" type="submit">Search</button>
      </form>
      <c:set var="con" value="${q.context}" />
      <c:set var="len" value="${d.len}" />
      <div class="mb-2" id="results">
        <c:if test="${len > 0}" var="hasResults" scope="request">
          <c:if test="${q.changed(old, q.query)}">
            <sql:update dataSource="jdbc/MediaRecom">
              INSERT INTO query_log (query, qtype)
              VALUES ('<jsp:getProperty name="q" property="query" />', '<jsp:getProperty name="q" property="type" />')
            </sql:update>
          </c:if>
          <div class="list-group text-center">
            <c:forEach var="i" begin="0" end="${len-1}" step="1">
              <c:set var="id" value="${d.listIds()[i]}" />
              <c:set var="name" value="${d.listNames()[i]}" />
              <a class="list-group-item list-group-item-action" href="<c:out value="${con.concat(id)}" />"><c:out value="${name}" /></a>
            </c:forEach>
          </div>
        </c:if>
        <c:if test="${!hasResults && q.query.length() != 0}">
          <h3>No results found!</h3>
        </c:if>
      </div>
      <c:choose>
        <%--for some reason JSTL doesn't support .equals()?--%>
        <c:when test='${q.type == "Movie"}'>
          <h6>Search results provided by <a href="https://www.omdbapi.com/">OMDb</a>.</h6>
        </c:when>
        <c:otherwise>
          <h6>Search results provided by <a href="https://musicbrainz.org/">MusicBrainz</a>.</h6>
        </c:otherwise>
      </c:choose>
    </main>
  </body>
</html>
