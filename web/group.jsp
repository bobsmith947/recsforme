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
    <c:set var="json" value='${ListData.generateItem(pageContext.request.getParameter("name"),
                                 pageContext.request.getParameter("id"),
                                 pageContext.request.getParameter("type"))}' />
    <c:if test='${action == "add" && !u.likeData.containsItem(json) && !u.dislikeData.containsItem(json)}'>
      <c:if test='${status == "like"}'>
        ${u.likeData.list.add(ListData.generateGroup(json))}
      </c:if>
      <c:if test='${status == "dislike"}'>
        ${u.dislikeData.list.add(ListData.generateGroup(json))}
      </c:if>
      <c:if test="${u.loggedIn}">
        <sql:update dataSource="jdbc/MediaRecom">
          UPDATE user_${status}s
          SET items = LEFT(items, LEN(items)-2) + ',' + ? + ']}'
          WHERE uid = ${u.id} AND LEN(items) > 11;
          <sql:param value="${json}" />
          -- for first entry
          UPDATE user_${status}s
          SET items = LEFT(items, LEN(items)-2) + ? + ']}'
          WHERE uid = ${u.id} AND LEN(items) <= 11;
          <sql:param value="${json}" />
        </sql:update>
      </c:if>
    </c:if>
    <c:if test='${action == "reset"}'>
      <c:set var="list" value='${pageContext.request.getParameter("list")}' />
      <c:if test='${list == "both"}'>
        ${u.likeData.list.clear()}
        ${u.dislikeData.list.clear()}
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
        ${u.likeData.list.clear()}
        <c:if test="${u.loggedIn}">
          <sql:update dataSource="jdbc/MediaRecom">
            UPDATE user_likes
            SET items = DEFAULT
            WHERE uid = ${u.id}
          </sql:update>
        </c:if>
      </c:if>
      <c:if test='${list == "dislike"}'>
        ${u.dislikeData.list.clear()}
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
      <c:if test='${status == "like"}'>
        ${u.likeData.removeItem(json)}
        <c:if test="${u.loggedIn}">
          <sql:update dataSource="jdbc/MediaRecom">
            UPDATE user_likes
            SET items = ?
            WHERE uid = ${u.id}
            <sql:param value="${ListData.stringifyData(u.likeData)}" />
          </sql:update>
        </c:if>
      </c:if>
      <c:if test='${status == "dislike"}'>
        ${u.dislikeData.removeItem(json)}
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
      <c:if test="${u.likeData.containsItem(json)}">
        <% response.addHeader("Item-Contained", "like"); %>
      </c:if>
      <c:if test="${u.dislikeData.containsItem(json)}">
        <% response.addHeader("Item-Contained", "dislike"); %>
      </c:if>
    </c:if>
  </body>
</html>
