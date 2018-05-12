<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.app.ListData"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<!DOCTYPE html>
<html>
  <title>Logging out...</title>
  <body>
    <c:if test="${u.loggedIn}">
      <sql:update dataSource="jdbc/MediaRecom">
        UPDATE user_likes
        SET items = ?
        WHERE uid = ${u.id}
        <sql:param value="${ListData.stringifyData(u.likeData)}" />
      </sql:update>
      <sql:update dataSource="jdbc/MediaRecom">
        UPDATE user_dislikes
        SET items = ?
        WHERE uid = ${u.id}
        <sql:param value="${ListData.stringifyData(u.dislikeData)}" />
      </sql:update>
      <em>Lists have been added to the database.</em>
      <em>Setting session attributes to null.</em>
      <% session.invalidate(); %>
    </c:if>
    <% response.sendRedirect("index.jsp"); %>
  </body>
</html>
