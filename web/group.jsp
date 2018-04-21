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
    <c:set var="json" value='${ListData.generateItem(pageContext.request.getParameter("name"),
                                 pageContext.request.getParameter("id"),
                                 pageContext.request.getParameter("type"))}' />
    <c:if test='${action == "add"}'>
      <c:if test='${status == "like" && !u.likeData.containsItem(json)}'>
        ${u.likeData.list.add(ListData.generateGroup(json))}
        <sql:update dataSource="jdbc/MediaRecom">
          UPDATE user_likes
          SET items = LEFT(items, LEN(items)-2) + ',' + ? + ']}'
          WHERE uid = ${u.id} AND LEN(items) > 11;
          <sql:param value="${json}" />
          -- for first entry
          UPDATE user_likes
          SET items = LEFT(items, LEN(items)-2) + ? + ']}'
          WHERE uid = ${u.id} AND LEN(items) <= 11;
          <sql:param value="${json}" />
        </sql:update>
      </c:if>
      <c:if test='${status == "dislike" && !u.dislikeData.containsItem(json)}'>
        ${u.dislikeData.list.add(ListData.generateGroup(json))}
        <sql:update dataSource="jdbc/MediaRecom">
          UPDATE user_dislikes
          SET items = LEFT(items, LEN(items)-2) + ',' + ? + ']}'
          WHERE uid = ${u.id} AND LEN(items) > 11
          <sql:param value="${json}" />
          -- for first entry
          UPDATE user_dislikes
          SET items = LEFT(items, LEN(items)-2) + ? + ']}'
          WHERE uid = ${u.id} AND LEN(items) <= 11;
          <sql:param value="${json}" />
        </sql:update>
      </c:if>
    </c:if>
    <c:if test='${action == "reset" && u.loggedIn}'>
      <c:set var="list" value='${pageContext.request.getParameter("list")}' />
      <c:if test='${list == "both"}'>
        <jsp:setProperty name="u" property="likeData" value="${null}" />
        <sql:update dataSource="jdbc/MediaRecom">
          UPDATE user_likes
          SET items = DEFAULT
          WHERE uid = ${u.id}
        </sql:update>
        <jsp:setProperty name="u" property="dislikeData" value="${null}" />
        <sql:update dataSource="jdbc/MediaRecom">
          UPDATE user_dislikes
          SET items = DEFAULT
          WHERE uid = ${u.id}
        </sql:update>
      </c:if>
      <c:if test='${list == "like"}'>
        <jsp:setProperty name="u" property="likeData" value="${null}" />
        <sql:update dataSource="jdbc/MediaRecom">
          UPDATE user_likes
          SET items = DEFAULT
          WHERE uid = ${u.id}
        </sql:update>
      </c:if>
      <c:if test='${list == "dislike"}'>
        <jsp:setProperty name="u" property="dislikeData" value="${null}" />
        <sql:update dataSource="jdbc/MediaRecom">
          UPDATE user_dislikes
          SET items = DEFAULT
          WHERE uid = ${u.id}
        </sql:update>
      </c:if>
    </c:if>
    <c:if test='${action == "remove" && u.loggedIn}'>
      <c:if test='${status == "like"}'>
        ${u.likeData.removeItem(json)}
        <sql:update dataSource="jdbc/MediaRecom">
          UPDATE user_likes
          SET items = '${ListData.stringifyData(u.likeData)}'
          WHERE uid = ${u.id}
        </sql:update>
      </c:if>
      <c:if test='${status == "dislike"}'>
        ${u.dislikeData.removeItem(json)}
        <sql:update dataSource="jdbc/MediaRecom">
          UPDATE user_dislikes
          SET items = '${ListData.stringifyData(u.dislikeData)}'
          WHERE uid = ${u.id}
        </sql:update>
      </c:if>
    </c:if>
  </body>
</html>
