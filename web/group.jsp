<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.app.ListData"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<!DOCTYPE html>
<html>
  <title>recsforme :: Group</title>
  <body>
    <c:if test='${pageContext.request.getParameter("action") == null}'>
      <c:set var="json" value='${ListData.generateItem(pageContext.request.getParameter("name"),
                                 pageContext.request.getParameter("id"),
                                 pageContext.request.getParameter("type"))}' />
      <c:if test='${pageContext.request.getParameter("status") == "like"}'>
        <sql:update dataSource="jdbc/MediaRecom">
          UPDATE user_likes
          SET items = LEFT(items, LEN(items)-2) 
            + ',{${json}}]}'
          WHERE uid = ${u.id} AND LEN(items) > 11;
          -- for first entry
          UPDATE user_likes
          SET items = LEFT(items, LEN(items)-2) 
            + '{${json}}]}'
          WHERE uid = ${u.id} AND LEN(items) <= 11;
        </sql:update>
      </c:if>
      <c:if test='${pageContext.request.getParameter("status") == "dislike"}'>
        <sql:update dataSource="jdbc/MediaRecom">
          UPDATE user_dislikes
          SET items = LEFT(items, LEN(items)-2) 
            + ',{${json}}]}'
          WHERE uid = ${u.id} AND LEN(items) > 11
          -- for first entry
          UPDATE user_dislikes
          SET items = LEFT(items, LEN(items)-2) +
            + '{${json}}]}'
          WHERE uid = ${u.id} AND LEN(items) <= 11;
        </sql:update>
      </c:if>
    </c:if>
    <c:if test='${pageContext.request.getParameter("action") == "reset" && u.loggedIn}'>
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
    </c:if>
    <c:if test='${pageContext.request.getParameter("action") == "remove" && u.loggedIn}'>
      <%-- TODO implement group removal --%>
    </c:if>
  </body>
</html>
