<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.app.ListData"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<!DOCTYPE html>
<html>
  <title>recsforme :: Group</title>
  <body>
    <c:set var="status" value='${pageContext.request.getParameter("status")}' />
    <c:set var="action" value='${pageContext.request.getParameter("action")}' />
    <c:if test='${action == null}'>
      <c:set var="json" value='${ListData.generateItem(pageContext.request.getParameter("name"),
                                 pageContext.request.getParameter("id"),
                                 pageContext.request.getParameter("type"))}' />
      <c:if test='${status == "like"}'>
        <sql:update dataSource="jdbc/MediaRecom">
          UPDATE user_likes
          SET items = LEFT(items, LEN(items)-2) 
            + ',${json}]}'
          WHERE uid = ${u.id} AND LEN(items) > 11;
          -- for first entry
          UPDATE user_likes
          SET items = LEFT(items, LEN(items)-2) 
            + '${json}]}'
          WHERE uid = ${u.id} AND LEN(items) <= 11;
        </sql:update>
      </c:if>
      <c:if test='${status == "dislike"}'>
        <sql:update dataSource="jdbc/MediaRecom">
          UPDATE user_dislikes
          SET items = LEFT(items, LEN(items)-2) 
            + ',${json}]}'
          WHERE uid = ${u.id} AND LEN(items) > 11
          -- for first entry
          UPDATE user_dislikes
          SET items = LEFT(items, LEN(items)-2) +
            + '${json}]}'
          WHERE uid = ${u.id} AND LEN(items) <= 11;
        </sql:update>
      </c:if>
    </c:if>
    <c:if test='${action == "reset" && u.loggedIn}'>
      <c:set var="list" value='${pageContext.request.getParameter("list")}' />
      <c:choose>
        <c:when test='${list == "both"}'>
          <sql:update dataSource="jdbc/MediaRecom">
            UPDATE user_likes
            SET items = DEFAULT
            WHERE uid = ${u.id}
          </sql:update>
          <sql:update dataSource="jdbc/MediaRecom">
            UPDATE user_dislikes
            SET items = DEFAULT
            WHERE uid = ${u.id}
          </sql:update>
        </c:when>
        <c:when test='${list == "likes"}'>
          <sql:update dataSource="jdbc/MediaRecom">
            UPDATE user_likes
            SET items = DEFAULT
            WHERE uid = ${u.id}
          </sql:update>
        </c:when>
        <c:when test='${list == "dislikes"}'>
          <sql:update dataSource="jdbc/MediaRecom">
            UPDATE user_dislikes
            SET items = DEFAULT
            WHERE uid = ${u.id}
          </sql:update>
        </c:when>
      </c:choose>
    </c:if>
    <c:if test='${action == "remove" && u.loggedIn}'>
      <c:choose>
        <c:when test='${status == "like"}'>
          <% u.getLikeData().removeItem(request.getParameter("name"), 
                  request.getParameter("id"), 
                  request.getParameter("type")); %>
          <sql:update dataSource="jdbc/MediaRecom">
            UPDATE user_likes
            SET items = '${ListData.stringifyData(u.getLikeData())}'
            WHERE uid = ${u.id}
          </sql:update>
        </c:when>
        <c:when test='${status == "dislike"}'>
          <% u.getDislikeData().removeItem(request.getParameter("name"), 
                  request.getParameter("id"), 
                  request.getParameter("type")); %>
          <sql:update dataSource="jdbc/MediaRecom">
            UPDATE user_dislikes
            SET items = '${ListData.stringifyData(u.getDislikeData())}'
            WHERE uid = ${u.id}
          </sql:update>
        </c:when>
      </c:choose>
    </c:if>
    <%-- TODO check for duplicates --%>
  </body>
</html>
