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
    <c:set var="changed" />
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
      <c:if test="${added}">
        <c:set var="changed" value="${true}" />
        <h5 class="text-success">This group has been added.</h5>
      </c:if>
      <c:if test="${!added}">
        <h5 class="text-warning">This group could not be added.</h5>
      </c:if>
    </c:if>
    <c:if test='${action == "reset"}'>
      <c:set var="changed" value="${true}" />
      <c:set var="list" value='${pageContext.request.getParameter("list")}' />
      <c:if test='${list == "both"}'>
        <% u.getLikeData().getList().clear(); %>
        <% u.getDislikeData().getList().clear(); %>
        <h5 class="text-success">Both of your lists have been reset.</h5>
      </c:if>
      <c:if test='${list == "like"}'>
        <% u.getLikeData().getList().clear(); %>
        <h5 class="text-success">Your likes list has been reset.</h5>
      </c:if>
      <c:if test='${list == "dislike"}'>
        <% u.getDislikeData().getList().clear(); %>
        <h5 class="text-success">Your dislikes list has been reset.</h5>
      </c:if>
    </c:if>
    <c:if test='${action == "remove"}'>
      <c:if test='${status == "like"}'>
        <c:set var="removed" value="${u.likeData.list.remove(group)}" />
      </c:if>
      <c:if test='${status == "dislike"}'>
        <c:set var="removed" value="${u.dislikeData.list.remove(group)}" />
      </c:if>
      <c:if test="${removed}">
        <c:set var="changed" value="${true}" />
        <h5 class="text-success">This group has been removed.</h5>
      </c:if>
      <c:if test="${!removed}">
        <h5 class="text-warning">This group could not be removed.</h5>
      </c:if>
    </c:if>
    <c:if test='${action == "check"}'>
      <c:choose>
        <c:when test="${u.likeData.list.contains(group)}">
          <h5 class="text-success">This group exists on your likes list.</h5>
          <% response.addHeader("Item-Contained", "like"); %>
        </c:when>
        <c:when test="${u.dislikeData.list.contains(group)}">
          <h5 class="text-success">This group exists on your dislikes list.</h5>
          <% response.addHeader("Item-Contained", "dislike"); %>
        </c:when>
        <c:otherwise>
          <h5 class="text-success">This group does not exist on either of your lists.</h5>
        </c:otherwise>
      </c:choose>
    </c:if>
    <c:if test="${u.loggedIn && changed}">
      <sql:update dataSource="jdbc/MediaRecom">
        UPDATE user_likes
        SET items = ?
        WHERE uid = ${u.id}
        <sql:param value="${ListData.stringifyData(u.likeData)}" />

        UPDATE user_dislikes
        SET items = ?
        WHERE uid = ${u.id}
        <sql:param value="${ListData.stringifyData(u.dislikeData)}" />
      </sql:update>
      <h5 class="text-success">The database has been updated.</h5>
    </c:if>
  </body>
</html>
