<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="q" scope="request" class="me.recsfor.search.QueryBean" />
<jsp:setProperty name="q" property="type" />
<jsp:setProperty name="q" property="query" />
<%
  q.setDelegation(q.delegateQuery());
  String t = q.getType().toLowerCase();
  String[] nms = q.listNames();
  String[] ids = q.listIds();
  String con = q.getContext();
%>
<!DOCTYPE html>
<html>
  <head>
    <meta name="author" content="Lucas Kitaev">
    <meta name="keywords" content="<jsp:getProperty name="q" property="query" />">
    <meta name="description" content="recsforme (recs for me) search results for <jsp:getProperty name="q" property="query" /> <jsp:getProperty name="q" property="type" />.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="style.css" rel="stylesheet" type="text/css">
    <!--<script src="bundle.js" type="text/javascript" charset="UTF-8" async></script>-->
    <title>recsforme :: <jsp:getProperty name="q" property="type" /> Search - <jsp:getProperty name="q" property="query" /></title>
  </head>
  <body>
    <h1>recsforme</h1>
    <h2>Enter a search:</h2>
    <form id="media-search" action="search.jsp">
      <input type="search" name="query" maxlength="100" autocomplete="off" value="<jsp:getProperty name="q" property="query" />" autofocus>
      <select name="type">
        <option value="movie" <% if (t.equals("movie")) out.print("selected"); %> >TV Show/Movie</option>
        <option value="artist" <% if (t.equals("artist")) out.print("selected"); %> >Artist</option>
        <option value="album" <% if (t.equals("album")) out.print("selected"); %> >Album/EP/Single</option>
      </select>
      <button type="submit">Search</button>
    </form>
    <div id="results">
      <%
        if (!q.getResults().isEmpty()) {
          for (int i = 0; i < q.getLen(); i++) {
            if (t.equals("album")) ids[i] = ids[i].concat("&");
      %>
            <a class="block" href="<%= con + ids[i] %>">
            <%= nms[i] %></a>
      <%
          }
        } else out.println("<h3>No results found!</h3>");
      %>
    </div>
  </body>
</html>
