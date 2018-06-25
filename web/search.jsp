<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.engine.search.Context" %>
<jsp:useBean id="q" scope="session" class="me.recsfor.engine.search.QueryBean" />
<c:set var="oldType" value="${q.type}" />
<c:set var="oldQuery" value="${q.query}" />
<jsp:setProperty name="q" property="type" />
<jsp:setProperty name="q" property="query" />
<% if (q.getQuery().toLowerCase().equals("make me coffee")) response.sendError(418); %>
<!DOCTYPE html>
<html>
  <title>recsforme :: <c:out value='${q.type}' /> Search - <c:out value='${q.query}' /></title>
  <body>
    <noscript class="alert alert-danger d-block">Scripts have been disabled. Some features may not work.</noscript>
    <main>
      <form id="adv-search" action="search.jsp">
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text">Searching for</span>
          </div>
          <input class="form-control form-control-lg" type="search" name="query" maxlength="100" autocomplete="off" value="<c:out value='${q.query}' />" required>
        </div>
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text">in the</span>
          </div>
          <select class="form-control form-control-lg" name="type">
            <% String t = q.getType().toLowerCase(); %>
            <option value="movie" <% if (t.equals("movie")) out.print("selected"); %>>
              Movies, Television Shows, and Video Games
            </option>
            <option value="artist" <% if (t.equals("artist")) out.print("selected"); %>>
              Music Groups and People
            </option>
            <option value="album" <% if (t.equals("album")) out.print("selected"); %>>
              Albums, EP's, Singles, and Compilations
            </option>
          </select>
          <div class="input-group-append">
            <span class="input-group-text">category.</span>
          </div>
        </div>
        <button class="btn btn-light btn-lg btn-block mt-2 mb-4" type="submit">Search</button>
      </form>
      <c:if test="${!q.query.equals(oldQuery) || !q.type.equals(oldType)}">
        <c:set var="con" value="${Context.valueOf(q.type.toUpperCase())}" />
        <jsp:setProperty name="q" property="context" value="${con.context}" />
        <c:set var="del" value="${con.createQuery(q.query)}" />
        <c:set var="len" value="${del.results.size()}" scope="session" />
        <c:if test="${len != 0}">
          <c:if test="${del.different(oldQuery)}">
            <sql:update dataSource="jdbc/MediaRecom">
              INSERT INTO query_log (query, qtype)
              VALUES (?, '${q.type}')
              <sql:param value='${q.query}' />
            </sql:update>
          </c:if>
          <jsp:setProperty name="q" property="names" value="${del.listNames()}" />
          <jsp:setProperty name="q" property="ids" value="${del.listIds()}" />
        </c:if>
      </c:if>
      <div class="list-group text-center mb-2" id="results">
        <c:if test="${len > 0}">
          <c:forEach var="i" begin="0" end="${len-1}" step="1">
            <a class="list-group-item list-group-item-action" href="${q.context.concat(q.ids[i])}">
              <c:out value="${q.names[i]}" />
            </a>
          </c:forEach>
        </c:if>
        <c:if test="${q.query.length() != 0 && len == 0}">
          <h3>No results found!</h3>
        </c:if>
      </div>
      <c:choose>
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
