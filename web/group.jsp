<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.app.ListData"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<!DOCTYPE html>
<html>
  <title>Group Page</title>
  <body>
    <c:set var="status" value='${pageContext.request.getParameter("status")}' />
    <c:set var="action" value='${pageContext.request.getParameter("action")}' />
    <c:set var="item" value='${ListData.generateItem(pageContext.request.getParameter("name"),
                                 pageContext.request.getParameter("id"),
                                 pageContext.request.getParameter("type"))}' />
    <c:set var="group" value="${ListData.generateGroup(item)}" />
    <c:if test='${action == "add"}'>
      <c:if test='${status == "like"}'>
        <c:choose>
          <c:when test="${u.dislikeData.list.contains(group)}">
            <h5 class="text-danger">This group already exists on your dislikes list.</h5>
            <% response.sendError(400); %>
          </c:when>
          <c:otherwise>
            <c:set var="added" value="${u.likeData.list.add(group)}" />
          </c:otherwise>
        </c:choose>
      </c:if>
      <c:if test='${status == "dislike"}'>
        <c:choose>
          <c:when test="${u.likeData.list.contains(group)}">
            <h5 class="text-danger">This group already exists on your likes list.</h5>
            <% response.sendError(400); %>
          </c:when>
          <c:otherwise>
            <c:set var="added" value="${u.dislikeData.list.add(group)}" />
          </c:otherwise>
        </c:choose>
      </c:if>
      <c:if test="${u.loggedIn && added}">
        <sql:update dataSource="jdbc/MediaRecom">
          UPDATE user_${status}s
          SET items = LEFT(items, LEN(items)-2) + ',' + ? + ']}'
          WHERE uid = ${u.id} AND LEN(items) > 11;
          <sql:param value="${item}" />
          -- for first entry
          UPDATE user_${status}s
          SET items = LEFT(items, LEN(items)-2) + ? + ']}'
          WHERE uid = ${u.id} AND LEN(items) <= 11;
          <sql:param value="${item}" />
        </sql:update>
      </c:if>
    </c:if>
    <c:if test='${action == "reset"}'>
      <c:set var="list" value='${pageContext.request.getParameter("list")}' />
      <c:if test='${list == "both"}'>
        <% u.getLikeData().getList().clear(); %>
        <% u.getDislikeData().getList().clear(); %>
        <c:if test="${u.loggedIn}">
          <sql:update dataSource="jdbc/MediaRecom">
            UPDATE user_likes
            SET items = DEFAULT
            WHERE uid = ${u.id}

            UPDATE user_dislikes
            SET items = DEFAULT
            WHERE uid = ${u.id}
          </sql:update>
        </c:if>
      </c:if>
      <c:if test='${list == "like"}'>
        <% u.getLikeData().getList().clear(); %>
        <c:if test="${u.loggedIn}">
          <sql:update dataSource="jdbc/MediaRecom">
            UPDATE user_likes
            SET items = DEFAULT
            WHERE uid = ${u.id}
          </sql:update>
        </c:if>
      </c:if>
      <c:if test='${list == "dislike"}'>
        <% u.getDislikeData().getList().clear(); %>
        <c:if test="${u.loggedIn}">
          <sql:update dataSource="jdbc/MediaRecom">
            UPDATE user_dislikes
            SET items = DEFAULT
            WHERE uid = ${u.id}
          </sql:update>
        </c:if>
      </c:if>
    </c:if>
    <c:if test='${action == "remove"}'>
      <c:if test='${status == "like" && u.likeData.list.remove(group)}'>
        <c:if test="${u.loggedIn}">
          <sql:update dataSource="jdbc/MediaRecom">
            UPDATE user_likes
            SET items = ?
            WHERE uid = ${u.id}
            <sql:param value="${ListData.stringifyData(u.likeData)}" />
          </sql:update>
        </c:if>
      </c:if>
      <c:if test='${status == "dislike" && u.dislikeData.list.remove(group)}'>
        <c:if test="${u.loggedIn}">
          <sql:update dataSource="jdbc/MediaRecom">
            UPDATE user_dislikes
            SET items = ?
            WHERE uid = ${u.id}
            <sql:param value="${ListData.stringifyData(u.dislikeData)}" />
          </sql:update>
        </c:if>
      </c:if>
    </c:if>
    <c:if test='${action == "check"}'>
      <c:if test="${u.likeData.list.contains(group)}">
        <% response.addHeader("Item-Contained", "like"); %>
      </c:if>
      <c:if test="${u.dislikeData.list.contains(group)}">
        <% response.addHeader("Item-Contained", "dislike"); %>
      </c:if>
    </c:if>
  </body>
</html>
